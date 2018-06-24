package todoitem;

import database.Mysql;
import exception.DataErrorException;
import javafx.collections.FXCollections;
import kernel.CalendarDate;
import kernel.DateUtil;
import todoitem.Item.ItemType;
import todoitem.itemSub.AnniversaryItem;
import todoitem.itemSub.OtherItem;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.util.*;

public class ItemManager {
    private final static Set<String> overlappedTypes = new HashSet<>(); //不允许重叠的待办事项类型
    private static volatile Mysql mysql = Mysql.getInstance();
    private static volatile ItemManager itemManager;    //volatile关键字保证不能同时对itemManager的读写
    private static ArrayList<Item> itemList = new ArrayList<>();
    private static ArrayList<Item> monthList = new ArrayList<>();

    private ItemManager() {
        overlappedTypes.addAll(FXCollections.observableArrayList(
                ItemType.MEETING.getTypeStr(), ItemType.DATE.getTypeStr(),
                ItemType.COURSE.getTypeStr(), ItemType.INTERVIEW.getTypeStr(),
                ItemType.TRAVEL.getTypeStr()
        ));
    }

    public static ItemManager getInstance() {
        if (itemManager == null) {  //降低同步锁发生概率
            synchronized (ItemManager.class) {  //添加同步锁，保证线程安全
                if (itemManager == null) {
                    itemManager = new ItemManager();
                }
            }
        }
        return itemManager;
    }

    public ArrayList<Item> getItemList() {
        itemListSort();
        return itemList;
    }

    private void itemListSort() {
        if (itemList == null)
            return;
        int length = itemList.size();
        Item tempItem;
        int j;
        for (int i = 1; i < length; i++) {
            tempItem = itemList.get(i);
            for (j = i; j > 0 && tempItem.getPriority() < itemList.get(j - 1).getPriority(); j--) {
                itemList.set(j, itemList.get(j - 1));
            }
            itemList.set(j, tempItem);
        }
    }

    public static void destroy() {
        itemList.clear();
        Mysql.getInstance().clear();
        // 用 itemManager = null 出问题，因为实际上外部可以保存这个单例，导致不是单例。
    }

    /**
     * 更新待办事项的完成状态
     *
     * @param item 待更新的待办事项对象
     */
    public boolean setCompleted(Item item) {
        if (item.isFather()) {//父待办事项一键完成
            ArrayList<Item> childrenItems = getItems(mysql.queryByFatherID(item.getID()));
            boolean allCompleted = true;
            for (Item child : childrenItems) {
                if (child.getStatus() != Const.OVERDUE) {
                    allCompleted &= mysql.updateState(child.getID(), Const.COMPLETED) == Const.COMPLETED;
                }
            }
            return allCompleted && mysql.updateState(item.getID(), Const.COMPLETED) == Const.COMPLETED;
        }
        if (item.getStatus() == Const.IN_PROGRESS) {//在进行中的待办事项才可以设置为完成状态
            if (mysql.updateState(item.getID(), Const.COMPLETED) != Const.COMPLETED) {//没更新成功
                return false;
            } else item.setStatus(Const.COMPLETED);
            ArrayList<HashMap<String, String>> childrenMsg = mysql.queryByFatherID(item.getFatherID());
            boolean allCompleted = true;
            for (HashMap<String, String> msg : childrenMsg) {
                allCompleted &= (Integer.parseInt(msg.get("status")) == Const.COMPLETED);
            }
            if (allCompleted)//所有子待办事项都完成后，更新父待办事项
                return mysql.updateState(item.getFatherID(), Const.COMPLETED) == Const.COMPLETED;
        }
        return item.getStatus() == Const.COMPLETED;
    }

    /**
     * @param item 待更新状态的待办事项
     * @return 返回更新后的状态值
     */
    public int updateStatus(Item item) {
        if (item == null)
            return -1;//更新失败
        if (item.getFrom() == null && item.getTo() == null && item.getStatus() != Const.COMPLETED) {
            item.setStatus(Const.IN_PROGRESS);
        }
        Calendar time = Calendar.getInstance();
        TimeStamp stamp = new TimeStamp(time.get(Calendar.YEAR), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH),
                time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
        TimeStamp from = item.getFrom();
        TimeStamp to = item.getTo();
        int st = item.getStatus(); //待办事项当前完成状态
        int status = Const.BEFORE_BEGINNING;
        if (stamp.isAfter(from) && stamp.isBefore(to) && st == Const.BEFORE_BEGINNING) {
            status = Const.IN_PROGRESS;
        } else if (stamp.isAfter(to) && st != Const.COMPLETED) {
            status = Const.OVERDUE;
        }
        if (st != Const.COMPLETED && st != Const.IN_PROGRESS) {
            if (item.getID() < 0)//还未插入数据库
                item.setStatus(status);
            else
                mysql.updateState(item.getID(), status);//不是完成状态就自动更新，是否完成由用户更新}
            return status;
        }
        return st;

    }

    public ArrayList<Item> getPrompts(long currentMilis) {
        ArrayList<Item> resultList = new ArrayList<>();

        TimeStamp from = TimeStampFactory.createStampDayStart(1800 , 1, 1 );
        TimeStamp to = TimeStampFactory.createStampDayEnd(2100 , 12,31);
        long currentMinute = currentMilis / (60 * 1000);
        List<Item> list = getItemsByStamp(from , to);
        for (Item tmp : list) {
            if (tmp.getFrom() == null || tmp.getTo() == null) {
                continue;
            }
            if (tmp.promptStatus()) {
                long startMinute = tmp.getFrom().getMinutes() - tmp.minutesAhead();
                long endMinute = tmp.getFrom().getMinutes();
                long minutesDelta = tmp.minutesDelta();
                for (long i = startMinute; i <= endMinute; i += minutesDelta) {
                    if (i == currentMinute) {
                        resultList.add(tmp);
                    }
                }
            }
        }
        return resultList;
    }

    public ArrayList<Item> getCustomItems() {
        ArrayList<Item> customItems = getItems(mysql.queryByTime("", ""));
        CalendarDate today = DateUtil.getToday();
        TimeStamp fr = TimeStampFactory.createStampDayStart(today.getYear(), today.getMonth(), today.getDay());
        TimeStamp to = TimeStampFactory.createStampDayEnd(today.getYear(), today.getMonth(), today.getDay());
        for (Item item : customItems) {
            item.setFrom(fr);
            item.setTo(to);
            item.addAttr("hasTime", "0");
        }
        return customItems;
    }

    public ArrayList<Item> getItemsByStamp(TimeStamp from, TimeStamp to) {
        ArrayList<HashMap<String, String>> itemsMsg = mysql.queryByTime(from.toString(), to.toString());
        ArrayList<Item> resList = new ArrayList<>();
        try {
            CalendarDate today = DateUtil.getToday();
            Item temp = new OtherItem(TimeStampFactory.createStampDayStart(today.getYear(), today.getMonth(), today.getDay()),
                    TimeStampFactory.createStampDayEnd(today.getYear(), today.getMonth(), today.getDay()), "");
            if (temp.isDuringTime(from, to)) {
                resList.addAll(getCustomItems());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resList.addAll(getItems(itemsMsg));
        return resList;
    }

    public ArrayList<Item> getItemsByFatherItem(Item item) {
        ArrayList<HashMap<String, String>> itemsMsg = mysql.queryByFatherID(item.getID());
        if (item.getItemType().equals(ItemType.ANNIVERSARY)) {//纪念日
            AnniversaryItem anniversaryItem = (AnniversaryItem) item;
            if (itemsMsg != null) {
                for (HashMap<String, String> msg : itemsMsg) {
                    TimeStamp frTime = TimeStampFactory.createStampByString(msg.get("startTime"));//TODO mysql
                    if (frTime != null && frTime.getYear() != anniversaryItem.getYear()) {  //只保留当年的纪念日的子待办事项
                        itemsMsg.remove(msg);
                    }
                }
            }
        }
        return getItems(itemsMsg);
    }

    private ArrayList<Item> getItems(ArrayList<HashMap<String, String>> itemsMsg) {
        ArrayList<Item> resItems = new ArrayList<>();
        for (HashMap<String, String> msg : itemsMsg) {
            ItemType type = ItemType.parseItemType(msg.get("type"));
            Item temp = ItemFactory.createItemByItemType(type, msg);
            if (temp != null) {
                updateStatus(temp);
                resItems.add(temp);
            }
        }
        return resItems;
    }

    public Item getItemByID(int id) {
        HashMap<String, String> attrs = mysql.queryByID(id + "");
        ItemType type = ItemType.parseItemType(attrs.get("type"));
        return ItemFactory.createItemByItemType(type, attrs);
    }

    public ArrayList<Item> getMonthDayItems(TimeStamp frTime, TimeStamp toTime) {
        ArrayList<Item> resList = new ArrayList<>();
        for (Item item : monthList) {
            if (item.isDuringTime(frTime, toTime)) {
                resList.add(item);
            }
        }
        return resList;
    }

    public void setMonth(TimeStamp monthStart, TimeStamp monthEnd) {
        monthList = getItemsByStamp(monthStart, monthEnd);
        monthList.addAll(getCustomItems());
    }

    /**
     * @param item    待添加的待办事项
     * @param confirm 确认是否添加
     * @return 添加成功返回id，否则返回0
     */
    public int addItem(Item item, boolean confirm) throws DataErrorException {
        if (confirm && item != null && item.getFrom() != null) {
            if (item.getID() <= 0 && canOverlapped(item)) {
                updateStatus(item);
                int id = mysql.addSchedule(item.getAttrs());
                if (id != 0) {
                    item.setID(id);
                    itemList.add(item);
                }
                return id;
            } else if (item.getID() > 0) {
                deleteItem(item);
                if (canOverlapped(item)) {
                    updateStatus(item);
                    int id = mysql.addSchedule(item.getAttrs());
                    item.setID(id);
                    ArrayList<HashMap<String, String>> childrenMsg = mysql.queryByFatherID(item.getID());
                    if (!childrenMsg.isEmpty()) {
                        for (HashMap childMsg : childrenMsg) {
                            childMsg.replace("fatherID", id + "");
                            mysql.addSchedule(childMsg);
                        }
                    }
                    return id;
                }
            }
        } else if (item != null && item.getFrom() == null) {
            if (item.getID() > 0) {
                deleteItem(item);
            }
            updateStatus(item);
            int id = mysql.addSchedule(item.getAttrs());
            if (id != 0)
                item.setID(id);
            return id;
        }
        return 0;
    }

    /**
     * @param item 待添加的待办事项
     * @return 添加成功返回id
     * @throws DataErrorException 添加失败抛出异常（失败原因）
     */
    public int addChildItem(Item item) throws DataErrorException {
        if (item == null)
            throw new DataErrorException("添加失败");
        Item father = getItemByID(item.getFatherID());
        if (father == null) {
            throw new DataErrorException("添加失败");
        }
        //保证待办事项的子待办事项不会再能添加子待办事项
        if (!father.isFather()) {
            throw new DataErrorException("子待办事项不能再添加子待办事项了！");
        }

        /*
        *某些待办事项不能互相包含，即他们之间是互斥的
        *如：会议不能有约会待办事项作为子待办事项。
        *目前互斥集合为（会议，课程，约会，面试）
        *但是相同类型不互斥。即会议允许存在子会议。
        **/
        Set<String> types = new HashSet<>();
        types.addAll(FXCollections.observableArrayList(
                ItemType.MEETING.getTypeStr(), ItemType.DATE.getTypeStr(),
                ItemType.COURSE.getTypeStr(), ItemType.INTERVIEW.getTypeStr()));
        if (types.remove(father.getItemType().getTypeStr())) {  //true表示父类型为四种类型之一
            if (types.contains(item.getItemType().getTypeStr())) //true表示子待办事项类型为互斥类型
                throw new DataErrorException("会议，课程，约会，面试等为互斥类型！");
        }


        /*
        *父待办事项必须有起止时间，添加的子待办事项时间必须在父待办事项的时间范围内
        *例如: 设置了时间的待办事项不能添加没有设置时间的子待办事项
        *     没有设置时间的待办事项不能设置子待办事项。
        **/
        TimeStamp fatherFr = father.getFrom();
        TimeStamp fatherTo = father.getTo();
        TimeStamp fr = item.getFrom();
        TimeStamp to = item.getTo();
        //必须有起止时间
        if (fatherFr == null || fatherTo == null) {
            throw new DataErrorException("未设置时间的代办事项不能添加子待办事项");
        }
        if (fr == null || to == null)
            throw new DataErrorException("未设置时间的待办事项不能添加为子待办事项");
        if ((fr.isBefore(fatherFr) || to.isAfter(fatherTo)) && !(fr.equals(fatherFr) || to.equals(fatherTo))) {
            throw new DataErrorException("子待办事项的时间应在父待办事项时间范围内");
        }
        ArrayList<Item> items = getItemsByFatherItem(father);
        if (overlappedTypes.contains(item.getItemType().getTypeStr()))
            for (Item temp : items) {
                if (temp.isDuringTime(item.getFrom(), item.getTo()) && overlappedTypes.contains(temp.getItemType().getTypeStr()))
                    throw new DataErrorException("与其他待办事项时间重叠！\n会议，课程，约会，面试，旅程等类型不允许时间重叠！");
            }
        updateStatus(item);
        int id = mysql.addSchedule(item.getAttrs());
        if (id != 0)
            item.setID(id);
        return id;
    }

    /**
     * what if delete the same thing twice;
     */

    public boolean deleteItem(Item item) {
        if (item.isFather()) {//删除子待办事项
            boolean deleteAll = true;
            ArrayList<HashMap<String, String>> childrenMsg = mysql.queryByFatherID(item.getID());
            if (childrenMsg != null)
                for (HashMap<String, String> msg : childrenMsg) {
                    deleteAll &= mysql.deleteSchedule(Integer.parseInt(msg.getOrDefault("scheduleID", msg.get("ID")))) == 0;
                }
            return mysql.deleteSchedule(item.getID()) == 0 && deleteAll;
        } else
            return mysql.deleteSchedule(item.getID()) == 0;
    }


    /**
     * 待办事项时间重叠
     *
     * @param item 待添加的待办事项
     */
    private boolean canOverlapped(Item item) throws DataErrorException {
        /*
        *以下类型的待办事项（会议，课程，约会，面试，旅程）不能出现时间重叠情况，包括相同类型的情况
        *后添加的时间冲突的待办事项不能添加成功并且为用户提示错误（具体到与哪个待办事项在XX时间冲突了）
        *父待办事项在计算时间重叠方面和普通地待办事项没有区别)
        *其余类型待办事项可以时间重叠
        *例子：会议不能和会议时间重叠，会议不能和约会时间重叠。会议不能和纪念日的子待办事项-约会时间重叠。
        **/
        ArrayList<Item> children = getItems(mysql.queryByTime(item.getFrom().toString(), item.getTo().toString()));
        if (children != null && overlappedTypes.contains(item.getItemType().getTypeStr()))
            for (Item child : children) {
                if (overlappedTypes.contains(child.getItemType().getTypeStr())) {
                    if (child.equals(item)) {//如果完全相等，则默认不添加、不提示
                        return false;
                    } else
                        throw new DataErrorException("与其他待办事项时间重叠！\n会议，课程，约会，面试，旅程等类型不允许时间重叠！");
                }
            }
        return true;
    }
}

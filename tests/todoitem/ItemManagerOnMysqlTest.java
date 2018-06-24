package todoitem;

import database.Mysql;
import exception.DataErrorException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import todoitem.itemSub.*;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ItemManagerOnMysqlTest {
    private static Mysql mysql;
    private static ItemManager manager;

    @BeforeClass
    public static void init() {
        mysql = Mysql.getInstance();
        manager = ItemManager.getInstance();
    }

    @Test
    public void getInstance() throws Exception {
        ItemManager manager1 = ItemManager.getInstance();
        ItemManager manager2 = ItemManager.getInstance();
        assertEquals(manager1, manager2);
        ItemManager.destroy();
        ItemManager manager3 = ItemManager.getInstance();   //单例模式
        assertEquals(manager2, manager3);
    }

    @Test
    public void setCompleted() {
        try {
            mysql.clear();
            // 只有处于进行中的待办事项才能设置成完成状态

            // 对于含有子待办事项的待办事项不能直接设置成完成状态

            // 当所有子待办事项都完成时，将父待办事项设置为完成状态

            ArrayList<Item> items = new ArrayList<Item>() {
                {
                    add(new OtherItem(new TimeStamp(2018, 5, 26, 0, 0),
                            new TimeStamp(2019, 6, 1, 23, 50),
                            "进行中"));

                    add(new OtherItem(new TimeStamp(2018, 5, 20, 0, 0),
                            new TimeStamp(2018, 5, 24, 23, 50),
                            "已过期"));

                    add(new OtherItem(new TimeStamp(2019, 6, 2, 0, 0),
                            new TimeStamp(2019, 6, 2, 23, 59),
                            "未开始"));

                    add(new OtherItem(new TimeStamp(2018, 2, 20, 0, 1),
                            new TimeStamp(2018, 2, 26, 0, 2),
                            "已过期"));

                    add(new OtherItem(new TimeStamp(2018, 4, 21, 23, 1),
                            new TimeStamp(2019, 6, 30, 16, 6), "进行中"));

                    add(new OtherItem(new TimeStamp(2018, 5, 20, 5, 42),
                            new TimeStamp(2020, 7, 30, 5, 43), "进行中"));

                    add(new OtherItem(new TimeStamp(2018, 5, 20, 5, 42),
                            new TimeStamp(2021, 7, 30, 5, 43), "进行中，含子待办事项"));

                    add(new OtherItem(new TimeStamp(2018, 5, 20, 5, 42),
                            new TimeStamp(2022, 7, 30, 5, 43), "进行中，含子待办事项"));
                }
            };
            for (Item item : items) {
                manager.addItem(item, true);

            }
            items = manager.getItemsByStamp(
                    new TimeStamp(2018, 1, 1, 0, 0),
                    new TimeStamp(2022, 12, 31, 23, 59)
            );
            for (Item item : items) {
                if (item.getStatus() == Const.OVERDUE || item.getStatus() == Const.BEFORE_BEGINNING
                        || item.getStatus() == Const.COMPLETED) { //未开始或已过期或已完成
                    assertFalse(manager.setCompleted(item));
                } else if (item.getDetailText().equals("进行中，含子待办事项")) {//进行中，含子待办事项
                    //添加子待办事项
                    Item item1 = new OtherItem(new TimeStamp(2018, 5, 20, 6, 0),
                            new TimeStamp(2019, 5, 20, 0, 0),
                            "已完成", Const.PRIORITY, Const.COMPLETED, false);
                    Item item2 = new OtherItem(new TimeStamp(2018, 5, 20, 8, 0),
                            new TimeStamp(2020, 2, 10, 0, 0),
                            "进行中", Const.PRIORITY, Const.IN_PROGRESS, false);
                    item1.setFatherID(item.getID());
                    item2.setFatherID(item.getID());
                    item1.setIsFather(false);
                    item2.setIsFather(false);
                    manager.addChildItem(item1);
                    manager.addChildItem(item2);
                    manager.setCompleted(item2);
                    item = manager.getItemByID(item.getID());
                    //TODO 获取新的父待办事项
                    assertEquals(Const.COMPLETED, item.getStatus());
                } else if (item.getDetailText().equals("进行中")) {
                    assertTrue(manager.setCompleted(item));
                }
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

    @Test
    public void updateStatus() throws Exception {
        mysql.clear();
        // 测试能否正确更新状态
        // 1、未开始

        // 2、进行中

        // 3、过期
        ArrayList<Item> items = new ArrayList<Item>() {
            {
                add(new OtherItem(new TimeStamp(2019, 5, 26, 0, 0),
                        new TimeStamp(2019, 6, 1, 23, 50),
                        "未开始"));
                add(new AppointmentItem(new TimeStamp(2020, 2, 20, 0, 1),
                        new TimeStamp(2021, 2, 26, 0, 2),
                        "未开始", "", ""));

                add(new TravelItem(new TimeStamp(2019, 5, 20, 5, 42),
                        new TimeStamp(2021, 7, 30, 5, 43),
                        "未开始", "", "", ""));

                add(new OtherItem(new TimeStamp(2018, 5, 20, 0, 0),
                        new TimeStamp(2019, 5, 24, 23, 50),
                        "进行中"));

                add(new AppointmentItem(new TimeStamp(2018, 4, 21, 23, 1),
                        new TimeStamp(2019, 6, 30, 16, 6),
                        "进行中", "", ""));

                add(new TravelItem(new TimeStamp(2018, 5, 20, 5, 42),
                        new TimeStamp(2022, 7, 30, 5, 43),
                        "进行中", "", "", ""));

                add(new OtherItem(new TimeStamp(2018, 5, 2, 0, 0),
                        new TimeStamp(2018, 5, 2, 23, 59),
                        "已过期"));

                add(new AppointmentItem(new TimeStamp(2016, 5, 20, 5, 42),
                        new TimeStamp(2017, 7, 30, 5, 43),
                        "已过期", "", ""));

                add(new TravelItem(new TimeStamp(2018, 5, 20, 5, 42),
                        new TimeStamp(2018, 5, 30, 5, 43),
                        "已过期", "", "", ""));
            }

        };
        for (Item item : items) {
            manager.updateStatus(item);// 根据当前时间更新状态
        }

        for (int i = 0; i < 3; i++) {
            assertEquals(Const.BEFORE_BEGINNING, items.get(i).getStatus());//前三个更新后应为：未开始
        }
        for (int i = 3; i < 6; i++) {
            assertEquals(Const.IN_PROGRESS, items.get(i).getStatus()); //第三至六个更新后为：进行中
        }
        for (int i = 6; i < items.size(); i++) {
            assertEquals(Const.OVERDUE, items.get(i).getStatus());//剩下几个更新后为：已过期
        }
    }

    @Test
    public void getItemsByStamp() throws Exception {
        // 测试能否正确查询到纪念日、课程信息
        mysql.clear(); //保证可以添加下列待办事项（不出现时间重叠）
        ArrayList<TimeStamp> startTimes = new ArrayList<TimeStamp>() {
            {
                add(new TimeStamp(2018, 5, 26, 0, 0));
                add(new TimeStamp(2019, 2, 20, 0, 1));
                add(new TimeStamp(2020, 5, 20, 5, 42));
                add(new TimeStamp(2018, 5, 20, 0, 0));
                add(new TimeStamp(2021, 4, 21, 23, 1));
                add(new TimeStamp(2022, 1, 1, 0, 0));
                add(new TimeStamp(2023, 5, 2, 0, 0));
            }
        };
        ArrayList<TimeStamp> endTimes = new ArrayList<TimeStamp>() {
            {
                add(new TimeStamp(2018, 6, 1, 23, 50));
                add(new TimeStamp(2019, 2, 26, 0, 2));
                add(new TimeStamp(2020, 7, 30, 5, 43));
                add(new TimeStamp(2018, 5, 20, 23, 50));
                add(new TimeStamp(2021, 6, 30, 16, 6));
                add(new TimeStamp(2022, 1, 1, 23, 59));
                add(new TimeStamp(2023, 5, 2, 23, 59));
            }
        };
        ArrayList<Item> items = new ArrayList<Item>() {
            {
                add(new OtherItem(startTimes.get(0), endTimes.get(0), ""));

                add(new AppointmentItem(startTimes.get(1), endTimes.get(1), "", "", ""));

                add(new TravelItem(startTimes.get(2), endTimes.get(2), "", "", "", ""));

                add(new CourseItem(startTimes.get(3), endTimes.get(3), "", "", "", "", "", "", ""));

                add(new MeetingItem(startTimes.get(4), endTimes.get(4), "", "", ""));

                add(new AnniversaryItem(startTimes.get(5), endTimes.get(5), "", "", "新年"));

                add(new InterviewItem(startTimes.get(6), endTimes.get(6), "", "", "", ""));

            }

        };
        ArrayList<Item.ItemType> types = new ArrayList<Item.ItemType>() {
            {
                add(Item.ItemType.CUSTOM);
                add(Item.ItemType.DATE);
                add(Item.ItemType.TRAVEL);
                add(Item.ItemType.COURSE);
                add(Item.ItemType.MEETING);
                add(Item.ItemType.ANNIVERSARY);
                add(Item.ItemType.INTERVIEW);
            }
        };
        for (Item item : items) {
            manager.addItem(item, true);
        }

        for (int i = 0; i < startTimes.size(); i++) {
            ArrayList<Item> resItems = manager.getItemsByStamp(startTimes.get(i), endTimes.get(i));
            assertEquals(1, resItems.size());
            assertEquals(types.get(i), resItems.get(0).getItemType());
        }
    }

    @Test
    public void addItem() throws Exception {
        mysql.clear();
        //一、测试互斥类型不允许作父子的情况


        //二、测试时间段不允许重叠的情况
        /**
         *  1、纪念日
         */

        /**
         * 2、课程
         */

        ArrayList<TimeStamp> startTimes = new ArrayList<TimeStamp>() {
            {
                add(new TimeStamp(2018, 5, 26, 0, 0));
                add(new TimeStamp(2018, 5, 26, 10, 0));
                add(new TimeStamp(2018, 5, 25, 0, 1));
                add(new TimeStamp(2018, 5, 26, 11, 0));

                add(new TimeStamp(2020, 5, 20, 5, 42));
                add(new TimeStamp(2020, 5, 20, 0, 0));
                add(new TimeStamp(2020, 5, 18, 23, 1));
                add(new TimeStamp(2020, 6, 1, 0, 0));
            }
        };
        ArrayList<TimeStamp> endTimes = new ArrayList<TimeStamp>() {
            {
                add(new TimeStamp(2018, 6, 1, 23, 50));
                add(new TimeStamp(2018, 5, 26, 12, 0));
                add(new TimeStamp(2018, 5, 28, 0, 2));
                add(new TimeStamp(2018, 5, 28, 0, 0));

                add(new TimeStamp(2020, 7, 30, 5, 43));
                add(new TimeStamp(2020, 5, 20, 23, 59));
                add(new TimeStamp(2020, 5, 21, 16, 6));
                add(new TimeStamp(2020, 8, 1, 23, 59));
            }
        };


        //父待办事项
        ArrayList<Item> items = new ArrayList<Item>() {
            {
                add(new AppointmentItem(startTimes.get(0), endTimes.get(0), "", "", ""));

                add(new AppointmentItem(startTimes.get(4), endTimes.get(4), "", "", ""));
            }
        };

        int fatherID1 = manager.addItem(items.get(0), true);
        manager.addItem(items.get(1), true);

        ArrayList<Item> children1 = new ArrayList<Item>() {
            {
                //添加为约会的子事项，
                add(new OtherItem(startTimes.get(1), endTimes.get(1), ""));//成功
                add(new AppointmentItem(startTimes.get(1), endTimes.get(1), "", "", ""));//成功
                add(new InterviewItem(startTimes.get(1), endTimes.get(1), "", "", "", ""));//互斥，失败
                add(new OtherItem(startTimes.get(2), endTimes.get(2), ""));//不在时间段内，失败
                add(new AppointmentItem(startTimes.get(3), endTimes.get(3), "", "", ""));//时间重叠，失败
            }
        };
        for (int i = 0; i < children1.size(); i++) {
            children1.get(i).setFatherID(fatherID1);
            children1.get(i).setIsFather(false);
        }

        boolean add = false;
        try {
            manager.addChildItem(children1.get(0));
            add = true;
        } catch (DataErrorException e) {

        }
        assertTrue(add);
        add = false;
        try {
            manager.addChildItem(children1.get(1));
            add = true;
        } catch (DataErrorException e) {

        }
        assertTrue(add);
        add = false;
        try {
            manager.addChildItem(children1.get(2));
            add = true;
        } catch (DataErrorException e) {

        }
        assertFalse(add);
        add = false;
        try {
            manager.addChildItem(children1.get(3));
            add = true;
        } catch (DataErrorException e) {

        }
        assertFalse(add);
        add = false;
        try {
            manager.addChildItem(children1.get(4));
            add = true;
        } catch (Exception e) {

        }
        assertFalse(add);
        ArrayList<Item> children2 = new ArrayList<Item>() {
            {
                add(new AnniversaryItem(startTimes.get(5), endTimes.get(5), "", "", ""));//成功，纪念日允许重叠
                add(new OtherItem(startTimes.get(5), endTimes.get(5), ""));//成功，自定义事项允许重叠
                add(new InterviewItem(startTimes.get(5), endTimes.get(5), "", "", "", ""));//重叠，失败
                add(new OtherItem(startTimes.get(6), endTimes.get(6), ""));//重叠，失败
                add(new AppointmentItem(startTimes.get(7), endTimes.get(7), "", "", ""));//重叠，失败
            }
        };
        add = false;
        try {
            manager.addItem(children2.get(0), true);
            add = true;
        } catch (Exception e) {

        }
        assertTrue(add);
        add = false;
        try {
            manager.addItem(children2.get(1), true);
            add = true;
        } catch (Exception e) {

        }
        assertTrue(add);
        add = false;
        try {
            manager.addItem(children2.get(2), true);
            add = true;
        } catch (Exception e) {

        }
        assertFalse(add);
        add = false;
        try {
            manager.addItem(children2.get(3), true);
            add = true;
        } catch (Exception e) {

        }
        assertTrue(add);
        add = false;
        try {
            manager.addItem(children2.get(4), true);
            add = true;
        } catch (Exception e) {

        }
        assertFalse(add);


    }

    @Test
    public void deleteItem() throws Exception {
        mysql.clear();
        ArrayList<Item> fathers = new ArrayList<Item>() {
            {
                add(new OtherItem(new TimeStamp(2018, 5, 20, 5, 42),
                        new TimeStamp(2021, 7, 30, 5, 43), ""));

                add(new OtherItem(new TimeStamp(2018, 5, 20, 5, 42),
                        new TimeStamp(2022, 7, 30, 5, 43), ""));
            }
        };
        assertTrue(manager.addItem(fathers.get(0), true) != 0);
        assertTrue(manager.deleteItem(fathers.get(0)));

        int id = manager.addItem(fathers.get(1), true);
        assertTrue(id != 0);
        ArrayList<Item> children = new ArrayList<Item>() {
            {
                add(new OtherItem(new TimeStamp(2018, 5, 20, 5, 42),
                        new TimeStamp(2021, 7, 30, 5, 43), ""));
                add(new OtherItem(new TimeStamp(2018, 5, 20, 5, 42),
                        new TimeStamp(2021, 7, 30, 5, 43), ""));
            }
        };
        for (Item aChildren : children) {
            aChildren.setFatherID(id);
            aChildren.setIsFather(false);
            boolean add = false;
            try {
                manager.addChildItem(aChildren);
                add = true;
            } catch (Exception e) {

            }
            assertTrue(add);
        }
        assertTrue(manager.deleteItem(fathers.get(1)));

    }

    @Test
    public void promptTest() throws DataErrorException {
        ItemManager.destroy();
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < Item.ItemType.values().length; i++) {
            list.add(ItemFactory.getDefaultItemByType(Item.ItemType.values()[i], new HashMap<String , String>()));
        }
        long currentMilis = System.currentTimeMillis();
        TimeStamp ts = TimeStampFactory.createStampByMiliseconds(currentMilis);
        ts = TimeStampFactory.createOneHourLater(ts); // 一个小时后的事件，每个事件持续一个小时， 相隔两个小时。
        for (int i = 0 ; i < list.size() ; i++) {
            Item item = list.get(i);
            item.setFrom(ts);
            item.setTo(TimeStampFactory.createOneHourLater(ts));
            if (item.getItemType() == Item.ItemType.ANNIVERSARY) {
                ((AnniversaryItem)item).setStartDay(ts);
                item.setTo(ts);
            }
            ts = TimeStampFactory.createHoursLater(ts , 2);
            item.setPromptStatus(true);
            item.setMinutesAhead(MinutesAheadType.ONEWEEK.getMinutes());
            item.setMinutesDelta(MinutesDeltaType.FIVEMINUTES.getMinutes());
            System.out.println(ItemManager.getInstance().addItem(item , true));
        }
        List<Item> actualList = ItemManager.getInstance().getPrompts(currentMilis);
        assertEquals(list.size(), actualList.size());
        boolean flag = false;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0 ; j < actualList.size(); j++) {
                   if(list.get(i).equals(actualList.get(j))) {
                       flag = true;
                       break;
                   }
            }
            System.out.println("index == " + i);
            assertTrue(flag);
            flag = false;
        }
    }
}
package todoitem;

import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.io.Serializable;
import java.util.*;

public abstract class Item implements Serializable, ItemInterface, AttributeMap, AttributeFunc {
    private HashMap<String, String> attrsMap;

    public Item(HashMap<String, String> attrsMap) {
        this.attrsMap = new HashMap<>();
        addAttrs(attrsMap);
    }

    public Item(TimeStamp from, TimeStamp to, String detailText, ItemType itemType,
                int priority, int status, boolean isFather) throws Exception {
        this(from, to, detailText, itemType, priority, status, isFather,
                Const.PROMPT_STATUS, Const.MINUTES_AHEAD, Const.SHOW_ON_STAGE, Const.MINUTES_DELTA);
    }


    public Item(TimeStamp from, TimeStamp to, String detailText, ItemType itemType,
                int priority, int status, boolean isFather,
                boolean promptStatus, long ahead, boolean showOnStage, long delta) throws Exception {
        if ((to == null || from == null) && (itemType != ItemType.CUSTOM)) {
            throw new Exception("to or from TimeStamp is null! ");
        }
        if ((to != null && from != null) && ((!from.isValid()) || (!to.isValid()) || !from.isBefore(to))) { // from  strictly after to
            throw new Exception("Time is invalid");
        }
        attrsMap = new HashMap<>();
        setFrom(from);                      //开始时间
        setTo(to);                          //结束时间
        setDetailText(detailText);          //具体描述
        setItemType(itemType);              //待办事项类型
        setPriority(priority);              //优先级
        setStatus(status);                  //完成进度
        setIsFather(isFather);              //是否是父待办事项
        setFatherID(Const.FATHER_ID);       // 默认此为父待办事项，因此不存在父待办事项，即父待办事项ID为0
        setID(Const.ID);
        /*
         *提醒
         **/
        setPromptStatus(promptStatus);      //是否进行提醒，默认不提醒
        setMinutesAhead(ahead);             //提前多久进行提醒，默认提前一小时
        setShowOnStage(showOnStage);        //是否在主界面区域显示，默认显示
        setMinutesDelta(delta);             //多久提醒一次，默认5分钟
    }


    @Override
    public boolean isDuringTime(TimeStamp from, TimeStamp to) {
        TimeStamp thisFrom = getFrom();
        TimeStamp thisTo = getTo();
        // 用to.isAfter(thisFrom) 和 from.isBefore(thisTo);比较好。因为from 和to 不会为null
        return thisFrom != null && thisTo != null && thisFrom.isBefore(to) && thisTo.isAfter(from);
    }

    @Override
    public TimeStamp getFrom() {
        return TimeStampFactory.createStampByString(getValue("startTime"));
    }

    @Override
    public void setFrom(TimeStamp from) {
        addAttr("startTime", from == null ? "" : from.toString());
    }

    @Override
    public TimeStamp getTo() {
        return TimeStampFactory.createStampByString(getValue("endTime"));
    }

    @Override
    public void setTo(TimeStamp to) {
        addAttr("endTime", to == null ? "" : to.toString());
    }

    @Override
    public String getDetailText() {
        return getValue("content");
    }

    @Override
    public void setDetailText(String detailText) {
        addAttr("content", detailText == null ? "" : detailText);
    }

    @Override
    public ItemType getItemType() {
        return ItemType.parseItemType(getValue("type"));
    }

    @Override
    public void setItemType(ItemType itemType) {
        addAttr("type", itemType == null ? "" : itemType.getTypeStr());
    }

    @Override
    public int getPriority() {
        return Integer.parseInt(getValue("priority"));
    }

    @Override
    public void setPriority(int priority) {
        addAttr("priority", priority + "");
    }

    @Override
    public int getStatus() {
        // 状态，进行中，完成
        return Integer.parseInt(getValue("status"));
    }

    @Override
    public void setStatus(int status) {
        addAttr("status", status + "");
    }

    @Override
    public int getID() {
        return getValue("scheduleID").equals("") ?
                Integer.parseInt(getValue("ID")) : Integer.parseInt(getValue("scheduleID"));
    }

    @Override
    public void setID(int ID) {
        addAttr("scheduleID", ID + "");
    }

    @Override
    public int getFatherID() {
        return Integer.parseInt(getValue("fatherID").equals("") ? "-1" : getValue("fatherID"));
    }

    @Override
    public void setFatherID(int fatherID) {
        addAttr("fatherID", fatherID + "");
    }

    @Override
    public long minutesAhead() {
        // 提前多少开始提醒
        return Long.parseLong(getValue("minutesAhead"));
    }

    @Override
    public void setMinutesAhead(long minutesAhead) {
        addAttr("minutesAhead", minutesAhead + "");
    }

    @Override
    public long minutesDelta() {
        //时间间隔
        return Long.parseLong(getValue("minuteDelta"));
    }

    @Override
    public void setMinutesDelta(long minutesDelta) {
        addAttr("minuteDelta", minutesDelta + "");
    }

    @Override
    public boolean isFather() {
        return getValue("isFather").equals("1");
    }

    @Override
    public void setIsFather(boolean isFather) {
        addAttr("isFather", (isFather ? 1 : 0) + "");
    }

    @Override
    public boolean promptStatus() {
        // 是否提醒
        return getValue("promptStatus").equals("1");
    }

    @Override
    public void setPromptStatus(boolean promptStatus) {
        addAttr("promptStatus", "" + (promptStatus ? 1 : 0));
    }

    @Override
    public boolean showOnStage() {
        // 是否在页面上显示
        return getValue("showOnStage").equals("1");
    }

    @Override
    public void setShowOnStage(boolean showOnStage) {
        addAttr("showOnStage", (showOnStage ? 1 : 0) + "");
    }

    @Override
    public void removeAttr(String key) {
        if (attrsMap.containsKey(key))
            attrsMap.remove(key);
    }

    @Override
    public void addAttrs(HashMap<String, String> attrs) {
        attrsMap.putAll(attrs);
    }

    @Override
    public void addAttr(String key, String value) {
        if (attrsMap.containsKey(key))
            attrsMap.replace(key, value);
        else
            attrsMap.put(key, value);
    }

    @Override
    public HashMap<String, String> getAttrs() {
        return attrsMap;
    }


    @Override
    public String getValue(String key) {
        if (attrsMap.containsKey(key))
            return attrsMap.get(key);
        return "";
    }


    public enum ItemType {
        CUSTOM("CUSTOM", "自定义"), MEETING("MEETING", "会议"), DATE("DATE", "约会"), ANNIVERSARY("ANNIVERSARY", "纪念日"),
        COURSE("COURSE", "课程"), TRAVEL("TRAVEL", "旅行"), INTERVIEW("INTERVIEW", "面试");
        private String typeStr;
        private String cnTypeStr;

        ItemType(String typeStr, String cnTypeStr) {
            this.typeStr = typeStr;
            this.cnTypeStr = cnTypeStr;
        }

        public String getTypeStr() {
            return typeStr;
        }

        public String getCnTypeStr() {
            return cnTypeStr;
        }

        public static ItemType parseItemType(String typeStr) {
            switch (typeStr) {
                case "MEETING":
                    return MEETING;
                case "DATE":
                    return DATE;
                case "CUSTOM":
                    return CUSTOM;
                case "ANNIVERSARY":
                    return ANNIVERSARY;
                case "COURSE":
                    return COURSE;
                case "TRAVEL":
                    return TRAVEL;
                case "INTERVIEW":
                    return INTERVIEW;
            }
            return null;
        }
    }

    @Override
    public boolean equals(Object object) {
        Item item = (Item) object;
        for (Iterator<Map.Entry<String , String>> it = attrsMap.entrySet().iterator(); it.hasNext() ; ) {
            Map.Entry<String ,String> entry = it.next();
            if (entry.getKey().equals("year")) {
                continue;
            }
            if (entry.getKey().equals("isFather")) {
                continue;
            }
            if (entry.getKey().equals("fatherID")) {
                continue;
            }
            if (entry.getKey().equals("scheduleID")) {
                continue;
            }
            if(!attrsMap.get(entry.getKey()).equals(item.attrsMap.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
//        return ((getValue("startTime")).equals(item.getValue("startTime")))
//                && ((getValue("endTime")).equals(item.getValue("endTime")))
//                && (getValue("content").equals(item.getDetailText()))
//                && ((getValue("type")).equals(item.getItemType().getTypeStr()));
    }

    @Override
    public String getSummary() {
        String summary = "Type: " + getItemType().getTypeStr() + "\n";
        summary += "From: " + getFrom().toString() + " ~ To: " + getFrom().toString() + "\n";
        summary += "Content: " + getDetailText() + "\n";
        return summary;
    }

    @Override
    public TreeMap<String, String> getDetailAttrs() {
        TreeMap<String, String> detailMap = new TreeMap<>();
        detailMap.put("类型", this.getItemType().getTypeStr());
        detailMap.put("状态", Const.STATUS_STRING[this.getStatus()]);
        detailMap.put("时间段", this.getFrom() + " ~ " + this.getTo());
        detailMap.put("类型", this.getItemType().getTypeStr());
        detailMap.put("类型", this.getItemType().getTypeStr());
        detailMap.put("类型", this.getItemType().getTypeStr());
        detailMap.put("类型", this.getItemType().getTypeStr());
        return detailMap;
    }

    @Override
    public String toString() {
        return "item";
    }
}

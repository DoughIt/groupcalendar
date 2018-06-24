package todoitem;

import todoitem.itemSub.*;

import java.util.HashMap;

public class ItemFactory {
    public static Item createItemByItemType(Item.ItemType type, HashMap<String, String> attrs) {
        if (type != null)
            switch (type) {
                case DATE:
                    return new AppointmentItem(attrs);
                case MEETING:
                    return new MeetingItem(attrs);
                case CUSTOM:
                    return new OtherItem(attrs);
                case ANNIVERSARY:
                    return new AnniversaryItem(attrs);
                case TRAVEL:
                    return new TravelItem(attrs);
                case COURSE:
                    return new CourseItem(attrs);
                case INTERVIEW:
                    return new InterviewItem(attrs);
                default:
                    return new OtherItem(attrs);
            }
        else return null;
    }

    /**
     * attrs should contain:
     * priority ,
     *
     * */
    public static Item getDefaultItemByType(Item.ItemType type, HashMap<String, String> attrs) {
        attrs.putIfAbsent("promptStatus" , "0");
        attrs.putIfAbsent("type",type.getTypeStr());
        attrs.putIfAbsent("priority" , "3");
        attrs.putIfAbsent("minutesAhead" , "60");
        attrs.putIfAbsent("isFather" , "1");
        attrs.putIfAbsent("minuteDelta" , "5");
        attrs.putIfAbsent("showOnStage" , "1");
        attrs.putIfAbsent("fatherID" , "-1");
        attrs.putIfAbsent("startTime" , "1800-1-1 0:0");
        attrs.putIfAbsent("endTime" , "1800-1-1 0:59");
        attrs.putIfAbsent("scheduleID" , "-1");
        attrs.putIfAbsent("status" , "0");
        if (type != null)
        {
            switch (type) {
                case DATE:
                    attrs.putIfAbsent("place", "default place");
                    attrs.putIfAbsent("people", "default people");
                    attrs.putIfAbsent("content", "default content");
                    return new AppointmentItem(attrs);
                case MEETING:
                    attrs.putIfAbsent("content" , "default content");
                    attrs.putIfAbsent("topic" , "default topic");
                    attrs.putIfAbsent("place" , "default place");
                    return new MeetingItem(attrs);
                case CUSTOM:
                    attrs.putIfAbsent("content" , "default content");
                    return new OtherItem(attrs);
                case ANNIVERSARY:
                    attrs.putIfAbsent("startDay" , attrs.get("startTime"));
                    attrs.putIfAbsent("content" , "default content");
                    attrs.putIfAbsent("name" , "default name");
                    attrs.putIfAbsent("anniversaryType" , "default anniversaryType");
                    return new AnniversaryItem(attrs);
                case TRAVEL:
                    attrs.putIfAbsent("remark" , "default remark");
                    attrs.putIfAbsent("way" , "plane");
                    //no content?
                    attrs.putIfAbsent("number" , "0");
                    attrs.putIfAbsent("place" , "default place");
                    return new TravelItem(attrs);
                case COURSE:
                    attrs.putIfAbsent("startDay" , attrs.get("startTime"));
                    attrs.putIfAbsent("remark" , "default remark");
                    attrs.putIfAbsent("content" , "default content");
                    attrs.putIfAbsent("duration" , "8");
                    attrs.putIfAbsent("teacher" , "default teacher");
                    attrs.putIfAbsent("name" , "default name");
                    attrs.putIfAbsent("place" , "default place");
                    attrs.putIfAbsent("day" , "0");
                    return new CourseItem(attrs);
                case INTERVIEW:
                    attrs.putIfAbsent("remark" , "default remark");
//                    attrs.putIfAbsent("content" , "default content");
                    attrs.putIfAbsent("company" , "default company");
                    attrs.putIfAbsent("place" , "default place");
                    attrs.putIfAbsent("job" , "default job");
                    return new InterviewItem(attrs);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

}

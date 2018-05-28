package todoitem.itemSub;

import kernel.CalendarDate;
import todoitem.Const;
import todoitem.Item;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.util.HashMap;

public class CourseItem extends Item {
    public CourseItem(HashMap<String, String> attrsMap) {
        super(attrsMap);
    }

    public CourseItem(TimeStamp from, TimeStamp to, String name, String detailText, String teacher,
                      String remark, String place, String duration, String day) throws Exception {
        this(from, to, name, detailText, teacher, remark, place, duration, day, Const.PRIORITY, Const.STATUS, Const.IS_FATHER);
    }

    public CourseItem(TimeStamp from, TimeStamp to, String name, String detailText, String teacher,
                      String remark, String place, String duration, String day, int priority) throws Exception {
        this(from, to, name, detailText, teacher, remark, place, duration, day, priority, Const.STATUS, Const.IS_FATHER);
    }

    public CourseItem(TimeStamp from, TimeStamp to, String name, String detailText, String teacher,
                      String remark, String place, String duration, String day, int priority, int status, boolean isFather) throws Exception {
        this(from, to, name, detailText, teacher, remark, place, duration, day, priority, status, isFather,
                Const.PROMPT_STATUS, Const.MINUTES_AHEAD, Const.SHOW_ON_STAGE, Const.MINUTES_DELTA);
    }

    public CourseItem(TimeStamp from, TimeStamp to, String name, String detailText, String teacher,
                      String remark, String place, String duration, String day, int priority, int status, boolean isFather,
                      boolean promptStatus, long ahead, boolean showOnStage, long delta) throws Exception {
        super(from, to, detailText, ItemType.COURSE, priority, status, isFather, promptStatus, ahead, showOnStage, delta);
        setName(name);
        setStartDay(from);
        setDuration(duration);
        setTeacher(teacher);
        setRemark(remark);
        setPlace(place);
        setDay(day);
    }

    public String getName() {
        return getValue("name");
    }

    private void setName(String name) {
        addAttr("name", name);
    }

    public TimeStamp getStartDay() {
        return TimeStampFactory.createStampByString(getValue("startDay"));
    }

    private void setStartDay(TimeStamp day) {
        addAttr("startDay", day.toString());
    }

    public int getDuration() {
        return Integer.parseInt(getValue("duration"));
    }

    private void setDuration(String duration) {
        addAttr("duration", duration == null || duration.trim().equals("") ? 1 + "" : duration);
    }

    public String getTeacher() {
        return getValue("teacher");
    }

    private void setTeacher(String teacher) {
        addAttr("teacher", teacher == null ? "" : teacher);
    }

    public String getRemark() {
        return getValue("remark");
    }

    private void setRemark(String remark) {
        addAttr("remark", remark == null ? "" : remark);
    }

    public String getPlace() {
        return getValue("place");
    }

    public void setPlace(String place) {
        addAttr("place", place == null ? "" : place);
    }

    public int getDay() {
        return Integer.parseInt(getValue("day"));
    }

    private void setDay(String day) {
        addAttr("day", day == null || day.trim().equals("") ?
                new CalendarDate(getFrom().getYear(), getFrom().getMonth(), getFrom().getDay()).getDayOfWeek() + "" : day)
        ;
    }
}

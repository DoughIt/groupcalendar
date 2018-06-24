package todoitem.itemSub;

import todoitem.Const;
import todoitem.Item;
import todoitem.util.TimeStamp;

import java.util.HashMap;

public class TravelItem extends Item {
    public TravelItem(HashMap<String, String> attrsMap) {
        super(attrsMap);
    }

    public TravelItem(TimeStamp from, TimeStamp to, String way, String place, String number, String remark) throws Exception {
        this(from, to, way, place, number, remark, Const.PRIORITY, Const.STATUS, Const.IS_FATHER);
    }

    public TravelItem(TimeStamp from, TimeStamp to, String way, String place, String number, String remark, int priority) throws Exception {
        this(from, to, way, place, number, remark, priority, Const.STATUS, Const.IS_FATHER);
    }

    public TravelItem(TimeStamp from, TimeStamp to, String way, String place, String number, String remark, int priority,
                      int status, boolean isFather) throws Exception {
        this(from, to, way, place, number, remark, priority, status, isFather,
                Const.PROMPT_STATUS, Const.MINUTES_AHEAD, Const.SHOW_ON_STAGE, Const.MINUTES_DELTA);
    }

    public TravelItem(TimeStamp from, TimeStamp to, String way, String place, String number, String remark, int priority,
                      int status, boolean isFather, boolean promptStatus, long ahead, boolean showOnStage, long delta) throws Exception {
        super(from, to, "", ItemType.TRAVEL, priority, status, isFather, promptStatus, ahead, showOnStage, delta);
        setWay(way);
        setPlace(place);
        setNumber(number);
        setRemark(remark);
    }

    public String getWay() {
        return getValue("way");
    }

    public void setWay(String way) {
        addAttr("way", way == null ? "" : way);
    }

    public String getPlace() {
        return getValue("place");
    }

    public void setPlace(String place) {
        addAttr("place", place == null ? "" : place);
    }

    public String getNumber() {
        return getValue("number");
    }

    public void setNumber(String number) {
        addAttr("number", number == null ? "" : number);
    }

    public String getRemark() {
        return getValue("remark");
    }

    public void setRemark(String remark) {
        addAttr("remark", remark == null ? "" : remark);
    }

    public String getPlaceDescription() {
        return "Attention: This travel destination is " + getValue("place");
    }

    public String getTransDescription() {
        return "You will go by " + getValue("way") + "(" + getValue("number") + ")" + " at "
                + getFrom().getStringWithoutHour();
    }

    public String getTimeDescription() {
        return "You will have a happy time from " + getFrom().getStringWithoutHour() + " to " + getTo().getStringWithoutHour();
    }
}

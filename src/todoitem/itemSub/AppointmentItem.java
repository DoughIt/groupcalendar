package todoitem.itemSub;

import todoitem.Const;
import todoitem.Item;
import todoitem.util.TimeStamp;

import java.util.HashMap;

/**
 * Created by Bing Chen on 2018/5/9.
 */
public class AppointmentItem extends Item {
    public AppointmentItem(HashMap<String, String> attrs) {
        super(attrs);
    }

    public AppointmentItem(TimeStamp from, TimeStamp to, String detailText, String participants, String location) throws Exception {
        this(from, to, detailText, participants, location, Const.PRIORITY, Const.STATUS, Const.IS_FATHER);
    }

    public AppointmentItem(TimeStamp from, TimeStamp to, String detailText, String participants, String location
            , int priority) throws Exception {
        this(from, to, detailText, participants, location, priority, Const.STATUS, Const.IS_FATHER);
    }

    public AppointmentItem(TimeStamp from, TimeStamp to, String detailText, String participants, String location
            , int priority, int status, boolean isFather) throws Exception {
        this(from, to, detailText, participants, location, priority, status, isFather,
                Const.PROMPT_STATUS, Const.MINUTES_AHEAD, Const.SHOW_ON_STAGE, Const.MINUTES_DELTA);
    }

    public AppointmentItem(TimeStamp from, TimeStamp to, String detailText, String participants, String location,
                           int priority, int status, boolean isFather, boolean promptStatus,
                           long ahead, boolean showOnStage, long delta) throws Exception {
        super(from, to, detailText, ItemType.DATE, priority, status, isFather, promptStatus, ahead, showOnStage, delta);
        setLocation(location);
        setParticipants(participants);
    }

    public String getParticipants() {
        return getValue("people");
    }

    public void setParticipants(String participants) {
        addAttr("people", participants == null ? "" : participants);
    }

    public String getLocation() {
        return getValue("place");
    }

    public void setLocation(String location) {
        addAttr("place", location == null ? "" : location);
    }

    public String getDetailDescription() {
        return "Description: You will date your dear " + getParticipants()
                + " in " + getLocation();
    }

    public String getTimeDescription() {
        return "Time: Your happy time will be from " + getValue("startTime")
                + " to " + getValue("endTime");
    }
//    public void setLocation(String location) {
//        this.location = location;
//    }
}

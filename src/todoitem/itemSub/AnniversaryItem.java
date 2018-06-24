package todoitem.itemSub;

import todoitem.Const;
import todoitem.Item;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.util.HashMap;

public class AnniversaryItem extends Item {
    public AnniversaryItem(HashMap<String, String> attrsMap) {
        super(attrsMap);
    }

    public AnniversaryItem(TimeStamp from, TimeStamp to, String name, String detailText, String anniversaryType) throws Exception {
        this(from, to, name, detailText, anniversaryType, Const.PRIORITY, Const.STATUS, Const.IS_FATHER);

    }

    public AnniversaryItem(TimeStamp from, TimeStamp to, String name, String detailText, String anniversaryType,
                           int priority) throws Exception {
        this(from, to, name, detailText, anniversaryType, priority, Const.STATUS, Const.IS_FATHER);
    }

    public AnniversaryItem(TimeStamp from, TimeStamp to, String name, String detailText, String anniversaryType,
                           int priority, int status, boolean isFather) throws Exception {
        this(from, to, name, detailText, anniversaryType, priority, status, isFather,
                Const.PROMPT_STATUS, Const.MINUTES_AHEAD, Const.SHOW_ON_STAGE, Const.MINUTES_DELTA);
    }

    public AnniversaryItem(TimeStamp from, TimeStamp to, String name, String detailText, String anniversaryType, int priority, int status, boolean isFather,
                           boolean promptStatus, long ahead, boolean showOnStage, long delta) throws Exception {
        super(from, to, detailText, ItemType.ANNIVERSARY, priority, status, isFather, promptStatus, ahead, showOnStage, delta);
        setName(name);
        setAnniversaryType(anniversaryType);
        setStartDay(from);
    }

    public String getAnniversaryType() {
        return getValue("anniversaryType");
    }

    public void setAnniversaryType(String anniversaryType) {
        addAttr("anniversaryType", anniversaryType == null ? "" : anniversaryType);
    }

    public TimeStamp getStartDay() {
        return TimeStampFactory.createStampByString(getValue("startDay"));
    }

    public void setStartDay(TimeStamp day) {
        if (day != null)
            addAttr("startDay", day.toString());
    }

    public void setName(String name) {
        addAttr("name", name != null ? name : "");
    }

    public String getName() {
        return getValue("name");
    }

    public int getYear() {
        return Integer.parseInt(getValue("year"));
    }

    public void setYear(int year) {
        addAttr("year", year + "");
    }

    public String getDetailDescription() {
        return "Pay Attention: You have a " + getAnniversaryType() + " type of anniversary at "
                + getFrom().getMonth() + "-" + getFrom().getDay();
    }

    public String getStartDayDescription() {
        return "Tip: The anniversary begin at " + getFrom().getStringWithoutHour();
    }
}

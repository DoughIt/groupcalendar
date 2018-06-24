package todoitem.itemSub;

import todoitem.Const;
import todoitem.Item;
import todoitem.util.TimeStamp;

import java.util.HashMap;

/**
 * Created by Bing Chen on 2018/5/9.
 */
public class OtherItem extends Item {
    public OtherItem(HashMap<String, String> attrs) {
        super(attrs);
    }

    public OtherItem(TimeStamp from, TimeStamp to, String detailText) throws Exception {
        this(from, to, detailText, Const.PRIORITY, Const.STATUS, Const.IS_FATHER);
    }

    public OtherItem(TimeStamp from, TimeStamp to, String detailText,
                     int priority) throws Exception {
        this(from, to, detailText, priority, Const.STATUS, Const.IS_FATHER);
    }

    public OtherItem(TimeStamp from, TimeStamp to, String detailText,
                     int priority, int status, boolean isFather) throws Exception {
        this(from, to, detailText, priority, status, isFather,
                Const.PROMPT_STATUS, Const.MINUTES_AHEAD, Const.SHOW_ON_STAGE, Const.MINUTES_DELTA);
    }

    public OtherItem(TimeStamp from, TimeStamp to, String detailText,
                     int priority, int status, boolean isFather,
                     boolean promptStatus, long ahead, boolean showOnStage, long delta) throws Exception {
        super(from, to, detailText, ItemType.CUSTOM, priority, status, isFather, promptStatus, ahead, showOnStage, delta);
    }
}

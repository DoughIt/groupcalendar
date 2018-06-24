package ui.view;

import javafx.scene.control.Label;

/**
 * 2018-05-08
 *
 * @author Jian Zhang
 */

/*假日、休息日*/
public class DayOff extends Special {
    private String content;

    public DayOff(DayItem item, String content) {
        super(item);
        this.content = content;
        init();
    }

    @Override
    protected void init() {
        setStyleClass();
        paint();
    }

    @Override
    protected void setStyleClass() {
        addStyleClass("day_off");
    }

    @Override
    protected void paint() {
        Label offLabel = new Label(content);
        offLabel.getStyleClass().add("day_off_label");
        //TODO label属性
        setRightNode(offLabel);
    }

}

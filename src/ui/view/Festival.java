package ui.view;


import javafx.scene.control.Label;

/**
 * 2018-05-08
 *
 * @author Jian Zhang
 */

/*节日*/
public class Festival extends Special {
    private String holiday;

    public Festival(DayItem item, String holiday) {
        super(item);
        this.holiday = holiday;
        init();
    }

    @Override
    protected void init() {
        setStyleClass();
        paint();
    }

    @Override
    protected void setStyleClass() {
        addStyleClass("festival");
    }

    @Override
    protected void paint() {
        Label holidayLabel = new Label(holiday);
        holidayLabel.getStyleClass().add("festival_label");
        //TODO label属性
        setBottomNode(holidayLabel);

    }
}

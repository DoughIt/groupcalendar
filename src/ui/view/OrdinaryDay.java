package ui.view;

import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.layout.HBox;
import kernel.CalendarDate;
import ui.Config;

/**
 * 2018-05-08
 *
 * @author Jian Zhang
 */

/*寻常的一天，即无待办事项、非节日、非假日*/
public class OrdinaryDay extends DayItem {
    private volatile Label dayLabel;

    public OrdinaryDay(CalendarDate d) {
        setDate(d);
        init();
    }

    @Override
    public DayItem getItem() {
        return this;
    }

    @Override
    protected void init() {
        this.dayLabel = new Label();
        setStyleClass();
        paint();
    }

    @Override
    protected void setStyleClass() {
        addStyleClass("basic_day");
    }

    @Override
    protected void paint() {
        this.dayLabel.setText(getDate().getDay() + "");
        this.dayLabel.getStyleClass().add("basic_label");
        this.dayLabel.setEffect(new Glow(.6));
        this.setPrefSize(Config.getRectangleWidth(), Config.getRectangleHeight());

        setTopNode(new HBox());
        setBottomNode(new Label());
        setLeftNode(new Label());
        setRightNode(new Label());
        setCenterNode(dayLabel);
    }
}

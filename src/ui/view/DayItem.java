package ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import kernel.CalendarDate;
import ui.Config;


/**
 * 2018-05-08
 *
 * @author Jian Zhang
 */
public abstract class DayItem extends BorderPane {
    private volatile CalendarDate date;
    public void setDate(CalendarDate d) {
        this.date = d;
    }

    public CalendarDate getDate() {
        return this.date;
    }

    void addStyleClass(String... classNames) {
        for (String name : classNames) {
            if (!this.getStyleClass().contains(name))
                this.getStyleClass().add(name);
        }
    }

    public void removeStyleClass(String... classNames) {
        for (String name : classNames) {
            while (this.getStyleClass().contains(name))
                this.getStyleClass().remove(name);
        }
    }

    //    public abstract void setTopNode(HBox hBox);
//
//    public abstract void setBottomNode(Label label);
//
//    public abstract void setLeftNode(Label label);
//
//    public abstract void setRightNode(Label label);
//
//    public abstract void setCenterNode(Label label);
//    @Override
    public void setTopNode(HBox hBox) {
        hBox.setPrefSize(Config.getRectangleWidth(), 18);
        this.setTop(hBox);
    }

    //    @Override
    public void setBottomNode(Label label) {
        label.setPrefSize(Config.getRectangleWidth(), Config.getRectangleHeight() / 4);
        label.setAlignment(Pos.CENTER);
        this.setBottom(label);
    }

    //    @Override
    public void setRightNode(Label label) {
        label.setPrefSize(15, 15);
        label.setAlignment(Pos.CENTER);
        setMargin(label, new Insets(-10, 0, 0, 0));
        this.setRight(label);
    }

    //    @Override
    public void setLeftNode(Label label) {
        label.setPrefSize(10, 40);
        this.setLeft(label);
    }

    //    @Override
    public void setCenterNode(Label label) {
        label.setPrefSize(40, 40);
        label.setAlignment(Pos.CENTER);
        this.setCenter(label);
    }

    public abstract DayItem getItem();

    protected abstract void init();

    protected abstract void setStyleClass();

    protected abstract void paint();
}

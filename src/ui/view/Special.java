package ui.view;


import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * 2018-05-08
 *
 * @author Jian Zhang
 */


abstract class Special extends DayItem {
    private DayItem dayItem;

    Special(DayItem item) {
        this.dayItem = item;
    }

    @Override
    public DayItem getItem() {
        return dayItem.getItem();
    }

    @Override
    void addStyleClass(String... classNames) {
        dayItem.addStyleClass(classNames);
    }

    @Override
    public void setTopNode(HBox hBox) {
        dayItem.setTopNode(hBox);
    }

    @Override
    public void setBottomNode(Label label) {
        dayItem.setBottomNode(label);
    }

    @Override
    public void setRightNode(Label label) {
        dayItem.setRightNode(label);
    }

    @Override
    public void setLeftNode(Label label) {
        dayItem.setLeftNode(label);
    }

    @Override
    public void setCenterNode(Label label) {
        dayItem.setCenterNode(label);
    }
}

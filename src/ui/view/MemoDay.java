package ui.view;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import todoitem.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 2018-05-08
 *
 * @author Jian Zhang
 */

public class MemoDay extends Special {
    private ArrayList<Item> items;
    private Set<Item.ItemType> types;

    public MemoDay(DayItem item, ArrayList<Item> items) {
        super(item);
        this.items = items;
        init();
    }

    private void setTypes() {
        for (Item item : items) {
            types.add(item.getItemType());
        }
    }

    @Override
    protected void init() {
        types = new HashSet<>();
        setTypes();
        setStyleClass();
        paint();
    }

    @Override
    protected void setStyleClass() {
        for (Item.ItemType type : types) {
            addStyleClass(type.getTypeStr());
        }
    }

    @Override
    protected void paint() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        for (Item.ItemType type : types) {
            Circle circle = new Circle();
            circle.setRadius(4);
            circle.getStyleClass().add("type_" + type.getTypeStr());
            hBox.getChildren().add(circle);
        }
        setTopNode(hBox);
    }
}

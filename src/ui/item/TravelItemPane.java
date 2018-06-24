package ui.item;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kernel.Display;
import todoitem.Item;
import todoitem.itemSub.TravelItem;
import todoitem.util.TimeStamp;

import java.util.ArrayList;
import java.util.Arrays;

import static todoitem.Const.WAYS;


public class TravelItemPane extends ItemPane {
    private Label numberLabel;
    private Label wayLabel;
    private Label placeLabel;
    private Label remarkLabel;
    private ComboBox<String> wayComboBox;
    private TextField numberText;
    private TextField placeText;
    private TextField remarkText;
    private TravelItem item;

    TravelItemPane(Item item, boolean fromAdd) {
        super(item, fromAdd);
        this.item = (TravelItem) item;
        init();
    }

    private void init() {
        numberLabel = new Label("车次/航班号：");
        wayLabel = new Label("交通方式：");
        placeLabel = new Label("地点：");
        remarkLabel = new Label("备忘：");
        ArrayList<String> wayList = new ArrayList<>();
        wayList.addAll(Arrays.asList(WAYS));
        wayComboBox = new ComboBox<>(FXCollections.observableArrayList(wayList));
        wayComboBox.setValue(item.getWay());
        wayComboBox.setEditable(true);

        numberText = new TextField(item.getNumber());
        placeText = new TextField(item.getPlace());
        remarkText = new TextField(item.getRemark());
        bindEvent();
        paint();
    }

    private void bindEvent() {
        addChildButton.setOnMouseClicked(event -> {
            try {
                TravelItem tempItem = new TravelItem(item.getFrom(), item.getTo(), "", "", "","");
                tempItem.setFatherID(item.getID());
                tempItem.setIsFather(false);
                CommonItemPane.addPane(tempItem);
            } catch (Exception e) {
                System.out.println();
            }
        });
    }

    private void paint() {
        int row = getTailRow();
        this.add(numberLabel, 0, row);
        this.add(numberText, 1, row++);
        this.add(wayLabel, 0, row);
        this.add(wayComboBox, 1, row++);
        this.add(placeLabel, 0, row);
        this.add(placeText, 1, row++);
        this.add(remarkLabel, 0, row);
        this.add(remarkText, 1, row++);

        this.add(addChildButton, 1, row);
        this.add(saveButton, 4, row);
    }


    @Override
    void save() {
        TimeStamp from = new TimeStamp(startDate.getValue().getYear(), startDate.getValue().getMonth().getValue(),
                startDate.getValue().getDayOfMonth(), startHourChoiceBox.getValue(), startMinuteChoiceBox.getValue());
        TimeStamp to = new TimeStamp(endDate.getValue().getYear(), endDate.getValue().getMonth().getValue(),
                endDate.getValue().getDayOfMonth(), endHourChoiceBox.getValue(), endMinuteChoiceBox.getValue());
        item.setFrom(from);
        item.setTo(to);
        item.setItemType(Item.ItemType.parseItemType(typeChoiceBox.getValue()));
        item.setPriority(priority);
        item.setNumber(numberText.getText());
        item.setWay(wayComboBox.getValue());
        item.setPlace(placeText.getText());
        item.setRemark(remarkText.getText());
        saveItem(item);
    }
}

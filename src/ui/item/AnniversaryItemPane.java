package ui.item;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kernel.Display;
import todoitem.Const;
import todoitem.Item;
import todoitem.itemSub.AnniversaryItem;
import todoitem.util.TimeStamp;
import ui.util.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class AnniversaryItemPane extends ItemPane {
    private AnniversaryItem item;
    private Label startLabel;
    private Label nameLabel;
    private Label contentLabel;
    private Label dayLabel;
    private Label typeLabel;
    private ComboBox<String> typeComboBox;
    private TextField nameText;
    private TextField contentText;

    AnniversaryItemPane(Item item, boolean fromAdd) {
        super(item, fromAdd);
        this.item = (AnniversaryItem) item;
        init();
    }


    private void init() {
        startLabel = new Label("开始日期：");
        nameLabel = new Label("名字：");
        contentLabel = new Label("描述：");
        typeLabel = new Label("纪念类型");

        dayLabel = new Label(startDate.getValue().toString());
        nameText = new TextField(item.getName());
        contentText = new TextField(item.getDetailText());

        ArrayList<String> typeList = new ArrayList<>();
        typeList.addAll(Arrays.asList(Const.ANNIVERSARY_TYPES));
        typeComboBox = new ComboBox<>(FXCollections.observableArrayList(typeList));
        typeComboBox.setValue(item.getAnniversaryType());

        startHourChoiceBox.setValue(0);
        startMinuteChoiceBox.setValue(0);
        endHourChoiceBox.setValue(23);
        endMinuteChoiceBox.setValue(59);
        startHourChoiceBox.setDisable(true);
        startMinuteChoiceBox.setDisable(true);
        endHourChoiceBox.setDisable(true);
        endMinuteChoiceBox.setDisable(true);
        endDate.setValue(startDate.getValue());
        bindEvent();
        paint();
    }

    private void updateDay() {
        dayLabel.setText(startDate.getValue().toString());
    }

    private void bindEvent() {
        startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            endDate.setValue(newValue);
            updateDay();
        });
        endDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            startDate.setValue(newValue);
            updateDay();
        });
        addChildButton.setOnMouseClicked(event -> {
            try {
                AnniversaryItem tempItem = new AnniversaryItem(item.getFrom(), item.getTo(), "", "", "");
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
        this.add(startLabel, 0, row);
        this.add(dayLabel, 1, row);
        this.add(typeLabel, 0, ++row);
        this.add(typeComboBox, 1, row++);
        this.add(nameLabel, 0, row);
        this.add(nameText, 1, row++);
        this.add(contentLabel, 0, row);
        this.add(contentText, 1, row++);
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
        item.setName(nameText.getText());
        item.setStartDay(from);
        item.setAnniversaryType(typeComboBox.getValue());
        item.setDetailText(contentText.getText());
        saveItem(item);
    }
}

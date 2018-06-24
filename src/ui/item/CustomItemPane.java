package ui.item;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kernel.Display;
import todoitem.Item;
import todoitem.ItemFactory;
import todoitem.itemSub.OtherItem;
import todoitem.util.TimeStamp;

public class CustomItemPane extends ItemPane {
    private CheckBox hasTimeBox;
    private Label hasTimeLabel;
    private Label contentLabel;
    private TextField contentText;

    private boolean hasTime;
    private OtherItem item;

    public CustomItemPane(Item item, boolean fromAdd) {
        super(item, fromAdd);
        this.item = (OtherItem) item;
        this.hasTime = item.getAttrs().getOrDefault("hasTime", "1").equals("1");
        init();
    }

    private void init() {
        hasTimeBox = new CheckBox();
        hasTimeLabel = new Label("设置时间");
        hasTimeBox.setSelected(hasTime);
        contentLabel = new Label("内容：");
        contentText = new TextField(item.getDetailText());
        bindEvent();
        paint();
    }

    private void bindEvent() {
        hasTimeBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (fromAdd) {
                hasTime = newValue;
                if (!oldValue) {
                    startDate.setDisable(false);
                    endDate.setDisable(false);
                    startHourChoiceBox.setDisable(false);
                    startMinuteChoiceBox.setDisable(false);
                    endHourChoiceBox.setDisable(false);
                    endMinuteChoiceBox.setDisable(false);
                } else {
                    setDisable();
                }
            }//暂时不支持修改时间
        });

        addChildButton.setOnMouseClicked(event -> {
            try {
                OtherItem tempItem = new OtherItem(item.getFrom(), item.getTo(), "");
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
        this.add(hasTimeLabel, 2, 1);
        this.add(hasTimeBox, 4, 1);
        this.add(contentLabel, 0, row);
        this.add(contentText, 1, row++);
        this.add(addChildButton, 1, row);
        this.add(saveButton, 4, row);
    }

    @Override
    void save() {
        if (hasTime) {
            TimeStamp from = new TimeStamp(startDate.getValue().getYear(), startDate.getValue().getMonth().getValue(),
                    startDate.getValue().getDayOfMonth(), startHourChoiceBox.getValue(), startMinuteChoiceBox.getValue());
            TimeStamp to = new TimeStamp(endDate.getValue().getYear(), endDate.getValue().getMonth().getValue(),
                    endDate.getValue().getDayOfMonth(), endHourChoiceBox.getValue(), endMinuteChoiceBox.getValue());
            item.setFrom(from);
            item.setTo(to);
        } else {
            item.setFrom(null);
            item.setTo(null);
        }

        item.setItemType(Item.ItemType.parseItemType(typeChoiceBox.getValue()));
        item.setPriority(priority);
        item.setDetailText(contentText.getText());
        saveItem(item);
    }
}

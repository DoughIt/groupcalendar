package ui.item;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kernel.Display;
import todoitem.Item;
import todoitem.itemSub.AppointmentItem;
import todoitem.util.TimeStamp;


public class AppointmentItemPane extends ItemPane {
    private AppointmentItem item;
    private Label locationLabel;
    private Label peopleLabel;
    private Label contentLabel;
    private TextField locationText;
    private TextField peopleText;
    private TextField contentText;

    AppointmentItemPane(Item item, boolean fromAdd) {
        super(item, fromAdd);
        this.item = (AppointmentItem) item;
        init();
    }

    private void init() {
        locationLabel = new Label("地点：");
        peopleLabel = new Label("参与人：");
        contentLabel = new Label("描述：");
        locationText = new TextField(item.getLocation());
        peopleText = new TextField(item.getParticipants());
        contentText = new TextField(item.getDetailText());
        bindEvent();
        paint();
    }

    private void bindEvent() {
        addChildButton.setOnMouseClicked(event -> {
            try {
                AppointmentItem tempItem = new AppointmentItem(item.getFrom(), item.getTo(), "", "", "");
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
        this.add(locationLabel, 0, row);
        this.add(locationText, 1, row++);
        this.add(peopleLabel, 0, row);
        this.add(peopleText, 1, row++);
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
        item.setLocation(locationText.getText());
        item.setParticipants(peopleText.getText());
        item.setDetailText(contentText.getText());
        saveItem(item);
    }
}

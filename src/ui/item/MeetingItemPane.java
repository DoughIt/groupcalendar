package ui.item;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kernel.Display;
import todoitem.Item;
import todoitem.itemSub.MeetingItem;
import todoitem.util.TimeStamp;

public class MeetingItemPane extends ItemPane {
    private MeetingItem item;
    private Label topicLabel;
    private Label locationLabel;
    private Label contentLabel;
    private TextField topicText;
    private TextField locationText;
    private TextField contentText;

    MeetingItemPane(Item item, boolean fromAdd) {
        super(item, fromAdd);
        this.item = (MeetingItem) item;
        init();
    }


    private void init() {
        topicLabel = new Label("话题：");
        locationLabel = new Label("地点：");
        contentLabel = new Label("内容：");

        topicText = new TextField(item.getTopic());
        locationText = new TextField(item.getLocation());
        contentText = new TextField(item.getDetailText());
        bindEvent();
        paint();
    }

    private void bindEvent() {
        addChildButton.setOnMouseClicked(event -> {
            try {
                MeetingItem tempItem = new MeetingItem(item.getFrom(), item.getTo(), "", "", "");
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
        this.add(topicLabel, 0, row);
        this.add(topicText, 1, row);
        this.add(locationLabel, 0, ++row);
        this.add(locationText, 1, row++);
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
        item.setTopic(topicText.getText());
        item.setLocation(locationText.getText());
        item.setDetailText(contentText.getText());
        saveItem(item);
    }
}

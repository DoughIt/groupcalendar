package ui.item;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kernel.Display;
import todoitem.Item;
import todoitem.itemSub.InterviewItem;
import todoitem.util.TimeStamp;

public class InterviewItemPane extends ItemPane {
    private InterviewItem item;
    private Label placeLabel;
    private Label firmLabel;
    private Label positionLabel;
    private Label remarkLabel;

    private TextField placeText;
    private TextField firmText;
    private TextField positionText;
    private TextField remarkText;

    InterviewItemPane(Item item, boolean fromAdd) {
        super(item, fromAdd);
        this.item = (InterviewItem) item;
        init();
    }


    private void init() {
        placeLabel = new Label("面试地点：");
        firmLabel = new Label("面试公司：");
        positionLabel = new Label("面试职位：");
        remarkLabel = new Label("备忘：");

        placeText = new TextField(item.getPlace());
        firmText = new TextField(item.getCompany());
        positionText = new TextField(item.getJob());
        remarkText = new TextField(item.getRemark());
        bindEvent();
        paint();
    }

    private void bindEvent() {
        addChildButton.setOnMouseClicked(event -> {
            try {
                InterviewItem tempItem = new InterviewItem(item.getFrom(), item.getTo(), "", "", "", "");
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
        this.add(firmLabel, 0, row);
        this.add(firmText, 1, row);
        this.add(positionLabel, 0, ++row);
        this.add(positionText, 1, row);
        this.add(placeLabel, 0, ++row);
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
        item.setCompany(firmText.getText());
        item.setPlace(placeText.getText());
        item.setJob(positionText.getText());
        item.setRemark(remarkText.getText());
        saveItem(item);
    }
}

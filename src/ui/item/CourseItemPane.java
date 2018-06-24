package ui.item;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kernel.Display;
import todoitem.Const;
import todoitem.Item;
import todoitem.ItemFactory;
import todoitem.itemSub.CourseItem;
import todoitem.util.TimeStamp;

import java.util.ArrayList;


public class CourseItemPane extends ItemPane {
    private Label nameLabel;
    private Label frClassLabel;
    private Label toClassLabel;
    private Label durationLabel;
    private Label circleLabel;
    private Label teacherLabel;
    private Label contentLabel;
    private Label remarkLabel;
    private Label placeLabel;

    private Label frTimeLabel;
    private Label toTimeLabel;
    private Label circleStringLabel;
    private ChoiceBox<Integer> durationChoiceBox;
    private TextField nameText;
    private TextField teacherText;
    private TextField contentText;
    private TextField remarkText;
    private TextField placeText;
    private CourseItem item;

    public CourseItemPane(Item item, boolean fromAdd) {
        super(item, fromAdd);
        this.item = (CourseItem) item;
        init();
    }

    private void init() {
        nameLabel = new Label("课程名称：");
        frClassLabel = new Label("上课时间：");
        toClassLabel = new Label("下课时间：");
        durationLabel = new Label("持续周数：");
        circleLabel = new Label("重复周天：");
        teacherLabel = new Label("任课教师：");
        placeLabel = new Label("上课地点");
        contentLabel = new Label("课程内容：");
        remarkLabel = new Label("备忘：");
        frTimeLabel = new Label(startHourChoiceBox.getValue() + " : " + startMinuteChoiceBox.getValue());
        toTimeLabel = new Label(endHourChoiceBox.getValue() + " : " + endMinuteChoiceBox.getValue());
        circleStringLabel = new Label(Const.WEEK_NAMES[startDate.getValue().getDayOfWeek().getValue()]);

        ArrayList<Integer> durationList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            durationList.add(i);
        }
        durationChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(durationList));
        durationChoiceBox.setValue(item.getDuration());
        nameText = new TextField(item.getName());
        nameText.setPromptText("20字以内");
        teacherText = new TextField(item.getTeacher());
        teacherText.setPromptText("可输入多个教师名字，50字以内");
        placeText = new TextField(item.getPlace());
        contentText = new TextField(item.getDetailText());
        contentText.setPromptText("100字以内");
        remarkText = new TextField(item.getRemark());
        remarkText.setPromptText("100字以内");


//        if (!item.isFather()) {
//            startDate.setDisable(true);
//            endDate.setDisable(true);
//        }
        bindEvent();
        paint();
    }

    private void updateTime() {
        frTimeLabel.setText(startHourChoiceBox.getValue() + " : " + startMinuteChoiceBox.getValue());
        toTimeLabel.setText(endHourChoiceBox.getValue() + " : " + endMinuteChoiceBox.getValue());
    }

    private void updateDay() {
        circleStringLabel.setText(Const.WEEK_NAMES[startDate.getValue().getDayOfWeek().getValue()]);
    }

    private void bindEvent() {
        startHourChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateTime();
        });
        startMinuteChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateTime();
        });
        endHourChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateTime();
        });
        endMinuteChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateTime();
        });
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
                CourseItem tempItem = new CourseItem(item.getFrom(), item.getTo(), "", "", "", "", "", "", "");
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
        this.add(frClassLabel, 0, row);
        this.add(frTimeLabel, 1, row);
        this.add(toClassLabel, 2, row);
        this.add(toTimeLabel, 4, row++);

        this.add(durationLabel, 0, row);
        this.add(durationChoiceBox, 1, row);
        this.add(circleLabel, 2, row);
        this.add(circleStringLabel, 4, row++);

        this.add(nameLabel, 0, row);
        this.add(nameText, 1, row++);
        this.add(teacherLabel, 0, row);
        this.add(teacherText, 1, row++);
        this.add(placeLabel, 0, row);
        this.add(placeText, 1, row++);
        this.add(contentLabel, 0, row);
        this.add(contentText, 1, row++);
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
        item.setName(nameText.getText());
        item.setDuration(durationChoiceBox.getValue().toString());
        item.setTeacher(teacherText.getText());
        item.setPlace(placeText.getText());
        item.setDetailText(contentText.getText());
        item.setRemark(remarkText.getText());
        item.setStartDay(from);
        saveItem(item);
    }
}

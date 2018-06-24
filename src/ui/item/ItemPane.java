package ui.item;

import exception.DataErrorException;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import kernel.Display;
import todoitem.Const;
import todoitem.Item;
import todoitem.ItemFactory;
import todoitem.ItemManager;
import todoitem.util.TimeStamp;
import ui.pane.BodyPane;
import ui.pane.PromptSetPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class ItemPane extends GridPane {
    private Item item;
    private Label typeLabel;
    private Label startLabel;
    private Label endLabel;
    private Label priorityLabel;

    private Button promptButton;
    private ChoiceBox<String> priorityChoiceBox;

    Button addChildButton;
    Button saveButton;
    ChoiceBox<String> typeChoiceBox;    //待办事项类型选择框
    ChoiceBox<Integer> startHourChoiceBox;
    ChoiceBox<Integer> endHourChoiceBox;
    ChoiceBox<Integer> startMinuteChoiceBox;
    ChoiceBox<Integer> endMinuteChoiceBox;
    DatePicker startDate;
    DatePicker endDate;

    ItemManager manager;
    protected int priority;
    private int row;
    public boolean fromAdd;

    ItemPane(Item item, boolean fromAdd) {
        this.item = item;
        this.fromAdd = fromAdd;
        init();
    }


    private void init() {
        this.setHgap(20);
        this.setVgap(15);
        this.setPadding(new Insets(5));

        row = 0;
        priority = item.getPriority();
        manager = ItemManager.getInstance();

        TimeStamp from = item.getFrom();
        TimeStamp to = item.getTo();
        //类型
        ArrayList<String> typeValues = new ArrayList<>();
        for (int i = 0; i < Item.ItemType.values().length; i++) {
            typeValues.add(Item.ItemType.values()[i].getTypeStr());
        }
        typeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(typeValues));
        typeChoiceBox.setValue(item.getItemType().getTypeStr());

        //优先级
        ArrayList<String> priorityList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            priorityList.add(Const.PRIORITY_TYPE[i]);
        }
        priorityChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(priorityList));
        priorityChoiceBox.setValue(Const.PRIORITY_TYPE[item.getPriority()]);

        //时间
        ArrayList<Integer> hoursList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hoursList.add(i);
        }
        ArrayList<Integer> minutesList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minutesList.add(i);
        }
        //开始
        startHourChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(hoursList));
        startMinuteChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(minutesList));
        startHourChoiceBox.setValue(from.getHour());
        startMinuteChoiceBox.setValue(from.getMinute());
        startHourChoiceBox.setPrefWidth(Const.CELL_WIDTH);
        startMinuteChoiceBox.setPrefWidth(Const.CELL_WIDTH);
        //结束
        endHourChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(hoursList));
        endMinuteChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(minutesList));
        endHourChoiceBox.setValue(to.getHour());
        endMinuteChoiceBox.setValue(to.getMinute());
        endHourChoiceBox.setPrefWidth(Const.CELL_WIDTH);
        endMinuteChoiceBox.setPrefWidth(Const.CELL_WIDTH);


        startDate = new DatePicker(LocalDate.of(from.getYear(), from.getMonth(), from.getDay()));
        endDate = new DatePicker(LocalDate.of(to.getYear(), to.getMonth(), to.getDay()));
        typeLabel = new Label("类型：");
        typeLabel.setPrefWidth(Const.CELL_WIDTH);
        priorityLabel = new Label("优先级：");
        startLabel = new Label("开始时间：");
        endLabel = new Label("结束时间：");

        addChildButton = new Button("添加子事项");
        promptButton = new Button("设置提醒");
        saveButton = new Button("保存");

        if (!item.isFather() || fromAdd) {
            addChildButton.setDisable(true);
        }
        if (!fromAdd) {
            setDisable();//暂时不支持修改时间
        }
        if (item.getStatus() == Const.COMPLETED) {
            addChildButton.setDisable(true);
        }
        bindEvent();
        paint();
    }

    void setDisable() {
        startDate.setDisable(true);
        startHourChoiceBox.setDisable(true);
        startMinuteChoiceBox.setDisable(true);
        endDate.setDisable(true);
        endHourChoiceBox.setDisable(true);
        endMinuteChoiceBox.setDisable(true);
    }

    private void bindEvent() {
        typeChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            HashMap<String, String> attrs = item.getAttrs();
            TimeStamp from = new TimeStamp(startDate.getValue().getYear(), startDate.getValue().getMonth().getValue(),
                    startDate.getValue().getDayOfMonth(), startHourChoiceBox.getValue(), startMinuteChoiceBox.getValue());
            TimeStamp to = new TimeStamp(endDate.getValue().getYear(), endDate.getValue().getMonth().getValue(),
                    endDate.getValue().getDayOfMonth(), endHourChoiceBox.getValue(), endMinuteChoiceBox.getValue());
            Item.ItemType type = Item.ItemType.parseItemType(newValue);
            attrs.replace("startTime", from.toString());
            attrs.replace("endTime", to.toString());
            if (type != null) {
                attrs.replace("type", type.getTypeStr());
            }
            attrs.replace("priority", priority + "");
            Item item = ItemFactory.createItemByItemType(type, attrs);
            CommonItemPane.close();
            CommonItemPane.addPane(item);
        });

        promptButton.setOnMouseClicked(event -> {
//            Display.addPromptPopPane(item);
            PromptSetPane.getInstance().setItem(item);
            Display.addPromptSetPane();
        });
        priorityChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            priority = newValue.intValue();
        });

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(startDate.getValue())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }

        };
        endDate.setDayCellFactory(dayCellFactory);

        startHourChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startDate.getValue().isEqual(endDate.getValue())) {
                if (newValue > endHourChoiceBox.getValue()) {
                    endHourChoiceBox.setValue(newValue);
                } else if (Objects.equals(newValue, endHourChoiceBox.getValue())) {
                    if (startMinuteChoiceBox.getValue() > endMinuteChoiceBox.getValue()) {
                        endMinuteChoiceBox.setValue(startMinuteChoiceBox.getValue());
                    }
                }

            }
        });

        startMinuteChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startDate.getValue().isEqual(endDate.getValue()) && startHourChoiceBox.getValue().equals(endHourChoiceBox.getValue())) {
                if (newValue > endMinuteChoiceBox.getValue()) {
                    endMinuteChoiceBox.setValue(newValue);
                }
            }
        });

        endHourChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startDate.getValue().isEqual(endDate.getValue())) {
                if (newValue < startHourChoiceBox.getValue()) {
                    startHourChoiceBox.setValue(newValue);
                } else if (newValue.equals(startHourChoiceBox.getValue())) {
                    if (endMinuteChoiceBox.getValue() < startMinuteChoiceBox.getValue()) {
                        startMinuteChoiceBox.setValue(endMinuteChoiceBox.getValue());
                    }
                }
            }

        });
        startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isAfter(endDate.getValue()) || newValue.isEqual(endDate.getValue())) {
                endDate.setValue(newValue);
                startHourChoiceBox.setValue(endHourChoiceBox.getValue());
            }
        });
        endDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBefore(startDate.getValue()) || newValue.isEqual(startDate.getValue())) {
                startDate.setValue(newValue);
                endHourChoiceBox.setValue(startHourChoiceBox.getValue());
            }
        });
        saveButton.setOnMouseClicked(event -> {
            if (Display.hasPromptSet()) {
                Display.showToast("请先保存提醒设置！");
                return;
            }
            save();
            if (item.isFather() && fromAdd)
                addChildButton.setDisable(false);
            else CommonItemPane.close();
        });
    }

    private void paint() {
        //类型
        this.add(typeLabel, 0, 0);
        this.add(typeChoiceBox, 1, 0);
        //设置提醒
        this.add(promptButton, 4, 0);
        //优先级
        this.add(priorityLabel, 0, 1);
        this.add(priorityChoiceBox, 1, 1);
        //开始时间
        this.add(startLabel, 0, 2);
        this.add(startDate, 1, 2);
        this.add(startHourChoiceBox, 2, 2);
        this.add(new Label(":"), 3, 2);
        this.add(startMinuteChoiceBox, 4, 2);
        //结束时间
        this.add(endLabel, 0, 3);
        this.add(endDate, 1, 3);
        this.add(endHourChoiceBox, 2, 3);
        this.add(new Label(":"), 3, 3);
        this.add(endMinuteChoiceBox, 4, 3);
        row = 4;
    }

    int getTailRow() {
        return row;
    }

    abstract void save();

    void saveItem(Item item) {
        try {
            if (!item.isFather() && item.getFatherID() > 0) {
                manager.addChildItem(item);
            } else if (item.isFather()) {
                manager.addItem(item, true);
            }
            CommonItemPane.close();
            BodyPane.getInstance().refresh();
        } catch (DataErrorException e) {
            Display.showToast(e.getMessage());
        }
    }
}

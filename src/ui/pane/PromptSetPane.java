package ui.pane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import kernel.Display;
import todoitem.MinutesAheadType;
import todoitem.Item;
import todoitem.MinutesDeltaType;
import ui.Config;
import ui.util.LabelAndCombo;

import java.util.ArrayList;

public class PromptSetPane extends StackPane {
    private static PromptSetPane promptSetPane;
    private Item item;
    private LabelAndCombo promptStatusCombo;
    private LabelAndCombo minutesAheadCombo;
    private LabelAndCombo promptMode;
    private LabelAndCombo minutesDeltaCombo;

    public static PromptSetPane getInstance() {
        if (promptSetPane  == null) {
            promptSetPane =  new PromptSetPane();
        }
        return promptSetPane;
    }
    private PromptSetPane() {
        GridPane grid = new GridPane();
        int row = 0 ;
        int col = 0;
        Label promptLabel = new Label("提示设置");
        promptLabel.getStyleClass().add("promptLabel");
        grid.add(promptLabel , col , row++);


        ArrayList<String> promptStatusStr = new ArrayList<>();
        promptStatusStr.add("是");
        promptStatusStr.add("否");
        promptStatusCombo = LabelAndCombo.getInstance("是否提醒", promptStatusStr);

        ArrayList<String> minutesAheadStr = new ArrayList<>();
        for (int i = 0; i < MinutesAheadType.values().length; i++) {
            minutesAheadStr.add(MinutesAheadType.values()[i].getInfo());
        }
        minutesAheadCombo = LabelAndCombo.getInstance("提前：" , minutesAheadStr);

        ArrayList<String> promptModeStr = new ArrayList<>();
        promptModeStr.add("页面提示区提醒");
        promptModeStr.add("弹出框提醒");
        promptMode = LabelAndCombo.getInstance("提醒方式：" , promptModeStr);

        ArrayList<String> minutesDeltaStr = new ArrayList<>();
        for (int i = 0; i < MinutesDeltaType.values().length; i++) {
            minutesDeltaStr.add(MinutesDeltaType.values()[i].getInfo());
        }
        minutesDeltaCombo = LabelAndCombo.getInstance("间隔：" , minutesDeltaStr);

        GridPane checkGrid = new GridPane();
        int checkCol = 0;
        int checkRow = 0;
        checkGrid.add(promptStatusCombo,checkCol , checkRow++);
        promptStatusCombo.getComboBox().getEditor().setEditable(false);
        promptStatusCombo.getComboBox().getStyleClass().add("combo1");
        checkGrid.add(minutesAheadCombo , checkCol , checkRow++);
        minutesAheadCombo.getComboBox().getEditor().setEditable(false);
        minutesAheadCombo.getComboBox().getStyleClass().add("combo2");
        checkGrid.add(promptMode , checkCol , checkRow++);
        promptMode.getComboBox().getEditor().setEditable(false);
        promptMode.getComboBox().getStyleClass().add("combo3");
        checkGrid.add(minutesDeltaCombo, checkCol, checkRow++);
        minutesDeltaCombo.getComboBox().getEditor().setEditable(false);
        minutesDeltaCombo.getComboBox().getStyleClass().add("combo4");
        checkGrid.getStyleClass().add("checkGrid");
        checkGrid.setVgap(10);

        grid.add(checkGrid, col , row++);
        checkGrid.setAlignment(Pos.CENTER);

        Label cancelBt = new Label("取消");
        cancelBt.getStyleClass().add("button");
        cancelBt.setOnMouseClicked(event -> {
            Display.removePromptSetPane();
        });
        Label confirmBt = new Label("确定");
        confirmBt.getStyleClass().add("button");
        confirmBt.setOnMouseClicked(event -> {
            // TODO: 2018/5/27
            boolean promptStatus = false;
            if(promptStatusCombo.getComboBox().getValue().equals("是")) {
                promptStatus = true;
            }
            boolean showOnStage = true;
            if (promptMode.getComboBox().getValue().equals("弹出框提醒")) {
                showOnStage = false;
            }

            long minutesAhead = MinutesAheadType.parseAheadType(minutesAheadCombo.getComboBox().getValue()).getMinutes();
            long minutesDelta = MinutesDeltaType.parseInterType(minutesDeltaCombo.getComboBox().getValue()).getMinutes();

            item.setPromptStatus(promptStatus);
            item.setShowOnStage(showOnStage);
            item.setMinutesAhead(minutesAhead);
            item.setMinutesDelta(minutesDelta);
            Display.removePromptSetPane();
        });
        GridPane buttons = new GridPane();
        buttons.getStyleClass().add("buttons");
        buttons.add(confirmBt, 0 , 0);
        buttons.add(cancelBt , 1, 0);
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(10);
        grid.add(buttons, col , row++);
        grid.setMaxSize(390, 300);
        grid.setMinSize(390 , 300);
        grid.getStyleClass().add("mainPrompt");

        this.getChildren().add(grid);
        this.getStyleClass().add("mainScene");
        this.getStylesheets().add(Config.class.getResource("/stylesheet/promptset.css").toString());
    }

    public void setItem(Item item) {
        this.item = item;
        if (!item.promptStatus()) {
            promptStatusCombo.getComboBox().setValue("否");
        }
        else {
            promptStatusCombo.getComboBox().setValue("是");
        }
        if (!item.showOnStage()) {
            promptMode.getComboBox().setValue("弹出框提醒");
        }
        else {
            promptMode.getComboBox().setValue("页面提示区提醒");
        }
        minutesAheadCombo.getComboBox().setValue(
                MinutesAheadType.parseAheadType(item.minutesAhead()).getInfo());
        minutesDeltaCombo.getComboBox().setValue(MinutesDeltaType.parseInterType(item.minutesDelta()).getInfo());
    }
}

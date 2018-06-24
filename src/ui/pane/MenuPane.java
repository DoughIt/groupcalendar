package ui.pane;

import exception.InvalidDateException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import kernel.CalendarDate;
import kernel.DateUtil;
import kernel.Display;
import ui.Config;
import ui.util.LabelAndCombo;

import java.util.ArrayList;

/**
 * Created by 谢东方xdf on 2018/3/25.
 */
public class MenuPane extends StackPane {
    private static volatile MenuPane menuPane;
    private GridPane yearUnit;
    private GridPane monthUnit;

    public static MenuPane getInstance() {
        if (menuPane == null) {
            synchronized (MenuPane.class) {
                if (menuPane == null) {
                    menuPane = new MenuPane();
                    menuPane.setPadding(new Insets(0, 0, 10, 0));
                    menuPane.getStylesheets().add(Config.class.getResource("/stylesheet/menuPane.css").toString());
                }
            }
        }
        return menuPane;
    }

    private MenuPane() {
        final int comboWidth = 70;
        final int comboHeight = 25;
        yearUnit = LabelAndCombo.getInstance("Year:   ", 1800, 2100);
        ((LabelAndCombo) yearUnit).getComboBox().setMaxSize(comboWidth, comboHeight);
        ((LabelAndCombo) yearUnit).getComboBox().setMinSize(comboWidth, comboHeight);
        ((LabelAndCombo) yearUnit).getComboBox().setValue(DateUtil.getToday().getYear() + "");

        monthUnit = LabelAndCombo.getInstance("Month:   ", 1, 12);
        ((LabelAndCombo) monthUnit).getComboBox().setMaxSize(comboWidth-10, comboHeight);
        ((LabelAndCombo) monthUnit).getComboBox().setMinSize(comboWidth-10, comboHeight);
        ((LabelAndCombo) monthUnit).getComboBox().setValue("" + DateUtil.getToday().getMonth());

        final int buttonWidth = 80;
        final int buttonHeight = 35;
        Button checkBt = new Button("Check");
        checkBt.getStyleClass().add("btn");
        checkBt.setMaxSize(buttonWidth, buttonHeight);
        checkBt.setMinSize(buttonWidth, buttonHeight);
        checkBt.setCursor(Cursor.HAND);
        checkBt.setOnMouseClicked(event -> {
            try {
                String year = ((LabelAndCombo) yearUnit).getComboBox().getValue();
                String month = ((LabelAndCombo) monthUnit).getComboBox().getValue();
                String dateString = year + "-" + month + "-" + 1;
                CalendarDate date = new CalendarDate(dateString);
                Display.paintDays(date);
            } catch (InvalidDateException e) {
                Display.showToast(e.toString());
            } catch (Exception e) {
                Display.showToast("Type in number, please! ");
            }
        });
        Button todayBt = new Button("Today");
        todayBt.getStyleClass().add("btn");
        todayBt.setMaxSize(buttonWidth, buttonHeight);
        todayBt.setMinSize(buttonWidth, buttonHeight);
        todayBt.setCursor(Cursor.HAND);
        todayBt.setOnMouseClicked(event -> {
            CalendarDate today = DateUtil.getToday();
            Display.paintDays(today);
        });

        Button searchBt = new Button("Search");
        searchBt.getStyleClass().add("btn");
        searchBt.setMaxSize(buttonWidth, buttonHeight);
        searchBt.setMinSize(buttonWidth, buttonHeight);
        searchBt.setCursor(Cursor.HAND);
        searchBt.setOnMouseClicked(event -> {
            Display.addSearchPane();
        });


        int colIndex = 0;
        GridPane contentGrid = new GridPane();
        contentGrid.add(yearUnit, colIndex++, 0);
        contentGrid.setMargin(yearUnit, new Insets(5 , 0, 0 , 0));
        contentGrid.add(monthUnit, colIndex++, 0);
        contentGrid.setMargin(monthUnit , new Insets(5, 0 , 0 , 0));
        contentGrid.add(checkBt, colIndex++, 0);
        contentGrid.add(todayBt, colIndex++, 0);
        contentGrid.add(searchBt , colIndex++ , 0);
        contentGrid.setHgap(20);
        contentGrid.setAlignment(Pos.CENTER);
        this.getChildren().add(contentGrid);
    }

    public void changeChoice(CalendarDate date) {
        ((LabelAndCombo) yearUnit).getComboBox().setValue("" + date.getYear());
        ((LabelAndCombo) monthUnit).getComboBox().setValue("" + date.getMonth());
    }
}

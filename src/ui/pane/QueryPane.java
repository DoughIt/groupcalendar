package ui.pane;

import exception.InvalidDateException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import kernel.CalendarDate;
import kernel.Display;
import ui.Config;

/**
 * Created by 谢东方xdf on 2018/3/25.
 */
public class QueryPane extends StackPane {
    private static volatile QueryPane queryPane;

    public static QueryPane getInstance() {
        if (queryPane == null) {
            synchronized (QueryPane.class) {
                if (queryPane == null) {
                    queryPane = new QueryPane();
                    queryPane.getStylesheets().add(Config.class.getResource("/stylesheet/query.css").toString());
                    queryPane.getStylesheets().add(Config.class.getResource("/stylesheet/buttonAndLabel.css").toString());
                }
            }
        }
        return queryPane;
    }

    private QueryPane() {
        Label hintLabel = new Label("HintLabel");
        hintLabel.setStyle("-fx-text-fill: white ; -fx-font-size: 20");
        hintLabel.setMaxSize(170,35);
        hintLabel.setMinSize(170 , 35);
        TextField dateText = new TextField();
        dateText.setPromptText("Search format : 2018-1-1");
        dateText.setMaxSize(230, 35);
        dateText.setMinSize(230, 35);
        Button searchBt = new Button("Jump");
        searchBt.setStyle("-fx-font-size: 15");
        searchBt.getStyleClass().add("btn");
        searchBt.setMaxSize(80, 35);
        searchBt.setMinSize(80, 35);
        searchBt.setCursor(Cursor.HAND);
        searchBt.setOnMouseClicked(event -> {
            String text = dateText.getText();
            try {
                CalendarDate date = new CalendarDate(text);
                if (!Display.paintDays(date)) {
                    hintLabel.setText("InvalidDate");
                    hintLabel.setStyle("-fx-text-fill: red;; -fx-font-size: 15;");
                } else {
                    hintLabel.setText("HintLabel");
                    hintLabel.setStyle("-fx-text-fill: white ; -fx-font-size: 20");
                }
            } catch (InvalidDateException e) {
                hintLabel.setText("InvalidFormat");
                hintLabel.setStyle("-fx-text-fill: red;; -fx-font-size: 15;");
            }
        });
        GridPane contentGrid = new GridPane();
        contentGrid.add(hintLabel, 0, 0);
        contentGrid.add(dateText, 1, 0);
        contentGrid.add(searchBt, 2, 0);
        contentGrid.setMargin(searchBt, new Insets(0, 10,0 , 10));
        contentGrid.setHgap(15);
        contentGrid.setAlignment(Pos.CENTER);
        contentGrid.setPadding(new Insets(10, 0, 0, 0));
        this.getChildren().add(contentGrid);
    }

}


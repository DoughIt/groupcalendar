package ui.item;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kernel.Display;
import todoitem.Item;
import todoitem.ItemManager;
import ui.pane.ItemListPane;
import ui.view.DayItem;

import java.util.ArrayList;


public class CommonItemPane extends VBox {
    private static ItemManager manager;
    private static Stage primaryStage;
    private static Item item;
    private boolean fromAdd;
    private ItemPane itemPane;
    private Label childrenLabel;
    private ScrollPane scrollPane;

    public CommonItemPane(Item temp, boolean fromAdd) {
        this.fromAdd = fromAdd;
        item = temp;
        manager = ItemManager.getInstance();
        init();
    }

    private void bindEvent() {
    }

    private void init() {
        this.itemPane = ItemPaneFactory.createPaneByItemType(item, fromAdd);
        this.childrenLabel = new Label("子待办事项：");
        this.scrollPane = new ScrollPane();
        bindEvent();
        paint();
    }

    private void paint() {
        this.getChildren().add(itemPane);
        if (item.isFather() && !fromAdd) {
            Insets insets = new Insets(5);
            HBox hBox = new HBox();
            hBox.setSpacing(20);
            hBox.setPadding(insets);
            scrollPane.setPadding(new Insets(5, 0, 0, 5));
            scrollPane.setMinSize(300, 60);
            this.getChildren().add(hBox);
            ArrayList<Item> children = manager.getItemsByFatherItem(item);
            if (children.isEmpty()) {
                scrollPane.setContent(new Label("没有子待办事项"));
            } else {
                VBox vBox = new VBox();
                vBox.setPadding(insets);
                vBox.setSpacing(20);
                for (Item child : children) {
                    HBox hBox1 = new HBox();
                    hBox1.setSpacing(20);
                    Label typeLabel = new Label(child.getItemType().getTypeStr());
                    Label timeLabel = new Label(child.getFrom().toString() + " ~ " + child.getTo().toString());
                    hBox1.getChildren().addAll(typeLabel, timeLabel);
                    hBox1.setOnMouseClicked(event -> {
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.alwaysOnTopProperty();
                        stage.setTitle("Memo");
                        stage.setResizable(false);
                        stage.setScene(new Scene(new ItemListPane(child, stage)));
                        stage.showAndWait();
                    });
                    hBox1.setStyle("-fx-border-color: transparent transparent #a39e9e transparent; -fx-cursor: hand");
                    vBox.getChildren().add(hBox1);
                }
                scrollPane.setContent(vBox);
            }
            hBox.getChildren().addAll(childrenLabel, scrollPane);
        }
    }

    public static void refresh() {
        close();
//        addPane(item);
    }

    public static void close() {
        if (primaryStage != null) {
            Display.removePromptSetPane();
            primaryStage.close();
        }
    }

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
        stage.setOnCloseRequest(event -> {
            Display.removePromptSetPane();
        });
    }

    public static void addPane(Item item) {
        CommonItemPane pane = new CommonItemPane(item, true);
        if (primaryStage != null) {
            primaryStage.close();
        }else {
            primaryStage = new Stage();
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setResizable(false);
        }
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }
}

package ui.item;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import todoitem.Item;
import todoitem.ItemManager;

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
        primaryStage = null;
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

                    });
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
        if (primaryStage != null)
            primaryStage.close();
    }

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void addPane(Item item) {
        CommonItemPane pane = new CommonItemPane(item, true);
        Stage stage = new Stage();
        pane.setPrimaryStage(stage);
        stage.setScene(new Scene(pane));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
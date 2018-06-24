package ui.pane;

import javafx.scene.layout.StackPane;
import todoitem.Item;
import ui.Config;

public class WrappedEditPane extends StackPane {

    public WrappedEditPane(Item item,boolean fromAdd) {
        EditPane editPane = new EditPane(item , fromAdd);
        this.getStyleClass().add("mainScene");
        this.getChildren().add(editPane);
        this.getStylesheets().add(Config.class.getResource("/stylesheet/wrappedEditPane.css").toString());
    }
}

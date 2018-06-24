package ui.pane;

import javafx.scene.layout.StackPane;
import todoitem.Item;
import todoitem.util.TimeStamp;
import ui.Config;

public class WrappedDetailPane extends StackPane {

    public WrappedDetailPane(Item item) {
        DetailPane detailPane = new DetailPane(item);
        this.getStyleClass().add("mainScene");
        this.getChildren().add(detailPane);
        this.getStylesheets().add(Config.class.getResource("/stylesheet/wrappedDetailPane.css").toString());
    }

}

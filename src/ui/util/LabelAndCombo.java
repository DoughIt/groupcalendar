package ui.util;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class LabelAndCombo extends GridPane {
    private Label label;
    private ComboBox<String> comboBox;
    private LabelAndCombo(String labelStr , ArrayList<String> list) {
        label = new Label(labelStr);
        label.getStyleClass().add("myLabel");
        this.add(label , 0 , 0);
        comboBox = new ComboBox<>(FXCollections.observableArrayList(list));
        comboBox.setMinSize(50 , 25);
        comboBox.setMaxSize(50 , 25);
        comboBox.setEditable(true);
        comboBox.setValue(list.get(0));
        comboBox.setOpacity(0.7);
        comboBox.getStyleClass().add("comboBox");
        this.add(comboBox , 1, 0);
    }

    public static LabelAndCombo getInstance(String labelStr , int from , int to) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = from ; i <= to ; i++) {
            list.add(i + "");
        }
        return new LabelAndCombo(labelStr , list);
    }

    public static LabelAndCombo getInstance(String labelStr, ArrayList<String> list) {
        return new LabelAndCombo(labelStr, list);
    }

    public Label getLabel() {
        return label;
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }
}

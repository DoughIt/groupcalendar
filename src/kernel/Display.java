package kernel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.effect.GaussianBlur;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import todoitem.Item;
import todoitem.ItemManager;
import todoitem.itemSub.AppointmentItem;
import todoitem.util.TimeStamp;
import ui.Config;
import ui.pane.*;
import ui.util.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * You need to implement Calendar GUI here!
 * show the calendar of month of today.
 * jump to last/next month's calendar
 * jump to last/next year's calendar
 * <p>
 * jump to one specific day's calendar
 */
public class Display extends Application {
    private static Stage stage = new Stage();
    private static Pane imageCalendarPane = new Pane();
    private static GridPane calendarPane = new GridPane();
    private static WrappedDetailPane detailPane;
    private static TimeStamp fromStatic;
    private static TimeStamp toStatic;
    private static ImageView backgroundImage;
    private static WrappedEditPane editPane;
    private static boolean hasEdit = false;
    private static volatile boolean isClosed = false;
    private static PromptSetPane promptSetPane = PromptSetPane.getInstance();
    private static boolean hasPromptSet = false;
    private static Item fromItem;
    public Display() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        calendarPane.add(MenuPane.getInstance(), 0, 0);
        calendarPane.add(BodyPane.getInstance(), 0, 1);
        calendarPane.add(QueryPane.getInstance(), 0, 2);
        calendarPane.setMargin(QueryPane.getInstance(), new Insets(10, 0, 10, 0));

        final int calendarXVal = 550;
        final int calendarYVal = 40;
        calendarPane.setTranslateX(calendarXVal);
        calendarPane.setTranslateY(calendarYVal);

        backgroundImage = new ImageView(Config.class.getResource("/res/" + DateUtil.getToday().getMonth() + ".jpg").toString());
        backgroundImage.setFitHeight(Config.getWindowHeight());
        backgroundImage.setFitWidth(Config.getWindowWidth());
        backgroundImage.setEffect(new GaussianBlur(40));
        imageCalendarPane.getChildren().add(backgroundImage);
        imageCalendarPane.getChildren().add(calendarPane);


        Scene scene = new Scene(imageCalendarPane, Config.getWindowWidth(), Config.getWindowHeight());
        stage.setMaxHeight(Config.getWindowHeight());
        stage.setMinHeight(Config.getWindowHeight());
        stage.setMaxWidth(Config.getWindowWidth());
        stage.setMinWidth(Config.getWindowWidth());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            isClosed = true;
        });
        setPrompt();
    }


    public static void startItemListStage(TimeStamp from, TimeStamp to) {
        fromStatic = from;
        toStatic = to;
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.alwaysOnTopProperty();
        stage.setTitle("Memos");
        stage.setResizable(false);
        stage.setScene(new Scene(new ItemListPane(from, to, stage)));
        stage.showAndWait();
    }

    public static void addDetailPane(Item item) {
        if (hasEdit) {
            return;
        }
        if (detailPane != null) {
            removeDetailPane();
        }
        detailPane = new WrappedDetailPane(item);
        fromStatic = item.getFrom();
        toStatic = item.getTo();
        fromItem = item;
        imageCalendarPane.getChildren().add(detailPane);
    }

    public static void removeDetailPane() {
        imageCalendarPane.getChildren().remove(detailPane);
        detailPane = null;
    }

    public static void refreshDetailPane() {
        imageCalendarPane.getChildren().remove(detailPane);
        detailPane = new WrappedDetailPane(fromItem);
        imageCalendarPane.getChildren().add(detailPane);
    }

    public static void addEditPane(Item item, boolean fromAdd) {
        editPane = new WrappedEditPane(item, fromAdd);
        imageCalendarPane.getChildren().add(editPane);
        hasEdit = true;
        editPane.setAlignment(Pos.CENTER);
    }

    public static void removeEditPane() {
        BodyPane.getInstance().refresh();
        imageCalendarPane.getChildren().remove(editPane);
//        refreshDetailPane();
        hasEdit = false;
    }

    public static void addSearchPane() {
        imageCalendarPane.getChildren().add(SearchPane.getInstance());
        SearchPane.getInstance().setAlignment(Pos.CENTER);
    }

    public static void removeSearchPane() {
        imageCalendarPane.getChildren().remove(SearchPane.getInstance());
    }

    public static void addPromptPopPane(Item item) {
        PromptPopPane promptPopPane = new PromptPopPane(item);
        imageCalendarPane.getChildren().add(promptPopPane);
    }

    public static void removePromptPopPane(PromptPopPane promptPopPane) {
        imageCalendarPane.getChildren().remove(promptPopPane);
    }

    public static void addPromptPane(Item item) {
        PromptPane promptPane = new PromptPane(item);
        imageCalendarPane.getChildren().add(promptPane);
    }

    public static void removePromptPane(PromptPane pane) {
        imageCalendarPane.getChildren().remove(pane);
    }

    public static void addPromptSetPane() {
        if (!hasPromptSet) {
            imageCalendarPane.getChildren().add(promptSetPane);
            hasPromptSet = true;
        }
    }

    public static void removePromptSetPane() {
        if (hasPromptSet) {
            imageCalendarPane.getChildren().remove(promptSetPane);
            hasPromptSet = false;
        }
    }

    public static boolean hasPromptSet() {
        return hasPromptSet;
    }

    /**
     * paint the days of whole current month on the frame with the given kernel.CalendarDate
     *
     * @param date a valid kernel.CalendarDate param.
     */
    public static boolean paintDays(CalendarDate date) {
        if (date == null) {
            System.out.println("date is null!");
            return false;
        }
        if (DateUtil.isConsidered(date)) {
            MenuPane.getInstance().changeChoice(date);
            BodyPane.getInstance().changeContent(date);
            backgroundImage.setImage(new Image(Config.class.getResource("/res/" + date.getMonth() + ".jpg").toString()));
            return true;
        } else {
            System.out.println("date " + date.getYear() + date.getMonth() + date.getDay() + " is not valid");
            return false;
        }
    }

    public static void showToast(String mes) {
        int delay = 3500;
        int fadeInt = 500;
        int fadeOut = 500;
        Toast.makeText(stage, mes, delay, fadeInt, fadeOut);
    }

    public static void setPrompt() {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

        long currentSeconds = (System.currentTimeMillis() / 1000) % 60;
        long waitSeconds = 60 - currentSeconds;
        service.scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis());
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    ArrayList<Item> items = ItemManager.getInstance().getPrompts(System.currentTimeMillis());
                    for (Item item : items) {
                        if (item.showOnStage()) {
                            addPromptPane(item);
                        } else {
                            addPromptPopPane(item);
                        }
                    }
                    if (items.size() == 0) {
                        System.out.println("当前没有要提醒的事项！");
                    }

                }
            };
            // Platform 不再执行，当主线程关闭时。
            Platform.runLater(run);
            if (isClosed) {
                service.shutdown();
            }
        }, waitSeconds, 60, TimeUnit.SECONDS);
    }
}

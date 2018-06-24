package ui.pane;

import exception.DataErrorException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kernel.Display;
import todoitem.Item;
import todoitem.ItemManager;
import todoitem.itemSub.*;
import todoitem.util.TimeStamp;
import ui.item.CommonItemPane;

import java.util.ArrayList;

public class DetailPane extends GridPane {
    public DetailPane(Item item) {
        int mainCol = 0;
        int mainRow = 0;
        this.getStyleClass().add("mainContent");
        this.getStylesheets().add("/stylesheet/buttonAndLabel.css");
        Label title = new Label("Detail");
        title.getStyleClass().add("title");
        this.add(title, mainCol, mainRow++);

        GridPane detailContent = new GridPane();
        ItemPane itemPane = new ItemPane(item);
        detailContent.add(itemPane, 0, 0);
        ScrollPane scrollContent = new ScrollPane();
        scrollContent.setContent(detailContent);
        scrollContent.getStyleClass().add("scrollView");
        this.add(scrollContent, mainCol, mainRow++);

        HBox btRow = new HBox();
        Button addBt = new Button("add");
        addBt.setOnMouseClicked(event -> {
            Item tmpItem = null;
            try {
                tmpItem = new OtherItem(item.getFrom(), item.getTo(), "");
                ItemManager.getInstance().addItem(tmpItem, false);
                Display.removeDetailPane();
//                Display.addEditPane(tmpItem, true);
//                Display.removeDetailPane();
//                Display.addEditPane(item, true);
                CommonItemPane.addPane(tmpItem);
            } catch (DataErrorException e) {
                Display.showToast(e.getMessage());
            } catch (Exception e) {
                Display.showToast("请输入正确的时间与正确的类型！");
            }
        });
        addBt.getStyleClass().add("btn");
        addBt.setMaxSize(45, 30);
        addBt.setMinSize(45, 30);
        Button quitBt = new Button("quit");
        quitBt.setOnMouseClicked(event -> {
            Display.removeDetailPane();
        });
        quitBt.getStyleClass().add("btn");
        quitBt.setMaxSize(45, 30);
        addBt.setMinSize(45, 30);
        btRow.getChildren().add(0, addBt);
        btRow.getChildren().add(1, quitBt);
        btRow.setMargin(addBt, new Insets(10, 10, 10, 0));
        btRow.setMargin(quitBt, new Insets(10, 0, 10, 10));
        btRow.getStyleClass().add("buttons");

        this.add(btRow, mainCol, mainRow++);
        btRow.setAlignment(Pos.CENTER);

        this.setStyle("-fx-background-color: rgba(255 , 255 , 255 , 0.9);-fx-background-radius: 5.0;");
    }

    private class ItemPane extends GridPane {
        private Item item;

        public ItemPane(Item item) {
            // TODO: 2018/5/11
            this.item = item;
            int rowIndex = 0;
            Line line = new Line(0, 0, 500, 0);
            line.setStroke(Color.YELLOW);
            this.add(line, 0, rowIndex++);

//            Text fromToText = new Text("From: " + item.getValue("startTime") + " To: " + item.getValue("endTime"));
//            this.add(fromToText,0 , rowIndex++);
//            this.setMargin(fromToText, new Insets(5, 0 , 0 , 0 ));
//
//            String typeStr = "Type: " + item.getItemType().getTypeStr();
//            Text typeText = new Text(typeStr);
//            this.add(typeText,0 , rowIndex++);
//            this.setMargin(typeText, new Insets(5, 0 , 0 , 0 ));


//            Text detailText = new Text("Detail: " + item.getDetailText());
//            this.add(detailText, 0 , rowIndex++);
//            this.setMargin(detailText, new Insets(5, 0 , 0 , 0 ));


            rowIndex = addOtherInfo(this, item, rowIndex);

            GridPane buttonPane = new GridPane();
            Button removeBt = new Button("remove");
            removeBt.getStyleClass().add("redInDetail");
            removeBt.setMaxSize(80, 23);
            removeBt.setMinSize(80, 23);
            removeBt.setOnMouseClicked(event -> {
                ItemManager.getInstance().deleteItem(item);
                Display.removeDetailPane();
                BodyPane.getInstance().refresh();
            });
            removeBt.setCursor(Cursor.HAND);

            Button editBt = new Button("edit");
            editBt.getStyleClass().add("greenInDetail");
            editBt.setOnMouseClicked(event -> {
                Display.removeDetailPane();
//                Display.addEditPane(item, false);
                CommonItemPane pane = new CommonItemPane(item, false);
                Stage stage = new Stage();
                stage.setScene(new Scene(pane));
                pane.setPrimaryStage(stage);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.show();
            });
            editBt.setMaxSize(80, 23);
            editBt.setMinSize(80, 23);
            editBt.setCursor(Cursor.HAND);

            buttonPane.add(removeBt, 0, 0);
            buttonPane.add(editBt, 1, 0);
            buttonPane.setHgap(10);
            this.add(buttonPane, 0, rowIndex++);
            this.setMargin(buttonPane, new Insets(5, 0, 0, 0));
            this.setPadding(new Insets(10, 10, 0, 10));
            this.getStylesheets().add("/stylesheet/greenAndRed.css");
        }

        private int addOtherInfo(ItemPane itemPane, Item item, int rowIndex) {
            Item.ItemType itemType = item.getItemType();
            switch (itemType) {
                case MEETING:
                    return addMeetingInfo(itemPane, item, rowIndex);
                case DATE:
                    return addDateInfo(itemPane, item, rowIndex);
                case ANNIVERSARY:
                    return addAnniversaryInfo(itemPane, item, rowIndex);
                case TRAVEL:
                    return addTravelInfo(itemPane, item, rowIndex);
                case INTERVIEW:
                    return addInterviewInfo(itemPane, item, rowIndex);
                case COURSE:
                    return addCourseInfo(itemPane, item, rowIndex);
                case CUSTOM:
                    return addCustomInfo(itemPane, item, rowIndex);
            }
            return rowIndex;
//            if (item.getItemType() == Item.ItemType.DATE){//约会则还需显示人员和地点
//                Text participantText = new Text("Participants: " + ((AppointmentItem)item).getParticipants());
//                itemPane.add(participantText, 0 , rowIndex++);
//                itemPane.setMargin(participantText, new Insets(5, 0 , 0 , 0 ));
//
//                Text locationText = new Text("Location: " + ((AppointmentItem)item).getLocation());
//                itemPane.add(locationText, 0 , rowIndex++);
//                itemPane.setMargin(locationText, new Insets(5, 0 , 0 , 0 ));
//                return rowIndex;
//            }else if(item.getItemType() == Item.ItemType.MEETING){//会议还需显示地点和主题
//                Text locationText = new Text("Location: " + ((MeetingItem)item).getLocation());
//                itemPane.add(locationText, 0 , rowIndex++);
//                itemPane.setMargin(locationText, new Insets(5, 0 , 0 , 0 ));
//
//                Text topicText = new Text("Topic: " + ((MeetingItem)item).getTopic());
//                itemPane.add(topicText, 0 , rowIndex++);
//                itemPane.setMargin(topicText, new Insets(5, 0 , 0 , 0 ));
//                return rowIndex;
//            }else{
//                //其他则什么都不干,直接返回rowIndex
//

//            }
        }

        private int addMeetingInfo(ItemPane itemPane, Item item, int rowIndex) {
            Text fromToText = new Text("From: " + item.getValue("startTime") + " To: " + item.getValue("endTime"));
            this.add(fromToText, 0, rowIndex++);
            this.setMargin(fromToText, new Insets(5, 0, 0, 0));

            String typeStr = "Type: " + item.getItemType().getTypeStr();
            Text typeText = new Text(typeStr);
            this.add(typeText, 0, rowIndex++);
            this.setMargin(typeText, new Insets(5, 0, 0, 0));
            Text locationText = new Text("Location: " + ((MeetingItem) item).getLocation());
            itemPane.add(locationText, 0, rowIndex++);
            itemPane.setMargin(locationText, new Insets(5, 0, 0, 0));

            Text topicText = new Text("Topic: " + ((MeetingItem) item).getTopic());
            itemPane.add(topicText, 0, rowIndex++);
            itemPane.setMargin(topicText, new Insets(5, 0, 0, 0));
            return rowIndex;
        }

        private int addDateInfo(ItemPane itemPane, Item item, int rowIndex) {
            Text descriptionText = new Text(((AppointmentItem) item).getDetailDescription());
            itemPane.add(descriptionText, 0, rowIndex++);
            itemPane.setMargin(descriptionText, new Insets(5, 0, 0, 0));

            Text timeText = new Text(((AppointmentItem) item).getTimeDescription());
            itemPane.add(timeText, 0, rowIndex++);
            itemPane.setMargin(timeText, new Insets(5, 0, 0, 0));
            return rowIndex;
        }

        private int addAnniversaryInfo(ItemPane itemPane, Item item, int rowIndex) {
            Text descriptionText = new Text(((AnniversaryItem) item).getDetailDescription());
            itemPane.add(descriptionText, 0, rowIndex++);
            itemPane.setMargin(descriptionText, new Insets(5, 0, 0, 0));

            Text content = new Text(((AnniversaryItem) item).getValue("content"));
            itemPane.add(content, 0, rowIndex++);
            itemPane.setMargin(content, new Insets(5, 0, 0, 0));

            Text startDayText = new Text(((AnniversaryItem) item).getStartDayDescription());
            itemPane.add(startDayText, 0, rowIndex++);
            itemPane.setMargin(startDayText, new Insets(5, 0, 0, 0));
            return rowIndex;
        }

        private int addTravelInfo(ItemPane itemPane, Item item, int rowIndex) {
            Text descriptionText = new Text(((TravelItem) item).getPlaceDescription());
            itemPane.add(descriptionText, 0, rowIndex++);
            itemPane.setMargin(descriptionText, new Insets(5, 0, 0, 0));

            Text transText = new Text(((TravelItem) item).getTransDescription());
            itemPane.add(transText, 0, rowIndex++);
            itemPane.setMargin(transText, new Insets(5, 0, 0, 0));

            Text timeText = new Text(((TravelItem) item).getTimeDescription());
            itemPane.add(timeText, 0, rowIndex++);
            itemPane.setMargin(timeText, new Insets(5, 0, 0, 0));

            Text remark = new Text("Remark: " + ((TravelItem) item).getValue("remark"));
            itemPane.add(remark, 0, rowIndex++);
            itemPane.setMargin(remark, new Insets(5, 0, 0, 0));
            return rowIndex;
        }

        private int addInterviewInfo(ItemPane itemPane, Item item, int rowIndex) {
            Text placeDescriptionText = new Text(((InterviewItem) item).getPlaceDescription());
            itemPane.add(placeDescriptionText, 0, rowIndex++);
            itemPane.setMargin(placeDescriptionText, new Insets(5, 0, 0, 0));

            Text jobText = new Text(((InterviewItem) item).getJobDescription());
            itemPane.add(jobText, 0, rowIndex++);
            itemPane.setMargin(jobText, new Insets(5, 0, 0, 0));

            Text remark = new Text("Remark: " + ((InterviewItem) item).getValue("remark"));
            itemPane.add(remark, 0, rowIndex++);
            itemPane.setMargin(remark, new Insets(5, 0, 0, 0));
            return rowIndex;
        }

        private int addCourseInfo(ItemPane itemPane, Item item, int rowIndex) {
            Text detailDescriptionText = new Text(((CourseItem) item).getDetailDescription());
            itemPane.add(detailDescriptionText, 0, rowIndex++);
            itemPane.setMargin(detailDescriptionText, new Insets(5, 0, 0, 0));

            Text courseContentText = new Text(((CourseItem) item).getCourseContent());
            itemPane.add(courseContentText, 0, rowIndex++);
            itemPane.setMargin(courseContentText, new Insets(5, 0, 0, 0));

            Text durationContentText = new Text(((CourseItem) item).getDurationDescription());
            itemPane.add(durationContentText, 0, rowIndex++);
            itemPane.setMargin(durationContentText, new Insets(5, 0, 0, 0));

            Text remark = new Text("Remark: " + ((CourseItem) item).getValue("remark"));
            itemPane.add(remark, 0, rowIndex++);
            itemPane.setMargin(remark, new Insets(5, 0, 0, 0));
            return rowIndex;
        }

        private int addCustomInfo(ItemPane itemPane, Item item, int rowIndex) {
            if (item.getFrom() != null && item.getTo() != null) {
                String time = "From: " + ((OtherItem) item).getFrom().toString() + "  To: " + ((OtherItem) item).getTo().toString();
                Text timeText = new Text(time);
                itemPane.add(timeText, 0, rowIndex++);
                itemPane.setMargin(timeText, new Insets(5, 0, 0, 0));
            } else {
                Text timeText = new Text("No time");
                itemPane.add(timeText, 0, rowIndex++);
                itemPane.setMargin(timeText, new Insets(5, 0, 0, 0));
            }

            Text detailText = new Text("Detail: " + item.getDetailText());
            itemPane.add(detailText, 0, rowIndex++);
            itemPane.setMargin(detailText, new Insets(5, 0, 0, 0));
            return rowIndex;
        }
    }
}

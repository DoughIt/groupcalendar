package ui.pane;

import exception.DataErrorException;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import kernel.Display;
import todoitem.Item;
import todoitem.ItemManager;
import todoitem.itemSub.*;
import todoitem.util.TimeStamp;
import ui.util.LabelAndCombo;

import java.util.ArrayList;

public class EditPane extends GridPane {
    private Item item;
    private TimeStamp from;
    private TimeStamp to;
    private Item.ItemType itemType;
    int priority;
    private String detail;
    private String location;
    private String meetingTopic;
    private String dateParticipants;
    private GridPane[] totalControl = new GridPane[7];
    //    private String[] meetingInformation=new String[3];
    private GridPane[] meetingControl = new GridPane[3];
    //    private String[] dateInformation=new String[3];
    private GridPane[] dateControl = new GridPane[3];
    //    private String[] anniversaryInformation=new String[2];
    private GridPane[] anniversaryControl = new GridPane[2];
    //    private String[] courseInformation=new String[7];
    private GridPane[] courseControl = new GridPane[7];
    //    private String[] travelInformation=new String[4];
    private GridPane[] travelControl = new GridPane[4];
    //    private String[] interviewInformation=new String[4];
    private GridPane[] interviewControl = new GridPane[4];
    private GridPane[] customControl = new GridPane[1];
    private GridPane priorityRow = new GridPane();
    private CheckBox importantCheckBox = new CheckBox();
    private CheckBox urgentCheckBox = new CheckBox();
    private RadioButton customIsSetTime = new RadioButton();


    public EditPane(Item item, boolean fromAdd) {
        this.item = item;
        from = item.getFrom();
        to = item.getTo();
        itemType = item.getItemType();
        initialInformationControl();
        initial(fromAdd);
    }

    private void initial(boolean fromAdd) {
        int checkCol = 0;
        int checkRow = 0;
        GridPane checkGrid = new GridPane();
        //将选择的内容放在checkGrid里面。 再放到主体里面

        int mainCol = 0;
        int mainRow = 0;
        this.getStyleClass().add("mainContent");

        Label title = new Label("编辑信息");
        title.getStyleClass().add("title");
        this.add(title, mainCol, mainRow++);

        /**
         * from timeStamp information
         * */
        GridPane fromFirstRow = new GridPane();
        Label fromLabel = new Label("From: ");
        fromFirstRow.add(fromLabel, 0, 0);

        GridPane fromYearRow = LabelAndCombo.getInstance("year:  ", 1800, 2100);
        ((LabelAndCombo) fromYearRow).getComboBox().setValue(from == null ? "2018" : from.getYear() + "");
        ((LabelAndCombo) fromYearRow).getComboBox().setMinSize(70, 25);
        ((LabelAndCombo) fromYearRow).getComboBox().setMaxSize(70, 25);
        fromFirstRow.add(fromYearRow, 1, 0);

        GridPane fromMonthRow = LabelAndCombo.getInstance("month:  ", 1, 12);
        ((LabelAndCombo) fromMonthRow).getComboBox().setValue(from == null ? "1" : from.getMonth() + "");
        fromFirstRow.add(fromMonthRow, 2, 0);

        GridPane fromDayRow = LabelAndCombo.getInstance("day:  ", 1, 31);
        ((LabelAndCombo) fromDayRow).getComboBox().setValue(from == null ? "1" : from.getDay() + "");
        fromFirstRow.add(fromDayRow, 3, 0);
        checkGrid.add(fromFirstRow, checkCol, checkRow++);


        GridPane fromSecondRow = new GridPane();
        GridPane fromHourRow = LabelAndCombo.getInstance("hour:  ", 0, 23);
        ((LabelAndCombo) fromHourRow).getComboBox().setValue(from == null ? "0" : from.getHour() + "");
        fromSecondRow.add(fromHourRow, 4, 0);

        GridPane fromMinuteRow = LabelAndCombo.getInstance("minute:  ", 0, 59);
        ((LabelAndCombo) fromMinuteRow).getComboBox().setValue(from == null ? "0" : from.getMinute() + "");
        fromSecondRow.add(fromMinuteRow, 5, 0);
        checkGrid.add(fromSecondRow, checkCol, checkRow++);
        fromSecondRow.setAlignment(Pos.CENTER);


        /**
         * to timeStamp information
         * */
        GridPane toFirstRow = new GridPane();
        Label toLabel = new Label("To: ");
        toFirstRow.add(toLabel, 0, 0);

        GridPane toYearRow = LabelAndCombo.getInstance("year:  ", 1800, 2300);
        ((LabelAndCombo) toYearRow).getComboBox().setValue(to == null ? "2018" : to.getYear() + "");
        ((LabelAndCombo) toYearRow).getComboBox().setMaxSize(70, 25);
        ((LabelAndCombo) toYearRow).getComboBox().setMinSize(70, 25);
        toFirstRow.add(toYearRow, 2, 0);

        GridPane toMonthRow = LabelAndCombo.getInstance("month:  ", 1, 12);
        ((LabelAndCombo) toMonthRow).getComboBox().setValue(to == null ? "1" : to.getMonth() + "");
        toFirstRow.add(toMonthRow, 4, 0);

        GridPane toDayRow = LabelAndCombo.getInstance("day:  ", 1, 31);
        ((LabelAndCombo) toDayRow).getComboBox().setValue(to == null ? "1" : to.getDay() + "");
        toFirstRow.add(toDayRow, 6, 0);
        checkGrid.add(toFirstRow, checkCol, checkRow++);

        GridPane toSecondRow = new GridPane();
        GridPane toHourRow = LabelAndCombo.getInstance("hour:  ", 0, 23);
        ((LabelAndCombo) toHourRow).getComboBox().setValue(to == null ? "23" : to.getHour() + "");
        toSecondRow.add(toHourRow, 8, 0);

        GridPane toMinuteRow = LabelAndCombo.getInstance("minute:  ", 0, 59);
        ((LabelAndCombo) toMinuteRow).getComboBox().setValue(to == null ? "59" : to.getMinute() + "");
        toSecondRow.add(toMinuteRow, 10, 0);
        checkGrid.add(toSecondRow, checkCol, checkRow++);
        toSecondRow.setAlignment(Pos.CENTER);

        /**
         * type information
         * */
        // TODO: 2018/5/11  
        ArrayList<String> typeList = new ArrayList<>();
        for (int i = 0; i < Item.ItemType.values().length; i++) {
            typeList.add(Item.ItemType.values()[i].toString());
        }
        GridPane typeRow = LabelAndCombo.getInstance("type: ", typeList);

        ((LabelAndCombo) typeRow).getComboBox().setMinSize(100, 25);
        ((LabelAndCombo) typeRow).getComboBox().setMaxSize(100, 25);
        checkGrid.add(typeRow, checkCol, checkRow++);
        typeRow.setAlignment(Pos.CENTER);
        /**
         * the pane for text of all the information
         * */
        GridPane informationRow = new GridPane();
        /**
         * Detail information
         * */
//        GridPane detailRow = new LabelAndTextRow("Info: ", detail);
//        informationRow.add(detailRow, 0, 0);
//        detailRow.setAlignment(Pos.CENTER);

        /**
         * location information
         * */
//        GridPane locationRow = new LabelAndTextRow("location: ", location);
//        locationRow.setAlignment(Pos.CENTER);

        /**
         * topic information
         * */
//        GridPane topicRow = new LabelAndTextRow("topic: ", meetingTopic);
//        topicRow.setAlignment(Pos.CENTER);
        /**
         * topic information
         * */
//        GridPane participantsRow = new LabelAndTextRow("participants: ", dateParticipants);
//        participantsRow.setAlignment(Pos.CENTER);

        checkGrid.add(priorityRow, checkCol, checkRow++);
        checkGrid.add(informationRow, checkCol, checkRow++);
        informationRow.setVgap(10);
        informationRow.setAlignment(Pos.CENTER);

        ((LabelAndCombo) typeRow).getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < totalControl.length; i++) {
                informationRow.getChildren().remove(totalControl[i]);
            }
            informationRow.getChildren().remove(customIsSetTime);
            switch (newValue) {
                case "MEETING":
                    informationRow.add(totalControl[0], 0, 0);
                    break;
                case "DATE":
                    informationRow.add(totalControl[1], 0, 0);
                    break;
                case "ANNIVERSARY":
                    informationRow.add(totalControl[2], 0, 0);
                    break;
                case "COURSE":
                    informationRow.add(totalControl[3], 0, 0);
                    break;
                case "TRAVEL":
                    informationRow.add(totalControl[4], 0, 0);
                    break;
                case "INTERVIEW":
                    informationRow.add(totalControl[5], 0, 0);
                    break;
                case "CUSTOM":
                    informationRow.add(totalControl[6], 0, 0);
                    informationRow.add(customIsSetTime, 0, 1);
                    break;

            }
//            informationRow.getChildren().removeAll(topicRow, locationRow, participantsRow);
//            if (newValue.equals("MEETING")) {
//                informationRow.add(locationRow, 0, 1);
//                informationRow.add(topicRow, 0, 2);
//            } else if (newValue.equals("DATE")) {
//                informationRow.add(locationRow, 0, 1);
//                informationRow.add(participantsRow, 0, 2);
//            }
        });
        ((LabelAndCombo) typeRow).getComboBox().setValue("MEETING");
        ((LabelAndCombo) typeRow).getComboBox().setValue("CUSTOM");
        ((LabelAndCombo) typeRow).getComboBox().setValue(itemType.toString());
        /**
         * Save and remove button;
         * */
        checkGrid.setVgap(10);
        checkGrid.getStylesheets().add("/stylesheet/greenAndRed.css");
        checkGrid.getStyleClass().add("checkGrid");
        this.add(checkGrid, mainCol, mainRow++);
        checkGrid.setAlignment(Pos.CENTER);


        GridPane buttonRow = new GridPane();
        Label saveBt = new Label("保存");
        saveBt.getStyleClass().add("button");
        saveBt.setOnMouseClicked(event -> {
            try {
                int[] fromArray = new int[5];
                fromArray[0] = Integer.parseInt(((LabelAndCombo) fromYearRow).getComboBox().getValue());
                fromArray[1] = Integer.parseInt(((LabelAndCombo) fromMonthRow).getComboBox().getValue());
                fromArray[2] = Integer.parseInt(((LabelAndCombo) fromDayRow).getComboBox().getValue());
                fromArray[3] = Integer.parseInt(((LabelAndCombo) fromHourRow).getComboBox().getValue());
                fromArray[4] = Integer.parseInt(((LabelAndCombo) fromMinuteRow).getComboBox().getValue());

                int[] toArray = new int[5];
                toArray[0] = Integer.parseInt(((LabelAndCombo) toYearRow).getComboBox().getValue());
                toArray[1] = Integer.parseInt(((LabelAndCombo) toMonthRow).getComboBox().getValue());
                toArray[2] = Integer.parseInt(((LabelAndCombo) toDayRow).getComboBox().getValue());
                toArray[3] = Integer.parseInt(((LabelAndCombo) toHourRow).getComboBox().getValue());
                toArray[4] = Integer.parseInt(((LabelAndCombo) toMinuteRow).getComboBox().getValue());

                TimeStamp fromStamp = new TimeStamp(fromArray[0], fromArray[1], fromArray[2], fromArray[3], fromArray[4]);
                TimeStamp toStamp = new TimeStamp(toArray[0], toArray[1], toArray[2], toArray[3], toArray[4]);
                if (!toStamp.isValid() || !fromStamp.isValid()) {
                    throw new Exception("TimeStamp not valid! ");
                }
                Item.ItemType tmpType = Item.ItemType.parseItemType(((LabelAndCombo) typeRow).getComboBox().getValue());
                if (tmpType == null) {
                    throw new Exception("No such item type!");
                }
                if (!importantCheckBox.isSelected() && !urgentCheckBox.isSelected()) {
                    priority = 4;
                } else if (!urgentCheckBox.isSelected() && importantCheckBox.isSelected()) {
                    priority = 3;
                } else if (urgentCheckBox.isSelected() && !importantCheckBox.isSelected()) {
                    priority = 2;
                } else {
                    priority = 1;
                }
                Item tmpItem = null;
                switch (tmpType) {
                    case MEETING:
                        tmpItem = meetingTypeItem(fromStamp, toStamp);
                        break;
                    case DATE:
                        tmpItem = dateTypeItem(fromStamp, toStamp);
                        break;
                    case ANNIVERSARY:
                        tmpItem = anniversaryTypeItem(fromStamp, toStamp);
                        break;
                    case COURSE:
                        tmpItem = courseTypeItem(fromStamp, toStamp);
                        break;
                    case TRAVEL:
                        tmpItem = travelTypeItem(fromStamp, toStamp);
                        break;
                    case INTERVIEW:
                        tmpItem = interviewTypeItem(fromStamp, toStamp);
                        break;
                    case CUSTOM:
                        tmpItem = customTypeItem(fromStamp, toStamp);
                        break;

                }
//                detail = ((LabelAndTextRow) detailRow).getTextField().getText();
//                if (tmpType == Item.ItemType.MEETING) {
//                    location = ((LabelAndTextRow) locationRow).getTextField().getText();
//                    meetingTopic = ((LabelAndTextRow) topicRow).getTextField().getText();
//                    tmpItem = new MeetingItem(fromStamp, toStamp, detail, meetingTopic, location);
//                } else if (tmpType == Item.ItemType.DATE) {
//                    location = ((LabelAndTextRow) locationRow).getTextField().getText();
//                    dateParticipants = ((LabelAndTextRow) participantsRow).getTextField().getText();
//                    tmpItem = new AppointmentItem(fromStamp, toStamp, detail, dateParticipants, location);
//                } else {
//                    tmpItem = new OtherItem(fromStamp, toStamp, detail);
//                }
                // 相当于这里新建了一个item， 但是没有保存原来所有的信息。
                tmpItem.setPromptStatus(this.item.promptStatus());
                tmpItem.setShowOnStage(this.item.showOnStage());
                tmpItem.setMinutesDelta(this.item.minutesDelta());
                tmpItem.setMinutesAhead(this.item.minutesAhead());

                ItemManager.getInstance().deleteItem(this.item);
                ItemManager.getInstance().addItem(tmpItem, true);
                Display.removeEditPane();
            } catch (DataErrorException e) {
                Display.showToast(e.getMessage());
            } catch (Exception e) {
                Display.showToast("请输入正确的时间与正确的类型！");
            }
        });
        Label cancelBt = new Label("取消");
        cancelBt.getStyleClass().add("button");

        cancelBt.setOnMouseClicked(event -> {
            if (fromAdd) {
                ItemManager.getInstance().deleteItem(item);
            }
            Display.removeEditPane();
        });
        Label promptBt = new Label("设置提醒");
        promptBt.getStyleClass().add("button");
        promptBt.setOnMouseClicked(event -> {
            PromptSetPane.getInstance().setItem(item);
            Display.addPromptSetPane();
        });

        buttonRow.add(promptBt, 0, 0);
        buttonRow.add(saveBt, 1, 0);
        buttonRow.add(cancelBt, 2, 0);
        buttonRow.setHgap(10);
        buttonRow.getStyleClass().add("buttons");
        this.add(buttonRow, mainCol, mainRow++);
    }

    public void initialInformationControl() {
        meetingControl[0] = new LabelAndTextRow("place", "");
        meetingControl[1] = new LabelAndTextRow("topic", "");
        meetingControl[2] = new LabelAndTextRow("content", "");
        dateControl[0] = new LabelAndTextRow("place", "");
        dateControl[1] = new LabelAndTextRow("people", "");
        dateControl[2] = new LabelAndTextRow("content", "");
        anniversaryControl[0] = new LabelAndTextRow("type", "");
        anniversaryControl[1] = new LabelAndTextRow("content", "");
        courseControl[0] = new LabelAndTextRow("courseName", "");
        courseControl[1] = new LabelAndTextRow("content", "");
        courseControl[2] = new LabelAndTextRow("duration", "");
        courseControl[3] = new LabelAndTextRow("teacher", "");
        courseControl[4] = new LabelAndTextRow("place", "");
        courseControl[5] = new LabelAndTextRow("remark", "");
        courseControl[6] = new LabelAndTextRow("dayOfWeek", "");
        ArrayList<String> way = new ArrayList<>();
        way.add("plane");
        way.add("train");
        way.add("bus");
        travelControl[0] = LabelAndCombo.getInstance("trans", way);
        ((LabelAndCombo) travelControl[0]).getComboBox().setEditable(true);
        ((LabelAndCombo) travelControl[0]).getComboBox().setMinWidth(100);
        ((LabelAndCombo) travelControl[0]).getComboBox().setMaxWidth(100);
        travelControl[1] = new LabelAndTextRow("shifts", "");
        travelControl[2] = new LabelAndTextRow("place", "");
        travelControl[3] = new LabelAndTextRow("remark", "");
        interviewControl[0] = new LabelAndTextRow("place", "");
        interviewControl[1] = new LabelAndTextRow("company", "");
        interviewControl[2] = new LabelAndTextRow("job", "");
        interviewControl[3] = new LabelAndTextRow("remark", "");
        customControl[0] = new LabelAndTextRow("content", "");
        customIsSetTime.setText("Set Time");
        customIsSetTime.setAlignment(Pos.CENTER);
        customIsSetTime.setSelected(true);
        importantCheckBox.setText("important");
        urgentCheckBox.setText("urgent");
        priorityRow.add(importantCheckBox, 0, 0);
        priorityRow.add(urgentCheckBox, 1, 0);
        priorityRow.setHgap(10);
        priorityRow.setAlignment(Pos.CENTER);
        for (int i = 0; i < totalControl.length; i++) {
            totalControl[i] = new GridPane();
            totalControl[i].setAlignment(Pos.CENTER);
            totalControl[i].setVgap(10);
        }
        for (int i = 0; i < 3; i++) {
            totalControl[0].add(meetingControl[i], 0, i);
            totalControl[1].add(dateControl[i], 0, i);
        }
        for (int i = 0; i < 2; i++) {
            totalControl[2].add(anniversaryControl[i], 0, i);
        }
        for (int i = 0; i < 7; i++) {
            totalControl[3].add(courseControl[i], 0, i);
        }
        for (int i = 0; i < 4; i++) {
            totalControl[4].add(travelControl[i], 0, i);
            totalControl[5].add(interviewControl[i], 0, i);
        }
        totalControl[6].add(customControl[0], 0, 0);
    }

    private Item meetingTypeItem(TimeStamp from, TimeStamp to) throws Exception {
        String meetingPlace = ((LabelAndTextRow) meetingControl[0]).getTextField().getText();
        String meetingTopic = ((LabelAndTextRow) meetingControl[1]).getTextField().getText();
        String meetingContent = ((LabelAndTextRow) meetingControl[2]).getTextField().getText();
        return new MeetingItem(from, to, meetingContent, meetingTopic, meetingPlace, priority);
    }

    private Item dateTypeItem(TimeStamp from, TimeStamp to) throws Exception {
        String datePlace = ((LabelAndTextRow) dateControl[0]).getTextField().getText();
        String datePeople = ((LabelAndTextRow) dateControl[1]).getTextField().getText();
        String dateContent = ((LabelAndTextRow) dateControl[2]).getTextField().getText();
        return new AppointmentItem(from, to, dateContent, datePeople, datePlace, priority);
    }

    private Item anniversaryTypeItem(TimeStamp from, TimeStamp to) throws Exception {
        String anniversaryType = ((LabelAndTextRow) anniversaryControl[0]).getTextField().getText();
        String anniversaryContent = ((LabelAndTextRow) anniversaryControl[1]).getTextField().getText();
        return new AnniversaryItem(from, to, "", anniversaryContent, anniversaryType, priority);
    }

    private Item courseTypeItem(TimeStamp from, TimeStamp to) throws Exception {
        String courseName = ((LabelAndTextRow) courseControl[0]).getTextField().getText();
        String courseContent = ((LabelAndTextRow) courseControl[1]).getTextField().getText();
        String courseDuration = ((LabelAndTextRow) courseControl[2]).getTextField().getText();
        String courseTeacher = ((LabelAndTextRow) courseControl[3]).getTextField().getText();
        String coursePlace = ((LabelAndTextRow) courseControl[4]).getTextField().getText();
        String courseRemark = ((LabelAndTextRow) courseControl[5]).getTextField().getText();
        String courseDay = ((LabelAndTextRow) courseControl[6]).getTextField().getText();
        return new CourseItem(from, to, courseName, courseContent, courseTeacher, courseRemark,
                coursePlace, courseDuration, courseDay, priority);
    }

    private Item travelTypeItem(TimeStamp from, TimeStamp to) throws Exception {
        String travelWay = ((LabelAndCombo) travelControl[0]).getComboBox().getValue();
        String travelPlace = ((LabelAndTextRow) travelControl[1]).getTextField().getText();
        String travelNumber = ((LabelAndTextRow) travelControl[2]).getTextField().getText();
        String travelRemark = ((LabelAndTextRow) travelControl[3]).getTextField().getText();
        return new TravelItem(from, to, travelWay, travelPlace, travelNumber, travelRemark, priority);
    }

    private Item interviewTypeItem(TimeStamp from, TimeStamp to) throws Exception {
        String interviewPlace = ((LabelAndTextRow) interviewControl[0]).getTextField().getText();
        String interviewCompany = ((LabelAndTextRow) interviewControl[1]).getTextField().getText();
        String interviewJob = ((LabelAndTextRow) interviewControl[2]).getTextField().getText();
        String interviewRemark = ((LabelAndTextRow) interviewControl[3]).getTextField().getText();
        return new InterviewItem(from, to, interviewPlace, interviewCompany, interviewJob, interviewRemark, priority);
    }

    private Item customTypeItem(TimeStamp from, TimeStamp to) throws Exception {
        String customContent = ((LabelAndTextRow) customControl[0]).getTextField().getText();
        return customIsSetTime.isSelected() ? new OtherItem(from, to, customContent, priority) : new OtherItem(null, null, customContent, priority);
    }

    private class LabelAndTextRow extends GridPane {
        private Label label;
        private TextField textField;

        public LabelAndTextRow(String labelStr, String textStr) {
            label = new Label(labelStr);
            textField = new TextField(textStr);
            this.add(label, 0, 0);
            this.add(textField, 1, 0);
        }

        public Label getLabel() {
            return label;
        }

        public TextField getTextField() {
            return textField;
        }
    }
}

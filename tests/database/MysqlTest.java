package database;

import exception.DataErrorException;
import org.junit.Test;
import todoitem.*;
import todoitem.itemSub.*;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class MysqlTest {
    @Test
    public void totalTest() throws Exception {
        Mysql mysql = Mysql.getInstance();
        mysql.clear();
        //Anniversary
        TimeStamp from = TimeStampFactory.createStampDayStart(1801,1,1);
        TimeStamp to = TimeStampFactory.createStampDayEnd(1801,2,2);
        AnniversaryItem anniversaryItem = new AnniversaryItem(from , to ,"张健","纪念日详情" , "结婚纪念日" );
        int anniId = mysql.addSchedule(anniversaryItem.getAttrs());
        anniversaryItem.setID(anniId); // 可能为0

        //AppointmentItem
        from = TimeStampFactory.createStampDayStart(1803 , 1, 1);
        to = TimeStampFactory.createStampDayEnd(1804, 1 , 1);
        AppointmentItem appointmentItem = new AppointmentItem(from, to , "今天约会" , "女票" , "五角场");
        int appointId = mysql.addSchedule(appointmentItem.getAttrs());
        appointmentItem.setID(appointId);

        //Course
        from = TimeStampFactory.createStampDayStart(1804, 1, 1);
        to = TimeStampFactory.createStampDayEnd(1804, 1,1);
        CourseItem courseItem = new CourseItem(from , to , "离散数学", "是一门早课", "陈老师",
                "课讲得不错", "2107", "7", "5");
        int courseId = mysql.addSchedule(courseItem.getAttrs());
        courseItem.setID(courseId);

        //Interview
        from = TimeStampFactory.createStampDayStart(1805, 1 , 1);
        to = TimeStampFactory.createStampDayEnd(1805, 1, 1);
        InterviewItem interviewItem = new InterviewItem(from, to, "张江高科", "张江高科技公司", "码农", "要记住的！");
        int interId = mysql.addSchedule(interviewItem.getAttrs());
        interviewItem.setID(interId);

        //MeetingItem
        from = TimeStampFactory.createStampDayStart(1810 , 1, 1);
        to = TimeStampFactory.createStampDayEnd(1811, 1, 1);
        MeetingItem meetingItem = new MeetingItem(from, to, "特大会议", "", "地点");
        int meetingId = mysql.addSchedule(meetingItem.getAttrs());
        meetingItem.setID(meetingId);

        //OtherItem
        from = TimeStampFactory.createStampDayStart(1810 , 1, 1);
        to = TimeStampFactory.createStampDayEnd(1811, 1, 1);
        OtherItem otherItem = new OtherItem(from , to , "34134");
        int otherId = mysql.addSchedule(otherItem.getAttrs());
        otherItem.setID(otherId);

        //TravelItem
        from = TimeStampFactory.createStampDayStart(1811 , 2, 1);
        to = TimeStampFactory.createStampDayEnd(1812, 1, 1);
        TravelItem travelItem = new TravelItem(from , to , "plane", "北京", "30", "旅游好玩");
        int travelId = mysql.addSchedule(travelItem.getAttrs());
        travelItem.setID(travelId);

        if(anniId != 0) {
            AnniversaryItem anniResult = new AnniversaryItem(mysql.queryByID(anniId+""));
            assertEquals(anniversaryItem.getAnniversaryType(), anniversaryItem.getAnniversaryType());
            if(mysql.deleteSchedule(anniId) != 0) {
                assertEquals(0, mysql.queryByID(anniId+"").size());
                anniId = 0;
            }
        }

        if(anniId == 0 && appointId!= 0 && courseId != 0 && interId != 0 && meetingId!= 0 && otherId != 0 && travelId!= 0) {
            from = TimeStampFactory.createStampDayStart(1800 , 1, 1);
            to = TimeStampFactory.createStampDayEnd(1812, 1, 1);
            assertEquals(6, mysql.queryByTime(from.toString() , to.toString()).size());
        }

        if(courseId != 0) {
            CourseItem courseResult = new CourseItem(mysql.queryByID(courseId+""));
            assertEquals(courseItem.getTeacher(), courseResult.getTeacher());
            if(mysql.deleteSchedule(courseId) != 0) {
                assertEquals(0, mysql.queryByID(courseId+"").size());
                courseId = 0;
            }
        }
        /**
         * 至此增删查的简单测试完毕
         * 现在开始增加子待办事项
         * */

        from = TimeStampFactory.createStampDayStart(1800 , 1, 1);
        to = TimeStampFactory.createStampDayEnd(1811, 2 , 2);
        AnniversaryItem anniFather = new AnniversaryItem(from , to , "谢东方" , "周年纪念日" , "生日");
        anniFather.setIsFather(true);
        int fatherId = mysql.addSchedule(anniFather.getAttrs());
        MeetingItem meetingChild = new MeetingItem(from , to ,"到逸夫楼开会", "外出研讨会", "衣服科技咯我" );
        meetingChild.setFatherID(fatherId);
        int childId = mysql.addSchedule(meetingChild.getAttrs());
        if(childId != 0) {
            MeetingItem meetingResult = new MeetingItem(mysql.queryByFatherID(meetingChild.getFatherID()).get(0));
            assertEquals(meetingChild.getLocation() , meetingResult.getLocation());
        }
        else {
            System.out.println("插入子待办事项失败！");
        }


        /**
         * 测试update方法
         * */
        meetingChild.setStatus(2);
        int resultId = mysql.updateState(meetingChild.getID(), 2);
        if(resultId != -1) {
            assertEquals(meetingChild.getStatus() , 2);
        }


    }

    @Test
    public void clearTest() throws DataErrorException {
        Mysql.getInstance().clear();
        TimeStamp from = TimeStampFactory.createStampDayStart(1800 , 1 , 1);
        TimeStamp to = TimeStampFactory.createStampDayEnd(2100 , 12 ,31);
        List<Item> list = ItemManager.getInstance().getItemsByStamp(from , to);
        assertEquals(0 , list.size());
        List<Item> expectedList = new ArrayList<>();
        for (int i = 0; i < Item.ItemType.values().length; i++) {
            expectedList.add(ItemFactory.getDefaultItemByType(Item.ItemType.values()[i], new HashMap<String , String>()));
        }
        long currentMilis = System.currentTimeMillis();
        TimeStamp ts = TimeStampFactory.createStampByMiliseconds(currentMilis);
        ts = TimeStampFactory.createOneHourLater(ts); // 一个小时后的事件，每个事件持续一个小时， 相隔两个小时。
        for (int i = 0 ; i < expectedList.size() ; i++) {
            Item item = expectedList.get(i);
            item.setFrom(ts);
            item.setTo(TimeStampFactory.createOneHourLater(ts));
            ts = TimeStampFactory.createHoursLater(ts , 2);
            System.out.println(ItemManager.getInstance().addItem(item , true));
        }
        List<Item> actualList = ItemManager.getInstance().getItemsByStamp(from , to);
        int size = 0;
        for (int i = 0 ; i < expectedList.size(); i++) {
            if (expectedList.get(i).getItemType() == Item.ItemType.ANNIVERSARY) {
                size += 100;
                continue;
            }
            if (expectedList.get(i).getItemType() == Item.ItemType.COURSE) {
                size += ((CourseItem)expectedList.get(i)).getDuration();
                continue;
            }
            size++;
        }
        assertEquals(size, actualList.size());
    }
}
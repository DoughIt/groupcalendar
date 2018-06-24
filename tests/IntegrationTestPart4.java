import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import exception.DataErrorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import todoitem.*;
import todoitem.itemSub.AnniversaryItem;
import todoitem.itemSub.CourseItem;
import todoitem.itemSub.MeetingItem;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;
import ui.pane.EditPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntegrationTestPart4 {
    @Before
    public void setUp() {
        ItemManager.destroy();
    }

    @After
    public void tearDown() {
        ItemManager.destroy();
    }


    @Test
    public void addTwiceTest() {
        List<Item> list = getTestList();
        for (int i = 0 ; i < list.size(); i++) {
            try {
                ItemManager.getInstance().addItem(list.get(i) , true);
            } catch (DataErrorException e) {
                e.printStackTrace();
                System.out.println("SQL error. Test stopped.");
                return;
            }
        }

        for (int i = 0 ; i < list.size(); i++) {
            try {
                int id = ItemManager.getInstance().addItem(list.get(i) , true);
                list.get(i).setID(id);
            } catch (DataErrorException e) {
                e.printStackTrace();
                System.out.println("add the same item. It should not throw exception. ");
                assertTrue(false);
                return;
            }
        }
        TimeStamp from = TimeStampFactory.createStampDayStart(1800 , 1, 1);
        TimeStamp to = TimeStampFactory.createStampDayEnd(2100 , 12, 31);
        List<Item> actualList = ItemManager.getInstance().getItemsByStamp(from , to );
        for (int i = 0 ; i < list.size(); i++) {
            Item item = list.get(i);
            int expectedNum = 1;
            int actualNum = 0;
            for (Item tmp: actualList) {
                if (tmp.equals(item)) {
                    actualNum++;
                }
            }
            assertEquals(expectedNum , actualNum);
        }
    }

    @Test
    public void finishedByOneClick() {
        // 还需要有状态的设置。
        Item meeting = ItemFactory.getDefaultItemByType(Item.ItemType.MEETING , new HashMap<>());
        TimeStamp from = TimeStampFactory.createStampByMiliseconds(System.currentTimeMillis() - 60 * 1000 * 60);
        TimeStamp to = TimeStampFactory.createHoursLater(from , 24*7);
        meeting.setFrom(from);
        meeting.setTo(to);
        try {
            ItemManager.getInstance().addItem(meeting , true);
        } catch (DataErrorException e) {
            e.printStackTrace();
            System.out.println("Add Father Item failed.Try again later!");
            return;
        }
        TimeStamp ts = new TimeStamp(from.getYear() , from.getMonth(), from.getDay() , from.getHour(),from.getMinute());
        for (int i = 0 ; i < 4 ; i++) {
            Item child = ItemFactory.getDefaultItemByType(Item.ItemType.MEETING, new HashMap<>());
            child.setFrom(ts);
            child.setTo(TimeStampFactory.createOneHourLater(ts));
            ts = TimeStampFactory.createHoursLater(ts, 2);
            child.setIsFather(false);
            child.setFatherID(meeting.getID());
            child.setStatus(i);
            try {
                ItemManager.getInstance().addChildItem(child);
            } catch (DataErrorException e) {
                e.printStackTrace();
                System.out.println("Add child item failed. Test stopped.");
                return;
            }
        }
        // 完成父待办事项。
        ItemManager.getInstance().setCompleted(meeting);
        List<Item> childList = ItemManager.getInstance().getItemsByFatherItem(meeting);
        int expectedFinishedNum = 3;
        int actualFinishedNum = 0;
        int expectedExpiredNum = 1;
        int actualExpiredNum = 0;
        // 有点问题，3是过期，还是
        for (int i = 0; i < childList.size() ; i++) {
            if (childList.get(i).getStatus() == Const.COMPLETED) {
                actualFinishedNum++;
            }
            if (childList.get(i).getStatus() == Const.OVERDUE) {
                actualExpiredNum++;
            }
        }
        assertEquals(expectedExpiredNum , actualExpiredNum);
        assertEquals(expectedFinishedNum , actualFinishedNum);
    }

    public List<Item> getTestList() {
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < Item.ItemType.values().length; i++) {
            list.add(ItemFactory.getDefaultItemByType(Item.ItemType.values()[i], new HashMap<String , String>()));
        }
        TimeStamp ts = TimeStampFactory.createStampDayStart(1800 , 1, 1);
        for (int i = 0 ; i < list.size() ; i++) {
            Item item = list.get(i);
            item.setFrom(ts);
            item.setTo(TimeStampFactory.createOneHourLater(ts));
            if (item.getItemType() == Item.ItemType.ANNIVERSARY) {
                ((AnniversaryItem)item).setStartDay(ts);
                item.setTo(ts);
            }
            ts = TimeStampFactory.createHoursLater(ts , 2);
        }
        return list;
    }


}

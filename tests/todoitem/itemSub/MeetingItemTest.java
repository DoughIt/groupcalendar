package todoitem.itemSub;

import exception.InvalidDateException;
import kernel.CalendarDate;
import org.junit.Test;
import todoitem.ItemManager;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MeetingItemTest {
    @Test
    public void attrTest() throws Exception {
        List<Object[]> list = Arrays.asList(new Object[][]{
                {"2019-1-20", "2020-1-30", "这是一次很重要的会议！", "软件定义世界", "张江高科"},
                {"2020-2-12", "2020-3-1", "这也是一次很重要的会议！", "软件重新定义世界", "高科苑"},
                {"2018-1-21", "2018-1-29", "北京中关村", "深入探讨软件理论", "北京软件研究所"},
        });
        ItemManager itemManager = ItemManager.getInstance();
        ItemManager.destroy();
        ArrayList<MeetingItem> itemArrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CalendarDate fromCal = new CalendarDate((String) list.get(i)[0]);
            TimeStamp from = TimeStampFactory.createStampDayStart(fromCal.getYear(), fromCal.getMonth(), fromCal.getDay());
            CalendarDate toCal = new CalendarDate((String) list.get(i)[1]);
            TimeStamp to = TimeStampFactory.createStampDayEnd(toCal.getYear(), toCal.getMonth(), toCal.getDay());
            MeetingItem tmpItem = new MeetingItem(from, to, (String) list.get(i)[2], (String) list.get(i)[3], (String) list.get(i)[4]);
            assertEquals(tmpItem.getDetailText(), (String) list.get(i)[2]);
            assertEquals(tmpItem.getTopic(), (String) list.get(i)[3]);
            assertEquals(tmpItem.getLocation(), (String) list.get(i)[4]);

            itemManager.addItem(tmpItem, true);
            itemArrayList.add((MeetingItem) tmpItem);
        }
        for (int i = 0; i < list.size(); i++) {
            MeetingItem item = itemArrayList.get(i);
            item.setLocation("修改后的地点");
            item.setTopic("修改后的话题");
            itemManager.addItem(item, true);
        }
        for (int i = 0; i < list.size(); i++) {
            MeetingItem item = (MeetingItem) itemManager.getItemByID(itemArrayList.get(i).getID());
            assertEquals("修改后的地点", item.getLocation());
            assertEquals("修改后的话题", item.getTopic());
        }
    }
}
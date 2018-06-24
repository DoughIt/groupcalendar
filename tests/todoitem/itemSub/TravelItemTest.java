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

public class TravelItemTest {
    @Test
    public void attrTest() throws Exception {
        List<Object []> list = Arrays.asList(new Object[][] {
                {"2018-5-30", "2019-1-2", "plane", "北京", "20", "北京一游"},
                {"2018-1-24", "2018-1-25", "train", "南京", "30", "南京一日游"},
                {"2018-3-5", "2018-3-7", "boat", "上海", "30", "上海两日游"},
        });
        ItemManager itemManager = ItemManager.getInstance();
        ItemManager.destroy();
        ArrayList<TravelItem> itemArrayList = new ArrayList<>();
        for (int i = 0 ; i < list.size() ; i++) {
            CalendarDate fromCal = new CalendarDate((String)list.get(i)[0]);
            TimeStamp from = TimeStampFactory.createStampDayStart(fromCal.getYear(), fromCal.getMonth(), fromCal.getDay());
            CalendarDate toCal = new CalendarDate((String)list.get(i)[1]);
            TimeStamp to = TimeStampFactory.createStampDayEnd(toCal.getYear(),toCal.getMonth(), toCal.getDay());
            TravelItem tmpItem = new TravelItem(from , to , (String)list.get(i)[2], (String)list.get(i)[3],
                    (String)list.get(i)[4], (String)list.get(i)[5]);
            assertEquals(tmpItem.getWay() , (String)list.get(i)[2]);
            assertEquals(tmpItem.getPlace(), (String)list.get(i)[3]);
            assertEquals(tmpItem.getNumber() , (String)list.get(i)[4]);
            assertEquals(tmpItem.getRemark(), (String)list.get(i)[5]);
            itemManager.addItem(tmpItem, true);
            itemArrayList.add((TravelItem) tmpItem);
        }
        for (int i = 0; i < list.size(); i++) {
            TravelItem item = itemArrayList.get(i);
            item.setPlace("修改后的地点");
            item.setWay("修改后的方式");
            itemManager.addItem(item, true);
        }
        for (int i = 0; i < list.size(); i++) {
            TravelItem item = (TravelItem) itemManager.getItemByID(itemArrayList.get(i).getID());
            assertEquals("修改后的地点", item.getPlace());
            assertEquals("修改后的方式", item.getWay());
        }
    }

}
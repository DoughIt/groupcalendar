package todoitem.itemSub;

import exception.InvalidDateException;
import kernel.CalendarDate;
import org.junit.Test;
import todoitem.Item;
import todoitem.ItemManager;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AnniversaryItemTest {
    @Test
    public void attrTest() throws Exception {
        List<Object[]> list = Arrays.asList(new Object[][]{
                {"2018-7-6", "2018-9-1", "名字", "总算放假了！", "放假纪念日"},
                {"2025-10-1", "2028-9-1", "名字", "啊！结婚了！呵呵！", "结婚纪念日"},
                {"2099-10-3", "2099-10-4", "名字", "我去世了！呵呵！", "逝世纪念日"},
                {"2018-9-10", "2029-10-4", "名字", "灾难性事件： 开学了，，，", "灾难纪念日"}
        });
        ItemManager itemManager = ItemManager.getInstance();
        ItemManager.destroy();
        ArrayList<AnniversaryItem> itemArrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CalendarDate fromCal = new CalendarDate((String) list.get(i)[0]);
            TimeStamp from = TimeStampFactory.createStampDayStart(fromCal.getYear(), fromCal.getMonth(), fromCal.getDay());
            CalendarDate toCal = new CalendarDate((String) list.get(i)[1]);
            TimeStamp to = TimeStampFactory.createStampDayEnd(toCal.getYear(), toCal.getMonth(), toCal.getDay());
            Item tmpItem = new AnniversaryItem(from, to, list.get(i)[2].toString(), (String) list.get(i)[2], (String) list.get(i)[3]);
            assertEquals(((AnniversaryItem) tmpItem).getAnniversaryType(), list.get(i)[3]);
            assertEquals(((AnniversaryItem) tmpItem).getStartDay(), from);

            itemManager.addItem(tmpItem, true);
            itemArrayList.add((AnniversaryItem) tmpItem);
        }
        for (int i = 0; i < list.size(); i++) {
            AnniversaryItem item = itemArrayList.get(i);
            item.setName("修改后的名字");
            item.setDetailText("修改后的内容");
            itemManager.addItem(item, true);
        }
        for (int i = 0; i < list.size(); i++) {
            AnniversaryItem item = (AnniversaryItem) itemManager.getItemByID(itemArrayList.get(i).getID());
            assertEquals("修改后的名字", item.getName());
            assertEquals("修改后的内容", item.getDetailText());
        }
    }
}
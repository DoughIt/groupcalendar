package todoitem.itemSub;

import exception.InvalidDateException;
import kernel.CalendarDate;
import org.junit.Test;
import todoitem.Item;
import todoitem.ItemManager;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OtherItemTest {
    @Test
    public void attrTest() throws Exception {
        List<Object []> list = Arrays.asList(new Object[][]{
                {"2018-5-20" , "2018-5-20" , "和c某某约会"} ,
                {"2018-5-12" , "2018-5-12" , "和s某某约会"},
                {"2018-5-18" , "2018-5-18" , "和m某某约会"}
        });
        ItemManager itemManager = ItemManager.getInstance();
        ItemManager.destroy();
        ArrayList<OtherItem> itemArrayList = new ArrayList<>();
        for(int i = 0 ; i < list.size() ; i++) {
            CalendarDate fromCal = new CalendarDate((String)list.get(i)[0]);
            TimeStamp from = TimeStampFactory.createStampDayStart(fromCal.getYear(), fromCal.getMonth(), fromCal.getDay());
            CalendarDate toCal = new CalendarDate((String)list.get(i)[1]);
            TimeStamp to = TimeStampFactory.createStampDayEnd(toCal.getYear(),toCal.getMonth(), toCal.getDay());
            OtherItem tmpItem = new OtherItem(from , to , (String)list.get(i)[2]);
            assertEquals(tmpItem.getDetailText(),(String)list.get(i)[2]);
            itemManager.addItem(tmpItem, true);
            itemArrayList.add((OtherItem) tmpItem);
        }
        for (int i = 0; i < list.size(); i++) {
            OtherItem item = itemArrayList.get(i);
            item.setDetailText("修改后的内容");
            itemManager.addItem(item, true);
        }
        for (int i = 0; i < list.size(); i++) {
            OtherItem item = (OtherItem) itemManager.getItemByID(itemArrayList.get(i).getID());
            assertEquals("修改后的内容", item.getDetailText());
        }
    }
}
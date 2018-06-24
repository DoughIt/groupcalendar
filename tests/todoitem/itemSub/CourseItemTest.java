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

public class CourseItemTest {
    @Test
    public void attrTest() throws Exception {
        ItemManager itemManager = ItemManager.getInstance();
        ItemManager.destroy();
        List<Object[]> list = Arrays.asList(new Object[][]{
                {"2018-1-1", "2018-1-2", "离散数学", "要上课了！", "谢老师",
                        "这门课很重要！", "第二教学楼", "8", "3"},
                {"2018-1-3", "2018-1-4", "软件设计", "又要上课了！", "张老师",
                        "这门课也很重要！", "第八教学楼", "10", "5"},
                {"2018-1-5", "2018-1-6", "深入理解", "又又又又要上课了！", "谢老师",
                        "这门课也特别重要！", "第十教学楼", "20", "6"}
        });
        List<CourseItem> courseItemList = new ArrayList<>();
        /**
         * detailText 和remark 重复了。
         * */
        for (int i = 0; i < list.size(); i++) {
            CalendarDate fromCal = new CalendarDate((String) list.get(i)[0]);
            TimeStamp from = TimeStampFactory.createStampDayStart(fromCal.getYear(), fromCal.getMonth(), fromCal.getDay());
            CalendarDate toCal = new CalendarDate((String) list.get(i)[1]);
            TimeStamp to = TimeStampFactory.createStampDayEnd(toCal.getYear(), toCal.getMonth(), toCal.getDay());
            CourseItem itemTmp = new CourseItem(from, to, (String) list.get(i)[2], (String) list.get(i)[3],
                    (String) list.get(i)[4], (String) list.get(i)[5], (String) list.get(i)[6], (String) list.get(i)[7],
                    (String) list.get(i)[8]);
            assertEquals(itemTmp.getName(), (String) list.get(i)[2]);
            assertEquals(itemTmp.getDetailText(), (String) list.get(i)[3]);
            assertEquals(itemTmp.getTeacher(), (String) list.get(i)[4]);
            assertEquals(itemTmp.getRemark(), (String) list.get(i)[5]);
            assertEquals(itemTmp.getPlace(), (String) list.get(i)[6]);
            assertEquals(itemTmp.getDuration(), Integer.parseInt((String) list.get(i)[7]));

            itemManager.addItem(itemTmp, true);
            courseItemList.add(itemTmp);
        }

        for (int i = 0; i < list.size(); i++) {
            CourseItem itemTmp = courseItemList.get(i);
            itemTmp.setTeacher("陈老师");
            itemTmp.setPlace("Z2101");
            itemTmp.setDetailText("内容已经修改");
            itemManager.addItem(itemTmp, true);
        }
        for (int i = 0; i < list.size(); i++) {
            CourseItem itemTmp = (CourseItem) itemManager.getItemByID(courseItemList.get(i).getID());
            assertEquals("陈老师", itemTmp.getTeacher());
            assertEquals("Z2101", itemTmp.getPlace());
            assertEquals("内容已经修改", itemTmp.getDetailText());
        }
    }
}
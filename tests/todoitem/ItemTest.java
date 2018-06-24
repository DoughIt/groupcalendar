package todoitem;

import kernel.CalendarDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import todoitem.itemSub.AnniversaryItem;
import todoitem.itemSub.AppointmentItem;
import todoitem.itemSub.MeetingItem;
import todoitem.itemSub.OtherItem;
import todoitem.util.TimeStamp;
import todoitem.util.TimeStampFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ItemTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Class ItemTest tests begin! Good Luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class TimeStamp tests end! Are you satisfied?");
    }

    @Test
    public void NullItemInstance() {
        TimeStamp from = new TimeStamp(2018, 5, 11, 0, 0);
        TimeStamp to = new TimeStamp(2018, 5, 10, 0, 0); //非法时间段，不应该创建待办事项实例
        Item item = null;
        try {
            item = new OtherItem(from, to, "");
        } catch (Exception e) {
            System.out.println();
        }
        assertNull(item);
    }

    @Test
    public void isDuringTime() throws Exception {
        List<TimeStamp> fromCases = new ArrayList<TimeStamp>() {
            {
                add(new TimeStamp(2016, 2, 29, 0, 0));
                add(new TimeStamp(2013, 1, 31, 16, 6));
                add(new TimeStamp(2018, 6, 30, 5, 42));
            }
        };
        List<TimeStamp> toCases = new ArrayList<TimeStamp>() {
            {
                add(new TimeStamp(2016, 2, 29, 6, 31));
                add(new TimeStamp(2014, 1, 31, 16, 6));
                add(new TimeStamp(2018, 6, 30, 5, 43));
            }
        };
        List<Item> itemsDuringThisPeriod = new ArrayList<Item>() {
            {
                add(new AppointmentItem(new TimeStamp(2016, 2, 29, 0, 1),
                        new TimeStamp(2016, 2, 29, 0, 2),
                        "", "", ""));

                add(new AppointmentItem(new TimeStamp(2013, 1, 31, 23, 1),
                        new TimeStamp(2014, 1, 31, 16, 6),
                        "", "", ""));

                add(new AppointmentItem(new TimeStamp(2018, 6, 30, 5, 42),
                        new TimeStamp(2018, 6, 30, 5, 43),
                        "", "", ""));
            }
        };
        for (int i = 0; i < fromCases.size(); i++) {
            assertTrue(itemsDuringThisPeriod.get(i).isDuringTime(fromCases.get(i), toCases.get(i)));
        }
    }

    @Test
    public void setFrom() throws Exception {
        Item item = new MeetingItem(new TimeStamp(2016, 2, 29, 0, 1),
                new TimeStamp(2016, 2, 29, 0, 2),
                "", "", "");
        TimeStamp timeStamp = new TimeStamp(2018, 4, 14, 20, 25);
        item.setFrom(timeStamp);
        assertEquals(timeStamp, item.getFrom());
    }

    @Test
    public void setTo() throws Exception {
        Item item = new OtherItem(new TimeStamp(2016, 2, 29, 0, 1),
                new TimeStamp(2016, 2, 29, 0, 2), "");
        TimeStamp timeStamp = new TimeStamp(2018, 4, 14, 20, 25);
        item.setTo(timeStamp);
        assertEquals(timeStamp, item.getTo());
    }

    @Test
    public void setDetailText() throws Exception {
        Item item = new OtherItem(new TimeStamp(2016, 2, 29, 0, 1),
                new TimeStamp(2016, 2, 29, 0, 2), "");
        String detailText = "HANG ON!";
        item.setDetailText(detailText);
        assertEquals(detailText, item.getDetailText());
    }

    @Test
    public void setItemType() throws Exception {
        Item item = new OtherItem(new TimeStamp(2016, 2, 29, 0, 1),
                new TimeStamp(2016, 2, 29, 0, 2), "");
        Item.ItemType type = Item.ItemType.DATE;
        item.setItemType(Item.ItemType.DATE);
        assertEquals(type, item.getItemType());
    }


    @Test
    public void promptRelatedTest() throws Exception {
        List<Object[]> list = Arrays.asList(new Object[][]{
                {"2018-2-28", "2018-3-1", "no details",
                        Const.PRIORITY, Const.STATUS, Const.IS_FATHER,
                        Const.PROMPT_STATUS, Const.MINUTES_AHEAD, Const.SHOW_ON_STAGE, Const.MINUTES_DELTA}, // 提示默认值
                {"2000-2-29", "2018-2-28", "no details",
                        Const.PRIORITY, Const.STATUS, Const.IS_FATHER,
                        Const.PROMPT_STATUS, Const.MINUTES_AHEAD, Const.SHOW_ON_STAGE, Const.MINUTES_DELTA}, // 提示默认值
                {"2020-2-29", "2028-4-1", "no details",
                        Const.PRIORITY, Const.STATUS, Const.IS_FATHER,
                        true, Const.MINUTES_AHEAD, Const.SHOW_ON_STAGE, Const.MINUTES_DELTA}, // 提示状态为真
                {"2020-2-29", "2028-4-1", "no details",
                        Const.PRIORITY, Const.STATUS, Const.IS_FATHER,
                        true, MinutesAheadType.ONEDAY.getMinutes(), Const.SHOW_ON_STAGE, Const.MINUTES_DELTA}, // 提前一天提示
                {"2020-2-29", "2028-4-1", "no details",
                        Const.PRIORITY, Const.STATUS, Const.IS_FATHER,
                        true, MinutesAheadType.ONEWEEK.getMinutes(), false, Const.MINUTES_DELTA}, // 弹出框显示为真
                {"2020-2-29", "2028-4-1", "no details",
                        Const.PRIORITY, Const.STATUS, Const.IS_FATHER,
                        true, MinutesAheadType.ONEWEEK.getMinutes(), false, MinutesDeltaType.ONEHOUR.getMinutes()}, // 每隔一小时显示
        });
        for (int i = 0; i < list.size(); i++) {
            CalendarDate fromCal = new CalendarDate(((String) list.get(i)[0]));
            TimeStamp from = TimeStampFactory.createStampDayStart(fromCal.getYear(), fromCal.getMonth(), fromCal.getDay());
            CalendarDate toCal = new CalendarDate(list.get(i)[1].toString());
            TimeStamp to = TimeStampFactory.createStampDayEnd(toCal.getYear(), toCal.getMonth(), toCal.getDay());
            Item itemTmp = new OtherItem(from, to, list.get(i)[2].toString(),
                    ((Integer) list.get(i)[3]).intValue(), ((Integer) list.get(i)[4]).intValue(), ((Boolean) list.get(i)[5]).booleanValue(),
                    ((Boolean) list.get(i)[6]).booleanValue(), ((Long) list.get(i)[7]).longValue(),
                    ((Boolean) list.get(i)[8]).booleanValue(), ((Long) list.get(i)[9]).longValue());
            assertTrue(itemTmp.promptStatus() == ((Boolean) list.get(i)[6]).booleanValue());
            assertTrue(itemTmp.minutesAhead() == ((Long) list.get(i)[7]).longValue());
            assertTrue(itemTmp.showOnStage() == ((Boolean) list.get(i)[8]).booleanValue());
            assertTrue(itemTmp.minutesDelta() == ((Long) list.get(i)[9]).longValue());
        }
    }

    @Test
    public void statusPriorityFatherRelatedTest() throws Exception {
        List<Object[]> list = Arrays.asList(new Object[][]{
                {"2018-2-28", "2018-3-1", "name", "no details", "生日",
                        Const.PRIORITY, Const.BEFORE_BEGINNING, Const.IS_FATHER}, // 优先级： 4， 完成状态： 未开始，父类型： 真
                {"2000-2-29", "2018-2-28", "name", "no details", "生日",
                        2, Const.IN_PROGRESS, !Const.IS_FATHER}, // 优先级： 2 ， 完成状态：进行中， 父类型： 假
                {"2020-2-29", "2028-4-1", "name", "no details", "生日",
                        1, Const.BEFORE_BEGINNING, Const.IS_FATHER}, // 优先级： 1， 完成状态： 未开始 ， 父类型： 真
                {"2020-2-29", "2028-4-1", "name", "no details", "生日",
                        3, Const.COMPLETED, !Const.IS_FATHER}, // 优先级： 3 ， 完成状态：完成， 父类型： 假
                {"2020-2-29", "2028-4-1", "name", "no details", "生日",
                        4, Const.OVERDUE, Const.IS_FATHER}, // 优先级： 4， 完成状态： 过期， 父类型： 真
                {"2020-2-29", "2028-4-1", "name", "no details", "生日",
                        2, Const.OVERDUE, !Const.IS_FATHER}, // 优先级： 2 ， 完成状态： 过期， 父类型：假
        });
        for (int i = 0; i < list.size(); i++) {
            CalendarDate fromCal = new CalendarDate(((String) list.get(i)[0]));
            TimeStamp from = TimeStampFactory.createStampDayStart(fromCal.getYear(), fromCal.getMonth(), fromCal.getDay());
            CalendarDate toCal = new CalendarDate(list.get(i)[1].toString());
            TimeStamp to = TimeStampFactory.createStampDayEnd(toCal.getYear(), toCal.getMonth(), toCal.getDay());
            Item itemTmp = new AnniversaryItem(from, to, list.get(i)[2].toString(), list.get(i)[3].toString(), ((String) list.get(i)[4]),
                    ((Integer) list.get(i)[5]).intValue(), ((Integer) list.get(i)[6]).intValue(), ((Boolean) list.get(i)[7]).booleanValue());
            assertEquals(itemTmp.getPriority(), ((Integer) list.get(i)[5]).intValue());
            assertEquals(itemTmp.getStatus(), ((Integer) list.get(i)[6]).intValue());
            assertEquals(itemTmp.isFather(), ((Boolean) list.get(i)[7]).booleanValue());
        }
    }

    @Test
    public void equalsTest() {
        for(int i = 0 ; i < Item.ItemType.values().length ; i++) {
            Item item = ItemFactory.getDefaultItemByType(Item.ItemType.values()[i] , new HashMap<String , String>());
            assertEquals(item , item);
        }
    }


}
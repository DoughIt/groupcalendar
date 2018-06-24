package kernel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Class DateUtil tests begin! Good Luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class DateUtil tests end! Are you satisfied?");
    }

    @Test
    public void getDaysInMonthNull() {
        assertNull(DateUtil.getDaysInMonth(null));
    }

    @Test
    public void getDaysInMonthNotNull() {
        CalendarDate date = new CalendarDate(2018, 4, 14);
        List<CalendarDate> actualList = DateUtil.getDaysInMonth(date);
        List<CalendarDate> expectedList = new ArrayList<CalendarDate>();

        for (int i = 1; i <= 30; i++) {
            expectedList.add(new CalendarDate(2018, 4, i));
        }
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 1; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void testGetDaysInMonthIllegal() {
        CalendarDate date1 = new CalendarDate(2018, 2, 29);
        List<CalendarDate> actualList1 = DateUtil.getDaysInMonth(date1);
        assertNull(actualList1);

        CalendarDate date2 = new CalendarDate(2018, 22, 3);
        List<CalendarDate> actualList2 = DateUtil.getDaysInMonth(date2);
        assertNull(actualList2);

        CalendarDate date3 = new CalendarDate(2018, 3, 100);
        List<CalendarDate> actualList3 = DateUtil.getDaysInMonth(date3);
        assertNull(actualList3);

        CalendarDate date4 = new CalendarDate(2019, 33, 42);
        List<CalendarDate> actualList4 = DateUtil.getDaysInMonth(date4);
        assertNull(actualList4);
    }

    @Test
    public void Integration() {
        List<List<CalendarDate>> generatedCalendar = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            List<CalendarDate> tmpList = new ArrayList<>();
            for (int j = 1; j <= 31; j++) {
                tmpList.add(new CalendarDate(2018, i, j));
            }
            generatedCalendar.add(tmpList);
        }
        for (int i = 1; i <= 20; i++) {
            List<CalendarDate> tmpList = generatedCalendar.get(i - 1);
            for (int j = 1; j <= 31; j++) {
                CalendarDate actualDate = tmpList.get(j - 1);
                if (DateUtil.isConsidered(actualDate)) {
                    assertEquals(new CalendarDate(2018, i, j), actualDate);
                } else {
                    assertNull(DateUtil.getDaysInMonth(actualDate));
                }
            }
        }
    }

    @Test
    public void testIsValidNull() {
        assertFalse(DateUtil.isValid(null));
    }

    @Test
    public void testIsValidTrue() {
        CalendarDate date = new CalendarDate(2018, 4, 1);
        assertTrue(DateUtil.isValid(date));
    }

    @Test
    public void testIsValidFalse() {
        CalendarDate date = new CalendarDate(1900, 2, 29);
        assertFalse(DateUtil.isValid(date));
    }

    @Test
    public void testIsFormattedNull() {
        assertFalse(DateUtil.isFormatted(null));
    }

    @Test
    public void testIsFormattedTrue() {
        String dateStr = "2018-22-1";
        assertTrue(DateUtil.isFormatted(dateStr));
    }

    @Test
    public void testIsFormattedFalse() {
        String dateStr1 = "2018-222-111";
        assertFalse(DateUtil.isFormatted(dateStr1));

        String dateStr2 = "2018/1-1";
        assertFalse(DateUtil.isFormatted(dateStr2));

        String dateStr3 = "2018-1";
        assertFalse(DateUtil.isFormatted(dateStr3));

        String dateStr4 = "2018-1-1sss";
        assertFalse(DateUtil.isFormatted(dateStr4));

        String dateStr5 = "sss2018-1-1";
        assertFalse(DateUtil.isFormatted(dateStr5));

        //完善isFormatted测试集
        assertFalse(DateUtil.isFormatted(null));
        assertFalse(DateUtil.isFormatted(""));
        assertFalse(DateUtil.isFormatted("0000-00-00"));
        assertFalse(DateUtil.isFormatted("0000-1-1"));
        assertFalse(DateUtil.isFormatted("0000-0-1"));
        assertFalse(DateUtil.isFormatted("2018-00-1"));
        assertFalse(DateUtil.isFormatted("2018-01-00"));
    }

    @Test
    public void testIsLeapYearTrue() {
        assertTrue(DateUtil.isLeapYear(2000));
        assertTrue(DateUtil.isLeapYear(2012));
    }

    @Test
    public void testIsLeapYearFalse() {
        assertFalse(DateUtil.isLeapYear(1999));
        assertFalse(DateUtil.isLeapYear(1900));
    }
}
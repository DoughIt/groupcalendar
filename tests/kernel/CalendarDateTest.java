package kernel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CalendarDateTest {

    @org.junit.Before
    public void setUp() throws Exception {
        System.out.println("Class CalendarDate tests begin! Good luck!");
    }

    @org.junit.After
    public void tearDown() throws Exception {
        System.out.println("Class CalendarDate tests end! Are you satisfied?");
    }

    @org.junit.Test
    public void getDayOfWeekTrue() {
        CalendarDate date = new CalendarDate(2018, 4, 2);
        int actual = date.getDayOfWeek();
        assertEquals(1, actual);
        assertEquals(1, new CalendarDate(2018, 1, 1).getDayOfWeek());
    }

    @org.junit.Test
    public void testGetDayOfWeekFalse() {
        CalendarDate date1 = new CalendarDate(2018, 4, 2);
        int actual1 = date1.getDayOfWeek();
        assertNotEquals(6, actual1);

        CalendarDate date2 = new CalendarDate(2018, 2, 29);
        int actual2 = date2.getDayOfWeek();
        assertEquals(-1, actual2);
    }
}
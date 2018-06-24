package kernel;/*
* This class provides some utils that may help you to finish this lab.
* getToday() is finished, you can use this method to get the current date.
* The other four methods getDaysInMonth(), isValid(), isFormatted() and isLeapYear() are not finished,
* you should implement them before you use.
*
* */


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class DateUtil {
    private static CalendarDate today;

    /**
     * get a kernel.CalendarDate instance point to today
     *
     * @return a kernel.CalendarDate object
     */
    public static CalendarDate getToday() {
        if (today == null) {
            Calendar calendar = Calendar.getInstance();
            today = new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH));
        }
        return today;
    }

    /**
     * get all dates in the same month with given date
     *
     * @param date the given date
     * @return a list of days in a whole month
     */
    public static List<CalendarDate> getDaysInMonth(CalendarDate date) {
        if (!isConsidered(date))
            return null;
        int num = getNumOfDays(date);
        int current = date.getDay();
        List<CalendarDate> calendarList = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            if (i == current) {
                calendarList.add(date);
                continue;
            }
            calendarList.add(new CalendarDate(date.getYear(), date.getMonth(), i));
        }
        return calendarList;
    }

    public static List<CalendarDate> getHeadOfCalendar(CalendarDate date) {
        if (!isConsidered(date))
            return null;
        List<CalendarDate> list = new ArrayList<>();
        int year = date.getYear();
        int month = date.getMonth();
        int daysOfLastMonth;
        if (month == 1) {
            year--;
            month = 12;
            daysOfLastMonth = 31;
        } else {
            month--;
            daysOfLastMonth = getNumOfDays(new CalendarDate(year, month, 1));
        }
        int w = new CalendarDate(date.getYear(), date.getMonth(), 1).getDayOfWeek() % 7;
        int index = daysOfLastMonth - w;
        for (int i = daysOfLastMonth; i > index; i--)
            list.add(0, new CalendarDate(year, month, i));
        return list;
    }

    public static List<CalendarDate> getTailOfCalendar(CalendarDate date, int index) {
        if (!isConsidered(date))
            return null;
        List<CalendarDate> list = new ArrayList<>();
        int year = date.getYear();
        int month = date.getMonth();
        if (month == 12) {
            year++;
            month = 1;
        } else
            month++;
        for (int i = index, j = 1; i < 42; i++, j++)
            list.add(new CalendarDate(year, month, j));

        return list;
    }

    private static int getNumOfDays(CalendarDate date) {
        MonthType monthType = MonthType.values()[date.getMonth()];
        if ((isLeapYear(date.getYear())) && (monthType == MonthType.FEB)) {
            return 29;
        }
        return monthType.getDays();
    }

    /**
     * Judge whether the input date is valid. For example, 2018-2-31 is not valid
     *
     * @param date the input date
     * @return true if the date is valid, false if the date is not valid.
     */
    public static boolean isValid(CalendarDate date) {
//        if ((date.getYear() < 1800) || (date.getYear() > 2100)) {
//            return false;
//        }
        return (date != null && date.getMonth() >= 1 && date.getMonth() <= 12
                && date.getDay() > 0 && date.getDay() <= getNumOfDays(date));
    }

    public static boolean isConsidered(CalendarDate date) {
        return isValid(date) && date.getYear() >= 1800 && date.getYear() <= 2300;
    }

    /**
     * Judge whether the input is formatted.
     * For example, 2018/2/1 is not valid and 2018-2-1 is valid.
     *
     * @param dateString 日期字符串
     * @return true if the input is formatted, false if the input is not formatted.
     */
    static boolean isFormatted(String dateString) {
        return dateString != null && Pattern.matches("^\\d{1,4}-\\d{1,2}-\\d{1,2}$", dateString) &&
                Pattern.matches(".*[1-9].*-.*[1-9].*-.*[1-9].*", dateString);
    }

    /**
     * Judge whether the input year is a leap year or not.
     * For example, year 2000 is a leap year, and 1900 is not.
     *
     * @param year 年份
     * @return true if the input year is a leap year, false if the input is not.
     */
    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

    public enum MonthType {
        NULL(0),
        JAN(31), FEB(28), MAR(31), APR(30),
        MAY(31), JUN(30), JUL(31), AUG(31),
        SEPT(30), OCT(31), NOV(30), DEC(31);
        private final int days;

        MonthType(int days) {
            this.days = days;
        }

        public int getDays() {
            return days;
        }
    }

    public enum DayType {
        SUN("Sun"), MON("Mon"), TUES("Tues"), WEDN("Wed"),
        THUR("Thur"), FRI("Fri"), SAT("Sat");
        private final String printMark;

        DayType(String printMark) {
            this.printMark = printMark;
        }

        public String getPrintMark() {
            return printMark;
        }

    }

}


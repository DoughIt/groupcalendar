package todoitem.util;

import exception.InvalidDateException;
import kernel.CalendarDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeStampFactory {
    public static TimeStamp createStampDayStart(int year, int month, int day) {
        return new TimeStamp(year, month, day, 0, 0);
    }

    public static TimeStamp createStampDayEnd(int year, int month, int day) {
        return new TimeStamp(year, month, day, 23, 59);
    }

    /**
     * 输入格式： yyyy-mm-dd hh:mm
     * */
    public static TimeStamp createStampByString(String stampString) {
        String msg[] = stampString.trim().split(" ");
        try {
            CalendarDate date = new CalendarDate(msg[0]);
            String timeMsg[] = msg[1].trim().split(":");
            return new TimeStamp(date.getYear(), date.getMonth(), date.getDay(), Integer.parseInt(timeMsg[0]), Integer.parseInt(timeMsg[1]));
        } catch (InvalidDateException e) {
            return null;
        }
    }

    public static TimeStamp createStampByMiliseconds(long milis) {
        Date date = new Date();
        date.setTime(milis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return createStampByString(format.format(date));
    }

    public static TimeStamp createStampByMinutes(long minutes) {
        long milis = minutes * (60 * 1000);
        return createStampByMiliseconds(milis);
    }

    public static TimeStamp createOneHourLater(TimeStamp ts) {
        if(ts.getHour() < 23) {
            return new TimeStamp(ts.getYear(), ts.getMonth(), ts.getDay(), ts.getHour()+1, ts.getMinute());
        }
        else {
            Calendar cal = Calendar.getInstance();
            cal.set(ts.getYear() , ts.getMonth()-1, ts.getDay(), ts.getHour(),ts.getMinute());
            cal.add(Calendar.HOUR, 1);
            Date date = cal.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return TimeStampFactory.createStampByString(format.format(date));
        }
    }

    public static TimeStamp createHoursLater(TimeStamp ts, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.set(ts.getYear() , ts.getMonth()-1, ts.getDay(), ts.getHour(),ts.getMinute());
        cal.add(Calendar.HOUR, hours);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return TimeStampFactory.createStampByString(format.format(date));
    }

    public static TimeStamp createOneDayLater(TimeStamp ts) {
            Calendar cal = Calendar.getInstance();
            cal.set(ts.getYear() , ts.getMonth()-1, ts.getDay(), ts.getHour(),ts.getMinute());
            cal.add(Calendar.HOUR, 24);
            Date date = cal.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return TimeStampFactory.createStampByString(format.format(date));
    }
}

package todoitem.util;


import kernel.CalendarDate;
import kernel.DateUtil;

import java.io.Serializable;
import java.sql.Timestamp;

public class TimeStamp implements Serializable {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public TimeStamp(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    /**
     * in practice, we find it not so useful. Therefore, we add setDay method;
     */
    public void changeTo(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public void setDay(int day) {
        this.day = day;
    }


    /**
     * 2018.1.1 9:30 is after 2018.1.1 9:30
     */
    public boolean isAfter(TimeStamp target) {
        if (target == null)
            return false;
        if (year < target.getYear()) {
            return false;
        }
        if (year > target.getYear()) {
            return true;
        }

        // year equal
        if (month < target.getMonth()) {
            return false;
        }
        if (month > target.getMonth()) {
            return true;
        }
        // month equal
        if (day < target.getDay()) {
            return false;
        }
        if (day > target.getDay()) {
            return true;
        }
        // day equal
        if (hour < target.getHour()) {
            return false;
        }
        if (hour > target.getHour()) {
            return true;
        }
        // hour equal
        if (minute < target.getMinute()) {
            return false;
        }
        if (minute > target.getMinute()) {
            return true;
        }
        // minute equal
        return true;
    }

    /**
     * 2018.1.1 9:30 is before 2018.1.1 9:30
     */
    public boolean isBefore(TimeStamp target) {// this <= target
        if (target == null)
            return false;
        if (year < target.getYear()) {
            return true;
        }
        if (year > target.getYear()) {
            return false;
        }
        // year equal
        if (month < target.getMonth()) {
            return true;
        }
        if (month > target.getMonth()) {
            return false;
        }
        // month equal
        if (day < target.getDay()) {
            return true;
        }
        if (day > target.getDay()) {
            return false;
        }
        // day equal
        if (hour < target.getHour()) {
            return true;
        }
        if (hour > target.getHour()) {
            return false;
        }
        // hour equal
        if (minute < target.getMinute()) {
            return true;
        }
        if (minute > target.getMinute()) {
            return false;
        }
        // minute equal
        return true;
    }

    public boolean isValid() {
        CalendarDate date = new CalendarDate(year, month, day);
        if (!DateUtil.isConsidered(date)) {
            return false;
        }
        if (hour < 0 || hour >= 24) {
            return false;
        }
        if (minute < 0 || minute >= 60) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object timeStamp) {
        TimeStamp tmp = (TimeStamp) timeStamp;
        return (year == tmp.getYear() && month == tmp.getMonth() && day == tmp.getDay() && hour == tmp.getHour() && minute == tmp.getMinute());
    }

    /**
     * 此方法返回自1970年依赖的分钟数
     * */
    public long getMinutes() {
        String timeStr = String.format("%04d-%02d-%02d %02d:%02d:00", year,month,day,hour,minute);
        Timestamp sysTimeStamp = Timestamp.valueOf(timeStr);
        long deltaMinutes = sysTimeStamp.getTime() / (1000 * 60);
        return deltaMinutes;
    }


    /**
     * 计算两者相差的时间，用分钟表示。
     * this - timeStamp
     * */
    public long delta(TimeStamp timeStamp) {
        return this.getMinutes() - timeStamp.getMinutes();
    }

    @Override
    public String toString() {
        return "" + year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }
    public String getStringWithoutHour(){
        return year + "-" + month + "-" + day;
    }
    public String getStringWithoutDay(){
        return hour+":"+minute;
    }
}

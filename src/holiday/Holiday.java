package holiday;

import java.util.Calendar;

public class Holiday {
    private String name;
    private String zh_name;
    private Calendar holidayTime;
    private Calendar startTime;
    private Calendar endTime;

    public Holiday(String name, String zh_name, String holidayTime, String startTime, String endTime) {
        this.name = name;
        this.zh_name = zh_name;
        this.holidayTime = strToCal(holidayTime);
        this.startTime = strToCal(startTime);
        this.endTime = strToCal(endTime);
    }

    public String getName() {
        return name;
    }

    public String getZh_name() {
        return zh_name;
    }

    public Calendar getHolidayTime() {
        return holidayTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public static Calendar strToCal(String str){
        String strArray[] = str.split("-");
        int year = Integer.valueOf(strArray[0]);
        int month = Integer.valueOf(strArray[1]);
        int day = Integer.valueOf(strArray[2]);
        Calendar cal = Calendar.getInstance();
        cal.set(year,month-1,day);
        return cal;
    }
    public String getDetail(){
        return name + " " + zh_name + " " + DayManager.print(holidayTime) + " " +  DayManager.print(startTime) + " " + DayManager.print(endTime);
    }
}

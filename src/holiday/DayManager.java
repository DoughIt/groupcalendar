package holiday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;


public class DayManager {
    private ArrayList<Holiday> holidays;
    private ArrayList<Calendar> workdays;
    private final static DayManager dayManager = new DayManager();

    private DayManager() {
        holidays = new ArrayList<Holiday>();
        workdays = new ArrayList<Calendar>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("holiday.json"));// 读取原始json文件
            String line, s;
            s = "";
            while ((line = br.readLine()) != null) {
                s += line;
            }
            try {
                JSONObject dataJson = new JSONObject(s);// 创建一个包含原始json串的json对象
                JSONArray holidayJson = dataJson.getJSONArray("holiday");// 找到holiday的json数组
                for (int i = 0; i < holidayJson.length(); i++) {
                    JSONObject info = holidayJson.getJSONObject(i);// 获取第i个节日
                    String name = info.getString("name");// 读取name字段值
                    String zh_name = info.getString("zh_name");//读取zh_name字段
                    String holiday_time = info.getString("holiday_time");
                    String start_time = info.getString("start_time");
                    String end_time = info.getString("end_time");
                    holidays.add(new Holiday(name, zh_name, holiday_time, start_time, end_time));
//                    System.out.println(name + " " + zh_name + " " + holiday_time + " " + start_time + " " + end_time + " "  );
                }
                JSONArray workdayJson = dataJson.getJSONArray("workday");// 找到holiday的json数组
                for (int i = 0; i < workdayJson.length(); i++) {
                    String info = workdayJson.getString(i);// 获取第i个节日
                    //System.out.println(info);
                    workdays.add(Holiday.strToCal(info));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 调用日期格式必须是 yyyy-mm-dd.
     * the date format should be yyyy-mm-dd.
     * */
    public static DayType isFestival(String str) {
        Calendar cal = Holiday.strToCal(str);
        for (int i = 0; i < dayManager.holidays.size(); i++)
            if (areSameDay(cal, dayManager.holidays.get(i).getHolidayTime())) {
                DayType dayType = DayType.HOLIDAY;
                dayType.setName(dayManager.holidays.get(i).getZh_name());
                return dayType;
            }
        return null;
    }

    public static DayType isWorkDay(String str) {
        Calendar cal = Holiday.strToCal(str);
        for (int i = 0; i < dayManager.workdays.size(); i++) {
            if (areSameDay(cal, dayManager.workdays.get(i))) {
                DayType dayType = DayType.WORKDAY;
                dayType.setName("班");
                return dayType;
            }
        }
        return null;
    }

    public static DayType isRest(String str) {
        Calendar cal = Holiday.strToCal(str);
        for (int i = 0; i < dayManager.holidays.size(); i++) {
            if (before(cal, dayManager.holidays.get(i).getEndTime()) && after(cal, dayManager.holidays.get(i).getStartTime())) {
                DayType dayType = DayType.REST;
                dayType.setName("休");
                return dayType;
            }
        }
        return null;
    }

    //如果是节假日当天，返回枚举类型节假日/工作/休息/正常。
    public static DayType judgeDays(String str) {
        DayType dayType;
        //判断是否为工作
        if ((dayType = isWorkDay(str)) != null) {
            return dayType;
        }
        //判断是否为节假日当天
        if ((dayType = isFestival(str)) != null) {
            return dayType;
        }
        //判断是否为节假日中的休息
        if ((dayType = isRest(str)) != null) {
            return dayType;
        }
        //为其他正常日子
        return DayType.NORMAL;
    }

    //返回节日信息
    public static Holiday holidayDetail(String str) {
        Calendar cal = Holiday.strToCal(str);

        for (int i = 0; i < dayManager.holidays.size(); i++) {
            if (before(cal, dayManager.holidays.get(i).getEndTime()) && after(cal, dayManager.holidays.get(i).getStartTime())) {
                return dayManager.holidays.get(i);
            }
        }
        return null;
    }

    //    //打印所有节日
//    private void printHolidays(){
//        for(int i=0;i<holidays.size();i++){
//            System.out.print(holidays.get(i).getName() + " " + holidays.get(i).getZh_name() + " ");
//
//            print(holidays.get(i).getHolidayTime());
//            print(holidays.get(i).getStartTime());
//            print(holidays.get(i).getEndTime());
//            System.out.println();
//        }
//    }
//    //打印所有工作日
//    private void printWorkDays(){
//        for(int i=0;i<workdays.size();i++){
//            print(workdays.get(i));
//            System.out.println();
//        }
//    }
//
    public static String print(Calendar cal) {
        return (cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " ");
    }

    //判断两天是不是同一天
    private static Boolean areSameDay(Calendar calA, Calendar calB) {
        return calA.get(Calendar.YEAR) == calB.get(Calendar.YEAR)
                && calA.get(Calendar.MONTH) == calB.get(Calendar.MONTH)
                && calA.get(Calendar.DAY_OF_MONTH) == calB.get(Calendar.DAY_OF_MONTH);
    }

    //判断calA在不在calB前
    private static Boolean before(Calendar calA, Calendar calB) {
        if (calA.get(Calendar.YEAR) < calB.get(Calendar.YEAR)) {//如果年份在前面肯定在前面
            return true;
        } else if (calA.get(Calendar.YEAR) == calB.get(Calendar.YEAR) && calA.get(Calendar.MONTH) < calB.get(Calendar.MONTH)) {
            return true;//年份一样，月份在前面
        } else if (calA.get(Calendar.YEAR) == calB.get(Calendar.YEAR) && calA.get(Calendar.MONTH) == calB.get(Calendar.MONTH) && calA.get(Calendar.DAY_OF_MONTH) <= calB.get(Calendar.DAY_OF_MONTH)) {
            return true;//年份月份都一样，日期在前面或相等
        } else {
            return false;
        }
    }

    //判断calA在不在calB后
    private static Boolean after(Calendar calA, Calendar calB) {
        if (calA.get(Calendar.YEAR) > calB.get(Calendar.YEAR)) {
            return true;
        } else if (calA.get(Calendar.YEAR) == calB.get(Calendar.YEAR) && calA.get(Calendar.MONTH) > calB.get(Calendar.MONTH)) {
            return true;
        } else if (calA.get(Calendar.YEAR) == calB.get(Calendar.YEAR) && calA.get(Calendar.MONTH) == calB.get(Calendar.MONTH) && calA.get(Calendar.DAY_OF_MONTH) >= calB.get(Calendar.DAY_OF_MONTH)) {
            return true;
        } else {
            return false;
        }
    }


}

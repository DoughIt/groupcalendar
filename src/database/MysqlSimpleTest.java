package database;

import java.util.*;

public class MysqlSimpleTest {
    public static void main(String[] args) {

        Mysql mysql = Mysql.getInstance();
        HashMap<String, String> hashMap = new HashMap<>();
        //1测试插入travel类型
//        hashMap.put("type", "travel");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "1");
//        hashMap.put("FatherID", "0");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("way", "飞机");
//        hashMap.put("place", "天津");
//        hashMap.put("number", "F1");
//        hashMap.put("remark", "这是测试一的备忘");
//        hashMap.put("startTime", "2018-6-1 00:00");
//        hashMap.put("endTime", "2018-6-1 23:59");

        //2测试插入meeting类型
//        hashMap.put("type", "meeting");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "1");
//        hashMap.put("FatherID", "0");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("topic", "这是meeting测试的topic");
//        hashMap.put("place", "这是meeting测试的place");
//        hashMap.put("content", "这是meeting测试的content");
//        hashMap.put("startTime", "2018-6-1 00:00");
//        hashMap.put("endTime", "2018-6-1 23:59");

        //3测试插入interview类型
//        hashMap.put("type", "interview");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "1");
//        hashMap.put("FatherID", "0");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("company", "这是interview测试的company");
//        hashMap.put("place", "这是interview测试的place");
//        hashMap.put("job", "这是interview测试的job");
//        hashMap.put("remark", "这是interview测试的remark");
//        hashMap.put("startTime", "2018-6-1 00:00");
//        hashMap.put("endTime", "2018-6-1 23:59");

        //4测试插入date类型
//        hashMap.put("type", "date");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "1");
//        hashMap.put("FatherID", "0");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("people", "这是date测试的people");
//        hashMap.put("place", "这是date测试的place");
//        hashMap.put("content", "这是date测试的content");
//        hashMap.put("startTime", "2018-6-1 00:00");
//        hashMap.put("endTime", "2018-6-1 23:59");

//5测试插入custom值（开始结束时间不为空）
//        hashMap.put("type", "custom");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "1");
//        hashMap.put("FatherID", "0");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("content", "这是custom测试的content");
//        hashMap.put("startTime", "2018-6-1 00:00");
//        hashMap.put("endTime", "2018-6-1 23:59");

        //测试插入custom值（开始结束时间为空字符串）
//        hashMap.put("type", "custom");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "1");
//        hashMap.put("FatherID", "0");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("content", "这是custom测试的content");
//        hashMap.put("startTime", "");
//        hashMap.put("endTime", "");

        //6测试插入course值
//        hashMap.put("type", "course");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "1");
//        hashMap.put("FatherID", "0");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("name", "这是course测试的name");
//        hashMap.put("content", "这是course测试的content");
//        hashMap.put("startDay", "2018-6-1");
//        hashMap.put("duration", "5");
//        hashMap.put("teacher", "这是course测试的teacher");
//        hashMap.put("remark", "这是course测试的remark");
//        hashMap.put("place", "这是course测试的place");
//        hashMap.put("day", "5");
//        hashMap.put("startTime", "2018-6-1 00:00");
//        hashMap.put("endTime", "2018-6-1 23:59");

        //7测试插入anniversary值
//        hashMap.put("type", "anniversary");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "1");
//        hashMap.put("FatherID", "0");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("anniversaryType", "这是anniversary测试的anniversaryType");
//        hashMap.put("content", "这是anniversary测试的content");
//        hashMap.put("startDay", "2008-8-8");
//        mysql.addSchedule(hashMap);

        //8给测试anniversary第一个meeting个子事件
//        hashMap.put("type", "meeting");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "0");
//        hashMap.put("FatherID", "27");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("topic", "这是anniversary子事件meeting测试的topic");
//        hashMap.put("place", "这是anniversary子事件meeting测试的place");
//        hashMap.put("content", "这是anniversary子事件meeting测试的content");
//        hashMap.put("startTime", "2009-8-8 00:00");
//        hashMap.put("endTime", "2009-8-8 23:59");

        //8给测试anniversary第二个date个子事件
//        hashMap.put("type", "date");
//        hashMap.put("priority", "4");
//        hashMap.put("status", "1");
//        hashMap.put("isFather", "0");
//        hashMap.put("FatherID", "27");
//        hashMap.put("promptStatus","1");
//        hashMap.put("minutesAhead","2");
//        hashMap.put("showOnStage","1");
//        hashMap.put("minuteDelta","4");
//        hashMap.put("people", "这是anniversary子事件date测试的people");
//        hashMap.put("place", "这是anniversary子事件date测试的place");
//        hashMap.put("content", "这是anniversary子事件date测试的content");
//        hashMap.put("startTime", "2009-8-8 00:00");
//        hashMap.put("endTime", "2009-8-8 23:59");
//        mysql.addSchedule(hashMap);


        //测试根据父亲ID查找子事件
//        ArrayList<HashMap<String,String>> arrayList = mysql.queryByFatherID(27);
//        System.out.println(arrayList.size());
//        for (int i=0;i<arrayList.size();i++){
//            Iterator iter = arrayList.get(i).entrySet().iterator();
//            while (iter.hasNext()) {
//                Map.Entry entry = (Map.Entry) iter.next();
//                Object key = entry.getKey();
//                Object value = entry.getValue();
//                System.out.println(key + ":" + value);
//            }
//        }

        //测试根据时间段查找父待办事件 基本测试
////        ArrayList<HashMap<String,String>> arrayList = mysql.queryByTime("2018-6-1 1:00","2018-6-1 23:00");
////        System.out.println(arrayList.size());
////        for (int i=0;i<arrayList.size();i++){
////            Iterator iter = arrayList.get(i).entrySet().iterator();
////            while (iter.hasNext()) {
////                Map.Entry entry = (Map.Entry) iter.next();
////                Object key = entry.getKey();
////                Object value = entry.getValue();
////                System.out.println(key + ":" + value);
////            }
////        }
        //测试根据时间段查找父待办事件2 测试纪念日
//        ArrayList<HashMap<String,String>> arrayList = mysql.queryByTime("2018-8-8 1:00","2018-6-1 23:00");
//        System.out.println(arrayList.size());
//        for (int i=0;i<arrayList.size();i++){
//            Iterator iter = arrayList.get(i).entrySet().iterator();
//            while (iter.hasNext()) {
//                Map.Entry entry = (Map.Entry) iter.next();
//                Object key = entry.getKey();
//                Object value = entry.getValue();
//                System.out.println(key + ":" + value);
//            }
//        }
        //测试根据时间段查找父待办事件3 测试课程
//        ArrayList<HashMap<String,String>> arrayList = mysql.queryByTime("2018-6-15 1:00","2018-6-15 23:00");
//        System.out.println(arrayList.size());
//        for (int i=0;i<arrayList.size();i++){
//            Iterator iter = arrayList.get(i).entrySet().iterator();
//            while (iter.hasNext()) {
//                Map.Entry entry = (Map.Entry) iter.next();
//                Object key = entry.getKey();
//                Object value = entry.getValue();
//                System.out.println(key + ":" + value);
//            }
//        }


//
////        mysql.deleteSchedule(8);//删除测试完毕
//        mysql.updateState(7,3);//更新测试完毕
//        mysql.close();
        //Calendar 的clone 方法测试完毕
//        String str = "2018-5-28 23:58";
//        String strArray[] = str.split(" ");
//        String strArray1[] = strArray[0].split("-");
//        int year = Integer.valueOf(strArray1[0]);
//        int month = Integer.valueOf(strArray1[1]);
//        int day = Integer.valueOf(strArray1[2]);
//        String strArray2[] = strArray[1].split(":");
//        int hour = Integer.valueOf(strArray2[0]);
//        int minute = Integer.valueOf(strArray2[1]);
//        Calendar cal = Calendar.getInstance();
//        cal.set(year,month-1,day,hour,minute);
//        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
//
//        Calendar cal1 =  (Calendar) cal.clone();
//        cal1.add(Calendar.HOUR_OF_DAY,7);
//        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
//        System.out.println(cal1.get(Calendar.HOUR_OF_DAY));


        //calToStr 通过测试
//        Calendar cal = Calendar.getInstance();
//        cal.set(1999,8-1,24,23,59);
//        String s = cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
//        System.out.println(s);



        //如果calendar不设置hour和minute会是奇怪的值
//        Calendar cal = Calendar.getInstance();
//        cal.set(1999,8-1,24);
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int minute =  cal.get(Calendar.MINUTE);
//        System.out.println(hour);
//        System.out.println(minute);

        //测试根据id查找事件详细信息
        hashMap =mysql.queryByID("203");
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + ":" + value);
        }


        mysql.close();
    }
}

package holiday;
public class test {
    public static void main(String[] args) {
       //测试工作是否成功
       System.out.println(DayManager.judgeDays("2018-02-11").getName());
       System.out.println(DayManager.judgeDays("2018-02-24").getName());
       System.out.println(DayManager.judgeDays("2018-04-08").getName());
       System.out.println(DayManager.judgeDays("2018-04-28").getName());
       System.out.println(DayManager.judgeDays("2018-09-29").getName());
       System.out.println(DayManager.judgeDays("2018-09-30").getName());
       //测试节日是否成功
       System.out.println(DayManager.judgeDays("2018-01-01").getName());
       System.out.println(DayManager.judgeDays("2018-02-16").getName());
       System.out.println(DayManager.judgeDays("2018-04-05").getName());
       System.out.println(DayManager.judgeDays("2018-05-01").getName());
       System.out.println(DayManager.judgeDays("2018-06-18").getName());
       System.out.println(DayManager.judgeDays("2018-09-24").getName());
       System.out.println(DayManager.judgeDays("2018-10-01").getName());
       //测试休息是否成功
        System.out.println(DayManager.judgeDays("2018-02-15").getName());
        System.out.println(DayManager.judgeDays("2018-02-17").getName());
        System.out.println(DayManager.judgeDays("2018-02-18").getName());
        System.out.println(DayManager.judgeDays("2018-02-19").getName());
        System.out.println(DayManager.judgeDays("2018-02-20").getName());
        System.out.println(DayManager.judgeDays("2018-02-21").getName());
        System.out.println(DayManager.judgeDays("2018-04-06").getName());
        System.out.println(DayManager.judgeDays("2018-04-07").getName());
        System.out.println(DayManager.judgeDays("2018-04-29").getName());
        System.out.println(DayManager.judgeDays("2018-04-30").getName());
        System.out.println(DayManager.judgeDays("2018-06-16").getName());
        System.out.println(DayManager.judgeDays("2018-06-17").getName());
        System.out.println(DayManager.judgeDays("2018-09-22").getName());
        System.out.println(DayManager.judgeDays("2018-09-23").getName());
        System.out.println(DayManager.judgeDays("2018-10-02").getName());
        System.out.println(DayManager.judgeDays("2018-10-03").getName());
        System.out.println(DayManager.judgeDays("2018-10-04").getName());
        System.out.println(DayManager.judgeDays("2018-10-05").getName());
        System.out.println(DayManager.judgeDays("2018-10-06").getName());
        System.out.println(DayManager.judgeDays("2018-10-07").getName());

        //测试正常是否成功
        System.out.println(DayManager.judgeDays("2018-05-09").getName());
        System.out.println(DayManager.judgeDays("2018-09-05").getName());
        System.out.println(DayManager.judgeDays("2018-12-24").getName());
        System.out.println(DayManager.judgeDays("2018-3-8").getName());
        //测试通过节日当天获取节日信息是否成功
        System.out.println(DayManager.holidayDetail("2018-01-01").getDetail());
        System.out.println(DayManager.holidayDetail("2018-02-16").getDetail());
        System.out.println(DayManager.holidayDetail("2018-04-05").getDetail());
        System.out.println(DayManager.holidayDetail("2018-05-01").getDetail());
        System.out.println(DayManager.holidayDetail("2018-06-18").getDetail());
        System.out.println(DayManager.holidayDetail("2018-09-24").getDetail());
        System.out.println(DayManager.holidayDetail("2018-10-01").getDetail());

        //测试节日休息日获取节日信息是否成功

        System.out.println(DayManager.holidayDetail("2018-02-17").getDetail());
        System.out.println(DayManager.holidayDetail("2018-04-06").getDetail());
        System.out.println(DayManager.holidayDetail("2018-04-29").getDetail());
        System.out.println(DayManager.holidayDetail("2018-06-17").getDetail());
        System.out.println(DayManager.holidayDetail("2018-09-23").getDetail());
        System.out.println(DayManager.holidayDetail("2018-10-02").getDetail());
       //测试其他日期获取节日日期是否返回null
        if(DayManager.holidayDetail("2018-10-10") == null){
            System.out.println("Success");
        }

    }
}

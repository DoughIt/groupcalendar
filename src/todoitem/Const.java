package todoitem;


public class Const {
    public final static int BEFORE_BEGINNING = 0;   //未开始
    public final static int IN_PROGRESS = 1;        //进行中
    public final static int COMPLETED = 2;          //已完成
    public final static int OVERDUE = 3;            //过期
    public final static int PRIORITY = 3;           //默认优先级为4，即不重要 & 不紧急
    public final static int STATUS = BEFORE_BEGINNING;  //默认状态为0， 即未开始
    public final static int FATHER_ID = 0;
    public final static int ID = -1;
    public final static long MINUTES_AHEAD = 60;        //默认提前一小时
    public final static long MINUTES_DELTA = 5;         //默认每5五分钟提醒一次
    public final static boolean IS_FATHER = true;       //默认为父待办事项
    public final static boolean PROMPT_STATUS = false;  //默认不提醒
    public final static boolean SHOW_ON_STAGE = true;   //默认显示在界面上
    public final static String STATUS_STRING[] = new String[]{
            "未开始", "进行中", "已完成", "过期"
    };
    public final static String PRIORITY_TYPE[] = new String[]{
            "紧急&重要", "紧急&不重要", "不紧急&重要", "不紧急&不重要"
    };
    public final static String WEEK_NAMES[] = new String[]{
            "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日",
    };
    public final static String WAYS[] = new String[]{
            "飞机", "火车", "大巴"
    };
    public final static String ANNIVERSARY_TYPES[] = new String[]{
            "生日", "节日", "结婚纪念日"
    };

    public final static float CELL_WIDTH = 75;

}

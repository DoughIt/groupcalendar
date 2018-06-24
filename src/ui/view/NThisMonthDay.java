package ui.view;


public class NThisMonthDay extends Special{
    public NThisMonthDay(DayItem item) {
        super(item);
        init();
    }

    @Override
    protected void init() {
        setStyleClass();
        paint();
    }

    @Override
    protected void setStyleClass() {
        addStyleClass("not_this_month_day");
    }

    @Override
    protected void paint() {

    }
}

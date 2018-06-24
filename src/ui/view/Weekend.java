package ui.view;

public class Weekend extends Special {

    public Weekend(DayItem item) {
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
        addStyleClass("weekend");
    }

    @Override
    protected void paint() {

    }
}

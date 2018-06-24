package todoitem;

public interface AttributeFunc {
    int getPriority();

    void setPriority(int priority);

    int getStatus();

    void setStatus(int status);

    int getID();

    void setID(int ID);

    int getFatherID();

    void setFatherID(int fatherID);

    long minutesAhead();

    void setMinutesAhead(long minutesAhead);

    long minutesDelta();

    void setMinutesDelta(long minutesDelta);

    boolean isFather();

    void setIsFather(boolean isFather);

    boolean promptStatus();

    void setPromptStatus(boolean promptStatus);

    boolean showOnStage();

    void setShowOnStage(boolean showOnStage);

}

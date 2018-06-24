package holiday;
public enum DayType {
    WORKDAY("work"), HOLIDAY("holiday"), REST("rest"),NORMAL("");
    private String name;

    DayType(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}



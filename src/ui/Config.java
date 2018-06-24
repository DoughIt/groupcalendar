package ui;

public class Config {
    private static final int rectangleHeight = 60;
    private static final int rectangleWidth = 80;

    private static final int vGap = 5;//for content grid
    private static final int hGap = 6;//for content grid

    private static final int windowWidth = 1200;
    private static final int windowHeight = 720;

    private Config() {

    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getRectangleWidth() {
        return rectangleWidth;
    }

    public static int getRectangleHeight() {
        return rectangleHeight;
    }

    public static int getvGap() {
        return vGap;
    }

    public static int gethGap() {
        return hGap;
    }
}

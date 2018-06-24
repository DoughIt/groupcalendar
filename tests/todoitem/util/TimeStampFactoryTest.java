package todoitem.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeStampFactoryTest {
    @Test
    public void createByMilisTest() {
        long milis = System.currentTimeMillis();
        TimeStamp ts = TimeStampFactory.createStampByMiliseconds(milis);
        long minutes = milis / (1000 * 60);
        assertEquals(minutes, ts.getMinutes());
    }

    @Test
    public void createByMinutesTest() {
        long minutes = System.currentTimeMillis() / (60 * 1000);
        TimeStamp ts = TimeStampFactory.createStampByMinutes(minutes);
        assertEquals(minutes , ts.getMinutes());
    }

}
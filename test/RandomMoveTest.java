import City.RandomMove;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomMoveTest {

    @Test
    public void testCityGetCenter()
    {
        RandomMove m = new RandomMove(100,200);
        assertEquals(100,m.getxAxis());
        assertEquals(200,m.getyAxis());
        m.setxAxis(150);
        m.setyAxis(150);
        assertEquals(150,m.getxAxis());
        assertEquals(150,m.getyAxis());
        m.setReLocated(true);
        assertEquals(true,m.isReLocated());
    }
}

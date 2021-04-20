import City.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocationTest {

    @Test
    public void testCityGetCenter()
    {
        Location p = new Location(100,200);
        assertEquals(100,p.getX());
        assertEquals(200,p.getY());
        p.reLocate(50,50);
        assertEquals(150,p.getX());
        assertEquals(250,p.getY());
    }
}


import City.City;
import org.junit.Test;

import static org.junit.Assert.*;

public class CityTest {

    @Test
    public void testCityGetCenter()
    {
        City c = new City(100,200);
        assertEquals(100,c.getCenterX());
    }

    @Test
    public void testCitySetCenter()
    {
        City c = new City(100,150);
        c.setCenterX(200);
        c.setCenterY(150);
        assertEquals(200,c.getCenterX());
        assertEquals(150,c.getCenterY());
    }

}
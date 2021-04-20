import City.City;
import Residents.Resident;
import Virus.Virus;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class VirusTest {
    @Test
    public void testVirus() throws IOException {
        City c = new City(1,2);
        Resident r = new Resident(c,1,2);
        Virus v = new Virus();
        v.setViralLoad();
        assertNotEquals(0,v.getViralLoad());
        float t = v.getFatalityRateByAge(r);
        float risk = v.calMaskRisk();
        boolean maskRatio = v.calMaskWearRatio();
        float infectionRisk  = v.calculateInfectionRisk(r);
    }
}

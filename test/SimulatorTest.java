import City.City;
import Residents.Resident;
import utill.Simulator;
import org.junit.Test;

import java.io.IOException;
import java.util.Timer;

import static org.junit.Assert.*;
public class SimulatorTest {
    @Test
    public void testSimulator() throws IOException {
        Simulator s = new Simulator();
        s.run();
    }
}

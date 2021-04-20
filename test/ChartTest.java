import org.jfree.data.xy.XYSeries;
import utill.Charts;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ChartTest {

    @Test
    public void testChart() throws IOException {
        Charts c = new Charts();
        XYSeries series = new XYSeries("Confirmed Patients");
        XYSeries series2 = new XYSeries("Vaccinated Patients");
        XYSeries series3 = new XYSeries("Dead Patients");
       // assertEquals(c.getSeries(),series);
        assertEquals(c.getSeries2(),series2);
        assertEquals(c.getSeries3(),series3);
    }
}


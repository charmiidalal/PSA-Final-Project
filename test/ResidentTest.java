import City.City;
import Residents.Resident;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ResidentTest {

    @Test
    public void testResidentConst() throws IOException {
        City c = new City(1,2);
        Resident r = new Resident(c,1,2);
    }

    @Test
    public  void testBeInfected() throws IOException {
        City c = new City(1,2);
        Resident r = new Resident(c,1,2);
        r.infectResident();
        assertEquals(r.getInfection_status(),1);
        r.setInfecteDay(50);
        assertEquals(50,r.getInfecteDay());
        r.setDiagnoseDay(100);
        assertEquals(100,r.getDiagnoseDay());
        r.setPossibleDeathDay(150);
        assertEquals(150,r.getPossibleDeathDay());
        r.setIsolationDay(15);
        assertEquals(15,r.getIsolationDay());
    }

    @Test
    public  void testisInfected() throws IOException {
        City c = new City(1,2);
        Resident r = new Resident(c,1,2);
        r.setInfection_status(2);
        assertEquals(r.isInfected(),true);
        r.setIsIsolating();
    }

    @Test
    public  void testVaccinated() throws IOException{
        City c = new City(1,2);
        Resident r = new Resident(c,1,2);
        r.setVaccinated(true);
        assertEquals(r.isVaccinated(),true);
        assertEquals(false,r.getIsSuper());

    }
    @Test
    public  void testDistance() throws IOException {
        City c = new City(1,2);
        Resident r1 = new Resident(c,1,2);
        Resident r2= new Resident(c,1,2);
        r1.setX(1);
        r2.setX(5);
        r1.setY(1);
        r2.setY(5);
        assertEquals(r1.calculateDistance(r2),Math.sqrt(32),0.01);
        assertEquals(r1.getX(),1);
        assertEquals(r2.getY(),5);
    }

    @Test
    public  void testCheckAxis() throws IOException {
        City c = new City(1,2);
        Resident r = new Resident(c,1,2);
        r.setxAxis(100);
        r.setyAxis(200);
        assertEquals(100,r.getxAxis(),0.001);
        assertEquals(200,r.getyAxis(),0.001);
    }

    @Test
    public  void testCheckHealth() throws IOException {
        City c = new City(1,2);
        Resident r = new Resident(c,1,2);
        r.checkHealth();
        assertEquals(false,r.getIsCure());

    }

    @Test
    public  void testSuperSppreader() throws IOException {
        City c = new City(1,2);
        Resident r = new Resident(c,1,2);
        r.setSuperSpreader(true);
        assertEquals(true,r.getSuperSpreader());
    }


}
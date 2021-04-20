import City.City;
import Residents.ResidentDirectory;
import Residents.Resident;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResidentDirectoryTest {
    @Test
    public void testResidentsList() throws IOException {
        City c = new City(1,2);
        Resident r = new Resident(c,1,2);
        r.setSuperSpreader(true);
        r.setIsIsolating();
        List<Resident> residentList = ResidentDirectory.getInstance().getResidentList();
        assertEquals(5000,residentList.size());
        assertEquals(0, ResidentDirectory.getInstance().getVaccinatedResidents());
        assertEquals(5000, ResidentDirectory.getInstance().getResidentNumberbyStatus(0));

    }

    @Test
    public void testVaccinatedList()
    {
        assertEquals(0, ResidentDirectory.getInstance().getVaccinatedResidents());
    }

    @Test
    public void testSuperInfector()
    {
        assertEquals(0, ResidentDirectory.getInstance().getSuperSpreaders());
    }

    @Test
    public void testCasesHistory()
    {
        assertEquals(0, ResidentDirectory.getInstance().getTotalCases());
        assertEquals(0, ResidentDirectory.getInstance().getTotalIsolated());
        assertEquals(0, ResidentDirectory.getInstance().getCuredResidents());
    }
}

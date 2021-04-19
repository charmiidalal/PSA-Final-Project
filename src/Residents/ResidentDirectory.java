package Residents;

import City.City;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ResidentDirectory {
    private static ResidentDirectory residentDirectory;
    List<Resident> residentList = new ArrayList<Resident>();
    Ini ini = new Ini(new File("./src/config.ini"));
    Map<String, String> resident_status = ini.get("resident_status");

    private ResidentDirectory() throws IOException {
        Ini ini = new Ini(new File("./src/config.ini"));
        Map<String, String> map = ini.get("default");
        City city = new City(300, 300);//position of city center-->(400,400)
        //add people in this city
        for (int i = 0; i < Integer.parseInt(map.get("city_population")); i++) {
            Random random = new Random();
            //generate the position of people-->N(a,b)：Math.sqrt(b)*random.nextGaussian()+a
            int x = (int) (100 * random.nextGaussian() + city.getCenterX());
            int y = (int) (100 * random.nextGaussian() + city.getCenterY());
            if (x > 700) x = 700;//people cannot be out the range of city
            residentList.add(new Resident(city, x, y));
        }
    }

    static {
        try {
            residentDirectory = new ResidentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ResidentDirectory getInstance() {
        return residentDirectory;
    }

    public List<Resident> getResidentList() {
        return residentList;
    }

    public int getVaccinatedResidents() {
        int vac = 0;
        for (Resident p : residentList) {
            if (p.isVaccinated()) {
                vac++;
            }
        }
        return vac;
    }

    public int getResidentNumberbyStatus(int virusStatus) {
        int i = 0;
        for (Resident resident : residentList) {
            if (resident.getInfection_status() == virusStatus) {
                i++;
            }
        }
        return i;
    }

    public int getSuperSpreaders() {
        int i = 0;
        for (Resident resident : residentList) {
            if (resident.getInfection_status() >= Integer.parseInt(resident_status.get("suspected")) &&
                    resident.getInfection_status() <= Integer.parseInt(resident_status.get("positive"))) {
                if (resident.getIsSuper()) {
                    i++;
                }
            }
        }
        return i;
    }
    public int getTotalCases() {
        int i = 0;
        for (Resident resident : residentList) {
            if (resident.diagnoseDay != 0) {
                    i++;
            }
        }
        return i;
    }
    public int getTotalIsolated() {
        int i = 0;
        for (Resident resident : residentList) {
            if (resident.getIsIsolating()) {
                    i++;
            }
        }
        return i;
    }
    public int getCuredResidents() {
        int i = 0;
        for (Resident resident : residentList) {
            if (resident.getInfection_status() == Integer.parseInt(resident_status.get("negative"))) {
                if (resident.getIsCure()) {
                    i++;
                }
            }
        }
        return i;
    }
}

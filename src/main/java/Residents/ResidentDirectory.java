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
        City city = new City(300, 300);
        for (int i = 0; i < Integer.parseInt(map.get("city_population")); i++) {
            Random random = new Random();
            //Randomly puts balls in city to simulate virus
            int x = (int) (100 * random.nextGaussian() + city.getCenterX());
            int y = (int) (100 * random.nextGaussian() + city.getCenterY());
            if (x > 700) x = 700;
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

    /* This method fetches vaccinated people in city */
    public int getVaccinatedResidents() {
        int vac = 0;
        for (Resident p : residentList) {
            if (p.isVaccinated()) {
                vac++;
            }
        }
        return vac;
    }
    /* This method takes virus status and counts people by it */
    public int getResidentNumberbyStatus(int virusStatus) {
        int i = 0;
        for (Resident resident : residentList) {
            if (resident.getInfection_status() == virusStatus) {
                i++;
            }
        }
        return i;
    }
    /* This method fetches the list of super spreder in city */
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
    /* This method fetches the list of total cases till day */
    public int getTotalCases() {
        int i = 0;
        for (Resident resident : residentList) {
            if (resident.diagnoseDay != 0) {
                    i++;
            }
        }
        return i;
    }
    /* This method fetches the list of current isolating people */
    public int getTotalIsolated() {
        int i = 0;
        for (Resident resident : residentList) {
            if (resident.getIsIsolating()) {
                    i++;
            }
        }
        return i;
    }
    /* This method fetches the list of all cured people */
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

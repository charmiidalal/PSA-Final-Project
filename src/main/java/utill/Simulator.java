package utill;

import Residents.Resident;
import Residents.ResidentDirectory;
import org.ini4j.Ini;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Simulator extends JPanel implements Runnable {
    //reading the config file to fetch the value of variables
    Ini ini = new Ini(new File("./src/config.ini"));
    //connecting the file to 2 map to ease the reading and fetching for the default and resident_status
    Map<String, String> map = ini.get("default");
    Map<String, String> resident_status = ini.get("resident_status");

    //variable for keeping track of days and timer and dose
    public static int pandemicDay = 0;
    public Timer timer = new Timer();
    int dosesPerDay;

    public void setDosesPerDay() {
        this.dosesPerDay = (int) Math.ceil(Integer.parseInt(map.get("city_population")) * Float.parseFloat(map.get("vaccination_rate")));
    }
    public int getDosesPerDay() {
        return this.dosesPerDay;
    }

    //constructor
    public Simulator() throws IOException {
        super();
        this.setBackground(new Color(255, 255, 255));
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        //fetching the list of all residents
        List<Resident> people = ResidentDirectory.getInstance().getResidentList();

        //coloring the oval representing each resident according to their condition
        for (Resident Resident : people) {
            switch (Resident.getInfection_status()) {
                case 0: {
                    //green for normal
                    graphics.setColor(new Color(0xF6BD2B));
                    break;
                }
                case 1: {
                    //yellow for suspected patients
                    graphics.setColor(new Color(0xF8752E));
                    break;
                }
                case 2: {
                    //red for positive
                    graphics.setColor(new Color(0xC71F1F));
                    break;
                }
                case 3: {
                    //black for dead
                    graphics.setColor(new Color(0x070707));
                    break;
                }
            }

            //if resident is not dead then update their health
            if (Resident.getInfection_status() != Integer.parseInt(resident_status.get("dead")))
                Resident.checkHealth();

            //vaccinating people by checking their vaccinated status and doses remaining in a day
            if (pandemicDay > Integer.parseInt(map.get("vaccination_discovery_time"))) {
                if (dosesPerDay > 0 && Resident.isVaccinated() == false) {
                    Resident.setVaccinated(true);
                    dosesPerDay--;
                }
            }
            //setting the size of oval representing residents
            graphics.fillOval(Resident.getX(), Resident.getY(), 4, 4);
        }
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            //adding the updated data to series in Charts class to update the charts
            Charts.getSeries().add(pandemicDay/10, ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("positive"))));
            Charts.getSeries2().add(pandemicDay/10, ResidentDirectory.getInstance().getVaccinatedResidents());
            Charts.getSeries3().add(pandemicDay/10, ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("dead"))));

            //updating the map in Charts class to update the label data along with graph
            Charts.data.put("Total Population: ", Integer.parseInt(map.get("city_population")));
            Charts.data.put("Normal Residents: ", ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("negative"))));
            Charts.data.put("Total Positive Cases: ", ResidentDirectory.getInstance().getTotalCases());
            Charts.data.put("Active Positive Patients: ", ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("positive"))));
            Charts.data.put("Suspected Patients: ", ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("suspected"))));
            Charts.data.put("Vaccinated People: ", ResidentDirectory.getInstance().getVaccinatedResidents());
            Charts.data.put("Dead Patients: ", ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("dead"))));
            Charts.data.put("Cured Patients: ", ResidentDirectory.getInstance().getCuredResidents());
            Charts.data.put("Isolated Patients: ", ResidentDirectory.getInstance().getTotalIsolated());
            Charts.data.put("Pandemic Day: ", pandemicDay/10);
            Charts.data.put("Super Spreaders: ", ResidentDirectory.getInstance().getSuperSpreaders());
            Charts.updateLbl();

            //repainting the graph with updates
            Simulator.this.repaint();
            pandemicDay++;

            //logging the data
            if (pandemicDay % 100 == 0) {
                setDosesPerDay();
                System.out.println("Time: " + pandemicDay / 10 + " days;Vaccinated people: " + ResidentDirectory.getInstance().getVaccinatedResidents() + ";Normal people: " + ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("negative"))) + " ;Suspected patients: " + ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("suspected"))) + " ;Positive patients: " + ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("positive"))) + " ;Cured: " + ResidentDirectory.getInstance().getCuredResidents() + " ;Super Spreaders: " + ResidentDirectory.getInstance().getSuperSpreaders() + " ;Dead: " + ResidentDirectory.getInstance().getResidentNumberbyStatus(Integer.parseInt(resident_status.get("dead"))));
            }
        }
    }

    @Override
    public void run() {
        timer.schedule(new MyTimerTask(), 0, 100);
    }
}

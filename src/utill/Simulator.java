package Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;

import Residents.*;
import org.ini4j.Ini;

public class Simulator extends JPanel implements Runnable {
    Ini ini = new Ini(new File("./src/config.ini"));
    Map<String, String> map = ini.get("default");
    Map<String, String> person_status = ini.get("person_status");
    public static int pandemicDay = 0;
    public java.util.Timer timer = new Timer();
    int doses = (int) Math.ceil(Integer.parseInt(map.get("city_population")) * Float.parseFloat(map.get("vaccination_rate")));

    public Simulator() throws IOException {
        super();
        this.setBackground(new Color(0x444444));
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        java.util.List<Resident> residentList = Residents.getInstance().getResidentList();
        for (Resident resident : residentList) {
            if (resident.getViruStatus() == Integer.parseInt(person_status.get("negative")))
                graphics.setColor(new Color(0xdddddd));
            else if (resident.getViruStatus() == Integer.parseInt(person_status.get("suspected")))
                graphics.setColor(new Color(0xffee00));
            else if (resident.getViruStatus() == Integer.parseInt(person_status.get("positive")))
                graphics.setColor(new Color(0xff0000));
            else if (resident.getViruStatus() == Integer.parseInt(person_status.get("dead")))
                graphics.setColor(new Color(0xFF655));
            resident.checkHealth();
            graphics.fillOval(resident.getX(), resident.getY(), 3, 3);
        }
        int captionStartOffsetX = 700 + 40;
        int captionStartOffsetY = 40;
        int captionSize = 24;
        //show data
        graphics.setColor(Color.BLUE);
        graphics.drawString("Total Population: " + Integer.parseInt(map.get("city_population")), captionStartOffsetX, captionStartOffsetY);
        graphics.setColor(new Color(0xdddddd));
        graphics.drawString("Negative Population: " + Residents.getInstance().getResidentsSize(Integer.parseInt(person_status.get("negative"))), captionStartOffsetX, captionStartOffsetY + captionSize);
        graphics.setColor(new Color(0xffee00));
        graphics.drawString("Suspected Population: " + Residents.getInstance().getResidentsSize(Integer.parseInt(person_status.get("suspected"))), captionStartOffsetX, captionStartOffsetY + 2 * captionSize);
        graphics.setColor(new Color(0xff0000));
        graphics.drawString("Positive Population: " + Residents.getInstance().getResidentsSize(Integer.parseInt(person_status.get("positive"))), captionStartOffsetX, captionStartOffsetY + 3 * captionSize);
        graphics.setColor(new Color(0x00ff00));
        graphics.drawString("Vaccinated Population: " + Residents.getInstance().getVaccinated(), captionStartOffsetX, captionStartOffsetY + 4 * captionSize);
        graphics.setColor(new Color(0xDE0F));
        graphics.drawString("Total Positive Cases: " + Residents.getInstance().getTotalCases(), captionStartOffsetX, captionStartOffsetY + 5 * captionSize);
        graphics.setColor(new Color(0xDE));
        graphics.drawString("Isolating Population: " + Residents.getInstance().getTotalIsolated(), captionStartOffsetX, captionStartOffsetY + 6 * captionSize);
        graphics.setColor(new Color(0xccbbcc));
        graphics.drawString("Dead Population: " + Residents.getInstance().getResidentsSize(Integer.parseInt(person_status.get("dead"))), captionStartOffsetX, captionStartOffsetY + 7 * captionSize);
        graphics.setColor(new Color(0xffffff));
        graphics.drawString("Pandemic Day: " + (int) (pandemicDay / 10.0), captionStartOffsetX, captionStartOffsetY + 8 * captionSize);
        graphics.setColor(new Color(0xF87BF504, true));
        graphics.drawString("Recovered Population: " + Residents.getInstance().getCure(), captionStartOffsetX, captionStartOffsetY + 9 * captionSize);
        graphics.setColor(new Color(0xDE0FD6));
        graphics.drawString("Super Spreaders: " + Residents.getInstance().getSuperInfector(), captionStartOffsetX, captionStartOffsetY + 10 * captionSize);

    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Simulator.this.repaint();
            pandemicDay++;
            doses = 2;
            //output
            List<Resident> people = Residents.getInstance().getResidentList();
            if (pandemicDay % 10 == 0 && pandemicDay > Integer.parseInt(map.get("vaccination_discovery_time"))) {
                for (Resident resident : people) {
                    if (doses > 0 && resident.isVaccinated() == false) {
                        resident.setVaccinated(true);
                        doses -= 1;
                    }
                }
            }
            if (pandemicDay % 100 == 0) {
                System.out.println("Pandamic Day: " + pandemicDay / 10 + " days;Vaccinated people: " +
                        Residents.getInstance().getVaccinated() + ";Negative Residents: " +
                        Residents.getInstance().getResidentsSize(Integer.parseInt(map.get("negative"))) + " ;Suspected Residents: " +
                        Residents.getInstance().getResidentsSize(Integer.parseInt(map.get("suspected"))) + " ;Positive Residents: " +
                        Residents.getInstance().getResidentsSize(Integer.parseInt(map.get("positive"))) + " ;Survivor Residents: " +
                        Residents.getInstance().getCure() + " ;Super Spreaders: " +
                        Residents.getInstance().getSuperInfector() + " ;Dead Residents: " +
                        Residents.getInstance().getResidentsSize(Integer.parseInt(map.get("dead"))));
            }
        }
    }

    @Override
    public void run() {
        timer.schedule(new MyTimerTask(), 0, 100);
    }
}

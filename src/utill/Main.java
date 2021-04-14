package Util;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import Residents.Resident;
import Residents.Residents;
import org.ini4j.Ini;

public class Main {
    public static void main(String[] args) throws IOException {
        initGraph();
        initInfected();
    }

    private static void initGraph() throws IOException {
        Ini ini = new Ini(new File("./src/config.ini"));
        Map<String, String> virusParams = ini.get("default");
        Simulator simulator = new Simulator();
        Thread panelThread = new Thread(simulator);
        JFrame frame = new JFrame();
        frame.add(simulator);
        frame.setSize(Integer.parseInt(virusParams.get("city_area_width"))+300, Integer.parseInt(virusParams.get("city_area_height")));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle(virusParams.get("virus_simulation"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelThread.start();
    }

    private static void initInfected() throws IOException {
        Ini ini = new Ini(new File("./src/config.ini"));
        Map<String, String> virusParams = ini.get("default");
        List<Resident> residentList = Residents.getInstance().getResidentList();
        for (int i = 0; i < Integer.parseInt(virusParams.get("randomly_infected")); i++) {
            Resident resident;
            do {
                resident = residentList.get(new Random().nextInt(residentList.size() - 1));
            } while (resident.isInfected());
            resident.beInfected();
        }
    }

}

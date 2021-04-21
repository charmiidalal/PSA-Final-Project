package utill;

import Residents.*;
import org.ini4j.Ini;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        initGraph();
        initInfected();
    }
    private static void initGraph() throws IOException {
        //reading the config file to fetch the value of variables
        Ini ini = new Ini(new File("./src/config.ini"));
        //connecting the file to map for ease in reading and fetching
        Map<String, String> map = ini.get("default");

        //creating the main frame and panel
        JFrame frame = new JFrame();
        JPanel ui = new JPanel();
        ui.setLayout(new GridLayout(1,3));

        //calling the class which creates the graph and passing it to thread to ensure continuous movement
        Simulator graph = new Simulator();
        Thread panelThread = new Thread(graph);

        //adding the graph and line charts to main panel and setting the background color
        ui.add(graph);
        ui.add(new Charts());
        ui.setBackground(Color.BLACK);

        //adding the ui to frame and adjusting its attribute
        frame.add(ui);
        frame.setVisible(true);
        frame.setTitle(map.get("virus_simulation")+" of "+map.get("city_population")+" Residents of Boston");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);
        frame.setResizable(false);
        frame.setLocation(50, 50);

        //starting the thread
        panelThread.start();
    }

    private static void initInfected() throws IOException {
        Ini ini = new Ini(new File("./src/config.ini"));
        Map<String, String> virusParams = ini.get("default");
        List<Resident> residentList = ResidentDirectory.getInstance().getResidentList();
        for (int i = 0; i < Integer.parseInt(virusParams.get("randomly_infected")); i++) {
            Resident resident;
            do {
                resident = residentList.get(new Random().nextInt(residentList.size() - 1));
            } while (resident.isInfected());
            resident.infectResident();
        }
    }
}

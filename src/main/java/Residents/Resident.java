package Residents;

import City.*;
import Virus.Virus;
import org.ini4j.Ini;
import utill.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Resident extends Location {
    //INI file which has all the needed factors and values
    Ini ini = new Ini(new File("./src/config.ini"));
    // These are for the virus factors
    Map<String, String> map = ini.get("default");
    //This shows current health of residents
    Map<String, String> resident_status = ini.get("resident_status");
    int moveScale = 1;
    int age;
    double xAxis;
    double yAxis;
    int infecteDay = 0; //The day on which infection occured
    int diagnoseDay = 0; //the day on which infection detected
    int possibleDeathDay = 0; // Estimated death tday
    int isolationDay = 0; //The isolation start day
    boolean isVaccinated = false; //Checks if person is vaccinated or not
    private City city;
    private RandomMove randomMove;
    private Virus virus;
    private Boolean isSuperSpreader = false; //If resident is super spreader
    private Boolean isCured = false; //Checks if resdient had virus or not
    private Boolean isIsolated = false; //Checks resident's isolation status
    private int infection_status = Integer.parseInt(resident_status.get("negative"));

    public Resident(City city, int x, int y) throws IOException {
        super(x, y);
        this.virus = new Virus();
        this.city = city;
        this.setAge(); //set random age of resident
        xAxis = MathUtil.stdGaussian(100, x);
        yAxis = MathUtil.stdGaussian(100, y);
    }

    public Boolean getSuperSpreader() {
        return isSuperSpreader;
    }

    public void setSuperSpreader(Boolean superSpreader) {
        isSuperSpreader = superSpreader;
    }

    public double getxAxis() {
        return xAxis;
    }

    public void setxAxis(double xAxis) {
        this.xAxis = xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    public void setyAxis(double yAxis) {
        this.yAxis = yAxis;
    }

    public int getInfecteDay() {
        return infecteDay;
    }

    public void setInfecteDay(int infecteDay) {
        this.infecteDay = infecteDay;
    }

    public int getDiagnoseDay() {
        return diagnoseDay;
    }

    public void setDiagnoseDay(int diagnoseDay) {
        this.diagnoseDay = diagnoseDay;
    }

    public int getPossibleDeathDay() {
        return possibleDeathDay;
    }

    public void setPossibleDeathDay(int possibleDeathDay) {
        this.possibleDeathDay = possibleDeathDay;
    }

    public int getIsolationDay() {
        return isolationDay;
    }

    public void setIsolationDay(int isolationDay) {
        this.isolationDay = isolationDay;
    }

    public Boolean getIsSuper() {
        return isSuperSpreader;
    }

    public Boolean getIsCure() {
        return isCured;
    }

    public Boolean getIsIsolating() {
        return this.isIsolated;
    }

    public int getAge() {
        return age;
    }

    public void setAge() {
        this.age = (int) (Math.random() * 100);
    }

    //Checks possibilty of infected resident's isolation with help of non_isolating_rate
    public void setIsIsolating() {
        float isoPossiblity = new Random().nextFloat();
        if (isoPossiblity < Float.parseFloat(map.get("non_isolating_rate"))) {
            this.isIsolated = true;
            this.isolationDay = Simulator.pandemicDay;
        } else this.isIsolated = false;
    }


    public int getInfection_status() {
        return infection_status;
    }

    public void setInfection_status(int infection_status) {
        this.infection_status = infection_status;
    }

    public boolean isInfected() {
        return infection_status >= Integer.parseInt(resident_status.get("suspected"));
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        isSuperSpreader = false;
        isVaccinated = vaccinated;
    }

    /* randomly infects resident and assigns random viral load */
    public void infectResident() {
        float randomLoad = new Random().nextFloat();
        virus.setViralLoad();
        int viralLoad = virus.getViralLoad();
        if (randomLoad < Float.parseFloat(map.get("K")) || viralLoad < Integer.parseInt(map.get("viral_safe_threshold"))) {
            this.isSuperSpreader = true;
        }
        this.infection_status = Integer.parseInt(resident_status.get("suspected"));
        this.infecteDay = Simulator.pandemicDay;
    }

    /*calculate distance to check if residents are following social distancing */
    public double calculateDistance(Resident resident) {
        return Math.sqrt(Math.pow(this.getX() - resident.getX(), 2) + Math.pow(this.getY() - resident.getY(), 2));
    }

    /* Does random movement to spread virus */
    private void doRandomMove() {
        if (this.isIsolated) {
            return;
        }
        if (MathUtil.stdGaussian(moveScale, Float.parseFloat(map.get("contact_intention"))) <= 0) {
            return;
        }
        if (randomMove == null || randomMove.isReLocated()) {
            double targetX = MathUtil.stdGaussian(100, xAxis);
            double targetY = MathUtil.stdGaussian(100, yAxis);
            randomMove = new RandomMove((int) targetX, (int) targetY);
        }
        if ((getY() - 400) * (randomMove.getxAxis() - 400) < 0) {
            this.setIsIsolating();
            if (randomMove.getyAxis() < 400) {
                city = new City(200, 200);
            } else {
                city = new City(500, 500);
            }
        }
        int dX = randomMove.getxAxis() - getX();
        int dY = randomMove.getyAxis() - getY();
        double length = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

        if (length < 1) {
            randomMove.setReLocated(true);
            return;
        }

        int udX = (int) (dX / length);
        if (udX == 0 && dX != 0) {
            if (dX > 0) {
                udX = 1;
            } else {
                udX = -1;
            }
        }

        int udY = (int) (dY / length);
        if (udY == 0 && dY != 0) {
            if (dY > 0) {
                udY = 1;
            } else {
                udY = -1;
            }
        }
        if (getX() > Integer.parseInt(map.get("city_area_width")) || getX() < 0) {
            randomMove = null;
            if (udX > 0) {
                udX = -udX;
            }
        }
        if (getY() > Integer.parseInt(map.get("city_area_height")) || getY() < 0) {
            randomMove = null;
            if (udY > 0) {
                udY = -udY;
            }
        }
        reLocate(udX, udY);
    }

    /* checks status of patiets by counting isolation and mortality rates*/
    public void checkHealth() {
        double targetX = MathUtil.stdGaussian(100, xAxis);
        double targetY = MathUtil.stdGaussian(100, yAxis);
        randomMove = new RandomMove((int) targetX, (int) targetY);
        if (!this.isIsolated)
            doRandomMove();
        /* checks if quarantine time is over and if yes then does random movement */
        if (this.isIsolated) {
            if (Simulator.pandemicDay - this.isolationDay > Integer.parseInt(map.get("quarantine_time"))) {
                this.isIsolated = false;
                doRandomMove();
                Random random = new Random();
                int x = (int) (500 * random.nextGaussian() + city.getCenterX());
                int y = (int) (500 * random.nextGaussian() + city.getCenterX());
                if (x > 500) x = 200;
                reLocate(x,y);
            }
        }
        /*Checks if patient is recovered and sets it negative*/
        if (infection_status == Integer.parseInt(resident_status.get("positive")) && this.possibleDeathDay == 0) {
            if (Simulator.pandemicDay - this.diagnoseDay >= Integer.parseInt(map.get("hospitalized_days"))) {
                float ranDeathPoss = new Random().nextFloat();
                if (ranDeathPoss <= Float.parseFloat(map.get("broad_risk_rate")) / Float.parseFloat(map.get("R"))) {
                    this.infection_status = Integer.parseInt(resident_status.get("negative"));
                    this.isSuperSpreader = false;
                    this.isCured = true;
                    this.possibleDeathDay = 0;
                    this.isIsolated = false;
                }
            } else {
                int mortalityPoss = new Random().nextInt(10000) + 1;
                float fatalityRate = virus.getFatalityRateByAge(this);
                if (1 <= mortalityPoss && mortalityPoss <= (int) (fatalityRate * 10000)) {
                    int dieTime = (int) MathUtil.stdGaussian(Double.parseDouble(map.get("death_variance")), Integer.parseInt(map.get("death_period")));
                    this.possibleDeathDay = this.diagnoseDay + dieTime;
                } else {
                    this.possibleDeathDay = 0;
                }
            }
        }

        //Checks if certain days are passed and if not recovered marks it as dead
        if (this.infection_status == Integer.parseInt(resident_status.get("positive")) && Simulator.pandemicDay >= possibleDeathDay && possibleDeathDay > 0) {
            this.infection_status = Integer.parseInt(resident_status.get("dead"));
            setX(300);
            setY(300);
        }

        //If incubation oeriod is over then mark as positive
        double incubationPeriod = MathUtil.stdGaussian(25, Integer.parseInt(map.get("quarantine_time")) / 2);
        if (Simulator.pandemicDay - this.infecteDay > incubationPeriod && this.infection_status == Integer.parseInt(resident_status.get("suspected"))) {
            this.infection_status = Integer.parseInt(resident_status.get("positive"));
            this.diagnoseDay = Simulator.pandemicDay;
        }
        //Checks risk of residents to get infect with respect to its contyact with other residents
        if (this.infection_status < Integer.parseInt(this.resident_status.get("suspected"))) {
            List<Resident> people = ResidentDirectory.getInstance().residentList;
            for (Resident resident : people) {
                if (resident.getInfection_status() == Integer.parseInt(resident_status.get("negative")) ||
                        resident.getInfection_status() == Integer.parseInt(resident_status.get("dead")) ||
                        resident.getIsIsolating() || resident.isVaccinated() == true)
                    continue;
                float ranPossiblity = new Random().nextFloat();
                float risk = virus.calculateInfectionRisk(resident);
                if (ranPossiblity < risk && calculateDistance(resident) < Float.parseFloat(map.get("safe_distance"))) {
                    this.infectResident();
                    break;
                }
            }
        }
    }
}

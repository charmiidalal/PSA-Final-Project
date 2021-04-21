package Virus;

import org.ini4j.Ini;
import Residents.Resident;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class Virus {
    Ini ini = new Ini(new File("./src/config.ini"));
    Map<String, String> map = ini.get("default");
    int viralLoad; //Viral load can decide super spreader residents

    public int getViralLoad() {
        return viralLoad;
    }

    public void setViralLoad() {
        this.viralLoad = (int)(Math.random() * Integer.parseInt(map.get("viral_load_threshold")));
    }

    public Virus() throws IOException {
    }

    /* checks resident's mortality rate with respect to thier age */
    public float getFatalityRateByAge(Resident resident){
        if(resident.getAge() >= 0 && resident.getAge() < 10)
            return Float.parseFloat(map.get("mortality_0_9"));
        else if(resident.getAge() >= 10 && resident.getAge() < 10)
            return Float.parseFloat(map.get("mortality_10_19"));
        else if(resident.getAge() >= 20 && resident.getAge() < 30)
            return Float.parseFloat(map.get("mortality_20_29"));
        else if(resident.getAge() >= 30 && resident.getAge() < 40)
            return Float.parseFloat(map.get("mortality_30_39"));
        else if(resident.getAge() >= 40 && resident.getAge() < 50)
            return Float.parseFloat(map.get("mortality_40_49"));
        else if(resident.getAge() >= 50 && resident.getAge() < 60)
            return Float.parseFloat(map.get("mortality_50_59"));
        else if(resident.getAge() >= 60 && resident.getAge() < 70)
            return Float.parseFloat(map.get("mortality_60_69"));
        else if(resident.getAge() >= 70 && resident.getAge() < 80)
            return Float.parseFloat(map.get("mortality_70_79"));
        else if(resident.getAge() >= 80 && resident.getAge() < 90)
            return Float.parseFloat(map.get("mortality_80_89"));
        else
            return Float.parseFloat(map.get("mortality_90_99"));

    }

    /* This method checks risk of virus with different type of masks risks*/
    public float calMaskRisk() {
        String[] maskType = {"cloth_mask_risk_rate", "surgical_mask_risk_rate", "n95_mask_risk_rate"};
        Random generator = new Random();
        int randomIndex = generator.nextInt(maskType.length);
        return  Float.parseFloat(map.get(maskType[randomIndex]));
    }

    /* This method checks the possiblity of wearing masks by residents */
    public boolean calMaskWearRatio() {
        float randomRisk = new Random().nextFloat();
        if (randomRisk < Float.parseFloat(map.get("mask_wearing_risk")))
            return true;
        else
            return false;
    }

    /* This method gathers all factors related to getting infection and calculates infection risk*/
    public float calculateInfectionRisk(Resident resident){
        float risk = Float.parseFloat(map.get("broad_risk_rate"));
        if (resident.isVaccinated()) {
            risk = (float) 0.1;
        }
        if (resident.getIsCure()) {
            risk = risk * Float.parseFloat(map.get("re_infection_risk"));
        }
        if (resident.getIsSuper()) {
            risk = 4 * (1 - Float.parseFloat(map.get("K"))) * Float.parseFloat(map.get("broad_risk_rate")) / Float.parseFloat(map.get("K"));
        }
        if (this.calMaskWearRatio()) {
            risk = risk * this.calMaskRisk();
        }
        if (this.calMaskWearRatio()) {
            risk = risk * this.calMaskRisk();
        }
        return risk;
    }

}

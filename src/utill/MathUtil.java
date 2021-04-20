package utill;

import java.util.Random;

public class MathUtil {
    private static final Random randomGen = new Random();
    /**
     * Gaussian distribution
     * u--mean value, sigma--standard deviation
     * StdX = (X-u)/sigma
     * X = sigma * StdX + u
     */
    public static double stdGaussian(double sigma, double u) {
        double X = randomGen.nextGaussian();
        return sigma * X + u;
    }
}

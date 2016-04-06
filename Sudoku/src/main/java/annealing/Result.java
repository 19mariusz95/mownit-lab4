package annealing;

/**
 * Created by Mariusz on 06.04.2016.
 */
public class Result {
    private final double energy;
    private final int iterations;
    private final double temperature;

    public Result(double en1, int i, double temp) {
        this.energy = en1;
        this.iterations = i;
        this.temperature = temp;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getIterations() {
        return iterations;
    }

    public double getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "Result{" +
                "energy=" + energy +
                ", iterations=" + iterations +
                ", temperature=" + temperature +
                '}';
    }
}

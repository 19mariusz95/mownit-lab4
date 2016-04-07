package annealing;

import javafx.scene.chart.XYChart;

/**
 * Created by Mariusz on 06.04.2016.
 */
public class Result {
    private final double energy;
    private final int iterations;
    private final double temperature;
    private final XYChart.Series<Number, Number> energySeries;

    public Result(double en1, int i, double temp, XYChart.Series<Number, Number> energySeries) {
        this.energy = en1;
        this.iterations = i;
        this.temperature = temp;
        this.energySeries = energySeries;
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

    public XYChart.Series<Number, Number> getEnergySeries() {
        return energySeries;
    }
}

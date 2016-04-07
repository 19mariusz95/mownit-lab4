package algorithm;

import algorithm.speed.Speed;
import javafx.scene.chart.XYChart;
import path.Path;
import swaps.Swap;
import visualization.PointsPanel;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class SimulatedAnnealing implements Callable<XYChart.Series<Number, Number>> {
    private static int nextid = 1;
    private List<Point> list;
    private double temp;
    private double minTemp;
    private int maxIteration;
    private Probability probability;
    private TempFunction tempFunction;
    private Swap swap;
    private Random random = new Random();
    private PointsPanel pointsPanel;
    private Speed speed;
    private int id;

    public SimulatedAnnealing(List<Point> list, double temp, double minTemp, int maxIteration, Probability probability,
                              TempFunction tempFunction, Swap swap, PointsPanel pointsPanel, Speed speed) {
        this.id = nextid++;
        this.list = list;
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxIteration = maxIteration;
        this.probability = probability;
        this.tempFunction = tempFunction;
        this.swap = swap;
        this.pointsPanel = pointsPanel;
        this.speed = speed;
    }

    private XYChart.Series<Number, Number> simulate() throws InterruptedException {
        int i = 0;
        Path p = new Path(list);
        Path pp;
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(id == 1 ? "Consecutive" : "Arbitrary");
        while (i < maxIteration && temp > minTemp) {
            double currentEnergy = p.getLength();
            series.getData().add(new XYChart.Data<>(i, currentEnergy));
            pp = new Path(swap.getSwap(p.getList()));
            double delta = pp.getLength() - currentEnergy;
            if (delta < 0 || random.nextDouble() < probability.getProbability(temp, delta)) {
                p = pp;
            }
            temp = tempFunction.newTemp(temp);
            i++;
            pointsPanel.setList(p.getList());
            pointsPanel.repaint();
            speed.slowDown();
        }
        System.out.println("iterations: " + i + " energy " + p.getLength());
        return series;
    }

    @Override
    public XYChart.Series<Number, Number> call() throws Exception {
        return simulate();
    }
}

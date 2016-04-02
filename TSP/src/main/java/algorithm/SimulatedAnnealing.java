package algorithm;

import algorithm.speed.Speed;
import path.Path;
import swaps.Swap;
import visualization.PointsPanel;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class SimulatedAnnealing implements Runnable {
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

    public SimulatedAnnealing(List<Point> list, double temp, double minTemp, int maxIteration, Probability probability,
                              TempFunction tempFunction, Swap swap, PointsPanel pointsPanel, Speed speed) {
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

    private void simulate() throws InterruptedException {
        int i = 0;
        Path p = new Path(list);
        Path pp;
        while (i < maxIteration && temp > minTemp) {
            pp = new Path(swap.getSwap(p.getList()));
            double delta = pp.getLength() - p.getLength();
            if (delta < 0 || random.nextDouble() < probability.getProbability(temp, delta)) {
                p = pp;
            }
            temp = tempFunction.newTemp(temp);
            i++;
            pointsPanel.setList(p.getList());
            pointsPanel.repaint();
            speed.slowDown();
        }
        System.out.println("iterations: " + i + " temperature " + temp);
    }

    @Override
    public void run() {
        try {
            simulate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}

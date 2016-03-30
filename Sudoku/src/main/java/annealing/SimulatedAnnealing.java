package annealing;

import energy.EnergyCounter;

import javax.swing.*;
import java.util.Random;

/**
 * Created by Mariusz on 30.03.2016.
 */
public class SimulatedAnnealing {
    private JLabel[][] labels;
    private JFrame frame;
    private double temp;
    private double minTemp;
    private int maxIteration;
    private Probability probability;
    private TempFunction tempFunction;
    private boolean[][] active;
    private int[][] tab;
    private Random random;
    private EnergyCounter energyCounter;

    public SimulatedAnnealing(JLabel[][] labels, int[][] tab, JFrame frame, double temp, double minTemp, int maxIteration, Probability probability, TempFunction tempFunction, EnergyCounter energyCounter) {
        this.labels = labels;
        this.frame = frame;
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxIteration = maxIteration;
        this.probability = probability;
        this.tempFunction = tempFunction;
        this.energyCounter = energyCounter;
        this.random = new Random();
        this.tab = tab;
        setActive();
    }

    private void setActive() {
        this.active = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int tmp = tab[i][j];
                if (tmp != -1)
                    active[i][j] = false;
                else {
                    active[i][j] = true;
                    int tmp1 = random.nextInt(8) + 1;
                    tab[i][j] = tmp1;
                    labels[i][j].setText(String.valueOf(tmp1));
                }
            }
        }
    }

    public void simulate() throws InterruptedException {
        int i = 0;
        int[][] tab2;
        while (i < maxIteration && temp > minTemp) {
            tab2 = getSwap(tab);
            double delta = energyCounter.getEnergy(tab2) - energyCounter.getEnergy(tab);
            if (delta < 0 || random.nextDouble() < probability.getProbability(temp, delta)) {
                tab = tab2;
                applyToLabels(tab);
            }
            temp = tempFunction.newTemp(temp);
            i++;
            frame.repaint();
            Thread.sleep(10);
        }
        System.out.println("DONE");
    }

    private int[][] getSwap(int[][] tab) {
        int[][] result = tab.clone();
        int a;
        int b;
        do {
            a = random.nextInt(9);
            b = random.nextInt(9);
        } while (!active[a][b]);
        result[a][b] = random.nextInt(8) + 1;
        return result;
    }

    private void applyToLabels(int[][] tab) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                labels[i][j].setText(String.valueOf(tab[i][j]));
            }
        }
    }
}

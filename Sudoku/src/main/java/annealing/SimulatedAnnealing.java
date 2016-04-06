package annealing;

import energy.EnergyCounter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mariusz on 30.03.2016.
 */
public class SimulatedAnnealing {
    private JLabel[][] labels;
    private double temp;
    private double minTemp;
    private int maxIteration;
    private Probability probability;
    private TempFunction tempFunction;
    private List<Integer> active;
    private int[][] tab;
    private Random random;
    private EnergyCounter energyCounter;

    public SimulatedAnnealing(JLabel[][] labels, int[][] tab, double temp, double minTemp, int maxIteration, Probability probability, TempFunction tempFunction, EnergyCounter energyCounter) {
        this.labels = labels;
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
        this.active = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int tmp = tab[i][j];
                if (tmp == 0)
                    active.add(i * 9 + j);
            }
        }
        fillGaps();
    }

    private void fillGaps() {
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                fillSquare(i, j);
            }
        }
    }

    private void fillSquare(int i, int j) {
        int[] values = new int[10];
        for (int k = 0; k < 10; k++) {
            values[k] = 0;
        }
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                int tmp = tab[i + k][j + l];
                if (tmp > 0)
                    values[tmp]++;
            }
        }
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                int tmp = tab[i + k][j + l];
                if (tmp == 0) {
                    int ala;
                    do {
                        ala = random.nextInt(9) + 1;
                    } while (values[ala] > 0);
                    tab[i + k][j + l] = ala;
                    values[ala]++;
                }
            }
        }
        applyToLabels(tab);
    }

    public Result simulate() throws InterruptedException {
        int i = 0;
        int[][] tab2;
        double en1 = energyCounter.getEnergy(tab);
        while (i < maxIteration && temp > minTemp && en1 > 0) {
            tab2 = getSwap(tab);
            double en2 = energyCounter.getEnergy(tab2);
            double delta = en2 - en1;
            if (delta < 0 || random.nextDouble() < probability.getProbability(temp, delta)) {
                tab = tab2;
                applyToLabels(tab2);
                en1 = en2;
            }
            temp = tempFunction.newTemp(temp);
            i++;
        }
        return new Result(en1, i, temp);
    }

    private int[][] getSwap(int[][] tab) {
        int[][] result = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(tab[i], 0, result[i], 0, 9);
        }
        int a = active.get(random.nextInt(active.size()));
        int b = active.get(random.nextInt(active.size()));
        int tmp = result[a / 9][a % 9];
        result[a / 9][a % 9] = result[b / 9][b % 9];
        result[b / 9][b % 9] = tmp;
        return result;
    }

    private void applyToLabels(int[][] tab) {
        if (labels != null)
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    labels[i][j].setText(String.valueOf(tab[i][j]));
                }
            }
    }
}

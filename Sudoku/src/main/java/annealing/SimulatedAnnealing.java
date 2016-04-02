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
                active[i][j] = tmp == 0;
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
                    applyToLabels(tab);
                    values[ala]++;
                }
            }
        }
    }

    public void simulate() throws InterruptedException {
        int i = 0;
        int[][] tab2;
        double en1 = energyCounter.getEnergy(tab);
        while (i < maxIteration && temp > minTemp) {
            tab2 = getSwap(tab);
            double en2 = energyCounter.getEnergy(tab2);
            double delta = en2 - en1;
            if (delta < 0 || random.nextDouble() < probability.getProbability(temp, delta)) {
                tab = tab2;
                applyToLabels(tab2);
                frame.repaint();
                en1 = en2;
                if (en1 == 0) {
                    System.out.println("Solved");
                    break;
                }
            }
            temp = tempFunction.newTemp(temp);
            i++;
            //Thread.sleep(10);
        }
        System.out.println("energy: " + en1 + " iterations: " + i + " temperature: " + temp);
    }

    private int[][] getSwap(int[][] tab) {
        int[][] result = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(tab[i], 0, result[i], 0, 9);
        }
        int a;
        int b;
        do {
            a = random.nextInt(9);
            b = random.nextInt(9);
        } while (!active[a][b]);
        int c;
        int d;
        do {
            c = (a / 3) * 3 + random.nextInt(3);
            d = (b / 3) * 3 + random.nextInt(3);
        } while ((c == a && b == d) || !active[c][d]);
        int tmp = result[a][b];
        result[a][b] = result[c][d];
        result[c][d] = tmp;
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

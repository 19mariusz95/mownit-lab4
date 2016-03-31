package algo;

import energy.Energy;
import neighbourhood.NeighbourhoodStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class SimulatedAnnealing {
    private double temp;
    private double minTemp;
    private int maxIteration;
    private Probability probability;
    private TempFunction tempFunction;
    private Random random = new Random();
    private JPanel[][] jPanels;
    private boolean[][] tab;
    private int n;
    private JFrame frame;
    private Energy energy;
    private NeighbourhoodStrategy strategy;

    public SimulatedAnnealing(double temp, double minTemp, int maxIteration,
                              Probability probability, TempFunction tempFunction,
                              JPanel[][] jPanels, int n, Energy energy, JFrame frame, NeighbourhoodStrategy strategy) {
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxIteration = maxIteration;
        this.probability = probability;
        this.tempFunction = tempFunction;
        this.jPanels = jPanels;
        this.n = n;
        this.frame = frame;
        this.energy = energy;
        this.strategy = strategy;
        initTab1();
    }

    private void initTab1() {
        tab = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tab[i][j] = jPanels[i][j].getBackground() == Color.BLACK;
            }
        }
    }

    public void simulate() {
        int i = 0;
        boolean[][] tp;
        while (i < maxIteration && temp > minTemp) {
            tp = new Swap(tab, n).getSwap();
            double en2 = energy.getEnergy(tp, n, strategy);
            double en = energy.getEnergy(tab, n, strategy);
            double delta = en2 - en;
            System.out.println(en + " " + en2);
            if (delta < 0 || random.nextDouble() < probability.getProbability(temp, delta)) {
                apply(tp);
                frame.repaint();
            }
            temp = tempFunction.newTemp(temp);
            i++;
        }
    }

    private void apply(boolean[][] tp) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tab[i][j] != tp[i][j]) {
                    jPanels[i][j].setBackground(tp[i][j] ? Color.BLACK : Color.WHITE);
                    tab[i][j] = tp[i][j];
                    jPanels[i][j].repaint();
                }
            }
        }
    }
}

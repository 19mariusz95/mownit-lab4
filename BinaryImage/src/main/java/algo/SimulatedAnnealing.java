package algo;

import energy.Energy;

import javax.swing.*;
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
    private JPanel[][] tab;
    private int n;
    private JFrame frame;
    private Energy energy;

    public SimulatedAnnealing(double temp, double minTemp, int maxIteration,
                              Probability probability, TempFunction tempFunction,
                              JPanel[][] tab, int n, Energy energy, JFrame frame) {
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxIteration = maxIteration;
        this.probability = probability;
        this.tempFunction = tempFunction;
        this.tab = tab;
        this.n = n;
        this.frame = frame;
        this.energy = energy;
    }

    public void simulate() {
        int i = 0;
        JPanel[][] tp;
        while (i < maxIteration && temp > minTemp) {
            tp = new Swap(tab, n).getSwap();
            double delta = energy.getEnergy(tp) - energy.getEnergy(tab);
            if (delta < 0 || random.nextDouble() < probability.getProbability(temp, delta)) {
                apply(tab, tp);
            }
            temp = tempFunction.newTemp(temp);
            i++;
            frame.repaint();
        }
    }

    private void apply(JPanel[][] tab, JPanel[][] tp) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tab[i][j].setBackground(tp[i][j].getBackground());
            }
        }
    }
}

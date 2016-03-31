import algo.SimulatedAnnealing;
import energy.Energy;
import energy.EnergyImpl;
import neighbourhood.NeighbourhoodStrategy;
import neighbourhood.NeighbourhoodStrategyImpl;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class MainClass {

    public static void main(String[] args) {


        Properties properties = null;
        try {
            properties = readProperties();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        int n = Integer.parseInt(properties.getProperty("n", "100"));
        double sigma = Double.parseDouble(properties.getProperty("sigma", "0.3"));
        Energy energy = getEnergy(properties.getProperty("energy", "OPTION1"));
        NeighbourhoodStrategy strategy = getNeighbourhoodStrategy(properties.getProperty("neighbourhood", "OPTION1"));


        JFrame frame = initFrame(n);
        JPanel[][] tab = getPanels(n, sigma, frame);

        frame.setVisible(true);

        SimulatedAnnealing simulatedAnnealing = getSimulatedAnnealing(n, frame, tab, strategy, energy);

        simulatedAnnealing.simulate();

    }

    private static Energy getEnergy(String property) {
        try {
            return EnergyImpl.valueOf(property);
        } catch (IllegalArgumentException e) {
            return EnergyImpl.OPTION1;
        }
    }

    private static Properties readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("properties.txt"));
        return properties;
    }

    private static SimulatedAnnealing getSimulatedAnnealing(int n, JFrame frame, JPanel[][] tab, NeighbourhoodStrategy strategy, Energy energy) {
        return new SimulatedAnnealing(200, Double.MIN_VALUE, 1000000, (temp, delta) -> Math.exp(-delta / temp),
                temp -> 0.9 * temp, tab, n, energy, frame, strategy);
    }

    private static NeighbourhoodStrategy getNeighbourhoodStrategy(String property) {
        try {
            return NeighbourhoodStrategyImpl.valueOf(property);
        } catch (IllegalArgumentException e) {
            return NeighbourhoodStrategyImpl.OPTION1;
        }
    }

    private static JPanel[][] getPanels(int n, double sigma, JFrame frame) {
        JPanel[][] tab = new JPanel[n][n];
        Random random = new Random();
        for (int i = 0; i < n * n; i++) {
            JPanel p = new JPanel();
            if (random.nextDouble() <= sigma)
                p.setBackground(Color.BLACK);
            else
                p.setBackground(Color.WHITE);
            frame.add(p);
            tab[i / n][i % n] = p;
        }
        return tab;
    }

    private static JFrame initFrame(int n) {
        JFrame frame = new JFrame("BinaryImage");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        GridLayout gridLayout = new GridLayout(n, n);
        frame.setLayout(gridLayout);
        return frame;
    }
}

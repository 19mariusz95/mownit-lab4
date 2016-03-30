import algorithm.ArbitrarySwap;
import algorithm.ConsecutiveSwap;
import algorithm.SimulatedAnnealing;
import algorithm.Swap;
import generator.GenStr;
import generator.GenStrategy;
import generator.PointGenerator;
import visualization.PointsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class MainClass {


    public static void main(String[] args) {

        List<Point> list = new ArrayList<>();
        Properties properties = null;
        try {
            properties = readProperties();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        GenStrategy genStrategy = getStrategy(properties);

        PointGenerator generator = new PointGenerator(genStrategy, Integer.parseInt(properties.getProperty("n", "50")), 50, 50);
        generator.generate(list);

        SimulatedAnnealing simulatedAnnealing = getSimulatedAnnealing(list, properties, "Consecutive swap", new ConsecutiveSwap(), 0, 0);
        SimulatedAnnealing simulatedAnnealing1 = getSimulatedAnnealing(new ArrayList<>(list), properties, "Arbitrary swap", new ArbitrarySwap(), 600, 0);

        simulatedAnnealing.start();
        simulatedAnnealing1.start();

    }

    private static Properties readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("properties.txt"));
        return properties;
    }

    private static GenStrategy getStrategy(Properties arg) {
        try {
            return GenStr.valueOf(arg.getProperty("distribution", "UNIFORM"));
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return GenStr.UNIFORM;
        }
    }

    private static SimulatedAnnealing getSimulatedAnnealing(List<Point> list, Properties properties, String title, Swap swap, int x, int y) {
        PointsPanel pointsPanel = initFrame(list, title, x, y);
        double T = Double.parseDouble(properties.getProperty("T", "200"));
        double minT = Double.parseDouble(properties.getProperty("minT", "200"));
        int maxit = Integer.parseInt(properties.getProperty("maxiteration", "100"));
        return new SimulatedAnnealing(list, T, minT, maxit, (temp, delta) -> Math.exp(-delta / temp),
                temp -> 0.9 * temp, swap, pointsPanel);
    }

    private static PointsPanel initFrame(List<Point> list, String title, int x, int y) {
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(600, 600));
        frame.setLocation(x, y);
        PointsPanel pointsPanel = new PointsPanel(list, 10);
        frame.add(pointsPanel);
        frame.setVisible(true);
        pointsPanel.repaint();
        return pointsPanel;
    }

}

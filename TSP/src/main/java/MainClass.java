import algorithm.ArbitrarySwap;
import algorithm.ConsecutiveSwap;
import algorithm.SimulatedAnnealing;
import algorithm.Swap;
import generator.GenStrategy;
import generator.PointGenerator;
import visualization.PointsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class MainClass {


    public static void main(String[] args) {

        List<Point> list = new ArrayList<>();

        PointGenerator generator = new PointGenerator(new GenStrategy() {
            private Random random = new Random();

            @Override
            public Point getPoint(int maxa, int maxb) {
                return new Point(random.nextInt(maxa), random.nextInt(maxb));
            }
        }, 10, 50, 50);
        generator.generate(list);

        SimulatedAnnealing simulatedAnnealing = getSimulatedAnnealing(list, "Consecutive swap", new ConsecutiveSwap(), 0, 0);
        SimulatedAnnealing simulatedAnnealing1 = getSimulatedAnnealing(new ArrayList<>(list), "Arbitrary swap", new ArbitrarySwap(), 600, 0);

        simulatedAnnealing.start();
        simulatedAnnealing1.start();

    }

    private static SimulatedAnnealing getSimulatedAnnealing(List<Point> list, String title, Swap swap, int x, int y) {
        PointsPanel pointsPanel = initFrame(list, title, x, y);
        return new SimulatedAnnealing(list, 200, 0.0, 100, (temp, delta) -> Math.exp(-delta / temp),
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

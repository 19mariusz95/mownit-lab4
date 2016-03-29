import algorithm.ArbitrarySwap;
import algorithm.SimulatedAnnealing;
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

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600, 600));
        frame.setLocationRelativeTo(null);
        PointsPanel pointsPanel = new PointsPanel(list, 10);
        frame.add(pointsPanel);
        frame.setVisible(true);
        pointsPanel.repaint();

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(list, 200, 0.0, 100, (temp, delta) -> Math.exp(-delta / temp),
                temp -> 0.9 * temp, new ArbitrarySwap(), pointsPanel);

        try {
            simulatedAnnealing.simulate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

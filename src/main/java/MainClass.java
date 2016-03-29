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
        frame.setSize(new Dimension(500, 500));
        PointsPanel pointsPanel = new PointsPanel(list, 10);
        frame.add(pointsPanel);
        frame.setVisible(true);
        pointsPanel.repaint();


    }
}

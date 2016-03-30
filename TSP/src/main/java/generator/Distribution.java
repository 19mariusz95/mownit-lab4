package generator;

import java.awt.*;
import java.util.Random;

/**
 * Created by Mariusz on 30.03.2016.
 */
public enum Distribution implements GenStrategy {
    UNIFORM {
        @Override
        public Point getPoint(int maxa, int maxb) {
            return new Point(random.nextInt(maxa), random.nextInt(maxb));
        }
    }, NORMAL {
        @Override
        public Point getPoint(int maxa, int maxb) {
            int a = (int) (maxa * random.nextGaussian() / 2);
            int b = (int) (maxb * random.nextGaussian() / 2);
            a = Math.abs(a);
            b = Math.abs(b);
            return new Point(a, b);
        }
    }, GROUPS {
        Point[] centers;

        @Override
        public Point getPoint(int maxa, int maxb) {
            try {
                Point c = centers[random.nextInt(9)];
                double r = 3;
                double angle = 2 * Math.PI * random.nextDouble();
                int a = (int) (c.x + r * Math.cos(angle));
                int b = (int) (c.y + r * Math.sin(angle));
                return new Point(a, b);
            } catch (NullPointerException e) {
                centers = new Point[9];
                for (int i = 0; i < 9; i++) {
                    centers[i] = new Point(random.nextInt(maxa - 2) + 2, random.nextInt(maxb - 2) + 2);
                }
                return getPoint(maxa, maxb);
            }
        }
    };
    Random random = new Random();

}

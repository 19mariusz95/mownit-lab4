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
        @Override
        public Point getPoint(int maxa, int maxb) {
            return null;
        }
    };
    Random random = new Random();

}

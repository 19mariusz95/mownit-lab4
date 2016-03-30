package generator;

import java.awt.*;
import java.util.Random;

/**
 * Created by Mariusz on 30.03.2016.
 */
public enum GenStr implements GenStrategy {
    UNIFORM {
        private Random random = new Random();

        @Override
        public Point getPoint(int maxa, int maxb) {
            return new Point(random.nextInt(maxa), random.nextInt(maxb));
        }
    }, NORMAL {
        @Override
        public Point getPoint(int maxa, int maxb) {
            return null;
        }
    }, GROUPS {
        @Override
        public Point getPoint(int maxa, int maxb) {
            return null;
        }
    };
}

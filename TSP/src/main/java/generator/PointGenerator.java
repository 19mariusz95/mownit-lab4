package generator;


import java.awt.*;
import java.util.List;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class PointGenerator {
    private GenStrategy strategy;
    private int n;
    private int maxa;
    private int maxb;

    public PointGenerator(GenStrategy strategy, int n, int maxa, int maxb) {
        this.strategy = strategy;
        this.n = n;
        this.maxa = maxa;
        this.maxb = maxb;
    }

    public List<Point> generate(List<Point> result) {
        for (int i = 0; i < n; i++) {
            result.add(strategy.getPoint(maxa, maxb));
        }
        return result;
    }
}

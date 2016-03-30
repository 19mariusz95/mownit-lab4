package swaps;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class ArbitrarySwap implements Swap {
    @Override
    public List<Point> getSwap(List<Point> list) {
        List<Point> result = new ArrayList<>(list);
        int n = result.size();
        Random random = new Random();
        int a = random.nextInt(n);
        int b = random.nextInt(n);
        swapPoints(result, a, b);
        return result;
    }

    void swapPoints(List<Point> result, int a, int b) {
        Point p1 = result.get(a);
        Point p2 = result.get(b);
        result.set(a, p2);
        result.set(b, p1);
    }
}

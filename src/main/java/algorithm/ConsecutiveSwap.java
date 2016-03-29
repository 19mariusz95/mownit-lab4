package algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class ConsecutiveSwap extends ArbitrarySwap {
    @Override
    public List<Point> getSwap(List<Point> list) {
        List<Point> result = new ArrayList<>(list);
        int n = result.size();
        Random random = new Random();
        int a = random.nextInt(n);
        int b = (a + 1) % n;
        swapPoints(result, a, b);
        return result;
    }
}

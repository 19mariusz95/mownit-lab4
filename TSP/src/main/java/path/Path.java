package path;

import java.awt.*;
import java.util.List;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class Path {
    private List<Point> list;

    public Path(List<Point> list) {
        this.list = list;
    }

    public double getLength() {
        double result = 0.0;
        int n = list.size();
        for (int i = 0; i < n; i++) {
            Point p1 = list.get(i);
            Point p2 = list.get((i + 1) % n);
            result += (Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2)));
        }
        return result;
    }

    public List<Point> getList() {
        return list;
    }
}

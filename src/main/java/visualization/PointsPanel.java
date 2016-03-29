package visualization;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class PointsPanel extends JPanel {
    private List<Point> list;
    private int side;

    public PointsPanel(List<Point> list, int side) {
        this.list = list;
        this.side = side;
    }

    @Override
    public void update(Graphics g) {
        list.forEach(f -> g.fillOval(f.x * side, f.y * side, side, side));
        for (int i = 0; i < list.size(); i++) {
            int a = i;
            int b = (i + 1) % list.size();
            Point p1 = list.get(a);
            Point p2 = list.get(b);
            g.drawLine(p1.x * side + side / 2, p1.y * side + side / 2, p2.x * side + side / 2, p2.y * side + side / 2);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        update(g);
    }
}

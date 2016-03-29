package neighbourhood;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class Neighbourhood {
    private JPanel[][] tab;
    private int n;
    private int a;
    private int b;
    private NeighbourhoodStrategy strategy;

    public Neighbourhood(JPanel[][] tab, int n, int a, int b, NeighbourhoodStrategy strategy) {
        this.tab = tab;
        this.n = n;
        this.a = a;
        this.b = b;
        this.strategy = strategy;
    }

    public List<JPanel> getNeighbourhood() {
        List<JPanel> result = new ArrayList<>();
        strategy.find(result, tab, a, b, n);
        return result;
    }
}

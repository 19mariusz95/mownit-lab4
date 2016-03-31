package neighbourhood;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class Neighbourhood {
    private boolean[][] tab;
    private int n;
    private int a;
    private int b;
    private NeighbourhoodStrategy strategy;

    public Neighbourhood(boolean[][] tab, int n, int a, int b, NeighbourhoodStrategy strategy) {
        this.tab = tab;
        this.n = n;
        this.a = a;
        this.b = b;
        this.strategy = strategy;
    }

    public List<Neighbour> getNeighbourhood() {
        List<Neighbour> result = new ArrayList<>();
        strategy.find(result, tab, a, b, n);
        return result;
    }
}

package neighbourhood;

import java.util.List;

/**
 * Created by Mariusz on 29.03.2016.
 */
public interface NeighbourhoodStrategy {
    void find(List<Neighbour> result, boolean[][] tab, int a, int b, int n);
}

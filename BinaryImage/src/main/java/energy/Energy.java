package energy;

import neighbourhood.NeighbourhoodStrategy;

/**
 * Created by Mariusz on 29.03.2016.
 */
public interface Energy {
    double getEnergy(boolean[][] tab, int n, NeighbourhoodStrategy strategy);
}

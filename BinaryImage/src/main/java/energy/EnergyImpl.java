package energy;

import neighbourhood.Neighbour;
import neighbourhood.Neighbourhood;
import neighbourhood.NeighbourhoodStrategy;

/**
 * Created by Mariusz on 31.03.2016.
 */
public enum EnergyImpl implements Energy {

    OPTION1 {
        @Override
        double getResult(boolean[][] tab, int n, NeighbourhoodStrategy strategy, double result, int i, int j) {
            Neighbourhood neighbourhood = new Neighbourhood(tab, n, i, j, strategy);
            for (Neighbour nn : neighbourhood.getNeighbourhood()) {
                if (Math.abs(i - nn.getRow()) < 2 && Math.abs(j - nn.getColumn()) < 2 && tab[i][j] == tab[nn.getRow()][nn.getColumn()]) {
                    result -= 2.8 * Math.sqrt(Math.pow(Math.abs(n / 2 - nn.getRow()), 2) + Math.pow(Math.abs(n / 2 - nn.getColumn()), 2));
                } else
                    result += 1.3;
            }
            return result;
        }
    }, OPTION2 {
        @Override
        double getResult(boolean[][] tab, int n, NeighbourhoodStrategy strategy, double result, int i, int j) {
            Neighbourhood neighbourhood = new Neighbourhood(tab, n, i, j, strategy);
            for (Neighbour nn : neighbourhood.getNeighbourhood()) {
                if (Math.abs(i - nn.getRow()) < 2 && Math.abs(j - nn.getColumn()) < 2 && tab[i][j] == tab[nn.getRow()][nn.getColumn()]) {
                    result -= 2.8;
                } else
                    result += 1.3;
            }
            return result;
        }
    }, OPTION3 {
        @Override
        double getResult(boolean[][] tab, int n, NeighbourhoodStrategy strategy, double result, int i, int j) {
            Neighbourhood neighbourhood = new Neighbourhood(tab, n, i, j, strategy);
            for (Neighbour nn : neighbourhood.getNeighbourhood()) {
                if (Math.abs(i - nn.getRow()) < 2 && Math.abs(j - nn.getColumn()) < 2 && tab[i][j] == tab[nn.getRow()][nn.getColumn()]) {
                    result += 2.8 * Math.sqrt(Math.pow(Math.abs(n / 2 - nn.getRow()), 2) + Math.pow(Math.abs(n / 2 - nn.getColumn()), 2));
                } else
                    result -= 1.3;
            }
            return result;
        }

    }, OPTION4 {
        double getResult(boolean[][] tab, int n, NeighbourhoodStrategy strategy, double result, int i, int j) {
            Neighbourhood neighbourhood = new Neighbourhood(tab, n, i, j, strategy);
            for (Neighbour nn : neighbourhood.getNeighbourhood()) {
                if (nn.getColumn() != j && nn.getRow() != i) {
                    result -= 2.4;
                } else
                    result += 1.3;
            }
            return result;
        }
    };

    @Override
    public double getEnergy(boolean[][] tab, int n, NeighbourhoodStrategy strategy) {
        double result = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result = getResult(tab, n, strategy, result, i, j);
            }
        }
        return result;
    }

    abstract double getResult(boolean[][] tab, int n, NeighbourhoodStrategy strategy, double result, int i, int j);
}

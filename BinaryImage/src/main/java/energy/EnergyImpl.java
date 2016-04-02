package energy;

import neighbourhood.Neighbour;
import neighbourhood.Neighbourhood;
import neighbourhood.NeighbourhoodStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariusz on 31.03.2016.
 */
public enum EnergyImpl implements Energy {

    OPTION1 {
        @Override
        double getResult(boolean[][] tab, int n, double result, int i, int j, Neighbour nn) {
            if (tab[i][j] == tab[nn.getRow()][nn.getColumn()]) {
                result -= 2.8 * Math.sqrt(Math.pow(Math.abs(n / 2 - nn.getRow()), 2) + Math.pow(Math.abs(n / 2 - nn.getColumn()), 2));
            } else
                result += 1.3;
            return result;
        }
    }, OPTION2 {
        @Override
        double getResult(boolean[][] tab, int n, double result, int i, int j, Neighbour nn) {
            if (Math.abs(i - nn.getRow()) < 2 && Math.abs(j - nn.getColumn()) < 2 && tab[i][j] == tab[nn.getRow()][nn.getColumn()]) {
                result -= 2.8;
            } else
                result += 1.3;
            return result;
        }
    }, OPTION3 {
        double getResult(boolean[][] tab, int n, double result, int i, int j, Neighbour nn) {
            if (Math.abs(i - nn.getRow()) < 2 && Math.abs(j - nn.getColumn()) < 2 && tab[i][j] == tab[nn.getRow()][nn.getColumn()]) {
                result += 2.8 * Math.sqrt(Math.pow(Math.abs(n / 2 - nn.getRow()), 2) + Math.pow(Math.abs(n / 2 - nn.getColumn()), 2));
            } else
                result -= 1.3;
            return result;
        }

    }, OPTION4 {
        @Override
        double getResult(boolean[][] tab, int n, double result, int i, int j, Neighbour nn) {
            if (tab[nn.getRow()][nn.getColumn()] && (nn.getRow() / 4) % 2 == 0)
                result -= 2.0;
            else
                result += 1.3;
            return result;
        }
    }, OPTION5 {
        List<Neighbour> fibs;

        @Override
        double getResult(boolean[][] tab, int n, double result, int i, int j, Neighbour nn) {
            try {
                double mind = 30.0;
                if (tab[nn.getRow()][nn.getColumn()])
                    mind = getMin(tab, nn, fibs, mind);
                else
                    result += 10.0;
                result += mind;
                return result;
            } catch (NullPointerException e) {
                fibs = initFibs(n);
                return getResult(tab, n, result, i, j, nn);
            }
        }

        private List<Neighbour> initFibs(int n) {
            List<Neighbour> list = new ArrayList<>(4);
            list.add(new Neighbour(n / 4, n / 4));
            list.add(new Neighbour(3 * n / 4, n / 4));
            list.add(new Neighbour(n / 4, 3 * n / 4));
            list.add(new Neighbour(3 * n / 4, 3 * n / 4));
            return list;
        }
    }, OPTION6 {
        List<Neighbour> fibs;

        @Override
        double getResult(boolean[][] tab, int n, double result, int i, int j, Neighbour nn) {
            try {
                double mind = 30.0;
                if (tab[nn.getRow()][nn.getColumn()])
                    mind = getMin(tab, nn, fibs, mind);
                else
                    result += 10.0;

                result += mind;
                return result;
            } catch (NullPointerException e) {
                initFibs(n);
                return getResult(tab, n, result, i, j, nn);
            }
        }

        private void initFibs(int n) {
            fibs = new ArrayList<>();
            int a = 1;
            int b = 1;
            do {
                fibs.add(new Neighbour(b / n, b % n));
                int tmp = b;
                b = a + b;
                a = tmp;
            } while (b < n * n);
        }
    };

    static private double getDistance(int i, int j, int row, int column) {
        return Math.sqrt(Math.pow(i - row, 2) + Math.pow(j - column, 2));
    }

    static double getMin(boolean[][] tab, Neighbour nn, List<Neighbour> list, double mind) {
        for (Neighbour t : list) {
            double d = getDistance(nn.getRow(), nn.getColumn(), t.getRow(), t.getColumn());
            if (d < mind) {
                mind = d;
            }
        }
        return mind;
    }

    @Override
    public double getEnergy(boolean[][] tab, int n, NeighbourhoodStrategy strategy) {
        double result = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Neighbourhood neighbourhood = new Neighbourhood(tab, n, i, j, strategy);
                for (Neighbour nn : neighbourhood.getNeighbourhood()) {
                    result = getResult(tab, n, result, i, j, nn);
                }
            }
        }
        return result;
    }

    abstract double getResult(boolean[][] tab, int n, double result, int i, int j, Neighbour nn);
}

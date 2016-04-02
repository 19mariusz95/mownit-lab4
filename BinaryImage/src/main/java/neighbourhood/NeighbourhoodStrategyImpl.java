package neighbourhood;

import java.util.List;

/**
 * Created by Mariusz on 31.03.2016.
 */
public enum NeighbourhoodStrategyImpl implements NeighbourhoodStrategy {
    OPTION4 {
        @Override
        public void find(List<Neighbour> result, boolean[][] tab, int a, int b, int n) {
            for (int i = a - 2; i < a + 3; i++) {
                for (int j = b - 2; j < b + 3; j++) {
                    if (good(i, j, n) && notEquals(a, b, i, j))
                        result.add(new Neighbour(i, j));
                }
            }
        }
    },
    OPTION3 {
        @Override
        public void find(List<Neighbour> result, boolean[][] tab, int a, int b, int n) {
            for (int i = a - 2; i < a + 3; i++) {
                for (int j = b - 2; j < b + 3; j++) {
                    if ((i == a || j == b) && good(i, j, n) && notEquals(a, b, i, j))
                        result.add(new Neighbour(i, j));
                }
            }
        }
    }, OPTION2 {
        @Override
        public void find(List<Neighbour> result, boolean[][] tab, int a, int b, int n) {
            for (int i = a - 1; i < a + 2; i++) {
                for (int j = b - 1; j < b + 2; j++) {
                    if (good(i, j, n) && notEquals(a, b, i, j))
                        result.add(new Neighbour(i, j));
                }
            }
        }
    }, OPTION1 {
        @Override
        public void find(List<Neighbour> result, boolean[][] tab, int a, int b, int n) {
            for (int i = a - 1; i < a + 2; i++) {
                for (int j = b - 1; j < b + 2; j++) {
                    if ((i == a || j == b) && good(i, j, n) && notEquals(a, b, i, j))
                        result.add(new Neighbour(i, j));
                }
            }
        }
    };

    private static boolean notEquals(int a, int b, int c, int d) {
        return a != b || c != d;
    }

    private static boolean good(int a, int b, int n) {
        return a >= 0 && a < n && b >= 0 && b < n;
    }
}

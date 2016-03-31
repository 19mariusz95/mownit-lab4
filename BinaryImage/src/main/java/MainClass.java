import algo.SimulatedAnnealing;
import neighbourhood.Neighbour;
import neighbourhood.Neighbourhood;
import neighbourhood.NeighbourhoodStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class MainClass {

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        double sigma = Double.parseDouble(args[1]);

        JFrame frame = initFrame(n);
        JPanel[][] tab = getPanels(n, sigma, frame);

        frame.setVisible(true);

        NeighbourhoodStrategy strategy = getNeighbourhoodStrategy(n);

        SimulatedAnnealing simulatedAnnealing = getSimulatedAnnealing(n, frame, tab, strategy);

        simulatedAnnealing.simulate();

    }

    private static SimulatedAnnealing getSimulatedAnnealing(int n, JFrame frame, JPanel[][] tab, NeighbourhoodStrategy strategy) {
        return new SimulatedAnnealing(200, 0.0, 1000000, (temp, delta) -> Math.exp(-delta / temp),
                temp -> 0.9 * temp, tab, n, tab1 -> {
            double result = 0.0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (tab1[i][j]) {
                        Neighbourhood neighbourhood = new Neighbourhood(tab1, n, i, j, strategy);
                        for (Neighbour nn : neighbourhood.getNeighbourhood()) {
                            if (Math.abs(i - nn.getRow()) < 2 && Math.abs(j - nn.getColumn()) < 2 && tab1[nn.getRow()][nn.getColumn()]) {
                                result -= 2.8;
                            } else
                                result += 1.3;
                        }
                    }
                }
            }
            return result;
        }, frame);
    }

    private static NeighbourhoodStrategy getNeighbourhoodStrategy(final int n) {
        return new NeighbourhoodStrategy() {
            @Override
            public void find(List<Neighbour> result, boolean[][] tab, int a, int b, int n1) {
                for (int i = a - 2; i < a + 3; i++) {
                    for (int j = b - 2; j < b + 3; j++) {
                        if (good(i, j))
                            result.add(new Neighbour(i, j));
                    }
                }
            }

            private boolean good(int a, int b) {
                return a >= 0 && a < n && b >= 0 && b < n;
            }
        };
    }

    private static JPanel[][] getPanels(int n, double sigma, JFrame frame) {
        JPanel[][] tab = new JPanel[n][n];
        Random random = new Random();
        for (int i = 0; i < n * n; i++) {
            JPanel p = new JPanel();
            if (random.nextDouble() <= sigma)
                p.setBackground(Color.BLACK);
            frame.add(p);
            tab[i / n][i % n] = p;
        }
        return tab;
    }

    private static JFrame initFrame(int n) {
        JFrame frame = new JFrame("ala ma kota");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        GridLayout gridLayout = new GridLayout(n, n);
        frame.setLayout(gridLayout);
        return frame;
    }
}

import algo.SimulatedAnnealing;
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
                    Neighbourhood neighbourhood = new Neighbourhood(tab1,n,i,j,getNeighbourhoodStrategy(n));
                    for (JPanel p :neighbourhood.getNeighbourhood())
                        if(p.getBackground().equals(tab[i][j].getBackground()))
                            result+=2.5;
                        else
                            result-=1.0;
                }
            }
            return result;
        }, frame);
    }

    private static NeighbourhoodStrategy getNeighbourhoodStrategy(final int n) {
        return new NeighbourhoodStrategy() {
            @Override
            public void find(List<JPanel> result, JPanel[][] tab, int a, int b, int n1) {
                int[] tmp = {0, -1, 0, 1};
                for (int i = 0; i < 4; i++) {
                    if (good(a + tmp[i], b + tmp[i]))
                        result.add(tab[a + tmp[i]][b + tmp[i]]);
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

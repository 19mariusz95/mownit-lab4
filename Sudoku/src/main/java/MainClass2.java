import annealing.SimulatedAnnealing;
import energy.EnergyCounterImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

/**
 * Created by Mariusz on 06.04.2016.
 */
public class MainClass2 {

    public static void main(String[] args) throws FileNotFoundException {
        Properties properties = null;
        try {
            properties = MainClass.readProperties();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        double T = Double.parseDouble(properties.getProperty("T", "200"));
        double minT = Double.parseDouble(properties.getProperty("minT", "1.0"));
        int maxit = Integer.parseInt(properties.getProperty("maxiteration", "100"));
        int[][] tmp = MainClass.readSudoku("test.txt");
        for (int i = 1; i <= 81; i++) {
            int[][] tmp2 = generateWithGaps(tmp, i);
            SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(null, tmp2, T, minT, maxit, (temp, delta) -> Math.exp(-delta / temp),
                    temp -> 0.997 * temp, new EnergyCounterImpl());

            try {
                System.out.println(i + " " + simulatedAnnealing.simulate());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static int[][] generateWithGaps(int[][] tmp, int i) {
        int[][] tab2 = new int[9][9];
        Random random = new Random();
        for (int j = 0; j < 9; j++) {
            System.arraycopy(tmp[j], 0, tab2[j], 0, 9);
        }
        for (int j = 0; j < i; j++) {
            int ala;
            do {
                ala = random.nextInt(81);
            } while (tab2[ala / 9][ala % 9] == 0);
            tab2[ala / 9][ala % 9] = 0;
        }
        return tab2;
    }

    private static int[][] getTabForTests() {
        int[][] tab = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tab[i][j] = (i + j + 2 * (i % 3)) % 9 + 1;
            }
        }
        return tab;
    }
}

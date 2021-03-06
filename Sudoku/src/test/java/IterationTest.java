import annealing.Result;
import annealing.SimulatedAnnealing;
import energy.EnergyCounterImpl;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;


/**
 * Created by Mariusz on 06.04.2016.
 */
public class IterationTest {

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

    XYChart.Series<Number, Number> getSeries() throws FileNotFoundException {
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
        int[][] tmp = MainClass.readSudoku(new File(IterationTest.class.getResource("test.txt").getFile()));
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 1; i <= 81; i++) {
            int[][] tmp2 = generateWithGaps(tmp, i);
            SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(null, tmp2, T, minT, maxit, (temp, delta) -> Math.exp(-delta / temp),
                    temp -> 0.997 * temp, new EnergyCounterImpl());

            try {
                Result result = simulatedAnnealing.simulate();
                series.getData().add(new XYChart.Data<>(i, result.getIterations()));
                System.out.println(i + " " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return series;
    }
}

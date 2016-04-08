import annealing.Result;
import annealing.SimulatedAnnealing;
import energy.EnergyCounterImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Mariusz on 06.04.2016.
 */
public class App extends Application {

    private static XYChart.Series<Number, Number> series;

    public static void main(String[] args) throws FileNotFoundException {
        String filename = args[0];
        Properties properties = null;
        try {
            properties = readProperties();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        int[][] tab = readSudoku(new File(filename));

        JFrame frame = new JFrame("Sudoku");
        JLabel[][] labels = initFrame(tab, 9, frame);

        double T = Double.parseDouble(properties.getProperty("T", "200"));
        double minT = Double.parseDouble(properties.getProperty("minT", "1.0"));
        int maxit = Integer.parseInt(properties.getProperty("maxiteration", "100"));
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(labels, tab, T, minT, maxit, (temp, delta) -> Math.exp(-delta / temp),
                temp -> 0.997 * temp, new EnergyCounterImpl());

        try {
            Result result = simulatedAnnealing.simulate();
            System.out.println(result);
            series = result.getEnergySeries();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        launch(args);
    }

    private static JLabel[][] initFrame(int[][] tab, int n, JFrame frame) {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        GridLayout gridLayout = new GridLayout(n, n);
        frame.setLayout(gridLayout);
        JLabel[][] labels = new JLabel[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                createLabel(tab[i][j], frame, labels, i, j);
            }
        }
        frame.setVisible(true);
        return labels;
    }

    private static void createLabel(int val, JFrame frame, JLabel[][] labels, int i, int j) {
        String l = String.valueOf(val);
        Color color = l.equals("0") ? Color.RED : Color.decode("#008000");
        JLabel label = new JLabel(l, SwingConstants.CENTER);
        label.setForeground(color);
        label.setBorder(new LineBorder(Color.BLACK));
        labels[i][j] = label;
        frame.add(label);
    }

    protected static Properties readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("properties.txt"));
        return properties;
    }

    protected static int[][] readSudoku(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int[][] result = new int[9][9];
        int tmp = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                try {
                    result[tmp][i] = Integer.parseInt(line.substring(i, i + 1));
                } catch (NumberFormatException e) {
                    result[tmp][i] = 0;
                }
            }
            tmp++;
        }
        return result;
    }

    @Override
    public void start(Stage stage) {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }
}
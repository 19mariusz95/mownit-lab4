import algorithm.SimulatedAnnealing;
import algorithm.speed.Speed;
import algorithm.speed.SpeedImpl;
import generator.Distribution;
import generator.GenStrategy;
import generator.PointGenerator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import swaps.ArbitrarySwap;
import swaps.ConsecutiveSwap;
import swaps.Swap;
import visualization.PointsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Mariusz on 06.04.2016.
 */
public class App extends Application {

    private static List<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException, ExecutionException, InterruptedException {
        java.util.List<Point> list = new ArrayList<>();
        Properties properties = null;
        try {
            properties = readProperties();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        GenStrategy genStrategy = getStrategy(properties);
        Speed speed = getSpeed(properties);

        PointGenerator generator = new PointGenerator(genStrategy, Integer.parseInt(properties.getProperty("n", "50")), 50, 50);
        generator.generate(list);

        SimulatedAnnealing simulatedAnnealing = getSimulatedAnnealing(list, properties, "Consecutive swap", new ConsecutiveSwap(), 0, 0, speed);
        SimulatedAnnealing simulatedAnnealing1 = getSimulatedAnnealing(new ArrayList<>(list), properties, "Arbitrary swap", new ArbitrarySwap(), 600, 0, speed);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<XYChart.Series<Number, Number>> submit = executor.submit(simulatedAnnealing);
        Future<XYChart.Series<Number, Number>> submit1 = executor.submit(simulatedAnnealing1);
        seriesList.add(submit.get());
        seriesList.add(submit1.get());

        launch(args);
    }

    private static Speed getSpeed(Properties properties) {
        try {
            return SpeedImpl.valueOf(properties.getProperty("speed", "FAST"));
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return SpeedImpl.FAST;
        }
    }

    private static Properties readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("properties.txt"));
        return properties;
    }

    private static GenStrategy getStrategy(Properties arg) {
        try {
            return Distribution.valueOf(arg.getProperty("distribution", "UNIFORM"));
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return Distribution.UNIFORM;
        }
    }

    private static SimulatedAnnealing getSimulatedAnnealing(List<Point> list, Properties properties, String title, Swap swap, int x, int y, Speed speed) {
        PointsPanel pointsPanel = initFrame(list, title, x, y);
        double T = Double.parseDouble(properties.getProperty("T", "200"));
        double minT = Double.parseDouble(properties.getProperty("minT", "200"));
        int maxit = Integer.parseInt(properties.getProperty("maxiteration", "100"));
        return new SimulatedAnnealing(list, T, minT, maxit, (temp, delta) -> Math.exp(-delta / temp),
                temp -> 0.997 * temp, swap, pointsPanel, speed);
    }

    private static PointsPanel initFrame(List<Point> list, String title, int x, int y) {
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(600, 600));
        frame.setLocation(x, y);
        PointsPanel pointsPanel = new PointsPanel(list, 10);
        frame.add(pointsPanel);
        frame.setVisible(true);
        pointsPanel.repaint();
        return pointsPanel;
    }

    @Override
    public void start(Stage stage) {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);
        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(seriesList);

        stage.setScene(scene);
        stage.show();
    }
}
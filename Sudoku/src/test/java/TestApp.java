import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * Created by Mariusz on 06.04.2016.
 */
public class TestApp extends Application {

    private static XYChart.Series<Number, Number> series;

    public static void main(String[] args) throws FileNotFoundException {
        series = new IterationTest().getSeries();
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);
        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }
}
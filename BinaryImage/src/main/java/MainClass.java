import javax.swing.*;
import java.awt.*;
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

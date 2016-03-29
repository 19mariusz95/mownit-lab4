package algo;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class Swap {
    int n;
    private JPanel[][] tab;

    public Swap(JPanel[][] tab, int n) {
        this.tab = tab;
        this.n = n;
    }

    public JPanel[][] getSwap() {
        JPanel[][] result = tab.clone();
        Random random = new Random();
        int a1 = random.nextInt(n);
        int a2 = random.nextInt(n);
        int b1 = random.nextInt(n);
        int b2 = random.nextInt(n);
        Color tmp = result[a1][a2].getBackground();
        result[a1][a2].setBackground(result[b1][b2].getBackground());
        result[b1][b2].setBackground(tmp);
        return result;
    }
}

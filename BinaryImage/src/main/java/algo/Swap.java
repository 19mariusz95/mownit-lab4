package algo;

import java.util.Random;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class Swap {
    int n;
    private boolean[][] tab;

    public Swap(boolean[][] tab, int n) {
        this.tab = tab;
        this.n = n;
    }

    public boolean[][] getSwap() {
        boolean[][] result = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tab[i], 0, result[i], 0, n);
        }
        Random random = new Random();
        int a1;
        int a2;
        int b1;
        int b2;
        do {
            a1 = random.nextInt(n);
            a2 = random.nextInt(n);
            b1 = random.nextInt(n);
            b2 = random.nextInt(n);
        } while (result[a1][a2] && result[b1][b2]);
        boolean tmp = result[a1][a2];
        result[a1][a2] = result[b1][b2];
        result[b1][b2] = tmp;
        return result;
    }
}

package energy;

/**
 * Created by Mariusz on 30.03.2016.
 */
public class EnergyCounterImpl implements EnergyCounter {
    public double getEnergy(int[][] tab) {
        double energy = 0.0;
        for (int i = 0; i < 9; i++) {
            energy += findRepeatsInRow(tab, i);
            energy += findRepeatsInColumn(tab, i);
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                energy += findRepeatsInSquare(tab, i, j);
            }
        }
        return energy;
    }

    private double findRepeatsInSquare(int[][] tab, int i, int j) {
        int[] values = getValues();
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                int tmp = tab[i + k][j + l];
                values[tmp]++;
            }
        }
        return getResult(values);
    }

    private double findRepeatsInColumn(int[][] tab, int i) {
        int[] values = getValues();
        for (int j = 0; j < 9; j++) {
            int tmp = tab[j][i];
            values[tmp]++;
        }
        return getResult(values);
    }

    private double getResult(int[] values) {
        double result = 0.0;
        for (int j = 0; j < 10; j++) {
            if (values[j] > 1)
                result += values[j];
        }
        return result;
    }

    private double findRepeatsInRow(int[][] tab, int i) {
        int[] values = getValues();
        for (int j = 0; j < 9; j++) {
            int tmp = tab[i][j];
            values[tmp]++;
        }
        return getResult(values);
    }

    private int[] getValues() {
        int[] val = new int[10];
        for (int i = 0; i < 10; i++) {
            val[i] = 0;
        }
        return val;
    }
}

package energy;

import javax.swing.*;

/**
 * Created by Mariusz on 30.03.2016.
 */
public class EnergyCounterImpl implements EnergyCounter {
    public double getEnergy(JPanel[][] tab) {
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

    private double findRepeatsInSquare(JPanel[][] tab, int i, int j) {
        int[] values = getValues();
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                int tmp = Integer.parseInt(tab[k][l].getToolTipText());
                values[tmp]++;
            }
        }
        return getResult(values);
    }

    private double findRepeatsInColumn(JPanel[][] tab, int i) {
        int[] values = getValues();
        for (int j = 0; j < 9; j++) {
            int tmp = Integer.parseInt(tab[j][i].getToolTipText());
            values[tmp]++;
        }
        return getResult(values);
    }

    private double getResult(int[] values) {
        double result = 0.0;
        for (int j = 0; j < 9; j++) {
            result += values[j];
        }
        return result;
    }

    private double findRepeatsInRow(JPanel[][] tab, int i) {
        int[] values = getValues();
        for (int j = 0; j < 9; j++) {
            int tmp = Integer.parseInt(tab[i][j].getToolTipText());
            values[tmp]++;
        }
        return getResult(values);
    }

    private int[] getValues() {
        int[] val = new int[9];
        for (int i = 0; i < 9; i++) {
            val[i] = 0;
        }
        return val;
    }
}

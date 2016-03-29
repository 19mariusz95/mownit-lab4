import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Mariusz on 29.03.2016.
 */
public class MainClass {
    public static void main(String[] args) throws FileNotFoundException {
        String filename = args[0];
        int [][] tab= readSudoku(filename);
        int n = tab.length;


        JFrame frame = new JFrame("Sudoku");
        JLabel[][] labels = initFrame(tab,n,frame);

    }

    private static JLabel[][] initFrame(int[][] tab, int n,JFrame frame) {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500,500));
        GridLayout gridLayout = new GridLayout(n,n);
        frame.setLayout(gridLayout);
        JLabel[][] labels = new JLabel[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String l = String.valueOf(tab[i][j]);
                if(l.equals("-1"))
                    l="";
                JLabel label = new JLabel(l,SwingConstants.CENTER);
                label.setBorder(new LineBorder(Color.BLACK));
                labels[i][j]=label;
                frame.add(label);
            }
        }
        frame.setVisible(true);
        return labels;
    }

    private static int[][] readSudoku(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        Integer size = Integer.parseInt(scanner.nextLine());
        if(size%3!=0)
            throw new IllegalArgumentException("3%0 != 0");
        int[][] result = new int[size][size];
        int tmp=0;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                try {
                    result[tmp][i] = Integer.parseInt(line.substring(i, i + 1));
                }catch (NumberFormatException e) {
                    result[tmp][i] = -1;
                }
            }
            tmp++;
        }
        return result;
    }
}

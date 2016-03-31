package neighbourhood;

/**
 * Created by Mariusz on 31.03.2016.
 */
public class Neighbour {
    private int row;
    private int column;

    public Neighbour(int row, int column) {

        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

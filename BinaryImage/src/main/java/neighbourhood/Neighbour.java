package neighbourhood;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighbour neighbour = (Neighbour) o;
        return getRow() == neighbour.getRow() &&
                getColumn() == neighbour.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }

    @Override
    public String toString() {
        return "Neighbour{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

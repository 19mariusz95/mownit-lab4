package neighbourhood;

import javax.swing.*;
import java.util.List;

/**
 * Created by Mariusz on 29.03.2016.
 */
public interface NeighbourhoodStrategy {
    void find(List<JPanel> result, JPanel[][] tab, int a, int b, int n);
}

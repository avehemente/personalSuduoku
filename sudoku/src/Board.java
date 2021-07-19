import java.lang.reflect.Array;
import java.util.*;

public class Board {

    private boolean solved;
    private ArrayList<HashSet<Integer>> filled;
    private int[][] positions;

    public Board() {
        this.solved = false;
        this.positions = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.positions[i][j] = 0;
            }
        }
        this.filled = new ArrayList<HashSet<Integer>>();
        for (int i = 0; i < 27; i++) {
            this.filled.add(new HashSet<Integer>());
        }
    }

    public boolean placeNumber(int entered, int x, int y) {
        //place number at position x, y: (0,0) is bottom left
        //boxes are numbered 0 in the topleft, 8 in the bottom right
        int boxNumber = 0;

        //insert method for getting boxNumber here

        HashSet<Integer> curRow = this.filled.get(9 + x);
        HashSet<Integer> curCol = this.filled.get(y);
        HashSet<Integer> curBox = this.filled.get(18 + boxNumber);
        if (curRow.contains(entered) || curCol.contains(entered) || curBox.contains(entered)) {
            return false;
        }

        curRow.add(entered);
        curCol.add((entered));
        curBox.add(entered);
        this.positions[x][y] = entered;
        return true;
    }

    public boolean removeNumber(int x, int y) {
        int toRemove = this.positions[x][y];
        this.filled.get(y).remove(toRemove);

        return true;
    }

    public boolean isSolved() {
        for (HashSet<Integer> cur: this.filled) {
            for (int i = 1; i <= 9; i++) {
                if (!cur.contains(i)) return false;
            }
        }
        return true;
    }
}

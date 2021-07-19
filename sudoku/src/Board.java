import java.util.*;

public class Board {

    private boolean solved;
    private ArrayList<HashSet<Integer>> filled;
    private int[][] positions;

    /**
     * The Board Constructor
     */
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

    /**
     * Places a number at position x, y on the sudoku board.
     * @param entered The number to be placed.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if the number was successfully placed there.
     */
    public boolean placeNumber(int entered, int x, int y) {
        //place number at position x, y: (0,0) is bottom left
        //boxes are numbered 0 in the bottomleft, 8 in the top right, increases left to right, bottom to top
        int boxNumber = getBoxNumber(x,y);

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

    /**
     * Remove the number at position x, y.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if there was a number to be removed.
     */
    public boolean removeNumber(int x, int y) {
        int toRemove = this.positions[x][y];
        if (toRemove == 0) return false;
        this.filled.get(y).remove(toRemove);
        this.filled.get(9 + x).remove(toRemove);
        this.filled.get(18 + getBoxNumber(x,y)).remove(toRemove);
        return true;
    }

    /**
     * Check if the board is in a solved state.
     * @return True if the board is in a solved state (i.e. all rows, cols, and boxes have 1-9)
     */
    public boolean isSolved() {
        for (HashSet<Integer> cur: this.filled) {
            for (int i = 1; i <= 9; i++) {
                if (!cur.contains(i)) return false;
            }
        }
        return true;
    }

    /**
     * Get the box number of a specified coordinate.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The box number corresponding to the given coordinates.
     */
    public int getBoxNumber(int x, int y) {
        int boxNumber;

        if (y < 3) {
            if (x < 3) boxNumber = 0;
            else if (x < 6) boxNumber = 1;
            else boxNumber = 2;
        }
        else if (y < 6) {
            if (x < 3) boxNumber = 3;
            else if (x < 6) boxNumber = 4;
            else boxNumber = 5;
        }
        else {
            if (x < 3) boxNumber = 6;
            else if (x < 6) boxNumber = 7;
            else boxNumber = 8;
        }

        return boxNumber;
    }

    public void solve() {

    }
}

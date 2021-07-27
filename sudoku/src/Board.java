import java.util.*;

public class Board {

    private boolean solved;
    private int[][] board;
    private boolean[][] fixed;
    private ArrayList<HashMap<Integer, Integer>> filled;

    private int numsFilled;

    /**
     * The Board Constructor
     */
    public Board() {
        this.numsFilled = 0;
        this.solved = false;
        this.board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = 0;
            }
        }
        this.fixed = new boolean[9][9];
        HashMap<Integer, Integer> cur;
        this.filled = new ArrayList<HashMap<Integer, Integer>>();
        for (int i = 0; i < 27; i++) {
            cur = new HashMap<Integer, Integer>();
            for (int j = 1; j <= 9; j++) {
                cur.put(j, 0);
            }
            filled.add(cur);
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
        //place number at position x, y: (0,0) is top left
        //boxes are numbered 0 in the top left, 8 in the bottom right, increases left to right, top to bottom
        if (board[x][y] != 0) {
            removeNumber(x, y);
        }
        int boxNumber = getBoxNumber(x,y);

        HashMap<Integer,Integer> curRow = filled.get(y);
        HashMap<Integer,Integer> curCol = filled.get(9 + x);
        HashMap<Integer,Integer> curBox = filled.get(18 + boxNumber);

        curRow.replace(entered, curRow.get(entered) + 1);
        curCol.replace(entered, curCol.get(entered + 1));
        curBox.replace(entered, curBox.get(entered + 1));
        this.board[x][y] = entered;
        this.numsFilled++;
        return true;
    }

    /**
     * Remove the number at position x, y.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if there was a number to be removed.
     */
    public boolean removeNumber(int x, int y) {
        int toRemove = this.board[x][y];
        if (toRemove == 0) return false;
        int boxNumber = getBoxNumber(x,y);
        filled.get(y).replace(toRemove, filled.get(y).get(toRemove) - 1);
        filled.get(9 + x).replace(toRemove, filled.get(9 + x).get(toRemove) - 1);
        filled.get(18 + boxNumber).replace(toRemove, filled.get(18 + boxNumber).get(toRemove) - 1);
        this.numsFilled--;
        return true;
    }

    public boolean validBoardState() {
        for (HashMap<Integer, Integer> cur: filled) {
            for (int i = 1; i <= 9; i++) {
                if (cur.get(i) > 1) return false;
            }
        }
        return true;
    }
    /**
     * Check if the board is in a solved state.
     * @return True if the board is in a solved state (i.e. all rows, cols, and boxes have 1-9)
     */
    public boolean isSolved() {
        return numsFilled == 81 && validBoardState();
    }

    public boolean solve() {
        return solveHelper(0,0);
    }
    public boolean solveHelper(int i, int j) {
        if (!validBoardState()) return false;
        if (i > 8) return true;
        if (j > 8) return solveHelper(i + 1, 0);
        if (fixed[i][j]) return solveHelper(i, j + 1);

        boolean solved;
        for (int x = 1; x <= 9; x++){
            placeNumber(x, i, j);
            solved = solveHelper(i, j + 1);
            if (solved) return true;
            removeNumber(i, j);
        }
        return false;
    }

    public void generateBoard() {

    }

    /**
     * Get the box number of a specified coordinate.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The box number corresponding to the given coordinates.
     */
    public static int getBoxNumber(int x, int y) {

        if (y < 3) {
            if (x < 3) return 0;
            else if (x < 6) return 1;
            else return 2;
        }
        else if (y < 6) {
            if (x < 3) return 3;
            else if (x < 6) return 4;
            else return 5;
        }
        else {
            if (x < 3) return 6;
            else if (x < 6) return 7;
            else return 8;
        }

    }
}

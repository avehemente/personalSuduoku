import java.util.*;

public class Board {
    private int[][] board;
    private boolean[][] fixed;
    private ArrayList<HashMap<Integer, Integer>> filled;
    private int numsFilled;
    private int[][] exampleBoard = {{5,3,0,0,7,0,0,0,0},
                                    {6,0,0,1,9,5,0,0,0},
                                    {0,9,8,0,0,0,0,6,0},
                                    {8,0,0,0,6,0,0,0,3},
                                    {4,0,0,8,0,3,0,0,1},
                                    {7,0,0,0,2,0,0,0,6},
                                    {0,6,0,0,0,0,2,8,0},
                                    {0,0,0,4,1,9,0,0,5},
                                    {0,0,0,0,8,0,0,7,9}};

    /**
     * Generate an empty board.
     */
    public Board() {
        this.numsFilled = 0;
        this.board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = 0;
            }
        }
        this.fixed = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.fixed[i][j] = false;
            }
        }
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
     * @param i The row number.
     * @param j The col number.
     * @return True if the number was successfully placed there.
     */
    public boolean placeNumber(int entered, int i, int j) {
        //place number at position row i, col j in the board matrix
        //boxes are numbered 0 in the top left, 8 in the bottom right, increases left to right, top to bottom
        if (fixed[i][j]) return false;
        if (board[i][j] != 0) {
            removeNumber(i, j);
        }
        int boxNumber = getBoxNumber(i,j);

        HashMap<Integer,Integer> curRow = filled.get(i);
        HashMap<Integer,Integer> curCol = filled.get(9 + j);
        HashMap<Integer,Integer> curBox = filled.get(18 + boxNumber);

        curRow.replace(entered, curRow.get(entered) + 1);
        curCol.replace(entered, curCol.get(entered) + 1);
        curBox.replace(entered, curBox.get(entered)+ 1);
        this.board[i][j] = entered;
        this.numsFilled++;
        return true;
    }

    /**
     * Remove the number at position x, y.
     * @param i The row number.
     * @param j The col number.
     * @return True if there was a number to be removed.
     */
    public boolean removeNumber(int i, int j) {
        int toRemove = this.board[i][j];
        if (toRemove == 0 || fixed[i][j]) return false;
        this.board[i][j] = 0;
        int boxNumber = getBoxNumber(i,j);
        filled.get(i).replace(toRemove, filled.get(i).get(toRemove) - 1);
        filled.get(9 + j).replace(toRemove, filled.get(9 + j).get(toRemove) - 1);
        filled.get(18 + boxNumber).replace(toRemove, filled.get(18 + boxNumber).get(toRemove) - 1);
        this.numsFilled--;
        return true;
    }

    /**
     * Clear the board to its original state.
     */
    public void clear() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                removeNumber(i, j);
        }
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
        int numRemoved = 55;
        //randomize and fix first row
        ArrayList<Integer> firstRow = new ArrayList<>();
        for (int i = 1; i <= 9; i++){
            firstRow.add(i);
        }
        Collections.shuffle(firstRow);
        for (int j = 0; j < 9; j++) {
            placeNumber(firstRow.get(j), 0, j);
            fixed[0][j] = true;
        }
        //solve from the given configuration
        solve();

        //unfix first row and randomly remove numRemoved elems
        for (int j = 0; j < 9; j++) {
            fixed[0][j] = false;
        }

        Random r = new Random();
        while (numRemoved > 0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if(r.nextBoolean()) {
                        if(removeNumber(i,j)) {
                            numRemoved--;
                        }
                        if (numRemoved == 0) break;
                    }
                }
            }
        }

        //fix the remaining numbers as clues
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] > 0) fixed[i][j] = true;
            }
        }
    }

    /*Utility Functions */

    /**
     * Get the box number of a specified coordinate.
     * @param i The row number.
     * @param j The col number.
     * @return The box number corresponding to the given coordinates.
     */
    public static int getBoxNumber(int i, int j) {

        if (i < 3) {
            if (j < 3) return 0;
            else if (j < 6) return 1;
            else return 2;
        }
        else if (i < 6) {
            if (j < 3) return 3;
            else if (j < 6) return 4;
            else return 5;
        }
        else {
            if (j < 3) return 6;
            else if (j < 6) return 7;
            else return 8;
        }

    }

    public String toString() {
        StringBuilder rv = new StringBuilder();
        rv.append("\n");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                for (int k = 0; k < 25; k++) {
                    rv.append("-");
                }
                rv.append("\n");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) rv.append("| ");
                rv.append(Integer.toString(board[i][j]));
                rv.append(" ");
            }
            rv.append("|");
            rv.append("\n");
        }
        for (int i = 0; i < 25; i++) {
            rv.append("-");
        }
        return rv.toString();
    }
}

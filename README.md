#Sudoku

##Main Class
Handles the game loop and the user input

##Board Class
Handles the board of the sudoku game.

Variables:
- int[][] board: a 9x9 2D matrix used to store the state of the board
- boolean[][] fixed: 9x9 2D matrix storing which numbers are set in place as clues
- ArrayList<HashMap<Integer,Integer> filled: ArrayList of HashMaps used for fast lookup of how the rows, columns, and boxes are filled in.
HashMaps 0-8 represent the 9 rows, 9-17 the 9 cols, and 18-26 the 9 boxes.
- int numsFilled: tracks how many numbers have been filled in the board

Methods:
- Board(): Constructs an empty board filled with all 0's. Initializes all instance variables.
- boolean placeNumber(int entered, int i, int j): places the number entered at board matrix position i,j. Returns true is number was successfully placed, or false if a numbers was attempted to be placed on top of a fixed clue.
- boolean removeNumber(int i, j): removes the number located at board matrix position i,j. Returns true if the number was removed successfully, or false if there was no number or a fixed clue there.
- void clear(): Restores the board to its initial state.
- boolean validBoardState(): returns true if the board is in a valid sudoku state (no more than 1 of each number in every row, col, and box)
- boolean isSolve(): returns true if the board is in a solved state.
- boolean solve(): solves the boared by calling solveHelper(0,0)
- boolean solveHelper(int i, int j): backtracking algorithm used to solve the board. Randomness is introduced in the order the numbers are tried in order to use the method for board generation.
- void generateBoard(int numClues): uses the solve() method to generate a board with numClues clues on it.

- int getBoxNumber(int i, int j): used to get the box represented at position i,j in the matrix
- String toString(): returns a String representation of the board

##Graphics Class
Handles displaying the game board
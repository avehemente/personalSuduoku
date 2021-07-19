#Sudoku

##Main Class
Handles the game loop and the user input

##Board Class
Handles the board

Variables:
- filled: ArrayList of HashSets used to determine how board is filled in,
elems 0-8 represent the 9 rows, 9-17 the 9 cols, and 18-26 the 9 boxes
- solved: true iff the board is solved (i.e. all 27 HashSets are filled with numbers 1-9)

##Graphics Class
Handles displaying the game board
import java.util.Scanner;

/**
 * A snapshot of a Game of Life board at one point in time
 * @author Isabella Patnode
 *
 */
public class Board {
	private int numRows;
	private int numCols;
	// The two-dimensional grid of board cells.
	// cells[r][c] is true if the cell at row r and column c is alive.
	private boolean[][] cells;
	
	
	/**
	 * Creates a Board with no live cells
	 * @param numberOfRows - number of rows in the board
	 * @param numberOfColumns - number of columns in the board
	 */
	public Board(int numberOfRows, int numberOfColumns){
		numRows = numberOfRows;
		numCols = numberOfColumns;
		
		cells = new boolean[numRows][numCols];
		
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				cells[i][j] = false;
			}
		}

	}
	
	
	/**
	 * Constructs a Board from the given String.
	 * @param boardInfo a String with all of the information from a 
	 *    given file
	 */
	public Board(String boardInfo){
		String boardInfo2 = boardInfo;
		Scanner boardScanner = new Scanner(boardInfo2);
		
		//Scans all information before first integer
		while(boardScanner.hasNext() && !boardScanner.hasNextInt()) {
			boardScanner.next();
		}
		
		numRows = boardScanner.nextInt();
		
		numCols = boardScanner.nextInt();
		//Gathers all information from the line after the integer
		String line = boardScanner.nextLine();
		
		int rowCount = 0;
		
		cells = new boolean[numRows][numCols];
		//Parses each line from the String
		while(boardScanner.hasNextLine()) {
			line = boardScanner.nextLine();
			
			int colCount = 0;
			//Parses each character from the line
			for(int i = 0; i < line.length(); i++) {
				char place = line.charAt(i);
				
				if(place == 'X') {
					cells[rowCount][colCount] = true;
				}
				else {
					cells[rowCount][colCount] = false;
				}	
				colCount++;
			}
			rowCount++;
		}
		boardScanner.close();
	}
	
	
	/**
	 * @return number of rows in the board
	 */
	public int getNumRows(){
		return numRows;
	}
	
	/**
	 * @return number of columns in the board
	 */
	public int getNumCols(){
		return numCols;
	}
	
	/**
	 * Collects the value of a specific cell
	 * @param row where the cell that is being grabbed is located
	 * @param col where the cell that is being grabbed is located
	 * @return value of the cell at the given @param row and @param col
	 * @throws IllegalArgumentException if row or col is out of bounds.
	 */
	public boolean getCell(int row, int col) throws IllegalArgumentException {
		if(row < 0 || row >= numRows) {
			throw new IllegalArgumentException("Row out of bounds");
		}
		else if(col < 0 || col >= numCols) {
			throw new IllegalArgumentException("Column of of bounds");
		}
		else if(cells[row][col] == true) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * Checks the neighbors of a cell to see if they are alive or dead
	 * @param cell the generation that contains the cell that is
	 *     being checked
	 * @param currentRow the row where the cell belongs
	 * @param currentCol the column where the cell belongs
	 * @param numRows the number of rows in the generation
	 * @param numCols the number of columns in the generation
	 * @return the number of neighbors of a cell that are alive
	 */
	private int checkCells(boolean [][] cell, int currentRow, int currentCol, int numRows, int numCols) {
		
		int countLive = 0;
		//Checks all cells in array with its neighbors
		for(int i = currentRow - 1; i <= currentRow + 1; i++) {
			//Makes sure all rows are in bounds
			if(i >= 0 && i < numRows) {
				for(int j = currentCol - 1; j <= currentCol + 1; j++) {
					//Checks that all columns are in bounds
					if(j >= 0 && j < numCols) {
						/* Makes sure that the cell whose neighbors 
						   are being checked is not also being checked */
						if(i != currentRow || j != currentCol) {
							if(cell[i][j]) {
								/* Keeps track of how many of the cell's 
								neighbors are alive */
								countLive++;
							}
						}
					}
				}	
			}
		}
		
		return countLive;
	}
	
	/**
	 * Creates generation after current Board
	 * @return A new Board for the next generation (i.e., after this).
	 *    The cell values for the next generation
	 *    are determined by the rules of Game of Life.
	 */
	public Board nextBoard() {
		/* Creates new generation with all dead cells that is modified 
		   based on the rules of Game of Life and 
		   the status of the previous generation */
		Board modBrd = new Board(numRows, numCols);
		
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				int neighborCount = checkCells(this.cells, i, j, numRows, numCols);
				/* Game of Life rules that determine whether 
				   a cell is alive or dead */
				if(neighborCount < 2 || neighborCount > 3) {
					modBrd.cells[i][j] = false;
				}
				else if(neighborCount == 3 && !this.cells[i][j]) {
					modBrd.cells[i][j] = true;
				}
				else if((neighborCount == 2 || neighborCount == 3) && this.cells[i][j]) {
					modBrd.cells[i][j] = true;
				}
				else {
					modBrd.cells[i][j] = false;
				}
			}
		}
		
		return modBrd;
	}
	

	/**
	 * Determines whether two boards are the same
	 * @param other the Board that is being checked against 
	 *   the current Board
	 * @return true if other is the same size as this
	 *   and all of the cells of other have the same
	 *   values as this
	 * @throws IllegalArgumentException if row or col are out of bounds
	 */
	public boolean isSame(final Board other) throws IllegalArgumentException {
		//Checks that number of rows in both boards are equal
		if(numRows != other.getNumRows()) {
			return false;
		}
		//Checks that number of columns in both boards are equal
		else if(numCols != other.getNumCols()) {
			return false;
		}
		/* If rows and columns in both boards are equal, checks each
		   cell compared to its counterpart */
		else {
			for(int i = 0; i < numRows; i++) {
				for(int j = 0; j < numCols; j++) {
					if(this.getCell(i, j) != other.getCell(i, j)) {
						return false;
					}	
				}
			}
			return true;
		}
	}
	
	/**
	 * @return a String representation of the current board state
	 */
	@Override
	public String toString(){
		String currentPos = " ";
		String currentBoard = "";
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				if(cells[i][j]) {
					currentPos = "X";
				}
				else {
					currentPos = ".";
				}
				currentBoard += currentPos;
			}
			//Places each row on its own line
			currentBoard += "\n";
		}
		//Ends last row on its own line
		currentBoard += "\n"; 
		
		return currentBoard;
	}	

}

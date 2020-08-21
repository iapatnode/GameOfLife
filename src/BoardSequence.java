
import java.util.ArrayList;
/**
 * A sequence of generations in Game of Life
 * @author Isabella Patnode
 *
 */
public class BoardSequence {
	//The zero generation of a board 
	Board startingBoard;
	//A list containing all generations of a Board
	public ArrayList<Board> boardGens = new ArrayList<Board>();
	/**
	 * Adds the zero generation to the list of all generations
	 * @param startingBoard the zero generation of a board
	 */
	public BoardSequence(Board startingBoard) {
		boardGens.add(startingBoard);
	}
	/**
	 * Gathers and adds a specified number of generations of a board to 
	 * the list of all board generations
	 * @param numSteps the number of generations to be run
	 * @throws IllegalArgumentException if board index is out of bounds
	 */
	public void runMoreSteps(int numSteps) throws IllegalArgumentException {
		for(int i = 0; i < numSteps; i++) {
			/* Collects the latest generation from the list of 
			   board generations */
			int indexNum = boardGens.size() - 1;
			Board brd = boardAt(indexNum);
			//Modifies the current generation to obtain the next generation
			boardGens.add(brd.nextBoard());
		}
	}
	/**
	 * Collects a generation at a specified index
	 * @param index the generation that is being collected
	 * @return the generation at a certain index in the list of board 
	 *         generations
	 * @throws IllegalArgumentException if index is out of bounds
	 */
	public Board boardAt(int index) throws IllegalArgumentException {
		if(index < 0 || index >= boardGens.size()) {
			throw new IllegalArgumentException("Index out of bounds");
		}
		return boardGens.get(index);
	}
	/**
	 * @return a String representation of all the board generations
	 * @throws IllegalArgumentException if board inde is out of bounds 
	 */
	@Override
	public String toString() throws IllegalArgumentException {
		String printBrds = "";
		
		for(int i = 0; i < boardGens.size(); i++) {
			printBrds += "Generation " + i + "\n" + boardAt(i).toString() + "\n";
		}
		
		return printBrds;
	}
	/**
	 * Determines whether two generations are the same
	 * @return the generation at which the first cycle appears 
	 *      (excluding generation 0)
	 *       if no cycles appear, it returns -1
	 * @throws IllegalArgumentException if board index is out of bounds
	 */
	public int findCycle() throws IllegalArgumentException {
		for(int i = 0; i < boardGens.size(); i++) {
			//Compare a generation with all generations after it
			for(int j = i + 1; j < boardGens.size(); j++) {
				if(boardAt(i).isSame(boardAt(j))) {
					return j;
				}			
			}
		}
		return -1;
	}
}

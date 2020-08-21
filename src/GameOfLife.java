
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Plays Game of Life
 * @author Isabella Patnode
 *
 */
public class GameOfLife {
	/**
	 * Reads in everything from a file into a String given from the user 
	 * @param fileName of file that is being read in
	 * @return String of everything in file
	 * @throws FileNotFoundException if file name is not found
	 */
	public static String readFileContents(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		
		Scanner scanIn = new Scanner(f);
		String fileContents = "";
		//adds everything from the file given to a String line by line
		while(scanIn.hasNextLine()) {
			fileContents = fileContents + scanIn.nextLine() + "\n";
		}
		scanIn.close();
		return fileContents;
		
	}

	public static void main(String[] args) throws IOException, IllegalArgumentException {
		Scanner scnr = new Scanner(System.in);
		
		//Gets input from user
		System.out.println("Enter the starting board file: ");
		String inputFileName = scnr.next();
		
		//Collects string version of file
		String fileContents = readFileContents(inputFileName);
		
		//Initializes zero generation from String of file input
		Board brd = new Board(fileContents);
		
		//Initializes starting generation
		BoardSequence brdSeq = new BoardSequence(brd);
		
		//Gets number of generations to run from user
		System.out.println("Enter the number of generations: ");
		int numGen = scnr.nextInt();
		
		//Initializes starting generation
		brdSeq.runMoreSteps(numGen);
		
		//Finds cycles
		int genNum = brdSeq.findCycle();
		
		if(genNum != -1) {
			System.out.println("Cycle detected on generation " + genNum + ".");
		}
		else {
			System.out.println("No cycles detected.");
		}
		
		//Prints list of generations to specified output file
		System.out.println("Enter the output file: ");
		String outputFileName = scnr.next();
		
		PrintWriter pw = new PrintWriter(outputFileName);
		
		pw.print(brdSeq.toString());
		
		
		scnr.close();
		pw.close();
	}
}

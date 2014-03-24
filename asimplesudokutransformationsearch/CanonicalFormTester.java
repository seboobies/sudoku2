package asimplesudokutransformationsearch;

import java.util.Scanner;

import parsingstuff.Parser;
import diuf.sudoku.Grid;

public class CanonicalFormTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		//Type a sudoku
		String sudoku = scan.nextLine();
		Grid grid = new Grid();
		Parser parser = new Parser();
		parser.parseLine(grid, sudoku);
		CanonicalTransformation cal = new CanonicalTransformation();
		cal.toCanonical(grid);
	}

}

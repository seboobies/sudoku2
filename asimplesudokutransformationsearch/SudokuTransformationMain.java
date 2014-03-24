package asimplesudokutransformationsearch;

import java.util.Scanner;

import parsingstuff.Parser;
import diuf.sudoku.Grid;
import diuf.sudoku.solver.Solver;
import diuf.sudoku.solver.checks.BruteForceAnalysis;

public class SudokuTransformationMain {
	
	private static Grid grid; // The Sudoku grid
    private static Solver solver; // The Sudoku solver
    private static Parser parser;

    private final static BruteForceAnalysis analyser = new BruteForceAnalysis(true);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		//Type a sudoku
		String sudoku = scan.nextLine();
		parser = new Parser();
		grid = new Grid();
		parser.parseLine(grid, sudoku);
		solver = new Solver(grid);
		
		if(analyser.getCountSolutions(grid) != 1){
			System.out.println("This is not a valid sudoku. Kill yourself");
			return;
		}
		int amountOfNewSudokus = 0;
		System.out.println(solver.getDifficulty());
		for(int x=0;x<9;x++){
			for(int y=0;y<9;y++){
				if(grid.getCell(x, y).isEmpty()){
					continue;
				}
				int val = grid.getCellValue(x, y);
				grid.setCellValue(x, y, 0);
				for(int x2 =0;x2<9;x2++){
					for(int y2=0;y2<9;y2++){
						
						for(int valInc = 1;valInc<10;valInc++){
							if(x2 == x && y2 == y && valInc == val){
								continue;
							}
							if(grid.getCell(x2, y2).hasPotentialValue(valInc)){
								grid.setCellValue(x2, y2, valInc);
								int sol = analyser.getCountSolutions(grid);
								if(sol == 1){
									System.out.println(parser.parseGridToLine(grid));
									System.out.println(solver.getDifficulty());
									amountOfNewSudokus++;
								}
								grid.setCellValue(x2, y2, 0);
							}
						}
					}
				}
				grid.setCellValue(x, y, val);
				
				
			}
		}
		System.out.println(amountOfNewSudokus);
		
		
	}

}

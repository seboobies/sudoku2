package asimplesudokutransformationsearch;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import parsingstuff.Parser;
import diuf.sudoku.Grid;
import diuf.sudoku.solver.Solver;
import diuf.sudoku.solver.checks.BruteForceAnalysis;

public class RecursiveSudokuTransformation {

	private static Grid grid; // The Sudoku grid
    private static Solver solver; // The Sudoku solver
    private static Parser parser;
    private static CanonicalTransformation canonTransformer = new CanonicalTransformation();
    private final static BruteForceAnalysis analyser = new BruteForceAnalysis(true);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		parser = new Parser();
		Scanner scan = new Scanner(System.in);
		//Type a sudoku
		String sudoku = scan.nextLine();
		ArrayList<String> toCalc = new ArrayList<String>();
		grid = new Grid();
		parser.parseLine(grid, sudoku);
		
		if(analyser.getCountSolutions(grid) != 1){
			System.out.println("This is not a valid sudoku. Kill yourself");
			return;
		}
		
		toCalc.add(canonTransformer.toCanonical(grid));
		ArrayList<String> calculated = new ArrayList<String>();
		recursiveTransformation(toCalc,calculated,2);
		
		System.out.println(calculated.size());
		for(String string: calculated){
			System.out.println(string);
		}
		

	}

	private static ArrayList<String> recursiveTransformation(ArrayList<String> toCalc,ArrayList<String> calculated, int depth) {
		if(depth == 0){
			for(String sudoku: toCalc){
				if(calculated.contains(sudoku)){
					continue;
				}
				calculated.add(sudoku);
			}
			return calculated;
		}
		
		ArrayList<String> newToCalc = new ArrayList<String>();
		int counter = 0;
		int size = toCalc.size();
		for(String sudoku: toCalc){
			counter++;
			if(calculated.contains(sudoku)){
				continue;
			}
			System.out.print("Depth: " + depth + " ,calculating: " + counter +"/" + size + " . . . ");
			ArrayList<String> newSudokus = transformSudoku(sudoku);
			newToCalc.addAll(newSudokus);
			calculated.add(sudoku);	
			System.out.println("Found "+ newSudokus.size() + " new Sudokus");
		}
		depth--;
		return recursiveTransformation(newToCalc,calculated,depth);	
	}

	private static ArrayList<String> transformSudoku(String sudoku) {
		ArrayList<String> newSudokus = new ArrayList<String>();
		Grid grid = new Grid();
		Solver solver = new Solver(grid);
		parser.parseLine(grid, sudoku);
		for(int x=0;x<9;x++){
			for(int y=0;y<9;y++){
				if(grid.getCell(x, y).isEmpty()){
					continue;
				}
				int val = grid.getCellValue(x, y);
				grid.setCellValue(x, y, 0);
				solver.rebuildPotentialValues();
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
									//System.out.println(parser.parseGridToLine(grid));
									newSudokus.add(canonTransformer.toCanonical(grid));
								}
								grid.setCellValue(x2, y2, 0);
							}
						}
					}
				}
				grid.setCellValue(x, y, val);
				
				
			}
		}
		
		return newSudokus;
	}
	
	


}

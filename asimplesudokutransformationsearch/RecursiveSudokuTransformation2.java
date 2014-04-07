package asimplesudokutransformationsearch;

import java.util.ArrayList;
import java.util.Scanner;

import parsingstuff.Parser;
import diuf.sudoku.Cell;
import diuf.sudoku.Grid;
import diuf.sudoku.solver.Solver;
import diuf.sudoku.solver.checks.BruteForceAnalysis;

public class RecursiveSudokuTransformation2 {

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
		long startTime = System.currentTimeMillis();
		recursiveTransformation(toCalc,calculated,1);
		long endTime = System.currentTimeMillis();
		System.out.println(calculated.size());
		for(String string: calculated){
			System.out.println(string);
		}
		System.out.println("Time elapsed:" + (float)(endTime-startTime)/1000);
		System.out.println(calculated.size());

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
			ArrayList<String> newSudokus = transformSudoku2(sudoku);
			newToCalc.addAll(newSudokus);
			calculated.add(sudoku);	
			//System.out.println("Found "+ newSudokus.size() + " new Sudokus");
		}
		depth--;
		return recursiveTransformation(newToCalc,calculated,depth);	
	}

	public static ArrayList<String> transformSudoku2(String sudoku) {
		ArrayList<String> newSudokus = new ArrayList<String>();
		Grid grid = new Grid();
		Solver solver = new Solver(grid);
		Parser parser = new Parser();
		parser.parseLine(grid, sudoku);
		
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(grid.getCellValue(i, j) != 0){
					cells.add(grid.getCell(i, j));
				}
			}
		}
		for(int i =1;i<cells.size();i++){
			//System.out.print(i + "/"+cells.size()+ " ");
			for(int j= 0;j<i;j++){
				Cell cell1 = cells.get(i);
				Cell cell2 = cells.get(j);
				int cell1Old = cell1.getValue();
				int cell2Old = cell2.getValue();
				cell1.setValue(0);
				cell2.setValue(0);
				solver.rebuildPotentialValues();
				for(int cell1Pos = 0;cell1Pos<81;cell1Pos++){
					int x = cell1Pos%9;
					int y = cell1Pos/9;
					for(int cell1Val = 0;cell1Val<9;cell1Val++){
						if(!grid.getCell(x, y).hasPotentialValue(cell1Val)){
							continue;
						}
						grid.setCellValue(x, y, cell1Val);
						solver.rebuildPotentialValues();
						for(int cell2Pos = 0;cell2Pos<cell1Pos;cell2Pos++){
							int x2 = cell2Pos%9;
							int y2 = cell2Pos/9;
							for(int cell2Val =0;cell2Val<9;cell2Val++){
								if(!grid.getCell(x2,y2).hasPotentialValue(cell2Val)){
									continue;
								}
								grid.setCellValue(x2, y2, cell2Val);
								
								if(analyser.getCountSolutions(grid) == 1){
									//System.out.println(canonTransformer.toCanonical(grid));
									newSudokus.add(canonTransformer.toCanonical(grid));
								}
								grid.setCellValue(x2, y2, 0);	
							}
						}
						grid.setCellValue(x, y, 0);
					}
				}
				cell1.setValue(cell1Old);
				cell2.setValue(cell2Old);
				
				
			}
		}
		
		
		
		return newSudokus;
	}
	
}

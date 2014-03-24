package asimplegeneratorofsudoku;

import java.util.Random;
import java.util.Scanner;

import parsingstuff.Parser;

import diuf.sudoku.Grid;
import diuf.sudoku.solver.Solver;
import diuf.sudoku.solver.checks.BruteForceAnalysis;

public class GeneratorMain {

	private final static BruteForceAnalysis analyser = new BruteForceAnalysis(true);
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		//Type format
		String partition = scan.nextLine();
		boolean success = false;
		Grid grid = new Grid();
		Solver solver = new Solver(grid);
		Random random = new Random();
		int numberOfTries = 0;
		while(!success){
			grid = new Grid();
			solver = new Solver(grid);
			int numberToPlace = 0;
			for(char ch : partition.toCharArray()){
				int amount = ch - '0';
				numberToPlace++;
				
				for(int j = 0;j<amount;j++){
					boolean placed = false;
					while(!placed){
						int x = random.nextInt(9);
						int y = random.nextInt(9);
						solver.rebuildPotentialValues();
						
						if(grid.getCell(x, y).hasPotentialValue(numberToPlace)){
							grid.setCellValue(x, y, numberToPlace);
							placed = true;
						}
					}
					
					//System.out.println(numberToPlace);
				}
			}
			 int state = analyser.getCountSolutions(grid);
			 if(state == 1){
				 success = true;
			 }
			if(numberOfTries%1000 == 0){
				System.out.println("Number of tries:" + numberOfTries);
			}
			numberOfTries++;
		}
		
		Parser parser = new Parser();
		System.out.println(parser.parseGridToLine(grid));
		
		System.out.println(solver.getDifficulty());
		
		
	}

}

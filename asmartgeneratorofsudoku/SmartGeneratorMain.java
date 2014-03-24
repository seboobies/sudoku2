package asmartgeneratorofsudoku;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import parsingstuff.Parser;
import diuf.sudoku.Cell;
import diuf.sudoku.Grid;
import diuf.sudoku.solver.Solver;
import diuf.sudoku.solver.checks.BruteForceAnalysis;


public class SmartGeneratorMain {


private final static BruteForceAnalysis analyser = new BruteForceAnalysis(true);

private final static int ATTEMPT_LIMIT = 20;//LOL
	
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
					int attempts = 0;
					int bestX = -1;
					int bestY = -1;
					int bestPotentialCount = -1;
					while(!placed){
						
						int x = random.nextInt(9);
						int y = random.nextInt(9);
						solver.rebuildPotentialValues();

						if(grid.getCell(x, y).hasPotentialValue(numberToPlace)){
							attempts++;
							Cell cell = grid.getCell(x, y);
							int potentialValuesInHouse = countPotentialValues(cell.getHouseCells(), numberToPlace);
							if(potentialValuesInHouse > bestPotentialCount){
								bestX = x;
								bestY = y;
								bestPotentialCount = potentialValuesInHouse;
								
							}
						}
						if(attempts > ATTEMPT_LIMIT){
							grid.setCellValue(bestX, bestY, numberToPlace);
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
			//if(numberOfTries%1000 == 0){
				System.out.println("Number of tries:" + numberOfTries);
			//}
			numberOfTries++;
		}
		
		Parser parser = new Parser();
		System.out.println(parser.parseGridToLine(grid));
		
		System.out.println(solver.getDifficulty());
		
		
	}
	// O(n) bitches
	private static int countPotentialValues(Collection<Cell> houseCells, int value){
		
		int count = 0;
		for(Cell cell : houseCells){
			if(cell.hasPotentialValue(value)){
				count++;
			}
		}
		return count;
		
	}
}

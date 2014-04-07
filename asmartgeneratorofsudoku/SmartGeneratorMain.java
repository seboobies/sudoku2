package asmartgeneratorofsudoku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import asimplesudokutransformationsearch.RecursiveSudokuTransformation;

import parsingstuff.Parser;
import diuf.sudoku.Cell;
import diuf.sudoku.Grid;
import diuf.sudoku.solver.Solver;
import diuf.sudoku.solver.checks.BruteForceAnalysis;


public class SmartGeneratorMain {


private final static BruteForceAnalysis analyser = new BruteForceAnalysis(true);
private static Parser parser;

private final static int ATTEMPT_LIMIT = 5;//LOL
	
	public static void main(String[] args) {
		parser = new Parser();
		Scanner scan = new Scanner(System.in);
		//Type format
		String partition = scan.nextLine();
		boolean success = false;
		Grid grid = new Grid();
		Solver solver = new Solver(grid);
		Random random = new Random();
		int numberOfTries = 0;
		ArrayList<String> sudokus = new ArrayList<String>();
		long startTime = System.currentTimeMillis();
		while(true){
			
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

				numberOfTries++;
			}
			sudokus.add(parser.parseGridToLine(grid));
			long currentTime = System.currentTimeMillis();
			System.out.println("Time elapsed:" + (float)(currentTime-startTime)/1000);
			System.out.println("Number of tries:"+numberOfTries);
			for(String sudk :sudokus){
				System.out.println(sudk);
			}
			success = false;
		}

		
		
		
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

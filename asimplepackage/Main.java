package asimplepackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import parsingstuff.Parser;

import diuf.sudoku.Grid;
import diuf.sudoku.solver.Hint;
import diuf.sudoku.solver.Rule;
import diuf.sudoku.solver.Solver;

public class Main {

	private static Grid grid; // The Sudoku grid
    private static Solver solver; // The Sudoku solver
    private static Parser parser;
    
    private static Map<Rule,Integer> rulesUsed;
    
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("mysudokumister.txt");
		BufferedReader buffReader = new BufferedReader(new FileReader(file));
		
		double highestDifficulty = 1;
		int hardestLine = 0;
		String hardestString = "";
        parser = new Parser();

        String line = "";
        int i = 0;
        while((line = buffReader.readLine()) != null){
        	
            grid = new Grid();
            parser.parseLine(grid,line);
            solver = new Solver(grid);
            solver.rebuildPotentialValues();
            try{
            	//System.out.println("Trying to solve sudoku");
            	double difficulty = solver.getDifficulty();
            	if(difficulty > highestDifficulty){
            		highestDifficulty = difficulty;
            		hardestLine = i;
                	rulesUsed = solver.solve(null);	
                	hardestString = line;
            	}
            	 System.out.println("Difficulty:"+difficulty+ " Clues:"+parser.countSudokuClues(line));
            	//System.out.println("Difficulty:" + solver.getDifficulty());
            	//rulesUsed = solver.solve(null);	
                
                
                //System.out.println(rulesUsed);
            }catch(Exception e){
            	System.out.println("unsolveable yo");
            }
           
            i++;
        }
        System.out.println(hardestString);
        System.out.println(highestDifficulty);
        System.out.println(hardestLine);
        System.out.println(rulesUsed);
        

	}

}

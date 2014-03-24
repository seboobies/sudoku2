package asimpleSudokuanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import diuf.sudoku.Grid;
import diuf.sudoku.solver.Solver;

import parsingstuff.Parser;


public class AnalysisMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File file = new File("100tusen.txt");
		BufferedReader buffReader = new BufferedReader(new FileReader(file));
		HashMap<String,SudokuInfo> allOccurrences = new HashMap<String, SudokuInfo>();
		Parser parser = new Parser();
		Solver solver;
		for(int i =0; i<10;i++){
			Grid grid = new Grid();
			int[] occurrences = new int[9];
			String sudoku =buffReader.readLine();
			
			if(sudoku == null){
				break;
			}
			
			analyzeSudokuLine(sudoku,occurrences);
			
			parser.parseLine(grid, sudoku);
			solver = new Solver(grid);
			solver.rebuildPotentialValues();
			double difficulty = solver.getDifficulty();
			
			Arrays.sort(occurrences);
			String occurrencesString = castIntArrayToString(occurrences);
			
			
			if(!allOccurrences.containsKey(occurrencesString)){
				
				allOccurrences.put(occurrencesString,new SudokuInfo(1,difficulty));
			}
			else{
				SudokuInfo previous = allOccurrences.get(occurrencesString);
				previous.size++;
				previous.difficulty += difficulty;
				allOccurrences.put(occurrencesString, previous);
			}
			
		}

		
		System.out.println(allOccurrences);
	}
	
	private static String castIntArrayToString(int[] occurrences) {
		String ret = "";
		for(int i =0;i<occurrences.length;i++){
			ret += occurrences[i];
		}
		return ret;
	}

	private static void analyzeSudokuLine(String sudoku,int[] occurrences){
		for(char ch: sudoku.toCharArray()){
			if(ch == '.' || ch == '0'){
				continue;
			}
			int val = ch - '0';
			occurrences[val-1]++;
			
		}
		
	}

}

package parsingstuff;

import diuf.sudoku.Grid;

public class Parser{
	
	public void parseLine(Grid grid, String line){
		for(int i = 0; i<9;i++){
			for(int j = 0;j<9;j++){
				if(line.charAt(i*9+j) == '.'){
					continue;
				}
				int value = line.charAt(i*9+j) - '0';
				if(value != 0)
					grid.setCellValue(i, j, line.charAt(i*9+j) - '0');
			}
		}
	}
	
	public String parseGridToLine(Grid grid){
		String line = "";
		for(int i = 0;i<9;i++){
			for(int j=0;j<9;j++){
				line += grid.getCellValue(i, j);
			}
		}
		return line;
	}
	
	public int countSudokuClues(String line){
		int clues = 0;
		for(int i = 0;i<81;i++){
			if(line.charAt(i) == '.'){
				continue;
			}
			int value = line.charAt(i) - '0';
			if(value != 0)
				clues++;
		}
		return clues;
	}
}

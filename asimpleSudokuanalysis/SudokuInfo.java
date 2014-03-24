package asimpleSudokuanalysis;

public class SudokuInfo {
	public int size;
	public double difficulty;
	
	
	//fuck yes ;) constructor
	public SudokuInfo(int size, double difficulty){
		this.size = size;
		this.difficulty = difficulty;
	}
	
	@Override
	public String toString(){
		return  "{Size = "+ size + " , Difficulty = "+difficulty/size + " }";
		
	}
}

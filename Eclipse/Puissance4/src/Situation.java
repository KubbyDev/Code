import java.io.Serializable;

public class Situation implements Serializable {

	private static final long serialVersionUID = -1680546361251832677L;
	private int[][] plateau = new int[7][6];
	private int[] resolution = new int[7];
	
	public Situation(int[][] pla) {
		plateau = pla;
		createResolution();
	}
	
	public int[] getResolution() {
		return resolution;
	}
	
	public int[][] getPlateau() {
		return plateau;
	}
	
	private void createResolution() {
		
		for(int i = 0; i <= 6; i++)
			
			if(plateau[i][5] == 0)
				resolution[i] = 100;
		
	}
	
	public void addToRes(int index, int toAdd) {
		resolution[index] += toAdd;
		
		if(resolution[index] < 1)
			resolution[index] = 1;
	}
	
}

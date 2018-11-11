package puissancequatre;

import java.util.ArrayList;
import java.util.Arrays;

public class GameInfos {
	
	private ArrayList<int[]> plateaux = new ArrayList<int[]>();	
	private ArrayList<Integer> plays = new ArrayList<Integer>();
	
	public GameInfos() {
	}

	public void addPlay(int[] plateau, int play) {
		
		plateaux.add(Arrays.copyOf(plateau, plateau.length));
		plays.add(play);
		
		//System.out.println("New plateaux list: "); for(int[] i : plateaux) MinimaxAI.disp(i);
		
	}
	
	public ArrayList<Integer> getPlays() {
		return plays;
	}

	public ArrayList<int[]> getPlateaux() {
		return plateaux;
	}
	
}

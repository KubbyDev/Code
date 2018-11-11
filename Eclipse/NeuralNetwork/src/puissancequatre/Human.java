package puissancequatre;

import java.util.Scanner;

public class Human extends Player {

	@Override
	public int play(int plateau[], int player) {

		int play = -1;
		
		do {
			
			Scanner sc = new Scanner(System.in);
			play = sc.nextInt() -1;
			
		} while(play == -1 || !Game.canPlayAt(play, plateau));
		
		return play;
	}
	
}

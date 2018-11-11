package morpion;

import java.util.Scanner;

public class Human extends Player {

	@Override
	public int play(int plateau[], int player) {

		int play = -1;
		
		do {
			
			Scanner sc = new Scanner(System.in);
			play = sc.nextInt();
			play = adjustPosition(play);
			
		} while(play == -1 || !Game.canPlayAt(play, plateau));
		
		return play;
	}
	
	public static int adjustPosition(int position) {
		
		position = position -1;
		
		if(position < 3)
			return position + 6;
		
		if(position > 5)
			return position - 6;
		
		return position;
	}
	
}

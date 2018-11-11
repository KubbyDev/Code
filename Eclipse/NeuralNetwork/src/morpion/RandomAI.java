package morpion;

public class RandomAI extends Player {

	public RandomAI() {
		
	}
	
	@Override
	public int play(int[] plateau, int player) {
		
		int play; 
	
		do
			play = (int) Math.round(Math.random()*8);
		while(!Game.canPlayAt(play, plateau));
			
		return play;
		
	}
	
}

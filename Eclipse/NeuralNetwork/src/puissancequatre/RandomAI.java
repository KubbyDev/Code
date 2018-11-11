package puissancequatre;

public class RandomAI extends Player {

	public RandomAI() {
		
	}
	
	@Override
	public int play(int[] plateau, int player) {
			
		return randomPlay(plateau);
	}
	
}

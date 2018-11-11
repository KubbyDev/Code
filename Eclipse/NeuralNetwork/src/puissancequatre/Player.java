package puissancequatre;

public abstract class Player {
	
	public int play(int[] plateau, int player) {
		
		System.out.println("Methode play de Player appellée !");
		
		return 0;
	}

	public int randomPlay(int[] plateau) {
		
		int play; 
		
		do
			play = (int) Math.round(Math.random()*6);
		while(!Game.canPlayAt(play, plateau));
		
		return play;
	}
	
}

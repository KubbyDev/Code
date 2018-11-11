package morpion;

public class SequenceAI extends Player {

	int progress = 0;
	int[] sequence;
	
	public SequenceAI(int[] pSequence) {
		sequence = pSequence;
	}
	
	@Override
	public int play(int[] plateau, int player) {
		
		int play; 
		
		do {
			
			if(progress < sequence.length)
				play = sequence[progress];
			else
				play = (int) Math.round(Math.random()*8);
			
			if(progress >= sequence.length)
				System.out.print("UTILISATION DE RANDOM");
			
			progress++;
			
		} while(!Game.canPlayAt(play, plateau));
		
		return play;
	}
	
	public static int[] generateSequence(int length) {
		
		int[] i = new int[length];
		
		for(int y = 0; y < length; y++)
			i[y] = (int) Math.round(Math.random()*8);
		
		return i;
	}
	
}

package puissancequatre;

import java.util.ArrayList;
import java.util.Arrays;

public class MinimaxAI extends Player {

	protected int stopChecker; //The number of plays in depth simulated
	public int[] rates;
	
	/**
	 * difficulty est le nombre de coups en profondeur simulés. 1 simulera uniquement son premier coup, 2 son premier coup et le premier coup adverse etc
	 * @param difficulty
	 */
	public MinimaxAI(int difficulty) {
		stopChecker = difficulty;
	}
	
	@Override
	public int play(int[] plateau, int player) {
		
		rates = getRates(plateau, player, -1, stopChecker);
		int best = getMax(rates);
		
		disp(rates);
		
		ArrayList<Integer> possible = new ArrayList<Integer>();
		
		for(int i = 0; i < 7; i++)
			if(rates[i] == best)
				possible.add(i);
		
		int play = possible.get(random(0, possible.size()-1));
		
		if(Game.canPlayAt(play, plateau))
			return play;

		System.out.print("Minimax error, suggested play (" + (play) + ") is not valid - Answer: ");
		for(int y : rates)
			System.out.print(y + " ");
		System.out.print(" - For ");
		disp(plateau);
		
		return randomPlay(plateau);
	}
	
	protected int[] getRates(int[] plateau, int player, int isOpponent, int stopChecker) {
		
		int[] results = new int[7];
		
		for(int i = 0; i < 7; i++)
			results[i] = getRate(Arrays.copyOf(plateau, plateau.length), player, i, isOpponent, stopChecker);
		
		//System.out.println();
		
		return results;
	}
	
	protected int getRate(int[] plateau, int player, int colonne, int isOpponent, int stopChecker) {
		
		//System.out.print("Position " + position + ": ");
		
		//Le coup n'est pas possible
		if(!Game.canPlayAt(colonne, plateau)) {
			
			//System.out.println("Impossible");
			return 1000000000 * isOpponent;
			
		}
		
		int position = Game.findPosition(plateau, colonne);
		plateau[position] = player;
		
		//disp(plateau); System.out.println("Victoire du O: " + Game.hasWon(plateau, player, position));
		
		//Le coup mene a la victoire
		if(Game.hasWon(plateau, player, position)) {
			
			//System.out.println("Victoire");
			return onVictory(isOpponent, stopChecker, plateau, player);
			
		}
		
		//Si on a atteint la limite de simulation
		if(hasToStop(stopChecker)) {
			
			//System.out.println("Limite atteinte");
			return onHasToStop(isOpponent, stopChecker, plateau, player);
			
		}
		
		//System.out.println("On sait pas, poursuite de la simulation");
		
		//Si rien ne se passe on continue la simulation
		return onNothingFound(isOpponent, stopChecker, plateau, player);
		
	}
	
	protected int select(int[] rates, int isOpponent) {
		
		int value;
		
		if(isOpponent == -1)
			value = getMax(rates);
		else
			value = getMin(rates);
		
		//Si toutes les colonnes sont pleines, on donne un rate choisi uniquement
		//en dernier recours, mais preferé a une colonne deja pleine au tour precedent
		if(Math.abs(value) >= 1000000000)
			return -value +1;
		
		return value;
		
	}
	
	public int getMax(int[] tab) {
		
		int max = Integer.MIN_VALUE;
		
		for (int i = 0; i < tab.length; i++)
			max = Math.max(tab[i], max);
		
		return max;
	}
	
	public int getMin(int[] tab) {
		
		int min = Integer.MAX_VALUE;
		
		for (int i = 0; i < tab.length; i++)
			min = Math.min(tab[i], min);
		
		return min;
	}
	
	public static void disp(int[] i) {
		
		for(int y : i)
			System.out.print(y + " ");
		
		System.out.println();
		
	}
	
	public static int random(int min, int max) {
		
		return (int) (Math.round(Math.random() * (max-min)) + min);
	}
		
	
	protected int onHasToStop(int isOpponent, int stopChecker, int[] plateau, int player) {
		
		return 0;
	}
	
	protected int onNothingFound(int isOpponent, int stopChecker, int[] plateau, int player) {
		
		return select(getRates(plateau, player * -1, isOpponent * -1, stopChecker-1), isOpponent * -1);
	}
	
	protected int onVictory(int isOpponent, int stopChecker, int[] plateau, int player) {
		
		return stopChecker * -isOpponent;
	}
		
	protected boolean hasToStop(int stopChecker) {
		
		return stopChecker == 1;
	}
	
}

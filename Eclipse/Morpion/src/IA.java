import java.util.ArrayList;

import neuralnetwork.SimpleNetwork;

public class IA {
	
	public static SimpleNetwork reseau;
	public ArrayList<float[]> situations = new ArrayList<float[]>();
	public ArrayList<Integer> answers = new ArrayList<Integer>();
	
	public IA() {
		
		if(SimpleNetwork.exists(2000))
			reseau = new SimpleNetwork(2000);
		else
			reseau = new SimpleNetwork(19, 9);
		
		reseau.adjustPrecision = 0.01f;
	}
	
	public int play() {
		
		float[] inputs = readPlateau(Morpion.plateau, Morpion.joueur);
		float[] outputs = reseau.simulate(inputs);
		
		//On trouve la plus haute valeur dans les outputs
		float maxValue = 0.0f;
		for (int i = 0; i < outputs.length; i++)
			if (maxValue < outputs[i])
				maxValue = outputs[i];

		int play = 0;
		
		//On essaye de jouer sur chaque output ayant la valeur max
		for(int i = 0; i < outputs.length; i++)
			if (maxValue == outputs[i] && Morpion.canPlayAt(i))
				play = i;
		
		//Si aucun output ayant la valeur max ne permet de jouer, on en prend un random
		if(play == 0)
			while(!Morpion.canPlayAt(play))
				play = (int) Math.round(Math.random()*8);
		
		situations.add(inputs);
		answers.add(play);
		
		return play;
	}

	private float[] readPlateau(int[] plateau, int player) {
		
		float[] f = new float[19];
	
		for(int i = 0; i < 9; i++)
			f[i] = (plateau[i] == player) ? 1.0f : 0.0f;

		for(int i = 9; i < 18; i++)
			f[i] = (plateau[i-9] == (player%2)+1) ? 1.0f : 0.0f;
		
		f[18] = 1.0f;
		
		return f;
	}

	private void learn(boolean hasWon) {
		
		for(int i = 0; i < situations.size(); i++) {
			
			float[] diff = new float[9];
			
			diff[answers.get(i)] = (hasWon) ? 1.0f : -1.0f;
			
			int bad = (int) Math.round(Math.random()*8);
			
			while(diff[bad] != 0.0f)
				bad = (int) Math.round(Math.random()*8);
			
			diff[bad] = (hasWon) ? -1.0f : 1.0f;
			
			//Si il a gagné, on veut -1 partout sauf dans le coup qu'il a joué qui recoit 1
			//Si il a perdu, on veut 1 partout sauf dans le coup qu'il a joué qui recoit -1
			//for(int y = 0; y < 9; y++)			
				//diff[y] = (hasWon) ? -1.0f : 1.0f;
			
			//Inversion du signe pour le coup joué
			//diff[answers.get(i)] *= -1.0f;
			
			reseau.modify(situations.get(i), diff);

		}
		
	}

	public void endGame(boolean hasWon) {
	
		learn(hasWon);
		
		//Vidage des listes
		situations = new ArrayList<float[]>();
		answers = new ArrayList<Integer>();	
		
		reseau.save(2000);
		
	}
	
	public void disp(float[] a) {
		
		for(float f : a)
			System.out.print(f + "  ");
		
		System.out.println();
		
	}
	
}

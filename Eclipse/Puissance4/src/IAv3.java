import java.util.ArrayList;

import reseau.Reseau;

public class IAv3 {
	
	public static Reseau reseau;
	public ArrayList<ArrayList<Float>> situations = new ArrayList<ArrayList<Float>>();
	public ArrayList<Integer> answers = new ArrayList<Integer>();
	
	public IAv3() {
		
		if(Reseau.exists(1000))
			reseau = new Reseau(1000);
		else
			reseau = new Reseau(42, 7);
		
		reseau.adjustPrecision = 0.00001f;
	}
	
	public int play() {
		
		ArrayList<Float> inputs = readPlateau(Main.getPlateau());
		int play = Reseau.getAnswer(reseau.simulate(inputs));
		
		while(Main.find(play, Main.getPlateau()) == -1)
			play = (int) Math.round(Math.random()*6);
		
		situations.add(inputs);
		answers.add(play);
		
		return play;
	}

	private ArrayList<Float> readPlateau(int[][] plateau) {
		
		ArrayList<Float> a = new ArrayList<Float>();
		
		for(int[] i : plateau)
			for(int y : i)
				a.add(Float.valueOf(y));
		
		return a;
	}

	private void learn(boolean hasWon) {
		
		for(int i = 0; i < situations.size(); i++) {
		
			//Si il a gagné, on veut -1 partout sauf dans le coup qu'il a joué qui recoit 1
			//Si il a perdu, on veut 1 partout sauf dans le coup qu'il a joué qui recoit -1
			ArrayList<Float> diff = new ArrayList<Float>();
			for(int y = 0; y < 7; y++)			
				diff.add((hasWon) ? -1.0f : 1.0f);
			
			//Inversion du signe pour le coup joué
			diff.set(answers.get(i), diff.get(answers.get(i)) * -1.0f);
			
			reseau.modify(situations.get(i), diff);

		}
		
	}

	public void endGame(boolean hasWon) {
	
		learn(hasWon);
		
		//Vidage des listes
		situations = new ArrayList<ArrayList<Float>>();
		answers = new ArrayList<Integer>();	
		
		reseau.save(1000);
		
	}
	
}

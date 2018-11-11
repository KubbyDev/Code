package puissancequatre;

import java.util.ArrayList;

import neuralnetwork.DeepNetwork;

public class TrainingV2 {
	
	public static boolean drawDebug = true;
	public static int nbreGames = 5;
	public static int slot = 4004;
	public static long timeMS = 600000;
	public static float adjustPrecision = 15.0f;
	//public static int[] nbreNeurons = {85,50,40,30,20,7};
	public static int[] nbreNeurons = {85,85,80,80,70,60,50,40,40,35,30,25,20,20,20,15,10,7};
	public static float modifiedProportion = 0.01f;
	public static int player = -1;
	
	public static DeepNetwork network;
	public static ArrayList<int[]> sequences = new ArrayList<int[]>();
	
	public static void main(String[] args) {
		
		if(DeepNetwork.exists(slot))
			network = new DeepNetwork(slot);
		else
			network = new DeepNetwork(nbreNeurons);
		
		long time = System.currentTimeMillis();
		int nbreGenerations = 0;
		
		network.adjustPrecision = adjustPrecision;
		
		while (System.currentTimeMillis() - time < timeMS) {
		
			//for(int i = 0; i < nbreGames; i++)
				//sequences.add(SequenceAI.generateSequence(200));
			
			network = getBetterNetwork();
			player *= -1;
			
			nbreGenerations++;
			
		}
		
		System.out.println("\n\nTraining ended !\nTraining Time: " + (timeMS/1000)
										+ "s - Number of generations: " + nbreGenerations
										+ " - New modifiedProportion: " + modifiedProportion
										+ " - Player: " + player);
		
		network.save(slot);
			
	}
	
 	public static DeepNetwork getBetterNetwork() {
		
		float result = 0.0f;
		int tries = 0;
		int tmp = 0;
		DeepNetwork dn = null;
		
		while(result != 100.0f) {
			
			dn = network.createChild(modifiedProportion);
			result = getWinRate(dn, nbreGames);
			tries++;
			tmp++;
			
			if(tmp == 10000) {
				
				modifiedProportion = Math.min(modifiedProportion + 0.05f, 1.0f);
				System.out.println("No better network found after 10 000 tries, new modifiedProportion = " + modifiedProportion);
				tmp = 0;
				
			}
			
		}
		
		if(drawDebug)
			System.out.println("New network found after " + tries + " tries");
			
		return dn;
		
	}
	
	public static float getWinRate(DeepNetwork dn, int nbreGames) {
		
		Game[] games = new Game[nbreGames];
		
		for(int i = 0; i < nbreGames; i++) {
			
			games[i] = (player == 1) ? new Game(new AI(network), new AI(dn)) : new Game(new AI(dn), new AI(network));
			games[i].start(false, true, false);
			
		}
		
		//Waits for the simulations to end
		boolean ended = false;
		while(ended == false) {
			
			ended = true;
			
			for(Game g : games)
				if(g.game.getState() == Thread.State.RUNNABLE || g.game.getState() == Thread.State.BLOCKED)
					ended = false;
					
		}
		
		int gamesWon = 0;
		
		for(int i = 0; i < nbreGames; i++)
			if(games[i].winner == -1 || games[i].winner == 0)
			//if(games[i].winner == 1)
				gamesWon++;
		 
		return 100 * (float) gamesWon/(float) nbreGames;		
	}
	
	public static void disp(float[] i) {
		
		for(float y : i)
			System.out.print(y + " ");
		
		System.out.println();
		
	}
	
}

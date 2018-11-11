package puissancequatre;

import java.util.ArrayList;

import neuralnetwork.DeepNetwork;

public class Training {
	
	public static boolean drawDebug = true;
	public static int nbreGames = 10;
	public static int nbreUnits = 100;
	public static int slot = 4003;
	public static long timeMS = 10000;
	public static float adjustPrecision = 10.0f;
	public static int[] nbreNeurons = {85,50,40,30,20,7};
	public static float modifiedProportion = 0.2f;
	
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
			
			network = getBest(createChilds(network, nbreUnits));
			
			nbreGenerations++;
			
		}
		
		System.out.println("\n\nTraining ended !\nTraining Time: " + (timeMS/1000)
										+ "s - Number of generations: " + nbreGenerations);
		
		network.save(slot);
			
	}

	public static DeepNetwork[] createChilds(DeepNetwork dn, int nbreChilds) {
		
		DeepNetwork[] networks = new DeepNetwork[nbreChilds];
		
		for(int i = 0; i < nbreChilds; i++)
			networks[i] = dn.createChild(modifiedProportion);
		
		return networks;		
	}
	
 	public static DeepNetwork getBest(DeepNetwork[] networks) {
		
		if(drawDebug)
			System.out.println("\n\nSimulation...");
		
		float[] results = new float[networks.length];
		
		for(int i = 0; i < networks.length; i++) {
			
			DeepNetwork dn = networks[i];
			results[i] = getWinRate(dn, nbreGames);
			
		}
		
		if(drawDebug)
			disp(results);
		
		int[] selectionResults = selectNetwork(results);
		
		DeepNetwork dn = networks[selectionResults[0]];
		
		if(drawDebug)
			System.out.print("Best network: " + selectionResults[0]);
		
		if(drawDebug && selectionResults[0] != 0)
			System.out.print(" - New = " + selectionResults[0]);
		
		/*
		//If no unit can do better than the current network, the changes become smaller
		if(selectionResults[2] == 1) {
			
			dn.adjustPrecision /= 1.5;
				
			if(drawDebug)
				System.out.print("\n\nNo progress ! New adjust precision: " + dn.adjustPrecision);
			
		}
		
		//If more than 10% of the units have the same score (and if it is the best score), the changes become bigger
		if(selectionResults[1] == 1) {
			
			dn.adjustPrecision *= 1.5;
			
			if(drawDebug)
				System.out.print("\n\nSame scores ! New adjust precision: " + dn.adjustPrecision);
			
		}
		*/
		
		return dn;
	}
	
	private static int[] selectNetwork(float[] f) {
		
		ArrayList<Integer> a = new ArrayList<Integer>();
		
		float maxValue = 0.0f;

		//Max value determination
		for (int i = 0; i < f.length; i++)
			if (maxValue < f[i])
				maxValue = f[i];
		
		//Networks selection
		for (int i = 0; i < f.length; i++)
			if (maxValue == f[i])
				a.add(i);
		
		int sameScore = 0;
		int noProgress = 0;
		
		if(((float) a.size())/((float) f.length) > 0.1f)
			sameScore = 1;
		
		if(maxValue == f[0])
			noProgress = 1;
		
		int[] ret = {a.get( (int) Math.round(Math.random() * (a.size()-1)) ), sameScore, noProgress};
		
		return ret;	
	}
	
	public static float getWinRate(DeepNetwork dn, int nbreGames) {
		
		Game[] games = new Game[nbreGames];
		
		for(int i = 0; i < nbreGames; i++) {
			
			games[i] = new Game(new MinimaxAI(3), new AI(dn));
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

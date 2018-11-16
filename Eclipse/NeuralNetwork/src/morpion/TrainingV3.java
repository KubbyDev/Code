package morpion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import neuralnetwork.Network;
import neuralnetwork.SimpleNetwork;

public class TrainingV3 {

	public static long timeMS = 30000;
	public static float adjustPrecision = 0.0001f;
	public static boolean stopRequest = false;
	
	public static SimpleNetwork network;
	
	//Implies some modifications in the AI class, that's ugly but it works
	public static ArrayList<float[]> situations;
	public static ArrayList<float[]> answers;
	
	public static int slot = 2002;
	
	public static void main(String[] args) {

		AI.sendInfos = true;
		
		//Commande d'arret
		Thread stopThread = createStopResquestThread();
		stopThread.start();
		
		if(SimpleNetwork.exists(slot))
			network = new SimpleNetwork(slot);
		else
			network = new SimpleNetwork(19,9);
		
		network.adjustPrecision = adjustPrecision;		
		
		long time = System.currentTimeMillis();
		int nbreGenerations = 0;
		int nbreWins = 0;
		
		while (System.currentTimeMillis() - time < timeMS && !stopRequest) {
			
			situations = new ArrayList<float[]>();
			answers = new ArrayList<float[]>();
			
			Game game = new Game(new AI(network), new RandomAI());
			game.start(false, false, false);
			
			for(int i = 0; i < situations.size(); i++) {
				
				network.modify(
						      situations.get(i), 
						      getDiff(answers.get(i), game.winner == 1)
							  );
				
			}

			nbreGenerations++;
			nbreWins += (game.winner == 1) ? 1 : 0;
		}
		
		System.out.println("\n\nTraining ended !\nTraining Time: " + ((System.currentTimeMillis() - time)/1000)
						 + "s - Number of generations: " + nbreGenerations
						 + "s - Number of victories: " + nbreWins);
		
		//Sauvegarde
		network.save(slot);
		
		stopRequest = true;

	}

	private static float[] getDiff(float[] answers, boolean hasWon) {
		
		float[] diff = new float[9];
		Arrays.fill(diff, ( ! hasWon ) ? 0.125f : -0.125f);
		
		diff[Network.getAnswer(answers)] = (hasWon) ? 1.0f : -1.0f;
		
		return diff;
	}
	
	private static Thread createStopResquestThread() {
		
		return new Thread() {
			
			@Override
			public void run() {
				
				Scanner sc = new Scanner(System.in);
				
				try {
					
					while(System.in.available() == 0 && !stopRequest)
						Thread.sleep(1000);
					
				} catch (IOException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}
				
				stopRequest = true;
				sc.close();
				
			}
			
		};
		
	}
	
}

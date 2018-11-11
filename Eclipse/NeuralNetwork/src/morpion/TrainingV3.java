package morpion;

import java.io.IOException;
import java.util.Scanner;

import neuralnetwork.SimpleNetwork;

public class TrainingV3 {

	public static long timeMS = 10000000;
	public static float adjustPrecision = 2.0f;
	public static boolean stopRequest = false;
	
	public static SimpleNetwork network;
	
	public static void main(String[] args) {

		//Commande d'arret
		Thread stopThread = createStopResquestThread();
		stopThread.start();
		
		if(SimpleNetwork.exists(2000))
			network = new SimpleNetwork(2000);
		else
			network = new SimpleNetwork(19,9);
		
		network.adjustPrecision = adjustPrecision;		
		
		long time = System.currentTimeMillis();
		int nbreGenerations = 0;
		
		while (System.currentTimeMillis() - time < timeMS && !stopRequest) {
			
			Game game = new Game(new AI(network), new AI(network));
			game.start(false, false, true);
			
			for(int i = 0; i < game.infos.getPlays().size(); i++) {
				
				//TODO Executer ces lignes une fois sur 2
				
				network.modify(
						readPlateau(game.infos.getPlateaux().get(i), 1), 
						getDiff(game.infos.getPlays().get(i), game.winner == 1));
				
				network.modify(
						readPlateau(game.infos.getPlateaux().get(i), -1), 
						getDiff(game.infos.getPlays().get(i), game.winner == -1));
				
			}

			
			
			
		}
		
		System.out.println("\n\nTraining ended !\nTraining Time: " + ((System.currentTimeMillis() - time)/1000)
						 + "s - Number of generations: " + nbreGenerations);
		
		//Sauvegarde
		network.save(2000);
		
		stopRequest = true;

	}

	private static float[] getDiff(int play, boolean hasWon) {
		
		return new float[9];
	}
	
	private static float[] readPlateau(int[] plateau, int player) {
		
		float[] f = new float[19];
	
		for(int i = 0; i < 9; i++)
			f[i] = (plateau[i] == player) ? 1.0f : 0.0f;

		for(int i = 9; i < 18; i++)
			f[i] = (plateau[i-9] == -player) ? 1.0f : 0.0f;
		
		//Cet input permet de "mettre du courant" dans le reseau meme si aucun input n'est allume
		f[18] = 1.0f;
		
		return f;
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

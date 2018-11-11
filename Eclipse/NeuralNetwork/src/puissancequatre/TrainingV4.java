package puissancequatre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import neuralnetwork.BiologicalNetwork;

public class TrainingV4 {

	public static long timeMS = 10000000;
	public static float adjustPrecision = 2.0f;
	public static int modificationsNumber = 30;
	public static int[] nbreNeurons = {85,100,100,7};
	public static boolean stopRequest = false;
	
	//Contient les 5 meilleurs reseaux
	public static ArrayList<BiologicalNetwork> networks = new ArrayList<BiologicalNetwork>();
	
	public static void main(String[] args) {
		
		//Commande d'arret
		Thread stopThread = createStopResquestThread();
		stopThread.start();
		
		//Rempli les 5 premiers slots
		for(int i = 0; i < 5; i++) {
			
			//System.out.println("Save " + i + ": " + BiologicalNetwork.exists(4100+i));
			
			if(BiologicalNetwork.exists(4100+i))
				networks.add(new BiologicalNetwork(4100+i));
			else
				networks.add(new BiologicalNetwork(nbreNeurons));
			
			networks.get(i).adjustPrecision = adjustPrecision;
			
		}
		
		long time = System.currentTimeMillis();
		int nbreGenerations = 0;
		
		while (System.currentTimeMillis() - time < timeMS && !stopRequest) {
		
			//Rempli les 45 derniers slots avec 9 enfants par reseau du top 5
			for(int i = 0; i < 5; i++)
				for(int y = 0; y < 9; y++)
					networks.add(networks.get(i).createChild(modificationsNumber));
			
			//System.out.println("Avant tri: "); for(BiologicalNetwork dn : networks) System.out.println(dn);
			
			//Determination des 5 meilleurs reseaux. La liste renvoyee ne contient que les 5 meilleurs
			networks = getBestNetworks(networks);
			
			//System.out.println("Apres tri: "); for(BiologicalNetwork dn : networks) System.out.println(dn);
			
			nbreGenerations++;
			
		}
		
		System.out.println("\n\nTraining ended !\nTraining Time: " + ((System.currentTimeMillis() - time)/1000)
										+ "s - Number of generations: " + nbreGenerations);
		
		//System.out.println("Save: "); for(BiologicalNetwork dn : networks) System.out.println(dn);
		
		//Sauvegarde les 5 meilleurs reseaux
		for(int i = 0; i < 5; i++)
			networks.get(i).save(4100+i);
		
		//for(int i = 0; i < 5; i++) networks.get(i).getNeuron(5,5).display();;
			
		stopRequest = true;
		
	}
	
	private static ArrayList<BiologicalNetwork> getBestNetworks(ArrayList<BiologicalNetwork> list) {
	
		int[] results = new int[50];
		SimulationUnit[] su = new SimulationUnit[50];
		
		//Each network plays twice against each other network
		//Starts the games
		for(int i = 0; i < 50; i++) {
			
			su[i] = new SimulationUnit(i, list);
			su[i].start();
			
		}
		
		//Waits for the simulations to end
		boolean ended = false;
		while(ended == false) {
					
			ended = true;
					
			for(SimulationUnit s : su)
				if(s.t.getState() == Thread.State.RUNNABLE || s.t.getState() == Thread.State.BLOCKED)
					ended = false;
							
		}
		
		for(int i = 0; i < 50; i++)
			results[i] = su[i].result;
		
		disp(results);
		
		ArrayList<BiologicalNetwork> ret = new ArrayList<BiologicalNetwork>();
		
		//The max possible value is 98 because each network plays twice against 49 networks, and a victoiry is 2 points
		int maxValue = 196;
		int newMaxValue = 0;
		
		while(ret.size() < 5) {
			
			//On prend tous les reseaux qui ont le score maximum et on determine le meilleur score sans eux
			for(int i = 0; i < results.length; i++) {
				
				//Si le resultat trouve est un max, on le met dans le tableau
				if(results[i] == maxValue)
					ret.add(list.get(i));
				
				//Sinon, on verifie que ce n'est pas un reseau deja pris, et on change la newMaxValue si necessaire
				else if(results[i] < maxValue)
					newMaxValue = Math.max(results[i], newMaxValue);
				
			}
			
			maxValue = newMaxValue;
			newMaxValue = 0;
			
		}	
		
		//Si on en a trop pris, on supprime ceux en trop
		while(ret.size() > 5)
			ret.remove(ret.size()-1);
		
		//System.out.println("ret.size(): " + ret.size());
		
		return ret;		
	}
	
	private static class SimulationUnit {
		
		Thread t;
		int result;
		
		private SimulationUnit(int index, ArrayList<BiologicalNetwork> list) {
		
			t = new Thread() {
				
				@Override
				public void run() {
			
					result = getResult(list.get(index), list);
				
				}
				
			};
		
		}
				
		private void start() {
			
			t.start();
			
		}
		
	}
	
	private static int getResult(BiologicalNetwork network, ArrayList<BiologicalNetwork> list) {
		
		//The number of wins
		int result = 0;
		
		for(BiologicalNetwork dn : list) {
			
			if(dn != network) {
			
				//getWinner renvoie winner qui vaut -1: victoire j2, 0: egalite, 1: victoire j1
			
				//Donc on ajoute 2 en cas de victoire, 0: defaite, 1: egalite
				result += getWinner(network, dn) +1;
			
				//Cette fois on veut que le j2 gagne donc on inverse
				result += getWinner(dn, network)*-1 +1;					
				
			}
				
		}
		
		return result;
	}
	
	private static int getWinner(BiologicalNetwork n1, BiologicalNetwork n2) {
		
		Game game = new Game(new AI(n1), new AI(n2));
		game.start(false, false, false);
		
		return game.winner;
	}

	@SuppressWarnings("unused")
	private static void disp(int[] i) {
		
		for(int y : i) {
		
			String number = Integer.toString(y);
			System.out.print(number);
			
			int spacesLeft = 4-number.length();
			
			while(spacesLeft > 0) {
				
				System.out.print(" ");
				spacesLeft--;
				
			}
			
		}
		
		System.out.println();
		
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











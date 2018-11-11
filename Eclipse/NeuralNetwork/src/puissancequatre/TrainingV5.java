package puissancequatre;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import neuralnetwork.Example;
import neuralnetwork.SimpleNetwork;

public class TrainingV5 {

	//Ce Training se fait en 3 etapes:
	//Generation d'un grand nombre de plateaux valides (qu'on peut obtenir en jouant) a l'aide de 2 RandomAI
	//Calcul des reponses interessantes a ces situations par une MinimaxAI (Les reponses qui ne donnent que des 0 sont inutiles)
	//Apprentissage d'un SimpleNetwork grace aux reponses

	public static long plateauxTimeMS = 1800000;
	public static long learningTimeMS = 600000;
	public static int slot = 5000;
	public static float adjPres = 0.001f;
	public static boolean stopRequest = false;
	public static String pathPlateaux = "F:\\Networks\\Puissance4\\plateaux";
	public static String pathExamples = "F:\\Networks\\Puissance4\\examples";
	public static int minimaxDifficulty = 8;
	public static int nbrePlateaux = 145000;
	
	public static void main(String[] args) {

		//Commande d'arret
		Thread stopThread = createStopResquestThread();
		stopThread.start();
			
		//Creation d'un grand nombre de plateaux possibles
		//Etape1 e1 = new Etape1(nbrePlateaux);
		//e1.run(plateauxTimeMS, pathPlateaux);
		
		//Calcul de la reponse d'une IA minimax a ces plateaux
		//Etape2 e2 = new Etape2();
		//e2.run(pathPlateaux, pathExamples);
			
		//Apprentissage du reseau de neurones
		Etape3 e3 = new Etape3(slot);
		e3.run(60000, pathExamples);
		
		stopRequest = true;
		
	}
	
	private static class Etape1 {
		
		private ArrayList<Plateau> plateaux;
		private int limit;
		
		private Etape1(int pLimit) {
			
			plateaux = new ArrayList<Plateau>();
			limit = pLimit;
			
		}
		
		private void run(long timeMS, String path) {
			
			long tours = 0;
			
			//Boucle
			long time = System.currentTimeMillis();
			while (System.currentTimeMillis() - time < timeMS && !stopRequest && plateaux.size() < limit) {
				
				//Cree une game et la fait tourner
				Game g = new Game(new RandomAI(), new RandomAI());
				g.start(false, false, true);
				
				//Recupere les plateaux rencontres et les ajoute a la base de donnees si ils n'y sont pas encore
				for(int i = 0; i < g.infos.getPlays().size(); i++) {
										
					Plateau pl = new Plateau(g.infos.getPlateaux().get(i), (i%2)==0);
						
					if(isNew(pl))
						plateaux.add(pl);
						
				}
				
				if(tours%100 == 0)
					System.out.println("Progres: " + plateaux.size() + " - Temps restant: " + (int) ((double) (timeMS - System.currentTimeMillis() + time)/1000) + " s");
			
				tours++;
				
			}
			
			System.out.println("Etape 1 terminee ! Nombre de plateaux obtenus: " + plateaux.size());
			
			//Sauvegarde
			save(plateaux, path);
			
		}
		
		private boolean isNew(Plateau plateau) {
			
			for(Plateau p : plateaux) 
				if(p.equals(plateau))
					return false;
			
			return true;			
		}
		
	}
	
	private static class Etape2 {
		
		private ArrayList<Plateau> plateaux;
		private ArrayList<Plateau> examples = new ArrayList<Plateau>();		
		
		private Etape2() {			
		}
		
		private void run(String pathInput, String pathOutput) {
			
			plateaux = read(pathInput);
			
			MinimaxAI ai = new MinimaxAI(minimaxDifficulty);
			
			long time = System.currentTimeMillis();
			
			for(int i = 0; i < plateaux.size(); i++) {
				
				if(i % 7500 == 0)
					System.out.println("Progress: " + i + "/" + plateaux.size() + " - In: " + (int) ((double) (System.currentTimeMillis()-time)/1000) + " s");
				
				int[] plateau = plateaux.get(i).plateau;
				int play = ai.play(plateau, (plateaux.get(i).p1Turn) ? 1 : -1);
				
				if(isInteresting(ai.rates))
					examples.add(new Plateau(plateau, plateaux.get(i).p1Turn, play));
				
			}
			
			System.out.println("Etape 2 terminee ! Nombre d'exemples obtenus: " + examples.size());
			
			//Sauvegarde
			save(examples, pathOutput);
			
		}
		
		private static boolean isInteresting(int[] i) {
			
			for(int y : i)
				if(y != 0)
					return true;
			
			return false;
		}
				
	}
	
	private static class Etape3 {
		
		private ArrayList<Example> plateaux = new ArrayList<Example>();
		public SimpleNetwork sn;
		private int slot_;
		
		public Etape3(int pSlot) {
			
			slot_ = pSlot;
			
			if(SimpleNetwork.exists(slot_))
				sn = new SimpleNetwork(slot_);
			else
				sn = new SimpleNetwork(85, 7);
			
		}
		
		private void run(long timeMS, String pathInput) {
			
			System.out.println("Lecture des plateaux...");
			ArrayList<Plateau> plateaux = read(pathInput);
			
			System.out.println("Transformation en exemples...");
			ArrayList<Example> examples = new ArrayList<Example>();
			
			for(Plateau p : plateaux)
				examples.add(createExample(p));
			
			System.out.println("Apprentissage...");
			
			sn.train(examples, timeMS);
			
			sn.test(examples).display();
			
			sn.save(slot_);
			
		}
		
		private Example createExample(Plateau p) {
			
			return new Example(
								AI.readPlateau(p.plateau, (p.p1Turn) ? 1 : -1),
									p.play
											);			
		}
		
	}
	
	private static class Plateau implements Serializable {
		
		int[] plateau;
		boolean p1Turn;
		int play;
		
 		private Plateau(int[] pPlateau, boolean pPlayer1Turn) {
			
 			plateau = Arrays.copyOf(pPlateau, pPlateau.length); 			
 			p1Turn = pPlayer1Turn;
 			
		}
 		
 		private Plateau(int[] pPlateau, boolean pPlayer1Turn, int answer) {
 			
 			plateau = Arrays.copyOf(pPlateau, pPlateau.length);
 			p1Turn = pPlayer1Turn;
 			play = answer;
 			
 		}
 		
 		public boolean equals(Plateau p) {
 			
 			if(p1Turn != p.p1Turn)
 				return false;
 			
 			return Arrays.equals(plateau, p.plateau); 			
 		}
 		
	}
	
	private static ArrayList<Plateau> read(String path) {
		
		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
				new FileInputStream(new File(path))))) {

			try {
				
				return (ArrayList<Plateau>) ois.readObject();
				
			} catch (ClassNotFoundException e) {e.printStackTrace();}
			
			ois.close();

		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
	
		return null;
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
	
	public static void save(Object o, String path) {

		try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(path))))) {

			oos.writeObject(o);

			oos.close();

		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		
	}
	
	public static void disp(int[] i) {
		
		for(int y : i)
			System.out.print(y + " ");
		
		System.out.println();
		
	}
	
}

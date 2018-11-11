import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Morpion {
	
	private static ArrayList<MorpionSituation> listeSituations = new ArrayList<MorpionSituation>();
	public static int[] plateau = new int[9];
	private static int[] coupsJ1 = new int[10];
	private static int[] coupsJ2 = new int[10];
	private static int[] situations = new int[10];
	private static int progress = 0;
	public static int joueur = 0;
	
	private static IA ia1 = new IA();
	private static IA ia2 = new IA();
	
	public static void main(String[] args) {
		
		int NombreDeParties = 50000;
		
		for(int plays = 1; plays <= NombreDeParties; plays++) {
		
			if(plays%1000==0) System.out.println(plays);
			
		//listeSituations = readFile();
		//lisArraySituationsLine();
		
		int gagnant = 0;
		boolean joueurIA = false;
		int position = 0;
		boolean joueur1IA = false;
		boolean joueur2IA = false;
		boolean dispGame = true;
		
		for(int u = 0; u <= 8; u++)
		{
				joueur = (u%2) + 1;
				if(joueur == 1)
					joueurIA = joueur1IA;
				if(joueur == 2)
					joueurIA = joueur2IA;
				
				if(joueurIA)
				{
					if(joueur == 1)
						position = playIA(joueur);
					else
						position = (int) Math.round(Math.random()*8);
				}
				else
				{
					Scanner sc = new Scanner(System.in);
					position = sc.nextInt();
					position = adjustPosition(position);
				}
				
				if(canPlayAt(position))
				{
					
					playAt(joueur, position);
					
					if(dispGame)
						dispPlateau();
					
					progress++;
					
					if(hasWon(joueur, position))
					{
						gagnant = joueur;
						break;
					}
					
				}
				else
				{
					u--;
				}
		}
		
		
		if(true)
		{
			if(gagnant != 0)
			{
				if(gagnant == 1)
					System.out.print("Le gagnant est O !");
				else
					System.out.print("Le gagnant est X !");
			}
			else
				System.out.print("Il n'y a pas de gagnant ...");
			
			System.out.print("\n");
		}
		
		
		if(gagnant != 0)
		{
			
			if(joueur1IA == true)
				ia1.endGame(gagnant == 1);
			if(joueur2IA == true)
				ia2.endGame(gagnant == 2);
			
			/*
			if(joueur1IA == true)
				updateWinRate(1 , gagnant == 1);
			if(joueur2IA == true)
				updateWinRate(2 , gagnant == 2);
			*/
		}
			
		//saveSituations();
		
		Arrays.fill(plateau, 0);
		Arrays.fill(coupsJ1, 0);
		Arrays.fill(coupsJ2, 0);
		Arrays.fill(situations, 0);
		listeSituations.clear();
		progress = 0;
		
		}
	}
	
	public static void dispPlateau() {
		System.out.print("\n\n\n");
		
		int line = 0;
		
		for(int b : plateau)
		{
			if(b != 0)
			{
				if(b == 1)
				{
					System.out.print(" O ");
				}
				else
				{
					System.out.print(" X ");
				}
			}
			else
			{
				System.out.print(" . ");
			}
			
			line++;
			
			if(line == 3)
			{
				line = 0;
				System.out.print("\n");
			}
		}
		System.out.print("\n");
	}
	
	public static boolean hasWon(int joueur, int position) {
		
		boolean gagne;
		int tmp;
		
		//Ligne horizontale
		gagne = true;
		tmp = (position - (position%3));
		for(int y = tmp; y < tmp + 3; y++)
		{
			if(joueur != plateau[y])
				gagne = false;
		}
		if(gagne == true)
			return gagne;
		
		
		//Ligne verticale
		gagne = true;
		tmp = position;
		while(tmp > 2)
			tmp = tmp-3;
		for(int y = tmp; y < tmp + 9; y = y+3)
		{
			if(joueur != plateau[y])
				gagne = false;
		}
		if(gagne == true)
			return gagne;
		
		
		//Diagonale bas
		gagne = true;
		tmp = position;
		if(tmp == 0 || tmp == 4 || tmp == 8)
		{	
			for(int y = 0; y <= 8; y = y+4)
			{
				if(joueur != plateau[y])
					gagne = false;
			}
		}
		else
			gagne = false;
		if(gagne == true)
			return gagne;
		
		
		//Diagonale haut
		gagne = true;
		tmp = position;
		if(tmp == 6 || tmp == 4 || tmp == 2)
		{
			for(int y = 2; y <= 6; y = y+2)
			{
				if(joueur != plateau[y])
					gagne = false;
			}
		}
		else 
			gagne = false;
		if(gagne == true)
			return gagne;
		
		
		return false;
	}

	public static void playAt(int joueur, int position) {
		plateau[position] = joueur; 
	}
	
	public static boolean canPlayAt(int position) {
		return plateau[position] == 0;
	}

	public static int adjustPosition(int position) {
		position = position -1;
		if(position < 3)
			return position + 6;
		if(position > 5)
			return position - 6;
		return position;
	}
	
	public static int randomPlayIA() {
		int position = 0;
		do {
			position = (int) Math.round(8 * Math.random());
		} while(canPlayAt(position) == false);
		
		return position;
	}
	
	public static int playIA(int joueur) {
		
		if(joueur == 1)
			return ia1.play();
		else
			return ia2.play();
		
		/*
		//System.out.print("IA Joue..." + "\n");
		
		MorpionSituation situ = new MorpionSituation();
		situ = knownSituation();
		
		int coup = 0;
		
		if(situ == null)
		{
			MorpionSituation sit = new MorpionSituation(plateau);
			listeSituations.add(sit);
			situations[progress] = listeSituations.size() - 1;
			coup = randomPlayIA();
			if(joueur == 1)
				coupsJ1[progress] = coup;
			else
				coupsJ2[progress] = coup;
			return coup;
		}
		else
		{
			coup = selectPlayIA(situ.getResolution());
			if(joueur == 1)
				coupsJ1[progress] = coup;
			else
				coupsJ2[progress] = coup;
			return coup;
		}
		*/
		
	}
	
	public static MorpionSituation knownSituation() {
		
		int knownSitIndex = -1;
		
		//System.out.print("\n");
		
		for(int i = 0; i < listeSituations.size(); i++)
		{
			if(areEqual(((MorpionSituation) listeSituations.get(i)).getSituation(), plateau))
				knownSitIndex = i;
		}
		
		//System.out.print("known sit = " + (knownSitIndex != -1) + "  Index : " + knownSitIndex + "\n");
		
		if(knownSitIndex != -1)
		{
			situations[progress] = knownSitIndex;
			return (MorpionSituation) listeSituations.get(knownSitIndex);
		}
		else
			return null;
	}
	
	public static void saveSituations() {
			
	    try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("F:\\JavaCode\\Morpion\\src\\BaseDeDonnees.txt"))))) {
	      
	      for(int i = 0; i < listeSituations.size(); i++)
	      {
	    	  oos.writeObject(listeSituations.get(i));
	      }
	      
	      
	    } catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	    
	}
	
	public static ArrayList<MorpionSituation> readFile() {
		
		ArrayList<MorpionSituation> listeSituations = new ArrayList<MorpionSituation>();
	    
	    try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("F:\\JavaCode\\Morpion\\src\\BaseDeDonnees.txt"))))) { 
		   
	    	try {
	    		MorpionSituation sit = new MorpionSituation();
	    		sit = (MorpionSituation) ois.readObject();
	    		while(sit != null)
	    		{
	    			listeSituations.add(sit);
	    			sit = (MorpionSituation) ois.readObject();
	    		}
	    	}
		    catch (ClassNotFoundException e) { e.printStackTrace(); }
	    
	    } catch (FileNotFoundException e) { e.printStackTrace(); }
	      catch (IOException e) {} 
	    
	    return listeSituations;
	}

	public static int selectPlayIA(int[] resolution) {
		
		int length = 0;
		
		for(int i = resolution.length/2; i <= resolution.length-1; i++)
			length = length + resolution[i];
		
		int[] playables = new int[length];
		int nbrePlayables = 0;
		int nbreLoops = 0;
		
		for(int i = resolution.length/2; i <= resolution.length-1; i++)
		{
			nbreLoops = 0;
			for(int y = nbrePlayables; y <= nbrePlayables + resolution[i] - 1; y++)
			{
				playables[y] = resolution[i - resolution.length/2];
				nbreLoops++;
			}
			nbrePlayables = nbrePlayables + nbreLoops;
		}
		
		return playables[(int) (Math.random() * playables.length)];
	}
	
	public static void lisArraySituations() {
		
		System.out.print("Lecture de l'ArrayList \n");
		
		for(int i = 0; i < listeSituations.size(); i++)
		{
			
			MorpionSituation sit = (MorpionSituation) listeSituations.get(i);
			int colonne = 0;
			int line = 0;
			
			for(int b = 0; b <= 8; b++)
			{
				if(b == sit.getRes(colonne))
				{
					System.out.print(" ");
					System.out.print(sit.getRes(colonne+(sit.getResolution().length)/2));
					System.out.print(" ");
					colonne++;
				}
				else
					System.out.print(" . ");
				
				line++;
			
				if(line == 3)
				{
					line = 0;
					System.out.print("\n");
				}
			}
			System.out.print("\n");
		}
	}
	
	public static void updateWinRate(int joueur, boolean victoire) {
	
		int index = 0;
		
		for(int i = joueur - 1; i <= progress; i = i + 2)
		{
			MorpionSituation sit = new MorpionSituation();
			sit = listeSituations.get(situations[i]);
			
			int[] newResolution = new int[sit.getResolution().length];
			copyTo(sit.getResolution(), newResolution);
			
			for(int y = 0; y < newResolution.length/2; y++)
			{
				if(joueur == 1 && coupsJ1[i] == newResolution[y])
				{
					index = y;
				}
				if(joueur == 2 && coupsJ2[i] == newResolution[y])
				{
					index = y;
				}
			}
			
			index = index + newResolution.length/2;
			
			if(victoire)
			{
				newResolution[index]++;
			}
			else
			{
				if(newResolution[index] > 1)
				{
					newResolution[index]--;
				}
			}
		
			sit.setResolution(newResolution);
		}
	}
	
	public static void displayLine(int[] pla)
	{
		for(int i : pla)
			System.out.print(i + " ");
		
		System.out.print("\n");
	}
	
	public static void lisArraySituationsLine() {
		
		System.out.print("Lecture de l'ArrayList \n");
		
		for(int i = 0; i < listeSituations.size(); i++)
		{
			displayLine(listeSituations.get(i).getResolution());
		}
		
	}
	
	public static void copyTo(int[] tab1, int[] tab2) {
		System.arraycopy(tab1, 0, tab2, 0, Math.min(tab1.length, tab2.length));
	}
	
	public static int[] createCopy(int[] tab1) {
		int[] tab2 = new int[tab1.length];
		System.arraycopy(tab1, 0, tab2, 0, tab1.length);
		return tab2;
	}	

	public static boolean areEqual(int[] tab1, int[] tab2) {
		return Arrays.equals(tab1, tab2);
	}

}

package puissancequatre;

import java.util.ArrayList;

//Cette classe entraine 2 réseaux qui servent a aider un Minimax a jouer (AIv2)
public class TrainingV6 {
	
	public static int slot = 10000;
	public static long timeMS = 60000;
	public static ArrayList<Plateau> bdd = new ArrayList<Plateau>();
	public static ArrayList<String> bddHashes = new ArrayList<String>();
	
	public static void main(String[] args) {

		runTraining();
		
		int interesting = 0;
		
		for(int i = 0; i < 10; i++) {
			
			Plateau p = bdd.get(i);
			
			int rate = (int) Math.round(((double) p.nbreWins/(double) (p.nbreLoses + p.nbreWins))*100);
			
			if(p.nbreLoses + p.nbreWins > 1) {
			
				Game.dispPlateau(p.plateau);
				System.out.println("Pourcentage de réussite: " + rate + "%  (" + p.nbreWins + "/" + p.nbreLoses + ")");
				interesting++;
				
			}
			
		}
		
		System.out.print("Nombre de plateaux: " + bdd.size() + ", Interessants: " + interesting);
			
	}
	
	public static void runTraining() {
		
		long time = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - time < timeMS) {
			
			Game[] games = new Game[10]; 
 			
			//Joue 10 parties entre 2 Minimax 2
			for(int i = 0; i < games.length; i++) {
				
				games[i] = new Game(new MinimaxAI(2), new MinimaxAI(2));
				games[i].start(false, true, true);
			
			}
			
			//Attend la fin des parties
			boolean ended = false;
			while(ended == false) {
						
				ended = true;
						
				for(Game g : games)
					if(g.game.getState() == Thread.State.RUNNABLE || g.game.getState() == Thread.State.BLOCKED)
						ended = false;
								
			}
			
			//Pour chaque plateau de chaque partie
			for(Game g : games) {
				for(int i = 0; i < g.infos.getPlateaux().size(); i++) {
					
					int[] plateau = g.infos.getPlateaux().get(i);
					String hash = hash(plateau);	
					int inBDD = findInBDD(hash);
					
					//Ajoute le plateau si il n'a jamais été rencontré
					if(inBDD == -1) {
						
						inBDD = bdd.size();
						bdd.add(new Plateau(plateau));
						bddHashes.add(hash);
						
					}
					
					//TODO Gerer les egalites
					
					//Modifie le winrate du plateau rencontré
					
					boolean won = (                        //true si le joueur qui va jouer a ce moment a gagne a la fin, false sinon
							((g.winner == 1) ? 1 : -1 ) *  //Qui a gagne la partie ?
							((i%2 == 0) ? 1 : -1 )		   //C'est au tour de quel joueur ?
							) == 1;
					
					if(won)
						bdd.get(inBDD).nbreWins++;
					else
						bdd.get(inBDD).nbreLoses++;
					
				}
			}
					
		}	
		
	}
	
	//TODO Paralleliser
	public static int findInBDD(String hash) {
		
		for(int i = 0; i < bddHashes.size(); i++)
			if(bddHashes.get(i).equals(hash))
				return i;
		
		return -1;
	}
	
	//TODO Simplifier
	public static String hash(int[] plateau) {
		
		String ret = "";
		
		for(int i = 0; i < plateau.length; i++)
			ret += String.valueOf(plateau[i]);
		
		return ret;
	}
	
	private static class Plateau {
		
		public int nbreWins;
		public int nbreLoses;
		public int[] plateau;
		
		public Plateau(int[] pPlateau) {
			
			plateau = pPlateau;
			nbreWins = 0;
			nbreLoses = 0;
			
		}
		
	}

	/*
	public static int findInBDD(String hash) {
		
		Searcher[] s = new Searcher[bddHashes.size()/10000 +1];
		int ret = -1;
		
		//System.out.println("Creation de " + s.length + " Searchers");
		
		//Lance une recherche parallelisee
		for(int i = 0; i < s.length; i++) {
			
			s[i] = new Searcher(hash, i*10000, (i == s.length-1) ? bddHashes.size()-1 : (i+1)*10000-1);
			s[i].start();
			
			//System.out.println("Searcher " + i + ": Lancement de la recherche entre " + i*10000 + " et " + ((i == s.length-1) ? bddHashes.size()-1 : (i+1)*10000-1));

		}
		
		//Attend la fin de la recherche
		boolean ended = false;
		while(ended == false) {
					
			ended = true;
					
			for(int i = 0; i < s.length; i++) {
				
				Searcher sr = s[i];
				
				//Regarde si il reste des threads qui travaillent
				if(sr.getState() == Thread.State.RUNNABLE || sr.getState() == Thread.State.BLOCKED)
					ended = false;
				
				//Regarde si un thread a trouve
				if(sr.found != -1) {
					
					//System.out.println("Plateau trouve par le Searcher " + i + " ! Position " + sr.found);
					
					ended = true;
					ret = sr.found;				
					i = s.length;
					
				}
				
			}
			
		}
		
		for(Searcher sr : s)
			sr.stop = true;
		
		return ret;		//-1 si rien n'est trouve
	}
	
	public static class Searcher extends Thread {
		
		public int found = -1;
		public String hash;
		public int beginIndex;
		public int endIndex;
		public boolean stop = false;
		
		public Searcher(String pHash, int begin, int end) {
			
			hash = pHash;
			beginIndex = begin;
			endIndex = end;
			
		}
		
		@Override
		public void run() {
			
			for(int i = beginIndex; i <= endIndex; i++) {
			
				if(stop) {
					
					//System.out.println("Arret a " + i);
					
					i = endIndex;
					
				}
				
				if(bddHashes.get(i).equals(hash)) {
					
					found = i;
					i = endIndex;
					
				}
					
			}
					
		}
		
	}
	*/
}

package puissancequatre;

import neuralnetwork.SimpleNetwork;

public class Game {
	
	/* Plateau:
	 * 
	 * 00 01 02 03 04 05 06
	 * 07 08 09 10 11 12 13
	 * 14 15 16 17 18 19 20
	 * 21 22 23 24 25 26 27
	 * 28 29 30 31 32 33 34
	 * 35 36 37 38 39 40 41
	 * 
	 */
	private int[] plateau;
	private int player = 1;	
	private Player player1;
	private Player player2;
	
	public Thread game;
	public int winner;
	public GameInfos infos;
	
	public static void main(String[] args) {
		
		Game g = new Game(new Human(), new MinimaxAI(2));
		g.start(true, false, false);
		
	}
	
	public Game(Player p1, Player p2) {
		
		player1 = p1;
		player2 = p2;
		plateau = new int[42];
		
	}
	
	private void play(Player p1, Player p2, boolean dispGame, boolean saveInfos) {
		
		int coups = 0;
		boolean gameEnded = false;
		player = 1;
		
		if(saveInfos)
			infos = new GameInfos();
		
		while(!gameEnded) {
			
			int play = 0;
			
			//Plays 
			if(player == 1)
				play = player1.play(plateau, player);
			if(player == -1)
				play = player2.play(plateau, player);
			
			if(saveInfos)
				infos.addPlay(plateau, play);
			
			//System.out.println("Play added ! " + play + " for inputs:");
			//MinimaxAI.disp(plateau);
			
			play = findPosition(plateau, play);
			
			playAt(player, play);
			
			//Draw verification
			coups++;
			if(coups == 42)
				gameEnded = true;
			
			//Victory verification
			if(hasWon(plateau, player, play))
				winner = player;
			if(winner != 0)
				gameEnded = true;
			
			//Displays the board
			if(dispGame)
				dispPlateau();
			
			//Switches to the other player
			player *= -1;
			
		}
		
		if(dispGame) {
			
			if(winner != 0)
			{
				if(winner == 1)
					System.out.print("Le gagnant est O !");
				else
					System.out.print("Le gagnant est X !");
			}
			else
				System.out.print("Il n'y a pas de gagnant ...");
			
			System.out.print("\n");	
			
		}
		
	}
	
	public void start(boolean display, boolean inNewThread, boolean saveGameInfos) {
		
		if(inNewThread) {
			
			game = new Thread() {
				
				@Override
				public void run() {
					
					play(player1, player2, display, saveGameInfos);
					
				}
			
			};
			
			game.start();
			
		}
		else {
			
			play(player1, player2, display, saveGameInfos);
			
		}
			
		
	}
	
	private void dispPlateau() {
		
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
			
			if(line == 7)
			{
				line = 0;
				System.out.print("\n");
			}
		}
		
		System.out.print("\n");
		
		for(int i = 1; i < 8; i++)
			System.out.print(" " + i + " ");
			
		System.out.print("\n\n");
		
	}

	public static int findPosition(int[] pPlateau, int column) {
		
		//Le coup est toujours verifie avant l'appel de cette methode, donc toujours possible
		
		int hauteur = 35;
		
		while(pPlateau[column + hauteur] != 0)
			hauteur -= 7;
		
		return column + hauteur; 
		
	}
	
	public static boolean hasWon(int[] pPlateau, int joueur, int position) {
		
		int tmp;
		int lined;
		int maxLined;
		
		//Ligne horizontale
		tmp = (position - (position%7));
		lined = 0;
		maxLined = 0;
		for(int y = tmp; y < tmp + 7; y++)
		{
			if(joueur != pPlateau[y])
				lined = 0;
			else {
				lined++;
				maxLined = Math.max(lined, maxLined);
			}
		}
		if(maxLined >= 4)
			return true;
		
		//Ligne verticale
		tmp = position;
		lined = 0;
		maxLined = 0;
		while(tmp > 6)
			tmp = tmp-7;
		for(int y = tmp; y < 42; y = y+7)
		{
			if(joueur != pPlateau[y])
				lined = 0;
			else {
				lined++;
				maxLined = Math.max(lined, maxLined);
			}
		}
		if(maxLined >= 4)
			return true;
		
		
		//Diagonales
		tmp = position;
		lined = 0;
		maxLined = 0;
		
		while(tmp%7 != 0 && tmp > 6)				//Tant que la case visee n'est ni au bord du haut si au bord de gauche
			tmp -= 8; 								//On bouge d'une case en diagonale haut gauche
			
		if(joueur != pPlateau[tmp])
			lined = 0;
		else {
			lined++;
			maxLined = Math.max(lined, maxLined);
		}
		
		while((tmp-6)%7 != 0 && tmp < 35) {			//Tant que la case visee n'est ni au bord du bas si au bord de droite
			
			tmp += 8; 								//On bouge d'une case en diagonale bas droite
			
			if(joueur != pPlateau[tmp])
				lined = 0;
			else {
				lined++;
				maxLined = Math.max(lined, maxLined);
			}
			
		}
		
		if(maxLined >= 4)
			return true;
		
		tmp = position;
		lined = 0;
		maxLined = 0;
		
		while(tmp%7 != 0 && tmp < 35)				//Tant que la case visee n'est ni au bord du bas si au bord de gauche
			tmp += 6; 								//On bouge d'une case en diagonale bas gauche
		
		if(joueur != pPlateau[tmp])
			lined = 0;
		else {
			lined++;
			maxLined = Math.max(lined, maxLined);
		}
		
		while((tmp-6)%7 != 0 && tmp > 6) {			//Tant que la case visee n'est ni au bord du haut si au bord de droite
			
			tmp -= 6; 								//On bouge d'une case en diagonale haut droite
			
			if(joueur != pPlateau[tmp])
				lined = 0;
			else {
				lined++;
				maxLined = Math.max(lined, maxLined);
			}
			
		}
		
		if(maxLined >= 4)
			return true;
		
		return false;
	}

	private void playAt(int joueur, int position) {
		
		plateau[position] = joueur; 
	}
	
	public static boolean canPlayAt(int column, int[] pPlateau) {
		
		if(column < -1 || column > 7)
			return false;
			
		//Veirifie que le haut de la colonne est vide
		return pPlateau[column] == 0;
	}
	
	public static void dispPlateau(int[] pPlateau) {
		
		System.out.print("\n\n\n");
		
		int line = 0;
		
		for(int b : pPlateau)
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
			
			if(line == 7)
			{
				line = 0;
				System.out.print("\n");
			}
		}
		
		System.out.print("\n");
		
		for(int i = 1; i < 8; i++)
			System.out.print(" " + i + " ");
			
		System.out.print("\n\n");
		
	}
	
}

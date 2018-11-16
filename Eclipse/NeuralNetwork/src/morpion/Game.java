package morpion;

import neuralnetwork.SimpleNetwork;

public class Game {
	
	private int[] plateau = new int[9];
	private int player = 1;	
	private Player player1;
	private Player player2;
	
	public Thread game;
	public int winner;
	public GameInfos infos;
	
	public static void main(String[] args) {
		
		Game g = new Game(new AI(new SimpleNetwork(2002)), new Human());
		g.start(true, false, false);
		
	}
	
	public Game(Player p1, Player p2) {
		
		player1 = p1;
		player2 = p2;
		
	}
	
	public void play(Player p1, Player p2, boolean disp, boolean saveInfos) {
		
		int coups = 0;
		boolean gameEnded = false;
		player = 1;
		
		if(saveInfos)
			infos = new GameInfos();
		
		while(!gameEnded) {
			
			int play = 0;
			
			//Plays 
			if(player == 1)
				play =  player1.play(plateau, player);
			if(player == -1)
				play = player2.play(plateau, player);
			playAt(player, play);
			
			if(saveInfos)
				infos.addPlay(plateau, play);
			 
			//Draw verification
			coups++;
			if(coups == 9)
				gameEnded = true;
			
			//Victory verification
			if(hasWon(player, play))
				winner = player;
			if(winner != 0)
				gameEnded = true;
			
			//Displays the board
			if(disp)
				dispPlateau();
			
			//Switches to the other player
			player *= -1;
			
		}
		
		if(disp) {
			
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
	
	public void dispPlateau() {
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

	public boolean hasWon(int joueur, int position) {
		
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

	public void playAt(int joueur, int position) {
		plateau[position] = joueur; 
	}
	
	public static boolean canPlayAt(int position, int[] pPlateau) {
		
		//System.out.print(position + " "); disp(pPlateau);
		
		if(position < 0 || position > 8)
			return false;
			
		return pPlateau[position] == 0;
	}

	public static void disp(int[] i) {
		
		for(int y : i)
			System.out.print(y + " ");
		
		System.out.println();
		
	}
	
}

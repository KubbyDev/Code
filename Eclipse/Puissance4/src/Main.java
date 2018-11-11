

public class Main {

	//Parametres
	public static boolean player1IA = true;
	public static boolean player2IA = true;
	public static boolean autoReset = false;
	public static boolean modeApprentissage = false;
	public static int timeBetweenPlays = 500;
	public static boolean IAv3 = true;
	public static boolean colorDisp = false;
	
	//Variables Techniques	
	public static Fenetre fenetre = new Fenetre();
    public static Panneau pan = new Panneau();
	private static int playX = -1;
	private static int playY = -1;
	private static int[][] plateau = new int[7][6];
	public static boolean updateColor = false;
	public static int joueur = 1;
	//La premiere valeur est le coup joué (0 a 6), la deuxieme est son index dans le fichier
	public static int coup[][] = new int[42][2];
	//Le numéro du coup joué (0 a 41)
	public static int progress = 0;
	public static int winner = -2;
	public static IA player1 = new IA();
	public static IA player2 = new IA();
	public static IAv3 player1v3 = new IAv3();	
	public static IAv3 player2v3 = new IAv3();	
	public static boolean waitForPaint = false;
	public static boolean playing = true;
	public static boolean waitForPlayer = false;
	public static boolean waitForReset = false;
	
	public static void main(String[] args) {
				
		if(!modeApprentissage) {
			fenetre.setContentPane(pan);
			fenetre.setVisible(true);
		}
	
		if(colorDisp) {
			fenetre.setSize(400, 700);
			fenetre.setLocationRelativeTo(null);
		}
		
		/*if(IAv2) {
			player1v2.idPlayer = -1;
			player2v2.idPlayer = 1;
		}*/
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) { e.printStackTrace(); }
		
		int nbreGamesIAv1 = 33690;
		//int nbreGamesIAv3 = 2 100 000;
		int nbreGames = 0;
		
		while(playing) {
			
			pan.firstPaint = false;
			
			if(modeApprentissage && !IAv3) {
				nbreGamesIAv1++;
				System.out.println(nbreGamesIAv1);		
			}
			
			if(modeApprentissage) {
				nbreGames++;
				if(nbreGames % 10000 == 0) System.out.println(nbreGames);		
				if(nbreGames == 1000000) playing = false;
			}
			
			do {
				
				pan.setFocusable(true);
				pan.requestFocus();
				
				//Temps entre 2 coups
				if(timeBetweenPlays != 0)
				{
					try {
						Thread.sleep(timeBetweenPlays);
					} catch (InterruptedException e) { e.printStackTrace(); }
				}
				
				waitForPaint();
				
				waitForPlayer = false;
				joueur *= -1;

				//waitForPlayer passe a true si c'est au joueur de jouer
				playIA();
				
				playX = -1;
			
				//Attente du coup du joueur
				while(waitForPlayer && playX == -1) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) { e.printStackTrace(); }
				}
				
				playX = -1;
				
				progress++;
				
				//Vérifie qu'il reste de la place dans la grille
				if(progress == 42 && winner == -2)
					winner = 0;

			} while(winner == -2);
			
			//Fin de partie
			if(IAv3) {
				
				//-1 = yellow 1 = red
				if(player1IA) {
					player1v3.endGame(winner == -1);
				}
				if(player2IA) {
					player2v3.endGame(winner == 1);
				}
			}
			else {
				if(player1IA)
					recompenseIA(1);
				if(player2IA)
					recompenseIA(-1);					
			}
			
			pan.gameEnd = true;
				
			waitForPaint = true;
			
			if(!modeApprentissage)
				pan.repaint();
			
			waitForPaint();				
			
			if(autoReset)
			{
				reset();
			}
			else
			{
				waitForReset = true;
				while(waitForReset) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) { e.printStackTrace(); }
				}
				reset();
			}
			
		}
		
	}
	
	public static void waitForPaint() {
		
		while(waitForPaint && !modeApprentissage) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) { e.printStackTrace(); }
		}
		
	}
	
	public static void play(int i) {
		
		playX = i;
		playY = findY();
		
		if(playY != -1) 
		{		
			if(updateColor)
				pan.color = playX;
			updateColor = false;

			plateau[playX][playY] = joueur;

			pan.playX = playX * 57 + 10;
			pan.playY = (5-playY) * 57 + 80;
			
			waitForPaint = true;
			if(!modeApprentissage)
				pan.repaint();
			
			waitForPlayer = false;
			if(hasWon())
				winner = joueur;
		}
		else
		{
			if(!waitForPlayer)
				joueur *= -1;
			
			playX = -1;
			
			progress--;
		}
			
	}
	
	private static int findY() {
		return find(playX, plateau);
	}
	
	public static int find(int play, int[][] pla) {
		
		for(int i = 0; i <= 5; i++) {			
			if(pla[play][i] == 0)
				return i;
		}
		
		return -1;
	}
	
	private static boolean hasWon() {
		return lineTest(plateau, playX, playY, joueur);
	}
	
	public static boolean lineTest(int[][] pla, int pX, int pY, int player) {
		return hasLine(player, pla, pY) || hasDiag(player, pla, pX, pY) || hasColumn(player, pla, pX);
	}
	
	private static boolean hasLine(int player, int[][] pla, int pY) {
		
		int lined = 0;
				
		for(int i = 0; i <= 6; i++)
		{
			if(pla[i][pY] == player)
				lined++;
			else
				lined = 0;
			
			if(lined == 4)
				return true;
		}
		
		return false;
	}
	
	private static boolean hasColumn(int player, int[][] pla, int pX) {
		
		int lined = 0;
		
		for(int i = 0; i <= 5; i++)
		{
			if(pla[pX][i] == player)
				lined++;
			else
				lined = 0;
			
			if(lined == 4)
				return true;
		}
		
		return false;
	}
	
	private static boolean hasDiag(int player, int[][] pla, int pX, int pY) {
		
		for(int i = -1; i <= 1; i += 2) {
		
			int[] racine = toBound(pX, pY, i);
			int x = racine[0];
			int y = racine[1];

			int lined = 0;
			
			do {
				
				if(pla[x][y] == player)
					lined++;
				else
					lined = 0;
				
				if(lined == 4)
					return true;
				
				x -= i;
				y++;
				
			} while(x > -1 && x < 7 && y < 6);
			x += i;
			y--;
		
		}
		
		return false;		
	}
	
	private static int[] toBound(int x, int y, int lr) {
		
		//lr est le sens de la diagonale, 1 pour gauche, -1 pour droite
		
		do {
			y--;
			x += lr;
		} while(x > -1 && x < 7 && y > -1);
		y++;
		x-= lr;
		
		int[] ret = new int[2];
		ret[0] = x;
		ret[1] = y;
		
		return ret;				
	}
	
	public static int[][] getPlateau() {
		return plateau;
	}
	
	public static void reset() {

		resetPlateau();
		
		pan.firstPaint = true;
		pan.gameEnd = false;
		joueur = 1;
		progress = 0;
		winner = -2;
		playX = -1;
		playY = -1;
		
		waitForPaint = true;
		
		if(!modeApprentissage)
			pan.repaint();
		
		pan.reset();
		
		waitForReset = false;
		
		waitForPaint();
		
	}
	
	public static void resetPlateau() {
		
		for(int i = 0; i <= 6; i++)
		{
			for(int y = 0; y <= 5; y++)
			{
				plateau[i][y] = 0;
			}
		}
		
	}
	
	public static void recompenseIA(int i) {
		
		int recompense = 0;
		
		if(winner == i)
			recompense = -2;
		else if(winner == i*-1)
				recompense = 5;
		     else
		    	 recompense = 1;
		
		if(i == 1)
			player1.learn(recompense, 0);
		
		if(i == -1)
			player2.learn(recompense, 1);
			
	}
	
	public static void playIA() {
		
		int playIA = 0;
		
		if(joueur == -1)
		{		
			if(player1IA)
				if(IAv3)
					playIA = player1v3.play();
				else
					playIA = player1.play();
			else
				waitForPlayer = true;
		}		
		else
		{		
			if(player2IA)
				if(IAv3)
					playIA = player2v3.play();
				else
					playIA = player2.play();
			else
				waitForPlayer = true;
		}
		
		if(!waitForPlayer) {
			updateColor = true;
			play(playIA);
			coup[progress][0] = playIA;	

			if(colorDisp) {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) { e.printStackTrace(); }
				
				pan.color = -1;
				waitForPaint = true;
				pan.repaint();
			
			
			}
		}
	}
	
	public static boolean tabEqual(int[][] tab1, int[][] tab2) {
		
		for(int i = 0; i <= 6; i++) {
			for(int y = 0; y <= 5; y++) {
				if(tab1[i][y] != tab2[i][y])
					return false;
			}
		}
		
		return true;
	}
	
}



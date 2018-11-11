
public class IAv2 {
	
	int playX = 0;
	int playY = 0;
	//Ce nombre est celui qui est écrit dans le tableau par l'IA (1 ou -1)
	public int idPlayer = 0;
	//Variable de test
	int temp = -1;
	
	public IAv2() {
	}
	
	public int play() {
		
		return selectPlay(adaptTab(getBestPlays()));		
		//temp++; if(temp == 7) temp = 0; return temp;
		//return (int) Math.round(Math.random()*6);
	
	}
	
	private int[] getBestPlays() {
		
		int[][] tempPla = new int[7][6];
		int[][] tempPla2 = new int[7][6];
		int[][] tempPla3 = new int[7][6];
		int[][] tempPla4 = new int[7][6];
		int ret[] = new int[7];
		boolean hasDouble;
		boolean hasDoubleAdv;
		
		//Retourne les coups entrainant une victoire ou une défaire immédiate
		ret = getSmartPlays();
		
		//S'arrete tout de suite si il peut gagner directement
		for(int i = 0; i <= 6; i++)
			if(ret[i] == 4)
				return ret;

		for(int coup1 = 0; coup1 <= 6; coup1++) {
		
			//System.out.println("coup1 = " + coup1);
			
			//Si ce coup est possible et n'entraine pas une défaite immédiate
			if(ret[coup1] > -4) {
			
				//Simule le premier coup
				for(int x = 0; x <= 6; x++) { for(int y = 0; y <= 5; y++) { 
					tempPla[x][y] = Main.getPlateau()[x][y];
					//tempPla[x][y] = Test.pla[x][y];
				}}
				int coupY = Main.find(coup1, tempPla);
				tempPla[coup1][coupY] = idPlayer;
				
				hasDouble = true;
				
				//Simule le 2e coup
				for(int coup2 = 0; coup2 <= 6; coup2++) {
					
					//System.out.println("coup2 = " + coup2);
					
					//Crée un plateau temporaire et simule le coup adverse (2e coup)
					for(int x = 0; x <= 6; x++) { for(int y = 0; y <= 5; y++) { 
						tempPla2[x][y] = tempPla[x][y];
					}}
					coupY = Main.find(coup2, tempPla2);
					
					//Si le coup est possible
					if(coupY != -1)	{
						
						tempPla2[coup2][coupY] = idPlayer*-1;
						
						hasDoubleAdv = true;
						boolean canWin = false;
						
						//Simule le 3e coup (allié)
						for(int coup3 = 0; coup3 <= 6; coup3++) {
							
							//System.out.println("coup3 = " + coup3);
							
							//Crée un plateau temporaire et simule le coup
							for(int x = 0; x <= 6; x++) { for(int y = 0; y <= 5; y++) { 
								tempPla3[x][y] = tempPla2[x][y];
							}}
							coupY = Main.find(coup3, tempPla3);
							
							if(coupY != -1) {
							
								tempPla3[coup3][coupY] = idPlayer;
								
								if(hasDouble)
									if(maxLined(tempPla3, coup3, coupY, idPlayer) == 4)
										canWin = true; //hasDouble regarde si tous les coups adverses (2e coup) entrainent une victoire au 3e coup
								
								boolean canWinAdv = false;
								
								//Simule le 4e coup (adverse)
								for(int coup4 = 0; coup4 <= 6; coup4++) {
									
									//System.out.println("coup4 = " + coup4);
									
									//Crée un plateau temporaire et simule le coup
									for(int x = 0; x <= 6; x++) { for(int y = 0; y <= 5; y++) { 
										tempPla4[x][y] = tempPla3[x][y];
									}}
									coupY = Main.find(coup4, tempPla4);
									
									if(coupY != -1) {
									
										tempPla4[coup4][coupY] = idPlayer*-1;
										
										if(hasDoubleAdv)
											if(maxLined(tempPla4, coup4, coupY, idPlayer*-1) == 4)
												canWinAdv = true; //hasDoubleAdv regarde si tous les coups alliés (3e coup) entrainent une défaite au 4e coup
						
									}
								}
								//Fin de la for du 4e coup (coup allié)
								
								//Si un coup allié empeche la défaite au 4e coup, l'adversaire n'a pas de double
								if(canWinAdv == false)
									hasDoubleAdv = false;
							}
						}
						//Fin de la for du 3e coup (coup allié)
						
						//Si un coup adverse empeche la victoire au 3e coup, on a pas de double
						if(canWin == false)
							hasDouble = false;
						
						//Si tous les coups alliés (3e coup) entrainent une défaite au 4e coup, l'adversaire a une double
						if(hasDoubleAdv == true)
							ret[coup1] = -3;
						
					}	
				}
				//Fin de la for du 2e coup (coup adverse)
				
				//Si tous les coups adverse (2e coup) entrainent une victoire au 3e coup, on a une double ligne
				if(hasDouble == true)
					ret[coup1] = 3;
				
			}
		}
		
		/*
		System.out.print("\n");
		Test.disp(ret);
		*/
		
		return ret;
			
	}

	public int[] adaptTab(int[] tab) {
		
		//Transforme un tableau dont la position représente le coup et le nombre représente les chances de réussite
		//en un tableau dont la position ne représente rien et le nombre représente le coup (et on garde seulement le ou les coups les plus intéressant)
		
		int newLenght = 0;
		int bestPossible = -10;
		
		for(int i = 0; i <= 6; i++) {
			
			if(tab[i] > bestPossible) {
				bestPossible = tab[i];
				newLenght = 0;
			}
			
			if(tab[i] == bestPossible)
				newLenght++;
						
		}
		
		int[] ret = new int[newLenght];
		int progress = 0;
		
		for(int i = 0; i <= 6; i++) {
			
			if(tab[i] == bestPossible) {
				ret[progress] = i;
				progress++;
			}
		}
		
		return ret;		
	}
	
	public int selectPlay(int[] possibles) {
		return possibles[(int) Math.round((possibles.length-1)*Math.random())];
	}
	
	public static int maxLined(int[][] pla, int pX, int pY, int player) {
		return Math.max(inLine(player, pla, pY), Math.max(inDiag(player, pla, pX, pY), inColumn(player, pla, pX)));
	}
	
	private static int inLine(int player, int[][] pla, int pY) {
		
		int lined = 0;
		int max = 0;
				
		for(int i = 0; i <= 6; i++)
		{
			if(pla[i][pY] == player)
				lined++;
			else
				lined = 0;
			
			if(lined == 4)
				return 4;
			
			if(lined < max)
				max = lined;
		}
		
		return max;
	}
	
	private static int inColumn(int player, int[][] pla, int pX) {
		
		int lined = 0;
		int max = 0;
		
		for(int i = 0; i <= 5; i++)
		{
			if(pla[pX][i] == player)
				lined++;
			else
				lined = 0;
			
			if(lined == 4)
				return 4;
			
			if(lined < max)
				max = lined;
		}
		
		return max;
	}
	
	private static int inDiag(int player, int[][] pla, int pX, int pY) {
		
		int max = 0;
		
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
					return 4;
				
				x -= i;
				y++;
				
				if(lined < max)
					max = lined;
				
			} while(x > -1 && x < 7 && y < 6);
			x += i;
			y--;
		
		}
		
		return max;		
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
	
	private int[] getSmartPlays() {
		
		int[] ret = new int[7];
		int[][] tempPla = new int[7][6];
		int[][] tempPla2 = new int[7][6];
		
		//Simule son coup et le coup adverse
		
		for(int coup1 = 0; coup1 <= 6; coup1++) {
		
			//Crée un plateau temporaire et simule le premier coup
			for(int x = 0; x <= 6; x++) { for(int y = 0; y <= 5; y++) { 
				tempPla[x][y] = Main.getPlateau()[x][y];
				//tempPla[x][y] = Test.pla[x][y];
			}}
			int coupY = Main.find(coup1, tempPla);
			
			if(coupY == -1) {
				ret[coup1] = -10;
			}
			else {
				
				tempPla[coup1][coupY] = idPlayer;
				
				int maxCoup1 = maxLined(tempPla, coup1, coupY, idPlayer);
				
				//Arrete tout de suite si il peut gagner
				if(maxCoup1 == 4) {
					ret[coup1] = 4;
					
					return ret;
				}
				
				for(int coup2 = 0; coup2 <= 6; coup2++) {
				
					//Crée un plateau temporaire et simule le coup adverse
					for(int x = 0; x <= 6; x++) { for(int y = 0; y <= 5; y++) { 
						tempPla2[x][y] = tempPla[x][y];
					}}
					coupY = Main.find(coup2, tempPla2);
					
					if(coupY != -1) {
					
						tempPla2[coup2][coupY] = idPlayer*-1;
						
						int maxCoup2 = maxLined(tempPla2, coup2, coupY, idPlayer*-1);
					
						//Arrete tout de suite si l'adversaire peut gagner
						if(maxCoup2 == 4) {
							ret[coup1] = -4;
							coup2 = 7;
						}
					}
				}
			}	
		}
		
		return ret;		
	}
	
}

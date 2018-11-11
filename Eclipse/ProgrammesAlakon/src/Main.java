import java.util.ArrayList;

public class Main {
	
	//Cette méthode sera ensuite inutile, puisque le bouton "Generate" appellera directement la méthode generate()
	public static void main(String[] args) {
	
		String text1 = "execute @e[";
		String text2 = "] ~ ~ ~ set @s ";
		String text3 = "";
		String balise2 =      "#Interp:0,100,1,0,0,1#";
		String balise1 = "#InterpScore:0,100,1,0,Score#";
		
		int nbreCommands = 50;
		
		disp(combine(nbreCommands, text1, generate(balise1, nbreCommands), text2, generate(balise2, nbreCommands), text3));

	}
		
	
	public static String[] combine(int nbreCommands, String arg1, String[] arg2, String arg3, String[] arg4, String arg5) {
		
		String[] ret = new String[nbreCommands];
		
		for(int i = 0; i < nbreCommands; i++)
			ret[i] = arg1 + arg2[i] + arg3 + arg4[i] + arg5;
		
		return ret;
	}
	
	
	/**
	* Renvoie un tableau contenant les Strings a placer dans les commandes générées, a la place de la balise donnée en entree
	*@param entree: la balise (seule: "#example#")
	*@param nbreCommandes: le nombre de commandes a generer
	*@return le String a mettre a la place de la balise sur chaque commande
	*/
	public static String[] generate(String entree, int nbreCommandes) {
		
		String baliseName = readName(entree);
		
		switch(baliseName) {
		case "Interp": return generateInterp(entree, nbreCommandes);
		case "InterpScore": return generateScoreInterp(entree, nbreCommandes);
		default:;
		}
		
		return null;
	}
	
	/**
	* Renvoie un tableau contenant les Strings a placer dans les commandes générées, a la place de la balise donnée en entree si la balise est un Interp
	* Format de la balise: #Interp:Debut,Fin,Puissance,Invert(0 ou 1),NombreDeDecimales#
	*@param entree: la balise (seule: "#example#")
	*@param nbreCommandes: le nombre de commandes a generer
	*@return le String a mettre a la place de la balise sur chaque commande
	*/
	public static String[] generateInterp(String balise, int nbCommands) {
	
		String[] ret = new String[nbCommands];
		String[] infosS = readTag(balise, "Interp");
		double[] infos = new double[6];
		
		for(int i = 0; i <= 5; i++) {
			infos[i] = Double.valueOf(infosS[i]);
		}
	
		double start = infos[0];
		double end = infos[1];
		double power = infos[2];  
		boolean revert = infos[3] == 1;
		int nbreDecimales = (int) infos[4];
		boolean noExt = infos[5] == 1;
		
		int commandeD = 0;
		int commandeF = nbCommands-1;
		int step = 1;
		
		if(noExt) {
			commandeD = 1;
			nbCommands = nbCommands*2 +1;
			commandeF = nbCommands-1;
			step = 2;
		}
		
		int numeroCommande = 0;
		for(int i = commandeD; i <= commandeF; i += step) {
	
			if(nbreDecimales == 0)
				ret[numeroCommande] = String.valueOf((int) Math.round(interp(start, end, nbCommands, i, power, revert)));
			else
				ret[numeroCommande] = String.valueOf(round(interp(start, end, nbCommands, i, power, revert), nbreDecimales));
			
			numeroCommande++;
		}
				
		return ret;
	}
	
	/**
	* Renvoie un tableau contenant les Strings a placer dans les commandes générées, a la place de la balise donnée en entree si la balise est un InterpScore
	* Format de la balise: #InterpScore:Debut,Fin,Puissance,Invert(0 ou 1),Objectif#
	*@param entree: la balise (seule: "#example#")
	*@param nbreCommandes: le nombre de commandes a generer
	*@return le String a mettre a la place de la balise sur chaque commande
	*/
	public static String[] generateScoreInterp(String balise, int nbreCommandes) {
		
		String[] infos = readTag(balise, "InterpScore");
		//index 0: nombre de départ
		//index 1: nombre de fin
		//index 2: puissance (courbure de la montée)
		//index 3: invert (0 ou 1)
		//index 4: nom de l'objectif
		
		int debut = Integer.valueOf(infos[0]);
		int fin = Integer.valueOf(infos[1]);
		double puissance = Double.valueOf(infos[2]);
		boolean invert = (infos[3] == "1");
		String objective = infos[4];		
		
		String[] ret = new String[nbreCommandes];
		String line = "";
		
		for(int i = 0; i < nbreCommandes; i++) {
			line = "score_" + objective + "_min=";
			line += String.valueOf(Math.round(interp(debut, fin, nbreCommandes+1, i, puissance, invert)) + ((i == 0) ? 0 : 1));
			line += ",score_" + objective + "=";
			line += String.valueOf(Math.round(interp(debut, fin, nbreCommandes+1, i+1, puissance, invert)));
			ret[i] = line;
		}
				
		return ret;		
	}
	
	public static void disp(double[] d) {
		
		for(double i : d)
			System.out.println(i);
		
	}
	
	public static void disp(String[] d) {
		
		for(String i : d)
			System.out.println(i);
		
	}
	
	/**
	* Renvoie le nom de la balise ("#Interp:25,4324,4,32,4#" renvoie "Interp")
	*@param entree: la balise (seule: "#example#")
	*@return le nom de la balise
	*/
	public static String readName(String balise) {
		    
		String name = "";
		int i = 1;    
		
		do {
			name += balise.charAt(i);
			i++;  
		} while(balise.charAt(i) != ':');
		    
		return name;
	}
		  
	/**
	* Renvoie les arguments de la balise dans des Strings séparés
	*@param entree: la balise (seule: "#example#")
	*@param baliseName: le nom de la balise (voir readName())
	*@return les arguments de la balise
	*/
	public static String[] readTag(String balise, String baliseName) {
		 
		int beginIndex = baliseName.length()+2;
		int nbreArgs = 1;
		
		for(int i = beginIndex; i < balise.length(); i++)
			if(balise.charAt(i) == ',')
				nbreArgs++;
	    
	    String[] args = new String[nbreArgs];
	    
	    int i = beginIndex;
	    int numeroArg = 0;
	    String argument = "";
	    
	    do {
			
			if(balise.charAt(i) == ',') {
				args[numeroArg] = argument;
				argument = "";
				numeroArg++;
			}
			else
				argument += balise.charAt(i);
			
			i++;
			
		} while(balise.charAt(i) != '#');
		//On lit le dernier (qui n'est pas détecté comme les autres par une virgule)
		args[nbreArgs-1] = argument;
	    
	    return args;
	}
	
	/**
	* Renvoie la valeur a alpha% de l'intervalle [debut; fin]
	*@param debut: le debut de l'intervale
	*@param fin: la fin de l'intervale
	*@param alpha: le pourcentage (0 a 1)
	*@return la valeur a alpha% de l'intervalle [debut; fin]
	*/
	public static double linearInterp(double debut, double fin, double alpha) {
		return fin*alpha + debut;
	}
	
	/**
	* Calcule alpha et renvoie la valeur a (alpha^puissance)% ou 1-(1-alpha)^puissance% de l'intervalle [debut; fin]
	*@param debut: le debut de l'intervale
	*@param fin: la fin de l'intervale
	*@param nbreCommandes: le nombre de commandes a générer
	*@param nbreCommandes: le numéro de la commande a générer
	*@param puissance: la courbure de la montée des valeurs
	*@param invert: inversion de la progression de la pente (rapide puis lente ou lente puis rapide)
	*@return la valeur
	*/
	public static double interp(double debut, double fin, int nbreCommandes, int numeroCommande, double puissance, boolean invert) {
		
		double alpha;
		if(nbreCommandes > 1)
			alpha = (double) numeroCommande/ (double) (nbreCommandes-1);	
		else 
			alpha = 0.5;
		
		if(invert)
			alpha = 1-alpha;
		
		alpha = Math.pow(alpha, puissance);
		
		if(invert)
			alpha = 1-alpha;
		
		return linearInterp(debut, fin, alpha);
	}
	
	/**
	* Renvoie l'arrondi a 10^-decimalsNumber
	*@param number: le nombre
	*@param decimalsNumber: le nombre de décimales
	*@return le nombre arrondi
	*/
	public static double round(double number, int decimalsNumber) {
		
		int power = (int) Math.pow(10, decimalsNumber);
		number *= power;
		return ((double) Math.round(number)) / power;
	}
	
	/**
	* Decompose l'entree en une ArrayList de chaque composants (les balises sont séparées)
	*@param entree: l'entrée (commande brute donnée par l'utilisateur)
	*@return l'ArrayList contenant chaque composant dans l'ordre
	*/
	public static ArrayList<String> decompose(String entree) {
		
		ArrayList<String> composants = new ArrayList<String>();
		String composant = new String();
		//isInSpecial est sur true quand le caractere lu est entre 2 #
		boolean isInSpecial = false;
		boolean ignoreNext = false;
		
		for(int i = 0; i < entree.length(); i++) {		
			
			if(!ignoreNext) {
			
				switch (entree.charAt(i)) {
				
				case '\\': 
					i++;
					ignoreNext = true;
					break;
					
				case '#':
					if(isInSpecial) {
						composant += "#";
						i++;
						isInSpecial = false;
						composants.add(composant);
						composant = "";					
					}
					else {
						isInSpecial = true;
						composants.add(composant);
						composant = "";
					}
					break;
				default:;
				}		
			}
			else
				ignoreNext = false;
			
			composant += entree.charAt(i);
			
		}
		composants.add(composant);
		
		return composants;
	}

}

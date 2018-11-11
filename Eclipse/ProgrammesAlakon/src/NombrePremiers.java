
public abstract class NombrePremiers
{
	
	private static boolean[] primeNbreTab = new boolean[0];
	
	public NombrePremiers() {}
	
	public static boolean estPremier (int nombre)
	{
		if (estDansTab(nombre))
		{ return primeNbreTab[nombre]; }
		else 
		{ return calcEstPremier(nombre); } 
	}

	private static boolean estDansTab (int nombre) { 
		return (primeNbreTab.length > nombre);
	}
	
	private static boolean calcEstPremier (int nombre) {
		
		boolean estPremier = true;
		
		if(nombre != 2)
		{
			for(int y = 2; y <= nombre-1 ; y++)
			{
				if(estDivisible(nombre,y))
				{ estPremier = false; }
			}
		}
		return estPremier;
	}
	
	private static boolean estDivisible (int nombre, int diviseur) {
		double nombreL = nombre;
		double diviseurL = diviseur;
		double resultL;
		resultL = nombreL / diviseurL;
		int resultRounded = (int) resultL;
		
		if(resultL == resultRounded)
		{ return true; }
		else
		{ return false; }
	}

	public static boolean[] createPrimeNbreTab (int jusqua) {
		primeNbreTab = new boolean[jusqua+1];
		
		for(int i = 2; i <= jusqua; i++)
		{ primeNbreTab[i] = calcEstPremier(i); }
		
		return primeNbreTab;
	}
	
	public static boolean[] getPrimeNbreTab() {
		return primeNbreTab;
	}

	public static int[] getPrimeNbres(int jusqua) {
		
		int[] tab = new int[jusqua];
		int newLenght = 0;
		
		for(int nombre = 0; nombre < tab.length; nombre++)
		{
			if(estPremier(nombre))
			{
				tab[newLenght] = nombre;
				newLenght++;
			}
		}
		
		int[] tabFinal = new int[newLenght];
		int o = 0;
		
		for(int i = 0; i < tab.length; i++)
		{
			if(tab[i] > 1)
			{
				tabFinal[o] = tab[i];
				o++;
			}
		}
		
		return tabFinal;
	}
	
}


public class DivisibilitePuissances {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int nombreMax = 3;
		int maxPows = 20;
		
		NombrePremiers.createPrimeNbreTab(nombreMax);
		
		for(int nombre = 2; nombre <= nombreMax; nombre++)
		{	
			//Teste si c'est un nombre premier
			boolean estPremier;
			estPremier = NombrePremiers.estPremier(nombre);
			
			//Si le nombre est premier, teste les divisibilités de ses puissances
			if(estPremier)
			{
				int nombreTest = nombre;
				
				//Teste pour chaque puissance du nombre en question (jusqu'a maxPows)
				for(int pow = 1; pow <= maxPows; pow++)
				{
					
					//Teste si le nombre est divisible
					for(int u = 2; u <= nombreTest-1 ; u++)
					{
						double resultFin = nombreTest / u;
						
						if(estDivisible(nombreTest,u))
						{
							//Regarde si le nombre trouvé est une puissance du nombre
							if(estUnePow(resultFin,nombre,maxPows))
							{
								System.out.print(nombre + ": " + nombreTest + " / " + u + " = " + resultFin + " -> puissance de " + nombre + "\n");
							}
							else
							{
								System.out.print(nombre + ": " + nombreTest + " / " + u + " = " + resultFin + " PROPRIETE NON VERIFIEE" + "\n");
							}
						}
						else
						{
							//System.out.print(nombre + ": " + nombreTest + " / " + u + " = " + resultFin + "\n");
						}
					}
					
					nombreTest = nombreTest*nombre;
				}
			}
			else {}
			
		}
		
	}
	

	
	static boolean estDivisible (int nombre, int diviseur)
	{
		double nombreL = nombre;
		double diviseurL = diviseur;
		double resultL;
		resultL = nombreL / diviseurL;
		int resultRounded = (int) resultL;
		if(resultL == resultRounded)
		{
			return true;
		}
		else return false;
	}

	static boolean estUnePow (double nombreResult, int puissance, int maxPow)
	{
		int puissanceBase = puissance;
		boolean estUnePow = false;
		for(int t = 1; t <= maxPow; t++)
		{
			if(puissance == nombreResult)
			{
				estUnePow = true;
			}
			
			puissance = puissance * puissanceBase;
		}
		
		return estUnePow;
	}

}

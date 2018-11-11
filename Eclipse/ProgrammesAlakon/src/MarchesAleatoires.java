
public class MarchesAleatoires {

	public static void main(String[] args) {
		
		//Nombre de déplacements
		int nbreMoves = 8;
		//Nombre de simulations
		long nbreSim = 10000000;
		
		long startTime = System.currentTimeMillis();
		
		long nbreRetour0 = 0;
		for(long i = 0; i < nbreSim; i++) {
			
			int pos = 0;
			for(int y = 0; y < nbreMoves; y++)
				if(Math.round(Math.random()) == 1)
					pos++;
				else
					pos--;
			
			if(pos == 0)
				nbreRetour0++;
			
		}
		
		
		
		double proportion = ((double) Math.round( (((double) nbreRetour0) / nbreSim ) * 100000)) / 100000;
		
		System.out.print(nbreRetour0 + "/" + nbreSim + " (" + proportion + " = " + proportion*100 + "%): " + (System.currentTimeMillis() - startTime) + " ms");
		
	}
	
}


public class Ville {
	protected String nomVille = new String("Ville");
	protected String nomPays = new String("Pays");
	protected int nbreHabts = 0;
	protected char cat = '?';
	protected static int nbreInstances = 0;
	
	//Constructeurs
	
	public Ville() {
		System.out.print("Construction d'une ville");
		nbreInstances++;
	}
	
	public Ville(String pNom, String pPays, int pHabts)
			throws NombreHabitantsException, NomVilleException {
		if (pHabts < 0)
			throw new NombreHabitantsException(pHabts);
		
		if (pNom.length() < 3)
			throw new NomVilleException(pNom);
		else 
		{
			System.out.print("Construction d'une ville");
			nbreInstances++;
			this.nomVille = pNom;
			this.nomPays = pPays;
			this.nbreHabts = pHabts;
			this.cat = this.calcCat();
		}
	}	
	
	//Accesseurs
	
	public String getNom() {
		return nomVille;
	}
	
	public String getPays() {
		return nomPays;
	}
	
	public int getNbreHabts() {
		return nbreHabts;
	}
	
	public char getCat() {
		return cat;
	}
	
	public static int getNbreInst() {
		return nbreInstances;
	}
	
	//Mutateurs

	public void setNom(String pNom) {
		nomVille = pNom;
	}
	
	public void setPays(String pPays) {
		nomPays = pPays;
	}
	
	public void setNbreHabts(int pNbreHabts) {
		nbreHabts = pNbreHabts;
	}
			
	//Methodes d'instance
	
	protected char calcCat() {
		
		int[] bornesSup = {0, 1000, 10000, 100000, 500000, 1000000, 5000000, 10000000};
		char[] categories = {'?', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
		int i = 0; 
		
		while (i < bornesSup.length && this.nbreHabts > bornesSup[i])
		{
			i++;
		}
		return categories[i];
	}
	
	public String toString() {
	    return "\t"+this.nomVille+" est une ville de "+this.nomPays+ ", elle comporte : "+this.nbreHabts+" habitant(s) => elle est donc de catégorie : "+this.cat;
	}

	public String comparer(Ville v1) {
		String str = new String();

		if (v1.getNbreHabts() > this.nbreHabts)
		str = v1.getNom()+" est une ville plus peuplée que "+this.nomVille;
	
		else
		str = this.nomVille+" est une ville plus peuplée que "+v1.getNom();
	     
		return str;
	}

}
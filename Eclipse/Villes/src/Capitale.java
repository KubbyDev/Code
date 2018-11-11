
public class Capitale extends Ville {

	private String nomMonument;
	
	//Constructeurs
	
	public Capitale() {
		super();
		this.nomMonument = "Monument";
	}
	
	public Capitale(String pNom, String pPays, int pHabts, String pMonument) throws NombreHabitantsException, NomVilleException {
		super(pNom, pPays, pHabts);
		this.nomMonument = pMonument;
	}	

	//Accesseurs
	
	public String getMonument() {
		return nomMonument;
	}
	
	//Mutateurs

	public void setMonument(String pMonument) {
		this.nomMonument = pMonument;
	}

	//Methodes de classe
	
	public String toString() {
	    return super.toString() + "Monument principal: " + this.nomMonument;
	}

}	
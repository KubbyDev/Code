
public enum TypeMoteur {

	Diesel ("Moteur Diesel"),
	Essence ("Moteur Essence"),
	Hybride ("Moteur Hybride"),
	Electrique ("Moteur Electrique");
	
	private String name = new String();
	
	TypeMoteur(String n) {
		name = n;
	}
	
	public String toString() {
		return name;
	}
	
}

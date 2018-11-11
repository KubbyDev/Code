
public enum Marque {

	RENO ("Renault"),
	PIGEOT ("Peugeot"),
	TROEN ("Citroen");
	
	private String name = new String();
	
	Marque(String n) {
		name = n;
	}
	
	public String toString() {
		return name;
	}
	
}

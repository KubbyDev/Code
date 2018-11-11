
public enum Langages {

	Java ("Langage Java"),
	Cplusplus ("Langage C++"),
	C ("Langage C"),
	Python ("Langage Python"),
	VB ("Langage VB"),
	PHP ("Langage PHP"),
	HTML ("Langage HTML"),
	JS ("Langage JS");
		
	private String name = "";
	
	Langages(String name) {
		this.name = name; 
	}
	
	public String toString(){
	    return name;
	}
	
}

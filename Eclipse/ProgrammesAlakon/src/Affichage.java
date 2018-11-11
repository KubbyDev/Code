
public class Affichage {

	public int temps = 0;
	public String path = new String("");
	public String type = new String("");
	
	public Affichage() {
	}
	
	public Affichage(String pPath, int pTemps, String pType) {
		temps = pTemps;
		path = pPath;
		type = pType;
	}

	public int getTemps() {
		return temps;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getType() {
		return type;
	}
	
}

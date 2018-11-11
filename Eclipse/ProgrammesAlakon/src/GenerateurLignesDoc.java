
public class GenerateurLignesDoc {

	public static void main(String[] args) {
		
		String nom = new String("Get Orientation");
		String nomFonction = new String("GetOri");
		String imports = new String("Ori");
		String desc = new String("Returns the Orientation of the entity");

		System.out.print("{\"text\":\" > \",\"bold\":\"true\"},{\"text\":\"" + nom + ":" + espaces(nom) + "\"},{\"selector\":\"@e[type=armor_stand,tag=" + nomFonction + "]\"},{\"text\":\"   [Install]\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/function Gunivers-Lib:Utils/\"}},{\"text\":\"   [Uninstall]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/function Gunivers-Lib:Utils/\"}}");
		
	}
	
	public static String espaces(String nom) {
		String espaces = "";
		for(int i = nom.length(); i <= 24; i++)
			espaces += " ";
		return espaces;
	}

}

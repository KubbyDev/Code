import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class NbreCommandes {

	public static void main(String[] args) {
		
		ArrayList<String> a = new ArrayList<String>();
		
		try {

			Files.lines(Paths.get("C:\\Users\\Deve\\Desktop\\latest.txt")).forEach((Object s) -> a.add((String) s));
			
		} catch (IOException e) {e.printStackTrace();}
		
		ArrayList<Integer> al = new ArrayList<Integer>();
	
		for(String s : a)
			if(matchesSearched(s))
				al.add(getNbre(s));
		
		long sum = 0;
		int max = 0;
		int min = 800;
		
		for(int i : al) {
			sum += i;
			max = Math.max(max, i);
			min = Math.min(min, i);
		}
		
		double moyenne = ((double) sum) / al.size();
		
		System.out.print("Duree du combat: " + al.size()/20 + " sec\nNombre de commandes min: " + min + "\nNombre de commandes max: " + max + "\nMoyenne: " + (int) moyenne);
			
	}

	public static boolean matchesSearched(String s) {
		
		if(s.length() > 0)	{	
			return s.substring(12, s.length()).startsWith("Server thread/INFO]: [Armour Stand: Executed ") && s.substring(12, s.length()).endsWith("commands from function 'tria:main']");
		}
		 
		return false;
	}
	
	public static int getNbre(String s) {
		
		System.out.println(Integer.valueOf(s.substring(57, 60).replace(" ", "")));
		
		return Integer.valueOf(s.substring(57, 60).replace(" ", ""));
	}
	
	public static void disp(ArrayList<Integer> a) {
		
		for(Integer s : a)
			System.out.println(s);
		
	}
	
}

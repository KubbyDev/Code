import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Conv113 {

	public static void main(String[] args) {
		
		ArrayList<String> a = new ArrayList<String>();
		
		try {

			Files.lines(Paths.get("C:\\Users\\Deve\\Desktop\\latest.txt")).forEach((Object s) -> a.add(convert((String) s)));
			
		} catch (IOException e) {e.printStackTrace();}
		
		ArrayList<String> list = new ArrayList<String>();
		
		
		StringBuilder s = new StringBuilder();
		
		for(int i = 0; i < input.length()-1; i++) {
			
			if(input.charAt(i) == '\\' && input.charAt(i+1) == 'n')
				list.add(readLine(new String(s)));
				
			s.append(input.charAt(i));
			
		}
		
	}	
	
	public static ArrayList<String> getScores(String line) {
		
		ArrayList<String> a = new ArrayList<String>();
		String s = line;
		
		while(s.contains("score_")) {
			
			s = s.substring(s.indexOf("score_")+6, s.length());
			
			a.add(s.substring( 0, Math.min(
					Math.max(0, s.indexOf("_min=")),
					Math.max(0, s.indexOf("="))) ));
			
		}
		
		return onlyOnce(a);
	}
	
	public static ArrayList<String> onlyOnce(ArrayList<String> i) {
		
		ArrayList<String> a = new ArrayList<String>();
		
		for(String s : i)
			if(!a.contains(s))
				a.add(s);
		
		return a;		
	}
	
	public static String convert(String input) {
		
	}
	
}

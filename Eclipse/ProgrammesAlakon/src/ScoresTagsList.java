import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class ScoresTagsList {

	public static HashMap<String, String> knownScores = new HashMap<String, String>();
	
	public static void main(String[] args) {
		
		
	}
	
	public static ArrayList<String> getAllScores(ArrayList<File> files) {
		
	}
	
	public static ArrayList<String> getAllScores(File f) {
		
		ArrayList<String> scores = new ArrayList<String>();
		
		try {
			Files.lines(Paths.get(f.getAbsolutePath())).forEach((Object s) -> scores.add((String) s));
		} catch (IOException e) {e.printStackTrace();}
		
		
		
	}
	
	public static String getScores(String command) {
		
	}
	
	public static void fillScores() {
		
		knownScores.put("Constant", "A list of constants to use in operations. For example, you can multiply by 2 using /scoreboard operation @s score *= 2 Constant");
		
	}
	
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileFunction {
    
    private File file;

    public FileFunction (File oldFile) {
    	
    	//Liste des commandes
	    ArrayList<String> commands = new ArrayList<String>();
	    
	    try {
	    	
	    	//Rempli la liste des commandes (et les convertis au passage)
			Files.lines(oldFile.toPath()).forEach((String s) -> commands.add(convert(s)));
			
		} catch (IOException e) {e.printStackTrace();}
		
	    oldFile.delete();
		
		//Cree le fichier renomme et renomme aussi les dossiers
		File newFile = new File(path + File.separator + (f.getParentFile().getAbsolutePath().replace(path , "") + File.separator + f.getName()).toLowerCase());
	    
	    try {
			newFile.createNewFile();
		} catch (IOException e) {e.printStackTrace();}
		
		System.out.println(newFile);
	    
	    try {
	    	
			PrintWriter pw = new PrintWriter(newFile);
			
		    //Ecriture des commandes
		    for(String command : commands) {
		    	pw.println(command);
		    	System.out.println(command);
		    }
		    
		    pw.close();
			
		} catch (FileNotFoundException e) {e.printStackTrace();}
        
    }
    
	public static String convert(String s) {
		
		String func;
		
		if(s.contains("function"))
			func = s.substring(s.indexOf("function") + 9, s.length());
		else
			return s;
		
		return s.substring(0, s.indexOf("function") + 9) + func.toLowerCase();
	}
    
}
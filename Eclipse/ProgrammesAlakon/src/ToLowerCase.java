import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

public class ToLowerCase {

	public static ArrayList<File> folders = new ArrayList<File>();
	
	public static void main(String[] args) {
		
		String path = "C:\\Users\\Deve\\Desktop\\temp";
	
		File dossier = new File(path);
		
		//Regroupe tous les fichiers
		ArrayList<File> all = getAllFiles(dossier);
		
		//Cree les dossiers
		for(File f : folders) {
			
			f.renameTo(new File(path + (f.getAbsolutePath().replace(path, "")).toLowerCase()));
		
		}
		
		for(File f : all) {
			
			//Liste des commandes
		    ArrayList<String> commands = new ArrayList<String>();
		    
		    try {
		    	
		    	//Rempli la liste des commandes (et les convertis au passage)
				Files.lines(f.toPath()).forEach((String s) -> commands.add(convert(s)));
				
			} catch (IOException e) {e.printStackTrace();}
			
		   // f.delete();
			
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
			
		    f.renameTo(newFile);
		    
		}
		
	}
	
	public static ArrayList<File> getAllFiles(File file) {
		
		File[] files = file.listFiles();
		ArrayList<File> ret = new ArrayList<File>();
		
		for(File f : files)
			ret.add(f);
		
		for(File f : files) {
			
			if(f.isDirectory()) {
				ret.remove(f);
				folders.add(f);
				ret.addAll(getAllFiles(f));
			}
		
		}
		
		return ret;
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

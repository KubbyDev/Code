import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFolder {

    private static int readProgress = 0;
	
	public static void main(String[] args) {
		
		ArrayList<Affichage> liste = readFile();
		
		for(Affichage a : liste)
			System.out.print("\n" + a.getPath() + "  " + a.getTemps() + "  " + a.getType());
	}

	public static ArrayList<Affichage> readFile() {
		
		ArrayList<Affichage> listeAffichages = new ArrayList<Affichage>();
		String str = "";
	    
	    try {
			FileReader fr = new FileReader("C:\\Users\\Deve\\Desktop\\Nouveau dossier\\Liste.txt");
			
		    int i = 0;
		    while((i = fr.read()) != -1)
		    {
		    	str += (char)i;
		    	//System.out.print((char)i);
		    }
			
		} catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace();	}
	    
	    int i = 0;
	    int nbre = 0;
	    
	    while(str.charAt(i) != '/')
	    {
	    	if(str.charAt(i) == ';')
	    		nbre++;
	    
	    	i++;
	    }
	    
	    //System.out.println(nbre);
	    
	    for(int u = 1; u <= nbre; u++)
	    {
	    	listeAffichages.add(readString(str));
	    }
	    
	    return listeAffichages;
	}
	
	public static Affichage readString(String str) {
		
		String temps = "";
		String path = "";
		String type = "";
		
		while(str.charAt(readProgress) != ',')
		{
			path += str.charAt(readProgress);
			readProgress++;
		}
		
		readProgress++;
		
		while(str.charAt(readProgress) != ',')
		{
			temps += str.charAt(readProgress);
			readProgress++;
		}
		
		readProgress++;
		
		while(str.charAt(readProgress) != ';')
		{
			type += str.charAt(readProgress);
			readProgress++;
		}
		
		readProgress++;
		
		Affichage a = new Affichage(path, Integer.valueOf(temps), type);
		
		return a;
	}
	
}


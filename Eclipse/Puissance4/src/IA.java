import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class IA {
	
	public int[][] plateau;
	public boolean fileExists;
	public boolean situationExists;	
	
	public IA() {
	}
	
	public int play() {
		
		plateau = Main.getPlateau();
		int progress = Main.progress;
		int[] res = findResolution(progress);
		
		if(res != null)
			return selectPlay(res);
		
		if(!fileExists) {
			createFile(progress);
			writeSituation(progress, true);
		}
		else if(!situationExists)
			 	 writeSituation(progress, false);
		
		return random();
	}
	
	private int random() {
		return (int) Math.round(Math.random()*6);
	}
	
	public File findFile(int coup) {
		
		String str = new String("F:\\JavaCode\\Puissance4\\baseDonnees\\" + coup);
		File file = new File(str);
		
		fileExists = true;
		
		if(file.exists())
			return file;
		else {	
			fileExists = false;
			return null;
		}
	
	}
	
	public ArrayList<Situation> readFile(File f) {

		ArrayList<Situation> a = new ArrayList<Situation>();
		
		try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))) { 
			   
	    	try {
	    		Situation sit = (Situation) ois.readObject();
	    	 	while(sit != null) {
	    	 		a.add(sit);
	    	 		sit = (Situation) ois.readObject();
	    		}	
	    	}
		    catch (ClassNotFoundException e) { e.printStackTrace(); }
	    
    		ois.close();
	    	
	    } catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) {} 
		
		return a;
	}

	public int[] findResolution(int coup) {
		
		File f = findFile(coup);
		
		if(f == null)
			return null;
		
		ArrayList<Situation> a = readFile(f);
		int[] resolution = null;
		boolean found = false;
		
		for(int i = 0; i < a.size(); i++) {
			
			Situation sit = a.get(i);
			
			if(Main.tabEqual(sit.getPlateau(), plateau)) {
				found = true;
				resolution = sit.getResolution();
				i = a.size()-1;
				Main.coup[Main.progress][1] = i;
			}
			
		}
		
		situationExists = true;
		
		if(found)
			return resolution;
		else
		{	
			situationExists = false;
			return null;
		}
		
	}

	public void writeSituation(int coup, boolean empty) {
		
		Situation sit = new Situation(plateau);		
		ArrayList<Situation> a = new ArrayList<Situation>();
		
		if(!empty) {
			File f = new File("F:\\JavaCode\\Puissance4\\baseDonnees\\" + coup);
			a = readFile(f);
		}
		
		a.add(sit);
		
		writeFile(coup, a);
		
	}
	
	public void createFile(int coup) {
		
		File f = new File("F:\\JavaCode\\Puissance4\\baseDonnees\\" + coup);
		
		try {
			f.createNewFile();
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public int selectPlay(int[] resolution) {
		
		long s = 0;
		for(int i : resolution)
			s += i;
		
		long r = Math.round(Math.random()*s);
		
		int p = 0;
		while(r >= 0) {
			r -= resolution[p];
			p++;
			if(p >= 7) return 6;
		}
		if(p <= -1) return 0;
		return p-1;
	}
	
	public void learn(int recompense, int start) {
		
		for(int i = start; i <= Main.progress; i += 2)
		{
			File f = findFile(i);
			
			if(f != null) {
				ArrayList<Situation> a = readFile(f);
			
				//Modifie la resolution dans l'ArrayList
				Situation sit = a.get(Main.coup[i][1]);
				sit.addToRes(Main.coup[i][0], recompense);
				a.set(Main.coup[i][1], sit);
				
				writeFile(i, a);		
			}
		}
	}
	
	public void writeFile(int index, ArrayList<Situation> a) {
				
		File f = new File("F:\\JavaCode\\Puissance4\\baseDonnees\\" + index);
		
		f.delete();
		
		try {
			f.createNewFile();
		} catch (IOException e) { e.printStackTrace(); }
		
	    try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
	      
	    	for(int i = 0; i < a.size(); i++)
	    	{
	    		oos.writeObject(a.get(i));
	    	}
	    	
			oos.close();
	      
	    } catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		    
	}
	
}
import javax.swing.JFrame;

public class Fourmi {

	static boolean plateau[][] = new boolean[90][90];
	static FourmiPanneau pan = new FourmiPanneau(); 
	static JFrame fenetre = new JFrame();
	static int posX = 45;
	static int posY = 45;
	static int rot = 1;
	
	public static void main(String[] args) {
		
		fenetre.setTitle("Fourmi");
	    fenetre.setSize(350, 350);
	    fenetre.setLocationRelativeTo(null);
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fenetre.setResizable(false);
	    fenetre.setContentPane(pan);               
	    
	    fenetre.setVisible(true);
		
	    while(posX < 89 && posX > 0 && posY < 89 && posY > 0) {
	    	
	    	tourne();
	    	
	    	if(isBlack())
	    		plateau[posX][posY] = false;
	    	else
	    		plateau[posX][posY] = true;
	    		
	    	avance();
	    	
	    	//System.out.println(posX + "   " + posY + "   " + rot + "   " + isBlack());
	    	
	    	pan.repaint();
	    	
	    	try {
				Thread.sleep(1);
			} catch (InterruptedException e) { e.printStackTrace();	}
	    	
	    }
	    
	}
	
	public static boolean isBlack() {
		return plateau[posX][posY];
	}
	
	public static void tourne() {
		if(isBlack())
    		rot++;
    	else
    		rot--;
    	
    	if(rot == 0)
    		rot = 4;
    	
    	if(rot == 5)
    		rot = 1;
	}

	public static void avance() {
		
	if(rot == 1)
		posX++;
		
	if(rot == 2)
		posY++;
	
	if(rot == 3)
		posX--;
	
	if(rot == 4)
		posY--;
	}
	
}

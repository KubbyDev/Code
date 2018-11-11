import javax.swing.JFrame;

public class Main {

		static PanneauMorpion pan = new PanneauMorpion(); 
		static JFrame fenetre = new JFrame();
	
	public static void main(String[] args) {

		fenetre.setTitle("Morpion");
	    fenetre.setSize(400, 400);
	    fenetre.setLocationRelativeTo(null);
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fenetre.setResizable(false);
	    fenetre.setContentPane(pan);               
	    
	    fenetre.setVisible(true);
		
	}

}

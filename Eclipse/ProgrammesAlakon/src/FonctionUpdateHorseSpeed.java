import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class FonctionUpdateHorseSpeed {

    static Panneau pan = new Panneau(); 
	static JFrame fenetre = new JFrame();
	
	//Parametres
	static int nombreCMD = 4;
	static float vitesseMin = 0.03f,
					vitesseMax = 0.85f,
						scoreMin = 0f,
							scoreMax = 5000f;
	static String commandeP1 = new String("entitydata @e[type=horse,score_VehicleType_min=1,score_VehicleType=9,score_GroundSpeed_min="),
					commandeP2 = new String(",score_GroundSpeed="),
						commandeP3 = new String("] {Attributes:[{Name:\"generic.movementSpeed\",Base:"),
							commandeP4 = new String("}]}");
    
	public static void main(String[] args) {
		
	    fenetre.setTitle("Fonction UpdateHorseSpeed");
	    fenetre.setSize(400, 470);
	    fenetre.setLocationRelativeTo(null);
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fenetre.setResizable(false);
	    
	    pan.setLayout(new BorderLayout());
	    pan.add(new Bouton("SOUTH"), BorderLayout.SOUTH);
	    
	    fenetre.setContentPane(pan);               
	    
	    fenetre.setVisible(true);
	    
	    animation();
	}
	
	public static String[] generate() {
		
		//Initialsation des variables
		nombreCMD = nombreCMD -1;
		String ligne = new String("");
		String[] lignes = new String[nombreCMD+2];
		int progress = 0;
		float alpha1 = 1f;
		float alpha2 = 1f;
		float alpha3 = 1f;
		int scoreFourchetteBasse = 0;
		int scoreFourchetteHaute = 0;
        float movementSpeed = vitesseMin;
		int tmp;
		float tmp2;
		
		ligne = "#Met a jour la vitesse du cheval en fonction de son score (Commandes générées par mon programme trop génial en java :D)";
		lignes[progress] = ligne;
		progress++;
		ligne = "";
		
		//Premiere commande (quand le score est a 0)
		ligne = ligne + commandeP1;
		ligne = ligne + scoreFourchetteBasse;
		ligne = ligne + commandeP2;
		ligne = ligne + scoreFourchetteHaute;
		ligne = ligne + commandeP3;
		ligne = ligne + movementSpeed;
		ligne = ligne + commandeP4;
        
		lignes[progress] = ligne;
		progress++;
		ligne = "";
		
		for (float i = 1f; i <= nombreCMD; i++)
		{
			//Calcul des variables
			alpha1 = (i-1)/nombreCMD;
			alpha2 = i/nombreCMD;
			alpha3 = i/nombreCMD;
			scoreFourchetteBasse = (int) linearInterp(scoreMin, scoreMax, alpha1) + 1;
			scoreFourchetteHaute = (int) linearInterp(scoreMin, scoreMax, alpha2);
			movementSpeed = squareInterp(vitesseMin, vitesseMax, alpha3) * 1000;
			
			//Arrondi (chhht, ça marche donc c'est bon)
			tmp = (int) movementSpeed;
			tmp2 = tmp;
			movementSpeed = tmp2 / 1000;
			
			//Generation de la commande
			ligne = ligne + commandeP1;
			ligne = ligne + scoreFourchetteBasse;
			ligne = ligne + commandeP2;
			ligne = ligne + scoreFourchetteHaute;
			ligne = ligne + commandeP3;
			ligne = ligne + movementSpeed;
			ligne = ligne + commandeP4;
			
			lignes[progress] = ligne;
			progress++;
			ligne = "";
		}
		
		return lignes;
	}
	
	public static float linearInterp(float min, float max, float alpha)
	{
		return (max-min) * alpha + min;
	}

	public static float squareInterp(float min, float max, float alpha)
	{
		alpha = 1 - alpha;
		return (max-min) * (1- (alpha*alpha)) + min;
	}      
	
	private static void animation() {
	    for(int i = -1000; i < 1000 + pan.getWidth(); i++)
	    {
	      pan.setPosX(i);
	      pan.repaint();
	      
	      try {
	        Thread.sleep(2);
	      } catch (InterruptedException e) { e.printStackTrace(); }
	      
	    }
	animation();
	}

	public static void display(String[] str) {
		for(String ligne : str)
			System.out.print(ligne + "\n");
	}
}

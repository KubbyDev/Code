import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
 
public class Panneau extends JPanel { 
  
	private static final long serialVersionUID = 8544534511093150594L;
	private int posX = -500, 
				 posY = 0;
	
	
	
	public void paintComponent(Graphics g) {
		Font fontBase = new Font("Arial", Font.PLAIN, 16);
		Font font2 = new Font("Arial", Font.PLAIN, 18);
		int espaceLignes = 30;
		int yLigne = 40;
		
		Image img = null;
		try {
			img = ImageIO.read(new File("F:\\JavaCode\\ProgrammesAlakon\\src\\Image.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
	    g.drawImage(img, posX, 150, this.getWidth()/3, this.getHeight()/3, this);
		
		g.setFont(fontBase);
	    g.setColor(Color.black); 
	    
		g.drawString("Nombre de commandes: ", 10, yLigne);
		yLigne = yLigne + espaceLignes;
		g.drawString("Vitesse Minimale: ", 10, yLigne);
		yLigne = yLigne + espaceLignes;
		g.drawString("Vitesse Maximale: ", 10, yLigne);
		yLigne = yLigne + espaceLignes;
		g.drawString("Score Minimal: ", 10, yLigne);
		yLigne = yLigne + espaceLignes;
		g.drawString("Score Maximal: ", 10, yLigne);
		
		yLigne = yLigne + (espaceLignes*3)/2;
	    g.setFont(font2);
	    g.setColor(Color.black);   
		g.drawString("Commande: ", 10, yLigne);
		
	    g.setFont(fontBase);
	    g.setColor(Color.black);   
	    yLigne = yLigne + espaceLignes;
	    
	    g.drawString("Partie1: ", 10, yLigne);
		yLigne = yLigne + espaceLignes;
		g.drawString("Partie2: ", 10, yLigne);
		yLigne = yLigne + espaceLignes;
		g.drawString("Partie3: ", 10, yLigne);
		yLigne = yLigne + espaceLignes;
		g.drawString("Partie4: ", 10, yLigne);

	}      
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}     

}
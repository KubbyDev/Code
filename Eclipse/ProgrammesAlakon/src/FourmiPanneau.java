import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class FourmiPanneau extends JPanel {

	public void paintComponent(Graphics g) {
		
		if(Fourmi.isBlack())
			g.setColor(Color.white);
		else
			g.setColor(Color.black);
		
		g.fillRect((Fourmi.posX)*3-1, (Fourmi.posY)*3-1, 3, 3);
		
	}
	
}

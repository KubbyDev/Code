import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Panneau extends JPanel {

	private static final long serialVersionUID = 3153962253715907216L;
	private String display = new String();
	private String display2 = new String();
	Font Prim = new Font("Arial", Font.PLAIN, 16);
	Font Sec = new Font("Arial", Font.PLAIN, 12);
 	
	public Panneau() {	
	}
	
	public void paintComponent(Graphics g) {
		
		
		super.paintComponent(g);
		
		g.setFont(Prim);
		g.setColor(Color.black);
		g.drawString(display, 350 - (g.getFontMetrics().stringWidth(display)), 60);

		g.setFont(Sec);
		g.setColor(Color.gray);
		g.drawString(display2, 300 - (g.getFontMetrics().stringWidth(display2)), 20);
		
	}
	
	public void updateDisp(String newDisp, String disp) {
		this.display = newDisp;
		this.display2 = disp;
		this.repaint();
	}
	
	public void error(String message) {
		this.display = message;
		this.repaint();
	}
	
}

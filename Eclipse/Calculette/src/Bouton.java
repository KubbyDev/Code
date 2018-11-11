import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class Bouton extends JButton implements MouseListener {

	private static final long serialVersionUID = -8525715718794492788L;
	public int buttonID;
	/*
	 * -2: tout effacer
	 * -1: effacer
	 * 0 a 9: nombres
	 * 10: .
	 * 11: +
	 * 12: *
	 * 13: -
	 * 14: /
	 * 20: +/-
	 * 100: =
	 *  
	 */
	
	public Bouton(int id, String name) {
		this.setText(name);
		this.addMouseListener(this);
	    this.buttonID = id;
	}

	public void mouseClicked(MouseEvent arg0) {
		Main.buttonClick(this.buttonID);
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {

	}
	
}

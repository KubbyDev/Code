import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class ResetButton extends JButton implements MouseListener {

	private static final long serialVersionUID = -8525715718794492788L;
	
	public ResetButton() {
	    this.addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent arg0) {
		Main.waitForReset = false;
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

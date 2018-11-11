import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class Button extends JButton implements MouseListener {

	private static final long serialVersionUID = -8525715718794492788L;
	public int number = 0;
	
	public Button() {
	    this.addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent arg0) {
		if(Main.waitForPlayer)
			Main.play(number);		
	}

	public void setNumber(int i) {
		number = i;
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



import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
  
public class Bouton extends JButton implements MouseListener{
  private String name;

  public Bouton(String str){
    super(str);
    this.name = str;
    this.addMouseListener(this);
  }

  	public void paintComponent(Graphics g){
  		Graphics2D g2d = (Graphics2D)g;
    	g2d.setColor(Color.black);
    	g2d.drawString(this.name, this.getWidth() / 2 - (this.getWidth() / 2 /4), (this.getHeight() / 2) + 5);
  	}

  	public void mouseClicked(MouseEvent arg0) {
  		FonctionUpdateHorseSpeed.display(FonctionUpdateHorseSpeed.generate());
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
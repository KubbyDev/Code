import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Function;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FrameManager extends JPanel implements MouseListener {

    private Mandelbrot frame;
	private Color[][] screen;
	
	public FrameManager(Mandelbrot parent) {
		
		frame = parent;
		addMouseListener(this);
		
	}
	
	public void paintComponent(Graphics g) {
		
		for(int x = 0; x < screen.length; x++) {
			
			for(int y = 0; y < screen[x].length; y++) {
			
				g.setColor(screen[x][y]);
				g.fillRect(x, y, 1, 1);
				
			}
			
		}
		
		frame.screenUpdated = true;
		
	}
	
	public void draw(double startx, double starty, double width, double height, Function<Integer, Color> coloration_function, int max_i) {
		
		screen = new Color[this.getWidth()][this.getHeight()];
		
		for(int x = 0; x < this.getWidth(); x++) {
			
			for(int y = 0; y < this.getHeight(); y++)
				screen[x][y] = calculatePixel(x * width / this.getWidth() + startx, y * height / this.getHeight() + starty, coloration_function, max_i);	
			
		}
		
		this.repaint();
		
	}
	
	private static Color calculatePixel(double x0, double y0, Function<Integer, Color> coloration_function, int max_i) {
		
		double x = 0, y = 0;
		int i = 0;
		
		while (x*x + y*y < 4 && i < max_i) {
			
			double x_temp = x*x - y*y + x0;
			y = 2*x*y + y0;
			x = x_temp;
			
			i++;	
		}

		return coloration_function.apply(i * 255 / max_i);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(frame.screenUpdated)
			frame.click(e.getX(), e.getY(), SwingUtilities.isRightMouseButton(e));
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {	
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}

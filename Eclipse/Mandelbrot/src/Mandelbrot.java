import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class Mandelbrot {

	private double posX = -0.75;	   //Increase this value to go to the right
	private double posY = 0;           //Increase this value to go up
	private double zoom = 1;           //Reduce this value to zoom
	private double screenRatio = 1.75; //Screen width / height
	public boolean screenUpdated;
	
	private FrameManager frame;
	private JFrame fen;
	
	public static void main(String[] args) {
		
		Mandelbrot main = new Mandelbrot(1050, 600);
		main.updateFrame();
	
	}
	
	public Mandelbrot(int width, int height) {

		fen = new JFrame();
		fen.setTitle("Mandelbrot");
		fen.setSize(width, height);
		fen.setLocationRelativeTo(null);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setResizable(true);
		
		frame = new FrameManager(this);
		fen.getContentPane().add(frame);
		
		fen.addComponentListener(new ComponentAdapter() {
			
			public void componentResized(ComponentEvent e) {
				
				if(screenUpdated)
					resize();
            
			}
			
		});
		
		fen.setVisible(true);
		
	}
	
	private void updateFrame() {
		
		frame.draw(zoom * -screenRatio + posX, zoom * -1 - posY, zoom * 2 * screenRatio, zoom * 2, ColorationFunction.Simple, 1000);
		
	}

	//Gets the event frow the FrameManager
	public void click(int x, int y, boolean isRight) {
		
		screenUpdated = false;
		
		posX = (((double) x / (double) frame.getWidth())- 0.5) * zoom * 3.5 + posX;
		posY = (((double) y / (double) frame.getHeight())- 0.5) * zoom * -2 + posY;
		zoom *= (isRight) ? 2 : 0.5;
		
		updateFrame();
		
	}
	
	public void resize() {
		
		screenUpdated = false;
		
		screenRatio = (double) frame.getWidth() / (double) frame.getHeight();
		updateFrame();
		
	}
	
}

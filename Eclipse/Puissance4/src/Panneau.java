import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Panneau extends JPanel implements KeyListener {

	private static final long serialVersionUID = 8544534511093150594L;
	public boolean firstPaint = true;
	public int playX = 0;
	public int playY = 0;
	public boolean gameEnd = false;
	ResetButton rButton;
	public int color = -1; 

	public Panneau() {
		
		this.addButtons();
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
		
	}	
	
	public void addButtons() {
		
		ArrayList<Button> buttons = new ArrayList<Button>();
		
	    this.setPreferredSize(new Dimension(400, 500));    
	    
	    Button button1 = new Button();
	    buttons.add(button1);
	    Button button2 = new Button();
	    buttons.add(button2);
	    Button button3 = new Button();
	    buttons.add(button3);
	    Button button4 = new Button();
	    buttons.add(button4);
	    Button button5 = new Button();
	    buttons.add(button5);
	    Button button6 = new Button();
	    buttons.add(button6);
	    Button button7 = new Button();
	    buttons.add(button7);
	
	    this.setBackground(Color.WHITE);
	    
        //disp(buttons);
        
	    int xPos = 0;
	    int i = 0;
	    
	    for(Button button : buttons)
	    {
	    	button.setNumber(i);
	    	button.setPreferredSize(new Dimension(50, 50));
	    	button.setAlignmentX(xPos);
	    	button.setAlignmentY(-500);
	    	xPos += 20;
	    	this.add(button);
	    	i++;
	    }
	    
	}
	
	public void paintComponent(Graphics g) {
			
		if(firstPaint) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 400, 500);
			g.setColor(Color.WHITE);
			g.fillRect(8, 60, 378, 350);
		}
		else 
		{
			g.setColor(Color.BLACK);
			g.fillOval(playX-1, playY-1, 32, 32);
			
			if(Main.joueur == 1)
				g.setColor(Color.RED);
			else
				g.setColor(new Color(255, 200, 0));
			g.fillOval(playX, playY, 30, 30);	
		}	
		
		if(gameEnd)
		{
			
			Font font = new Font("Arial", Font.BOLD, 20);
			g.setFont(font);
			String player = new String();
			
			if(Main.winner == -1) {
				g.setColor(new Color(255, 200, 0));
				player = new String("Yellow");
			}
			else {
				player = new String("Red");
				g.setColor(Color.RED);
			}
			
			if(Main.winner == 0) {
				g.setColor(Color.WHITE);
				g.drawString(("Equality !"), 90, 450);
			}
			else {
				g.drawString(("Player " + player + " Won !"), 90, 450);
			}
			
			if(!Main.autoReset)
				rButton = addResetButton();
			
		}
		
		if(Main.colorDisp) {
			g.setColor(getColor(color));
			g.fillRect(0, 500, 400, 200);
		}
		
		Main.waitForPaint = false;
		
	}
	
	public ResetButton addResetButton() {
		
		ResetButton reset = new ResetButton();
		reset.setText("Reset");
		reset.setBounds(300, 420, 80, 40);
    	this.add(reset);
		
    	return reset;
	}
	
	public void reset() {
		
		if(rButton != null)
			this.remove(rButton);
		
		rButton = null;
		
	}
	
	public Color getColor(int color) {
		
		if(color == 0)
			return new Color(65, 65, 65);
		
		if(color == 1)
			return new Color(105, 105, 105);
		
		if(color == 2)
			return new Color(135, 135, 135);
	
		if(color == 3)
			return new Color(165, 165, 165);
		
		if(color == 4)
			return new Color(195, 195, 195);
		
		if(color == 5)
			return new Color(225, 225, 225);
		
		if(color == 6)
			return new Color(255, 255, 255);
		
		return new Color(0, 0, 0);
		
	}
	
	public void keyPressed(KeyEvent e) {		
		
		int code = e.getKeyCode();
	
		if(code >= 49 && code <= 55 && Main.waitForPlayer)
			Main.play(code-49);
	
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

}

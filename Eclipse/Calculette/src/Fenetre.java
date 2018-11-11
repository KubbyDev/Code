import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Fenetre extends JFrame {

	private static final long serialVersionUID = 8164118974463460991L;
	private Panneau pan = new Panneau();

	public Fenetre() {	
		
		this.setTitle("Calculatrice");
		this.setSize(400, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Container cont = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		((JSplitPane) cont).setEnabled(false);
		((JSplitPane) cont).setDividerLocation(80);
		
		cont.add(pan);
		cont.add(panneauBouttons());

		this.getContentPane().add(cont);
		
	}
	
	private static JPanel panneauBouttons() {
		
		JPanel p = new JPanel();
		
		GridLayout gl = new GridLayout();
		gl.setColumns(4);
		gl.setRows(5);
		p.setLayout(gl);
		
		p.add(new Bouton(-10, ""));
		p.add(new Bouton(20, "+/-"));
		p.add(new Bouton(-2, "C"));
		p.add(new Bouton(-1, "<"));
		p.add(new Bouton(7, "7"));
		p.add(new Bouton(8, "8"));
		p.add(new Bouton(9, "9"));
		p.add(new Bouton(11, "+"));
		p.add(new Bouton(4, "4"));
		p.add(new Bouton(5, "5"));
		p.add(new Bouton(6, "6"));
		p.add(new Bouton(12, "*"));
		p.add(new Bouton(1, "1"));
		p.add(new Bouton(2, "2"));
		p.add(new Bouton(3, "3"));
		p.add(new Bouton(13, "-"));
		p.add(new Bouton(0, "0"));
		p.add(new Bouton(10, "."));
		p.add(new Bouton(100, "="));
		p.add(new Bouton(14, "/"));
		
		return p;
	}

	public void updateDisp(String newDisp, String disp2) {
		pan.updateDisp(newDisp, disp2);
	}
	
	public void error(String message) {
		pan.error(message);
	}
	
}



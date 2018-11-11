package reseau;

import java.util.ArrayList;

public class Example {

	private ArrayList<Float> inputs;
	private int label;
	
	public Example(ArrayList<Float> pInputs, int pLabel) {
		
		label = pLabel;
		inputs = pInputs;
		
	}
	
	public ArrayList<Float> inputs() {
		return inputs;
	}
	
	public int label() {
		return label;
	}
	
}

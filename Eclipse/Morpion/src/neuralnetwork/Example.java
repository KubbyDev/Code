package neuralnetwork;

public class Example {

	private float[] inputs;
	private int label;
	
	/**
	 * The inputs are the object given to the network, the label is the expected answer
	 * 
	 * @param pInputs
	 * @param pLabel
	 */
	public Example(float[] pInputs, int pLabel) {
		
		label = pLabel;
		inputs = pInputs;
		
	}
	
	public float[] inputs() {
		return inputs;
	}
	
	public int label() {
		return label;
	}
	
}

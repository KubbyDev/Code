package neuralnetwork;

import java.io.Serializable;
import java.util.ArrayList;

public class BioConnection implements Serializable {

	private static final long serialVersionUID = -2682204287001753512L;
	private int connectedLayer;
	private int connectedIndex;
	private float connectedWeight;
	
	public BioConnection(int layer, int index, float weight) {
		
		connectedLayer = layer;
		connectedIndex = index;
		connectedWeight = weight;
		
	}
	
	public float getInput(ArrayList<float[]> inputs) {
		
		//System.out.print("Layer " + connectedLayer + ", index: " + connectedIndex + " size " + inputs.get(connectedLayer).length + "  ");	
			
		float input = inputs.get(connectedLayer)[connectedIndex];
		
		return input * connectedWeight;
	}
	
	public void display() {
		
		System.out.println("Layer " + connectedLayer + ", Neuron " + connectedIndex + ": Weight = " + connectedWeight);
		
	}
	
	public boolean equals(BioConnection b) {
		
		return b.connectedIndex == connectedIndex && b.connectedLayer == connectedLayer;
	}
	
	public BioConnection copy() {
		
		int l = connectedLayer;
		int i = connectedIndex;
		float w = connectedWeight;
		
		return new BioConnection(l, i, w);
	}
	
	void addToWeight(float toAdd) {
		
		connectedWeight += toAdd;
	}
	
}

package reseau;

import java.io.Serializable;
import java.util.ArrayList;

public class Neuron implements Serializable {
		
	private static final long serialVersionUID = 4737079993472230069L;
	private ArrayList<Float> inputsWeights;
	private float biais;

	public Neuron(ArrayList<Float> weights, float pBiais) {
		inputsWeights = weights;
		biais = pBiais;
	}
	
	public float simulate(ArrayList<Float> inputs) {
		
		float sum = 0;
		
		for(int i = 0; i < inputs.size(); i++)
			sum += ( inputs.get(i) * inputsWeights.get(i));
		
		return clamp(sum + biais, 0, 1);
	}
	
	private float clamp(float x, float min, float max) {
		return Math.max(Math.min(x, max), min);
	}

	public void display() {
		
		System.out.println("Nombre d'inputs: " + inputsWeights.size());
		System.out.println("Seuil d'activation: " + (-biais));
		
		for(int i = 0; i < inputsWeights.size(); i++)
			System.out.println("Weight sur l'input " + i + ": " + inputsWeights.get(i));
		
	}
	
	public ArrayList<Float> weights() {
		
		return inputsWeights;
	}
	
}

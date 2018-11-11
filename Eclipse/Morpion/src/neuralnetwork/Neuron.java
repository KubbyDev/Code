package neuralnetwork;

import java.io.Serializable;
import java.util.Arrays;

public class Neuron implements Serializable {
		
	private static final long serialVersionUID = 4737079993472230069L;
	public float[] inputsWeights;
	private float biais;

	public Neuron(float[] weights, float pBiais) {
		inputsWeights = weights;
		biais = pBiais;
	}
	
	public float simulate(float[] inputs) {
		
		float sum = 0;
		
		for(int i = 0; i < inputs.length; i++)
			sum += ( inputs[i] * inputsWeights[i]);
		
		return clamp(sum + biais, 0, 1);
	}
	
	private float clamp(float x, float min, float max) {
		return Math.max(Math.min(x, max), min);
	}

	public void display() {
		
		System.out.println("Nombre d'inputs: " + inputsWeights.length);
		System.out.println("Seuil d'activation: " + (-biais));
		
		for(int i = 0; i < inputsWeights.length; i++)
			System.out.println("Weight sur l'input " + i + ": " + inputsWeights[i]);
		
	}
	
	public float[] weights() {
		
		return inputsWeights;
	}
	
	public void modifyWeights(float strengh, float modifiedProportion) {
		
		int nbreModifs = (int) Math.round(inputsWeights.length*modifiedProportion);
		
		for(int i = 0; i < nbreModifs; i++)
			inputsWeights[(int) Math.round(Math.random()*(inputsWeights.length-1))] += (Math.random()-0.5f) * strengh;
		
	}
	
	public Neuron copy() {
		
		float f = biais;
		
		return new Neuron(Arrays.copyOf(inputsWeights, inputsWeights.length), f);
	}
	
}

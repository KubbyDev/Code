package neuralnetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BioNeuron implements Serializable {

	private static final long serialVersionUID = 4737079531762230069L;
	public ArrayList<BioConnection> connections;
	private float biais;
	private int layer; //La couche dans laquelle se situe le neurone. Demarre a 1 car 0 c'est les inputs

	public BioNeuron(ArrayList<BioConnection> pConnections, float pBiais, int pLayer) {
		
		connections = pConnections;
		biais = pBiais;
		layer = pLayer;
	
	}
	
	public float simulate(ArrayList<float[]> inputs) {
		
		float sum = 0;
		
		for(BioConnection input : connections)
			sum += input.getInput(inputs);
		
		//System.out.println("Layer " + layer);
		
		return clamp(sum + biais, 0, 1);
	}
	
	private float clamp(float x, float min, float max) {
		return Math.max(Math.min(x, max), min);
	}

	public void display() {
		
		System.out.println("Nombre d'inputs: " + connections.size());
		System.out.println("Seuil d'activation: " + (-biais));
		
		for(BioConnection c : connections)
			c.display();
		
	}
		
	public void addConnections(int number, float weightMult, ArrayList<ArrayList<BioNeuron>> network, int nbInputs) {
		
		for(int i = 0; i < number; i++) {
			
			//0 est la ligne d'input de départ, 1+ sont les outputs des couches précédentes
			int connectedLayer = random(0, layer-1);
			//Un index random entre 0 et la taille de la couche concernee
			int connectedIndex = random(0, (connectedLayer == 0) ? nbInputs-1 : network.get(connectedLayer-1).size()-1);  
			float connectedWeight = (float) (weightMult*(Math.random()*2 -1));
			
			BioConnection bc = new BioConnection(connectedLayer, connectedIndex, connectedWeight);
			
			//Si la connection existe deja on modifie juste le weight
			BioConnection connection = findInConnections(bc);
			
			if(connection == null)
				connections.add(bc);
			else
				connection.addToWeight(connectedWeight);
			
		}
		
	}
	
	public void removeConnections(int number) {
		
		for(int i = 0; i < number; i++)
			if(connections.size() > 0)
				connections.remove(random(0, connections.size()-1));
		
	}

	public BioNeuron copy() {
		
		float f = biais;
		int i = layer;
		
		ArrayList<BioConnection> c = new ArrayList<BioConnection>();
		
		for(BioConnection b : connections)
			c.add(b.copy());
		
		return new BioNeuron(c, f, i);
	}
	
	public static int random(int min, int max) {
		
		return (int) (Math.round(Math.random() * (max-min)) + min);
	}
	
	BioConnection findInConnections(BioConnection toFind) {
		
		for(BioConnection bc : connections)
			if(bc.equals(toFind))
				return bc;
		
		return null;		
	}
	
}

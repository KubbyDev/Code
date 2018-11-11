package neuralnetwork;

import java.util.ArrayList;

public class BiologicalNetwork extends Network {

	private ArrayList<ArrayList<BioNeuron>> network;
	private int nbInputs;
	private int nbOutputs;
	
	public float adjustPrecision = 0.1f;
	
	public static float weightMult = 3.0f;
	
	
	// -------------------------------------------- Constructeurs --------------------------------------------
	
	
	
	 /**
     * Example: (784,10) will take 784 inputs and give 10 different possible outputs
     *
     * @param nbInputs: The number of inputs of the network
     * @param nbOutputs: The number of outputs of the network
     *
     */
	public BiologicalNetwork(int[] neuronsPerLayer) {
		
		boolean validArg = true;
		
		if(neuronsPerLayer.length >= 2) {
			for (int i = 0; i < neuronsPerLayer.length; i++)
				if(neuronsPerLayer[i] < 1)
					validArg = false;
		}
		else
			validArg = false;
		
		if(validArg) {
			network = randomNetwork(neuronsPerLayer);
			nbInputs = neuronsPerLayer[0];
			nbOutputs = neuronsPerLayer[neuronsPerLayer.length-1];
		}
		else {
			System.out.print("The numbers must be positive numbers (!= 0). You entered: ");
			disp(neuronsPerLayer);
			System.out.println();
			throw new IllegalArgumentException();	
		}
		
		adjustPrecision = 1.0f;
	
	}
	
	 /**
     * Reads the network at the given slot on the networks folder. To change the path of this folder, please change the public static String path of this class
     *
     * @param s
     *
     */
	@SuppressWarnings("unchecked")
	public BiologicalNetwork(int slot) {
		
		if(Save.exists(slot, Network.BIOLOGICAL)) {
			
			Save s = Save.read(slot, Network.BIOLOGICAL);
			network = (ArrayList<ArrayList<BioNeuron>>) s.network();
			nbInputs = s.nbInputs();
			nbOutputs = s.nbOutputs();
			adjustPrecision = (float) s.otherInfos().get(0);
			
		}
		else {
			
			System.out.println("No network saved on slot " + Network.BIOLOGICAL + "/" + slot);
			throw new IllegalArgumentException();
			
		}
		
	}

	BiologicalNetwork(ArrayList<ArrayList<BioNeuron>> a, int pInputsNumber, int pOutputsNumber, float pAdjustPrecision) {
		
		network = a;
		nbInputs = pInputsNumber;
		nbOutputs = pOutputsNumber;
		adjustPrecision = pAdjustPrecision;
		
	}
	
	
	// --------------------------------------------- Simulation  ---------------------------------------------

	
	 /**
    * Simulates the network. Use getAnswer(ArrayList<Float> a) to get the network's answer.
    *
    * @param inputs: the inputs
    *
    * @return An ArrayList of the activations of each neuron
    */
	@Override
	public float[] simulate(float[] inputs) {
		
		ArrayList<float[]> outputs = new ArrayList<float[]>();
		outputs.add(inputs);
		
		//Genere les outputs de chaque couche
		for(ArrayList<BioNeuron> layer : network)
			outputs.add(simulateLayer(layer, outputs));
		
		//Renvoie le resultat de la derniere couche
		return outputs.get(outputs.size()-1);
	}

	private static float[] simulateLayer(ArrayList<BioNeuron> line, ArrayList<float[]> inputs) {

		float[] output = new float[line.size()];

		for(int i = 0; i < line.size(); i++)
			output[i] = line.get(i).simulate(inputs);

		return output;
	}

	 /**
    * Determines what is the index of the biggest number of the array
    *
    * @param inputs: the inputs
    *
    * @return The answer of the network
    */
	public static int getAnswer(float[] f) {

		return getBiggestIndex(f);
	}

	public int getInputsNumber() {
		return nbInputs;
	}
	
	public int getOutputsNumber() {
		return nbOutputs;
	}
	
	
	// -------------------------------------------- Apprentissage -------------------------------------------

	
	public BiologicalNetwork createChild(int nbreModifs) {
		
		int nbreInputs = nbInputs;
		int nbreOutputs = nbOutputs;
		float aP = adjustPrecision;
		
		BiologicalNetwork n = new BiologicalNetwork(copy(network), nbreInputs, nbreOutputs, aP);
		
		int inputsNumber = nbInputs;
		
		for(ArrayList<BioNeuron> couche : n.network) {
			
			for(BioNeuron neuron : couche) {
				
				int toRemove = 2 * (int) Math.round(nbreModifs * 0.1);
				
				neuron.addConnections(nbreModifs, adjustPrecision/inputsNumber, n.network, nbInputs);
				neuron.removeConnections(random(0, (toRemove == 0) ? 1 : toRemove));
				
			}
				
			inputsNumber = couche.size();
			
		}
		
		return n;
	}
	
	public TestResult test(ArrayList<Example> testExamples) {

		int errorsNumber = 0;
		
		for (Example e : testExamples) {
			
			// Simulation du reseau et obtention de sa réponse
			float[] answer = simulate(e.inputs());

			if (getAnswer(answer) != e.label())
				errorsNumber++;

		}
		
		return new TestResult(testExamples.size(), errorsNumber);		
	}
	
	
	// -------------------------------------------- Creation -------------------------------------------
	
	
	private static ArrayList<ArrayList<BioNeuron>> randomNetwork(int[] nbNeurones) {

		ArrayList<ArrayList<BioNeuron>> ret = new ArrayList<ArrayList<BioNeuron>>();
		
		for (int i = 1; i < nbNeurones.length; i++) {

			ArrayList<BioNeuron> couche = new ArrayList<BioNeuron>();

			for (int y = 0; y < nbNeurones[i]; y++)
				couche.add(randomNeuron(i, nbNeurones[i - 1], weightMult/nbNeurones[i - 1]));
			
			ret.add(couche);

		}

		return ret;
	}
	
	private static BioNeuron randomNeuron(int layerIndex, int inputsNumber, float weightMult) {
		
		ArrayList<BioConnection> connections = new ArrayList<BioConnection>();
		int connectionsNumber = random((int) (inputsNumber/3), (int) (2*inputsNumber/3));
		
		for (int i = 0; i < connectionsNumber; i++)
			connections.add(new BioConnection(layerIndex-1, random(0, inputsNumber-1), (float) ((Math.random()*1.5 -0.5) * weightMult)));

		return new BioNeuron(connections, 0.0f, layerIndex);
	}
	
	
	// --------------------------------------------- Memory --------------------------------------------
	
	
	public void save(int slot) {

		ArrayList<Float> otherInfos = new ArrayList<Float>();
		otherInfos.add(adjustPrecision);
		
		(new Save(network, nbInputs, nbOutputs, otherInfos)).write(slot, Network.BIOLOGICAL);
	}
	
	public static boolean exists(int slot) {
		return Save.exists(slot, Network.BIOLOGICAL);
	}
	
	
	// --------------------------------------------- Other ---------------------------------------------
	
	
	@SuppressWarnings("unused")
	private static void disp(int[] i) {
		
		for(int y : i)
			System.out.print(y + " ");
		
		System.out.println();
		
	}
	
	@SuppressWarnings("unused")
	private static void disp(float[] i) {
		
		for(float y : i)
			System.out.print(y + " ");
		
		System.out.println();
		
	}
	
	public BioNeuron getNeuron(int couche, int index) {
		return network.get(couche).get(index);
	}

	public void display() {

		System.out.println("Nombre de couches: " + network.size());
		System.out.println("Nombre d'inputs: " + nbInputs);
		
		for (int i = 0; i < network.size(); i++)
			System.out.println("Neurones sur la couche " + i + ": " + network.get(i).size());

	}
	
	private static int getBiggestIndex(float[] f) {
		
		float maxValue = 0;
		int maxIndex = 0;

		for (int i = 0; i < f.length; i++)
			if (maxValue < f[i]) {
				maxValue = f[i];
				maxIndex = i;
			}

		return maxIndex;	
	}
	
	private static ArrayList<ArrayList<BioNeuron>> copy(ArrayList<ArrayList<BioNeuron>> a) {
		
		ArrayList<ArrayList<BioNeuron>> ret = new ArrayList<ArrayList<BioNeuron>>();
		
		for(ArrayList<BioNeuron> couche : a) {
			
			ArrayList<BioNeuron> newCouche = new ArrayList<BioNeuron>();
			
			for(BioNeuron neuron : couche)
				newCouche.add(neuron.copy());
			
			ret.add(newCouche);
			
		}
		
		return ret;
	}
	
	public static int random(int min, int max) {
		
		return (int) (Math.round(Math.random() * (max-min)) + min);
	}
	
}

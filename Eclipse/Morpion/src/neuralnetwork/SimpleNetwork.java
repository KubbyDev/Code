package neuralnetwork;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleNetwork extends Network {
	
	private ArrayList<Neuron> network;
	private int nbInputs;
	private int nbOutputs;
	
	public float adjustPrecision;
	
	
	// -------------------------------------------- Constructeurs --------------------------------------------
	
	
	 /**
     * Example: (784,10) will take 784 inputs and give 10 different possible outputs
     *
     * @param nbInputs: The number of inputs of the network
     * @param nbOutputs: The number of outputs of the network
     *
     */
	public SimpleNetwork(int inputNumber, int outputNumber) {
				
		if(inputNumber > 0 && outputNumber > 0) {
			network = randomNetwork(inputNumber, outputNumber);
			nbInputs = inputNumber;
			nbOutputs = outputNumber;
		}
		else {
			System.out.println("The number of inputs and the number of outputs must be positive numbers (!= 0). You entered: " + nbInputs + " " + nbOutputs);
			throw new IllegalArgumentException();	
		}
		
		adjustPrecision = 0.00001f;
	
	}

	 /**
     * Reads the network at the given slot on the networks folder. To change the path of this folder, please change the public static String path of this class
     *
     * @param s
     *
     */
	@SuppressWarnings("unchecked")
	public SimpleNetwork(int slot) {
		
		if(Save.exists(slot, Network.SIMPLE)) {
			
			Save s = Save.read(slot, Network.SIMPLE);
			network = (ArrayList<Neuron>) s.network();
			nbInputs = s.nbInputs();
			nbOutputs = s.nbOutputs();
			adjustPrecision = (float) s.otherInfos().get(0);
			
		}
		else {
			
			System.out.println("No network saved on slot " + Network.SIMPLE + "/" + slot);
			throw new IllegalArgumentException();
			
		}
		
	}

	SimpleNetwork(ArrayList<Neuron> a, int pInputsNumber, int pOutputsNumber, float pAdjustPrecision) {
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
	public float[] simulate(float[] inputs) {

		float[] a = new float[nbInputs];

		for(int i = 0; i < network.size(); i++)
			a[i] = network.get(i).simulate(inputs);

		return a;
	}

	 /**
    * Determines what is the index of the biggest number of the array
    *
    * @param inputs: the inputs
    *
    * @return The answer of the network
    */
	public static int getAnswer(float[] f) {

		float maxValue = 0;
		int maxIndex = 0;

		for (int i = 0; i < f.length; i++)
			if (maxValue < f[i]) {
				maxValue = f[i];
				maxIndex = i;
			}

		return maxIndex;
	}

	public int getInputsNumber() {
		return nbInputs;
	}
	
	public int getOutputsNumber() {
		return nbOutputs;
	}
	
	
	// -------------------------------------------- Apprentissage -------------------------------------------

	
	public void modify(float[] inputs, float[] diff) {

		// Pour chaque neurone
		for(int i = 0; i < network.size(); i++) {
			
			//Si il y a des ajustements a faire
			if(diff[i] != 0.0f) {
			
				Neuron n = network.get(i);

				//Pour chaque weight
				for (int y = 0; y < n.weights().length; y++) {

					// L'ajustement est:
					// exitation du neurone concerné par le weight en cours de traitement
					// * Signe de l'ajustement a faire: signe de la somme des ajustements des neurones précédents (donc -1 ou 1)
					// * adjustPrecision (this depends on the performances of the network)
					float adjust = inputs[y] * diff[i] * adjustPrecision;

					n.weights()[y] += adjust;
					
				}
				
			}

		}
				
	}
	
	public void learn(float[] input, int label) {

		// Generation des outputs
		float[] output = simulate(input);
		
		// Generation de la liste des ajustements a faire
		float[] diff = getDiff(output, label);

		modify(input, diff);
		
	}

	public void train(ArrayList<Example> trainExamples, long timeMS) {
		
		train(trainExamples, timeMS, false);
	}
	
	public void train(ArrayList<Example> trainExamples, long timeMS, boolean drawDebug) {

		long time = System.currentTimeMillis();
		int index = 0;
		int nbreTrains = 0;

		if (drawDebug)
			System.out.println("Training...");

		while (System.currentTimeMillis() - time < timeMS) {

			index = (int) (Math.random() * trainExamples.size());

			learn(trainExamples.get(index).inputs(), trainExamples.get(index).label());

			nbreTrains++;
			
		}
		
		if (drawDebug) System.out.println("\nTraining ended !\nTraining Time: " + (timeMS/1000)
										+ "s - Number of examples seen: " + nbreTrains);

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
	
	public static float[] getDiff(float[] f, int label) {

		float[] a = new float[f.length];

		for (int i = 0; i < f.length; i++)
			a[i] = (float) (i == label ? 1 : 0) - f[i];

		return a;
	}
	
	
	// -------------------------------------------- Creation -------------------------------------------

	
	private static ArrayList<Neuron> randomNetwork(int inputs, int outputs) {

		ArrayList<Neuron> ret = new ArrayList<Neuron>();
		
		float weightMult = 100.0f/inputs;
		
		for(int i = 0; i < outputs; i++)
			ret.add(randomNeuron(inputs, weightMult));

		return ret;
	}

	private static Neuron randomNeuron(int inputsNumber, float weightMult) {
		
		float[] weights = new float[inputsNumber];

		for (int i = 0; i < inputsNumber; i++)
			weights[i] = (float) ((Math.random() + -0.5) * weightMult);

		// return new Neuron(weights, (Math.random() + biaisAdd) * biaisMult);
		return new Neuron(weights, -0.5f);
	}

	public SimpleNetwork invert() {
		
		ArrayList<Neuron> ret = new ArrayList<Neuron>();
		float mult = (float) nbInputs / (float) nbOutputs;
		
		float[] weights = new float[nbOutputs];
		float baseWeight = 1.0f/nbInputs;
		Arrays.fill(weights, baseWeight);
		
		//Creates a list of (old inputs number) neurons. Each neuron has (old outputs number) weigths
		for(int i = 0; i < nbInputs; i++)
			ret.add(new Neuron(weights, -0.5f));
		
		//For each neuron of the new network
		for(int i = 0; i < ret.size(); i++) {
			
			Neuron n = ret.get(i);
			
			//For each weight
			for(int y = 0; y < nbOutputs; y++) {
				
				n.inputsWeights[y] *= mult;
				
			}
		
		}
			
		return new SimpleNetwork(ret, nbOutputs, nbInputs, adjustPrecision);		
	
	}	
	
	public DeepNetwork toDeepNetwork() {
		
		ArrayList<ArrayList<Neuron>> newNetwork = new ArrayList<ArrayList<Neuron>>();
		newNetwork.add(network);
		
		return new DeepNetwork(newNetwork, nbInputs, nbInputs, adjustPrecision);		
	}
	
	
	// --------------------------------------------- Memory --------------------------------------------
	
	
	public void save(int slot) {

		ArrayList<Float> otherInfos = new ArrayList<Float>();
		otherInfos.add(adjustPrecision);
		
		(new Save(network, nbInputs, nbOutputs, otherInfos)).write(slot, Network.SIMPLE);
	}
	
	public static boolean exists(int slot) {
		return Save.exists(slot, Network.SIMPLE);
	}

	
}

package neuralnetwork;

import java.util.ArrayList;

import neuralnetwork.Neuron;

public class DeepNetwork extends Network {
	
	private ArrayList<ArrayList<Neuron>> network;
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
	public DeepNetwork(int[] neuronsPerLayer) {
		
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
	public DeepNetwork(int slot) {
		
		if(Save.exists(slot, Network.DEEP)) {
			
			Save s = Save.read(slot, Network.DEEP);
			network = (ArrayList<ArrayList<Neuron>>) s.network();
			nbInputs = s.nbInputs();
			nbOutputs = s.nbOutputs();
			adjustPrecision = (float) s.otherInfos().get(0);
			
		}
		else {
			
			System.out.println("No network saved on slot " + Network.DEEP + "/" + slot);
			throw new IllegalArgumentException();
			
		}
		
	}

	DeepNetwork(ArrayList<ArrayList<Neuron>> a, int pInputsNumber, int pOutputsNumber, float pAdjustPrecision) {
		
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

		float[] output = inputs;
		
		for(ArrayList<Neuron> layer : network)
			output = simulateLayer(layer, output);
		
		return output;
	}

	private static float[] simulateLayer(ArrayList<Neuron> line, float[] inputs) {

		float[] output = new float[line.size()];

		for (int i = 0; i < line.size(); i++)
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

	
	public void train(ArrayList<Example> trainExamples, long timeMS) {
		
		train(trainExamples, timeMS, false);
	}
	
	public void train(ArrayList<Example> trainExamples, long timeMS, boolean drawDebug) {

		long time = System.currentTimeMillis();
		int nbreGenerations = 0;

		int nbUnsuccess = 0;
		
		if (drawDebug)
			System.out.print("\nTraining...");

		ArrayList<SimulationUnit> childs = new ArrayList<SimulationUnit>();
		
		//Creation of the Simulation Units
		for(int i = 0; i < 128; i++) {
			
			SimulationUnit su = new SimulationUnit();
			childs.add(su);
			
		}	
		
		//Creation of the results array
		float[] results = new float[childs.size()+1];
		results[0] = this.test(trainExamples).errorRate;
		
		
		// TRAINING LOOP
		while (System.currentTimeMillis() - time < timeMS) {
			
			if(drawDebug)
				System.out.println("\n\nSimulation...");
			
			//Stats all the Units
			for(SimulationUnit su : childs)
				su.start(trainExamples);
			
			boolean ended = false;
			
			//Waits for the simulations to end
			while(ended == false) {
				
				ended = true;
				
				for(SimulationUnit su : childs)
					if(su.ended == false) {
						ended = false;
					}
				
			}
			
			//Gets the simulations results
			for(int i = 0; i < childs.size(); i++)
				results[i+1] = childs.get(i).errorRate;
			
			if(drawDebug)
				disp(results);
			
			//Gets all the needed informations on the simulation results
			int[] simulationResults = selectNetwork(results);
			
			//Selects the best unit
			int bestNetwork = simulationResults[0];
			
			if(drawDebug)
				System.out.print("Best network: " + bestNetwork);
			
			//If a unit has a better result than the current network, it takes its place
			if(bestNetwork != 0) {
				
				network = childs.get(bestNetwork-1).dn.network;
				results[0] = results[bestNetwork];
				
			}
			
			if(drawDebug && bestNetwork != 0)
				System.out.print(" - New = " + bestNetwork);
			
			//If no unit can do better than the current network 10 times in a row, the changes become smaller
			if(simulationResults[2] == 1) {
				
				nbUnsuccess++;
				
				if(nbUnsuccess == 10) {
					
					nbUnsuccess = 0;
					adjustPrecision /= 1.5;
					
					if(drawDebug)
						System.out.print("\n\nNo progress ! New adjust precision: " + adjustPrecision);
					
				}
				
			}
			else
				nbUnsuccess = 0;
			
			//If more than 10% of the units have the same score (and if it is the best score), the changes become bigger
			if(simulationResults[1] == 1) {
				
				adjustPrecision *= 1.5;
				
				if(drawDebug)
					System.out.print("\n\nSame scores ! New adjust precision: " + adjustPrecision);
				
			}
			
			nbreGenerations++;
			
		}
		
		if (drawDebug) System.out.println("\n\nTraining ended !\nTraining Time: " + (timeMS/1000)
										+ "s - Number of generations: " + nbreGenerations);

	}	
	
	private static int[] selectNetwork(float[] f) {
		
		ArrayList<Integer> a = new ArrayList<Integer>();
		
		float minValue = 100.0f;

		//Max value determination
		for (int i = 0; i < f.length; i++)
			if (minValue > f[i])
				minValue = f[i];
		
		//Networks selection
		for (int i = 0; i < f.length; i++)
			if (minValue == f[i])
				a.add(i);
		
		int sameScore = 0;
		int noProgress = 0;
		
		if(((float) a.size())/((float) f.length) > 0.5f)
			sameScore = 1;
		
		if(minValue == f[0])
			noProgress = 1;
		
		int[] ret = {a.get( (int) Math.round(Math.random() * (a.size()-1)) ), sameScore, noProgress};
		
		return ret;	
	}
	
	public DeepNetwork createChild() {
		
		int nbreInputs = nbInputs;
		int nbreOutputs = nbInputs;
		float aP = nbInputs;
		
		DeepNetwork dn = new DeepNetwork(copy(network), nbreInputs, nbreOutputs, aP);
		
		int inputsNumber = nbInputs;
		
		for(ArrayList<Neuron> couche : dn.network) {
			
			for(Neuron neuron : couche)
				neuron.modifyWeights(adjustPrecision/inputsNumber, 0.005f);
			
			inputsNumber = couche.size();
			
		}
		
		return dn;
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
	
	private class SimulationUnit {
		
		private Thread t;
		public DeepNetwork dn;
		public float errorRate;
		public boolean ended;
		
		public SimulationUnit() {			
		}
		
		public void start(ArrayList<Example> testSet) {
			
			t = new Thread(new Runnable() {
				
				@Override
				public void run() {

					dn = createChild();
					
					errorRate = dn.test(testSet).errorRate;
					ended = true;
					
				}
			
			}); 
			
			ended = false;
			t.start();
		
		}
		
	}
	
	
	// -------------------------------------------- Creation -------------------------------------------


	private static ArrayList<ArrayList<Neuron>> randomNetwork(int[] nbNeurones) {

		ArrayList<ArrayList<Neuron>> ret = new ArrayList<ArrayList<Neuron>>();
		
		for (int i = 1; i < nbNeurones.length; i++) {

			ArrayList<Neuron> couche = new ArrayList<Neuron>();

			for (int y = 0; y < nbNeurones[i]; y++)
				couche.add(randomNeuron(nbNeurones[i - 1], 1.0f/(nbNeurones[i - 1]*nbNeurones[i - 1])));
			
			ret.add(couche);

		}

		return ret;
	}
	
	private static Neuron randomNeuron(int inputsNumber, float weightMult) {
		
		float[] weights = new float[inputsNumber];

		for (int i = 0; i < inputsNumber; i++)
			weights[i] = (float) (Math.random() * weightMult);

		// return new Neuron(weights, (Math.random() + biaisAdd) * biaisMult);
		return new Neuron(weights, 0.0f);
	}

	//TODO
	public DeepNetwork invert() {
		
		/*
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
			
		return new DeepNetwork(ret, nbOutputs, nbInputs, adjustPrecision);		
		*/
		
		return this;
	}	
		
	public SimpleNetwork toSimpleNetwork() {
		
		if(network.size() == 1)
			return new SimpleNetwork(network.get(0), nbInputs, nbInputs, adjustPrecision);		
		else
			System.out.println("The network must contain only one layer to be converted to a simple network !\nThis network contains " + network.size() + "layers");
		
		return null;
	}
	
	
	// --------------------------------------------- Memory --------------------------------------------
	
	
	public void save(int slot) {

		ArrayList<Float> otherInfos = new ArrayList<Float>();
		otherInfos.add(adjustPrecision);
		
		(new Save(network, nbInputs, nbOutputs, otherInfos)).write(slot, Network.DEEP);
	}
	
	public static boolean exists(int slot) {
		return Save.exists(slot, Network.DEEP);
	}
	
	
	// --------------------------------------------- Other ---------------------------------------------
	
	
	private static void disp(int[] i) {
		
		for(int y : i)
			System.out.print(y + " ");
		
		System.out.println();
		
	}
	
	private static void disp(float[] i) {
		
		for(float y : i)
			System.out.print(y + " ");
		
		System.out.println();
		
	}
	
	public Neuron getNeuron(int couche, int index) {
		return network.get(couche).get(index);
	}

	public void display() {

		System.out.println("Nombre de couches: " + network.size());

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
	
	private static ArrayList<ArrayList<Neuron>> copy(ArrayList<ArrayList<Neuron>> a) {
		
		ArrayList<ArrayList<Neuron>> ret = new ArrayList<ArrayList<Neuron>>();
		
		for(ArrayList<Neuron> couche : a) {
			
			ArrayList<Neuron> newCouche = new ArrayList<Neuron>();
			
			for(Neuron neuron : couche)
				newCouche.add(neuron.copy());
			
			ret.add(newCouche);
			
		}
		
		return ret;
	}
	
	
}

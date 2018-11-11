package reseau;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Reseau implements Serializable {

	private static final long serialVersionUID = 1294705106808047063L;
	public static String path = "F:\\JavaCode\\NeuralNetwork\\reseaux\\v1\\";
	
	protected ArrayList<Neuron> reseau;
	protected static int inputsNumber;

	// Initialisation des variables pour les reseaux random
	private static float weightAdd = -0.5f;
	private static float weightMult = 2.0f / 784.0f;
	
	public float adjustPrecision;

	// -------------------------------------------- Constructeurs --------------------------------------------
	
	
	 /**
     * Example: (784,10) will take 784 inputs and give 10 different possible outputs
     *
     * @param nbInputs: The number of inputs of the network
     * @param nbOutputs: The number of outputs of the network
     *
     */
	public Reseau(int nbInputs, int nbOutputs) {
				
		if(nbInputs > 0 && nbOutputs > 0) {
			reseau = randomNetwork(nbInputs, nbOutputs);
			inputsNumber = nbInputs;
		}
		else {
			System.out.println("The number of inputs and the number of outputs must be positive numbers (!= 0). You entered: " + nbInputs + " " + nbOutputs);
			throw new IllegalArgumentException();	
		}
		
		adjustPrecision = 0.0001f;
	
	}

	 /**
     * Reads the network at the given slot on the networks folder. To change the path of this folder, please change the public static String path of this class
     *
     * @param s
     *
     */
	public Reseau(int slot) {
		
		if(exists(slot))
			reseau = readNetwork(slot);
		else {
			System.out.println("No network saved on slot " + slot);
			throw new IllegalArgumentException();
		}
		
	}

	
	// --------------------------------------------- Simulation  ---------------------------------------------

	
	 /**
     * Simulates the network. Use getAnswer(ArrayList<Float> a) to get the network's answer.
     *
     * @param inputs: the inputs
     *
     * @return An ArrayList of the activations of each neuron
     */
	public ArrayList<Float> simulate(ArrayList<Float> inputs) {

		ArrayList<Float> a = new ArrayList<Float>();

		for(Neuron n : reseau)
			a.add(n.simulate(inputs));

		return a;
	}

	 /**
     * Determines what is the index of the biggest number of the list
     *
     * @param inputs: the inputs
     *
     * @return The answer of the network
     */
	public static int getAnswer(ArrayList<Float> a) {

		float maxValue = 0;
		int maxIndex = 0;

		for (int i = 0; i < a.size(); i++)
			if (maxValue < a.get(i)) {
				maxValue = a.get(i);
				maxIndex = i;
			}

		return maxIndex;
	}


	// -------------------------------------------- Apprentissage -------------------------------------------

	
	public void modify(ArrayList<Float> inputs, ArrayList<Float> diff) {

		// Pour chaque neurone
		for(int i = 0; i < reseau.size(); i++) {
			
			//Si il y a des ajustements a faire
			if(diff.get(i) != 0.0f) {
			
				Neuron n = reseau.get(i);

				//Pour chaque weight
				for (int y = 0; y < n.weights().size(); y++) {

					// L'ajustement est:
					// exitation du neurone concerné par le weight en cours de traitement
					// * Signe de l'ajustement a faire: signe de la somme des ajustements des neurones précédents (donc -1 ou 1)
					// * adjustPrecision (this depends on the performances of the network)
					float adjust = inputs.get(y) * diff.get(i) * adjustPrecision;

					n.weights().set(y, n.weights().get(y) + adjust);
					
				}
				
			}

		}
				
	}
	
	public void learn(ArrayList<Float> input, int label) {

		// Generation des outputs
		ArrayList<Float> output = simulate(input);
		
		// Generation de la liste des ajustements a faire
		ArrayList<Float> diff = getDiff(output, label);

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
			ArrayList<Float> answer = simulate(e.inputs());

			if (Reseau.getAnswer(answer) != e.label())
				errorsNumber++;

		}
		
		return new TestResult(testExamples.size(), errorsNumber);		
	}
	
	public static ArrayList<Float> getDiff(ArrayList<Float> al, int label) {

		ArrayList<Float> a = new ArrayList<Float>();

		for (int i = 0; i < al.size(); i++)
			a.add((float) (i == label ? 1 : 0) - al.get(i));

		return a;
	}
	
	
	// -------------------------------------------- Creation -------------------------------------------

	
	private static ArrayList<Neuron> randomNetwork(int nbInputs, int nbOutputs) {

		ArrayList<Neuron> ret = new ArrayList<Neuron>();
		
		for(int i = 0; i < nbOutputs; i++)
			ret.add(randomNeuron(nbInputs));

		return ret;
	}

	private static Neuron randomNeuron(int inputsNumber) {

		ArrayList<Float> weights = new ArrayList<Float>();

		for (int i = 0; i < inputsNumber; i++)
			weights.add((float) ((Math.random() + weightAdd) * weightMult));

		// return new Neuron(weights, (Math.random() + biaisAdd) * biaisMult);
		return new Neuron(weights, -0.5f);
	}

	
	// -------------------------------------------- Memoire -------------------------------------------

 	
	public void save(int slot) {

		File f = new File(path + slot);

		f.delete();

		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {

			oos.writeObject(reseau);

			oos.close();

		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}

	}

	public static boolean exists(int slot) {
		return (new File(path + slot)).exists();
	}

	@SuppressWarnings("unchecked")
	protected static ArrayList<Neuron> readNetwork(int slot) {

		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
				new FileInputStream(new File(path + slot))))) {

			try {
				return (ArrayList<Neuron>) ois.readObject();
			} catch (ClassNotFoundException e) {e.printStackTrace();}

			ois.close();

		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {}

		System.out.println("Nothing found at slot " + slot);
		return null;
	}
	

	// -------------------------------------------- Autres -------------------------------------------

	
	public Neuron getNeuron(int index) {
		return reseau.get(index);
	}

	public void display() {

		System.out.println("Nombre d'inputs: " + inputsNumber);
		System.out.println("Nombre d'outputs: " + reseau.size());
		
	}

	
}

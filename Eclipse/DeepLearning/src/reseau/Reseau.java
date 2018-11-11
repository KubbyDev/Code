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

import digitsreading.Main;

public class Reseau implements Serializable {

	public static boolean debug = true;
	public static String path = "F:\\JavaCode\\NeuralNetwork\\reseaux\\";
	
	protected ArrayList<ArrayList<Neuron>> reseau;
	protected static int inputsNumber;

	// Initialisation des variables pour les reseaux random
	private static float weightAdd = -0.5f;
	private static float weightMult = 2.0f / 784.0f;

	// -------------------------------------------- Constructeurs --------------------------------------------
	
	// Par couches: exemple int[] i = {784,10}
	public Reseau(int[] nbNeurones) {
		
		boolean validArg = true;
		
		if(nbNeurones.length == 2) {
			for (int i = 0; i < 2; i++)
				if(nbNeurones[i] < 1)
					validArg = false;
		}
		else
			validArg = false;
		
		if(validArg) {
			reseau = randomNetwork(nbNeurones);
			inputsNumber = nbNeurones[0];
		}
		else {
			System.out.print("The input must contain 2 numbers: the inputs number and the outputs number.\nThey are only positive numbers (!= 0). You entered: ");
			disp(nbNeurones);
			System.out.println();
			throw new IllegalArgumentException();	
		}
	
	}

	// Par slot en memoire
	public Reseau(int slot) {
		
		if(exists(slot))
			reseau = readNetwork(slot);
		else {
			System.out.println("No network saved on slot " + slot);
			throw new IllegalArgumentException();
		}
		
	}

	// --------------------------------------------- Simulation  ---------------------------------------------
	
	// -------------------------------------------- Simulation -------------------------------------------

	public ArrayList<Float> simulate(ArrayList<Float> inputs) {

		return simulateLine(reseau.get(0), inputs);
	}

	private static ArrayList<Float> simulateLine(ArrayList<Neuron> line, ArrayList<Float> inputs) {

		ArrayList<Float> a = new ArrayList<Float>();

		for (Neuron n : line)
			a.add(n.simulate(inputs));

		return a;
	}

	public static ArrayList<Float> getDiff(ArrayList<Float> al, int label) {

		ArrayList<Float> a = new ArrayList<Float>();

		for (int i = 0; i < al.size(); i++)
			a.add((float) (i == label ? 1 : 0) - al.get(i));

		return a;
	}

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

	public void modifyNetwork(ArrayList<ArrayList<Float>> inputs, ArrayList<Float> diff) {
		
		if(debug) Main.disp(diff);
		if(debug) System.out.print("reseau.size() = " + reseau.size() + " ");

		// Pour chaque couche du reseau en partant de la fin
		for (int i = reseau.size() - 1; i >= 0; i--) {

			if(debug) System.out.println("couche: " + i);

			// Si il y a d'autres couches a traiter
			// Cette liste enregistre les ajustements faits sur cette couche
			ArrayList<Float> newDiff = new ArrayList<Float>();
			if (i > 0)
				for (int y = 0; y < inputs.get(i).size(); y++)
					newDiff.add(0.0f);

			if(debug) System.out.println("newDiff.size(): " + newDiff.size());

			// Pour chaque neurone de la couche
			for (int u = 0; u < reseau.get(i).size(); u++) {
				
				//Si il y a des ajustements a faire
				//if(diff.get(u) != 0.0f) {
				
					// System.out.println("Neurone: " + u);
					Neuron n = reseau.get(i).get(u);
	
					for (int y = 0; y < n.weights().size(); y++) {
	
						// L'ajustement est:
						// exitation du neurone concerné par le weight en cours de traitement
						// * Signe de l'ajustement a faire: signe de la somme des ajustements des neurones précédents (donc -1 ou 1)
						// * 0.01
						float adjust = (inputs.get(i).get(y) + 0.1f) * diff.get(u) * 0.01f;
	
						if(debug) if (u == 0 && y < 100) System.out.print("Adjust on weight " + y + " = " + inputs.get(i).get(y) + " + 0.1f) * " + diff.get(u) + " * " + 0.01f);
	
						n.weights().set(y, n.weights().get(y) + adjust);
						if (i > 0) newDiff.set(y, newDiff.get(y) + adjust);
	
						if(debug) if (i == 0 && u == 0 && y < 100) System.out.println(" = " + adjust);
						
						if(debug) if (i == 1 && u == 0) System.out.println("newDiff.get(y) " + newDiff.get(y));
						
					}
					
				//}

			}

			diff = new ArrayList<Float>();

			// Si il y a d'autres couches a traiter
			// Les ajustements qui viennent d'être faits deviennent parametre des ajustements a faire (on garde juste le signe)
			if (i > 0)
				for (int y = 0; y < newDiff.size(); y++) {
					diff.add(newDiff.get(y) > 0.0f ? 1.0f : (newDiff.get(y) < -0.0f ? -1.0f : 0.0f));
					if(debug) System.out.println("y= " + y + " newDiff.size()= " + newDiff.size() + " newDiff.get(y)= " + newDiff.get(y) + " traitement: " + ((newDiff.get(y) > 0.0f) ? 1.0f : ((newDiff.get(y) < -0.0f) ? -1.0f : 0.0f)));
				}

		}
				
	}
	
	public void learn(ArrayList<Float> input, int label) {

		if(debug) System.out.println("\nLearn");

		// ArrayList contenant tous les inputs de chaque couche
		ArrayList<ArrayList<Float>> inputs = new ArrayList<ArrayList<Float>>();

		// Generation des inputs de chaque couche + output de la derniere
		ArrayList<Float> output = input;
		for (int i = 0; i < reseau.size(); i++) {

			inputs.add(output);
			output = simulateLine(reseau.get(i), output);

		}
		
		inputs.add(output);
		
		// Generation de la liste des ajustements a faire
		ArrayList<Float> diff = getDiff(output, label);

		modifyNetwork(inputs, diff);
		
	}
	
	public void train(ArrayList<Example> trainExamples, ArrayList<Example> testExamples, long timeMS) {

		train(trainExamples, testExamples, timeMS, true);

	}

	public void train(ArrayList<Example> trainExamples, ArrayList<Example> testExamples, long timeMS, boolean drawDebug) {

		long time = System.currentTimeMillis();

		int nbreTrains = 0;
		int nbreErreurs = 0;
		int index = 0;

		if (drawDebug)
			System.out.println("Train Set");

		while (System.currentTimeMillis() - time < timeMS) {

			index = (int) (Math.random() * trainExamples.size());

			learn(trainExamples.get(index).inputs(), trainExamples.get(index).label());

			nbreTrains++;
			
		}

		if (drawDebug)
			System.out.println("Test Set");

		for (Example e : testExamples) {

			// Simulation du reseau et obtention de sa réponse
			ArrayList<Float> answer = simulate(e.inputs());

			if (Reseau.getAnswer(answer) != e.label())
				nbreErreurs++;

		}

		if (drawDebug)
			System.out.println("\nTemps d'apprentissage: " + (timeMS/1000)
					+ "s - Nombre d'Exemples Vus: " + nbreTrains 
					+ "\nTest Set Size: " + testExamples.size() 
					+ " - Erreurs: " + nbreErreurs
					+ " - Pourcentage d'Erreurs: " + (((float) nbreErreurs / (float) testExamples.size()) * 100)
					+ " %");
	}

	// -------------------------------------------- Creation -------------------------------------------

	private static ArrayList<ArrayList<Neuron>> randomNetwork(int[] nbNeurones) {

		ArrayList<ArrayList<Neuron>> ret = new ArrayList<ArrayList<Neuron>>();

		for (int i = 1; i < nbNeurones.length; i++) {

			ArrayList<Neuron> couche = new ArrayList<Neuron>();

			for (int y = 0; y < nbNeurones[i]; y++)
				couche.add(randomNeuron(nbNeurones[i - 1]));
			ret.add(couche);

		}

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

	private static String getPath() {
		
		return path;
	}
 	
	public void save(int slot) {

		File f = new File(getPath() + slot);

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
		return (new File(getPath() + slot)).exists();
	}

	@SuppressWarnings("unchecked")
	protected static ArrayList<ArrayList<Neuron>> readNetwork(int slot) {

		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
				new FileInputStream(new File(getPath() + slot))))) {

			try {
				return (ArrayList<ArrayList<Neuron>>) ois.readObject();
			} catch (ClassNotFoundException e) {e.printStackTrace();}

			ois.close();

		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {}

		System.out.println("Nothing found at slot " + slot);
		return null;
	}

	// -------------------------------------------- Autres -------------------------------------------

	public Neuron getNeuron(int couche, int index) {
		return reseau.get(couche).get(index);
	}

	public void display() {

		System.out.println("Nombre de couches: " + reseau.size());

		for (int i = 0; i < reseau.size(); i++)
			System.out.println("Neurones sur la couche " + i + ": " + reseau.get(i).size());

	}

	public void disp(int[] i) {
		for(int y : i)
			System.out.print(y + " ");
	} 
	
	
}

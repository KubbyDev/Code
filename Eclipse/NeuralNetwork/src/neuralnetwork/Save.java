package neuralnetwork;

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

public class Save implements Serializable {

	private static final long serialVersionUID = 5330040308999609454L;
	private ArrayList<?> network;
	private int nbInputs;
	private int nbOutputs;
	private ArrayList<?> otherInfos = null;
	
	public Save(ArrayList<?> pNetwork, int pInputNumber, int pOutputNumber) {
		
		network = pNetwork;
		nbInputs = pInputNumber;
		nbOutputs = pOutputNumber;
		
	}
	
	public Save(ArrayList<?> pNetwork, int pInputNumber, int pOutputNumber, ArrayList<?> pOtherInfos) {
		
		network = pNetwork;
		nbInputs = pInputNumber;
		nbOutputs = pOutputNumber;
		otherInfos = pOtherInfos;
		
	}
	
	public ArrayList<?> network() {
		return network;
	}
	
	public int nbInputs() {
		return nbInputs;
	}
	
	public int nbOutputs() {
		return nbOutputs;
	}
	
	public ArrayList<?> otherInfos() {
		return otherInfos;
	}
	
	public static Save read(int slot, String networkType) {

		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
				new FileInputStream(new File(Network.path + "\\" + networkType + "\\" + slot))))) {

			try {
				return (Save) ois.readObject();
			} catch (ClassNotFoundException e) {e.printStackTrace();}

			ois.close();

		} catch (FileNotFoundException e) {} catch (IOException e) {}

		System.out.println("Error while reading network at slot " + networkType + "/" + slot);
		return null;
	}
	
	public void write(int slot, String networkType) {

		File f = new File(Network.path + "\\" + networkType + "\\" + slot);

		f.delete();

		try {
			f.createNewFile();
		} catch (IOException e) {e.printStackTrace();}

		try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {

			oos.writeObject(this);

			oos.close();

		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}

	}
	
	public static boolean exists(int slot, String networkType) {
		return (new File(Network.path + "\\" + networkType + "\\" + slot)).exists();
	}
	
}

package digitsreading;

import java.util.ArrayList;

import neuralnetwork.Example;
import neuralnetwork.Network;
import neuralnetwork.SimpleNetwork;
import neuralnetwork.BiologicalNetwork;
import neuralnetwork.DeepNetwork;

public class Main {

	public static int slot = 4;
	public static ArrayList<Example> trainExamples = ReadData.trainExamples();	
	//public static ArrayList<Example> testExamples = ReadData.testExamples();	
	
	public static void main(String[] args) {
		
		BiologicalNetwork bn;
		
		int[] i = {784,50,50,10};
		
		if(BiologicalNetwork.exists(slot))
			bn = new BiologicalNetwork(slot);
		else
			bn = new BiologicalNetwork(i);
		
		bn.test(trainExamples).display();
		
		for(int y = 0; y < 10; y++) {
		
			//bn.train(trainExamples, 10000, 5);
			bn.test(trainExamples).display();
		
		}
		
        bn.save(slot);
		
	}
	
	private static ArrayList<Example> selectExamples(ArrayList<Example> examples, int nbreParLabel) {
		
		ArrayList<Example> ret = new ArrayList<Example>();
		
		//The Data base contains approximatly 6000 images of each label
		//For each label
		for(int i = 0; i < 10; i++) {
			
			//Adds 50 images of this label
			//for(int y = 0; y < 50; y++)
				//ret.add(examples.get(	(int) (Math.round( Math.random()*5999 ) + i*6000) 	));
			
			for(int y = 2000; y < 2000 + nbreParLabel; y++)
				ret.add(examples.get(	y + i*6000) 	);
			
		}
		
		return ret;
	}
	
	public static ArrayList<Float> getInputs(Image i) {
		
		ArrayList<Float> a = new ArrayList<Float>();
		short[] pixels = i.pixels();
		
		for(short pixel : pixels)
			a.add((float) (pixel/255));
		
		return a;
	}

	public static void disp(Image i) {
		
		int line = 0;
		
		for(short s : i.pixels()) {
			
			System.out.print((s > 50) ? "O" : ".");
			
			line++;
			if (line == 28) {
				System.out.println();
				line = 0;
			}
			
		}
			
	}
	
	public static void disp(ArrayList<Float> a) {
		
		for(float d : a) {
			if(round(d, 1) != 0) System.out.print(((d >= 0) ? "+" : "") + round(d, 1) + " ");
			else System.out.print(".... ");
		}
			
	}
	
	public static void disp(float[] a) {
		
		for(float d : a) {
			if(round(d, 1) != 0) System.out.print(((d >= 0) ? "+" : "") + round(d, 1) + " ");
			else System.out.print(".... ");
		}
			
	}
	
    private static float round(float number, int precision) {

        int power = (int) Math.pow(10, precision);
        number *= power;
        
        return ((float) Math.round(number)) / power;
    }
	
    public static Example randomExample(ArrayList<Example> e) {
    	return e.get((int) Math.round((e.size()-1) * Math.random()));
    }
    
}










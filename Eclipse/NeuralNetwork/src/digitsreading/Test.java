package digitsreading;

import java.util.ArrayList;

import neuralnetwork.BiologicalNetwork;
import neuralnetwork.DeepNetwork;
import neuralnetwork.Example;
import neuralnetwork.SimpleNetwork;

public class Test {
	
	public static void main(String[] args) {

		
		int[] y = {5,500,500,7};
		
		BiologicalNetwork bn = new BiologicalNetwork(2100);
		
		for(int i = 0; i < 7; i++)
			bn.getNeuron(2, i).display();
		
		
		
		BiologicalNetwork s = new BiologicalNetwork(2103);
			
		float[] inputs = generateMorpionInputs(true);
		
		disp(s.simulate(inputs));
		
	}
	
	public static void convert() {
		
		ReadData.convert("F:\\JavaCode\\NeuralNetwork\\", "train-images.idx3-ubyte", "train-labels.idx1-ubyte", "training\\");
		ReadData.convert("F:\\JavaCode\\NeuralNetwork\\", "t10k-images.idx3-ubyte", "t10k-labels.idx1-ubyte", "testing\\");
		
	}

	public static void dispSimple(float[] inputs) {
		
		int line = 0;
		
		for(float f : inputs) {
			
			System.out.print((f > 0.3) ? " O " : " . ");
			
			line++;
			if (line == 28) {
				System.out.println();
				line = 0;
			}
			
		}
		
	}
	
	public static void disp(float[] a) {
		
		int line = 0;
		
		for(float d : a) {
			
			if(round(d, 1) != 0) System.out.print(((d >= 0) ? "+" : "") + round(d, 1) + " ");
			else System.out.print(".... ");
			
			line++;
			
			if(line == 3)
			{
				line = 0;
				System.out.print("\n");
			}
			
		}
			
	}
	
	private static float round(float number, int precision) {

	    int power = (int) Math.pow(10, precision);
	    number *= power;
	        
	    return ((float) Math.round(number)) / power;
	}
	
	public static void disp(ArrayList<Float> a) {
		
		int line = 0;
		
		for(float f : a) {
			
			System.out.print((f > 0.3) ? "O" : ".");
			
			line++;
			if (line == 28) {
				System.out.println();
				line = 0;
			}
			
		}
		
	}
	
	public static float[] generateMorpionInputs(boolean display) {
		
		float[] f = new float[9];
		boolean b = true;
		
		for(int i = 0; i < 4; i++) {
			
			int play = (int) Math.round(Math.random()*8);
			
			while(f[play] != 0.0f)
				play = (int) Math.round(Math.random()*8);
		
			f[play] = (b) ? 1.0f : 2.0f;
			b = !b;
			
		}
		
		/*
		f[7] = 1.0f;
		f[4] = 1.0f;
		f[0] = -1.0f;
		f[1] = -1.0f;
		*/
		
		if(display)
			dispPlateauMorpion(f);
			
		float[] r = new float[19];
		
		for(int i = 0; i < 9; i++)
			r[i] = (f[i] == 1.0f) ? 1.0f : 0.0f;

		for(int i = 9; i < 18; i++)
			r[i] = (f[i-9] == 2.0f) ? 1.0f : 0.0f;
			
		r[18] = 1.0f;
		
		return r;
	}
	
	public static void dispPlateauMorpion(float[] plateau) {
		System.out.print("\n\n\n");
		
		int line = 0;
		
		for(float a : plateau)
		{
			int b = (int) a;
			
			if(b != 0)
			{
				if(b == 1)
				{
					System.out.print(" O ");
				}
				else
				{
					System.out.print(" X ");
				}
			}
			else
			{
				System.out.print(" . ");
			}
			
			line++;
			
			if(line == 3)
			{
				line = 0;
				System.out.print("\n");
			}
		}
		System.out.print("\n");
	}
	
}

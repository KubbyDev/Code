import java.util.ArrayList;
import java.util.Arrays;

public class Test {

	public static IA ia = new IA();
	public static IAv2 iav2 = new IAv2();
	public static IAv3 iav3 = new IAv3();
	public static int[][] pla = new int[7][6];
	
	public static void main(String[] args) {
		
		for(int i = 0; i < 7; i++)
			iav3.reseau.getNeuron(i).display();
		
	}
	
	public static void disp(ArrayList<Float> a) {
		for(float d : a)
			System.out.print(d + " ");
		
		System.out.println();
	}
	
	public static void disp(int[][] i) {
		
		for(int y = 5; y >= 0; y--) {
			for(int x = 0; x <= 6; x++) {
				
				int z = i[x][y];
				
				if(z == -1)
					System.out.print("O");
				
				if(z == 1)
					System.out.print("X");
				
				if(z == 0)
					System.out.print(".");
			}
			System.out.print("\n");
		}
		
	}
	
	public static void disp(int[] i) {

		for(int z : i) {
			System.out.print(z + " ");
		}
		
	}
	
	public static void displayFile(int i) {
		
		//O a placer = file pair
		//X a placer = file impair
		
		ArrayList<Situation> a = ia.readFile(ia.findFile(i));
		
		for(Situation sit : a) {
			
			System.out.println("Plateau:");
			disp(sit.getPlateau());
			System.out.println("Resolution:");
			disp(sit.getResolution());
			
			System.out.print("\n");
			
		}
		
	}
	
	public static void displayFiles() {
		
		for(int i = 0; i <= 41; i++) {		
			
			try {
				
			System.out.print("File " + i + ":\n"); 
			displayFile(i);
			System.out.print("\n");
			
			} catch (NullPointerException e) {}
		}
	
	}

}


public class GenerateButtonDeclarations {

	public static void main(String[] args) {
		
	}
	
	public static void generate() {
		
		for(int i = 1; i <= 35; i++) {
			
			System.out.println("			//Level " + i);
			
			for(int y = 0; y < Main.levels.get(i-1).nbreButtons; y++)
				System.out.println("			levels.get(" + (i-1) + ").addButton(new Button(new Vector(0, 0, 0), 0));");
			
			System.out.println("			levels.get(" + (i-1) + ").addButton(new Button(new Vector(0, 0, 0), 0, true));\n");			
		}
		
	}

}
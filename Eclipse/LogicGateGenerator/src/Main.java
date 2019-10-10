
public class Main {

	public static void main(String[] args) {
		
		boolean[] input1 = {true};
		boolean[] input2 = {false};
		
		//Creation d'une porte qui sort toujours true
		CustomGate alwaysT = new CustomGate("alwt", "or(i0,not(i0))");
		System.out.println("Simulation de AlwaysTrue: true: " + alwaysT.simulate(input1) + " | false: " + alwaysT.simulate(input2));
		
		//Creation d'une porte qui sort toujours le contraire de AlwaysT
		CustomGate alwaysF = new CustomGate("alwf", "not(alwt(i0))");
		alwaysF.addGateToMemory(alwaysT); //On ajoute AlwaysT a la memoire de AlwaysF pour qu'elle connaisse son circuit (plus pratique pour pouvoir la simuler)
		System.out.println("Simulation de AlwaysFalse: true: " + alwaysF.simulate(input1) + " | false: " + alwaysF.simulate(input2));
		
		//Creation d'une porte un peu plus complexe
		CustomGate multiplex = new CustomGate("mux", "or(and(i0,i2),and(i1,not(i2)))");
		boolean[] input3 = {false, true, false};
		System.out.println("Simulation de Multiplex: false, true, false -> " + multiplex.simulate(input3));
		
		//On peut generer la table de verites de cette porte
		System.out.println("Table de verites de Multiplex: ");
		multiplex.generateTruthTable();
		disp(multiplex.getTruthTable());
		
		//On peut generer une porte en donnant une table de verites
		boolean[][] truthT = {{false,false,false},{false,true,true},{true,false,true},{true,true,false}};
		CustomGate c = new CustomGate("c", truthT, 1, true); //Genere une porte qui correspond a cette truth table avec un temps max de 10 sec et en utilisant uniquement les 3 portes de base (not, and, or)
		System.out.println("\nTruth Table:");
		disp(truthT);
		System.out.println("\nCircuit correspondant: " + c.getCircuit());
		
		//On peut verifier l'equivalence de 2 circuits
		CustomGate x = new CustomGate("x", "xor(i0,i1)");
		x.generateTruthTable();
		System.out.println("Circuits equivalent a xor(i0,i1) ? : " + x.isCircuitEquivalent(c.getCircuit()));
		
		//Simplification
		String circuit = CustomGate.simplify("or(and(xnor(i1,and(i1,i2)),nand(not(xor(i1,i2)),i2)),or(and(i2,i3),not(xor(i1,and(i1,i0)))))", 60, false);
		CustomGate cus = new CustomGate("name", circuit);
		System.out.println(circuit);
		cus.generateTruthTable();
		disp(cus.getTruthTable());
	}	
	
	public static char toChar(boolean i) {
		if(i)
			return '1';
		else
			return '0';
	}
	
	public static void disp(boolean[][] t) {
		
		for(boolean[] line : t) {
			for(int x = 0; x < line.length; x++) {
				if(x == line.length-2)
					System.out.print(toChar(line[x]) + " -> ");
				if(x == line.length-1)
					System.out.print(toChar(line[x]));
				if(x < line.length-2)
					System.out.print(toChar(line[x]) + ", ");
			}
			System.out.print("\n");
		}
		
	}
	
}

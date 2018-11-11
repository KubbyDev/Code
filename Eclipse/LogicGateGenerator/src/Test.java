import java.util.ArrayList;

public class Test {

	public static CustomGate addO1 = new CustomGate("addO1", "xor(xor(i0,i1),i2)");
	public static CustomGate addO2 = new CustomGate("addO2", "or(and(i1,i2),and(i0,or(i1,i2)))");
	
	//xor(xor(i0,i1),i2)
	
	public static void main(String[] args) {

		String i1 = new String("0000011000001111");
		String i2 = new String("0000110010010110");

		String s = add(i1, i2);
		
		System.out.print(i1 + " = " + Tools.toDecimal(i1) + "\n+\n" + i2 + " = " + Tools.toDecimal(i2) + "\n=\n" + s + " = " + Tools.toDecimal(s));

		System.out.print("\n\n\n");
		
		boolean[] input = {false};
		
		//On crée une porte dont la sortie est toujours vraie (i0 représente le premier input (il n'y en a qu'un seul))
		CustomGate tjrsTrue = new CustomGate("tjrst", "or(i0,not(i0))");
		
		//On affiche le résultat de la simulation de cette porte
		System.out.println(tjrsTrue.simulate(input));
		
		//On crée une porte dont la sortie est toujours fausse
		CustomGate tjrsFalse = new CustomGate("tjrsf", "not(tjrst(i0))");
		
		//On ajoute la porte toujours vraie dans la liste des portes custom connues par cette nouvelle porte
		tjrsFalse.addGateToMemory(tjrsTrue);
		
		//On affiche le résultat de la simulation de cette porte
		System.out.println(tjrsFalse.simulate(input));
		
		//boolean[] input2 = {false, true, false};
		CustomGate multiplex = new CustomGate("mux", "or(and(i0,i2),and(i1,not(i2)))");
		
		System.out.print("\n\n\n");
		
		//CustomGate c = new CustomGate("t", "and(i0,i1)");
		multiplex.generateTruthTable();
		
		//System.out.print("\n\n\n");
		
		boolean[][] table1 = {{false,true,false,true},{false,true,true,false},{true,true,false,false},{true,true,true,true},{false,false,false,false},{false,false,true,true},{true,false,false,true},{true,false,true,false}};
		boolean[][] table2 = {{false,true,false,false},{false,true,true,true},{true,true,false,true},{true,true,true,true},{false,false,false,false},{false,false,true,false},{true,false,false,false},{true,false,true,true}};
		//disp(table);
		//System.out.print("\n" + CustomGate.isValidTruthTable(table));
		System.out.print("\n\n\n");
		disp(multiplex.getTruthTable());
		System.out.print("\n\n\n");
		
		//disp(CustomGate.orderTruthTable(table));
		
		CustomGate c = new CustomGate("cus", "mux(i0,i1,i2)");
		c.addGateToMemory(multiplex);
		c.generateTruthTable();
		
		CustomGate test = new CustomGate("test", c.getTruthTable(), 2, true);
		//CustomGate add2 = new CustomGate("add2", table2, 20, false);
		
		System.out.print("\n\n\n");
		
		System.out.println(test.getCircuit());
		//System.out.println(add2.getCircuit());
		
		System.out.print("\n\n\n");
		
	}
	
	public static char toChar(boolean i) {
		if(i)
			return '1';
		else
			return '0';
	}
	
	public static void disp(char[] i) {
		for(char c : i) {
			System.out.print(c);
		}
	}
	
	public static void disp(boolean[] i) {
		for(boolean c : i) {
			System.out.print(c);
		}
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
	
	public static void disp(ArrayList<String> a) {
		for(int i = 0; i < a.size(); i++)
			System.out.println(a.get(i));
	}
	
	public static void dispMemory(CustomGate c) {
		for(CustomGate cu : c.getknownGates())
			System.out.println(cu.getName());
	}
	
	public static String add(String i1, String i2) {
		
		boolean[] inputs = new boolean[3];
		char[] c = new char[i1.length()+1];
				
		for(int i = 0; i < i1.length(); i++) {
					
			inputs[0] = Tools.toBool(i1.charAt(i1.length()-1-i));
			inputs[1] = Tools.toBool(i2.charAt(i1.length()-1-i));
			
			c[i1.length()-i] = toChar(addO1.simulate(inputs));
			
			inputs[2] = addO2.simulate(inputs);
			
		}
		//Ajout de la derniere retenue
		c[0] = toChar(inputs[2]);
		
		return new String(c);
	}
	
}

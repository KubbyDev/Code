import java.util.ArrayList;
import java.util.Arrays;

public class Gate {

	private boolean[][] truthTable;
	private String circuit;
	private String name;
	private ArrayList<Gate> knownGates = new ArrayList<Gate>();
	
	// Constructors --------------------------------------------------------------------------------------
	
	/**
	 * Creates a Logic Gate from a circuit
	 * @param Name of the gate
	 * @param Circuit (ex: or(i0, i1))
	 * @throws IllegalArgumentException if the circuit or the name is not valid
	 */
	public Gate(String pName, String pCircuit) {
		
		if(!isValid(pCircuit))
			throw new IllegalArgumentException("This circuit is not valid (maybe you forgot a parenthesis)");
		
		if(pName == "")
			throw new IllegalArgumentException("The name of the gate must contain at least one character");
		
		if(pName.charAt(0) == 'i')
			throw new IllegalArgumentException("The name of the gate can't start with an \'i\'");
		
		//On donne le circuit, le programme peut génerer la table de vérités
		circuit = pCircuit;
		name = pName;
		
	}

	//TODO: Constructeurs prenant une truthTable en parametre
	
	private boolean isValid(String c) {
		// TODO Verifie que les portes ont le bon nombre d'arguments et les bonnes parentheses
		return true;
	}
	
	// Simulation ----------------------------------------------------------------------------------------
	
	/**
	 * Adds a gate to the memory.
	 * If you want to add a custom gate to the circuit of an other custom gate, use this method. Example:
	 * <p>Gate alwt = new Gate("always_true", "or(i0, not(i0))");
	 * <br>Gate alwf = new Gate("always_false", "not(always_true(i0))");
	 * <br>//Without this line you can't simulate always_false
	 * <br>alwf.addGateToMemory(alwt);
	 * <p>If gate is null, does nothing
	 * <br>If the gate already exists, replaces it
	 * @param gate the gate you want to add
	 */
	public void addGateToMemory(Gate gate) {

		if(gate == null)
			return;
		
		int i = findGateInMemory(gate.getName());
		
		if(i == -1)
			knownGates.add(gate);
		else
			knownGates.set(i, gate);
		
	}
	
	/**
	 * Returns the boolean value of the gate applied to the inputValues
	 * @param inputValues the value of each input (i0 = true, i1 = false...)
	 * @return the boolean value of the gate following the inputValues
	 */
	public boolean simulate(boolean[] inputValues) {
		return this.simulateGate(this.circuit, inputValues);
	}
	
	/**
	 * The basic logic gates (nor, or, and, xor, xnor, nor, nand)
	 */
	public static class Basic {
		
		public static boolean not(boolean i) {
			return !i;
		}
		
		public static boolean and(boolean i1, boolean i2) {
			return i1 && i2;
		}
		
		public static boolean or(boolean i1, boolean i2) {
			return i1 || i2;
		}
		
		public static boolean xor(boolean i1, boolean i2) {
			return (i1 && !i2) || (i2 && !i1);
		}
		
		public static boolean xnor(boolean i1, boolean i2) {
			return (!i1 && !i2) || (i2 && i1);
		}	
		
		public static boolean nand(boolean i1, boolean i2) {
			return !i1 || !i2;
		}	
		
		public static boolean nor(boolean i1, boolean i2) {
			return !i1 && !i2;
		}	
	}
	
	//TODO javadoc
	//TODO gestion des problemes de nombre d'arguments
	private boolean simulateGate(String gate, boolean[] inputValues) {
		//Retourne la valeur de sortie de la porte en fonction des inputs donnés
		
		String[] gateAndInputs = readGate(gate);
		
		switch (gateAndInputs[0]) {
		
		case "not"  : return Basic.not(  findInput(gateAndInputs[1], inputValues)   );
		case "and"  : return Basic.and(  findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "or"   : return Basic.or(   findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "xor"  : return Basic.xor(  findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "xnor" : return Basic.xnor( findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "nor"  : return Basic.nor(  findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "nand" : return Basic.nand( findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		
		default: 
			int index = findGateInMemory(gateAndInputs[0]);
				
			if(index == -1) {
				new UnknownGateException(this.getName() + "(" + this + ") doesn't know this gate: " + gateAndInputs[0] + " !").printStackTrace();
				return false;
			}
			else					
				return knownGates.get(index).simulate(inputValues);
			
		}
		
	}
	
	/**
	 * This method separates the name of the gate and its inputs
	 * @param gate the expression
	 * @return the name of the gate (index 0), its inputs (indexes 1+)
	 */
	private static String[] readGate(String gate) {
		
		int inputNum = 1;
		
		//Calcule le nombre d'inputs de la porte concernée
		int openBracket = 0;
		for(int i = 0; i < gate.length() ; i++) {
			
			if(gate.charAt(i) == '(') {
				openBracket++;
				//System.out.println("Bracket Opened at: " + i + "  count: " + openBracket);
			}
			if(gate.charAt(i) == ')') {
				openBracket--;
				//System.out.println("Bracket Closed at: " + i + "  count: " + openBracket);
			}
			
			if(gate.charAt(i) == ',' && openBracket == 1) {
				inputNum++;
				//System.out.println("Coma found at: " + i + "  inputs: " + inputNum);
			}
			
		}
		
		String[] ret = new String[inputNum+1];
		openBracket = 0;
		int stringBegin = 0;
		int input = 0;
		
		for(int i = 0; i < gate.length() ; i++) {
			
			//Cherche le nom de la porte. Quand il est trouvé, indique qu'on cherche maintenant l'input 1
			if(gate.charAt(i) == '(' && openBracket == 0) {
				ret[0] = gate.substring(stringBegin, i);
				stringBegin = i;
				input++;
				//System.out.println("Gate trouvée: " + ret[0]);
			}		
			
			if(gate.charAt(i) == '(')
				openBracket++;
			if(gate.charAt(i) == ')')
				openBracket--;
			
			//Cherche l'input n. Quand il est trouvé, indique qu'on cherche maintenant l'input n+1
			if((gate.charAt(i) == ',' && openBracket == 1) || (gate.charAt(i) == ')' && openBracket == 0)){
				ret[input] = gate.substring(stringBegin+1, i);
				stringBegin = i;
				//System.out.println("Input trouvé: " + ret[input]);
				input++;
			}		
			
		}		
		
		return ret;	
	}
	
	/**
	 * Finds the index of the gate in the knownGates
	 * @param pName name of the searched gate
	 * @return the index of the gate, -1 if it is not found
	 */
	private int findGateInMemory(String pName) {
		
		for(int i = 0; i < this.knownGates.size(); i++) 			
			if(this.knownGates.get(i).getName().equals(pName))
				return i;
				
		return -1;
	}
	
	/**
	 * Returns the boolean value of the expression input 
	 * (Simulates the gate if it is one, if it is an input, returns the corresponding value in the array)
	 * Example: for "and(i0,i1)" it will simulate the and gate and return its results.
     * For "i2" it will return inputValues[2]
	 * @param input the expression to process
	 * @param inputValues the values of the inputs
	 * @return The boolean value of the expression input following the inputValues
	 */	
	private boolean findInput(String input, boolean[] inputValues) {
		
		if(input.charAt(0) == 'i') {
			
			String s = "";			
			for(int i = 1; i < input.length(); i++)
				s += input.charAt(i);
			
			return inputValues[Integer.valueOf(s)];
		}
		else
			return this.simulateGate(input, inputValues);
					
	}

	// Truth Tables --------------------------------------------------------------------------------------
	
	//TODO javadoc
	public void generateTruthTable() {
		
		//Calcule le nombre d'inputs
		int nombreInputs = 0;
		for(int i = 0; i < circuit.length(); i++) {
			
			//Cherche un i (marqueur des inputs)
			if(circuit.charAt(i) == 'i') {
				
				String inputNumber = "";
				for(int y = i+1; y < circuit.length(); y++) {
										
					//Le nombre d'input est le plus grand index d'input trouvé
					if(circuit.charAt(y) == ',' || circuit.charAt(y) == ')') {
						y = circuit.length();
						nombreInputs = Math.max((Integer.valueOf(inputNumber))+1, nombreInputs);
					}
					else
						inputNumber += circuit.charAt(y);
					
				}
			}
		}

		int nbreTests = (int) Math.pow(2, nombreInputs);
		
		//System.out.println("Generation de la truthTable de " + this.getName() + "(" + this + ")\nNombre d'inputs: " + nombreInputs + ", Nombre de Combinaisons: " + nbreTests); 
		
		//Tableau final
		boolean[][] ret = new boolean[nbreTests][nombreInputs+1];
		
		//Remplissage du tableau
		for(int testedInput = 0; testedInput < nbreTests; testedInput++) {
			
			//On génere les inputs
			boolean[] input = new boolean[nombreInputs];
			String inputs = Long.toBinaryString(testedInput);
			
			
			for(int i = nombreInputs-1; i >= 0; i--) {
				
				if(i < inputs.length()) {
					boolean b = Tools.toBool(inputs.charAt(inputs.length()-i-1));
					input[i] = b;
					ret[testedInput][i] = b;
				}
				else {
					input[i] = false;
					ret[testedInput][i] = false;
				}
			}
			
			//On génere l'output
			ret[testedInput][nombreInputs] = this.simulate(input);
			
		}
			
		truthTable = ret;
		
	}
	
	//TODO javadoc
	public static boolean isValidTruthTable(boolean[][] table) {
		
		int nbreCombinaisons = (int) Math.pow(2, table[0].length-1);
		
		for(int i = 0; i < nbreCombinaisons; i++) {
			
			//Creation d'un input
			boolean[] inputToTest = new boolean[table[0].length-1];
			String inputs = Long.toBinaryString(i);
			
			for(int y = table[0].length-2; y >= 0; y--)
				if(y < inputs.length())
					inputToTest[y] = Tools.toBool(inputs.charAt(inputs.length()-y-1));
				else
					inputToTest[y] = false;
			
			//Verification de la presence de l'input dans la table
			boolean found = false;
			//Parcours des lignes
			for(boolean[] line : table)	{		
				
				//Parcours des valeurs, si une valeur est differente on passe a la ligne suivante, sinon la ligne est trouvee
				boolean equal = true;
				for(int y = 0; y < line.length-1; y++) {
					if(line[y] != inputToTest[y]) {
						equal = false;
						break;
					}
				}
				
				//Si la ligne est trouvee on arrete la recherche
				if(equal) {
					found = true;
					break;
				}
				
			}
			
			if(!found) {
				System.out.print("Missing line: ");
				for(int u = 0; u < inputToTest.length-1; u++)
					System.out.print(inputToTest[u] + ", ");
				System.out.print(inputToTest[inputToTest.length-1] + ". To must specify the output for those inputs !");
			
				return false;
			}
			
		}
		
		return true;
	}
	
	//TODO javadoc
	public static boolean[][] orderTruthTable(boolean[][] table) {
			
		if(!isValidTruthTable(table))
			return null;
		
		int nbreCombinaisons = (int) Math.pow(2, table[0].length-1);
		boolean[][] ret = new boolean[nbreCombinaisons][table[0].length];
		
		for(int i = 0; i < nbreCombinaisons; i++) {
			
			//Creation d'un input
			boolean[] inputToTest = new boolean[table[0].length-1];
			String inputs = Long.toBinaryString(i);
			
			for(int y = table[0].length-2; y >= 0; y--)
				if(y < inputs.length())
					inputToTest[y] = Tools.toBool(inputs.charAt(inputs.length()-y-1));
				else
					inputToTest[y] = false;
			
			//Recuperation de l'input dans la table
			//Parcours des lignes
			for(boolean[] line : table)	{		
				
				//Parcours des valeurs, si une valeur est differente on passe a la ligne suivante, sinon la ligne est trouvee
				boolean equal = true;
				for(int y = 0; y < line.length-1; y++) {
					if(line[y] != inputToTest[y]) {
						equal = false;
						break;
					}
				}
				
				//Si la ligne est trouvee on arrete la recherche et on passe la ligne dans la nouvelle table
				if(equal) {
					ret[i] = line;
					break;
				}
				
			}
			
		}
		
		return ret;
	}
	
	// Expressions --------------------------------------------------------------------------------------
	
	//TODO javadoc
	//TODO verifier que l'expression est valide
	public static String toCircuit(String boolExpr, String[] inputNames) {
		
		String s = cleanBoolExpr(boolExpr);
		
		int i = 0;
		int imbrication = 0;
		while(i < s.length()) {
			
			char c = s.charAt(i);
			
			if(c == '(')
				imbrication++;
			
			if(c == ')')
				imbrication--;
			
			if(imbrication == 0) {
				
				if(c == '+')
					return "or(" + toCircuit(removeParenthesis(s.substring(0, i)), inputNames) + "," + toCircuit(removeParenthesis(s.substring(i+1, s.length())), inputNames) + ")";
				
				if(c == '*')
					return "and(" + toCircuit(removeParenthesis(s.substring(0, i)), inputNames) + "," + toCircuit(removeParenthesis(s.substring(i+1, s.length())), inputNames) + ")";
				
			}
			
			i++;
		}
		
		if(s.charAt(0) == '/')
			return "not(" + toCircuit(removeParenthesis(s.substring(1, s.length())), inputNames) + ")";
		
		return "i" + Arrays.asList(inputNames).indexOf(s);		
	}
	
	//TODO javadoc
	public static String toCircuit(String boolExpr) {
		return toCircuit(boolExpr, defaultInputNames);
	}
	
	private static String[] defaultInputNames = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	//TODO javadoc
	private static String cleanBoolExpr(String s) {
		
		//Au cas ou l'expression commence par des espaces
		int j = 0;
		while(s.charAt(j) == ' ')
			j++;
		
		//On clean l'entree (suppression des espaces, ajout de * la ou il en faut)
		String ret = new String() + s.charAt(j);
		char prev = s.charAt(j);
		
		for(int i = j+1; i < s.length(); i++) {
			
			char c = s.charAt(i);
			
			if(c != ' ' && c != '*' && c != '+' && c != ')' && prev != '*' && prev != '+' && prev != '(' && prev != '/')
				ret += '*';
			
			if(c != ' ') {
				ret += c;	
				prev = c;
			}
		}
		
		return ret;
	}
	
	private static String removeParenthesis(String s) {
		return (s.charAt(0) == '(') ? s.substring(1, s.length()-1) : s;
	}
	
	// Others -------------------------------------------------------------------------------------------
	
	/**
	 * @return the name of the gate
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the list of the gates this gate knows (in order to simulate them)
	 */
	public ArrayList<Gate> getknownGates() {
		return knownGates;
	}

	/**
	 * Generates the truth table if it is not done yet
	 * @return the truth table of the gate
	 */
	public boolean[][] getTruthTable() {
		
		if(truthTable == null)
			generateTruthTable();		
		
		return truthTable;
	}
	
	/**
	 * @return the circuit of the gate
	 */
	public String getCircuit() {
		return this.circuit;
	}
	
	//TODO javadoc
	public boolean isCircuitEquivalent(String gate) {
		
		for(boolean[] line : getTruthTable()) {
			
			boolean[] inputs = new boolean[line.length-1];
			for(int y = 0; y < inputs.length; y++)
				inputs[y] = line[y];
			
			if(simulateGate(gate, inputs) != line[line.length-1])
				return false;
			
		}
		
		return true;		
	}	

	//TODO javadoc
	public boolean isCircuitEquivalent(Gate gate) {
		return isCircuitEquivalent(gate.getCircuit());
	}

	//TODO javadoc
	public static boolean areCircuitsEquivalent(String circuit1, String circuit2) {
		
		Gate g1 = new Gate("name", circuit1);
		return g1.isCircuitEquivalent(circuit2);
		
	}
	
}



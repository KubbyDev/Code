import java.util.ArrayList;
import java.util.Arrays;

public class CustomGate {

	//Attributs de la porte
	private boolean[][] truthTable;
	private String circuit;
	private boolean onlyBasic = false;
	private String name;
	private ArrayList<CustomGate> knownGates = new ArrayList<CustomGate>();
	
	//Variables utiles a la generation des circuits
	private ArrayList<String> ret = new ArrayList<String>();
	private boolean continueSearch = false;
	
	//Constructeurs
	
	public CustomGate(String pName, boolean[][] pTruthTable, long maxTimeSec, boolean pOnlyBasic) {
		
		//On donne la table de vérité, le programme génere le circuit en moins de (maxTimeSec) secondes 
		
		name = pName; 
		truthTable = pTruthTable;
		onlyBasic = pOnlyBasic;

		boolean[][] orderedTable = orderTruthTable(pTruthTable);
		if(orderedTable != null)
			circuit = selectBest(generateCircuit(orderedTable, maxTimeSec), pTruthTable[0].length-1);
		
	}
	
	public CustomGate(String pName, boolean[][] pTruthTable, long maxTimeSec) {
		//On donne la table de vérité, le programme génere le circuit en moins de (maxTimeSec) secondes 
		
		name = pName; 
		truthTable = pTruthTable;
		
		boolean[][] orderedTable = orderTruthTable(pTruthTable);
		if(orderedTable != null)
			circuit = selectBest(generateCircuit(orderedTable, maxTimeSec), pTruthTable[0].length-1);
		
	}
	
	public CustomGate(String pName, String pCircuit) {
		//On donne le circuit, le programme peut génerer la table de vérités
		
		circuit = pCircuit;
		name = pName;
		
	}

	//Simulation
	
	private boolean simulateGate(String gate, boolean[] inputValues) {
		//Retourne la valeur de sortie de la porte en fonction des inputs donnés
		
		String[] gateAndInputs = readGate(gate);
		
		switch (gateAndInputs[0]) {
		
		case "not"  : return Tools.not(  findInput(gateAndInputs[1], inputValues)   );
		case "and"  : return Tools.and(  findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "or"   : return Tools.or(   findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "xor"  : return Tools.xor(  findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "xnor" : return Tools.xnor( findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "nor"  : return Tools.nor(  findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		case "nand" : return Tools.nand( findInput(gateAndInputs[1], inputValues) , findInput(gateAndInputs[2], inputValues));
		
		default: 
			int index = this.findGateInMemory(gateAndInputs[0]);
				
			if(index == -1) {
				try { 
					throw new UnknownGateException(this.getName() + "(" + this + ") doesn't know this gate: " + gateAndInputs[0] + " !");
				} catch (UnknownGateException e) { e.printStackTrace(); }
				return false;
			}
			else					
				return knownGates.get(index).simulate(inputValues);
			
		}
		
	}
	
	public boolean simulate(boolean[] inputValues) {
		return this.simulateGate(this.circuit, inputValues);
	}
	
	private boolean findInput(String input, boolean[] inputValues) {
		//Renvoie la valeur de l'input (Simule la porte si c'en est une, si c'est directement un input, il renvoie la valeur correpondante du tableau)
		//Exemple: pour "and(i0,i1)" il simulera la porte and et renvera son résultat
		//         pour 	"i2"     il renvera inputValues[2]
		
		if(input.charAt(0) == 'i') {
			
			String s = "";			
			for(int i = 1; i < input.length(); i++)
				s += input.charAt(i);
			
			return inputValues[Integer.valueOf(s)];
		}
		else
			return this.simulateGate(input, inputValues);
					
	}
	
	public void addGateToMemory(CustomGate gate) {
		
		int i = findGateInMemory(gate.getName());
		
		if(i == -1)
			knownGates.add(gate);
		else
			knownGates.set(i, gate);
		
	}
	
	private static String[] readGate(String gate) {
		//Cette méthode sépare le nom de la porte et ses inputs
		//and(i0,not(i1)) deviendra "and" + "i0" + "not(i1)"
		
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

	private int findGateInMemory(String pName) {
		
		for(int i = 0; i < this.knownGates.size(); i++) 			
			if(this.knownGates.get(i).getName().equals(pName))
				return i;
				
		return -1;
	}
		
	//Generation de Table de verites
	
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
			
		this.truthTable = ret;
		
	}
	
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
	
	//Generation de Circuit

	public static String simplify(String circuit, long maxTimeSec, boolean pOnlyBasic) {
	
		CustomGate c = new CustomGate("c", circuit);
		c.generateTruthTable();
		CustomGate ret = new CustomGate("ret", c.getTruthTable(), maxTimeSec, pOnlyBasic);
		return ret.getCircuit();
		
	}

	private String generateTestGate(int nbreInputs) {
		
		TestGate gate = new TestGate(onlyBasic);
		
		for(int i = 0; i < gate.gateInputs; i++)
			gate.setInput(i, generateInput(gate, nbreInputs));
		
		return gate.toString();
	}
	
	private GateInput generateInput(TestGate gate, int nbreInputs) {
		
		int rand = (int) Math.round(Math.random());
		
		if(rand == 0)
			return new GateInput((int) Math.round( Math.random()*(nbreInputs-1) ));
		else {
			
			TestGate gate2 = new TestGate(onlyBasic);
			
			for(int i = 0; i < gate2.gateInputs; i++)
				gate2.setInput(i, generateInput(gate2, nbreInputs));
			
			return gate2;
		}
		
	}
	
	private ArrayList<String> generateCircuit(boolean[][] truthT, long maxTimeSec) {
		
		ret.clear();
		continueSearch = true;
		
		RandomGen r1 = new RandomGen(truthT);
		RandomGen r2 = new RandomGen(truthT);
		RandomGen r3 = new RandomGen(truthT);
		RandomGen r4 = new RandomGen(truthT);
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		Thread t3 = new Thread(r3);
		Thread t4 = new Thread(r4);
		
		//Lance la recherche
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		long start = System.currentTimeMillis();
		long timeSpent = 0;
		
		//Attend la fin du temps
		while(timeSpent < maxTimeSec*1000) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { e.printStackTrace(); }
			timeSpent = System.currentTimeMillis() - start;
			//System.out.println("Check: " + timeSpent + "  Continue: " + (timeSpent < maxTimeSec*1000));
		}
		
		//Termine la recherche
		continueSearch = false;
				
		return ret;
	}

	private String selectBest(ArrayList<String> a, int nbreInputs) {
	
		long bestNbre = 1000000000;
		int bestIndex = 0;
		
		if(a.isEmpty())
			return null;
		
		for(int i = 0; i < a.size(); i++) {
			
			String tested = a.get(i);
			
			if(tested != null) {
			
				boolean foundGate = false;
				int gateNbre = 0;
				for(int y = 0; y < tested.length(); y++) {
					
					if(foundGate) {
						if(tested.charAt(y) != '(')	
							foundGate = false;
					}
					else {
						if(Character.isAlphabetic(tested.charAt(y)) && tested.charAt(y) != 'i') {
							foundGate = true;
							gateNbre++;
						}
					}
					
				}
				//System.out.println("Circuit: " + tested + " GateNbre: " + gateNbre);
				
				if(gateNbre < bestNbre) {
					bestNbre = gateNbre;
					bestIndex = i;
				}
			
			}
			
		}
		
		return a.get(bestIndex);	
	}
	
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
	
	public static String toCircuit(String boolExpr, ArrayList<String> inputNames) {
		
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
		
		return "i" + inputNames.indexOf(s);
	}
	
	public static String toCircuit(String boolExpr) {
		return toCircuit(boolExpr, generateInputNames());
	}
	
	private static ArrayList<String> generateInputNames() {
	
		ArrayList<String> a = new ArrayList<String>();
		for(int i = 0; i < 26; i++)
			a.add((char) (i + 'a') + "");
		
		return a;		
	}
	
	private class GateInput {
		
		public int inputNumber;
		
		public GateInput(int number) {
			inputNumber = number;
		}
		
		public String toString() {
			return "i" + inputNumber;
		}
		
	}
	
	private class TestGate extends GateInput {
		
		private GateInput[] inputs;
		private String name;
		private int gateInputs;
		
		public TestGate(boolean onlySimple) {
			
			super(0);
			
			name = generateGateName(onlySimple);
			
			gateInputs = 2;
			if(name == "not")
				gateInputs = 1;
		
			inputs = new GateInput[gateInputs];
			
		}
	
		public void setInput(int index, GateInput input) {
			inputs[index] = input;
		}
		
		private String generateGateName(boolean onlySimple) {
			
			switch((int) Math.round( ((onlySimple) ? 2 : 6)*Math.random() )) {
			
			case 0: return "not"; 
			case 1: return "and"; 
			case 2: return "or";
			case 3: return "nor";
			case 4: return "nand";
			case 5: return "xor";
			case 6: return "xnor"; 
			
			default: return null;
			}
			
		}

		public String toString() {
			
			StringBuilder sb = new StringBuilder();
			
			//Le nom de la porte
			sb.append(name + "(");
			
			//Tous les inputs sauf le dernier, séparés par des virgules
			for(int i = 0; i < inputs.length-1; i++)
				sb.append(inputs[i].toString() + ",");
			
			//Le dernier input, avec une parenthese
			sb.append(inputs[inputs.length-1].toString() + ")");
			
			return new String(sb);
		}
		
	}
	
	private class RandomGen implements Runnable {

		boolean[][] truthT;
		
		public RandomGen(boolean[][] pTruthT) {
			truthT = pTruthT;
		}
		
		@Override
		public void run() {
			
			while(continueSearch) {
				
				String circuitTest = "";

				circuitTest = generateTestGate(truthT[0].length-1);

				CustomGate c = new CustomGate("temp", circuitTest);
				c.generateTruthTable();
					
				//System.out.println("Tested: " + circuitTest);
				
				if(CustomGate.equals(c.getTruthTable(), truthT)) {
					//System.out.println("FOUND: " + circuitTest);
					ret.add(circuitTest);
				}
				
			}
			 
		}
		
	}
	
	//Autres
	
	public static boolean equals(boolean[][] b1, boolean[][] b2) {
		
		for (int i = 0; i < b1.length; i++)
			if (!Arrays.equals(b1[i], b2[i]))
			    return false;
		
		return true;
	}
	
	public boolean isCircuitEquivalent(String gate) {
		
		for(boolean[] line : truthTable) {
			
			boolean[] inputs = new boolean[line.length-1];
			for(int y = 0; y < inputs.length; y++)
				inputs[y] = line[y];
			
			if(simulateGate(gate, inputs) != line[line.length-1])
				return false;
			
		}
		
		return true;		
	}
	
	//Getters
	
	public String getName() {
		return name;
	}

	public ArrayList<CustomGate> getknownGates() {
		return knownGates;
	}

	public boolean[][] getTruthTable() {
		return this.truthTable;
	}
	
	public String getCircuit() {
		return this.circuit;
	}
	
	public static String toMathExpression(String s) {
		return toMathExpression(s, generateInputNames());
	}
	
	public static String toMathExpression(String s, ArrayList<String> inputNames) {
		
		String[] gateAndInputs = readGate(s);
			
		switch (gateAndInputs[0]) {
	
		case "not"  : return "/" + getMathInput(gateAndInputs[1], inputNames, true);
		case "and"  : return getMathInput(gateAndInputs[1], inputNames, false) + getMathInput(gateAndInputs[2], inputNames, false);
		case "or"   : return getMathInput(gateAndInputs[1], inputNames, false) + " + " + getMathInput(gateAndInputs[2], inputNames, false);
	
		default: 
			new Exception("The math expression can only contain +, *, / operators").printStackTrace();
			return "";
		}
		
	}
	
	private static String getMathInput(String input, ArrayList<String> inputNames, boolean needParenthesis) {
	
		if(input.charAt(0) != 'i')   //Si on a une porte a traduire
			if(input.contains("or(") || needParenthesis)
				return "(" + toMathExpression(input, inputNames) + ")";	
			else
				return toMathExpression(input, inputNames);	
		
		//Si on a un input a traduire
		return inputNames.get(Integer.valueOf(input.substring(1, input.length())));
		
	}
	
	public String getMathExpression() {
		return toMathExpression(circuit);
	}
	
	public String getMathExpression(ArrayList<String> inputNames) {
		return toMathExpression(circuit, inputNames);
	}
	
}

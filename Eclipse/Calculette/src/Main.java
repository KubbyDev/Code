
public class Main {

	private static String currentNumber = "0";
	private static String savedNumber = "";
	private static String disp2 = "";
	private static Fenetre fen = new Fenetre();
	private	static int operation = 0;
	
	public static void main(String[] args) {

		fen.setVisible(true);
		
	}

	public static void buttonClick(int buttonID) {

		switch (buttonID) {
			case -2:
				reset();
				break;
			case -1:
				removeNumber();
				break;
			case 10:
				addVirgule();	
				break;
			case 20:
				invertSign();
				break;
			case 100:
				calculate();
				break;
			default:
				if(buttonID >= 0 && buttonID <= 9)
					addNumber(buttonID);
				if(buttonID >= 11 && buttonID <= 14)
					operation(buttonID);
		}
		
	}
	
	private static void addNumber(int number) {
		
		if(currentNumber != "0")
			currentNumber += String.valueOf(number);
		else
			currentNumber = String.valueOf(number);
		
		fen.updateDisp(currentNumber, disp2);
	}
	
	private static void removeNumber() {
		
		if(currentNumber.length() == 1)
			currentNumber = "0";
		else
			currentNumber = currentNumber.substring(0, currentNumber.length()-1);
		
		fen.updateDisp(currentNumber, disp2);
	}
	
	private static void addVirgule() {
		currentNumber += ".";
		fen.updateDisp(currentNumber, disp2);
	}
	
	private static void operation(int id) {
		
		disp2 = currentNumber;
		savedNumber = currentNumber;
		operation = id - 10;
		
		switch (operation) {
			case 1:
				disp2 += " +";
				break;
			case 2:
				disp2 += " *";
				break;
			case 3:
				disp2 += " -";
				break;
			case 4:
				disp2 += " /";
				break;
			default:;
		}

		currentNumber = "0";
		fen.updateDisp(currentNumber, disp2);
		
	}
	
	private static void calculate() {
		
		boolean error = false;
		
		switch (operation) {
			case 1:
				currentNumber = String.valueOf(Double.valueOf(savedNumber) + Double.valueOf(currentNumber));
				break;
			case 2:
				currentNumber = String.valueOf(Double.valueOf(savedNumber) * Double.valueOf(currentNumber));
				break;
			case 3:
				currentNumber = String.valueOf(Double.valueOf(savedNumber) - Double.valueOf(currentNumber));
				break;
			case 4:
				if(Double.valueOf(currentNumber) != 0)
					currentNumber = String.valueOf(Double.valueOf(savedNumber) / Double.valueOf(currentNumber));
				else {
					fen.error("Division par zéro impossible !");
					error = true;
				}
				break;
			default:;
		}
		
		if(!error) {
			savedNumber = "";
			disp2 = "";
			fen.updateDisp(currentNumber, "");
		}
		
	}

	private static void reset() {
		currentNumber = "0";
		savedNumber = "";
		disp2 = "";
		fen.updateDisp("0", "");
	}

	private static void invertSign() {
		
		if(currentNumber.charAt(0) == '-')
			currentNumber = currentNumber.substring(1, currentNumber.length());
		else
			currentNumber = "-" + currentNumber;
		
		fen.updateDisp(currentNumber, disp2);
	}
	
}

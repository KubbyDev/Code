
public class Tools {

	//Portes logiques basiques
	
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
		return not(xor(i1, i2));
	}	
	
	public static boolean nand(boolean i1, boolean i2) {
		return not(and(i1, i2));
	}	
	
	public static boolean nor(boolean i1, boolean i2) {
		return not(or(i1, i2));
	}	
	
	//Autres
	
	public static long toDecimal(String binNumber) {
		
		long ret = 0;
		for(int i = 0; i < binNumber.length(); i++)			
			if(binNumber.charAt(i) == '1')
				ret += Math.pow(2, binNumber.length()-1-i);
		
		return ret;		
	}
	
	public static boolean toBool(char i) {
		return i == '1';
	}
	
	public static boolean toBool(int i) {
		return i == 1;
	}
	
	public static boolean toBool(long i) {
		return i == 1;
	}
	
	public static boolean toBool(String i) {
		return i == "1";
	}
	
}

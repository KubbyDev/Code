
public class expLn {

	public static int nbTours = 0;
	public static int var1 = 0;
	public static int var3 = 0;
	public static int var2 = 0;
	public static int res = 0;
	
	public static void main(String[] args) {

		double x = -0.23;
		System.out.println("Exp(" + x + ")\n" + "Valeur de Java: " + Math.exp(x) + "\nApproximation avec des doubles: " + approxExp2(x));
		
		var1 = (int) (x*100);
		nbTours = 0;
		exp();
		System.out.println("Approximation avec des ints: " + res + " En " + nbTours + " tours");
	}

	public static void exp() {
		
		res = 1000;
		var3 = var1;
		var3 *= 10;
		var2 = 1;
		expLoop();
	}
	
	private static void expLoop() {
		
		res += var3;
		var2 += 1;
		var3 /= var2;
		var3 *= var1;
		var3 /= 100;
		
		nbTours++;
		if(var3 != 0)
			expLoop();
	}
	
	public static double approxExp(double x) {
		
		double ex = 0;
				
		for(int i = 0; i <= 14; i++) 
		{
			//System.out.println(Math.pow(x, i) + "  " + factorial(i) + "  " + (Math.pow(x, i) / factorial(i)));
			ex += Math.pow(x, i) / factorial(i); 
		}
		
		return ex;
	}
	
	public static double approxExp2(double x) {
		
		double sum = 1.0;
		double term = x;
		
		for(int i = 2; i-1 <= 20; i++) 
		{
			sum += term;
			term  = term * (x / i);
		}
		
		return sum;
	}
	
    public static long factorial(int number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }
	
    public static double approxLn(double x) {
		
		double lnx = 0;
		int negate = -1;	
		
		for(int i = 1; i <= 010; i++) 
		{
			
			negate *= -1;
			System.out.println(Math.pow(x, i) + "  " + i + "  " + (Math.pow(x, i) / i)*negate);
			
			lnx += (Math.pow(x, i) / i)*negate; 
			
		}
		
		return lnx;
    }
    
}

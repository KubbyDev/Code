
public class expLn {

	public static void main(String[] args) {

		System.out.println(Math.exp(0.1) + "    " + approxExp(0.1));
		//System.out.println(Math.log(5) + "    " + approxLn(4));
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

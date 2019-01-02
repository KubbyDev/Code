
public class expLn {
	
	public static int var1 = 0,
					  var2 = 0,
					  var3 = 0,
					  var4 = 0,
					  var5 = 0,
					  var6 = 0,
					  var7 = 0,
					  var8 = 0,
					  res = 0;

	public static int nbTours = 0;
	
	public static void main(String[] args) {
		
		/*
		double x = -0.23;
		System.out.println("Exp(" + x + ")\n" + "Valeur de Java: " + Math.exp(x) + "\nApproximation avec des doubles: " + approxExp2(x));
		
		var1 = (int) (x*100);
		nbTours = 0;
		exp();
		System.out.println("Approximation avec des ints: " + res + " En " + nbTours + " tours");
		*/
		
		/*
		double[] tests = {0.001, 0.005, 0.01, 0.05, 0.1, 0.5, 1, 5, 10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000};
		for(double d : tests) {
			System.out.print("x= " + d + " ");
			approxLn2(d);
			var1 = (int) (d*1000);
			ln();
			System.out.println();
		}
		*/
				
		double x = 0.002;
		System.out.println("ln(" + x + ")\nApproximation:     " + approxLn2(x) + "\nValeur de Java:    " + Math.log(x));
		
		var1 = (int) (x*1000);
		ln();
		System.out.println("Methode Minecraft: " + res);
		
		int i = 1;
		
		int maxTest = 1;
		int step = -3;
		System.out.println("\n\n\nTest des valeurs 10^-3 a 10^" + maxTest + " avec un pas de 10^" + step);
		
		int pas = (int) Math.pow(10, step +3);
		long valeursTestees = 0;
		double erreurSomme = 0;
		double erreurMax = 0;
		double erreurMaxPos = 0;
		int erreurGrosses = 0;
		int erreurTresGrosses = 0;
		
		while(i <= Math.pow(10, maxTest +3)) {
			
			var1 = i;
			ln();
			int valeurJava = (int) (10000 * Math.log((double) i / 1000));
			
			double erreur = res - valeurJava; 
			
			valeursTestees++;
			erreurSomme += erreur;
			if(Math.abs(erreur) > Math.abs(erreurMax)) {
				//System.out.println("i = " + i + " Java: " + valeurJava + " Minecraft: " + res);
				erreurMax = erreur;
				erreurMaxPos = (double) i / 1000;
			}
			if(Math.abs(erreur) >= 10) erreurGrosses++;
			if(Math.abs(erreur) >= 20) erreurTresGrosses++;
			
			i += pas;
		}
		
		System.out.println("Nombre de valeurs testées: " + valeursTestees);
		System.out.println("Erreur Moyenne: " + (double) Math.round(erreurSomme/valeursTestees)/10000);
		System.out.println("Erreur Max: " + erreurMax/10000 + " en " + erreurMaxPos);
		System.out.println("Erreurs >= 0.001: " + erreurGrosses);
		System.out.println("Proportion d'erreurs >= 0.001: " + (double) Math.round(100000*((double)erreurGrosses/(double)valeursTestees))/1000 + "%");
		System.out.println("Erreurs >= 0.002: " + erreurTresGrosses);
		System.out.println("Proportion d'erreurs >= 0.002: " + (double) Math.round(100000*((double)erreurTresGrosses/(double)valeursTestees))/1000 + "%");

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
    
    public static double approxLn2(double x) {
    	
    	int n = (int) Math.log10(x)+1;
    	double a = x / Math.pow(10, n-1);
    	double sp = 0;
    	double y = (a-1)/(a+1);
    	
    	//System.out.print(" (n+2)= " + (n+2));
    	System.out.println("(n+2)= " + (n+2));
    	System.out.println("a= " + a);
    	System.out.println("y= " + y);
    	
    	for(int i = 0; i < 13; i++) {
    		System.out.println("Add to sp: " + Math.pow(y,  2*i +1) / (2*i +1));
    		sp += Math.pow(y,  2*i +1) / (2*i +1);
    	}
    	
    	System.out.println("Sp= " + sp);
    	
    	return 2*sp + (n-1)*2.302585;
    }
    
    public static void ln() {
    	
    	//Var4 = x (* 1000)
    	var4 = var1; 
    	
    	//Var2 = n-1
    	if(var4 <= 1000) var1 -= 1;   	
    	var2 = 0;
    	if(var4 != 1)
    		ln_ndigits();
    	if(var4 > 1000) var2 -= 1;
    	
    	//Var7 = n-1
    	var7 = var2;
    	var7 -= 3;
    	
    	//System.out.print(" var2= " + var2 + "  ");
    	
    	//Res = 10^(n-1) (* 1000)
    	var1 = 10;
    	res = (int) Math.pow(var1, var2);   //Attention, var3 est modifie a ce moment la (dans la fonction de la lib)
    	
		//y = (a-1)/(a+1), a = x/10^(n-1) => y = (x-10^(n-1))/x+10^(n-1)
    	//Var1 = y (* 10 000)
    	var1 = var4;
    	var1 -= res;
    	var4 += res;
    	
    	//On adapte les ordres de grandeur pour avoir la meilleure précision possible
    	//Var2 = 10^(min (5, n-2))
    	/*
    	var2 = var7;
    	var2 -= 1;
    	if(var2 > 5) var2 = 5;
    	res = (int) Math.pow(var1, var2);
    	var8 = 10000;
    	if(res == 0) res = 1;
    	var8 /= res;
    	*/
    	
    	var8 = (int) Math.pow(10, Math.max(-1, -var7+5));
    	if(var8 == 0) var8 += 1;
    	
    	double mult = Math.pow(10, Math.min(5, var7-1));
    	System.out.print(mult);
    	
    	var1 *= var8;   //10000/Math.pow(10, Math.min(5, var7-1))
    	if(var1 > 0)
    		var1 += 50000;
    	if(var1 < 0)
    		var1 -= 50000;
    	var4 /= Math.pow(10, Math.min(5, var7-1));
    	
    	var1 /= var4;
	
    	//Var6 = Sp (* 10 000)
    	var2 = 1;
    	var6 = 0;
    	
    	var3 = var1;
    	ln_loop();
    	
    	res = var6;
    	res *= 2;
    	var7 *= 2302585;
    	var7 /= 100;
    	res += var7;
    	
    	res += 10;
    }
    
    public static void ln_ndigits() {
    	
    	//Counts the number of digits before the point
    	var1 /= 10;
    	var2 += 1;
    	
    	if(var1 != 0)
    		ln_ndigits();
    	
    }    
    
    public static void ln_loop() {
    	
    	res = var3;
    	res /= var2;
    	var6 += res;
    	var2 += 2;
    	
    	//Calcul de y^(var2)
    	var3 *= var1;
    	if(var3 > 0)
    		var3 += 5000;
    	if(var3 < 0)
    		var3 -= 5000;
    	var3 /= 10000;    	
    	var3 *= var1;
    	if(var3 > 0)
    		var3 += 5000;
    	if(var3 < 0)
    		var3 -= 5000;
    	var3 /= 10000;
    	
    	if(res > 1 || res < -1)
    		ln_loop();
    
    }
    
}


public class sincos {

	public static void main(String[] args) {
		
		double nbreGErrS = 0;
		double nbre1ErrS = 0;
		double nbre2ErrS = 0;
		int maxErrorS = 0;
		
		for(int angle = 0; angle <= 360000; angle = angle+1)
		{
		
			double angleR = (angle) * 3.1415 / 180000;
			int x = (int) (Math.sin(angleR) * 1000);
			int xApprox = approxSin(angle/10);
			
			if(Math.abs(x-xApprox) > maxErrorS)
				maxErrorS = Math.abs(x-xApprox);
			
			if(Math.abs(x-xApprox) > 2)
				nbreGErrS++;
			
			if(Math.abs(x-xApprox) == 2)
				nbre2ErrS++;
			
			if(Math.abs(x-xApprox) == 1)
				nbre1ErrS++;
			
			//System.out.println("approxSin(" + angle + ") = " + xApprox + "      sin(" + angle + ") = " + x + "     difference = " + Math.abs(x - xApprox));
			
		}
		
		System.out.println("Sin >  Erreur Max: " + maxErrorS + " | Erreurs de 1: " + (double)(Math.round(nbre1ErrS/360))/10 + "%" + " | Erreurs de 2: " + (double)(Math.round(nbre2ErrS/360))/10 + "%" + " | Erreurs de 3+: " + (double)(Math.round(nbreGErrS/360))/10 + "%");
		
		double nbreGErrC = 0;
		double nbre1ErrC = 0;
		double nbre2ErrC = 0;
		int maxErrorC = 0;
		
		for(int angle = 0; angle <= 360000; angle = angle+1)
		{
		
			double angleR = (angle) * 3.1415 / 180000;
			int x = (int) (Math.cos(angleR) * 1000);
			int xApprox = approxCos(angle/10);
			
			if(Math.abs(x-xApprox) > maxErrorC)
				maxErrorC = Math.abs(x-xApprox);
			
			if(Math.abs(x-xApprox) > 2)
				nbreGErrC++;
			
			if(Math.abs(x-xApprox) == 2)
				nbre2ErrC++;
			
			if(Math.abs(x-xApprox) == 1)
				nbre1ErrC++;
			
			//System.out.println("approxSin(" + angle + ") = " + xApprox + "      sin(" + angle + ") = " + x + "     difference = " + Math.abs(x - xApprox));
			
		}
		
		System.out.println("Cos >  Erreur Max: " + maxErrorC + " | Erreurs de 1: " + (double)(Math.round(nbre1ErrC/360))/10 + "%" + " | Erreurs de 2: " + (double)(Math.round(nbre2ErrC/360))/10 + "%" + " | Erreurs de 3+: " + (double)(Math.round(nbreGErrC/360))/10 + "%");
		

	}
	
	public static int approxCos(int a) {
		
		int angle = a;
		
		if(a >= 9000 && a < 18000)
		{
			a = a * -1;
			a = a + 18000;
		}
		
		if(a >= 18000 && a < 27000)
		{
			a = a - 18000; 
		}
		
		if(a >= 27000)
		{
			a = a * -1;
			a = a + 36000; 
		}
		
		int temp1 = (a * a * -4 + 324000000);
		int temp2 = (a * a + 324000000)/1000;
		
		a = temp1 / temp2;
		
		if(angle >= 9000 && angle < 27000)
			a = a * -1;
		
		return a;
	}
	
	public static int approxSin(int a) {
		
		int angle = a;
		
		if(a >= 18000)
			a = a - 18000; 
		
		int temp1 = (18000 - a) * 4 * a;
		int temp2 = (405000000 - a * (18000 - a)) / 1000;
		
		a = temp1 / temp2;
		
		if(angle >= 18000)
			a = a * -1;
		
		return a;
	}

}

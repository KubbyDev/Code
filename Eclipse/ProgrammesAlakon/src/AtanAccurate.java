
public class AtanAccurate {

	static double pi = 3.14159265359;
	
	public static void main(String[] args) {

		int erreurMax = 0;
		
		for(int a = 500; a <= 500; a++)
		{
			double d = ((double) (a))/1000;
			int x = (int) Math.round(((Math.atan(d)) * 180 / pi)*1000);
			int xApp = (int) Math.round(atanApproxM(d));
					
			System.out.print("\n" + x + "    " + xApp  + "     " +  (x - xApp));
			
			if(Math.abs(x - xApp) > erreurMax)
			{
				erreurMax = Math.abs(x - xApp);
				System.out.print("   NOUVELLE ERREUR MAX  ");
			}
		}
		
		System.out.print("\nErreur Max:   " + (int)(erreurMax));
		
	}

	public static double atanApprox(double a) {
		double c = 0.640388;
		return 1000*(pi/2)*(c*a + a*a + a*a*a)/(1 + (c+1)*a + (c+1)*a*a + a*a*a);
	}
	
	public static int atanApproxI(double angle) {
		int a = (int) (angle*1000);
		int c = 640;
		
		int temp1 =  (785*((c*a + a*a + (a*a*a)/1000)));
		int temp2 =  (1000000 + c*a + a*1000 + (c * a * a)/1000 + a * a + (a * a * a)/1000);
		
		return ((((temp1/temp2)*2)+1) * 18000) /314; 
	}

	public static int atanApproxM(double angle) {
		int temp1 = (int) (angle*1000);
		
		int temp2 = 0;
		int temp3 = 0;
		int res = 0;
		
		//Nominateur
		temp3 = temp1;
		temp3 *= temp1;
		
		temp2 = temp3;
		
		temp3 *= temp1;
		temp3 /= 1000;
		
		temp2 += temp3;
		
		res = temp1;
		res *= 640;
		
		res += temp2;
		res *= 785;
			
		//Dénominateur
		temp2 = temp1;
		temp2 *= 640;
		
		temp3 = temp1;
		temp3 *= 1000;
		
		temp2 += temp3;
		temp2 += 1000000;
		
		temp3 = temp1;
		temp3 *= temp1;
		temp3 *= 640;
		temp3 /= 1000;
		
		temp2 += temp3;
		
		temp3 = temp1;
		temp3 *= temp1;
		
		temp2 += temp3;
		
		temp3 *= temp1;
		temp3 /= 1000;
		
		temp2 += temp3;
		
		//Division
		res /= temp2;
		res *= 2;
		res++;
		
		res *= 18000;
		res /= 314;
		
		return res;
	}
	
}


public class AcosAccurate {

	static double pi = 3.14159265359;
	
	public static void main(String[] args) {

		int erreurMax = 0;
		
		for(int x = -100000; x <= -100000; x++)
		{
			x = -843;
			System.out.print("\n");
			double aDouble = ((double) (x))/1000;
			int a = (int) Math.round(((Math.acos(aDouble)) * 180 / pi)*1000);
			int aApp = (int) acosApproxM(aDouble);
					
			System.out.print("      Angle: " + a + " | Approx: " + aApp  + "     " +  (a - aApp));
			
			if(Math.abs(a - aApp) > erreurMax)
			{
				erreurMax = Math.abs(a - aApp);
				System.out.print("   NOUVELLE ERREUR MAX  ");
			}
		}
		
		System.out.print("\nErreur Max:   " + (int)(erreurMax));
		
	}

	public static double acosApprox(double x) {
		
		x = Math.abs(x);
		
		/*
		double res = -0.0187293; //a
		res = res * x;
		res = res + 0.0742610;	//b
		res = res * x;
		res = res - 0.2121144; //c
		res = res * x;
		res = res + 1.5707288; //d
		*/
		
		double res = -0.0187293*x*x*x + 0.0742610*x*x + -0.2121144*x + 1.5707288;
		
		System.out.print(" Avant sqrt: " + res);
		
		res = res * Math.sqrt(1-x);
		
		return res;
	}
	
	public static int acosApproxI(double xInput) {
		
	
		int x = (int) ((Math.abs(xInput))*1000);

	
		int res = (((x*x*x)/1000) * -19)/1000 + (74 * x * x)/1000 - 212 * x + 1570729;
	
		/*
		int res = -19; //a
		res = res * x;
		res = res + 74;	//b
		res = res * x;
		res = res - 212; //c
		res = res * x;
		res = res + 1570;
		*/
		
		System.out.print(" Avant sqrt: " + res + "  sqrt: " + Math.round((Math.sqrt(100000000 - 100000 * x))));
		
		res = (int) ((res)/100 * (  Math.round(Math.sqrt(100000000 - 100000 * x)) ) );
		
		return (((res/100000)*18000)/314)+8;
	}
	
	public static int acosApproxM(double input) {

		int res = 0;
		int tmp1 = (int) ((Math.abs(input))*1000);
		int tmp2 = 0;
		int tmp6 = 0;
		int negate = 0;
		if(input < 0)
			negate = 1;
		
		tmp6 = tmp1;
		tmp6 *= tmp1;
		tmp6 *= tmp1;
		tmp6 /= 1000;
		tmp6 *= -19;
		tmp6 /= 1000;	
		
		System.out.println(tmp6);
		
		tmp2 = tmp1;
		tmp2 *= tmp1;
		tmp2 *= 74;
		tmp2 /= 1000;
		tmp6 += tmp2;
		
		System.out.println(tmp6);
		
		tmp2 = tmp1;
		tmp2 *= 212;
		tmp6 -= tmp2;
		
		System.out.println(tmp6);
		
		tmp6 += 1570729;
		tmp6 /= 100;
		
		System.out.println(tmp6);
		
		tmp1 *= -1;
		tmp1 *= 100000;
		tmp1 += 100000000;
		
		System.out.println(tmp1);
		
		res = (int) Math.round(Math.sqrt(tmp1));
		
		System.out.println(res);
		
		res *= tmp6;
		
		System.out.println(res);
		
		res = res - 2 * negate * res;
		res = negate * 314080000 + res;
		
		System.out.println(res);
		
		res /= 100000;
		res *= 18000;
		res /= 314;
		res += 8;
		
		return res;
	}
	
}

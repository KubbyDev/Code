
public class atanmcfunction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double x = 0,
				z = 0,
				 d = 0,
				  angleR = 0,
				   pi = 3.141592,
				    absX = 0,
				     absZ = 0,
				      x2 = 0,
				       z2 = 0,
						d2 = 0,
						 tmp3 = 0,
						  tmp4 = 0,
						   resD = 0;
		
		int vx = 0,
			 vz = 0,
			  pi2 = 314,
			   res = 0,
			    tmp = 0,
			     tmp2 = 0,
				  vx2 = 0,
				   vz2 = 0;
		
		int deg0 = 0,
			 deg1 = 0,
			  deg2 = 0;
		
		for(int angle = -179; angle <= 180; angle++)
		{
			angleR = angle * pi / 180;
			
			//Calcul des vecteurs
			z = Math.sin(angleR);
			x = Math.cos(angleR);
			
			absX = Math.abs(x);
			absZ = Math.abs(z);
			
			tmp3 = round(1000 * x);
			tmp4 = round(1000 * z);
			vx = (int) tmp3;
			vz = (int) tmp4;
			
			//Approximation de Java
			d2 = (Math.atan(z / x)) * 180 / pi;
			
			if(x < 0 && z < 0)
				d2 = d2 - 180;
			if(x < 0 && z > 0)
				d2 = d2 + 180;
		
			System.out.print("\n");
		
			//Approximation du site
			if(absX >= absZ && x > 0)
			{
				
				d = (((x * z) / (x*x + 0.28125 * z*z))) * 180 / pi;
				
				System.out.print("1 \n");
				System.out.print(vx + "\n");
				System.out.print(vz + "\n");
				
				tmp = (vx * vz * 1000);	
				
				tmp2 = vz * 28125;
				tmp2 = tmp2 / 10000;
				tmp2 = vx * vx * 10 + tmp2 * vz;
			    tmp2 = tmp2 / 10;
			    
				res = (tmp / tmp2);
				
				System.out.print(tmp2 + "\n");
				System.out.print(tmp + "\n");
				
				System.out.print(res + "\n");
				
				res = (res * 18);
				res = divide(res, pi2);
				
				System.out.print(res + "\n");
				System.out.print(res +270 + "\n");
				
				d = res;
				
			}
			if(absX >= absZ && x < 0)
			{
				if(z > 0)
				{
					d = (pi + ((x * z) / (x*x + 0.28125 * z*z))) * 180 / pi;	
					
					System.out.print("1 \n");
					System.out.print(vx + "\n");
					System.out.print(vz + "\n");
					
					tmp = (vx * vz * 1000);	
					
					tmp2 = vz * 28125;
					tmp2 = tmp2 / 10000;
					tmp2 = vx * vx * 10 + tmp2 * vz;
				    tmp2 = tmp2 / 10;
					
				    res = (tmp / tmp2);
					
				    System.out.print(tmp2 + "\n");
					System.out.print(tmp + "\n");
					
					System.out.print(res + "\n");
				    
					res = (res * 18);
					res = divide(res, pi2);
					
					System.out.print(res + "\n");
					
					res = res + 180;
					
					System.out.print(res +270 + "\n");
					
					d = res;	
				}
				else
				{
					x2 = x - z;
					z2 = x + z;
					d = ((-3 * pi / 4) - ((x2 * z2) / (z2*z2 + 0.28125 * x2*x2))) * 180 / pi;
					
					
					vx2 = vx - vz;
					vz2 = vx + vz;
					
					tmp = (vx2 * vz2 * 1000);	
					
					
					tmp2 = vx2 * 28125;
					tmp2 = tmp2 / 10000;
					tmp2 = vz2 * vz2 * 10 + tmp2 * vx2;
				    tmp2 = tmp2 / 10;
					
				    res = (tmp / tmp2);
					
					res = (res * 18);
					res = divide(res, pi2);
					
					res = -135 - res;
					
					d = res;	
					
				}
			}
			if(absX < absZ && z > 0)
			{
				d = (pi/2 - ((x * z) / (z*z + 0.28125 * x*x))) * 180 / pi;	
				
				
				tmp = (vx * vz * 1000);	
				
				tmp2 = vx * 28125;
				tmp2 = tmp2 / 10000;
				tmp2 = vz * vz * 10 + tmp2 * vx;
			    tmp2 = tmp2 / 10;
				
			    res = (tmp / tmp2);
				
				res = (res * 18);
				res = divide(res, pi2);
				
				res = 90 - res;
				
				d = res;		
				
			}
			if(absX < absZ && z < 0)
			{
				d = (-pi/2 - ((x * z) / (z*z + 0.28125 * x*x))) * 180 / pi;
				
				tmp = (vx * vz * 1000);	
				
				tmp2 = vx * 28125;
				tmp2 = tmp2 / 10000;
				tmp2 = vz * vz * 10 + tmp2 * vx;
			    tmp2 = tmp2 / 10;
				
			    res = (tmp / tmp2);
				
				res = (res * 18);
				res = divide(res, pi2);
				
				res = -90 - res;
				
				d = res;	
				
			}
			
				System.out.print(d);
			
			if(round(d) != round(d2))
			{
				System.out.print("\nErreur de " + Math.abs(round(d) - round(d2)) + " degré !");
				if(Math.abs(round(d) - round(d2)) > 2)
						break;
				else if(Math.abs(round(d) - round(d2)) == 1)
					deg1++;
				else
					deg2++;
			}
			else
				deg0++;
			
			System.out.print("\n");
		}
		
		System.out.print("\n\nValeurs bonnes: " + deg0 + " | Erreurs de 1 degré: " + deg1 + " | Erreurs de 2 degrés: " + deg2);
		
	}
	

	public static int divide(int a, int b)
	{
		if(a%b == 0)
			return a / b;
		else
		{
			double a2 = a;
			double b2 = b;
			double d = a2 / b2;
			return round(d);	
		}	
	}
	
	public static int round(double a) {
		int rounded;
		int dizieme;
		dizieme = (int) ((a * 10) % 10);
		rounded = (int) a;
		if(Math.abs(dizieme) >= 5)
		{
			if(a > 0)
				rounded++;
			else
				rounded--;
		}
		return rounded;
	}
}


public class tan {

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
						d2 = 0;
		
		for(int angle = -180; angle <= 180; angle++)
		{
			angleR = angle * pi / 180;
			absX = Math.abs(x);
			absZ = Math.abs(z);
			
			//Calcul des vecteurs
			z = Math.sin(angleR);
			x = Math.cos(angleR);
			
			//Approximation de Java
			d2 = (Math.atan(z / x)) * 180 / pi;
			if(x < 0 && z < 0)
				d2 = d2 - 180;
			if(x < 0 && z > 0)
				d2 = d2 + 180;
			System.out.print(d2);
		
			System.out.print("\n");
		
			//Approximation du site
			if(absX >= absZ && x > 0)
			{
				d = (((x * z) / (x*x + 0.28125 * z*z))) * 180 / pi;
			}
			if(absX >= absZ && x < 0)
			{
				if(z > 0)
				{
					d = (pi + ((x * z) / (x*x + 0.28125 * z*z))) * 180 / pi;	
				}
				else
				{
					x2 = x - z;
					z2 = x + z;
					d = ((-3 * pi / 4) - ((x2 * z2) / (z2*z2 + 0.28125 * x2*x2))) * 180 / pi;
				}
			}
			if(absX < absZ && z > 0)
			{
				d = (pi/2 - ((x * z) / (z*z + 0.28125 * x*x))) * 180 / pi;
			}
			if(absX < absZ && z < 0)
			{
				d = (-pi/2 - ((x * z) / (z*z + 0.28125 * x*x))) * 180 / pi;
			}
			System.out.print(d);
			
			if(round(d) != round(d2))
			{
				System.out.print("\n\n Erreur d'un degré ! \n\n");
				break;
			}
			
			System.out.print("\n\n");
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

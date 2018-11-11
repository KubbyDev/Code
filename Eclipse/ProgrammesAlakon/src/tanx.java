
public class tanx {

	public static void main(String[] args) {
		
		for(double a = 0; a < 90; a=a+0.1) 
		{
			System.out.println( (((double) (Math.round(a*10)))/10)  +  ":    "  +  Math.tan( (a*Math.PI)/180 ) + "      Approx: " + tan( (a*Math.PI)/180 ) );
			
		}

	}
	
	public static double tan(double fAngle) {
		
	    double fASqr = fAngle*fAngle;
	    double fResult = 9.5168091e-03;
	    fResult *= fASqr;
	    fResult += 2.900525e-03;
	    fResult *= fASqr;
	    fResult += 2.45650893e-02;
	    fResult *= fASqr;
	    fResult += 5.33740603e-02;
	    fResult *= fASqr;
	    fResult += 1.333923995e-01;
	    fResult *= fASqr;
	    fResult += 3.333314036e-01;
	    fResult *= fASqr;
	    fResult += 1.0;
	    fResult *= fAngle;
	    return fResult;
	    
	}
	

}

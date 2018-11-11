
public class Divide {

	public static void main(String[] args) {
	
	int a = 83;
	int b = 4;
	int c;
	int r;
	boolean positif;
	
	c = Math.abs(a%b*2) - Math.abs(b);
	r = a/b;
	positif = ((a>0 && b>0) || (a<0 && b<0));
	
	if(c >= 0)
		if(positif)
			r++;
		else
			r--;
	
	System.out.print(r + "\n");
	
	double a2 = a;
	double b2 = b;
	System.out.print(a2/b2);
	
	}	
}

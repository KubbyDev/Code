import java.util.Scanner;

public class Factoriel {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int tmp = sc.nextInt();
		
		int res = 1;
		int tmp2 = 0;
		
		for(tmp2 = tmp; tmp2 > 1; tmp2--)
		{
			res = res * tmp2;
		}
		
		System.out.println(res);
	}

}

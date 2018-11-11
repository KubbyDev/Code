
public class acos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double borneMin = 0;
		double borneMax = 0;
		double pi = 3.14169;
		int y1 = 0;
		int y2 = 0;
		int precision = 18;
		
		for(int angle = 90; angle >= 0; angle=angle-precision)
		{
			
			//Calcul des bornes
			borneMin = ((Math.cos((angle+precision)*pi/180) * 1000) + (Math.cos(angle*pi/180) * 1000)) / 2;
			borneMax = ((Math.cos((angle-precision)*pi/180) * 1000) + (Math.cos(angle*pi/180) * 1000)) / 2 -1;
			
			y1 = round(borneMin);
			y2 = round(borneMax);
			
			System.out.print("scoreboard players set @e[tag=GetOriYFromVec,score_Tmp2_min=" + y1 + ",score_Tmp2=" + y2 + "] Theta " + angle + "\n");
			
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

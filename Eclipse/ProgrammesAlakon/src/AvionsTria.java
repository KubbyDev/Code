
public class AvionsTria {

	public static void main(String[] args) {
		
		int maxSpeed = 1000;
		int time = 100;
		
		System.out.println((int) (45/(((double) ((1030)/1000.0)-1))));
		System.out.println((int) (2.996/Math.log((double) (1030)/1000.0)));
		
		int airResistance = (int) Math.round(Math.exp(2.996/time) * 1000);
		int enginePower = (int) Math.round((Math.exp(2.996/time)-1) * maxSpeed);
		
		int speed = 0;
		
		for(int i = 0; i < 2*time; i++) {
			
			int var1 = 1000 * enginePower;
			var1 /= 1000;
			
			speed += var1;
			
			speed *= 1000;
			speed /= airResistance;
			
			System.out.println(speed);
			
		}
		
		maxSpeed = (int) (enginePower/(((double) ((airResistance)/1000.0)-1)));
		time = (int) (2.996/Math.log((double) (airResistance)/1000.0));
		
		System.out.println("airResistance = " + airResistance + "  enginePower = " + enginePower);
		System.out.println("MaxSpeed = " + maxSpeed + "   Time = " + time);
		
	}

}

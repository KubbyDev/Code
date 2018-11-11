
public class TestVitesse {

	public static void main(String[] args) {
		
		SimulationUnit[] units = new SimulationUnit[1];
		
		for(int i = 0; i < units.length; i++) {
			units[i] = new SimulationUnit();
			units[i].start();
		}
			
		
		try {
			Thread.sleep(61000);
		} catch (InterruptedException e) {e.printStackTrace();}
		
		long count = 0;
		
		for(SimulationUnit s : units)
			count += s.count;
		
		System.out.print(count);
		
	}
	
	public static class SimulationUnit extends Thread {
		
		public long count;

		public SimulationUnit() {	
		}
		
		public void run() {
			
			long time = System.currentTimeMillis();
			
			while(System.currentTimeMillis() - time < 60000) {
				
				double d = 1573;
				double d2 = Math.exp(Math.log(Math.asin(Math.exp(Math.sqrt(d)))));
				count++;
				
			}
						
		}
		
	}
	
}

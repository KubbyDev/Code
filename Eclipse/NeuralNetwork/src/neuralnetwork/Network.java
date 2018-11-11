package neuralnetwork;

public class Network {

	public static String path = "F:\\Networks";
	
	public static final String SIMPLE = "Simple";
	public static final String DEEP = "Deep";
	public static final String BIOLOGICAL = "Biological";
	
	
	// --------------------------------------------- Simulation  ---------------------------------------------
	
	
	 /**
	   * Determines what is the index of the biggest number of the array
	    *
	    * @param inputs: the inputs
	    *
	    * @return The answer of the network
	    */
	public static int getAnswer(float[] f) {

			float maxValue = 0;
			int maxIndex = 0;

			for (int i = 0; i < f.length; i++)
				if (maxValue < f[i]) {
					maxValue = f[i];
					maxIndex = i;
				}

			return maxIndex;
		}
		
	public float[] simulate(float[] inputs) {
			
			System.out.println("ATTENTION: Methode simulate() de Network appellee");
			
			return null;
		}
	
		
	// --------------------------------------------- Memory --------------------------------------------
	
		
	public static boolean exists(int slot, String networkType) {
		
		if(networkType == Network.SIMPLE || networkType == Network.DEEP || networkType == Network.BIOLOGICAL) {
			
			return Save.exists(slot, networkType);
		}
		
		System.out.println("This network type doesn't exist !\nPlease use Network.SIMPLE, Network.DEEP or Network.BIOLOGICAL");
		return false;
		
	}

}

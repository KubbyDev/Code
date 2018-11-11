package neuralnetwork;

public class Network {

	public static String path = "F:\\Networks";
	
	public static final String SIMPLE = "Simple";
	public static final String DEEP = "Deep";
	public static final String BIOLOGICAL = "Biological";
	
	
	
	// --------------------------------------------- Memory --------------------------------------------
	
	public static boolean exists(int slot, String networkType) {
		
		if(networkType == Network.SIMPLE || networkType == Network.DEEP || networkType == Network.BIOLOGICAL) {
			
			return Save.exists(slot, networkType);
		}
		
		System.out.println("This network type doesn't exist !\nPlease use Network.SIMPLE, Network.DEEP or Network.BIOLOGICAL");
		return false;
		
	}

	
}

package communication;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Transfert {
	
	Consumer<?> c;
	
	public Transfert(Consumer calculation) {
	}
	
	public ArrayList<?> calculate(ArrayList<?> data) {
		c.accept(data);
	}
	 
}

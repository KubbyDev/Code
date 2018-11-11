import java.awt.Color;
import java.util.function.Function;

public class ColorationFunction {

	public static Function<Integer, Color> Simple = (i -> new Color(0, i, 0));
	
	public static Function<Integer, Color> Trigo = (i -> { 
		double x = (double) i / 255 * Math.PI * 50;
	    return new Color((int) Math.round((Math.sin(x)+1) * 127.5), (int) Math.round((Math.cos(x)+1) * 127.5), 0);
	});
	
}

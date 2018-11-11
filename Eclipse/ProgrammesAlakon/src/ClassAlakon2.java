import java.util.ArrayList;

public class ClassAlakon2 {

	public static void main(String[] args) {
		
		for(String s : dichotomy(20, 360))
			System.out.println(s);

	}
    
	public static ArrayList<String> dichotomy(int min, int max)
    {

        ArrayList<String> commands = new ArrayList<>();

        long l = 1;

        while (l < max)
        {
            commands.add(String.valueOf(l + min));
            l = 2 * l;
        }

        return commands;
    }

}

import java.util.ArrayList;

public class ClassAlakon3
{

	public static void main(String[] args) {
	
		ArrayList<String> a = scoreInterpolation(0, 100, 2, true, 5);
		
		for(String s : a)
			System.out.println(s);
		
	}
	
    private static double linearInterp(double start, double end, double alpha)
    {
        return (end - start) * alpha + start;
    }

    private static double interp(double start, double end, int nbCommands, int commandRange, double power, boolean revert)
    {

        double alpha;
        if (nbCommands > 1)
            alpha = (double) commandRange / (double) (nbCommands - 1);
        else
            alpha = 0.5;

        if (revert)
            alpha = 1 - alpha;

        alpha = Math.pow(alpha, power);

        if (revert)
            alpha = 1 - alpha;

        return linearInterp(start, end, alpha);
    }

    private static double round(double number, int precision)
    {

        int power = (int) Math.pow(10, precision);
        number *= power;
        return ((double) Math.round(number)) / power;
    }
    
    public static ArrayList<String> interpolation(double start, double end, double power, boolean revert, int nbreDecimales, boolean noExt, int nbCommands)
    {

        ArrayList<String> commands = new ArrayList<>();

        int commandeD = 0;
        int commandeF = nbCommands - 1;
        int step = 1;

        if (noExt)
        {
            commandeD = 1;
            nbCommands = nbCommands * 2 + 1;
            commandeF = nbCommands - 1;
            step = 2;
        }

        for (int i = commandeD; i <= commandeF; i += step)
            if (nbreDecimales == 0)
                commands.add(String.valueOf((int) Math.round(interp(start, end, nbCommands, i, power, revert))));
            else
                commands.add(String.valueOf(round(interp(start, end, nbCommands, i, power, revert), nbreDecimales)));

        return commands;
    }


    public static ArrayList<String> scoreInterpolation(double start, double end, double power, boolean revert, int nbCommands)
    {

        ArrayList<String> commands = new ArrayList<>();

        for (int i = 0; i < nbCommands; i++)
            commands.add(
                    (String.valueOf(Math.round(interp(start, end, nbCommands + 1, i, power, revert)) + ((i == 0) ? 0 : 1)))
                            + ".."
                            + (String.valueOf(Math.round(interp(start, end, nbCommands + 1, i + 1, power, revert)))));

        return commands;

    }

    
}

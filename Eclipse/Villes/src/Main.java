public class Main {
	
	public static void main(String[] args){
		
		try
		{
			Ville paris = new Ville("pa","france", -50000);
			System.out.print(paris.toString());
		} 
		catch (NombreHabitantsException | NomVilleException e) {	} 
		
	}
}

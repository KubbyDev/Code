class NombreHabitantsException extends Exception{ 
  public NombreHabitantsException(int pNbre){
    System.out.println("Vous essayez d'instancier une classe Ville avec un nombre d'habitants négatif ! (" + pNbre + ")");
  }  
}
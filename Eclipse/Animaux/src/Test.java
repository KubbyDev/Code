public class Test { 
  public static void main(String[] args) {
    Animal l = new Chat("Gris bleut�", 20);
    l.boire();
    l.manger();
    l.deplacement();
    l.crier();
    System.out.println(l.toString());
  } 
}
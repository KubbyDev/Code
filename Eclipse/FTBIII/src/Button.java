
public class Button {

	public Vector pos;
	public int dam;
	public boolean isGolden = false;
	
	public Button(Vector pCoordinates, int pDamage) {
		pos = pCoordinates;
		dam = pDamage;
		
		Main.buttons.add(this);
	}
	
	public Button(Vector pCoordinates, int pDamage, boolean pIsGolden) {
		pos = pCoordinates;
		dam = pDamage;
		isGolden = pIsGolden;
		
		Main.buttons.add(this);
	}
		
}

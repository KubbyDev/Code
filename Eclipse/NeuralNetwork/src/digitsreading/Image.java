package digitsreading;

public class Image {

	private short[] pixels = new short[784];
	private byte label;
	
	public Image(short[] pPixels, byte pLabel) {
		label = pLabel;
		
		for(int i = 0; i < pPixels.length; i++)
			pixels[i] = pPixels[i];
		
	}
	
	public short[] pixels() {
		return pixels;
	}
	
	public short pixel(short index) {
		return pixels[index];
	}
	
	public byte label() {
		return label;
	}
	
	public void disp() {
		
		int x = 0;
		
		for(int i = 0; i < 784; i++) {
			
			if(x >= 784) {
				System.out.println();
				x -= 783;
			}
			
			short s = pixels[x];
			System.out.print((s != 0) ? "." : "O");
			
			x += 28;
			
		}
			
	}
	
}

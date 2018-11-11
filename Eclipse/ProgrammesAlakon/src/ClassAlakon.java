import java.io.File;
import java.io.IOException;

public class ClassAlakon {

	public static void main(String[] args) {

		File f = new File("C:\\Users\\Deve\\Desktop\\gne.txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

public class Main {

    static Thread sent;
    static Thread receive;
    static Socket socket;
	static String path = "server.properties"; //The server.properties file path
	
	public static String dlJson(String hote, int port) {
	
		StringBuilder sb = new StringBuilder();
		
        try {
            socket = new Socket(hote, port);
        } catch (UnknownHostException e1) { e1.printStackTrace(); } catch (IOException e1) { e1.printStackTrace(); }
        
        sent = new Thread(new Runnable() {

	        @Override
	        public void run() {
	        	
	            try {
	            	
	                BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	                while(true) {
	                	
	                	System.out.println("Trying to read...");
	                    String in = stdIn.readLine();
	                    System.out.println(in);
	                    sb.append(in);
	                    out.print("Try"+"\r\n");
	                    out.flush();
	                    
	                }
	            } catch (IOException e) { e.printStackTrace(); }
	
	        }
        });
        
	    sent.start();
	    try {
	        sent.join();
	    } catch (InterruptedException e) { e.printStackTrace(); }
    
	return new String(sb);	
	}
	
	public static ArrayList<Object> readConfig() {
		
		ArrayList<Object> a = new ArrayList<Object>();
		Properties p = new Properties();
		
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");
			p.load(input);
			
			a.add(p.getProperty("host"));
			a.add(p.getProperty("port"));
			
		} catch (IOException ex) { ex.printStackTrace(); } 
		finally {
			
			if (input != null)
				try {
					input.close();
				} catch (IOException e) { e.printStackTrace(); }
			
		}
		
		return a;
	}
	
	public static String getJson() {
		
		ArrayList<Object> a = readConfig();
		return dlJson((String) a.get(0), (int) a.get(1));
	}
	
	public static ArrayList<Property> getJsonArgs(String json) {
		
		ArrayList<Property> args = new ArrayList<Property>();
		
		
		
		
		
		return args;
	}
	
	public static void writeProperties(ArrayList<Property> a) {
		
		OutputStream o = null;
		Properties p = new Properties();

		try {

			o = new FileOutputStream(path);
			
			for(int i = 0; i < a.size(); i++)
				p.setProperty(a.get(i).name, a.get(i).value);
			
		} catch (IOException ex) { ex.printStackTrace(); } 
		finally {
			
			if (o != null)
				try {
					o.close();
				} catch (IOException e) { e.printStackTrace(); }
			
		}
		
	}
	
}




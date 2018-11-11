package digitsreading;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import neuralnetwork.Example;

public class ReadData {
	
	private static ArrayList<Image> readImages(String path) {
		
		ArrayList<Image> images = new ArrayList<Image>();
		
		File[] listImages = (new File(path)).listFiles();
			
		//int nbre = 0;
		
		for(File f : listImages) {
			
			if((images.size()+1) % 1000 == 0) System.out.println("Images lues: " + (images.size()+1));
			
			//nbre++;
			
			//if(nbre > 11000)
				//break;
			
			try {
				
				BufferedImage bi = ImageIO.read(f);
				
				short[] pixels = new short[784];
				
				int index = 0;
				for(int i = 0; i < bi.getHeight(); i++) {
					
					for(int y = 0; y < bi.getWidth(); y++) {

						pixels[index] = (short) toGray(bi.getRGB(i, y));
						index++;
						
					}
					
				}
					
				images.add( 
						new Image( pixels, Byte.valueOf(f.getName().substring(0, 1)) ) 
							);	
				
			} catch (IOException e) {e.printStackTrace();}
        
		}
			
        return images;
    }
	
	private static int toGray(int rgb) {
		
		int r = (rgb & 0xFF0000) >> 16;
		int g = (rgb & 0xFF00) >> 8;
		int b = (rgb & 0xFF);
		
		if(r <= 100 || g <= 100 || b <= 100) 
			return 0;
		else 
			return 255;
	}
	
	public static void convert(String path, String imagesName, String labelsName, String newFolder) {
		
        FileInputStream inImage = null;
        FileInputStream inLabel = null;

        String inputImagePath = path + imagesName;
        String inputLabelPath = path + labelsName;

        String outputPath = path + newFolder;

        int[] hashMap = new int[10]; 

        try {
            inImage = new FileInputStream(inputImagePath);
            inLabel = new FileInputStream(inputLabelPath);

            int magicNumberImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfRows  = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfColumns = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());

            int magicNumberLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());
            int numberOfLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());

            BufferedImage image = new BufferedImage(numberOfColumns, numberOfRows, BufferedImage.TYPE_INT_ARGB);
            int numberOfPixels = numberOfRows * numberOfColumns;
            int[] imgPixels = new int[numberOfPixels];

            for(int i = 0; i < numberOfImages; i++) {

                if(i % 100 == 0) {System.out.println("Number of images extracted: " + i);}

                for(int p = 0; p < numberOfPixels; p++) {
                    int gray = 255 - inImage.read();
                    imgPixels[p] = 0xFF000000 | (gray<<16) | (gray<<8) | gray;
                }

                image.setRGB(0, 0, numberOfColumns, numberOfRows, imgPixels, 0, numberOfColumns);

                int label = inLabel.read();

                hashMap[label]++;
                File outputfile = new File(outputPath + label + "_0" + hashMap[label] + ".png");

                ImageIO.write(image, "png", outputfile);
            }

        } catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();} 
        finally {
            if (inImage != null) {
                try {
                    inImage.close();
                } catch (IOException e) {e.printStackTrace();}
            }
            if (inLabel != null) {
                try {
                    inLabel.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
    }
	 
	public static ArrayList<Image> trainImages() {

		return readImages("C:\\NeuralNetworks\\BanqueImages\\Training\\");
	}
	
	public static ArrayList<Image> testImages() {
		
		return readImages("C:\\NeuralNetworks\\BanqueImages\\Testing\\");
	}

	@SuppressWarnings("unused")
	private static void disp(short[] s) {
		
		for(short d : s)
			System.out.print(d + " ");
		
	}

	private static ArrayList<Example> toExamples(ArrayList<Image> i) {
		
		ArrayList<Example> e = new ArrayList<Example>();
		
		for(Image img : i)
			e.add(new Example(getInputs(img), img.label()));
		
		return e;
	}
	
	private static float[] getInputs(Image img) {
		
		short[] pixels = img.pixels();
		float[] f = new float[pixels.length];
		
		for(int i = 0; i < pixels.length; i++)
			f[i] = pixels[i];
		
		return f;
	}
	
	public static ArrayList<Example> trainExamples() {
		
		return toExamples(trainImages());
	}
	
	public static ArrayList<Example> testExamples() {
		
		return toExamples(testImages());
	}
	
}

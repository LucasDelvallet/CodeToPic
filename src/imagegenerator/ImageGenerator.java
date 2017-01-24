package imagegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import reader.CodeToBytes;

public class ImageGenerator {
	private CodeToBytes codeCovertor;
	private String imageName;

	public ImageGenerator(CodeToBytes codeCovertor, String imageName) {
		this.codeCovertor = codeCovertor;
		this.imageName = imageName;
	}

	public void generate() throws IOException {
		int height, width, reste;
		byte[] data = codeCovertor.getBytes();
		
		System.out.println(data.length);
		
		height = data.length / 6;
		width = data.length / 6;
		reste = data.length % 6;
		
		BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
		image.getRaster().setPixels(0, 0, 35197, 35198, generateImage(300, 300, data));
		ImageIO.write(image, "BMP", new File(imageName + ".bmp"));
	}
	
	public int[] generateImage(int height, int width, byte[] data) {
		int[] flattenedData = new int[data.length];
		
		for(int i = 0; i < data.length; i++) {
			flattenedData[i] = data[i];
		}
		
		return flattenedData;
	}
}

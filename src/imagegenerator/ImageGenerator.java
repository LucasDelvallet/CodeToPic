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
		byte[] data = codeCovertor.getBytes();
		int imageSize = getImageSize(data.length);
		
		BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
		image.getRaster().setPixels(0, 0, imageSize, imageSize, getRVGData(imageSize*imageSize*3, data));
		ImageIO.write(image, "BMP", new File(imageName + ".bmp"));
	}
	
	private int[] getRVGData(int dataSize, byte[] data) {
		int[] flattenedData = new int[dataSize];
		
		for(int i = 0; i < dataSize; i++) {
			if(i + 1 > data.length) {
				flattenedData[i] = 0;
			} else {
				flattenedData[i] = data[i];
			}
		}
		
		return flattenedData;
	}
	
	private int getImageSize(int nbByte) {
		int nbRVB = (nbByte / 3) + (nbByte % 3 > 0 ? 1 : 0);
		int imageSize = (int) Math.sqrt(nbRVB);
		if((imageSize * imageSize) < nbRVB) {
			imageSize ++;
		}
		return imageSize;
	}
}

package writer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import reader.ToBytes;

public class ImageGenerator extends Generator {
	// One RGB Pixel equal 3 bytes
	public static final int HEADER_SIZE = 6;

	public ImageGenerator(ToBytes codeCovertor, String imagePath) {
		super(codeCovertor, imagePath);
	}

	@Override
	public void generate(Extension e) throws IOException {
		byte[] data = convertor.getBytes();
		int imageSize = getImageSize(data.length + HEADER_SIZE);
		
		BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
		image.getRaster().setPixels(0, 0, imageSize, imageSize, getRVGData(imageSize*imageSize*3, data));
		ImageIO.write(image, e.getFormatName(), new File(targetFolder + fileName + e.getExtension()));
	}
	
	private int[] getRVGData(int pixelDataSize, byte[] data) {
		int[] flattenedData = new int[pixelDataSize];
		
		flattenedData[0] = (byte) (data.length >> 24);
		flattenedData[1] = (byte) (data.length >> 16);
		flattenedData[2] = (byte) (data.length >> 8);
		flattenedData[3] = (byte) data.length;
		flattenedData[4] = 0;
		flattenedData[5] = 0;
		
		for(int i = HEADER_SIZE; i < pixelDataSize; i++) {
			if(i + 1 > data.length + HEADER_SIZE) {
				flattenedData[i] = 0;
			} else {
				flattenedData[i] = data[i - HEADER_SIZE];
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

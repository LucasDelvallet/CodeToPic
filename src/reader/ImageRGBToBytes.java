package reader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.imageio.ImageIO;

import writer.ImageGenerator;

/**
 * Convertie une image d'au moins 4 sur 4 pixels de données
 * @author Mickael
 */
public class ImageRGBToBytes extends ToBytes {

	public ImageRGBToBytes(String path) {
		super(path);
	}

	@Override
	public byte[] getBytes() throws IOException {
		BufferedImage img = ImageIO.read(new File(path));
		int rgbIdx = 0, dataSize;
		dataSize = getDataSizeFromHeader(img);
		byte[] res = new byte[img.getHeight()*img.getWidth()*3];
		
		for(int h = 0; h < img.getHeight(); h++) {
			for(int w = 0; w < img.getWidth(); w++) {
				convertPixelToByte(rgbIdx, img.getRGB(w, h), res);
				rgbIdx += 3;
			}
		}
		
		return Arrays.copyOfRange(res, ImageGenerator.HEADER_SIZE, ImageGenerator.HEADER_SIZE + dataSize);
	}
	
	private int getDataSizeFromHeader(BufferedImage img) {
		int pixel0, pixel1;
		byte[] pixel0ToByte, pixel1ToByte, headerData;
		pixel0 = img.getRGB(0, 0);
		pixel0ToByte = ByteBuffer.allocate(4).putInt(pixel0).array();
		pixel1 = img.getRGB(1, 0);
		pixel1ToByte = ByteBuffer.allocate(4).putInt(pixel1).array();
		headerData = new byte[4];
		headerData[3] = pixel1ToByte[1];
		headerData[2] = pixel0ToByte[3];
		headerData[1] = pixel0ToByte[2];
		headerData[0] = pixel0ToByte[1];
		return ByteBuffer.wrap(headerData).getInt();
	}
	
	private void convertPixelToByte(int startIndex, int pixel, byte[] toSave) {
		toSave[startIndex] = (byte) ((pixel >> 16) & 0xff);
		toSave[startIndex + 1] = (byte) ((pixel >> 8) & 0xff);
		toSave[startIndex + 2] = (byte) ((pixel) & 0xff);
	}

}

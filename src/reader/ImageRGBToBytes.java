package reader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageRGBToBytes extends ToBytes {

	public ImageRGBToBytes(String path) {
		super(path);
	}

	@Override
	public byte[] getBytes() throws IOException {
		BufferedImage img = ImageIO.read(new File(path));
		byte[] res = new byte[img.getHeight()*img.getWidth()*3];
		int rgbIdx = 0;
		
		for(int h = 0; h < img.getHeight(); h++) {
			for(int w = 0; w < img.getWidth(); w++) {
				convertPixelToByte(rgbIdx, img.getRGB(w, h), res);
				rgbIdx += 3;
			}
		}
		
		return res;
	}
	
	private void convertPixelToByte(int startIndex, int pixel, byte[] toSave) {
		toSave[startIndex] = (byte) ((pixel >> 16) & 0xff);
		toSave[startIndex + 1] = (byte) ((pixel >> 8) & 0xff);
		toSave[startIndex + 2] = (byte) ((pixel) & 0xff);
	}

}

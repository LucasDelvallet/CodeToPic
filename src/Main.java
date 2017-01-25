import java.io.IOException;

import reader.FileToBytes;
import reader.ImageRGBToBytes;
import reader.ToBytes;
import writer.FileGenerator;
import writer.Generator;
import writer.Generator.Extension;
import writer.ImageGenerator;

public class Main {

	public static void main(String[] args) {
		ToBytes coder = new FileToBytes("ressources/test.txt");
		Generator generator = new ImageGenerator(coder, "ressources/generated/image/test");
		try {
			generator.generate(Extension.BMP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageRGBToBytes convertor = new ImageRGBToBytes("ressources/generated/image/test.bmp");
		generator = new FileGenerator(convertor, "ressources/generated/code/test");
		try {
			generator.generate(Extension.TXT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

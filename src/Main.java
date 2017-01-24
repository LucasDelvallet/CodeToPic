import java.io.IOException;

import imagegenerator.ImageGenerator;
import reader.CodeToBytes;
import reader.JarToBytes;

public class Main {

	public static void main(String[] args) {
		CodeToBytes coder = new JarToBytes("ressources/com.google.gson_2.2.4.v201311231704.jar");
		ImageGenerator generator = new ImageGenerator(coder, "ressources/generated/1");
		try {
			generator.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

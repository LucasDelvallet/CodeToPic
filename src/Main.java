import java.io.IOException;

import reader.FileToBytes;
import reader.ToBytes;
import writer.hierarchy.CircleViewGenerator;
import writer.hierarchy.ClassCircleViewGenerator;

public class Main {

	public static void main(String[] args) throws Exception {
		ToBytes coder = new FileToBytes("ressources/Test.java");
		CircleViewGenerator generator = new ClassCircleViewGenerator(coder, "target/image");
		try {
			generator.generate(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

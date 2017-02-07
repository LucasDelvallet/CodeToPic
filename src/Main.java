import java.io.IOException;

import reader.FileToBytes;
import reader.ToBytes;
import writer.hierarchy.CircleViewGenerator;
import writer.hierarchy.GeneratorBuilder;

public class Main {

	public static void main(String[] args) throws Exception {
		String path = "src/";
		ToBytes coder = new FileToBytes(path);
		CircleViewGenerator generator = GeneratorBuilder.getProjectGenerator(path, coder, "target/image");
		try {
			generator.generate(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

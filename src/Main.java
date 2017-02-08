import java.io.IOException;

import hierarchyView.GeneratorBuilder;
import hierarchyView.generator.CircleViewGenerator;

public class Main {

	public static void main(String[] args) throws Exception {
		String path = "src/";
		CircleViewGenerator generator = GeneratorBuilder.getProjectGenerator(path, "target/image");
		try {
			generator.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

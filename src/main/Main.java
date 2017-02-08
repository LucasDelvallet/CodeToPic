package main;
import java.io.IOException;

import hierarchyView.GeneratorBuilder;
import hierarchyView.generator.CircleViewGenerator;

public class Main {

	public static void main(String[] args) {
		String path = "src/";
		try {
			CircleViewGenerator generator = GeneratorBuilder.getProjectGenerator(path, "target/image");
			generator.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

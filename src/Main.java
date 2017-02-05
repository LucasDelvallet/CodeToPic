import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import reader.FileToBytes;
import reader.ToBytes;
import writer.hierarchy.CircleViewGenerator;
import writer.hierarchy.GeneratorBuilder;

public class Main {

	public static void main(String[] args) throws Exception {
		String path = "ressources/Test.java";
		FileInputStream in = new FileInputStream(path);
		CompilationUnit cu = JavaParser.parse(in);
		ToBytes coder = new FileToBytes(path);
		CircleViewGenerator generator = GeneratorBuilder.getFileGenerator(path, coder, "target/image", cu);
		try {
			generator.generate(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

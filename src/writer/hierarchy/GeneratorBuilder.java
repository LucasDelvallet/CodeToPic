package writer.hierarchy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import reader.FileToBytes;
import reader.ToBytes;
import writer.hierarchy.visitors.ClassLevelVisitor;
import writer.hierarchy.visitors.metrics.ClassMetric;
import writer.hierarchy.visitors.metrics.MethodMetric;

public class GeneratorBuilder {

	public static CircleViewGenerator getProjectGenerator(String sourcePath, ToBytes convertor, String targetFolder) throws FileNotFoundException {
		File file = new File(sourcePath);
		return getProjectGenerator(file, convertor, targetFolder);
	}

	private static CircleViewGenerator getProjectGenerator(File file, ToBytes convertor, String targetFolder)
			throws FileNotFoundException {
		if (file.isDirectory()) {
			CircleViewGenerator generator = new FolderCircleViewGenerator(new FileToBytes(file.getAbsolutePath()), targetFolder);
			File[] childs = file.listFiles();
			
			for(File child : childs) {
				generator.addChild(getProjectGenerator(child, convertor, targetFolder));
			}
			
			return generator;
		} else {
			FileInputStream in = new FileInputStream(file.getAbsolutePath());
			CompilationUnit cu = JavaParser.parse(in);
			return getFileGenerator(file.getAbsolutePath(), convertor, targetFolder, cu);
		}
	}

	private static CircleViewGenerator getFileGenerator(String filePath, ToBytes convertor, String targetFolder,
			CompilationUnit cu) {
		ClassLevelVisitor visitor = new ClassLevelVisitor(cu);
		visitor.init();
		ClassMetric metric = visitor.getClassMetric();

		CircleViewGenerator fileGenerator = new ClassCircleViewGenerator(convertor, targetFolder, metric);

		for (MethodMetric methodMetric : metric.getMethodsMetric()) {
			MethodeCircleViewGenerator mGenerator = new MethodeCircleViewGenerator(convertor, targetFolder,
					methodMetric);
			fileGenerator.addChild(mGenerator);
		}

		return fileGenerator;
	}
}

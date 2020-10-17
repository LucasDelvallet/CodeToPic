package hierarchyView.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import hierarchyView.visitors.ClassLevelVisitor;
import hierarchyView.visitors.metrics.ClassMetric;
import hierarchyView.visitors.metrics.MethodMetric;

public class GeneratorBuilder {

	public static CircleViewGenerator getProjectGenerator(String sourcePath, String targetFolder) throws FileNotFoundException {
		File file = new File(sourcePath);
		return getProjectGenerator(file, targetFolder);
	}

	private static CircleViewGenerator getProjectGenerator(File file, String targetFolder) throws FileNotFoundException {
		if (file.isDirectory()) {
			return getFolderGenerator(file, targetFolder);
		} else {
			return getJavaFileGenerator(file, targetFolder);
		}
	}
	
	private static FolderCircleViewGenerator getFolderGenerator(File file, String targetFolder) throws FileNotFoundException {
		FolderCircleViewGenerator generator = new FolderCircleViewGenerator(file.getAbsolutePath(), targetFolder);
		File[] childs = file.listFiles();

		for (File child : childs) {
			CircleViewGenerator circleViewGenerator = getProjectGenerator(child, targetFolder);
			if (circleViewGenerator != null)
				generator.addChild(circleViewGenerator);
		}

		return generator;
	}
	
	private static ClassCircleViewGenerator getJavaFileGenerator(File file, String targetFolder) throws FileNotFoundException {
		if (file.getName().endsWith(".java")) {
			return getFileGenerator(file.getAbsolutePath(), targetFolder);
		} else {
			return null;
		}
	}

	private static ClassCircleViewGenerator getFileGenerator(String filePath, String targetFolder) throws FileNotFoundException {
		FileInputStream in = new FileInputStream(filePath);
		CompilationUnit cu = JavaParser.parse(in);
		ClassLevelVisitor visitor = new ClassLevelVisitor(cu);
		visitor.init();
		ClassMetric metric = visitor.getClassMetric();

		ClassCircleViewGenerator fileGenerator = new ClassCircleViewGenerator(filePath, targetFolder, metric);

		for (MethodMetric methodMetric : metric.getMethodsMetric()) {
			MethodeCircleViewGenerator mGenerator = new MethodeCircleViewGenerator(filePath, targetFolder,
					methodMetric);
			fileGenerator.addChild(mGenerator);
		}

		return fileGenerator;
	}
}

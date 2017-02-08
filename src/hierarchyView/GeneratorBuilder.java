package hierarchyView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import hierarchyView.generator.CircleViewGenerator;
import hierarchyView.generator.ClassCircleViewGenerator;
import hierarchyView.generator.FolderCircleViewGenerator;
import hierarchyView.generator.MethodeCircleViewGenerator;
import hierarchyView.visitors.ClassLevelVisitor;
import hierarchyView.visitors.metrics.ClassMetric;
import hierarchyView.visitors.metrics.MethodMetric;

public class GeneratorBuilder {

	public static CircleViewGenerator getProjectGenerator(String sourcePath, String targetFolder) throws FileNotFoundException {
		File file = new File(sourcePath);
		return getProjectGenerator(file, targetFolder);
	}

	private static CircleViewGenerator getProjectGenerator(File file, String targetFolder)
			throws FileNotFoundException {
		if (file.isDirectory()) {
			CircleViewGenerator generator = new FolderCircleViewGenerator(file.getAbsolutePath(), targetFolder);
			File[] childs = file.listFiles();
			
			for(File child : childs) {
				CircleViewGenerator circleViewGenerator = getProjectGenerator(child, targetFolder);
				if(circleViewGenerator != null) generator.addChild(circleViewGenerator);
			}
			
			return generator;
		} else {
			if(file.getName().contains(".java")) {
				FileInputStream in = new FileInputStream(file.getAbsolutePath());
				CompilationUnit cu = JavaParser.parse(in);
				return getFileGenerator(file.getAbsolutePath(), targetFolder, cu);
			} else {
				return null;
			}
			
		}
	}

	private static CircleViewGenerator getFileGenerator(String filePath, String targetFolder,
			CompilationUnit cu) {
		ClassLevelVisitor visitor = new ClassLevelVisitor(cu);
		visitor.init();
		ClassMetric metric = visitor.getClassMetric();

		CircleViewGenerator fileGenerator = new ClassCircleViewGenerator(filePath, targetFolder, metric);

		for (MethodMetric methodMetric : metric.getMethodsMetric()) {
			MethodeCircleViewGenerator mGenerator = new MethodeCircleViewGenerator(filePath, targetFolder,
					methodMetric);
			fileGenerator.addChild(mGenerator);
		}

		return fileGenerator;
	}
}

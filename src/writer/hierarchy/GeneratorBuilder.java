package writer.hierarchy;

import com.github.javaparser.ast.CompilationUnit;

import reader.ToBytes;
import writer.hierarchy.visitors.ClassLevelVisitor;
import writer.hierarchy.visitors.metrics.ClassMetric;
import writer.hierarchy.visitors.metrics.MethodMetric;

public class GeneratorBuilder {

	public static CircleViewGenerator getFileGenerator(String filePath, ToBytes convertor, String targetFolder, CompilationUnit cu) {
		ClassLevelVisitor visitor = new ClassLevelVisitor(cu);
		visitor.init();
		ClassMetric metric = visitor.getClassMetric();
		
		CircleViewGenerator fileGenerator = new ClassCircleViewGenerator(convertor, targetFolder, metric);
		
		for(MethodMetric methodMetric : metric.getMethodsMetric()) {
			MethodeCircleViewGenerator mGenerator = new MethodeCircleViewGenerator(convertor, targetFolder, methodMetric);
			fileGenerator.addChild(mGenerator);
		}
		
		return fileGenerator;
	}
}

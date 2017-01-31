package writer.hierarchy.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import writer.hierarchy.metrics.ClassMetric;

public class ClassVisitor extends VoidVisitorAdapter<Void> {
	private ClassMetric metric;

	@Override
	public void visit(ClassOrInterfaceDeclaration arg0, Void arg1) {
		metric = new ClassMetric(arg0.getEnd().get().line, arg0.getName().toString());
		super.visit(arg0, arg1);
	}
	
	public ClassMetric getMetric() {
		return metric;
	}
	
}

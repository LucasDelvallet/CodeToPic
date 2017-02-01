package writer.hierarchy.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import writer.hierarchy.metrics.ClassMetric;

public class ClassVisitor extends VoidVisitorAdapter<Void> {
	private ClassMetric metric;
	private int numberOfVariableDeclaration, nbOfMethode;
	
	public ClassVisitor() {
		metric = new ClassMetric();
		numberOfVariableDeclaration = 0;
		nbOfMethode = 0;
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration arg0, Void arg1) {
		metric.setNumberOfLine(arg0.getEnd().get().line);
		metric.setName(arg0.getName().toString());
		super.visit(arg0, arg1);
	}
	
	@Override
	public void visit(VariableDeclarator n, Void arg) {
		numberOfVariableDeclaration ++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(MethodDeclaration arg0, Void arg1) {
		nbOfMethode ++;
		super.visit(arg0, arg1);
	}
	
	public ClassMetric getMetric() {
		metric.setNumberOfVariableDeclaration(numberOfVariableDeclaration);
		metric.setNumberOfMethode(nbOfMethode);
		return metric;
	}
	
}

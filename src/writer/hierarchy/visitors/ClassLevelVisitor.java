package writer.hierarchy.visitors;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import writer.hierarchy.visitors.metrics.ClassMetric;
import writer.hierarchy.visitors.metrics.MethodMetric;

public class ClassLevelVisitor extends LevelVisitor {
	private int numberOfLine, numberOfVariableDeclaration, numberOfMethode;
	private String name;
	private List<MethodMetric> methodsMetric;
	
	public ClassLevelVisitor(CompilationUnit cu) {
		super(cu);
		methodsMetric = new ArrayList<MethodMetric>();
		numberOfVariableDeclaration = 0;
		numberOfMethode = 0;
		numberOfLine = 0;
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration arg0, Void arg1) {
		numberOfLine = arg0.getEnd().get().line;
		name = arg0.getName().toString();
		super.visit(arg0, arg1);
	}
	
	@Override
	public void visit(VariableDeclarator n, Void arg) {
		numberOfVariableDeclaration ++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(MethodDeclaration arg0, Void arg1) {
		numberOfMethode ++;
		MethodMetric mMetric = new MethodMetric();
		mMetric.setNumberOfLine(arg0.getEnd().get().line - arg0.getBegin().get().line);
		methodsMetric.add(mMetric);
		super.visit(arg0, arg1);
	}
	
	public ClassMetric getClassMetric() {
		ClassMetric metric = new ClassMetric();
		metric.setName(name);
		metric.setNumberOfLine(numberOfLine);
		metric.setNumberOfMethode(numberOfMethode);
		metric.setNumberOfVariableDeclaration(numberOfVariableDeclaration);
		metric.setMethodsMetric(methodsMetric);
		return metric;
	}
}

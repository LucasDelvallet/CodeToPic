package writer.hierarchy.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

public class ClassLevelVisitor extends LevelVisitor {
	private int numberOfLine, numberOfVariableDeclaration, numberOfMethode;
	private String name;
	
	public ClassLevelVisitor(CompilationUnit cu) {
		super(cu);
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
		super.visit(arg0, arg1);
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumberOfVariableDeclaration() {
		return numberOfVariableDeclaration;
	}
	
	public int getNumberOfLine() {
		return numberOfLine;
	}
	
	public int getNumberOfMethode() {
		return numberOfMethode;
	}
}

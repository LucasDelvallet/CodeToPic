package writer.hierarchy.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class MethodeVisitor extends LevelVisitor {
	
	public MethodeVisitor(CompilationUnit cu) {
		super(cu);
	}

	@Override
	public void visit(MethodDeclaration arg0, Void arg1) {
		super.visit(arg0, arg1);
	}
	
	@Override
	public void visit(ConstructorDeclaration arg0, Void arg1) {
		super.visit(arg0, arg1);
	}
}

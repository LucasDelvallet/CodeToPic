package writer.hierarchy.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodeVisitor extends VoidVisitorAdapter<Void> {
	
	@Override
	public void visit(MethodDeclaration arg0, Void arg1) {
		super.visit(arg0, arg1);
	}
	
	@Override
	public void visit(ConstructorDeclaration arg0, Void arg1) {
		super.visit(arg0, arg1);
	}
}

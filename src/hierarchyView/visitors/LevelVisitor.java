package hierarchyView.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LevelVisitor extends VoidVisitorAdapter<Void> {
	private CompilationUnit cu;
	
	public LevelVisitor(CompilationUnit cu) {
		this.cu = cu;
	}
	
	public void init() {
		visit(cu, null);
	}
}

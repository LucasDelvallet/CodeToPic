package hierarchyView.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LevelVisitor extends VoidVisitorAdapter<Void> {
	private CompilationUnit compilationUnit;
	
	public LevelVisitor(CompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
	}
	
	public void init() {
		visit(compilationUnit, null);
	}
}

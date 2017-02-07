package writer.hierarchy.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

import writer.hierarchy.visitors.metrics.ClassMetric;
import writer.hierarchy.visitors.metrics.MethodMetric;

public class ClassLevelVisitor extends LevelVisitor {
	private int numberOfLine, numberOfVariableDeclaration, numberOfMethode, cyclomaticMethodCounter;
	private String name;
	private List<MethodMetric> methodsMetric;
	
	public ClassLevelVisitor(CompilationUnit cu) {
		super(cu);
		methodsMetric = new ArrayList<MethodMetric>();
		numberOfVariableDeclaration = 0;
		numberOfMethode = 0;
		numberOfLine = 0;
		cyclomaticMethodCounter = 0;
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
		cyclomaticMethodCounter = 0;
		numberOfMethode ++;
		MethodMetric mMetric = new MethodMetric();
		mMetric.setNumberOfLine(arg0.getEnd().get().line - arg0.getBegin().get().line + 1);
		mMetric.setNbOfParameters(arg0.getParameters().size());
		super.visit(arg0, arg1);
		mMetric.setCyclomaticComplexity(cyclomaticMethodCounter);		
		methodsMetric.add(mMetric);
	}
	
	@Override
	public void visit(CatchClause n, Void arg) {
		cyclomaticMethodCounter++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(DoStmt n, Void arg) {
		cyclomaticMethodCounter++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(ForStmt arg0, Void arg1) {
		cyclomaticMethodCounter++;
		super.visit(arg0, arg1);
	}
	
	@Override
	public void visit(ForeachStmt n, Void arg) {
		cyclomaticMethodCounter++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(BreakStmt n, Void arg) {
		cyclomaticMethodCounter++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(ContinueStmt n, Void arg) {
		cyclomaticMethodCounter++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(ThrowStmt n, Void arg) {
		cyclomaticMethodCounter++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(WhileStmt n, Void arg) {
		cyclomaticMethodCounter++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(SwitchStmt arg0, Void arg1) {
		cyclomaticMethodCounter++;
		super.visit(arg0, arg1);
	}
	
	@Override
    public void visit(IfStmt n, Void arg) {
		ifCyclomaticCount(n);
        super.visit(n, arg);
    }

    private void ifCyclomaticCount(IfStmt n) {
    	cyclomaticMethodCounter++;
        Optional<Statement> elseStmt = n.getElseStmt();
        if (elseStmt.isPresent())
        {
            if ( IfStmt.class.isAssignableFrom(elseStmt.getClass())) {
            	ifCyclomaticCount((IfStmt) elseStmt.get());
            } else {
            	cyclomaticMethodCounter++;
            }
        }
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

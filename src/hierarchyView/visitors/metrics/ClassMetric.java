package hierarchyView.visitors.metrics;

import java.util.ArrayList;
import java.util.List;

public class ClassMetric {
	private List<MethodMetric> methodsMetric;
	private int numberOfLine, numberOfVariableDeclaration, numberOfMethode;
	private String name;
	
	public ClassMetric() {
		methodsMetric = new ArrayList<MethodMetric>();
		name = new String();
	}
	
	public List<MethodMetric> getMethodsMetric() {
		return methodsMetric;
	}
	
	public void addMethodsMetric(MethodMetric methodsMetric) {
		this.methodsMetric.add(methodsMetric);
	}
	
	public void setMethodsMetric(List<MethodMetric> methodsMetric) {
		this.methodsMetric = methodsMetric;
	}
	
	public int getNumberOfLine() {
		return numberOfLine;
	}
	
	public void setNumberOfLine(int numberOfLine) {
		this.numberOfLine = numberOfLine;
	}
	
	public int getNumberOfVariableDeclaration() {
		return numberOfVariableDeclaration;
	}
	
	public void setNumberOfVariableDeclaration(int numberOfVariableDeclaration) {
		this.numberOfVariableDeclaration = numberOfVariableDeclaration;
	}
	
	public int getNumberOfMethode() {
		return numberOfMethode;
	}
	
	public void setNumberOfMethode(int numberOfMethode) {
		this.numberOfMethode = numberOfMethode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}

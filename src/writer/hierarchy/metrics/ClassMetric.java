package writer.hierarchy.metrics;

public class ClassMetric {
	private int numberOfLine, numberOfVariableDeclaration, numberOfMethode;
	private String name;
	
	public ClassMetric() {
		this(0, "", 0);
	}
	
	public ClassMetric(int numberOfLine, String name, int numberOfVariableDeclaration) {
		this.numberOfLine = numberOfLine;
		this.name = name;
		this.numberOfVariableDeclaration = numberOfVariableDeclaration;
	}

	public int getNumberOfLine() {
		return numberOfLine;
	}

	public void setNumberOfLine(int numberOfLine) {
		this.numberOfLine = numberOfLine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}

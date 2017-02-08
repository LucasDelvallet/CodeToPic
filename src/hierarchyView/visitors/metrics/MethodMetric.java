package hierarchyView.visitors.metrics;

public class MethodMetric {
	String name;
	int numberOfLine, cyclomaticComplexity, nbOfParameters;

	public int getNbOfParameters() {
		return nbOfParameters;
	}

	public void setNbOfParameters(int nbOfParameters) {
		this.nbOfParameters = nbOfParameters;
	}

	public int getNumberOfLine() {
		return numberOfLine;
	}

	public void setNumberOfLine(int numberOfLine) {
		this.numberOfLine = numberOfLine;
	}
	
	public void setCyclomaticComplexity(int cyclomaticComplexity) {
		this.cyclomaticComplexity = cyclomaticComplexity;
	}
	
	public int getCyclomaticComplexity() {
		return this.cyclomaticComplexity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package writer.hierarchy.visitors.metrics;

public class MethodMetric {
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
}

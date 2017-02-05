package writer.hierarchy.visitors.metrics;

public class MethodMetric {
	int numberOfLine, cyclomaticComplexity;

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

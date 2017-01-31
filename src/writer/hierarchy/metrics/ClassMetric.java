package writer.hierarchy.metrics;

public class ClassMetric {
	private int numberOfLine;
	private String name;
	
	public ClassMetric() {
		this(0, "");
	}
	
	public ClassMetric(int numberOfLine, String name) {
		this.numberOfLine = numberOfLine;
		this.name = name;
	}
	
	public int getNumberOfLines() {
		return numberOfLine;
	}
	
	public String getName() {
		return name;
	}
}

package hierarchyView.generator;

import hierarchyView.util.RGB;
import hierarchyView.visitors.metrics.MethodMetric;
import java.lang.*;

public class MethodeCircleViewGenerator extends CircleViewGenerator {
	private MethodMetric metric;

	public MethodeCircleViewGenerator(String originFile, String targetFolder, MethodMetric metric) {
		super(originFile, targetFolder);
		this.metric = metric;
	}

	@Override
	public RGB getStrokeColor() {
		int red = (int)(metric.getNbOfParameters() * 42.5f);
		red = Math.min(red, 255);
		return new RGB(red, 0, 255-red);
	}

	/**
	 * Cyclomatic Complexity should be between 0 and 10
	 */
	@Override
	public RGB getBackgroundColor() {
		int red = (int)(metric.getCyclomaticComplexity() * 25.5f);
		if(red > 255) {
			red = 255;
		}
		return new RGB(red, 255-red, 0);
	}

	/**
	* This method returns the number of lines.
	*/
	@Override
	public int getNumberOfLines() {
		return metric.getNumberOfLine();
	}

	@Override
	public String getName() {
		return metric.getName();
	}

}

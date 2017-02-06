package writer.hierarchy;

import reader.ToBytes;
import util.RGB;
import writer.hierarchy.visitors.metrics.MethodMetric;

public class MethodeCircleViewGenerator extends CircleViewGenerator implements IHierarchyView {
	private MethodMetric metric;

	public MethodeCircleViewGenerator(ToBytes convertor, String targetFolder, MethodMetric metric) {
		super(convertor, targetFolder);
		this.metric = metric;
	}

	@Override
	public RGB getStrokeColor() {
		int red = (int)(metric.getNbOfParameters() * 42.5f);
		if(red > 255) {
			red = 255;
		}
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

	@Override
	public int getNumberOfLines() {
		return metric.getNumberOfLine();
	}

}

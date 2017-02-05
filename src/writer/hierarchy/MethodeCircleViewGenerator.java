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
	public RGB getNameColor() {
		return new RGB(128, 128, 128);
	}

	@Override
	public RGB getCodeColor() {
		return new RGB(255, 0, 255);
	}

	@Override
	public int getNumberOfLines() {
		return metric.getNumberOfLine();
	}

}

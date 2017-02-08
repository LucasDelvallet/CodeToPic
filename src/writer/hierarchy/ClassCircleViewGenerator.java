package writer.hierarchy;

import reader.ToBytes;
import util.RGB;
import writer.hierarchy.visitors.metrics.ClassMetric;

public class ClassCircleViewGenerator extends CircleViewGenerator implements IHierarchyView {
	private ClassMetric metric;

	public ClassCircleViewGenerator(ToBytes convertor, String targetFolder, ClassMetric metric) {
		super(convertor, targetFolder);
		this.metric = metric;
	}

	@Override
	public RGB getStrokeColor() {
		int red = 0, green = 0, blue = 0, childrenSize = children.size();

		for (IHierarchyView child : children) {
			RGB color = child.getStrokeColor();
			red += color.getRed();
			green += color.getGreen();
			blue += color.getBlue();
		}

		return new RGB((int) red / childrenSize, (int) green / childrenSize, (int) blue / childrenSize);
	}

	@Override
	public RGB getBackgroundColor() {
		int red = 0, green = 0, blue = 0, childrenSize = children.size();

		for (IHierarchyView child : children) {
			RGB color = child.getBackgroundColor();
			red += color.getRed();
			green += color.getGreen();
			blue += color.getBlue();
		}

		return new RGB((int) red / childrenSize, (int) green / childrenSize, (int) blue / childrenSize);
	}

	@Override
	public int getNumberOfLines() {
		int childrenSize = 0;
		for (IHierarchyView child : children) {
			childrenSize += child.getNumberOfLines();
		}
		return metric.getNumberOfLine() - childrenSize;
	}

	@Override
	public String getName() {
		return metric.getName();
	}

}

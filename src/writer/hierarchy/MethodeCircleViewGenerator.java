package writer.hierarchy;

import reader.ToBytes;
import util.RGB;

public class MethodeCircleViewGenerator extends CircleViewGenerator implements IHierarchyView {

	public MethodeCircleViewGenerator(ToBytes convertor, String targetFolder) {
		super(convertor, targetFolder);
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
		return 0;
	}

}

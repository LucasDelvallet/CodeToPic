package writer.hierarchy;

import reader.ToBytes;
import util.RGB;

public class FolderCircleViewGenerator extends CircleViewGenerator implements IHierarchyView {

	public FolderCircleViewGenerator(ToBytes convertor, String targetFolder) {
		super(convertor, targetFolder);
	}

	@Override
	public RGB getStrokeColor() {
		return new RGB(0, 255, 0);
	}

	@Override
	public RGB getBackgroundColor() {
		return new RGB(255, 255, 255);
	}

	@Override
	public int getNumberOfLines() {
		return 0;
	}

	@Override
	public String getName() {
		return fileName;
	}

}

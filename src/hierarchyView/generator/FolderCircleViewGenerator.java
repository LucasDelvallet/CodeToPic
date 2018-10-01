package hierarchyView.generator;

import hierarchyView.RGB;

public class FolderCircleViewGenerator extends CircleViewGenerator {

	public FolderCircleViewGenerator(String originFile, String targetFolder) {
		super(originFile, targetFolder);
	}

	@Override
	public RGB getStrokeColor() {
		return new RGB(0, 0, 0);
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
		return originFileName;
	}

}

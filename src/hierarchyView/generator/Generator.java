package hierarchyView.generator;

import java.io.File;
import java.io.IOException;

public abstract class Generator {
	protected String targetFolder, originFileName;

	public Generator(String originFile, String targetFolder) {
		this.targetFolder = targetFolder;
		this.originFileName = new File(originFile).getName().replaceFirst("[.][^.]+$", "");
	}
	
	public abstract void generate() throws IOException;
}

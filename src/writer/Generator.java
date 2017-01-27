package writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import reader.ToBytes;

public abstract class Generator {
	public enum Extension {
		BMP("BMP", ".bmp"), TXT("TXT", ".txt");
		
		private String extension, formatName;
		
		Extension(String formatName, String extension) {
			this.formatName = formatName;
			this.extension = extension;
		}
		
		public String getFormatName() {
			return formatName;
		}
		
		public String getExtension() {
			return extension;
		}
	}
	protected ToBytes convertor;
	protected String targetFolder, fileName;

	public Generator(ToBytes convertor, String targetFolder) {
		this.convertor = convertor;
		this.targetFolder = targetFolder;
		this.fileName = new File(convertor.getPath()).getName().replaceFirst("[.][^.]+$", "");
	}
	
	public abstract void generate(Extension e) throws IOException;
}

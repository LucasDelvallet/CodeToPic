package writer;

import java.io.IOException;

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
	protected ToBytes covertor;
	protected String imagePath;

	public Generator(ToBytes covertor, String imagePath) {
		this.covertor = covertor;
		this.imagePath = imagePath;
	}
	
	public abstract void generate(Extension e) throws IOException;
}

package writer;

import java.io.FileOutputStream;
import java.io.IOException;

import reader.ToBytes;

public class FileGenerator extends Generator {

	public FileGenerator(ToBytes covertor, String imagePath) {
		super(covertor, imagePath);
	}

	@Override
	public void generate(Extension e) throws IOException {
		FileOutputStream fos = new FileOutputStream(imagePath + e.getExtension());
		fos.write(covertor.getBytes());
		fos.close();
	}

}

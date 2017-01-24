package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JarToBytes extends CodeToBytes {

	public JarToBytes(String path) {
		super(path);
	}

	@Override
	public byte[] getBytes() throws IOException {
		// TODO Check extension
		
		File jarFile = new File(codePath);
		if(jarFile.exists()) {
			return Files.readAllBytes(Paths.get(codePath));
		} else {
			throw new FileNotFoundException();
		}
	}
	
}


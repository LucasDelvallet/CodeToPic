package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileToBytes extends ToBytes {

	public FileToBytes(String path) {
		super(path);
	}

	@Override
	public byte[] getBytes() throws IOException {
		File jarFile = new File(path);
		
		if(jarFile.exists()) {
			return Files.readAllBytes(Paths.get(path));
		} else {
			throw new FileNotFoundException();
		}
	}
	
}


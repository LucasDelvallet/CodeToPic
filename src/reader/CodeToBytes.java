package reader;

import java.io.IOException;

public abstract class CodeToBytes {
	protected String codePath;
	
	public CodeToBytes(String path) {
		this.codePath = path;
	}
	
	public abstract byte[] getBytes() throws IOException;
}

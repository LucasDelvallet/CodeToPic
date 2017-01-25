package reader;

import java.io.IOException;

public abstract class ToBytes {
	protected String path;
	
	public ToBytes(String path) {
		this.path = path;
	}
	
	public abstract byte[] getBytes() throws IOException;
}

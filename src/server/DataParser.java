package server;

public class DataParser {
	private final int HEAD_SIZE = 4;
	public String pasingHeader(String line) {
		String header = null;
		if(line.length() >= HEAD_SIZE) {
			header = line.substring(0, HEAD_SIZE);
		}
		return header;
	}
	
	public String pasingContents(String line) {
		String Contents = null;
		if(line.length() > HEAD_SIZE) {
			Contents = line.substring(HEAD_SIZE+1);
		}
		return Contents;
	}

}

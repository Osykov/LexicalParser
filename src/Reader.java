import java.io.*;

public class Reader {
	
	private FileReader fileReader;
	
	public Reader(String fileName) {
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open file: " + fileName);
		}
	}
	
	public String read() {
		char[] charBuffer = new char[20000];
		StringBuilder text = new StringBuilder();
		int length;
		
		try {
			do {
				length = fileReader.read(charBuffer);
				if (length != -1) {
					for (int i = 0; i < length; i++) {
						text.append(charBuffer[i]);
					}
				}
			} while (length != -1);
		} catch (IOException ex) {
			System.err.println("Reading exception.");
		}
		
		return text.toString();
	}
	
}

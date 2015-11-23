import java.io.*;

public class Writer {
	public void writeToHtmlFile(String fileName, String text) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException ex ){
				ex.printStackTrace();
			}
		}
	}
}

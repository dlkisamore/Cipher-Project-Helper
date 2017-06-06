import java.io.*;
import java.util.*;

public class FileManip {
	public static ArrayList<String> getFile(File file) {
		String line = "";
		ArrayList<String> lines = new ArrayList<String>();
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {	
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static void outputFile(String fileName, String message) {
		try(PrintWriter pw = new PrintWriter(fileName)) {
			pw.println(message);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
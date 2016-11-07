package security;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserFile {
	
	private static boolean isFile;

	public UserFile (){
		
	}
	
	public static boolean exists(String fileName){
		
		Path file = Paths.get(fileName);
		isFile = Files.isRegularFile(file) & Files.isReadable(file);
		return isFile;
	}
	
	public static void create(String fileName){
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
        	writer.write("");
        } catch (Exception e) {
			e.printStackTrace();
        }
	}
}

package security_Test_Cases;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import security.Hash;

public class Hash_Test {

	public static void main(String[] args) {
    	String userName = "UserName";
    	Hash tempHash = null;
		try {
			tempHash = new Hash(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}

        String key = tempHash.getHashed();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/"+ key), "utf-8"))) {
        	writer.write("This is the third test");
        } catch (Exception e) {
			e.printStackTrace();
		}

	}

}
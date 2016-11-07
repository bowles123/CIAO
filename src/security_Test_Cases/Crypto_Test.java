package security_Test_Cases;
import java.io.*;
import security.Crypto;
import security.CryptoException;

public class Crypto_Test {
	
	public static void main(String[] args) {
					//****************/
		String key = "7e20d471144b1bff4e1f5d953e05ed15";
		
		String token = "";
		
		
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/cryptoTest.txt"), "utf-8"))) {
        	writer.write(key);
        } catch (Exception e) {
			e.printStackTrace();
        }
		
        File encryptedFile = new File("src/cryptoTest.encrypted");
        
        try {
            Crypto.encrypt(key, token, encryptedFile);
            token = Crypto.decrypt(key, encryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
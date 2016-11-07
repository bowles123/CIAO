package security_Test_Cases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import security.Crypto;
import security.CryptoException;
import security.Hash;

public class HashCrypto_Test {
	
	public static void main(String[] args) {
    	String userName = "FlintstoneF";
    	String passWord = "YoMamma321";
    	String token = "Hash, Encrypt, and Decrypt Test";
    	String decrypted_token = "";
    	
    	Hash fileHash = null;
    	try {
			fileHash = new Hash(userName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Hash keyHash = null;
		try {
			keyHash = new Hash(userName+passWord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileHash.getHashed()), "utf-8"))) {
        	writer.write("");
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
		
//        File inputFile = new File("src/cryptoTest.txt");
        File encryptedFile = new File(fileHash.getHashed());
//        File decryptedFile = new File("src/cryptoTest.decrypted");
        
        String key = keyHash.getHashed();
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(encryptedFile+".unencrypted"), "utf-8"))) {
        	writer.write(token);
        } catch (Exception e) {
			e.printStackTrace();
        }
        
        try {
            Crypto.encrypt(key, token, encryptedFile);
            decrypted_token = Crypto.decrypt(key, encryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(encryptedFile+".decrypted"), "utf-8"))) {
        	writer.write(decrypted_token);
        } catch (Exception e) {
			e.printStackTrace();
        }
        
	}

}
package security_Test_Cases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.swing.JOptionPane;
import security.Crypto;
import security.CryptoException;
import security.Hash;
import security.UserFile;

public class FileHashCrypto_test {
	
	public static void main(String[] args) {
    	String userName = "mcmurraysc";
    	String passWord = "BlahBlah123";
    	
    	Hash fileHash = null;
    	File encryptedFile = null;
    	
    	try {
			fileHash = new Hash(userName);
			encryptedFile = new File(fileHash.getHashed());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	Hash keyHash = null;
		try {
			keyHash = new Hash(userName+passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	if (UserFile.exists(fileHash.getHashed())){
    		try{
    			JOptionPane.showMessageDialog(null, "Token = "+Crypto.decrypt(keyHash.getHashed(), encryptedFile));
    		} catch (CryptoException ex) {
	            System.out.println(ex.getMessage());
	            ex.printStackTrace();
	        }
    		
    		try {
				if (Crypto.decrypt(keyHash.getHashed(), encryptedFile).startsWith("1009~")){
					JOptionPane.showMessageDialog(null, "Login Successful, Token = "+Crypto.decrypt(keyHash.getHashed(), encryptedFile));
					
				}else{
					JOptionPane.showMessageDialog(null, "Invalid Password");
				}
			} catch (CryptoException ex) {
	            System.out.println(ex.getMessage());
	            ex.printStackTrace();
	        }
    		
    	}else{
    		JOptionPane.showMessageDialog(null, "Invalid User");
    	}
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileHash.getHashed()), "utf-8"))) {
        	writer.write("");
        } catch (Exception e) {
			e.printStackTrace();
        }

	}

}

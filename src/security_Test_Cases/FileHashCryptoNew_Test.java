package security_Test_Cases;

import java.io.File;
import javax.swing.JOptionPane;
import security.Crypto;
import security.CryptoException;
import security.Hash;
import security.UserFile;

public class FileHashCryptoNew_Test {
	
	public static void main(String[] args) {
    	String userName = "flintstonef";
    	String passWord = "GoodPass321";
    	String token = "1009~";
    	
    	Hash fileHash = null;
    	Hash keyHash = null;
    	File fileToEncrypt = null;
    	
    	try {
			fileHash = new Hash(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			keyHash = new Hash(userName+passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		UserFile.create(fileHash.getHashed());
		fileToEncrypt = new File(fileHash.getHashed());
		
		JOptionPane.showMessageDialog(null, "Key = " + keyHash.getHashed());
		JOptionPane.showMessageDialog(null, "File = " + fileHash.getHashed());
		
		try {
            Crypto.encrypt(keyHash.getHashed(), token, fileToEncrypt);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    	
	}

}

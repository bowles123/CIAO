package security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	
	private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    
    public Crypto (){
    	
    }
	
    public static void encrypt(String key, String token, File outputFile)
            throws CryptoException {
        doEncrypt(Cipher.ENCRYPT_MODE, key, token, outputFile);
    }
 
    public static String decrypt(String key, File inputFile)
            throws CryptoException {
        return doDecrypt(Cipher.DECRYPT_MODE, key, inputFile);
    }
 
    private static void doEncrypt(int cipherMode, String key, String token, File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
             
            byte[] inputBytes = token.getBytes();
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
             
            outputStream.close();
             
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting file", ex);
        }
    }
    
    private static String doDecrypt(int cipherMode, String key, File inputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
             
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
            
            String token = new String(outputBytes);
            
            inputStream.close();
            
            return token;
             
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error decrypting file", ex);
        }
    }
}
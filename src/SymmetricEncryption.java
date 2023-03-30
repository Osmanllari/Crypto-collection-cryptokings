import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.CipherOutputStream;

public class SymmetricEncryption {
	
	//Method for generating an AES SecretKey
	public static SecretKey generateAESkey(int keySize, String sourceOfRandomness)
			throws NoSuchAlgorithmException { 
		SecureRandom secureRandom;
		switch(sourceOfRandomness) {
    	case "Default":
    		secureRandom = new SecureRandom();
    		break;
    	case "DRBG":
    		secureRandom = SecureRandom.getInstance("DRBG");
    		break;
    	case "SHA1PRNG":
    		secureRandom = SecureRandom.getInstance("SHA1PRNG");
    		break;
    	default:
    		throw new NoSuchAlgorithmException();
    	}
		
		SecretKey key = null;
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(keySize, secureRandom);
			key = keyGenerator.generateKey();
		} catch (Exception e) {
			throw e;
		}
		return key;
	}
	
	//Method for creating a keyStore to save a secretKey
	public static void saveKeyToStorage(SecretKey secretKey, String password, String filepath) throws Exception {
		File file = new File(filepath);
		KeyStore myKeyStore = KeyStore.getInstance("JCEKS");
		if(!file.exists()) 
		{
			myKeyStore.load(null,null);
		}
		
		try {
			//Store secretKey as "myKey" in the keyStore
			myKeyStore.setKeyEntry("myKey", secretKey, password.toCharArray(), null);
			OutputStream writeStream = new FileOutputStream(filepath);
			//Store keyStore
			myKeyStore.store(writeStream, password.toCharArray());
		} catch (Exception e) {
			throw e;
		}
	}
	
	//Method for getting a SecretKey from a KeyStore
	public static SecretKey getKeyFromStorage(String filepath, String password) throws Exception {
		SecretKey key = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("JCEKS");
			InputStream readStream = new FileInputStream(filepath);
			keyStore.load(readStream,password.toCharArray());
			key = (SecretKey) keyStore.getKey("myKey", password.toCharArray());
		}
		catch (Exception e)
		{
			throw e;
		}
		return key;
	}
	
	//Method for encrypting a file using AES
	public static void encryptFile(SecretKey key, File fileToEncrypt) throws Exception {
		//Initializing the cipher in encrypt mode
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //Creating a new file for the encrypted data
        String fileName = fileToEncrypt.getName();
        String encryptedFileName = fileName + ".encrypted";
        File encryptedFile = new File(fileToEncrypt.getParent(), encryptedFileName);

        //Opening the input and output streams
        FileInputStream in = new FileInputStream(fileToEncrypt);
        FileOutputStream out = new FileOutputStream(encryptedFile);

        //Encrypting the file
        CipherOutputStream cipherOut = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int numRead;
        while ((numRead = in.read(buffer)) >= 0) {
            cipherOut.write(buffer, 0, numRead);
        }

        //Closing the streams
        cipherOut.close();
        in.close();
        out.close();

        //Deleting the original file and renaming the encrypted file
        fileToEncrypt.delete();
        encryptedFile.renameTo(fileToEncrypt);
	}
	
	//Method for decrypting a file using AES
	public static void decryptFile(SecretKey key, File fileToDecrypt) throws Exception {
		//Initializing the cipher in decrypt mode using AES
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        //Creating a temporary file to store the decrypted data
        File tempFile = File.createTempFile("temp", null);

        //Opening the input and output streams for reading and writing into files
        FileInputStream input = new FileInputStream(fileToDecrypt);
        FileOutputStream output = new FileOutputStream(tempFile);

        //Decrypting the file
        CipherInputStream cipherInput = new CipherInputStream(input, cipher);
        byte[] buffer = new byte[1024];
        int numRead;
        while ((numRead = cipherInput.read(buffer)) >= 0) {
            output.write(buffer, 0, numRead);
        }

        cipherInput.close();
        input.close();
        output.close();

        //Copying the decrypted data back to the original file
        input = new FileInputStream(tempFile);
        output = new FileOutputStream(fileToDecrypt);
        while ((numRead = input.read(buffer)) >= 0) {
            output.write(buffer, 0, numRead);
        }

        //Closing the streams and deleting the temporary file
        input.close();
        output.close();
        tempFile.delete();
    }	
}

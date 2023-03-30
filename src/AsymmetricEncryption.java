import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class AsymmetricEncryption {
	
	//Method for generating a KeyPair using RSA cipher
	public static KeyPair generateRSAKeyPair(int keySize, String sourceOfRandomness) throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
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
	    
		//Generating a key pair based on the selected keySize and source of randomness
	    keyGen.initialize(keySize, secureRandom);
	    KeyPair keyPair = keyGen.generateKeyPair();
	    return keyPair;
	}
	
	//Method for getting a stored RSA Public key from a PEM file 
	public static PublicKey getPublicKeyFromStorage(File selectedFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		//Reading the public key file
		FileReader fileReader = new FileReader(selectedFile);
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		PemReader pemReader = new PemReader(bufferedReader);
		
		PemObject pemObject = pemReader.readPemObject();
		byte[] pemContent = pemObject.getContent();
		
		pemReader.close();
		bufferedReader.close();
		fileReader.close();

		//Converting the public key from PEM format to a PublicKey object
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pemContent);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
			      
		return publicKey;
	}
	  
	//Method for getting a stored RSA Private key from a PEM file 
	public static PrivateKey getPrivateKeyFromStorage(File selectedFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException  {
		//Reading the private key file
	    FileReader fileReader = new FileReader(selectedFile);
	    
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    PemReader pemReader = new PemReader(bufferedReader);
	    
	    PemObject pemObject = pemReader.readPemObject();
	    byte[] pemContent = pemObject.getContent();
	    
	    pemReader.close();
	    bufferedReader.close();
	    fileReader.close();
	    
	    //Converting the private key from PEM format to a PrivateKey object
	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pemContent);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			      
	    return privateKey;
	}
	  
	  
	//Method for Encrypting a file using a Public Key
	public static void encryptFile(PublicKey key, File inputFile) throws Exception {
		//Creating a cipher object and initializing it for encrypting
	    Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    
	    //Reading the input file and encrypting the content
	    FileInputStream inputStream = new FileInputStream(inputFile);
	    byte[] inputBytes = new byte[(int) inputFile.length()];
	    inputStream.read(inputBytes);
	    byte[] outputBytes = cipher.doFinal(inputBytes);

	    //Writing the encrypted content to the input file
	    FileOutputStream outputStream = new FileOutputStream(inputFile);
	    outputStream.write(outputBytes);

	    //Close the streams
	    inputStream.close();
	    outputStream.close();
	}
	  
	  
	//Method for Decrypting a file using a Private Key
	public static void decryptFile(PrivateKey key, File inputFile) throws Exception {
		//Creating a cipher object and initializing it for decrypting
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);

		//Reading the input file and decrypting the content
		FileInputStream inputStream = new FileInputStream(inputFile);
		byte[] inputBytes = new byte[(int) inputFile.length()];
		inputStream.read(inputBytes);
		byte[] outputBytes = cipher.doFinal(inputBytes);

		//Writing the decrypted content to the input file
		FileOutputStream outputStream = new FileOutputStream(inputFile);
		outputStream.write(outputBytes);

		//Closing the streams
		inputStream.close();
		outputStream.close();
	}
}


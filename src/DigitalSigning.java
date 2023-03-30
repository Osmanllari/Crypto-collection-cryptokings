import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class DigitalSigning {
	
	//Method for generating a Key Pair using DSA cipher
	public static KeyPair generateDSAKeyPair(int keySize, String sourceOfRandomness) throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
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
	    keyGen.initialize(keySize, secureRandom);
	    KeyPair keyPair = keyGen.generateKeyPair();
	    return keyPair;
	}
	
	//Method that uses a Private Key to digitally sign a file
	public static byte[] signFile(PrivateKey privateKey, File file) throws Exception {
		//Initializing the signature object
	    Signature signature = Signature.getInstance("DSA");
	    signature.initSign(privateKey);

	    //Reading the file and updating the signature
	    FileInputStream inputStream = new FileInputStream(file);
	    byte[] data = new byte[1024];
	    int numRead;
	    while ((numRead = inputStream.read(data)) != -1) {
	      signature.update(data, 0, numRead);
	    }
	    inputStream.close();

	    //Generating the signature
	    byte[] signatureBytes = signature.sign();
	    return signatureBytes;
	}
	
	//Method for verifying that a file has been signed 
	public static boolean verifySignature(PublicKey publicKey, File file, byte[] signature) throws Exception {
	    //Initializing the signature object
	    Signature sig = Signature.getInstance("DSA");
	    sig.initVerify(publicKey);

	    //Reading the file and updating the signature
	    FileInputStream fis = new FileInputStream(file);
	    byte[] data = new byte[1024];
	    int numRead;
	    while ((numRead = fis.read(data)) != -1) {
	      sig.update(data, 0, numRead);
	    }
	    fis.close();

	    //Verifying the signature, returns true if signature matches
	    boolean decision = sig.verify(signature);
	    return decision;
	  }

	//Method for getting a stored DSA Public key from a PEM file 
	public static PublicKey getDSAPublicKeyFromStorage(File selectedFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
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
		KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
			      
		return publicKey;
	}
		  
	//Method for getting a stored DSA Private key from a PEM file 
	public static PrivateKey getDSAPrivateKeyFromStorage(File selectedFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException  {
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
	    KeyFactory keyFactory = KeyFactory.getInstance("DSA");
	    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			      
	    return privateKey;
	}
}

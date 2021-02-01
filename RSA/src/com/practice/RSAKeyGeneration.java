package com.practice;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSAKeyGeneration {
	
	/* RSA keys path configuration final properties */
	private static final String publicKeyPath = Paths.get("..", "RSA", "src", "com", "practice").toString() + File.separator + "public.key"; // "D:\\POC\\SpringBoot\\RSA\\src\\com\\practice\\public.key";
	private static final String privateKeyPath = Paths.get("..", "RSA", "src", "com", "practice").toString() + File.separator + "private.key"; // "D:\\POC\\SpringBoot\\RSA\\src\\com\\practice\\private.key";
	private static final Integer keyGeneratorSize = 4096;
	private static final String keyFacroryInstance = "RSA";
	private static final String keyCipherInstance = "RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING";
	
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private KeyFactory keyFactory;
	private RSAPublicKeySpec publicKeySpec;
	private RSAPrivateKeySpec privateKeySpec;

	/** Get the private key */
	public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

	/** Get the public key */
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    
    /** get the KeyPair */
    public KeyPair getKeyPair() {
        return this.pair;
    }
    
    /** get the KeyPair */
    public KeyFactory getKeyFactorInstance() {
        return keyFactory;
    }
    
    /** get the RSAPublicKeySpec */
    public RSAPublicKeySpec getRSAPublicKeySpec() {
        return publicKeySpec;
    }
    
    /** get the RSAPrivateKeySpec */
    public RSAPrivateKeySpec getRSAPrivateKeySpec() {
        return privateKeySpec;
    }
    
    /** Get an instance of the RSA key generator */
 	public RSAKeyGeneration() throws NoSuchAlgorithmException, NoSuchProviderException {
         this.keyGen = KeyPairGenerator.getInstance(keyFacroryInstance);
         this.keyGen.initialize(keyGeneratorSize);
     }
 	
 	/** create keys and its key pair */
 	public void createKeys() throws NoSuchAlgorithmException, InvalidKeySpecException {
         this.pair = this.keyGen.generateKeyPair();
         this.privateKey = pair.getPrivate();
         this.publicKey = pair.getPublic();
         this.keyFactory = KeyFactory.getInstance(keyFacroryInstance);
         this.publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
         this.privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
     }
    
    
 	/** Saving the Key to the file */
 	public void saveKeys() throws IOException {
 		saveKeyToFile(publicKeyPath, publicKeySpec.getModulus(), publicKeySpec.getPublicExponent());
 		saveKeyToFile(privateKeyPath, privateKeySpec.getModulus(), privateKeySpec.getPrivateExponent());
 	}
 	
 	/** encrypt text with public key */ 
    public byte[] encrypt(String plainText) throws Exception
    {
        Key publicKey = readKeyFromFile(publicKeyPath);

        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance(keyCipherInstance);

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes());

        return cipherText;
    }
    
    /** encrypt text/object with public key */ 
    public byte[] encrypt(byte[] object) throws Exception
    {
        Key publicKey = readKeyFromFile(publicKeyPath);

        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance(keyCipherInstance);

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(object);

        return cipherText;
    }

    /** decrypt text/object with private key */
    public  String decrypt(byte[] cipherTextArray) throws Exception {
        Key privateKey = readKeyFromFile(privateKeyPath);

        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance(keyCipherInstance);

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);

        return new String(decryptedTextArray);
    }
    
    /** get the modulus and exponent of both the keys and write it to the key file */
    private void saveKeyToFile(String fileName, BigInteger modulus, BigInteger exponent) throws IOException {
        ObjectOutputStream ObjOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			ObjOutputStream.writeObject(modulus);
			ObjOutputStream.writeObject(exponent);
		} catch (Exception e) {
			//LOG.error("RSAKeyGeneration.saveKeyToFile() : Error while saving keys at src/com/practice directory {}", e);
			System.out.println("EmployeeRSAKeyGeneration.saveKeyToFile() : Error while saving keys at src/com/practice directory {}");
		} finally {
			ObjOutputStream.close();
		}
    }
    
    /** read keys from located files */
    private  Key readKeyFromFile(String keyFileName) throws IOException {
        Key key = null;
        InputStream inputStream = new FileInputStream(keyFileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(inputStream));
        try {
            BigInteger modulus = (BigInteger) objectInputStream.readObject();
            BigInteger exponent = (BigInteger) objectInputStream.readObject();
            KeyFactory keyFactory = KeyFactory.getInstance(keyFacroryInstance);
            if (keyFileName.equalsIgnoreCase(publicKeyPath))
                key = keyFactory.generatePublic(new RSAPublicKeySpec(modulus, exponent));
            else
                key = keyFactory.generatePrivate(new RSAPrivateKeySpec(modulus, exponent));

        } catch (Exception e){
        	//LOG.error("RSAKeyGeneration.saveKeyToFile() : Error while reading keys from src/com/practice directory {}", e);
        	System.out.println("EmployeeRSAKeyGeneration.saveKeyToFile() : Error while reading keys from src/com/practice directory {}");
        } finally {
            objectInputStream.close();
        }
        return key;
    }

}

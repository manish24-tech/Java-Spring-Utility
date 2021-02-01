package com.practice;


import java.util.Base64;

public class RSAMain
{
    static String plainText = "Text should be encrypted and decrypted by Java RSA Encryption in ECB Mode";

    public static void main(String[] args) throws Exception
    {
    	
    	RSAKeyGeneration obj = new RSAKeyGeneration();
    	obj.createKeys();
    	obj.saveKeys();
    	
    	System.out.println("Original Text  : " + plainText);
    	
    	byte[] cipherTextArray = obj.encrypt(plainText);
    	String encryptedText = Base64.getEncoder().encodeToString(cipherTextArray);
        System.out.println("Encrypted Text : " + encryptedText);
        
        String decryptedText = obj.decrypt(cipherTextArray);
        System.out.println("DeCrypted Text : " + decryptedText);
    
    }


}


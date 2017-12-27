package com.cloudscrapperportal.tools;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


public class Encryption {

	public static String encrypt(String s, SecretKey myDesKey) throws EncryptionException{
			Cipher desCipher;
			try {
				
				byte [] raw = myDesKey.getEncoded();
			    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			    
				desCipher = Cipher.getInstance("AES");

				desCipher.init(Cipher.ENCRYPT_MODE, skeySpec);
				byte[] text = s.getBytes();
				byte[] textEncrypted = desCipher.doFinal(text);
				
				return new String(Base64.encodeBase64String(textEncrypted));  
			
				//return new String(textEncrypted);
			} catch (Exception e) {
				throw new EncryptionException ("Error encrypting message: " + e.getMessage());
			}
	}
	
	public static String decrypt(String s, SecretKey myDesKey) throws EncryptionException {
		

		try {
			byte [] raw = myDesKey.getEncoded();
		    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher desCipher = Cipher.getInstance("AES");
			desCipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] decryptedBytes = Base64.decodeBase64(s);
			byte[] textDecrypted = desCipher.doFinal(decryptedBytes);
			return new String(textDecrypted);
		} catch (Exception e) {
			throw new EncryptionException ("Error decrypting message: " + e.getMessage());
		}
	}
}

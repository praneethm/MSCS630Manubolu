package main.security.init;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import android.util.Base64;

public class KeyGen {




	public static SecretKey GenerateASEKey() {
		KeyGenerator generator;
		SecretKey AESKEY = null;
		try {
			generator = KeyGenerator.getInstance("AES");
			// generator.init(128);
			// generator.init(192);
			generator.init(256);
			AESKEY = generator.generateKey();
			System.out
					.println("Generated ASE Key :"
							+ Base64.encodeToString(AESKEY.getEncoded(),
									Base64.NO_WRAP));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return AESKEY;

	}

	public static String AESdataEncrypt(String data, SecretKey AESKEY) {
		String val = null;
		try {
			final Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.ENCRYPT_MODE, AESKEY);
			// System.out.println("data before encryption \n" + readFile(Data));
			final byte[] encValue = c.doFinal(data.getBytes());
			val = Base64.encodeToString(encValue, Base64.NO_WRAP);
			return val;
			// encode(encValue);
		} catch (Exception ex) {
			System.out.println("The Exception is=" + ex);
		}
		return val;
	}

	public static String AESdataDecrypt(String data, SecretKey AESKEY) {
		try {

			final Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.DECRYPT_MODE, AESKEY);
			final byte[] decorVal = Base64.decode(data, Base64.NO_WRAP);
			final byte[] decValue = c.doFinal(decorVal);
			System.out.println("data after decryption "
					+ (new String(decValue, "UTF-8")));
			return new String(decValue, "UTF-8");
			// System.out.println( new String(decValue, "UTF-8"));
		} catch (Exception ex) {
			System.out.println("The Exception is=" + ex);
		}
		return null;

	}
	
	public static String getHashfile(String text) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
			String newValue = new String(hash, "UTF-8");
			return newValue; 
			// System.out.println("hash file converted to string :" + newValue);
			//return hash;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

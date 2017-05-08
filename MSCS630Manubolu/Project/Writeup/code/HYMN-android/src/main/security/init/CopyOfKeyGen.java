package main.security.init;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;





import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import android.util.Base64;

public class CopyOfKeyGen {

	private static PublicKey PKA;
	private static PrivateKey SKA;
	public static String mod;
	public static String exponent;
	public static SecretKey AESKEY;

	public static String RSAEncrypt(String data,PublicKey pk) {

		try {
			Cipher cipher;
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			byte[] bytes = (data).getBytes("UTF-8");

			byte[] encrypted = blockCipher(bytes, Cipher.ENCRYPT_MODE, cipher);

			char[] encryptedTranspherable = Hex.encodeHex(encrypted);

			return new String(encryptedTranspherable);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String RSADecrypt(String data, int i) {

		// SecretKey originalKey = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance("RSA");

			// decryptRsaKey the text using the private key
			cipher.init(Cipher.DECRYPT_MODE, SKA);
			byte[] bts = Hex
					.decodeHex(data.toCharArray());
			byte[] decrypted = blockCipher(bts,
					Cipher.DECRYPT_MODE, cipher);
			return new String(decrypted, "UTF-8").replaceAll("[^\\x20-\\x7e]",
					"");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public static void GenerateKeys() {
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			final KeyPair key = keyGen.generateKeyPair();
			setPKA(key.getPublic());
			setSKA(key.getPrivate());
			KeyFactory fact = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec pub = (RSAPublicKeySpec) fact.getKeySpec(
					key.getPublic(), RSAPublicKeySpec.class);
			exponent = pub.getPublicExponent().toString();
			mod = pub.getModulus().toString();

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
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


	public PrivateKey getSKA() {
		return SKA;
	}

	public static void setSKA(PrivateKey sKA) {
		SKA = sKA;
	}

	public static PublicKey getPKA() {
		return PKA;
	}

	public static void setPKA(PublicKey pKA) {
		PKA = pKA;
	}

	private static byte[] blockCipher(byte[] bytes, int mode, Cipher cipher)
			throws IllegalBlockSizeException, BadPaddingException {

		byte[] scrambled = new byte[0];

		byte[] toReturn = new byte[0];

		int length = (mode == Cipher.ENCRYPT_MODE) ? 245 : 256;

		byte[] buffer = new byte[length];

		for (int i = 0; i < bytes.length; i++) {

			if ((i > 0) && (i % length == 0)) {

				scrambled = cipher.doFinal(buffer);

				toReturn = append(toReturn, scrambled);

				int newlength = length;

				if (i + length > bytes.length) {
					newlength = bytes.length - i;
				}

				buffer = new byte[newlength];
			}

			buffer[i % length] = bytes[i];
		}

		scrambled = cipher.doFinal(buffer);

		toReturn = append(toReturn, scrambled);

		return toReturn;
	}

	private static byte[] append(byte[] prefix, byte[] suffix) {
		byte[] toReturn = new byte[prefix.length + suffix.length];
		for (int i = 0; i < prefix.length; i++) {
			toReturn[i] = prefix[i];
		}
		for (int i = 0; i < suffix.length; i++) {
			toReturn[i + prefix.length] = suffix[i];
		}
		return toReturn;
	}
	public static String RSAEncrypt(PublicKey PKB,SecretKey AES) {
		
		try {

			Cipher cipher;
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, PKB);
			byte[] bytes = Base64.encodeToString(AES.getEncoded(), Base64.DEFAULT).getBytes("UTF-8");
			byte[] encrypted = blockCipher(bytes, Cipher.ENCRYPT_MODE, cipher);
			char[] encryptedTranspherable = Hex.encodeHex(encrypted);
			
			return new String(encryptedTranspherable);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public static SecretKey RSADecrypt(String data) {
		
		System.out.println("trying to decrypt AES key");
		System.out.println("public rsa key ");
		SecretKey originalKey = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance("RSA");

			// decryptRsaKey the text using the private key
			cipher.init(Cipher.DECRYPT_MODE, SKA);
			byte[] bts = Hex.decodeHex(data.toCharArray());
			
			byte[] decrypted = blockCipher(bts, Cipher.DECRYPT_MODE, cipher);

			//System.out.println("test variable "+testde.replaceAll("[^\\x20-\\x7e]", ""));
			String temp=new String(decrypted,"UTF-8").replaceAll("[^\\x20-\\x7e]", "");
			byte[] decodedKey = Base64.decode(temp,Base64.DEFAULT);
			System.out.println("after dec only "+new String(decrypted,"UTF-8").replaceAll("[^\\x20-\\x7e]", ""));
			originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length,
					"AES");
			System.out.println("aes key after decry "+Base64.encodeToString(originalKey.getEncoded(), Base64.NO_WRAP));
			return originalKey;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return originalKey;

	}

	
}

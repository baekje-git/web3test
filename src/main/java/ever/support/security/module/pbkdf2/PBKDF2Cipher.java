package ever.support.security.module.pbkdf2;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2Cipher {
	public String getPBKDF2Hash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String retStr = "";
		try {
			int iterations = 1000;
			char[] chars = password.toCharArray();
			byte[] salt = getSalt();
			PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 512);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			retStr = String.valueOf(iterations) + ":" + toHex(salt) + ":" + toHex(hash);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException Error");
			retStr = "";
		} catch (InvalidKeySpecException e) {
			System.out.println("InvalidKeySpecException Error");
			retStr = "";
		}
		return retStr;
	}

	public byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	public String toHex(byte[] array) throws NoSuchAlgorithmException {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = array.length * 2 - hex.length();
		if (paddingLength > 0)
			return String.valueOf(String.format("%0" + paddingLength + "d", new Object[] { Integer.valueOf(0) })) + hex;
		return hex;
	}
}

package ever.support.security.module.sha;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHACipher {
	public String encryptSHA256(String str) throws Exception {
		if (str == null || "".equals(str))
			return str;
		String sha = "";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte[] byteData = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++)
				sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
			sha = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Encrypt (encryptSHA) Error - NoSuchAlgorithmException");
			sha = null;
		}
		return sha;
	}

	public String encryptSHA(String str, String type) throws Exception {
		if (str == null || "".equals(str))
			return str;
		String sha = "";
		try {
			MessageDigest sh = MessageDigest.getInstance(type);
			sh.update(str.getBytes());
			byte[] byteData = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++)
				sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
			sha = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Encrypt (encryptSHA) Error - NoSuchAlgorithmException");
			sha = null;
		}
		return sha;
	}
}

package ever.support.security.module.aes.aes128;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AES128Cipher {
	private static final String TRANSFORM = "AES/ECB/PKCS5Padding";

	public static String Encrypt(String plainText, String key) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(1, skeySpec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes());
		return asHex(encrypted);
	}

	public static String Decrypt(String cipherText, String key) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(2, skeySpec);
		byte[] original = cipher.doFinal(fromString(cipherText));
		String originalString = new String(original);
		return originalString;
	}

	private static String asHex(byte[] buf) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		for (int i = 0; i < buf.length; i++) {
			if ((buf[i] & 0xFF) < 16)
				strbuf.append("0");
			strbuf.append(Long.toString((buf[i] & 0xFF), 16));
		}
		return strbuf.toString();
	}

	private static byte[] fromString(String hex) {
		int len = hex.length();
		byte[] buf = new byte[(len + 1) / 2];
		int i = 0, j = 0;
		if (len % 2 == 1)
			buf[j++] = (byte) fromDigit(hex.charAt(i++));
		while (i < len)
			buf[j++] = (byte) (fromDigit(hex.charAt(i++)) << 4 | fromDigit(hex.charAt(i++)));
		return buf;
	}

	private static int fromDigit(char ch) {
		if (ch >= '0' && ch <= '9')
			return ch - 48;
		if (ch >= 'A' && ch <= 'F')
			return ch - 65 + 10;
		if (ch >= 'a' && ch <= 'f')
			return ch - 97 + 10;
		throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
	}
}

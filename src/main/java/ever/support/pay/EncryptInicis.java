package ever.support.pay;


/**
 * 
 */
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @FileName EncryptInicis.java
 */
public class EncryptInicis {

	//static String mid = "INIBillTst";
	//static String KEY = "rKnPljRn5m6J9Mzz";
	//static String IV = "W2KLNKra6Wxc1P==";

	static String mid;
	static String KEY;
	static String IV;

	public static String encrypt_AES(String plainText) throws Exception {
		return encrypt_AES(plainText,KEY,IV);
	}

	public static String decrypt_AES(String plainText) throws Exception {
		return decrypt_AES(plainText,KEY,IV);
	}

	public static String encryptSHA512(String input) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(input.getBytes("UTF-8"));
		return byteArrayToHex(md.digest());
	}

	public static String encrypt_AES(String plainText, String Key, String IV) throws Exception {
		byte[] merchantKey;
		SecretKeySpec keyspec;
		Cipher cipher;

		merchantKey = Key.getBytes();
		keyspec = new SecretKeySpec(merchantKey, "AES");

		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keyspec, new IvParameterSpec(IV.getBytes()));

		byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted));

		return enStr;
	}

	public static String decrypt_AES(String encryptedText, String Key, String IV) throws Exception {
		byte[] merchantKey;
		SecretKeySpec keyspec;
		Cipher cipher;

		merchantKey = Key.getBytes();
		keyspec = new SecretKeySpec(merchantKey, "AES");

		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keyspec, new IvParameterSpec(IV.getBytes()));

		return new String(cipher.doFinal(Base64.decodeBase64(encryptedText.getBytes())));
	}

	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;

		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}

		return sb.toString();
	}

	// ����
	public static void main(String[] args) throws Exception {

		String encCardNumber = encrypt_AES("123567812345678", KEY, IV);
		System.out.println("카드번호 암호화 : " + encCardNumber);

		String decCardNumber = decrypt_AES(encCardNumber, KEY, IV);
		System.out.println("카드번호 복호화 : " + decCardNumber);
		System.out.println("");


		String orgData;
		String cardNo;

		cardNo = "4450901048473843";
		System.out.println("카드번호 : " + cardNo);
		System.out.println("암호화 : " + encrypt_AES(cardNo));
		System.out.println("");

		orgData = "0521";
		System.out.println("유효기간 : " + orgData);
		System.out.println("암호화 : " + encrypt_AES(orgData));
		System.out.println("");

		orgData = KEY+"Pay"+"Card"+"20200025030052"+"192.168.0.71"+mid+"20200025030052"+"10"+encrypt_AES(cardNo);
		System.out.println("orgiData : " + orgData);
		System.out.println("HashData : " + encryptSHA512(orgData));
		System.out.println("");

	}
}

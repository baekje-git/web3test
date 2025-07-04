package ever.support.security.module.hmac;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACCipher {
	private static final String DEFAULT_ENCODING = "UTF-8";

	private static final String HMAC_SHA256 = "HmacSHA256";

	private static final String HMAC_SHA512 = "HmacSHA512";

	public byte[] hmacSha(String value, String key, String sha) {
		if ("256".equals(sha)) {
			sha = "HmacSHA256";
		} else if ("512".equals(sha)) {
			sha = "HmacSHA512";
		} else {
			return null;
		}
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), sha);
			Mac mac = Mac.getInstance(sha);
			mac.init(keySpec);
			return mac.doFinal(value.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String asHex(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}
}

package ever.support.security.module.bcrypt;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptCipher {
	private int saltLevel = 10;

	public String HashPw(String str) {
		if (str == null || "".equals(str))
			return str;
		return BCrypt.hashpw(str, BCrypt.gensalt(this.saltLevel));
	}

	public boolean checkPw(String str, String bcryptHash) {
		if (str == null || "".equals(str))
			return false;
		return BCrypt.checkpw(str, bcryptHash);
	}
}

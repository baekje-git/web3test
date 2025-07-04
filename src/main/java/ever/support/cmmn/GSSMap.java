package ever.support.cmmn;

import ever.support.date.DateUtil;
import ever.support.session.SessionUtil;
import java.util.HashMap;

public class GSSMap<K, V> extends HashMap {
	private static final long serialVersionUID = -6505326388114687691L;

	public GSSMap() {
		try {
			put((K) "GSS", (V) SessionUtil.getUserInfo());
			if (containsKey("GSS"))
				((HashMap<String, String>) get("GSS")).put("SYSDTM", DateUtil.getCurrentTimeToString());
		} catch (Exception exception) {
		}
	}
}

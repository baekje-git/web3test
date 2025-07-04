package kada.k03.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PlayerLocationService {
	
	public void insertPlayerLocationDefaultReg(HashMap<String, Object> hList, String ipaddr, String userAgent) throws Exception;
	
}

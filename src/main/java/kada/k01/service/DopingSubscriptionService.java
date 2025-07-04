package kada.k01.service;

import java.util.HashMap;
import java.util.List;

public interface DopingSubscriptionService {
	public void saveDopingSubscriptionRegReq(HashMap<String, Object> hList, String ipaddr, String userAgent) throws Exception;
	public void saveDopingSubscriptionChaperonRegReq(HashMap<String, Object> hList, String ipaddr, String userAgent) throws Exception;
	public void saveDopingSubscriptionRegCancle(HashMap<String, Object> hList, String ipaddr, String userAgent) throws Exception;
	public void validateRequest(HashMap<String, Object> hmap) throws Exception;
	public HashMap<String, Object> checkSaveValidation(HashMap<String, Object> hmap) throws Exception;
	public HashMap<String, Object> checkCancleValidation(HashMap<String, Object> hmap) throws Exception;
	
}

package kada.k99.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserRegistService {
	public void saveUserRegist(HashMap<String, Object> hMap) throws Exception;
	public void initPasswordUser(HashMap<String, Object> hMap) throws Exception;
	public void saveClubManager(HashMap<String, Object> hMap) throws Exception;
}

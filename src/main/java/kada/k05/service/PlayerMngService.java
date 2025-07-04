package kada.k05.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PlayerMngService {
	public void saveUserRegist(HashMap<String, Object> hMap) throws Exception;
	public int saveUserList(List<Map<String, Object>> hList) throws Exception;
	
	//프로단체등록화면
	public void proorgsaveUserRegist(HashMap<String, Object> hMap) throws Exception;
	
}

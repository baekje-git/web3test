package kada.k05.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LocationScheduleMngService {
	
	public void saveLocationScheduleMng(HashMap<String, Object> hMap) throws Exception;
	public int saveLocationScheduleMngList(List<Map<String, Object>> hList) throws Exception;
	public int saveRepeatLocationScheduleMng(HashMap<String, Object> hMap) throws Exception;	
	public void deleteLocationScheduleMng(HashMap<String, Object> hMap) throws Exception;
}

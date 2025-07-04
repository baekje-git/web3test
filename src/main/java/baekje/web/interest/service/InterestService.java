package baekje.web.interest.service;

import java.util.HashMap;
import java.util.List;

public interface InterestService {
	int insertInterestItem(HashMap<String, Object> paramMap) throws Exception;
	
	int deleteInterestItem(HashMap<String, Object> paramMap) throws Exception;
	
	int updateAlarmYn(HashMap<String, Object> paramMap) throws Exception;
	
	int deleteMyItems(HashMap<String, Object> paramMap) throws Exception;
	
	int insertGroup(HashMap<String, Object> paramMap) throws Exception;
	
	int deleteGroups(List<HashMap<String, Object>> paramMap) throws Exception;
	
	int updateSortNo(List<HashMap<String, Object>> paramMap) throws Exception;
	
	int updateGroupNo(List<HashMap<String, Object>> paramMap) throws Exception;
	
	int updateMonthQty(HashMap<String, Object> paramMap) throws Exception;
	
	int updateAllAlarmYn(HashMap<String, Object> paramMap) throws Exception;
	
	int updateGroupNm(HashMap<String, Object> paramMap) throws Exception;
}

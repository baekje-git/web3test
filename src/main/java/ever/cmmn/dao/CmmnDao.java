package ever.cmmn.dao;

import java.util.HashMap;
import java.util.List;

public interface CmmnDao {
	String select(String paramString);

	Object select(HashMap<String, Object> paramHashMap);

	Object select(String paramString, HashMap<String, Object> paramHashMap);

	List<Object> selectList(HashMap<String, Object> paramHashMap);

	List<Object> selectList(String paramString, HashMap<String, Object> paramHashMap);

	int insert(HashMap<String, Object> paramHashMap);

	int insert(String paramString, HashMap<String, Object> paramHashMap);

	int update(HashMap<String, Object> paramHashMap);

	int update(String paramString, HashMap<String, Object> paramHashMap);

	int delete(HashMap<String, Object> paramHashMap);

	int delete(String paramString, HashMap<String, Object> paramHashMap);

	Object callProcedure(HashMap<String, Object> paramHashMap) throws Exception;

	Object callProcedure(String paramString, HashMap<String, Object> paramHashMap) throws Exception;
}


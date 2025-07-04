package ever.cmmn.dao;

import java.util.HashMap;
import java.util.List;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
public interface CmmnDynamicDao {
	void setDbConnectKey(String paramString) throws Exception;

	void endTransaction() throws Exception;

	void rollback() throws Exception;

	void endBatch() throws Exception;

	String select(String paramString1, String paramString2) throws Exception;

	Object select(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	Object select(String paramString1, String paramString2, HashMap<String, Object> paramHashMap) throws Exception;

	List<Object> selectList(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	List<Object> selectList(String paramString1, String paramString2, HashMap<String, Object> paramHashMap)
			throws Exception;

	int insert(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	int insert(String paramString1, String paramString2, HashMap<String, Object> paramHashMap) throws Exception;

	int update(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	int update(String paramString1, String paramString2, HashMap<String, Object> paramHashMap) throws Exception;

	int delete(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	int delete(String paramString1, String paramString2, HashMap<String, Object> paramHashMap) throws Exception;

	Object callProcedure(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	Object callProcedure(String paramString1, String paramString2, HashMap<String, Object> paramHashMap)
			throws Exception;
}

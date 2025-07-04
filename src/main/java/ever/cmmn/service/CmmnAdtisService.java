package ever.cmmn.service;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface CmmnAdtisService {
	Object select(HashMap<String, Object> paramHashMap) throws Exception;

	Object select(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	List<Object> selectList(HashMap<String, Object> paramHashMap) throws Exception;

	List<Object> selectList(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	int insert(HashMap<String, Object> paramHashMap) throws Exception;

	int insert(String paramString, HashMap<String, Object> paramHashMap) throws Exception;
	
	int insertList(String paramString, List<HashMap<String, Object>> paramHashMapList) throws Exception;

	int update(HashMap<String, Object> paramHashMap) throws Exception;

	int update(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	int delete(HashMap<String, Object> paramHashMap) throws Exception;

	int delete(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	Object callProcedure(HashMap<String, Object> paramHashMap) throws Exception;

	Object callProcedure(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	List<Object> selectAttachFileList(HashMap<String, Object> paramHashMap) throws Exception;

	List<Object> selectAttachFileList(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	Object insertAttatchFileInfo(HttpServletRequest paramHttpServletRequest) throws Exception;

	Object insertAttatchFileInfo(String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;

	Object doSave(String paramString1, String paramString2, String paramString3, HashMap<String, Object> paramHashMap)
			throws Exception;

	Object doSaveSuffix(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	Object selectDynamic(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	Object selectDynamic(String paramString1, String paramString2, HashMap<String, Object> paramHashMap)
			throws Exception;

	Object selectListDynamic(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	Object selectListDynamic(String paramString1, String paramString2, HashMap<String, Object> paramHashMap)
			throws Exception;

	int insertDynamic(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	int insertDynamic(String paramString1, String paramString2, HashMap<String, Object> paramHashMap) throws Exception;

	int updateDynamic(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	int updateDynamic(String paramString1, String paramString2, HashMap<String, Object> paramHashMap) throws Exception;

	int deleteDynamic(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	int deleteDynamic(String paramString1, String paramString2, HashMap<String, Object> paramHashMap) throws Exception;

	Object callProcedureDynamic(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	Object callProcedureDynamic(String paramString1, String paramString2, HashMap<String, Object> paramHashMap)
			throws Exception;
}

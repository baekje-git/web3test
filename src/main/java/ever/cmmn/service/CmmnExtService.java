package ever.cmmn.service;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface CmmnExtService {
	Object select(HashMap<String, Object> paramHashMap) throws Exception;

	Object select(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	List<Object> selectList(HashMap<String, Object> paramHashMap) throws Exception;

	List<Object> selectList(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

	int insert(HashMap<String, Object> paramHashMap) throws Exception;

	int insert(String paramString, HashMap<String, Object> paramHashMap) throws Exception;

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

}

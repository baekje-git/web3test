package ever.cmmn.dao;

import ever.support.exception.AjaxMsgCustomException;
import ever.support.mybatis.SqlSessionExt;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("cmmnDao")
public class CmmnDaoImpl extends SqlSessionExt implements CmmnDao {
	private static final Logger logger = LoggerFactory.getLogger(CmmnDaoImpl.class);

	public Object select(HashMap<String, Object> hMap) {
		Object retObj = null;
		try {
			retObj = selectDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retObj;
	}

	public String select(String queryId) {
		String retString = "";
		try {
			retString = selectDataSource(queryId);
		} catch (Exception e) {
			logger.error("[" + queryId + "]" + queryId + " [EXCEPTION] => " + e);
			throw e;
		}
		return retString;
	}

	public Object select(String queryId, HashMap<String, Object> hMap) {
		Object retObj = null;
		try {
			retObj = selectDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retObj;
	}

	public List<Object> selectList(HashMap<String, Object> hMap) {
		List<Object> retObj = null;
		try {
			retObj = selectListDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retObj;
	}

	public List<Object> selectList(String queryId, HashMap<String, Object> hMap) {
		List<Object> retObj = null;
		try {
			retObj = selectListDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retObj;
	}

	public int insert(HashMap<String, Object> hMap) {
		int retCnt = 0;
		try {
			retCnt = insertDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int insert(String queryId, HashMap<String, Object> hMap) {
		int retCnt = 0;
		try {
			retCnt = insertDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int update(HashMap<String, Object> hMap) {
		int retCnt = 0;
		try {
			retCnt = updateDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int update(String queryId, HashMap<String, Object> hMap) {
		int retCnt = 0;
		try {
			retCnt = updateDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int delete(HashMap<String, Object> hMap) {
		int retCnt = 0;
		try {
			retCnt = deleteDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int delete(String queryId, HashMap<String, Object> hMap) {
		int retCnt = 0;
		try {
			retCnt = deleteDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public Object callProcedure(HashMap<String, Object> hMap) throws Exception {
		callDataSource(hMap);
		if (hMap.containsKey("O_VAL") && hMap.get("O_VAL").toString().indexOf("ERROR") >= 0) {
			logger.debug("[" + hMap.get("QUERY_ID") + "]" + hMap);
			throw new AjaxMsgCustomException("ERR",
					"[" + hMap.get("QUERY_ID") + "] 처리중 오류\n" + hMap.get("O_MSG").toString());
		}
		return hMap;
	}

	public Object callProcedure(String queryId, HashMap<String, Object> hMap) throws Exception {
		callDataSource(queryId, hMap);
		if (hMap.containsKey("O_VAL") && hMap.get("O_VAL").toString().indexOf("ERROR") >= 0) {
			logger.debug("[" + queryId + "]" + hMap);
			throw new AjaxMsgCustomException("ERR", "[" + queryId + "] 처리중 오류\n" + hMap.get("O_MSG").toString());
		}
		return hMap;
	}
}

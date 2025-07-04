package baekje.web.comOrd.dao;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ever.support.mybatis.SqlSessionExt;

@Repository("comOrdDao")
public class ComOrdDaoImpl extends SqlSessionExt implements ComOrdDao {
	private static final Logger logger = LoggerFactory.getLogger(ComOrdDaoImpl.class);

	@Override
	public int saveComOrdWebgate(HashMap<String, Object> paramMap) throws Exception {
		return 1;
	}

	@Override
    public int insertSearchIpHistory(HashMap<String, Object> hMap) throws Exception {
		int retCnt = 0;
		try {
			retCnt = insertDataSource("comOrd.saveSearchIpHistory", hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
    }
}

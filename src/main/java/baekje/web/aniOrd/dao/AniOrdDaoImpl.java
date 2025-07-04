package baekje.web.aniOrd.dao;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ever.support.mybatis.SqlSessionExt;

@Repository("aniOrdDao")
public class AniOrdDaoImpl extends SqlSessionExt implements AniOrdDao {
	private static final Logger logger = LoggerFactory.getLogger(AniOrdDaoImpl.class);

	@Override
	public int saveComOrdWebgate_ani(HashMap<String, Object> paramMap) throws Exception {
		return 1;
	}

	@Override
    public int insertSearchIpHistory_ani(HashMap<String, Object> hMap) throws Exception {
		int retCnt = 0;
		try {
			retCnt = insertDataSource("ani.saveSearchIpHistory_ani", hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
    }
}

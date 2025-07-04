package baekje.web.bakRcpt.dao;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ever.support.mybatis.SqlSessionExt;

@Repository("bakRcptDao")
public class BakRcptDaoImpl extends SqlSessionExt implements BakRcptDao {

	private static final Logger logger = LoggerFactory.getLogger(BakRcptDaoImpl.class);

	@Override
	public int updateBakRcptBasketItemSave(HashMap<String, Object> paramMap) throws Exception {
		int retCnt = 0;

		try {
			retCnt = updateDataSource("bakRcpt.updateBakRcptBasketItemSave", paramMap);
		} catch (Exception e) {
			logger.error("[" + paramMap.get("QUERY_ID") + "]" + paramMap + " [EXCEPTION] => " + e);
			throw e;
		}

		return retCnt;
	}
	
	@Override
	public int updateBakRcptBasketItemImsiSave(HashMap<String, Object> paramMap) throws Exception {
		int retCnt = 0;

		try {
			retCnt = updateDataSource("bakRcpt.updateBakRcptBasketItemImsiSave", paramMap);
		} catch (Exception e) {
			logger.error("[" + paramMap.get("QUERY_ID") + "]" + paramMap + " [EXCEPTION] => " + e);
			throw e;
		}

		return retCnt;
	}

	@Override
	public int updateBasketDelYn(HashMap<String, Object> paramMap) throws Exception {
		int retCnt = 0;

		try {
			retCnt = updateDataSource("bakRcpt.updateBasketDelYn", paramMap);
		} catch (Exception e) {
			logger.error("[" + paramMap.get("QUERY_ID") + "]" + paramMap + " [EXCEPTION] => " + e);
			throw e;
		}

		return retCnt;
	}

}

package baekje.web.payment.dao;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ever.support.mybatis.SqlSessionExt;

@Repository("paymentDao")
public class PaymentDaoImpl extends SqlSessionExt implements PaymentDao {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentDaoImpl.class);

	@Override
	public int insertResultPayment(HashMap<String, Object> hMap) throws Exception {
		int retCnt = 0;
		
		try {
			retCnt = insertDataSource("payment.insertResultPayment", hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}
	
	/**
	 * 결제 결과 등록
	 * <strong>작성자 : </strong>abj1<br>
	 * <strong>작성일 : </strong>2017. 12. 19.오후 4:41:51<br>
	 * 설명 : 직접입력 <br>
	 *
	 * @version 1.0
	 * @author  abj1
	 * @since   2017. 12. 19.
	 * @param   파라미터타입_직접입력  파라미터설명_직접입력
	 * @return  int 리턴에대한설명_직접입력
	 */
	@Override
	public int updateWpayUserKeyPayment(HashMap<String, Object> paramMap) throws Exception {
		int retCnt = 0;
		
		try {
			retCnt = insertDataSource("payment.updateWpayUserKeyPayment", paramMap);
		} catch (Exception e) {
			logger.error("[" + paramMap.get("QUERY_ID") + "]" + paramMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}
}

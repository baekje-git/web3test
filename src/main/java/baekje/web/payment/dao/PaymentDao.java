package baekje.web.payment.dao;

import java.util.HashMap;

public interface PaymentDao {
	int insertResultPayment(HashMap<String, Object> hMap) throws Exception;
	
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
	public int updateWpayUserKeyPayment(HashMap<String, Object> paramMap) throws Exception;
}

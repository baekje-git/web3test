package baekje.web.payment.service;

import java.util.HashMap;
import java.util.List;

public interface PaymentService {
	int saveMyCard(HashMap<String, Object> hMap) throws Exception;

	List<Object> selectMyCardList(HashMap<String, Object> hMap) throws Exception;

	HashMap<String, Object> selectMyCardDtl(HashMap<String, Object> hMap) throws Exception;

	int selectPwdChk(HashMap<String, Object> hMap) throws Exception;
	
	int insertResultPayment(HashMap<String, Object> hMap) throws Exception;
	
	int deleteMyCard(HashMap<String, Object> hMap) throws Exception;
	
	int insertWorkIngPayment(HashMap<String, Object> paramMap) throws Exception;
	
	int insertWebWpayError(HashMap<String, Object> paramMap) throws Exception;
	
	/**
	 * 결제 결과 등록
	 * <strong>작성자 : </strong>abj1<br>
	 * <strong>작성일 : </strong>2017. 12. 19.오후 4:44:28<br>
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

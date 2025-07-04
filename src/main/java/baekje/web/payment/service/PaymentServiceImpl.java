package baekje.web.payment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import baekje.web.payment.dao.PaymentDao;
import ever.cmmn.dao.CmmnDao;
import ever.cmmn.service.CmmnService;
import ever.support.security.module.sha.SHACipher;


@Service
public class PaymentServiceImpl implements PaymentService {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CmmnService cmmnService;

	@Autowired
	CmmnDao cmmnDao;
	
	@Autowired
	PaymentDao paymentDao;

	@Override
	@Transactional
	public int saveMyCard(HashMap<String, Object> hMap) throws Exception {

		String pwdSha256 = String.valueOf(hMap.get("pwd"));

		hMap.put("pwd", (new SHACipher()).encryptSHA256(pwdSha256));

		try {
			if ("C".equals(hMap.get("saveMode"))) {
				cmmnDao.insert("payment.insertMyCard", hMap);
			} else if ("U".equals(hMap.get("saveMode"))) {
				cmmnDao.update("payment.updateMyCard", hMap);
			}

			logger.info("UserInfo==>{}", hMap.toString());
		} catch (Exception e) {
			throw new Exception();
		}

		return 1;

	}

	@Override
	public List<Object> selectMyCardList(HashMap<String, Object> hMap) throws Exception {

		List<Object> list = cmmnService.selectList("payment.selectMyCardList", hMap);
		List<Object> rtnList = new ArrayList<>();

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) list.get(i);

				String cardNo = map.get("CARD_NO").toString();
				String cardNoV = cardNo.substring(12);
				String cardInfo = map.get("CARD_COMP").toString() + " " + cardNoV;

				map.put("CARD_INFO", cardInfo);

				rtnList.add(map);
			}
		}

		return rtnList;

	}
	
	@Override
	@Transactional
	public int deleteMyCard(HashMap<String, Object> hMap) throws Exception {
		return cmmnDao.delete("payment.deleteMyCard", hMap);
	}

	@Override
	public HashMap<String, Object> selectMyCardDtl(HashMap<String, Object> hMap) throws Exception {

		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("payment.selectMyCardDtl", hMap);

		return rtnMap;

	}

	@Override
	public int selectPwdChk(HashMap<String, Object> hMap) throws Exception {

		String pwdSha256 = String.valueOf(hMap.get("pwd"));

		hMap.put("pwd", (new SHACipher()).encryptSHA256(pwdSha256));

 		int cnt = (int) cmmnService.select("payment.selectPwdChk", hMap);

		return cnt;

	}

	@Override
	@Transactional
	public int insertResultPayment(HashMap<String, Object> hMap) throws Exception {
		return paymentDao.insertResultPayment(hMap);
	}
	
	@Override
	@Transactional
	public int insertWorkIngPayment(HashMap<String, Object> paramMap) throws Exception {
		return cmmnService.insert("payment.insertWorkIngPayment", paramMap);
	}
	
	@Override
	@Transactional
	public int insertWebWpayError(HashMap<String, Object> paramMap) throws Exception {
		return cmmnService.insert("payment.insertWebWpayError", paramMap);
	}
	
    @Override
	public int updateWpayUserKeyPayment(HashMap<String, Object> paramMap) throws Exception {
		return paymentDao.updateWpayUserKeyPayment(paramMap);
	}

}

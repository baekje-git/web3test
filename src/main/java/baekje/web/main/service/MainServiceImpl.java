package baekje.web.main.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.security.module.sha.SHACipher;

@Service
public class MainServiceImpl implements MainService {
	@Autowired
	CmmnService cmmnService;

	@Override
	@Transactional
	public int saveLoginPwFind(HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("main.selectLoginPwFind", hMap);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		String loginId = rtnMap.get("LOGIN_ID").toString();

        // --------------------------------------------------------------------------------
		dataMap.put("trPhone", rtnMap.get("HP_NO"));							// TR_PHONE
		dataMap.put("trMsg", rtnMap.get("CEO_NM")+"님의 비밀번호가 초기화되었습니다.");	// TR_MSG
		dataMap.put("trCallback", "");											// TR_CALLBACK
		dataMap.put("trEtc1", "WEB_ORDER");										// TR_ETC1(구분코드)
		dataMap.put("trEtc2", "");												// TR_ETC2(발송자ID)
		dataMap.put("trEtc3" , rtnMap.get("CEO_NM"));							// TR_ETC3(수신자명)
		dataMap.put("trEtc4", "백제약품 (주) 영등포지점");								// TR_ETC4(발송자명)
		dataMap.put("userId", rtnMap.get("USER_ID"));							//
		dataMap.put("loginId", rtnMap.get("LOGIN_ID"));							//
		dataMap.put("initLoginId", loginId);
		dataMap.put("encNewPassword" , (new SHACipher()).encryptSHA256(loginId));
        // --------------------------------------------------------------------------------

		// 패스워드 초기화 업데이트
		cmmnService.update("main.updatePassword", dataMap);

		// 패스워드 초기화 문자발송
		cmmnService.insert("main.insertSms2", dataMap);

		return 1;
	}
}

package baekje.web.myPage.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ever.cmmn.service.CmmnService;

@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Autowired
	CmmnService cmmnService;

	@Override
	@Transactional
	public int insertSms(HashMap<String, Object> hMap) throws Exception {
		return cmmnService.insert("myPage.insertSms", hMap);
	}

	@Override
	@Transactional
	public int insertQna(HashMap<String, Object> hMap) throws Exception {
		return cmmnService.insert("myPage.insertQna", hMap);
	}

	@Override
	@Transactional
	public int updateQna(HashMap<String, Object> hMap) throws Exception {
		return cmmnService.update("myPage.updateQna", hMap);
	}

	@Override
	@Transactional
	public int updateUserUseYn(HashMap<String, Object> hMap) throws Exception {
		return cmmnService.update("myPage.updateUserUseYn", hMap);
	}

	@Override
	@Transactional
	public int updateUserInfo(HashMap<String, Object> hMap) throws Exception {
		return cmmnService.update("myPage.updateUserInfo", hMap);
	}
	
	@Override
	@Transactional
	public int updateUserPw(HashMap<String, Object> hMap) throws Exception {
		return cmmnService.update("myPage.updateUserPw", hMap);
	}

	@Override
	@Transactional
	public int insertNewUserInfo(HashMap<String, Object> hMap) throws Exception {
		return cmmnService.insert("myPage.insertNewUserInfo", hMap);
	}

	@Override
	@Transactional
	public int updateMypageCustInfo(HashMap<String, Object> hMap) throws Exception {
		return cmmnService.update("myPage.updateMypageCustInfo", hMap);
	}

}

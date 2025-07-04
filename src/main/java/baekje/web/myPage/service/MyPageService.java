package baekje.web.myPage.service;

import java.util.HashMap;

public interface MyPageService {
	int insertSms(HashMap<String, Object> hMap) throws Exception;
	
	int insertQna(HashMap<String, Object> hMap) throws Exception;
	
	int updateQna(HashMap<String, Object> hMap) throws Exception;
	
	int updateUserUseYn(HashMap<String, Object> hMap) throws Exception;
	
	int updateUserInfo(HashMap<String, Object> hMap) throws Exception;
	
	int updateUserPw(HashMap<String, Object> hMap) throws Exception;
	
	int insertNewUserInfo(HashMap<String, Object> hMap) throws Exception;
	
	int updateMypageCustInfo(HashMap<String, Object> hMap) throws Exception;
}

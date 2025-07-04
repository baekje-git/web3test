package kada.k05.service;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.session.SessionUtil;
import kada.util.EmailSender;
import kada.util.Util;

@Service
public class ReAuthMgmtServiceImpl implements ReAuthMgmtService{
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Resource(name = "emailSender")
	private EmailSender emailSender;
	
	@Override
	public void saveUserRegist(HashMap<String, Object> hMap) throws Exception {
		String userId = String.valueOf(hMap.get("USER_ID"));
		HashMap<String, Object> basic = (HashMap<String, Object>) hMap.get("BASIC"); // 기본정보,추가정보
		HashMap<String, Object> lastEduction = (HashMap<String, Object>) hMap.get("LAST_EDUCATION"); // 최종학력
		HashMap<String, Object> reAuth = (HashMap<String, Object>) hMap.get("ReAuth"); // 최종학력
		List<HashMap<String, Object>> relatedEvent = (List<HashMap<String, Object>>) hMap.get("RELATED_EVENT"); // 유관종목
		List<HashMap<String, Object>> career = (List<HashMap<String, Object>>) hMap.get("CAREER"); // 경력사항
		List<HashMap<String, Object>> language = (List<HashMap<String, Object>>) hMap.get("LANGUAGE"); // 외국어능력

		// 기본정보,추가정보
		cmmnDao.update("k99.user.updateUSER01TB", basic);
		cmmnDao.insert("k05.ReAuth.insertREAUTH", reAuth);

		// 최종학력
		cmmnDao.delete("k99.user.deleteUSER05TB", hMap);
		lastEduction.put("EDUCATION_SEQ", "1");
		cmmnDao.insert("k99.user.insertUSER05TB", lastEduction);

		// 유관종목
		cmmnDao.delete("k99.user.deleteUSER13TB", hMap);
		int eventSeq = 0;
		for (HashMap<String, Object> row : relatedEvent) {
			row.put("USER_ID", userId);
			row.put("EVENT_SEQ", ++eventSeq);
			row.put("EVENT_CODE", row.get("code"));
			cmmnDao.insert("k99.user.insertUSER13TB", row);
		}

		// 경력사항
		cmmnDao.delete("k99.user.deleteUSER06TB", hMap);
		int careerNo = 0;
		for (HashMap<String, Object> row : career) {
			row.put("USER_ID", userId);
			row.put("CAREER_NO", ++careerNo);
			cmmnDao.insert("k99.user.insertUSER06TB", row);
		}

		// 외국어능력
		cmmnDao.delete("k99.user.deleteUSER07TB", hMap);
		int languageSeq = 0;
		for (HashMap<String, Object> row : language) {
			row.put("USER_ID", userId);
			row.put("LANGUAGE_SEQ", ++languageSeq);
			cmmnDao.insert("k99.user.insertUSER07TB", row);
		}

		// 인증번호, 인증코드
		String eduYn = String.valueOf(basic.get("EDU_YN"));
		String dcoYn = String.valueOf(basic.get("DCO_YN"));
		String bcoYn = String.valueOf(basic.get("BCO_YN"));
		String supervisorYn = String.valueOf(basic.get("SUPERVISOR_YN"));

		HashMap<String, Object> user03TB = new HashMap<>();
		user03TB.put("", userId);
		if ("Y".equals(eduYn) && "N".equals(dcoYn) && "N".equals(bcoYn)) {
			user03TB.put("AUTH_CODE","3");
		} else if (supervisorYn == null || "".equals(supervisorYn) || "undefined".equals(supervisorYn)) {
			user03TB.put("AUTH_CODE","2");
		} else {
			if ("beenant35".equals(userId) || "mir210".equals(userId)) {
                user03TB.put("AUTH_CODE","0");
            } else {
            	user03TB.put("AUTH_CODE",supervisorYn);
            }
		}
		
		HashMap<String, Object> user02TB = new HashMap<>();
		if (user03TB != null && user03TB.get("AUTH_CODE") != null && ! "".equals(user03TB.get("AUTH_CODE"))) {
            user02TB.put("AUTH_ID",basic.get("AUTH_ID"));
            user02TB.put("AUTH_CODE",user03TB.get("AUTH_CODE"));            
            cmmnDao.update("k99.user.updateUSER02TB", user02TB);
            cmmnDao.update("k99.user.updateUSER03TB", user03TB);
        }
		
	}
}

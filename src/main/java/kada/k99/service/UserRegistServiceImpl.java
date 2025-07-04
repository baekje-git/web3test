package kada.k99.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;
import ever.support.session.SessionUtil;
import kada.util.EmailSender;
import kada.util.Util;

@Service
public class UserRegistServiceImpl implements UserRegistService {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Resource(name = "emailSender")
	private EmailSender emailSender;
	
	
	@Autowired
	private RoleService roleService;

	@Override
	public void saveUserRegist(HashMap<String, Object> hMap) throws Exception {
		String userId = String.valueOf(hMap.get("USER_ID"));
		HashMap<String, Object> basic = (HashMap<String, Object>) hMap.get("BASIC"); // 기본정보,추가정보
		HashMap<String, Object> lastEduction = (HashMap<String, Object>) hMap.get("LAST_EDUCATION"); // 최종학력
		List<HashMap<String, Object>> relatedEvent = (List<HashMap<String, Object>>) hMap.get("RELATED_EVENT"); // 유관종목
		List<HashMap<String, Object>> career = (List<HashMap<String, Object>>) hMap.get("CAREER"); // 경력사항
		List<HashMap<String, Object>> language = (List<HashMap<String, Object>>) hMap.get("LANGUAGE"); // 외국어능력

		// 기본정보,추가정보
		cmmnDao.update("k99.user.updateUSER01TB", basic);	
	}

	@Override
	public void initPasswordUser(HashMap<String, Object> hMap) throws Exception {
		String newPwd = Util.newRandomPassword(10);
		
		// 비밀번호 초기화
		hMap.put("USER_PWD_ORI", newPwd);
		hMap.put("USER_PWD", Util.SHA256(newPwd));
		cmmnDao.update("k99.user.initPassword",hMap);
		
		// 초기화된 비밀번호 이메일로 전송
		Session session = emailSender.emailAuthInit();
        session.setDebug(true);

		Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(emailSender.getEmailSender()));
        
        if(emailSender.getEmailDev()) {
        	mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailSender.getEmailDevTo()));
        }else {
        	mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(String.valueOf(hMap.get("USER_EMAIL"))));
            mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress("jaeyoon@kada-ad.or.kr"));
            mimeMessage.setRecipient(Message.RecipientType.BCC, new InternetAddress("jhwan95@naver.com"));	
        }
        
        String id = "P".equals(Util.getValue("USER_FLAG", hMap))? Util.getValue("LOGIN_ID", hMap): Util.getValue("USER_ID", hMap);
        mimeMessage.setSubject("[한국도핑방지위원회] 비밀번호 초기화 안내 - ");
        StringBuffer sb = new StringBuffer();
        sb.append("회원님의 아이디/비밀번호 정보입니다. \n");        
        sb.append("성명 : " + hMap.get("USER_NAME") + " \n");        
        sb.append("아이디 : " + id + " \n");
        sb.append("비밀번호 : " + newPwd + " \n");
        sb.append("로그인 후 비밀번호를 변경해 주시기 바랍니다. \n");
        mimeMessage.setText(sb.toString());
        Transport.send(mimeMessage);
	}

	@Override
	public void saveClubManager(HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> basic = (HashMap<String, Object>) hMap.get("BASIC"); // 기본정보,추가정보
		basic.put("GSS", SessionUtil.getUserInfo());
		
		// 초기비밀번호생성 (등록에만 적용)
		String initPwd = basic.get("USER_PWD").toString();
		basic.put("USER_PWD_ORI", initPwd);
		basic.put("USER_PWD", Util.SHA256(initPwd));
		
		// 기본정보,추가정보
		cmmnDao.update("k99.user.insertClubManager", basic);
		
		// 권한생성
		roleService.triggerSaveRole(basic);
		
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
			@Override
			public void afterCommit() {
				try {
					sendPwd(basic,initPwd);
				} catch (Exception e) {					
					logger.error("관계자/선수/kist 등록후 초기비밀번호 전송 error===>",e);
				}
			}
		});
	}

	/**
	 * 사용자정보의 휴대폰,이메일중 하나로 비번정보 전송()
	 * 
	 */
	private void sendPwd(HashMap<String, Object> hMap, String Pwd) throws Exception {
		String userPhone = String.valueOf(hMap.get("USER_PHONE"));
		String userEmailPrefix = String.valueOf(hMap.get("USER_EMAIL_PREFIX"));
		String userEmailSuffix = String.valueOf(hMap.get("USER_EMAIL_SUFFIX"));
		String id = "P".equals(Util.getValue("USER_FLAG", hMap))? Util.getValue("LOGIN_ID", hMap): Util.getValue("USER_ID", hMap);
		StringBuffer sb = new StringBuffer();
        sb.append("회원님의 아이디/비밀번호 정보입니다. \n");        
        sb.append("성명 : " + hMap.get("USER_NAME") + " \n");        
        sb.append("아이디 : " + id + " \n");
        sb.append("비밀번호 : " + Pwd + " \n");
        sb.append("로그인 후 비밀번호를 변경해 주시기 바랍니다. \n");
		
		if(!StringUtils.isEmpty(userEmailPrefix) && !StringUtils.isEmpty(userEmailSuffix)) { // email
			Session session = emailSender.emailAuthInit();
	        session.setDebug(true);

			Message mimeMessage = new MimeMessage(session);
	        mimeMessage.setFrom(new InternetAddress(emailSender.getEmailSender()));
	        
	        if(emailSender.getEmailDev()) {
	        	mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailSender.getEmailDevTo()));
	        }else {
	        	mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(String.valueOf(hMap.get("USER_EMAIL"))));
	            mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress("jaeyoon@kada-ad.or.kr"));
	            mimeMessage.setRecipient(Message.RecipientType.BCC, new InternetAddress("jhwan95@naver.com"));	
	        }	        
	        
	        mimeMessage.setSubject("[한국도핑방지위원회] 비밀번호 초기화 안내 - ");	        
	        mimeMessage.setText(sb.toString());
	        Transport.send(mimeMessage);
	        logger.info("비밀번호 초기화 안내 - 이메일로 전송되었습니다.");
		}else if(!StringUtils.isEmpty(userPhone)) { // sms
	        logger.info("비밀번호 초기화 안내 - 핸드폰으로 전송되었습니다.");
		}else {
			logger.info("비밀번호 초기화 안내 - 핸드폰,이메일정보가 존재하지 않습니다.");
		}
		
	}
}

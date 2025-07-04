package kada.util;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "emailSender")
	private EmailSender emailSender;
	
	@Async
	public void playerRegSendMail(HashMap<String, Object> hMap) throws Exception {
		logger.info("사용자등록 안내 메일발송");
		// 초기화된 비밀번호 이메일로 전송
		Session session = emailSender.emailAuthInit();
        session.setDebug(true);

		Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(emailSender.getEmailSender()));
        
        if(emailSender.getEmailDev()) {
        	mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailSender.getEmailDevTo()));
        }else {
        	mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(hMap.get("USER_EMAIL_PREFIX")+"@"+hMap.get("USER_EMAIL_SUFFIX")));
            mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress("jaeyoon@kada-ad.or.kr"));
            mimeMessage.setRecipient(Message.RecipientType.BCC, new InternetAddress("jhwan95@naver.com"));	
        }
        
        mimeMessage.setSubject("[한국도핑방지위원회] 사용자등록 안내 - ");
        StringBuffer sb = new StringBuffer();
        sb.append("회원님의 아이디/비밀번호 정보입니다. \n");        
        sb.append("성명 : " + hMap.get("USER_NAME") + " \n");        
        sb.append("로그인 아이디 : " + hMap.get("USER_ID") + " \n");
        sb.append("비밀번호 : " + hMap.get("USER_PWD_ORI") + " \n");
        sb.append("로그인 후 비밀번호를 변경해 주시기 바랍니다. \n");
        mimeMessage.setText(sb.toString());
        Transport.send(mimeMessage);
	}
}

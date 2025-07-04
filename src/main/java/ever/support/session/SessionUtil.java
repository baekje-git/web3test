package ever.support.session;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 
 * <pre>
 * -------------------------------------------------------------
 * @프로젝트 : ever
 * @파일경로 : ever.support.session
 * @파일이름 : SessionUtil.java 
 * @작성일자 : 2019. 12. 6. 
 * @작성자명 : KMSDEV 
 * @상세설명 : 세션관리
 * -------------------------------------------------------------
 * </pre>
 */
public class SessionUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);
        
    public static void setSessionLoginInfo(HttpServletRequest request,  HashMap<String, Object> loginInfo) throws Exception {
        // 세션객체 생성
        HttpSession session = request.getSession();
        String sessionId    = session.getId();
        
        // 세션값 담기
        session.setAttribute("GSS_SESSION_ID" , sessionId);
        session.setAttribute("GSS_USER_INFO"  , loginInfo);
    }
    
    public static Object getSessionLoginID(HttpSession session){
        Object userInfo = session.getAttribute("GSS_SESSION_ID");
        return userInfo;
    }
    
    public static HashMap<String, Object> getSessionLoginInfo(HttpSession session){
        HashMap<String, Object> userInfo = (HashMap<String, Object>)session.getAttribute("GSS_USER_INFO");
        return userInfo;
    }
    
    public static HashMap<String, Object> getUserInfo() throws Exception {
        return (HashMap<String, Object>)RequestContextHolder.getRequestAttributes().getAttribute("GSS_USER_INFO", RequestAttributes.SCOPE_SESSION); 
    }
    
    public static void logout(HttpSession session){
        //session.invalidate();
        session.removeAttribute("GSS_USER_INFO");
        session.removeAttribute("GSS_SESSION_ID");        
    }
    
}

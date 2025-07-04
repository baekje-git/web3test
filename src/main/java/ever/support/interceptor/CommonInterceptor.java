package ever.support.interceptor;

import java.io.File;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import ever.cmmn.service.CmmnService;
import ever.dbsession.service.DbSessionService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.date.DateUtil;
import ever.support.jackson.CustomObjectMapper;
import ever.support.session.SessionUtil;

/**
 * 
 * <pre>
 * @FileName : ever.support.interceptor.CommonInterceptor.java
 * @Date     : 2019. 12. 3. 
 * @Writer   : KMSDEV
 * @Description :
 * </pre>
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
    
    @Autowired
    private CmmnService cmmnService;
            
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //logger.debug("==================== preHandle ========================== ");
        boolean result = false;
        boolean isSessionTimeout = false;
        boolean isChangeIp = false;
        
        String uri = request.getRequestURI().toString().trim();
        logger.debug(">>>> uri : " + uri);
        
        
        logger.debug("===> CmmnUtil.toJsonString(request) : " + CmmnUtil.toJsonString(request));
        
        request.setAttribute("systemDate"  , DateUtil.getCurrDate()); 
        request.setAttribute("paramToJson" , CmmnUtil.toJsonString(request)); 
        
        /*
        response.setHeader("pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.addHeader("Cache-Control","No-store");
        response.setDateHeader("Expires",1L);
        */
        
        // ------------------------------------------------------------------------
        // 요청시간
        long preTime = System.currentTimeMillis();
        request.setAttribute("preTime", preTime);
        // ------------------------------------------------------------------------
        // 세션객체생성
        HttpSession session = request.getSession();
        //logger.debug(">>> session : " + session);
        
        // ------------------------------------------------------------------------
        // 세션정보가져오기
        HashMap<String, Object> loginInfo = SessionUtil.getSessionLoginInfo(session);
        //logger.debug(">>> 세션정보 : " + loginInfo);
        
        // ------------------------------------------------------------------------
        // 세션아이디가져오기
        Object sessionId = SessionUtil.getSessionLoginID(session);
        //logger.debug(">>> 세션키값 : " + sessionId );
        
        // ------------------------------------------------------------------------
        // Root
        String webRoot = request.getContextPath();
        //logger.debug("webRoot : " + webRoot );
        
        // ------------------------------------------------------------------------
        // STEP 1. 대상경로 여부체크
        // ------------------------------------------------------------------------
        if(excludeURL(request)) {
            
            // ------------------------------------------------------------------------
            // STEP 1-1. 체크대상경로에서 제외
            // ------------------------------------------------------------------------
            //logger.debug("STEP 1-1. 로그인 및 로그아웃 요청은 체크대상에서 제외합니다.");
            //if(!StringUtils.isEmpty(String.valueOf(sessionId))) {}
            if(!StringUtils.isEmpty(String.valueOf(sessionId))) {
                //response.sendRedirect(webRoot + Gv.HOME_VIEW);
            }
            result = true;
            
        } else {
                        
            
            /** DB 세션 처리 흐름
            A.세션이 없을경우 sesesionID를 이용하여 조회 
                A-1) 레코드가 없는경우 -> 로그인 불가   
                A-2) 세션시간  유효하고 접속 아이피 일치시 
                    result = true;
                A-3) 세션시간  유효하지 않거나 접속 아이피 불일치시 -> 로그인 불가                      
            B.세션이 있을 경우 
                B-1). 일치할경우 result = true;  
                B-2)  접속PC 아이피 체크하여 접근아이피 불일치시 로그아웃처리  
                
                          
            C.공통처리
                                로그인성공.result==true 일경우                                      
                   C-1) 로그인처리
                   C-2) DB세션 정보 저장
                   C-3) DB세션 시간 연장처리    
                      
                                시간만료된건 레코드 delete(처리 고려중)                
           */         

            
            String jsessionId = session.getId();
            
            
            // 쿠키로 부터 세션id 가져오기 
            Cookie loginCookie = WebUtils.getCookie(request,Gv.JSESSIONID_COOKIE);
            if(loginCookie != null) {
                jsessionId = loginCookie.getValue();
            }            
            
            //A.세션이 없을경우 
            if(sessionId == null) {            
            	result = false;
            }
            //B.세션이 있을 경우 
            else {
                result = true;                        
            }
                
            //C. 공통처리
            if(result == false) {
                String msg = "";
                
                // ajax일경우 
                if(isAjaxRequest(request)) {                    
                    response.setContentType("text/html; charset=UTF-8");
                    response.setStatus(401);
                    PrintWriter out = response.getWriter();
                    out.println("{\"errorCode\":\"401\",\"errorMsg\":\"로그인 시간이 만료되었습니다.\"}");
                    out.flush();
                    
                } else {                    
                    response.setContentType("text/html; charset=UTF-8"); 
                    PrintWriter out = response.getWriter();
                    if(uri.indexOf("/popup/") > -1) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("   window.close();"); 
                        out.println("   alert('로그인 시간이 만료되었습니다.\\n로그인 페이지로 이동합니다.');");
                        out.println("   window.opener.top.location.replace('" + webRoot + Gv.LOGIN_VIEW + "');");
                        out.println("</script>");
                        out.flush(); 
                    } else {                        
                        response.setContentType("text/html; charset=UTF-8"); 
                        out.println("<script type=\"text/javascript\">");
                        
                        if(isChangeIp) 
                            out.println("   alert('다른 IP로 접속하였습니다. 해당 창은 로그아웃됩니다.\\n로그인 페이지로 이동합니다.');");
                        
                        
                        out.println("   parent.location.replace('" + webRoot + Gv.LOGIN_VIEW + "');");
                        out.println("</script>");
                        out.flush(); 
                    }
                }                
            }
            //로그인 성공시 
            else {
              
                request.getSession().setMaxInactiveInterval(Gv.SESSION_TIMEOUT); //타임아웃지정
            }
        }
        return result;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        logger.debug("==================== postHandle ========================= ");
        String uri = request.getRequestURI().toString().trim();
        if(modelAndView != null) {
        	logger.debug(">>>> modelAndView is not null");
        	logger.debug(">>>> uri : " + uri);
            
            CustomObjectMapper mapper = new CustomObjectMapper();
            
            HashMap<String, Object> userInfo = SessionUtil.getSessionLoginInfo(request.getSession());
            
            if(uri.equals(Gv.INDEX_VIEW)) {
                
                List<Object> topMenuList = cmmnService.selectList("cmmn.selectTopMenuList", userInfo);
                request.setAttribute("topMenuList", topMenuList);
                
                List<Object> subMenuList = cmmnService.selectList("cmmn.selectSubMenuList", userInfo);
                request.setAttribute("subMenuList", subMenuList);
                
                //request.setAttribute("GSS"   , mapper.writeValueAsString(userInfo));
                request.setAttribute("GSS", userInfo);
                
            } else if(!excludeURL(request)) {
                
                String menuInfo = request.getParameter("MENU_INFO");
                if(!StringUtils.isEmpty(menuInfo)) {
                    Map<String, Object> menuInfoMap = mapper.readValue(menuInfo, new TypeReference<Map<String, Object>>() {});
                    
                    // ------------------------------------------------------------------------
                    // FOOTER INFO
                    // ------------------------------------------------------------------------
                    ServletContext context = request.getSession().getServletContext();
                    String fileName = modelAndView.getViewName();
                    String filePath = context.getRealPath("WEB-INF/views/" + fileName + ".jsp").replaceAll("//", File.separator);
                    
                    File file = new File(filePath);
                    long millisec = 0;
                    boolean fileCheck = file.isFile();
                    if(fileCheck) {
                        filePath = file.getPath();         // 파일 위치
                        millisec = file.lastModified();    // 파일 수정시간
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String lastModDate = dateFormat.format(millisec);
                    String lastMenuId  = (String) menuInfoMap.get("MENU_ID");
                    String lastPgmId   = (String) menuInfoMap.get("PGM_ID");
                    
                    request.setAttribute("LAST_MENU_ID"  , lastMenuId);
                    request.setAttribute("LAST_PGM_ID"   , lastPgmId);
                    request.setAttribute("LAST_MOD_DATE" , lastModDate);
                    
                    // ------------------------------------------------------------------------
                    // NAVI INFO
                    // ------------------------------------------------------------------------
                    String menuPkId    = (String) menuInfoMap.get("VIEW_PKID");
                    String menuNavi    = (String) menuInfoMap.get("MENU_NAVI");
                    logger.debug(">>>> 메뉴고유키값 : " + menuPkId);
                    logger.debug(">>>> 메뉴경로정보 : " + menuNavi);
                    String navi1 = "";
                    String navi2 = "";
                    String navi3 = "";
                    if(!StringUtils.isEmpty(menuNavi)) {
                        List<String> navigation = Arrays.asList(menuNavi.split("," , -1));
                        navi1 = navigation.get(0);
                        navi2 = navigation.get(1);
                        navi3 = navigation.get(2);
                        // navi3 = navigation.get(2) + "("+ menuPkId +")";
                    }
                    
                    // ------------------------------------------------------------------------
                    // AUTH INFO
                    // ------------------------------------------------------------------------
                    String menuAuth    = (String) menuInfoMap.get("MENU_AUTH");
                    logger.debug(">>>> 메뉴권한정보 : " + menuAuth);
                    if(!StringUtils.isEmpty(menuAuth)) {
                        StringBuffer titleAreaHtml = new StringBuffer();
                        titleAreaHtml.append("<div class=\"title_area\" id=\"top_nav_area\" data-menu-nav=\""+menuNavi+"\" data-menu-pk-id=\""+menuPkId+"\" data-page-menu-id=\""+lastMenuId+"\" data-page-pgm-id=\""+lastPgmId+"\" data-page-mod-date=\""+lastModDate+"\">");
                        titleAreaHtml.append("<div class=\"sub_title\">"+navi1+" > "+navi2+" > <span>"+navi3+"</span>");
                        titleAreaHtml.append("\r\n");
                        titleAreaHtml.append("(<a href=\"javascript:PopUtil.openHelpLink('"+lastMenuId+"');\" class=\"helpLink\" ondragstart=\"return false\"><span>도움말</span></a>)");
                        titleAreaHtml.append("</div>"); 
                        titleAreaHtml.append("<ul class=\"buarea\">");
                        
                        // ================================================================================================================================================================
                        // 조회
                        if(menuAuth.charAt(0) == "1".charAt(0)){
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_SEARCH\"><img src=\"/common/images/ico_search.png\"><span>조회</span></a></li>");
                        }
                        // 입력
                        if(menuAuth.charAt(1) == "1".charAt(0)){
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_CREATE\"><img src=\"/common/images/ico_input.png\"><span>입력</span></a></li>");
                        }
                        // 삭제
                        if(menuAuth.charAt(2) == "1".charAt(0)){
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_DELETE\"><img src=\"/common/images/ico_delete.png\"><span>삭제</span></a></li>");
                        }
                        // 저장
                        if(menuAuth.charAt(3) == "1".charAt(0)){
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_SAVE\"><img src=\"/common/images/ico_save.png\"><span>저장</span></a></li>");
                        }
                        // 엑셀
                        if(menuAuth.charAt(4) == "1".charAt(0)){
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_EXCEL\"><img src=\"/common/images/ico_excel.png\"><span>엑셀</span></a></li>");
                        }
                        // 출력
                        if(menuAuth.charAt(5) == "1".charAt(0)){
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_PRINT\"><img src=\"/common/images/ico_print.png\"><span>출력</span></a></li>");
                        }
                        // 취소
                        if(menuAuth.charAt(6) == "1".charAt(0)){
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_CANCEL\"><img src=\"/common/images/ico_cancel.png\"><span>취소</span></a></li>");
                        }
                        // 승인
                        if(menuAuth.indexOf("gf_aprvl") > 1 ) {
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_APPROVAL\"><img src=\"/common/images/ico_appro.png\"><span>승인</span></a></li>");
                        }
                        // 발주확정
                        if(menuAuth.indexOf("gf_order") > 1 ) {
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_ORDER\"><img src=\"/common/images/ico_order.png\"><span>발주확정</span></a></li>");
                        }
                        // 불러오기
                        if(menuAuth.indexOf("gf_load") > 1 ) {
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_MST_LOAD\"><span>불러오기</span></a></li>");
                        }
                        // 문자전송
                        if(menuAuth.indexOf("gf_send1") > 1 ) {
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_SMS_SEND\"><img src=\"/common/images/ico_smssend.png\"><span>문자전송</span></a></li>");
                        }
                        // 메일전송
                        if(menuAuth.indexOf("gf_send2") > 1 ) {
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_EMAIL_SEND\"><img src=\"/common/images/ico_emailsend.png\"><span>메일전송</span></a></li>");
                        }
                        // 초기화
                        if(menuAuth.indexOf("gf_reset") > 1 ) {
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_MST_RESET\"><img src=\"/common/images/ico_reset.png\"><span>초기화</span></a></li>");
                        }
                        // 확정
                        if(menuAuth.indexOf("gf_confirm") > 1 ) {
                            titleAreaHtml.append("  <li><a href=\"javascript:void(0);\" id=\"BTN_CONFIRM\"><img src=\"/common/images/ico_order.png\"><span>확정</span></a></li>");
                        }
                        // ================================================================================================================================================================
                        
                        titleAreaHtml.append("</ul>");
                        titleAreaHtml.append("</div>"); 
                        request.setAttribute("titleAreaHtml",titleAreaHtml.toString());
                    }
                }
                
              //request.setAttribute("GSS"   , mapper.writeValueAsString(userInfo));
                request.setAttribute("GSS"   , userInfo);
              //request.setAttribute("GSS"   , mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userInfo));
            }
            
        } else {
            //logger.debug(">>>> modelAndView is null");
            //logger.debug(">>>> uri : " + uri);
        }
        
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        HandlerMethod hm = (HandlerMethod) handler;
        String reqUrl = request.getRequestURI();
        // 응답시간
        long curTime  = System.currentTimeMillis();
        long preTime  = (long)request.getAttribute("preTime");
        long proTime  = curTime - preTime;
        logger.debug("───────────────────────────────────────────────────────── ");
        logger.debug("class  : " + hm.getBeanType().getCanonicalName());
        logger.debug("method : " + hm.getMethod().getName());
        logger.debug("uri    : " + reqUrl );
        logger.debug("complete time : " + (proTime / 1000.0) + "(sec)");
        logger.debug("───────────────────────────────────────────────────────── ");
    }
    
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //logger.debug("============= afterConcurrentHandlingStarted ============ ");
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
    
    /**
     * 
     * <pre>
     * @MethodName : isAjaxRequest
     * @Date       : 2019. 12. 6. 오후 1:46:10
     * @Writer     : KMSDEV
     * @Description : 비동기 통신여부 확인
     * @param request
     * @return
     * </pre>
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        HashMap<String, Object> pMap = CmmnUtil.getParameters(request);
        logger.debug("pMap : " + pMap);
        String ajaxHeader = StringUtils.defaultString(request.getHeader(Gv.AJAX_HEADER_KEY));
        logger.debug("통신유형 : " + ajaxHeader);
        if(ajaxHeader.equals(Gv.AJAX_HEADER_VAL)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * <pre>
     * @MethodName : excludeURL
     * @Date       : 2019. 12. 6. 오후 1:46:12
     * @Writer     : KMSDEV
     * @Description : 요청대상 URI 존재여부 확인
     * @param request
     * @return
     * </pre>
     */
    private boolean excludeURL(HttpServletRequest request) {
        String uri = request.getRequestURI().toString().trim();
        
        if (uri.indexOf(Gv.LOGIN_VIEW) > -1 || uri.indexOf(Gv.LOGOUT_VIEW) > -1 || uri.indexOf("/api/") > -1 
        		|| uri.indexOf("/login/") > -1 || uri.indexOf("/sample/") > -1 || uri.indexOf("/sample2/") > -1 ){
            return true;
        } else {
            return false;
        }
    }
    
}

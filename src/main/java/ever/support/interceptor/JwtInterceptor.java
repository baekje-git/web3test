package ever.support.interceptor;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ever.cmmn.service.CmmnService;
import ever.support.exception.JwtCustomException;
import ever.support.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
public class JwtInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CmmnService cmmnService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
	        return true;
	    }
		
		Map<String, String[]> paramMap = request.getParameterMap();

	    String uri = request.getRequestURI();
	    System.out.println("🔍 요청 URI = " + uri);
		// logger.debug("Interceptor > preHandle uri  : {}, {}", uri, jwtToken);

		if (   uri.indexOf("wpaystdPayAuthCardRequest") >= 0 
        	|| uri.indexOf("wpaystdPayAuthCardReturn") >= 0
        	|| uri.indexOf("wpaystdMemRegForm") >= 0
        	|| uri.indexOf("acceptanceFile") >= 0
            || uri.indexOf("agreementFileDownLoad2") >= 0
        	|| uri.indexOf("wpaystdMemRegRequest") >= 0
        	|| uri.indexOf("wpaystdMemRegReturn") >= 0
        	|| uri.indexOf("wpaystdCardRegRequest") >= 0
        	|| uri.indexOf("wpaystdCardRegReturn") >= 0
        	//|| url.indexOf("getWpayUserKey") >= 0
        	|| uri.indexOf("/onuriLogin") >= 0
        	|| uri.indexOf("/coldChain") >= 0
        	|| uri.indexOf("/shinhanLogin") >= 0
	        || uri.indexOf("/loginChk") >= 0
            || uri.equals("/onuriLoginChk.ajx")
            || uri.equals("/shinhanLoginChk.ajx")
            || uri.equals("/mdpharmLoginChk.ajx")
            || uri.equals("/chpharmLoginChk.ajx")
            || uri.equals("/dgpharmLoginChk.ajx")
            || uri.equals("/reinpharmLoginChk.ajx")
            || uri.equals("/nuripharmLoginChk.ajx")
            || uri.equals("/mediplanLoginChk.ajx")
            || uri.equals("/anshimLoginChk.ajx")
            || uri.equals("/eghisLoginChk.ajx")
            || uri.equals("/bukwangLoginChk.ajx")
            || uri.equals("/makerLogin.ajx")
            || uri.equals("/makerLoginChk.ajx")
            || uri.equals("/logout.act")
            || uri.equals("/updatePassword.ajx")
            || uri.equals("/mng/saveBoardNotice.ajx")
            || uri.startsWith("/excel")
            || uri.equals("/loginChkM.ajx")
            || uri.equals("/loginChkMis.ajx")
            || uri.equals("/api/StoreList.ajx")
            || uri.equals("/api/UserCheck.ajx")
            || uri.equals("/api/login.ajx")
            || uri.equals("/apiSaleInfo/SaleInfo.ajx")    /* 신성,대성 */
            || uri.equals("/apiSaleInfo/SaleDtlInfo.ajx") /* 신성,대성 */
            || uri.equals("/apiOnConnect/saleMaster.ajx") /* 온커넥트 */
            || uri.equals("/apiOnConnect/saleDetail.ajx") /* 온커넥트 */
            || uri.equals("/apiOnConnect/saleSerial.ajx") /* 온커넥트 */
            || uri.equals("/apiOnConnect/codeInfo.ajx")   /* 온커넥트 */
            || uri.indexOf("/apiMediPlan/") >= 0          /* 메디플랜 */
            || uri.indexOf("/apiOptima/") >= 0            /* 옵티마 */
            || uri.indexOf("/apiMdPharm/") >= 0           /* 엠디약품 */
            || uri.indexOf("/apiMobileBack/") >= 0        /* 바코드반품(앱) */
            || uri.indexOf("/apiShopStock/") >= 0         /* 쇼핑몰재고조회 */
            || uri.indexOf("/apiVanPharm/") >= 0          /* 반팜(앱반품) */
            || uri.indexOf("/apiAiOcr/") >= 0             /* AIOCR */
            || uri.indexOf("getOnuriInfo") >= 0 
            || uri.indexOf("getShinhanInfo") >= 0 
        	) {
			logger.info("Interceptor > preHandle session check passed  : {}", uri);
			return true;
		}

		try {
		    String paramLog = paramMap.entrySet().stream()
		        .map(entry -> {
		            String key = entry.getKey();
		            String value;
		            try {
		                value = key.toLowerCase().contains("password") ? "******"
		                        : String.join(", ", entry.getValue());
		            } catch (Exception e) {
		                value = "[ERROR]";
		            }
		            return key + "=" + value;
		        })
		        .collect(Collectors.joining(", "));

		    logger.info("[{}] {}{}  {}",
		        request.getMethod(),
		        request.getRequestURI(),
		        request.getQueryString() != null ? "?" + request.getQueryString() : "",
		        paramLog
		    );
		} catch (Exception e) {
		    logger.warn("[{}] {}{}  파라미터 로깅 중 오류 발생",
		        request.getMethod(),
		        request.getRequestURI(),
		        request.getQueryString() != null ? "?" + request.getQueryString() : ""
		    );
		}
			
		/* String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION); */
		String jwtToken = resolveToken(request);
		
		//logger.debug("Interceptor > preHandle : {}, {}", uri, jwtToken);
		if (jwtToken != null) {
			try {
				if (jwtUtil.isUsable(jwtToken)) { // JWT 토큰이 유효하면
//					String userId = jwtUtil.getUserId(jwtToken);
//					HashMap<String, Object> loginInfo = new HashMap<>();
//					loginInfo.put("USER_ID",userId);
//					loginInfo.put("IP_ADDR",Util.getRemortIP(request));
//					loginInfo.put("USER_AGENT", request.getHeader("User-Agent"));
					
					/* 히스토리 테이블 저장 */
					/* 
					 등록(/insert~), 수정(/update~), 삭제(/delete~) 등
					 약속된 url이 있다면, 그것만 indexOf로 분기해서 히스토리에 저장시킬 수 있음.
					*/
//					logger.debug("getRequestURI ========>{}", request.getRequestURI());
//					String reqUrl = StringUtils.isEmpty(request.getRequestURI()) ? "" : request.getRequestURI(); 
//					
//					if (reqUrl.contains("/insert")) {
//						HashMap<String, Object> hMap = new HashMap<>();
//						hMap.put("ACT_CD", "C");
//						hMap.put("userData", loginInfo);
//						hMap.put("PGM_URL", request.getRequestURI());
//						cmmnService.insert("cmmn.insertHistory", hMap);
//					} else if (reqUrl.contains("/update") || reqUrl.contains("/delete")) {
//						HashMap<String, Object> hMap = new HashMap<>();
//						hMap.put("ACT_CD", "U");
//						hMap.put("userData", loginInfo);
//						hMap.put("PGM_URL", request.getRequestURI());
//						cmmnService.insert("cmmn.insertHistory", hMap);
//					}
					return true;
				}				
				throw new JwtCustomException("1.유효한 인증토큰이 존재하지 않습니다.");
			} catch (MalformedJwtException e) { // 위조 시도
				logger.error("Malformed Jwt Token: {}", e.getMessage());				
				throw new JwtCustomException("위조된 인증토큰입니다.");
			} catch (ExpiredJwtException e) { // 만료된 토큰
				logger.error("ExpiredJ Jwt Token: {}", e.getMessage());				
				throw new JwtCustomException("만료된 토큰입니다.");
			}
		} else { // 토큰이 없음			
			if (uri.contains("/token")) { // 토큰 발급
				//logger.debug("Generate Token, Request URI: {}", uri);
				return true;
			}			
			throw new JwtCustomException("2.유효한 인증토큰이 존재하지 않습니다.");
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//super.postHandle(request, response, handler, modelAndView);
		response.setHeader("X-UA-Compatible", "IE=edge");
		//logger.debug("Interceptor > postHandle");
		String url = request.getRequestURI();
		if (url.indexOf("wpaystdPayAuthCardRequest") >= 0 
        	|| url.indexOf("wpaystdPayAuthCardReturn") >= 0
        	|| url.indexOf("wpaystdMemRegForm") >= 0
        	|| url.indexOf("wpaystdMemRegRequest") >= 0
        	|| url.indexOf("wpaystdMemRegReturn") >= 0
        	//|| url.indexOf("getWpayUserKey") >= 0
        	) {
        	String getID = "";
        	javax.servlet.http.Cookie[]  cookies = request.getCookies();
        	if (cookies != null) {
        		for (javax.servlet.http.Cookie c : cookies) {
        			String name = c.getName();
        			String value = c.getValue();
        			//logger.debug("===========> name : " + name + ", value : " + value);
        			if (name.equals("Web_JSESSIONID")) {
        				getID = value;
        			}
        		}
        	}
        	
        	//String getID = request.getSession().getId();
         	//logger.debug("===========> wpaystdPay in url : " + url + ", getID : " + getID);
         	response.setHeader("SET-COOKIE", "Web_JSESSIONID="+getID+"; Path=/; Secure; SameSite=None");
        }
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
		//logger.debug("Interceptor > afterCompletion");
	}
	
	/**
     * Request Header에서 토큰 정보를 꺼내오기 위한 resolveToken 메서드
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
	
}

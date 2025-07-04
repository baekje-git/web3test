package ever.inicis.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inicis.std.util.HttpUtil;
import com.inicis.std.util.ParseUtil;
import com.inicis.std.util.SignatureUtil;

import ever.inicis.service.InicisService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;

/**
 * 
 * <pre>
 * -------------------------------------------------------------
 * &#64;프로젝트 : ever
 * &#64;파일경로 : ever.inicis.controller
 * &#64;파일이름 : InicisController.java 
 * &#64;작성일자 : 2024. 1. 5. 
 * &#64;작성자명 :  
 * &#64;상세설명 : 이니시스 결제 컨트롤러
 * -------------------------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping("/inicis")
public class InicisController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InicisService inicisService;

	@RequestMapping("/reqPayment")
    public String reqPayment(HttpServletRequest request, Model model) throws Exception {
		//request 받아서 전달
		HashMap<String, Object> hMap = CmmnUtil.getParameters(request);
		logger.info(">>> hMap111111111 : " + hMap);
		
		
		//추가 필수 데이터
		hMap.put("mid", "INIpayTest");
		hMap.put("mKey", SignatureUtil.hash("SU5JTElURV9UUklQTEVERVNfS0VZU1RS", "SHA-256"));
		hMap.put("timestamp", SignatureUtil.getTimestamp());
		hMap.put("orderNumber", "INIpayTest_"+SignatureUtil.getTimestamp());
		hMap.put("use_chkfake", "Y");
		
		HashMap<String, String> signParam = new HashMap<String, String>();
		signParam.put("oid", hMap.get("orderNumber").toString());
		signParam.put("price", hMap.get("price").toString());
		signParam.put("timestamp", hMap.get("timestamp").toString());
		
		String signature = SignatureUtil.makeSignature(signParam);
		
		hMap.put("signature", signature);
		
		signParam.put("signKey", "SU5JTElURV9UUklQTEVERVNfS0VZU1RS");
		
		String verification = SignatureUtil.makeSignature(signParam);
		
		hMap.put("verification", verification);
		
		
		logger.info(">>> hMap22222222222 : " + hMap);
		//inicisService.saveOrder(pcPayRequest.getOid(), pcPayRequest.getTimestamp(), pcPayRequest.getSignature(), pcPayRequest.getPrice());
		//inicisService.saveOrder(hMap.get("oid").toString(), hMap.get("timestamp").toString(), hMap.get("signature").toString(), hMap.get("price").toString());
		
		
		model.addAttribute("hMap", hMap);
		
		//payService.saveOrder(pcPayRequest.getOid(), pcPayRequest.getTimestamp(), pcPayRequest.getSignature(), pcPayRequest.getPrice());
        //model.addAttribute("pay", pcPayRequest);
		
        return "inicis/reqPayment";
    }
	
	
	@RequestMapping("/resPayment")
	public String resPayment(HttpServletRequest request, HttpServletResponse response , RedirectAttributes redirectAttributes ) throws Exception {
		//응답 결과를 받아서 내부에서 처리
		//HashMap<String, Object> hMap = CmmnUtil.getParameters(request);
		//inicisService.checkPcReturn(hMap);
		
		CmmnUtil.methodLog("결제 return");
        Map<String, String> resultMap = new HashMap<String, String>();

    	try{

    		//#############################
    		// 인증결과 파라미터 일괄 수신
    		//#############################
    		request.setCharacterEncoding("UTF-8");

    		Map<String,String> paramMap = new Hashtable<String,String>();
    		
    		Enumeration elems = request.getParameterNames();
    		
    		String temp = "";

    		while(elems.hasMoreElements())
    		{
    			temp = (String) elems.nextElement();
    			paramMap.put(temp, request.getParameter(temp));
    		}
    		
    		//##############################
    		// 인증성공 resultCode=0000 확인
    		// IDC센터 확인 [idc_name=fc,ks,stg]	
    		// idc_name 으로 수신 받은 값 기준 properties 에 설정된 승인URL과 authURL 이 같은지 비교
    		// 승인URL은  https://manual.inicis.com 참조
    		//##############################
    		
    		if("0000".equals(paramMap.get("resultCode")) && paramMap.get("authUrl").equals(ResourceBundle.getBundle("properties/idc_name").getString(paramMap.get("idc_name")))){			
    			
    			logger.info("####인증성공/승인요청####");

    			//############################################
    			// 1.전문 필드 값 설정(***가맹점 개발수정***)
    			//############################################
    			
    			String mid 		= paramMap.get("mid");
    			String timestamp= SignatureUtil.getTimestamp();
    			String charset 	= "UTF-8";
    			String format 	= "JSON";
    			String authToken= paramMap.get("authToken");
    			String authUrl	= paramMap.get("authUrl");
    			String netCancel= paramMap.get("netCancelUrl");	
    			String merchantData = paramMap.get("merchantData");
    			
    			//#####################
    			// 2.signature 생성
    			//#####################
    			Map<String, String> signParam = new HashMap<String, String>();

    			signParam.put("authToken",	authToken);		// 필수
    			signParam.put("timestamp",	timestamp);		// 필수

    			// signature 데이터 생성 (모듈에서 자동으로 signParam을 알파벳 순으로 정렬후 NVP 방식으로 나열해 hash)
    			String signature = SignatureUtil.makeSignature(signParam);
    			
    			signParam.put("signKey",	"SU5JTElURV9UUklQTEVERVNfS0VZU1RS");		// 필수
    			
    			// signature 데이터 생성 (모듈에서 자동으로 signParam을 알파벳 순으로 정렬후 NVP 방식으로 나열해 hash)
    			String verification = SignatureUtil.makeSignature(signParam);
    			

    			//#####################
    			// 3.API 요청 전문 생성
    			//#####################
    			Map<String, String> authMap = new Hashtable<String, String>();
    			
    			authMap.put("mid"			,mid);			// 필수
    			authMap.put("authToken"		,authToken);	// 필수
    			authMap.put("signature"		,signature);	// 필수
    			authMap.put("verification"	,verification);	// 필수
    			authMap.put("timestamp"		,timestamp);	// 필수
    			authMap.put("charset"		,charset);		// default=UTF-8
    			authMap.put("format"		,format);

    			HttpUtil httpUtil = new HttpUtil();

    			try{
    				//#####################
    				// 4.API 통신 시작
    				//#####################

    				String authResultString = "";
    				
    				authResultString = httpUtil.processHTTP(authMap, authUrl);
    				
    				//############################################################
    				//6.API 통신결과 처리(***가맹점 개발수정***)
    				//############################################################
    				
    				String test = authResultString.replace(",", "&").replace(":", "=").replace("\"", "").replace(" ","").replace("\n", "").replace("}", "").replace("{", "");
    							
    				resultMap = ParseUtil.parseStringToMap(test); //문자열을 MAP형식으로 파싱
    				
    				logger.info(">>> resultMap : " + resultMap);
    				
    				
    				//결제 정보 저장
    				HashMap<String, Object> dataMap = new HashMap<>();
    				
    				//HashMap<String, Object> convert to HashMap<String, Object>
    				dataMap.putAll(resultMap);
    				
    				logger.info(">>> dataMap : " + dataMap);
    				
    				//db에 결제정보 저장
    				//inicisService.savePayment(dataMap);
    				
    				// 수신결과를 파싱후 resultCode가 "0000"이면 승인성공 이외 실패
    			   	//throw new Exception("강제 망취소 요청 Exception ");
    				
    			  	
    			} catch (Exception ex) {

    				//####################################
    				// 실패시 처리(***가맹점 개발수정***)
    				//####################################

    				//---- db 저장 실패시 등 예외처리----//
    				System.out.println(ex);

    				//#####################
    				// 망취소 API
    				//#####################
    				String netcancelResultString = httpUtil.processHTTP(authMap, netCancel);	// 망취소 요청 API url(고정, 임의 세팅 금지)

    				logger.error("## 망취소 API 결과 ##");

    				// 망취소 결과 확인
    				logger.error("## netcancelResultString ##" + netcancelResultString);
    			}

    		}else{
    			
    			resultMap.put("resultCode", paramMap.get("resultCode"));
    			resultMap.put("resultMsg", paramMap.get("resultMsg"));
    		}

    	}catch(Exception e){

    		System.out.println(e);
    		logger.error(" InicisController resPayment [EXCEPTION] => " + e);
    	}
    	
    	
    	//return "inicis/completePayment";
    	
    	redirectAttributes.addAttribute("MSG", "success");
//    	return "redirect:http://localhost:3050/dist/payment";
    	return "redirect:http://localhost:3050/dist/kakaoPay";
    }
	
	@RequestMapping("/closePayment")
	public String closePayment( RedirectAttributes redirectAttributes ) throws Exception {
		//return "inicis/closePayment";
		CmmnUtil.methodLog("결제 취소");
		
//		redirectAttributes.addAttribute("MSG", "cancel");
//		return "redirect:http://localhost:4040/dist/InicisTest";
		return "inicis/closePayment";
    }
	
}

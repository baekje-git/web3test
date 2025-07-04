/**
 * Entity.java V1.0 2017. 06. 08.
 *
 * Copyright (c) 2016 UBIStorm Co. All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of UBIStorm Co.  You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement
 * you entered into with UBIStorm Co.
 **/

package baekje.web.api.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
/*추가*/
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.support.Gv;
import ever.support.CmnUtil;
import ever.support.exception.AjaxMsgCustomException;
import ever.support.session.PassPermission;
import ever.cmmn.service.CmmnService;

import baekje.web.api.svr.ApiMobileBackSvc;

/**
 * 주문연동 API CMD
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>MSL<br>
 * <strong>작성일 : </strong>2023.08.28<br>
 * @author       MSL
 * @package      baekje.web.api.cmd
 * @file         apiMobileBack.java
 * @version      1.0
 * @since        2023.08.28
 */
@Controller
@Aspect
@RequestMapping("/apiMobileBack")
public class apiMoblieBack {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private CmmnService cmmnService;

    @Autowired
    private ApiMobileBackSvc apiMobileBackSvc;

    public static final String API_ID_PREFIX = "MOBILEBACK";
    public static final String API_INS_ID    = "mobileback";

    /**
     * 1.로그인
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong><br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  moon
     * @since   2023. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @PassPermission
    @ResponseBody
	@RequestMapping(value = "/getUserInfo.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getUserInfo(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getUserInfo");
	        reqMap.put("API_INS", API_INS_ID);
			
		    // 로그 insert
			apiMobileBackSvc.insertApiHistory(reqMap);

			//사용자정보 조회
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.selectUserInfo");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());


			if (resultList.size() == 1) {
			    //정상조회시 인증키 추가
				reqMap.put(Gv.QUERY_ID  , "apiMobileBack.selectAPIKEY");
				hMapResult.put("API_KEY", (String)cmmnService.select(reqMap));
			}
			else {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", "로그인에 실패하였습니다");
			}


			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("api Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }


    /**
     * 2.거래처조회
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong><br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  moon
     * @since   2023. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @PassPermission
    @ResponseBody
	@RequestMapping(value = "/getCustList.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getCustList(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getCustList");
	        reqMap.put("API_INS", API_INS_ID);
			
		    // 로그 insert
			apiMobileBackSvc.insertApiHistory(reqMap);

			//인증 체크
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.checkAuth");
			checkMessage = (String)cmmnService.select(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", checkMessage);
				hMapResult.put("MESSAGE", checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiMobileBackSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//사용자정보조회
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.selectCustList");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("api Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }


    /**
     * 3.실물반품용 바코드 정보조회
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong><br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  moon
     * @since   2023. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @PassPermission
    @ResponseBody
	@RequestMapping(value = "/getItemBarcode.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getItemBarcode(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getItemBarcode");
	        reqMap.put("API_INS", API_INS_ID);
			
		    // 로그 insert
			apiMobileBackSvc.insertApiHistory(reqMap);

			//인증 체크
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.checkAuth");
			checkMessage = (String)cmmnService.select(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", checkMessage);
				hMapResult.put("MESSAGE", checkMessage+"<br><br>"+reqMap.get("SGTIN"));
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiMobileBackSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//중복접수 체크
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.checkBarcode");
			reqMap.put("ORD_TYPE_CD"  , "01");
			checkMessage = (String)cmmnService.select(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage+"<br><br>"+reqMap.get("SGTIN"));
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiMobileBackSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//반품가능여부 체크
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.checkTakeback");
			checkMessage = (String)cmmnService.select(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage+"<br><br>"+reqMap.get("SGTIN"));
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiMobileBackSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//바코드정보조회
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.selectItemBarcode");
			resultList = cmmnService.selectList(reqMap);
			
			if (resultList.size() > 0) {
				hMapResult.put("DATA", resultList);
				hMapResult.put("DATACOUNT", resultList.size());
			}
			else {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", "출고내역이 존재하지 않습니다"+"<br><br>"+reqMap.get("SGTIN"));
			}
			
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("api Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }
    
    /**
     * 4.실물반품 등록
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong><br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  moon
     * @since   2023. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @PassPermission
    @ResponseBody
	@RequestMapping(value = "/putBack.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> putBack(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	//log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";
		int putCount = 0;

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".putBack");
	        reqMap.put("API_INS", API_INS_ID);

	      //log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiMobileBackSvc.insertApiHistory(reqMap);
			
			//인증 체크
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.checkAuth");
			checkMessage = (String)cmmnService.select(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", checkMessage);
				hMapResult.put("MESSAGE", checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiMobileBackSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//반품등록
			reqMap.put("ORD_TYPE_CD", "01");
			hMapResult = apiMobileBackSvc.putBack(reqMap);

			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("api Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }    


    /**
     * 5.약가보상용 바코드 정보조회
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong><br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  moon
     * @since   2023. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @PassPermission
    @ResponseBody
	@RequestMapping(value = "/getRewardItemBarcode.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getRewardItemBarcode(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getRewardItemBarcode");
	        reqMap.put("API_INS", API_INS_ID);
			
		    // 로그 insert
			apiMobileBackSvc.insertApiHistory(reqMap);

			//인증 체크
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.checkAuth");
			checkMessage = (String)cmmnService.select(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", checkMessage);
				hMapResult.put("MESSAGE", checkMessage+"<br><br>"+reqMap.get("SGTIN"));
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiMobileBackSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//약가보상 중복접수 체크
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.checkBarcode");
			reqMap.put("ORD_TYPE_CD"  , "34");
			reqMap.put("REWARD_YN"  , "Y");

			checkMessage = (String)cmmnService.select(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage+"<br><br>"+reqMap.get("SGTIN"));
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiMobileBackSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//약가보상반품 가능여부 체크
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.checkRewardItem");
			checkMessage = (String)cmmnService.select(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage+"<br><br>"+reqMap.get("SGTIN"));
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiMobileBackSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//바코드정보조회
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.selectRewardItemBarcode");
			resultList = cmmnService.selectList(reqMap);
			
			if (resultList.size() > 0) {
				hMapResult.put("DATA", resultList);
				hMapResult.put("DATACOUNT", resultList.size());
			}
			else {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", "출고내역이 존재하지 않습니다"+"<br><br>"+reqMap.get("SGTIN"));
			}
			
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("api Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }
    
    /**
     * 6.약가보상 반품 등록
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong><br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  moon
     * @since   2023. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @PassPermission
    @ResponseBody
	@RequestMapping(value = "/putRewardBack.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> putRewardBack(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	//log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";
		int putCount = 0;

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".putRewardBack");
	        reqMap.put("API_INS", API_INS_ID);

	      //log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiMobileBackSvc.insertApiHistory(reqMap);
			
			//인증 체크
			reqMap.put(Gv.QUERY_ID  , "apiMobileBack.checkAuth");
			checkMessage = (String)cmmnService.select(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", checkMessage);
				hMapResult.put("MESSAGE", checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiMobileBackSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//약가보상등록
			reqMap.put("ORD_TYPE_CD", "34");
			hMapResult = apiMobileBackSvc.putBack(reqMap);

			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("api Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }        
    
}

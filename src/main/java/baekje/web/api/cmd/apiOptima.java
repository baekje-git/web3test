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

import baekje.web.api.svr.ApiOptimaSvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*추가*/
import org.springframework.http.MediaType;

import ever.support.Gv;
import ever.support.CmnUtil;
import ever.support.exception.AjaxMsgCustomException;
import ever.support.session.PassPermission;
import ever.cmmn.service.CmmnService;

/**
 * 주문연동 API CMD
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>MSL<br>
 * <strong>작성일 : </strong>2023.08.28<br>
 * @author       MSL
 * @package      baekje.web.api.cmd
 * @file         apiOptima.java
 * @version      1.0
 * @since        2023.08.28
 */
@Controller
@Aspect
@RequestMapping("/apiOptima")
public class apiOptima {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private CmmnService cmmnService;

    @Autowired
    private ApiOptimaSvc apiOptimaSvc;

    public static final String API_BRCH_CD   = "32";
    public static final String API_ID_PREFIX = "OPTIMA";
    public static final String API_INS_ID    = "optima";

    
    /**
     * 1.창고조회
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
	@RequestMapping(value = "/getDlvBrch.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getDlvBrch(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	//log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getDlvBrch");
	        reqMap.put("API_INS", API_INS_ID);
	        reqMap.put("API_BRCH_CD", API_BRCH_CD);
	      //log.debug("reqMap MOD:"+reqMap);
			
		    // 로그 insert
			apiOptimaSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiOptimaSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("api authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiOptimaSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//배송지점 조회
			reqMap.put(Gv.QUERY_ID  , "apiOptima.selectDlvBrch");
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
     * 2.품목조회
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
	@RequestMapping(value = "/getItem.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getItem(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	//log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getItem");
	        reqMap.put("API_INS", API_INS_ID);
	        reqMap.put("API_BRCH_CD", API_BRCH_CD);

	      //log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiOptimaSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiOptimaSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("api authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiOptimaSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//품목조회
			reqMap.put(Gv.QUERY_ID  , "apiOptima.selectItem");
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
     * 3.거래처등록
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
	@RequestMapping(value = "/putCust.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> putCust(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	log.debug("reqMap ORI:"+reqMap);
		
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
	        reqMap.put("API_ID",  API_ID_PREFIX+".putCust");
	        reqMap.put("API_INS", API_INS_ID);
	        reqMap.put("API_BRCH_CD", API_BRCH_CD);

			log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiOptimaSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiOptimaSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				log.debug("api authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiOptimaSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//거래처체크 (사업자번호중복, 요양기관코드중복)
			checkMessage = apiOptimaSvc.checkCust(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				log.debug("api checkCustMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiOptimaSvc.insertApiHistory(reqMap);
				return hMapResult;
			}

			//거래처등록
			hMapResult = apiOptimaSvc.putCust(reqMap);

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
     * 4.거래처조회
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
	@RequestMapping(value = "/getCust.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getCust(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getCust");
	        reqMap.put("API_INS", API_INS_ID);
	        reqMap.put("API_BRCH_CD", API_BRCH_CD);

			log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiOptimaSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiOptimaSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				log.debug("api authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiOptimaSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//거래처조회
			reqMap.put(Gv.QUERY_ID  , "apiOptima.selectCust");
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
     * 5.재고조회
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
	@RequestMapping(value = "/getStock.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getStock(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getStock");
	        reqMap.put("API_INS", API_INS_ID);
	        reqMap.put("API_BRCH_CD", API_BRCH_CD);

			log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiOptimaSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiOptimaSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				log.debug("api authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiOptimaSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//재고조회
			reqMap.put(Gv.QUERY_ID  , "apiOptima.selectStock");
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
     * 6.주문등록
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
	@RequestMapping(value = "/putOrder.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> putOrder(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	log.debug("reqMap ORI:"+reqMap);
		
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
	        reqMap.put("API_ID",  API_ID_PREFIX+".putOrder");
	        reqMap.put("API_INS", API_INS_ID);
	        reqMap.put("API_BRCH_CD", API_BRCH_CD);

			log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiOptimaSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiOptimaSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				log.debug("apiOptima authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiOptimaSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			//주문등록
			hMapResult = apiOptimaSvc.putOrder(reqMap);
			
			if ("ERROR".equals(hMapResult.get("RESULT"))) {
				// 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", hMapResult.get("MESSAGE"));
				apiOptimaSvc.insertApiHistory(reqMap);
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
     * 7.주문조회
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
	@RequestMapping(value = "/getOrderState.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getOrderState(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getOrderState");
	        reqMap.put("API_INS", API_INS_ID);
	        reqMap.put("API_BRCH_CD", API_BRCH_CD);

			log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiOptimaSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiOptimaSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				log.debug("api authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiOptimaSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//주문상태조회
			reqMap.put(Gv.QUERY_ID  , "apiOptima.selectOrderState");
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
     * 8.매출내역조회
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
	@RequestMapping(value = "/getSaleList.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getSaleList(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+".getSaleList");
	        reqMap.put("API_INS", API_INS_ID);
	        reqMap.put("API_BRCH_CD", API_BRCH_CD);

			log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiOptimaSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiOptimaSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				log.debug("api authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", API_INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiOptimaSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//매출내역조회
			reqMap.put(Gv.QUERY_ID  , "apiOptima.selectSaleList");
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
    
    
}

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

import baekje.web.api.svr.ApiAiOcrSvc;

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
 * AIOCR 연동 API CMD
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>MSL<br>
 * <strong>작성일 : </strong>2023.08.28<br>
 * @author       MSL
 * @package      baekje.web.api.cmd
 * @file         apiMediPlan.java
 * @version      1.0
 * @since        2023.08.28
 */
@Controller
@Aspect
@RequestMapping("/apiAiOcr")
public class apiAiOcr {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private CmmnService cmmnService;

    @Autowired
    private ApiAiOcrSvc apiAiOcrSvc;


    /**
     * 품목조회
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
	        reqMap.put("API_ID",  "AIOCR.getItem");
	        reqMap.put("API_INS", "AIOCR");

			//log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//품목조회
			reqMap.put(Gv.QUERY_ID  , "apiAiOcr.getItem");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }

    /**
     * 품목메이커조회
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
	@RequestMapping(value = "/getMaker.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getMaker(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

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
	        reqMap.put("API_ID",  "AIOCR.getMaker");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//품목메이커조회
			reqMap.put(Gv.QUERY_ID  , "apiAiOcr.getMaker");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }
    
    /**
     * 매입거래처조회
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
	@RequestMapping(value = "/getBuyCust.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getBuyCust(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

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
	        reqMap.put("API_ID",  "AIOCR.getBuyCust");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//매입거래처조회
			reqMap.put(Gv.QUERY_ID  , "apiAiOcr.getBuyCust");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }
    

    /**
     * 매입지점조회
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
	@RequestMapping(value = "/getBrch.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getBrch(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

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
	        reqMap.put("API_ID",  "AIOCR.getBrch");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//매입지점조회
			reqMap.put(Gv.QUERY_ID  , "apiAiOcr.getBrch");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }

    /**
     * 품목별매입거래처조회
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
	@RequestMapping(value = "/getItemBuyCust.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getItemBuyCust(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

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
	        reqMap.put("API_ID",  "AIOCR.getItemBuyCust");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//품목별매입거래처조회
			reqMap.put(Gv.QUERY_ID  , "apiAiOcrgetItemBuyCust");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }

    /**
     * 매입명세서 마스터 목록 조회
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
	@RequestMapping(value = "/getInvoiceList.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getInvoiceList(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

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
	        reqMap.put("API_ID",  "AIOCR.getInvoiceList");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			reqMap.put(Gv.QUERY_ID  , "apiAiOcrgetInvoiceList");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }


    /**
     * 매입명세서 품목 목록 조회
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
	@RequestMapping(value = "/getInvoiceItemList.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getInvoiceItemList(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

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
	        reqMap.put("API_ID",  "AIOCR.getInvoiceItemList");
	        reqMap.put("API_INS", "AIOCR");

			//log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			reqMap.put(Gv.QUERY_ID  , "apiAiOcrgetInvoiceItemList");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }

    /**
     * 매입명세서 마스터 수정건 조회
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
	@RequestMapping(value = "/getInvoiceModify.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getInvoiceModify(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

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
	        reqMap.put("API_ID",  "AIOCR.getInvoiceModify");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//매입지점조회
			reqMap.put(Gv.QUERY_ID  , "apiAiOcr.getInvoiceModify");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }


    /**
     * 매입명세서 품목 수정건 조회
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
	@RequestMapping(value = "/getInvoiceItemModify.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getInvoiceItemModify(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

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
	        reqMap.put("API_ID",  "AIOCR.getInvoiceItemModify");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//매입지점조회
			reqMap.put(Gv.QUERY_ID  , "apiAiOcr.getInvoiceItemModify");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}
    }

    /**
     * 매입명세서등록
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
	@RequestMapping(value = "/putBuyInvoice.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> putInvoice(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	//log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";
		int putCount = 0;

		try {
			// 기본정보 셋팅
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  "AIOCR.putBuyInvoice");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiOptima authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//매입명세서등록
			hMapResult = apiAiOcrSvc.putInvoice(reqMap);
			
			if ("ERROR".equals(hMapResult.get("RESULT"))) {
				// 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", hMapResult.get("MESSAGE"));
		        apiAiOcrSvc.insertApiHistory(reqMap);
			}
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("api Exception:"+ex);
    		int len = ex.toString().length() > 400 ? 400 : ex.toString().length();

			reqMap.put("API_MOD", "AIOCR");
	        reqMap.put("RESULT", "ERROR");
	        reqMap.put("ERR_MSG", ex.toString().substring(0,len));
	        apiAiOcrSvc.insertApiHistory(reqMap);

			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));

	        return hMapResult;
    	}
    }    

    /**
     * 이미지업로드 결과
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
	@RequestMapping(value = "/saveFileUploadResult.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> saveFileUploadResult(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

    	//log.debug("reqMap ORI:"+reqMap);
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";
		int putCount = 0;

		try {
			// 기본정보 셋팅
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  "AIOCR.saveFileUploadResult");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiOptima authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//업로드이미지 처리결과 UPDATE
			hMapResult = apiAiOcrSvc.saveFileUploadResult(reqMap);
			
			if ("ERROR".equals(hMapResult.get("RESULT"))) {
				// 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", hMapResult.get("MESSAGE"));
		        apiAiOcrSvc.insertApiHistory(reqMap);
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
     * 매입명세서 등록 오류건 조회
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
	@RequestMapping(value = "/getBuyInvoiceError.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getBuyInvoiceError(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

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
	        reqMap.put("API_ID",  "AIOCR.getBuyInvoiceError");
	        reqMap.put("API_INS", "AIOCR");

	      //log.debug("reqMap MOD:"+reqMap);

			//로그 처리
			apiAiOcrSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiAiOcrSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("apiAiOcr authMessage:"+checkMessage);
			    // 에러로깅
		        reqMap.put("API_MOD", "AIOCR");
		        reqMap.put("ERR_MSG", checkMessage);
		        apiAiOcrSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//매입명세서 등록 오류건 조회
			reqMap.put(Gv.QUERY_ID  , "apiAiOcr.getBuyInvoiceError");
			resultList = cmmnService.selectList(reqMap);
			
			hMapResult.put("DATA", resultList);
			hMapResult.put("DATACOUNT", resultList.size());
			return hMapResult;			
    	}
    	catch(Exception ex) {
    		log.error("apiAiOcr Exception:"+ex);
    		int len = ex.toString().length() > 200 ? 200 : ex.toString().length();
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", ex.toString().substring(0,len));
			return hMapResult;
    	}

    }    
    
}

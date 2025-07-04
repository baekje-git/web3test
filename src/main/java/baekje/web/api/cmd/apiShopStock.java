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

import baekje.web.api.svr.ApiShopStockSvc;

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
 * 쇼핑몰재고조회 API CMD
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>MSL<br>
 * <strong>작성일 : </strong>2023.08.28<br>
 * @author       MSL
 * @package      baekje.web.api.cmd
 * @file         apiShopStock.java
 * @version      1.0
 * @since        2023.08.28
 */
@Controller
@Aspect
@RequestMapping("/apiShopStock")
public class apiShopStock {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private CmmnService cmmnService;

    @Autowired
    private ApiShopStockSvc apiShopStockSvc;

    public static final String API_ID_PREFIX = "SHOPSTOCK";
    public static final String API_NAMESPACE = "apiShopStock";

     /**
     * 재고조회 - 한미몰
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
	@RequestMapping(value = "/getStockHmp.ajx", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public HashMap<String, Object> getStockHmp(HttpServletRequest request, @RequestBody HashMap<String, Object> reqMap) throws Exception {

        //수정내용
    	String SHOP_ID         = "S3";             /* 공통코드 581 */
        String INS_ID          = "HMP";


        //이후는 고정내용
        HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		List<Object> resultList = new ArrayList<>();
		String checkMessage = "";

		try {
			// 기본정보 셋팅
	        reqMap.put("API_TXT", reqMap.toString());
	        reqMap.put("API_IP", CmnUtil.getClientIp(request));
	        reqMap.put("API_ID",  API_ID_PREFIX+"."+INS_ID+".getStock");
	        reqMap.put("API_INS", INS_ID);
	        reqMap.put("API_SHOP_ID", SHOP_ID);
	        reqMap.put("API_NAMESPACE", API_NAMESPACE);

	      //log.debug("reqMap MOD:"+reqMap);

		    // 로그 insert
			apiShopStockSvc.insertApiHistory(reqMap);
			
			//인증 체크
			checkMessage = apiShopStockSvc.checkAuth(reqMap);
			if (!"OK".equals(checkMessage)) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", checkMessage);
				//log.debug("api authMessage:"+checkMessage);
				// 에러로깅
		        reqMap.put("API_MOD", INS_ID);
		        reqMap.put("ERR_MSG", checkMessage);
				apiShopStockSvc.insertApiHistory(reqMap);
				return hMapResult;
			}
			
			//재고조회
			reqMap.put(Gv.QUERY_ID  , "apiShopStock.selectStockHmp");
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

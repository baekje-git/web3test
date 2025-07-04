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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*추가*/
import org.springframework.http.MediaType;

import ever.support.Gv;
import ever.support.CmnUtil;
import ever.support.exception.AjaxMsgCustomException;
import ever.support.session.PassPermission;
import ever.cmmn.service.CmmnService;

import baekje.web.api.svr.apiOnConnectSvc;

/**
 * 온커넥트 매출조회 API CMD
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>abj1<br>
 * <strong>작성일 : </strong>2017. 9. 28.오후 6:18:03<br>
 * @author       abj1
 * @package      baekje.web.comOrd.cmd
 * @file         comOrdCmd.java
 * @version      1.0
 * @since        2017. 9. 28.
 */
@Controller
@Aspect
@RequestMapping("/apiOnConnect")
public class apiOnConnectCmd {

    private Logger log = LogManager.getLogger(this.getClass());


    @Autowired
    private apiOnConnectSvc apiOnConnectSvc;


    /**
     * 매출 > 매출정보마스터조회
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong>2021. 09.14<br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  leehh71
     * @since   2021. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @RequestMapping(value = "/saleMaster.ajx",produces=MediaType.APPLICATION_XML_VALUE) // 
    @PassPermission
    @ResponseBody
    public Object saleMaster(HttpServletRequest request ) throws Exception {
        HashMap<String, Object> pReq=CmnUtil.getParameters(request);
        HashMap<String, Object> pMap=CmnUtil.getParameters(request);

		pMap.put("IP_ADDR", CmnUtil.getClientIp(request));
		pMap.put("API_ID",  "saleMaster");
		pMap.put("API_TXT", pReq.toString());
		pMap.put("INS_ID",  "onconnect");
		apiOnConnectSvc.insertApiHistory(pMap);
        
        Map<Object, Object> resultMap = apiOnConnectSvc.selectSaleMaster(pMap);
        
        return resultMap;
    }
   
    /**
     * 매출 > 매출정보디테일조회
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong>2021. 09.14<br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  leehh71
     * @since   2021. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @RequestMapping(value = "/saleDetail.ajx",produces=MediaType.APPLICATION_XML_VALUE) // 
    @PassPermission
    @ResponseBody
    public Object saleDetail(HttpServletRequest request ) throws Exception {
        HashMap<String, Object> pReq=CmnUtil.getParameters(request);
        HashMap<String, Object> pMap=CmnUtil.getParameters(request);

		pMap.put("IP_ADDR", CmnUtil.getClientIp(request));
		pMap.put("API_ID",  "saleDetail");
		pMap.put("API_TXT", pReq.toString());
		pMap.put("INS_ID",  "onconnect");
		apiOnConnectSvc.insertApiHistory(pMap);
        
        Map<Object, Object> resultMap = apiOnConnectSvc.selectSaleDetail(pMap);
        
        return resultMap;
    }

    /**
     * 매출 > 매출정보시리얼조회
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong>2021. 09.14<br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  leehh71
     * @since   2021. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @RequestMapping(value = "/saleSerial.ajx",produces=MediaType.APPLICATION_XML_VALUE) // 
    @PassPermission
    @ResponseBody
    public Object saleSerial(HttpServletRequest request ) throws Exception {
        HashMap<String, Object> pReq=CmnUtil.getParameters(request);
        HashMap<String, Object> pMap=CmnUtil.getParameters(request);

		pMap.put("IP_ADDR", CmnUtil.getClientIp(request));
		pMap.put("API_ID",  "saleSerial");
		pMap.put("API_TXT", pReq.toString());
		pMap.put("INS_ID",  "onconnect");
		apiOnConnectSvc.insertApiHistory(pMap);
        
        Map<Object, Object> resultMap = apiOnConnectSvc.selectSaleSerial(pMap);
        
        return resultMap;
    }

    /**
     * 매출 > code정보조회
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong>2021. 09.14<br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  leehh71
     * @since   2021. 09.14
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @RequestMapping(value = "/codeInfo.ajx",produces=MediaType.APPLICATION_XML_VALUE) // 
    @PassPermission
    @ResponseBody
    public Object selectCode(HttpServletRequest request ) throws Exception {
        HashMap<String, Object> pReq=CmnUtil.getParameters(request);
        HashMap<String, Object> pMap=CmnUtil.getParameters(request);

		pMap.put("IP_ADDR", CmnUtil.getClientIp(request));
		pMap.put("API_ID",  "codeInfo");
		pMap.put("API_TXT", pReq.toString());
		pMap.put("INS_ID",  "onconnect");
		apiOnConnectSvc.insertApiHistory(pMap);

        Map<Object, Object> resultMap = apiOnConnectSvc.selectCode(pMap);
        
        return resultMap;
    }
}

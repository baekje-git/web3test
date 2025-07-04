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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/*추가*/
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import ever.support.Gv;
import ever.support.CmnUtil;
import ever.support.exception.AjaxMsgCustomException;
import ever.support.session.PassPermission;
import ever.cmmn.service.CmmnService;

import baekje.web.api.svr.ApiSaleInfoSvc;

/**
 * 반품접수 CMD
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
@RequestMapping("/apiSaleInfo")
public class ApiSaleInfoCmd {

    private Logger log = LogManager.getLogger(this.getClass());


    @Autowired
    private CmmnService cmmnService;

    @Autowired
    private ApiSaleInfoSvc apiSaleInfoSvc;


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
    @RequestMapping(value = "/SaleInfo.ajx",produces=MediaType.APPLICATION_XML_VALUE) // 
    @PassPermission
    @ResponseBody
    public Object SaleInfo(HttpServletRequest request ) throws Exception {
        HashMap<String, Object> paramMap = CmnUtil.getParameters(request);
        String ServiceKey   =     new String(""); //사업자번호
        String ComGb        =     new String(""); //도도매업체 구분 신성,대성 param 추가 공통코드에 추가
        
        //String userPasswd = new String("");
        if (paramMap.get("key") != null) {
            ServiceKey = paramMap.get("key").toString();
        }
        
        //String userPasswd = new String("");
        if (paramMap.get("comgb") != null) {
            ComGb = paramMap.get("comgb").toString();
        }
 	    
        //System.out.println("enc userPasswd:"+userPasswd);
        paramMap.put("ServiceKey" , ServiceKey );
        paramMap.put("ComGb" , ComGb );

        Map<Object, Object> storemap = apiSaleInfoSvc.selectSaleMstInfo(paramMap);//selectInicisLogin
        
        return storemap;
    }

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
    @RequestMapping(value = "/SaleDtlInfo.ajx",produces=MediaType.APPLICATION_XML_VALUE) //
    @PassPermission
    @ResponseBody
    public Object SaleDtlInfo(HttpServletRequest request ) throws Exception {
        HashMap<String, Object> paramMap = CmnUtil.getParameters(request);
        String ServiceKey   =     new String(""); //사업자번호
        String ComGb        =     new String(""); //도도매업체 구분 신성,대성 param 추가 공통코드에 추가
        
        //String userPasswd = new String("");
        if (paramMap.get("key") != null) {
            ServiceKey = paramMap.get("key").toString();
        }
        
        //String userPasswd = new String("");
        if (paramMap.get("comgb") != null) {
            ComGb = paramMap.get("comgb").toString();
        }
        
        //System.out.println("enc userPasswd:"+userPasswd);
        paramMap.put("ServiceKey" , ServiceKey );
        paramMap.put("ComGb" , ComGb );
        
        Map<Object, Object> storemap = apiSaleInfoSvc.selectSaleDtlInfo(paramMap);//selectInicisLogin
        
        return storemap;
    }
   
}

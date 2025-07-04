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

import baekje.web.api.svr.ApiSvc;
import ever.support.Gv;
import ever.support.CmnUtil;

import ever.support.exception.AjaxMsgCustomException;
import ever.support.security.module.sha.SHACipher;
import ever.support.session.PassPermission;
import ever.cmmn.service.CmmnService;

import ever.cmmn.service.CmmnService;

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
@RequestMapping("/api")
public class ApiCmd {

    private Logger log = LogManager.getLogger(this.getClass());


    @Autowired
    private CmmnService cmmnService;

    @Autowired
    private ApiSvc apiSvc;


    /**
     * 반품접수 > 약국조회
     * <strong>작성자 : </strong><br>
     * <strong>작성일 : </strong>2020. 01. 31<br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  abj1
     * @since   2020. 01. 31.
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  String 리턴에대한설명_직접입력
     */
    @RequestMapping(value = "/login.ajx")
    @PassPermission
    @ResponseBody
    public Object login(HttpServletRequest request ) throws Exception {
        HashMap<String, Object> paramMap = CmnUtil.getParameters(request);
        String userPasswd = new String("");
        if (paramMap.get("userPasswd") != null) {
    		SHACipher shaCipher = new SHACipher();
    		userPasswd = shaCipher.encryptSHA256(paramMap.get("userPasswd").toString());
        }
 	    
        //System.out.println("enc userPasswd:"+userPasswd);
        paramMap.put("pwd" , userPasswd );

 	    Map<String , Object> storemap=apiSvc.selectInicisLogin(paramMap);
        
        return storemap;
    }

   
   
}

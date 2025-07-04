package kada.k05.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.session.SessionUtil;
import kada.k05.service.CertificateService;
import kada.util.Util;


@Controller
@RequestMapping("/k05/myaddress/")
public class MyAddressController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	
	        
	/**
     * <pre>
     * @MethodName : saveMyAddress
     * @Description : 주소저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveMyAddress.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveMyAddress(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("주소저장 ");        
        
        try {
        	
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	String mode = Util.getValue("MODE", hMap);
        	if("ADD".equals(mode)) {
        		cmmnService.insert("k05.myaddress.insertMyAddress", hMap);	
        	}else if("UPDATE".equals(mode)) {
        		cmmnService.insert("k05.myaddress.updateMyAddress", hMap);
        	}else if("DELETE".equals(mode)) {
        		cmmnService.insert("k05.myaddress.deleteMyAddress", hMap);
        	}

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveMyAddress : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
}

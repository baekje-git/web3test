package kada.k99.controller;

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
import kada.k99.service.CertiNumberService;
import kada.k99.service.RecertificationPutService;
import kada.util.Util;


@Controller
@RequestMapping("/k99/recertification/")
public class RecertificationPutController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private RecertificationPutService reauthService;
	/**
     * <pre>
     * @MethodName : saveRecertification
     * @Description : 자격구분저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveRecertification.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveRecertification(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("자격구분저장 ");        
        
        try {
        	String mode = Util.getValue("MODE", hMap);
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	
        	if("ADD".equals(mode)) {
        		cmmnService.update("k99.recertification.insertREAUTH01TB", hMap);
        	}else if("UPDATE".equals(mode)) {
        		cmmnService.update("k99.recertification.updateREAUTH01TB", hMap);
        	}

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveRecertification : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateReauthConfirm
     * @Description : 재인증신청 개별 승인,반려
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateReauthConfirm.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateReauthConfirm(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("재인증신청 개별 승인,반려 ");        
        
        try {        	
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	cmmnService.insert("k99.recertification.changeConfirmREAUTH02TB", hMap);        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateReauthConfirm : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateSelectedReauthConfirm
     * @Description : 선택된 재인증신청 개별 승인,반려
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateSelectedReauthConfirm.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateSelectedReauthConfirm(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("선택된 재인증신청 개별 승인,반려 ");        
        
        try {
        	reauthService.updateSelectedReauthConfirm(hMap);	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateSelectedReauthConfirm : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : changeUseYnReauth
     * @Description : 재인증신청 사용,미사용 업데이트
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/changeUseYnReauth.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> changeUseYnReauth(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("재인증신청 사용,미사용 업데이트");        
        
        try {        	
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	cmmnService.insert("k99.recertification.changeUseYnREAUTH01TB", hMap);        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]changeUseYnReauth : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
}

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
import kada.k05.service.CertificateService;


@Controller
@RequestMapping("/k05/certificate/")
public class CertificateController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private CertificateService certificateService;
	        
	/**
     * <pre>
     * @MethodName : saveCertificateReq
     * @Description : 확인서신청등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveCertificateReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveCertificateReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("확인서신청등록저장 ");        
        
        try {
        	//String mode = String.valueOf(hMap.get("_STATUS"));
        	int maxSeq=0;
        	//if("I".equals(mode)) {
        		
        	maxSeq=(int)cmmnService.select("k99.appConfirm.selectCertificateSequence",hMap);
        	hMap.put("SEQ", maxSeq);
        	hMap.put("USER_AGENT",request.getHeader("user-agent"));
        	hMap.put("IP_ADDR", request.getRemoteAddr());
        	cmmnService.insert("k99.appConfirm.insertCertificateList", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveCertificateReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateCertificateReq
     * @Description : 확인서신청수정저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateCertificateReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateCertificateReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("확인서신청수정저장 ");        
        
        try {
        	
        	hMap.put("USER_AGENT",request.getHeader("user-agent"));
        	hMap.put("IP_ADDR", request.getRemoteAddr());
        	cmmnService.insert("k99.appConfirm.updateCertificateList", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateCertificateReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateCertificateReq
     * @Description : 확인서신청관리수정저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateCertificateReqMng.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateCertificateReqMng(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("확인서신청관리수정저장 ");        
        
        try {
        	
        	hMap.put("USER_AGENT",request.getHeader("user-agent"));
        	hMap.put("IP_ADDR", request.getRemoteAddr());
        	cmmnService.insert("k99.appConfirm.updateCertificateList", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateCertificateReqMng : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteCertificateReq
     * @Description : 확인서신청 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteCertificateReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteCertificateReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("확인서신청삭제 ");        
        
        try {
        		
        	cmmnService.insert("k99.appConfirm.deleteCertificateList", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteCertificateReqMng : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteCertificateReq
     * @Description : 확인서신청관리 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteCertificateReqMng.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteCertificateReqMng(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("확인서신청관리삭제 ");        
        
        try {
        		
        	cmmnService.insert("k99.appConfirm.deleteCertificateList", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteCertificateReqMng : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateCertificateReqConfirm
     * @Description : 확인서신청관리 승인
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateCertificateReqConfirmMng.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateCertificateReqConfirm(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("확인서신청관리 승인 ");        
        
        try {
        		
        	hMap.put("USER_AGENT",request.getHeader("user-agent"));
        	hMap.put("IP_ADDR", request.getRemoteAddr());
        	cmmnService.insert("k99.appConfirm.updateCertificateListConfirmStatusR", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteCertificateReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateCertificateReqConfirm
     * @Description : 확인서신청관리 최초승인일 지정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateCertificateFirstCertMng.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateCertificateFirstCertMng(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("확인서신청관리 최초승인일 지정 ");        
        
        try {
        		
        	cmmnService.insert("k99.appConfirm.updateAuthYMDByUserID", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateCertificateFirstCertMng : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    
    
}

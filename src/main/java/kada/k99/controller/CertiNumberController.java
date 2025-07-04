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
import kada.k99.service.CertiNumberService;


@Controller
@RequestMapping("/k99/certi/")
public class CertiNumberController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private CertiNumberService certiNumberService;
	        
	/**
     * <pre>
     * @MethodName : saveCertiNumberReq
     * @Description : 인증번호신청등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveCertiNumberReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveCertiNumberReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호저장 ");        
        
        try {
        	certiNumberService.saveCertiNumberReq(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveCertiNumberReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateCertiNumberReq
     * @Description : 인증번호신청수정저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateCertiNumberReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateCertiNumberReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호저장 ");        
        
        try {
        	
        	cmmnService.insert("k99.certi.updateAUTH_STAGE", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateCertiNumberReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteCertiNumberReq
     * @Description : 인증번호신청삭제저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteCertiNumberReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteCertiNumberReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호삭제 ");        
        
        try {
        		
        	cmmnService.insert("k99.certi.deleteAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteCertiNumberReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    
    
}

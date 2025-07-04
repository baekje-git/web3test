package kada.k04.controller;

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
import kada.k04.service.ResultMgmtService;


@Controller
@RequestMapping("/k04/result/")
public class ResultMgmtController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private ResultMgmtService resultMgmtService;
	        
	/**
     * <pre>
     * @MethodName : saveResultMgmt
     * @Description : 결과관리등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveResultMgmt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveResultMgmt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("결과관리저장 ");        
        
        try {
        	resultMgmtService.saveResultMgmt(hMap);
        	//cmmnService.insert("k04.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveResultMgmt : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateResultMgmt
     * @Description : 결과관리수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateResultMgmt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateResultMgmt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("결과관리수정 ");        
        
        try {
        	
        	cmmnService.insert("k04.result.updateResultMgmt", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateResultMgmt : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteResultMgmt
     * @Description : 인증번호신청삭제저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteResultMgmt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteResultMgmt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호삭제 ");        
        
        try {
        		
        	cmmnService.insert("k04.result.deleteResultMgmt", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteResultMgmt : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    
    
}

package kada.k03.controller;

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
import kada.k03.service.ProClubMngService;
import kada.util.Util;


@Controller
@RequestMapping("/k03/errorReportMng/")
public class ErrorReportMngController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	
	/**
     * <pre>
     * @MethodName : deleteErrorReport
     * @Description : 오류리포트 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteErrorReport.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteErrorReport(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("오류리포트 삭제 ");        
        
        try {
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	cmmnService.insert("k03.error.deleteIPER01TB", hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteErrorReport : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
	        
	
    /**
     * <pre>
     * @MethodName : saveErrorReport
     * @Description : 오류리포트 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveErrorReport.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveErrorReport(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("오류리포트 저장 ");        
        
        try {
        	String mode = Util.getValue("MODE", hMap);
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	
        	if("ADD".equals(mode)) {
        		cmmnService.update("k03.error.insertIPER01TB", hMap);
        		hMap.put("CONFIRM_STATUS", "2");
        		cmmnService.update("k03.error.update0306IPCF01TB", hMap);
        	}else if("UPDATE".equals(mode)) {
        		cmmnService.update("k03.error.updateIPER01TB", hMap);
        	}

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveProClubMngReg : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
}

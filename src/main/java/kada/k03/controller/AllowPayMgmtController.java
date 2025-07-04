package kada.k03.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

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

import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.exception.CustomException;
import kada.k03.service.AllowPayMgmtService;

@Controller
@RequestMapping("/k03/allowpaymgmt/")
public class AllowPayMgmtController {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AllowPayMgmtService allowPayMgmtService;
	
	
    /**
     * <pre>
     * @MethodName : saveUserRegist
     * @Description : 도핑검사공지 및 신청 - 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveUpdateAllowPayMgmt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveUpdateAllowPayMgmt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("수당지급관리 - 저장 ");        
        
        try {        	
        	allowPayMgmtService.saveAllowPayMgmt(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]saveDopingInspect : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    
}

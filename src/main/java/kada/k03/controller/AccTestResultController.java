package kada.k03.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.exception.CustomException;
import kada.k03.service.AccTestResultService;
import kada.k03.service.AllowPayMgmtService;

@Controller
@RequestMapping("/k03/acctestresult/")
public class AccTestResultController {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AccTestResultService accTestResultService;
	
	
    /**
     * <pre>
     * @MethodName : saveUnitAmout
     * @Description : 시료별단가저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveUnitAmout.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveUnitAmout(@RequestBody List<HashMap<String, Object>> hList, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("시료별단가저장 ");        
        
        try {        	
        	accTestResultService.saveUnitAmout(hList);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]saveUnitAmout : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    /**
     * <pre>
     * @MethodName : saveLabBalance
     * @Description : LAB정산저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveLabBalance.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveLabBalance(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("LAB정산저장 ");        
        
        try {        	
        	accTestResultService.saveLabBalance(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]saveUnitAmout : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
}

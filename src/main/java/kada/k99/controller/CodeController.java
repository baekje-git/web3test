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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import kada.k99.service.CodeService;

@Controller
@RequestMapping("/k99/code/")
public class CodeController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private CodeService codeService;
	
	/**
     * <pre>
     * @MethodName : saveCodeMst
     * @Description : 대분류코드저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveCodeMst.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveCodeMst(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("대분류코드저장 ");        
        
        try {
        	String mode = String.valueOf(hMap.get("MODE"));
        	
        	if("ADD".equals(mode)) {
        		cmmnService.insert("k99.code.insertCodeMst", hMap);
        	}else if("UPDATE".equals(mode)) {
        		cmmnService.update("k99.code.updateCodeMst", hMap);
        	}

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveCodeMst : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveCodeDetail
     * @Description : 상세코드저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveCodeDetail.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveCodeDetail(@RequestBody List<HashMap<String, Object>> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("상세코드저장 ");        
        
        try {
        	codeService.saveCodeDetail(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
	 * 여러건의 코드 데이터를 리턴하는 경우
	 * parameter를 json형태로 받기위해서 client쪽에서 post로 호출해야 정상동작
	 * 
	 */
	@RequestMapping(value="/getCodeList", method = RequestMethod.POST )
	@ResponseBody
	public ResponseEntity<Object> bindList(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) {
		CmmnUtil.methodLog("getCodeList : " + hMap);
		
		try {
			List<Object> dataList =  cmmnService.selectList("k99.code.getCodeList", hMap);
			return new ResponseEntity<>(dataList, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]getCodeList : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
	}
	
	/**
     * <pre>
     * @MethodName : saveAppVersion
     * @Description : 앱버전저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveAppVersion.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveAppVersion(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("앱버전저장 ");        
        
        try {        	
        	cmmnService.update("k99.code.updateAppVersion", hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveAppVersion : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
}

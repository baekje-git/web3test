package kada.k01.controller;

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

@Controller
@RequestMapping("/k01/subForInspector/")
public class SubForInspectorController {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	private String errMsg = "사전일정중 겹치는 건이 존재합니다.확인을 부탁드립니다.";
	/**
     * 
     * <pre>
     * @MethodName : insertInspectorSchedule
     * @Description : 검사관 검사 가능 일정 추가
     * @param request  
     * @return
     * </pre>
     */
	@RequestMapping(value="/insertInspectorSchedule.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> insertInspectorSchedule(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사관 검사 가능 일정 추가 ");        
        
        try {        	
        	
        	List<Object> checkList = cmmnService.selectList("k01.main.subinspector.selectTermCheckByinspector", hMap);
        	if(checkList.size() > 0) {
        		return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        	}else {
        		cmmnService.insert("k01.main.subinspector.insertInspectorSchedule", hMap);
        		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
        	}						
		} catch (Exception e) {
			logger.error("[오류]insertSchedule : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	/**
     * 
     * <pre>
     * @MethodName : updateInspectorSchedule
     * @Description : 검사관 검사가능일정 수정
     * @param request  
     * @return
     * </pre>
     */
	@RequestMapping(value="/updateInspectorSchedule.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateInspectorSchedule(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사관 검사가능일정 수정 ");        
        
        try {        	
        	List<Object> checkList = cmmnService.selectList("k01.main.subinspector.selectTermCheckByinspector", hMap);
        	if(checkList.size() > 0) {
        		return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        	}else {
        		cmmnService.insert("k01.main.subinspector.updateInspectorSchedule", hMap);
        		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
        	}        	        							
		} catch (Exception e) {
			logger.error("[오류]updateSchedule : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	/**
     * 
     * <pre>
     * @MethodName : deleteSchedule
     * @Description : 검사관 검사가능일정 삭제
     * @param request  
     * @return
     * </pre>
     */
	@RequestMapping(value="/deleteInspectorSchedule.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteInspectorSchedule(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사관 검사가능일정 삭제 ");        
        
        try {        	
        	cmmnService.insert("k01.main.subinspector.deleteInspectorSchedule", hMap);        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteSchedule : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    } 
	
}

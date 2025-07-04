package kada.k02.controller;

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
import kada.k02.service.InspectorReportService;

@Controller
@RequestMapping("/k02/inspectorReport/")
public class InspectorReportController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InspectorReportService inspectorReportService;
	
	/**
     * <pre>
     * @MethodName : saveInspectorReportViewCnt
     * @Description : 검사실시보고 - 조회이력
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveInspectorReportViewCnt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveInspectorReportViewCnt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사실시보고 - 조회이력 ");        
        
        try {        	
        	inspectorReportService.saveInspectorReportViewCnt(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]saveInspectorReportViewCnt : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
	
	/**
     * <pre>
     * @MethodName : saveInspectorReport
     * @Description : 검사실시보고 - 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveInspectorReport.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveInspectorReport(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사실시보고 - 저장 ");        
        
        try {        	
        	inspectorReportService.saveInspectorReport(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]saveInspectorReport : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
}

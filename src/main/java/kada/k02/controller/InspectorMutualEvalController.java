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
import kada.k02.service.InspectorMutualEvalService;

@Controller
@RequestMapping("/k02/inspectorMutualEval/")
public class InspectorMutualEvalController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InspectorMutualEvalService evalService;
	
	
	/**
     * <pre>
     * @MethodName : saveEvalResult
     * @Description : 검사관상호평가 - 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveEvalResult.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveEvalResult(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사실시보고 - 저장 ");        
        
        try {        	
        	evalService.saveEvalResult(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]saveEvalResult : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
}

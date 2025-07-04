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
import ever.support.session.SessionUtil;
import kada.k99.service.CodeService;

@Controller
@RequestMapping("/k99/indication/")
public class IndicationMngController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	/**
     * <pre>
     * @MethodName : saveIndication
     * @Description : 지시사항저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveIndication.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveIndication(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("지시사항저장 ");        
        
        try {
        	String mode = String.valueOf(hMap.get("ROW_STATUS"));
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	
        	if("ADD".equals(mode)) {
        		cmmnService.insert("k99.indication.insertIndication", hMap);
        	}else if("UPDATE".equals(mode)) {
        		cmmnService.update("k99.indication.updateIndication", hMap);
        	}

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveIndication : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	
}

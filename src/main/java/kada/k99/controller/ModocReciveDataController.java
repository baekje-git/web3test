package kada.k99.controller;

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
import kada.modoc.ModocApi;

@Controller
@RequestMapping("/k99/modocReciveData/")
public class ModocReciveDataController {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ModocApi modoc;
	
	/**
     * <pre>
     * @MethodName : getReciveData
     * @Description : modoc의 데이터를 Get 으로 가져옴 
     * @param hMap - 요청 api 정보
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/getReciveData.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getReciveData(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("modoc의 데이터를 Get 으로 가져옴  ");        
        
        try {
        	modoc.getReciveData(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]getReciveData : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
}

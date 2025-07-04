package chem.fdg.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;

import kada.util.Util;

@RequestMapping("/fdg/apqr")
@RestController
public class ApqrController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CmmnService cmmnService;
	
	@RequestMapping(value="/fdgApqrList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgApqrList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		returnMap.put("data", cmmnService.selectList("fdg.apqr.selectFdgApqrList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/fdgApqrGpList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgApqrgpList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		returnMap.put("data", cmmnService.selectList("fdg.apqr.selectFdgApqrGpList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
}

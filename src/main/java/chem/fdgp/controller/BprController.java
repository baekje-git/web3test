package chem.fdgp.controller;

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

@RequestMapping("/fdgp/bpr")
@RestController
public class BprController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CmmnService cmmnService;
	
	@RequestMapping(value="/fdgpBprList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgpBprList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		returnMap.put("data", cmmnService.selectList("fdgp.bpr.selectFdgpBprList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/fdgpBprDataForQna",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgpBprDataForQna(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		returnMap.put("data", cmmnService.select("fdgp.bpr.selectFdgpBprData", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }	
	
	@RequestMapping(value="/fdgpBprDisposeList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgpBprDisposeList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		returnMap.put("data", cmmnService.selectList("fdgp.bpr.selectFdgpBprDisposeList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/updateFdgpBprDispose",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFdgpBprDispose(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.update("fdgp.bpr.updateFdgpBprDispose", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	@RequestMapping(value="/mainContentsList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getMainContentsList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("fdgp.bpr.selectMainContentsList", hMap));
    		returnMap.put("cnt", cmmnService.select("fdgp.bpr.selectMainContentsCnt", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
}

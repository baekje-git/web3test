package chem.fdgi.controller;

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

@RequestMapping("/fdgi/in")
@RestController
public class InController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CmmnService cmmnService;
	
	@RequestMapping(value="/fdgiInList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgiInList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		returnMap.put("data", cmmnService.selectList("fdgi.in.selectFdgiInList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }	
	
	@RequestMapping(value="/insertFdgiIn",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> insertFdgiIn(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.insert("fdgi.lot.changeFdgiLotData", hMap);
    		cmmnService.insert("fdgi.in.insertFdgiInData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	@RequestMapping(value="/updateFdgiIn",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFdgiIn(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.insert("fdgi.lot.changeFdgiLotData", hMap);
    		cmmnService.update("fdgi.in.updateFdgiInData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	@RequestMapping(value="/fdgiInUseHisList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgiInUseHisList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		returnMap.put("data", cmmnService.selectList("fdgi.in.selectFdgiInUseHisList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
}

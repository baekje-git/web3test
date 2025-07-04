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

@RequestMapping("/fdgi/mst")
@RestController
public class MstController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CmmnService cmmnService;
	
	@RequestMapping(value="/fdgiMstList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgiMstList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		returnMap.put("data", cmmnService.selectList("fdgi.mst.selectFdgiMstList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }	
	
	@RequestMapping(value="/updateFdgiMstData",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFdgiMstData(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.update("fdgi.mst.updateFdgiMstData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	@RequestMapping(value="/fdgiMstNmByCdData",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgiMstNmByCd(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		returnMap.put("data", cmmnService.select("fdgi.mst.selectFdgiMstNmByCd", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }	
	
	@RequestMapping(value="/fdgiMstQtyList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getFdgiMstQtyList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		
    		System.out.println("!@#$%"+hMap);
    		returnMap.put("data", cmmnService.selectList("fdgi.mst.selectFdgiMstQtyList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
}

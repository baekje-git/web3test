package ever.cmmn.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;

@Controller
public class CmmnApiController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	/**
	 * 여러건의 데이터를 리턴하는 경우
	 * parameter를 json형태로 받기위해서 client쪽에서 post로 호출해야 정상동작
	 * 
	 * @param pDir1 : mybatis의 namespace
	 * @param pDir2 : mybatis의 query id
	 *  
	 */
	@RequestMapping(value="/bindList/{pDir1}/{pDir2}", method = RequestMethod.POST )
	@ResponseBody
	public ResponseEntity<Object> bindList(@PathVariable String pDir1, @PathVariable String pDir2, @RequestBody HashMap<String, Object> hMap, HttpServletRequest request) {
		CmmnUtil.methodLog("bindList : " + hMap);
		
		try {
			List<Object> dataList =  cmmnService.selectList(pDir1 + "." + pDir2, hMap);
			return new ResponseEntity<>(dataList, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]bindList : " + pDir1 + "." + pDir2 + " : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
	}
	
	/**
	 * 한건의 데이터를 리턴하는 경우
	 * 
	 * @param pDir1 : mybatis의 namespace
	 * @param pDir2 : mybatis의 query id
	 *  
	 */
	@RequestMapping(value="/bindRow/{pDir1}/{pDir2}",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> bindRow(@PathVariable String pDir1, @PathVariable String pDir2, @RequestBody HashMap<String, Object> hMap, HttpServletRequest request) {
		CmmnUtil.methodLog("bindRow : " + pDir1 + "." + pDir2);
		
		try {
			HashMap<String, Object> data = (HashMap<String, Object>) cmmnService.select(String.valueOf(pDir1) + "." + pDir2, hMap);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]bindRow : " + pDir1 + "." + pDir2 + " : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	/**
	 * 한건의 데이터를 저장하는 경우
	 * 
	 * @param pDir1 : mybatis의 namespace
	 * @param pDir2 : mybatis의 query id
	 *  
	 */
	@RequestMapping(value="/bindInsert/{pDir1}/{pDir2}",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> bindInsert(@PathVariable String pDir1, @PathVariable String pDir2, @RequestBody HashMap<String, Object> hMap, HttpServletRequest request) {
		CmmnUtil.methodLog("bindInsert : " + pDir1 + "." + pDir2);
		CmmnUtil.methodLog("bindList : " + hMap);
		
		try {
			cmmnService.insert(String.valueOf(pDir1) + "." + pDir2, hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]bindRow : " + pDir1 + "." + pDir2 + " : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	
	

}

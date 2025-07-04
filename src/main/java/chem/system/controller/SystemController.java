package chem.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.wstx.util.StringUtil;

import chem.system.service.SystemService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.jwt.JwtUtil;
import kada.k99.service.RoleService;
import kada.k99.service.UserRegistService;
import kada.util.Util;

@RequestMapping("/system")
@RestController
public class SystemController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SystemService systemService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	CmmnService cmmnService;

	@Autowired
	private UserRegistService userRegistService;

	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/useHisList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> useHisList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("chem.selectUseHisList", hMap));
    		returnMap.put("cnt", cmmnService.select("chem.selectUseHisCnt", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/userList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getUserList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("system.selectUserList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/updateUserUseYn",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateUserUseYn(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.update("system.updateUserUseYn", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	@RequestMapping(value="/insertUserData",  method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> insertUserData(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> userData = (HashMap<String, Object>) cmmnService.select("system.selectUserRegistCheck", hMap);
		System.out.println(">>>>>>>"+userData);
		if (userData == null) {
			try {
				/* 비밀번호 암호화 */
				hMap.put("USER_PWD", Util.SHA256(Util.getValue("USER_PWD", hMap)));			
				cmmnService.insert("system.insertUserData", hMap);
										
				return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
			} catch (Exception e) {
				logger.error("[오류]: ", e);
				return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
			}
		} else {
			return new ResponseEntity<>("사용중인 아이디 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/updateUserData",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateUserData(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		/* 비밀번호 암호화 */
			hMap.put("USER_PWD", Util.SHA256(Util.getValue("USER_PWD", hMap)));			
			cmmnService.update("system.updateUserData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	@RequestMapping(value="/authList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getAuthList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("system.selectAuthList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/insertAuthData",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> insertAuthData(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.insert("system.insertAuthData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }
	
	@RequestMapping(value="/deleteAuthData",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteAuthData(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.delete("system.deleteAuthData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }
	
	@RequestMapping(value="/menuMstList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getMenuMstList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("system.selectMenuMstList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/menuDtlList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getMenuDtlList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("system.selectMenuDtlList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/codeMstList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getCodeMstList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("system.selectCodeMstList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/codeDtlList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getCodeDtlList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("system.selectCodeDtlList", hMap));
    		
			return new ResponseEntity<>(returnMap, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
    }
	
	@RequestMapping(value="/insertCodeMstData",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> insertCodeMstData(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.insert("system.insertCodeMstData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	@RequestMapping(value="/insertCodeDtlData",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> insertCodeDtlData(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.insert("system.insertCodeDtlData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	@RequestMapping(value="/updateCodeMstData",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateCodeMstData(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.update("system.updateCodeMstData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	@RequestMapping(value="/updateCodeDtlData",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateCodeDtlData(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {
    		
    		cmmnService.update("system.updateCodeDtlData", hMap);
    		
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
}

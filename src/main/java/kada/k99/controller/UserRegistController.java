package kada.k99.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.excel.ExcelReadBox;
import ever.support.excel.ExcelUtil;
import kada.k99.service.UserRegistService;
import ever.cmmn.service.CmmnService;

@Controller
@RequestMapping("/k99/userRegist/")
public class UserRegistController {
	final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CmmnService cmmnService;
	@Autowired
	private UserRegistService userRegistService;
	
	/**
     * <pre>
     * @MethodName : saveUserRegist
     * @Description : 회원정보저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveUserRegist.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveUserRegist(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("회원정보저장 ");        
        
        try {
        	userRegistService.saveUserRegist(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteUserRegist
     * @Description : 인증번호신청삭제저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteUserRegist.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteUserRegist(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호삭제 ");        
        
        try {
        		
        	cmmnService.insert("chem.DELETE_USER", hMap);
        	cmmnService.insert("chem.INSERT_USER", hMap);
        	
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteUserRegist : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : initPasswordUser
     * @Description : 비밀번호초기화
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/initPasswordUser.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> initPasswordUser(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("비밀번호초기화 ");        
        
        try {
        	userRegistService.initPasswordUser(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveClubManager
     * @Description : 구단관계자,선수 등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveClubManager.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveClubManager(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("구단관계자등록 ");        
        
        try {
        	userRegistService.saveClubManager(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    

}

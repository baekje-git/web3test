package kada.k03.controller;

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

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.excel.ExcelReadBox;
import ever.support.excel.ExcelUtil;
import kada.k03.service.PlayerLocationService;
import kada.util.EmailSender;
import kada.util.Util;


@Controller
@RequestMapping("/k03/playerlocation/")
public class PlayerLocationController {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PlayerLocationService playerLocationService;
	
	@Autowired
	CmmnService cmmnService;
	/**
     * <pre>
     * @MethodName : insertPlayerLocationDefaultReg
     * @Description : 소재지위반등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/PlayerLocationDefaultReg.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> insertPlayerLocationDefaultReg(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("소재지위반등록 ");        
        
        try {
        	String ipAddr = request.getRemoteAddr();
            String userAgent = request.getHeader("user-agent");
            
            playerLocationService.insertPlayerLocationDefaultReg(hMap,ipAddr,userAgent);
        	
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
    	} catch (Exception e) {
    		logger.error("[오류]saveNoticeReg : ", e);
    		return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
    	}
        
    }
    
    
    
    
    
    /**
     * <pre>
     * @MethodName : updateplayerlocationdefaultEdit
     * @Description : 소재지위반업데이트
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping("/playerlocationdefaultEdit.do")
	@ResponseBody
	public ResponseEntity<Object> updateplayerlocationdefaultEdit(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("소재지위반업데이트 ");        
        
        try {
        	//패스워드변경 내용 확인
        	
        	
    		String Checkpw =hMap.get("USER_PWD").toString();
    		String userId =hMap.get("USER_ID").toString();
    		// 비밀번호 초기화
    		hMap.put("USER_ID", userId);
    		hMap.put("USER_PWD", Util.SHA256(Checkpw));
    		
    		
    		int updatecnt= cmmnService.update("k05.editMember.changePwd", hMap);
    		
    		
    		
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
}

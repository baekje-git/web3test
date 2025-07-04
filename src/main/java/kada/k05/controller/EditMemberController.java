package kada.k05.controller;

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
import kada.k05.service.EditMemberService;
import kada.util.EmailSender;
import kada.util.Util;


@Controller
@RequestMapping("/k05/editMember/")
public class EditMemberController {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EditMemberService editMemberService;
	
	@Autowired
	CmmnService cmmnService;
	/**
     * <pre>
     * @MethodName : savePlayerRegist
     * @Description : 선수정보저장 - 단건 입력/수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/checkEditMember.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> checkEditMember(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("사용자패스워드체크 ");        
        
        try {
    		String Checkpw =hMap.get("PASSWORD").toString();
    		String userId =hMap.get("USER_ID").toString();
    		// 비밀번호 초기화
    		hMap.put("USER_ID", userId);
    		hMap.put("USER_PWD", Util.SHA256(Checkpw)); 
    		
    		
    		HashMap<String, Object> loginInfo = (HashMap<String, Object>) cmmnService.select("login.selectLogin", hMap);
    		
    		String strMsg="패스워드를 확인해주세요";
    		if(loginInfo!=null && loginInfo.size()>0) {
    			strMsg=Gv.SUCCMSG;
    		}
    			
			return new ResponseEntity<>(strMsg, loginInfo!=null && loginInfo.size()>0 ? HttpStatus.OK:HttpStatus.INTERNAL_SERVER_ERROR );
		} catch (Exception e) {
			e.printStackTrace();
			String strMsg="패스워드를 확인해주세요";
			return new ResponseEntity<>(strMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : PlayerExcelUpload
     * @Description : 구단소속선수엑셀업로드
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping("/ChgNewPwReq.do")
	@ResponseBody
	public ResponseEntity<Object> ChgNewPwReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("사용자패스워드변경저장 ");        
        
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

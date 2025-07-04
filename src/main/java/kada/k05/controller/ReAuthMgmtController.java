package kada.k05.controller;
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
import kada.k05.service.ReAuthMgmtService;

@Controller
@RequestMapping("/k05/ReAuthMgmt/")
public class ReAuthMgmtController {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ReAuthMgmtService reAuthMgmtService;

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
        	reAuthMgmtService.saveUserRegist(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
}

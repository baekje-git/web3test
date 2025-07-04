package kada.k99.controller;

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

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import kada.k99.service.LoginImageMngService;

@Controller
@RequestMapping("/k99/loginBgImageMng")
public class LoginImageMngController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LoginImageMngService imageMngService;
	
	/**
     * <pre>
     * @MethodName : selectedLoginBgImage
     * @Description : 로그인페이지의 배경이미지 설정
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/selectedLoginBgImage.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> selectedLoginBgImage(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("로그인페이지의 배경이미지 설정 ");        
        
        try {
        	imageMngService.selectedLoginBgImage(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]selectedLoginBgImage : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }
}

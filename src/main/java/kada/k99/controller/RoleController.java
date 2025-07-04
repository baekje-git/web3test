package kada.k99.controller;

import java.util.HashMap;
import java.util.List;

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
import kada.k99.service.RoleService;

@Controller
@RequestMapping("/k99/role/")
public class RoleController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private RoleService roleService;
	
	/**
     * <pre>
     * @MethodName : saveRole
     * @Description : role,role에 할당된 menu,role을 부여받은 user정보 저장 
     * @param hMap - role정보
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveRole.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveMenuUpper(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("상위메뉴저장 ");        
        
        try {
        	roleService.saveRole(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveMenuUpper : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	
}

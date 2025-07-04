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
import kada.k99.service.MenuService;

@Controller
@RequestMapping("/k99/menu/")
public class MenuController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private MenuService menuService;
	
	/**
     * <pre>
     * @MethodName : saveMenuUpper
     * @Description : 상위메뉴저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveMenuUpper.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveMenuUpper(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("상위메뉴저장 ");        
        
        try {
        	String mode = String.valueOf(hMap.get("MODE"));
        	
        	if("ADD".equals(mode)) {
        		cmmnService.insert("k99.menu.insertUpperMenu", hMap);
        	}else if("UPDATE".equals(mode)) {
        		cmmnService.update("k99.menu.updateUpperMenu", hMap);
        	}

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveMenuUpper : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveMenuLower
     * @Description : 하위메뉴저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveMenuLower.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveMenuLower(@RequestBody List<HashMap<String, Object>> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("하위메뉴저장 ");        
        
        try {
        	menuService.saveMenuLower(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
        
    }
}

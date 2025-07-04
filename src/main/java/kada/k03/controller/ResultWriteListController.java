package kada.k03.controller;

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
import ever.support.session.SessionUtil;
import kada.k03.service.ResultWriteListService;


@Controller
@RequestMapping("/k03/ResultWrite/")
public class ResultWriteListController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private ResultWriteListService resultWriteListService;
	        
	/**
     * <pre>
     * @MethodName : saveResultWriteList
     * @Description : 종이검사결과등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveResultWriteList.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveResultWriteList(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
    	CmmnUtil.methodLog("종이검사결과등록 ");       
        
        try {
        	resultWriteListService.saveResultWriteListRegist(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}

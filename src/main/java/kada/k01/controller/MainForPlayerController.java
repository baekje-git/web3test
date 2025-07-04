package kada.k01.controller;

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
import kada.k01.service.MainForPlayerService;
import kada.util.Util;

@Controller
@RequestMapping("/k01/mainForPlayer/")
public class MainForPlayerController {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private MainForPlayerService mainService;
	
	private String errMsg = "사전일정중 겹치는 건이 존재합니다.확인을 부탁드립니다.";
	
	/**
     * 
     * <pre>
     * @MethodName : insertSchedule
     * @Description : 선수일정 추가
     * @param request  
     * @return
     * </pre>
     */
	@RequestMapping(value="/insertSchedule.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> insertSchedule(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("선수일정 추가 ");        
        
        try {       	
        	mainService.insertSchedule(hMap);
        	return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]insertSchedule : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	/**
     * 
     * <pre>
     * @MethodName : insertRepeatSchedule
     * @Description : 선수반복일정 추가
     * @param request  
     * @return
     * </pre>
     */
	@RequestMapping(value="/insertRepeatSchedule.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> insertRepeatSchedule(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("선수반복일정 추가 ");        
        
        try {        	
        	mainService.insertRepeatSchedule(hMap);
        	return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]insertRepeatSchedule : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	/**
     * 
     * <pre>
     * @MethodName : updateSchedule
     * @Description : 선수일정 수정
     * @param request  
     * @return
     * </pre>
     */
	@RequestMapping(value="/updateSchedule.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateSchedule(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("선수일정 수정 ");        
        
        try {        	
        		
    		/* 구단일정 : 관계자의 소속을 기준으로 저장, 선수 : 저장일기준의 소속을 등록 */
    		if(Util.getValue("PLAYER_ID",hMap) == "") {  
    			HashMap<String, Object> pMap = new HashMap<>();
    			pMap.put("USER_ID", Util.getUserId());
    			HashMap<String, Object> clubCode = (HashMap<String, Object>) cmmnService.select("k05.LocationSchedule.selectClubCode", pMap);
    			hMap.put("PRO_ORGZ", clubCode.get("PRO_ORGZ"));
    			hMap.put("CLUB_CODE", clubCode.get("CLUB_CODE"));
    		}else {
    			HashMap<String, Object> pMap = new HashMap<>();
    			pMap.put("USER_ID", Util.getValue("PLAYER_ID",hMap));
    			HashMap<String, Object> clubCode = (HashMap<String, Object>) cmmnService.select("k05.LocationSchedule.selectClubCode", pMap);
    			hMap.put("PRO_ORGZ", clubCode.get("PRO_ORGZ"));
    			hMap.put("CLUB_CODE", clubCode.get("CLUB_CODE"));
    		}        		
    		
    		hMap.put("GSS", SessionUtil.getUserInfo());
    		cmmnService.insert("k01.main.player.updateSchedule", hMap);
    		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
        	        	        							
		} catch (Exception e) {
			logger.error("[오류]updateSchedule : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }	
	
	/**
     * 
     * <pre>
     * @MethodName : deleteSchedule
     * @Description : 선수일정 삭제
     * @param request  
     * @return
     * </pre>
     */
	@RequestMapping(value="/deleteSchedule.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteSchedule(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("선수일정 삭제 ");        
        
        try {        	
        	cmmnService.insert("k01.main.player.deleteSchedule", hMap);        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteSchedule : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    } 
	
	/**
     * 
     * <pre>
     * @MethodName : deleteCheckedSchedule
     * @Description : 선택된 선수일정 삭제
     * @param request  
     * @return
     * </pre>
     */
	@RequestMapping(value="/deleteCheckedSchedule.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteCheckedSchedule(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("선택된 선수일정 삭제 ");        
        
        try {        	
        	cmmnService.insert("k01.main.player.deleteCheckedSchedule", hMap);        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteCheckedSchedule : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }
	
}

package kada.k03.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
import ever.support.exception.CustomException;
import kada.k03.service.DopingInspectService;

@Controller
@RequestMapping("/k03/dopingInspect/")
public class DopingInspectController {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DopingInspectService dopingInspectService;
	
	/**
     * <pre>
     * @MethodName : daumLocation
     * @Description : 상세주소 조회
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/daumLocation.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> daumLocation(HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("상세주소 조회 ");        
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        
        try {
        	HashMap<String,Object> hMap = CmmnUtil.getParameters(request);
        	logger.info("다음주소 검색 {}",hMap.toString());
        	String extend = "";
        	
        	if(hMap.get("page")!=null) {
        		extend += "&page="+hMap.get("page");
        	}
        	if(hMap.get("size")!=null) {
        		extend += "&size="+hMap.get("size");
        	}
        	URL url = new URL(hMap.get("daumURL") + "?query=" + hMap.get("query")+extend);
        	
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", "KakaoAK 62491384b8db7cb3b7cec7a5757d7759") ;
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

			return new ResponseEntity<>(sb.toString(), HttpStatus.OK);			
		} catch (Exception e) {
			if (reader != null) reader.close();
			if (conn != null) conn.disconnect();
			logger.error("[오류]daumLocation : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveUserRegist
     * @Description : 도핑검사공지 및 신청 - 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveDopingInspect.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveDopingInspect(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("도핑검사공지 및 신청 - 저장 ");        
        
        try {        	
        	dopingInspectService.saveDopingInspect(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]saveDopingInspect : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteDopingInspect
     * @Description : 도핑검사공지 및 신청 - 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/deleteDopingInspect.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteDopingInspect(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("도핑검사공지 및 신청 - 삭제 ");        
        
        try {        	
        	dopingInspectService.deleteDopingInspect(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]도핑검사공지 및 신청 - 삭제 : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : addDopingInspectReadHist
     * @Description : 도핑검사공지 및 신청 - 조회내역 기록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/addDopingInspectReadHist.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addDopingInspectReadHist(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("도핑검사공지 및 신청 - 조회내역 기록 ");        
        
        try {        	
        	dopingInspectService.addDopingInspectReadHist(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]도핑검사공지 및 신청 - 조회내역 기록 : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : sendSMS
     * @Description : 도핑검사공지 및 신청 - 메세지보내기
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/sendSMS.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> sendSMS(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("도핑검사공지 및 신청 - 메세지보내기 ");        
        
        try {        	
        	dopingInspectService.sendSMS(hMap);
			return new ResponseEntity<>("메세지가 발송되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]도핑검사공지 및 신청 - 메세지보내기 : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteChoose
     * @Description : 도핑검사공지 및 신청 - 선정내역삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/deleteChoose.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteChoose(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("도핑검사공지 및 신청 - 선정내역삭제 ");        
        
        try {        	
        	dopingInspectService.deleteChoose(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]도핑검사공지 및 신청 - 선정내역삭제 : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveChoose
     * @Description : 도핑검사공지 및 신청 - 선정내역저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveChoose.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateChoose(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("도핑검사공지 및 신청 - 선정내역저장 ");        
        
        try {        	
        	dopingInspectService.saveChoose(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
        } catch (CustomException e) {	
        	logger.error("[오류]도핑검사공지 및 신청 - 선정내역수정 (1): ", e.getMessage());
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]도핑검사공지 및 신청 - 선정내역수정 (2): ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveComment
     * @Description : 도핑검사공지 및 신청 - 선정관련 평가
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveComment.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveComment(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("도핑검사공지 및 신청 - 선정관련 평가 저장");        
        
        try {        	
        	dopingInspectService.saveComment(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]도핑검사공지 및 신청 - 선정관련 평가 저장: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    /**
     * <pre>
     * @MethodName : deleteComment
     * @Description : 도핑검사공지 및 신청 - 선정관련 평가 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/deleteComment.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteComment(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("도핑검사공지 및 신청 - 선정관련 평가 삭제");        
        
        try {        	
        	dopingInspectService.deleteComment(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]도핑검사공지 및 신청 - 선정관련 평가: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    /**
     * <pre>
     * @MethodName : chooseComplete
     * @Description : 도핑검사공지 및 신청 - 선정완료(modoc 전송)
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/chooseComplete.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> chooseComplete(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("도핑검사공지 및 신청 - 선정완료");        
        
        try {        	
        	dopingInspectService.chooseComplete(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]도핑검사공지 및 신청 - 선정완료: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
}

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
import kada.k01.service.SuggestionsService;


@Controller
@RequestMapping("/k01/suggestions/")
public class SuggestionsController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private SuggestionsService suggestionsService;
	        
	/**
     * <pre>
     * @MethodName : saveReplyDetail
     * @Description : 게시판댓글저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveReplyDetail.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveReplyDetail(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("게시판댓글저장 ");        
        
        try {
        	String mode = String.valueOf(hMap.get("MODE"));
        	int maxRelplySeq=0;
        	if("ADD".equals(mode)) {
        		
        		maxRelplySeq=(int)cmmnService.select("k01.knotice.selectK01_REPLYSequence", hMap);
        		hMap.put("REPLY_SEQ", maxRelplySeq);
        		hMap.put("REPLY_GROUP", maxRelplySeq);
        		hMap.put("USER_AGENT",request.getHeader("user-agent"));
        		hMap.put("IP_ADDR", request.getRemoteAddr());
        		cmmnService.insert("k01.knotice.insertReplyDetail", hMap);
        	}else if("DELETE".equals(mode)) {
        		cmmnService.update("k01.knotice.deleteReply", hMap);
        	}

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveReplyDetail : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveVoteResult
     * @Description : 게시판투표결과저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveVoteResult.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveVoteResult(@RequestBody List<HashMap<String, Object>> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("게시판투표결과저장 ");        
        
        try {
        	
        	suggestionsService.saveVoteResult(hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveVoteResult : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveNoticeReg
     * @Description : 게시판등록저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveNoticeReg.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveNoticeReg(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("게시판등록저장 ");        
        
        try {
        	String ipAddr = request.getRemoteAddr();
            String userAgent = request.getHeader("user-agent");
            
            suggestionsService.saveNoticeReg(hMap,ipAddr,userAgent);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveNoticeReg : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveNoticeEdit
     * @Description : 게시판수정저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveNoticeEdit.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveNoticeEdit(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("일반게시판수정저장 ");        
        
        try {
        	String ipAddr = request.getRemoteAddr();
            String userAgent = request.getHeader("user-agent");
                        
            suggestionsService.saveNoticeEdit(hMap,ipAddr,userAgent);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveNoticeReg : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteNotice
     * @Description : 게시판삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteNotice.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteNotice(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("게시판삭제 ");        
        
        try {
        	
        	//게시판글삭제
        	cmmnService.update("k01.knotice.deleteK01_NOTICE", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteNotice : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteReply
     * @Description : 게시판댓글삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteReply.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteReply(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("댓글삭제 ");        
        
        try {
        	
        	//게시판댓글삭제
        	cmmnService.update("k01.knotice.deleteReply", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveReplyDetail : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
}

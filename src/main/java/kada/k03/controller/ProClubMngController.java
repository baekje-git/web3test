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
import kada.k03.service.ProClubMngService;


@Controller
@RequestMapping("/k03/proclubmng/")
public class ProClubMngController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private ProClubMngService proclubmngService;
	        
	/**
     * <pre>
     * @MethodName : saveProClubMngReg
     * @Description : 신규프로구단등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveProClubMngReg.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveProClubMngReg(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("신규프로구단등록 ");        
        
        try {
        	//String mode = String.valueOf(hMap.get("_STATUS"));
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	cmmnService.insert("k03.proclubmng.insertClubMngReg", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveProClubMngReg : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveProClubMngEdit
     * @Description : 구단정보수정저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveProClubMngEdit.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveProClubMngEdit(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("구단정보수정저장 ");        
        
        try {
        	hMap.put("GSS", SessionUtil.getUserInfo());        	
        	cmmnService.update("k03.proclubmng.updateClubMngEdit", hMap);        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveProClubMngEdit : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    
    /**
     * <pre>
     * @MethodName : deleteProClubMng
     * @Description : 구단등록 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteProClubMng.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteProClubMng(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("구단등록 삭제 ");        
        
        try {
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	cmmnService.insert("k03.proclubmng.deleteClubMngEdit", hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteProClubMng : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }
    
    
    /**
     * <pre>
     * @MethodName : updateClubMngFileSeq
     * @Description : 구단별 이미지 번호 업데이트
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateClubMngFileSeq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateClubMngFileSeq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("구단별 이미지 번호 업데이트 ");        
        
        try {
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	cmmnService.insert("k03.proclubmng.updateClubMngFileSeq", hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateClubMngFileSeq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }
    
    /**
     * <pre>
     * @MethodName : saveClubGroup
     * @Description : 구단별 그룹 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveClubGroup.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveClubGroup(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("구단별 그룹 저장");        
        
        try {
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	proclubmngService.saveClubGroup(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateClubMngFileSeq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }
    
}

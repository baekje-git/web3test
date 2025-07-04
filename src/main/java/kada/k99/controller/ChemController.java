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
import kada.k99.service.ChemService;


@Controller
@RequestMapping("/k99/chem/")
public class ChemController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private ChemService ChemService;
	   
	/**
     * <pre>
     * @MethodName : saveProdNo
     * @Description : 결과관리등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveProdNo.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveProdNo(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("제조지시서저장 ");        
        
        try {
        	ChemService.saveProdNo(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveProdNo : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
	
	/**
     * <pre>
     * @MethodName : saveChem
     * @Description : 결과관리등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveChem.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveChem(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("제조지시서저장 ");        
        
        try {
        	ChemService.saveChem(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveChem : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveInspRecord
     * @Description : 결과관리등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveInspRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveInspRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("작업 전 점검기록서 저장 ");        
        
        try {
        	ChemService.saveInspRecord(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveInspRecord : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveCmpsRecord
     * @Description : 결과관리등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveCmpsRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveCmpsRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("합성작업기록서 저장 ");        
        
        try {
        	ChemService.saveCmpsRecord(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveCmpsRecord : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveDspsRecord
     * @Description : 결과관리등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveDspsRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveDspsRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("분주작업기록서 저장 ");        
        
        try {
        	ChemService.saveDspsRecord(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveDspsRecord : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : savePackRecord
     * @Description : 결과관리등록
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/savePackRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> savePackRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("포장작업기록서 저장 ");        
        
        try {
        	ChemService.savePackRecord(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]savePackRecord : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateFdgBpr
     * @Description : 결과관리수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateChem.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateChem(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("결과관리수정 ");        
        
        try {
        	
        	cmmnService.insert("chem.changeFdgBpr", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]changeFdgBpr : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateInspRecord
     * @Description : 작업 전 점검기록서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateInspRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateInspRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("작업 전 점검기록서  수정 ");        
        
        try {
        	
        	cmmnService.insert("chem.changeFdgCsfm", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]changeFdgCsfm : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateCmpsRecord
     * @Description : 작업 전 점검기록서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateCmpsRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateCmpsRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("합성작업 기록서  수정 ");        
        
        try {
        	
        	cmmnService.insert("chem.changeFdgCmps", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]changeFdgCsfm : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateDspsRecord
     * @Description : 작업 전 점검기록서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateDspsRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateDspsRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("분주작업 기록서  수정 ");        
        
        try {
        	
        	cmmnService.insert("chem.changeFdgDsps", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]changeFdgDsps : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updatePackRecord
     * @Description : 포장 점검기록서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updatePackRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updatePackRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("포장작업 기록서  수정 ");        
        
        try {
        	
        	cmmnService.insert("chem.changeFdgPack", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]changeFdgPack : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteChem
     * @Description : 인증번호신청삭제저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteChem.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteChem(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호삭제 ");        
        
        try {
        		
        	cmmnService.insert("chem.deleteFdgBpr", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteChem : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteInspRecord
     * @Description : 인증번호신청삭제저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteInspRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteInspRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호삭제 ");        
        
        try {
        		
        	cmmnService.insert("chem.deleteFdgCsfm", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteInspRecord : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteCmpsRecord
     * @Description : 인증번호신청삭제저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteCmpsRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteCmpsRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호삭제 ");        
        
        try {
        		
        	cmmnService.insert("chem.deleteFdgCmpsfm", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteCmpsRecord : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteDspsRecord
     * @Description : 인증번호신청삭제저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteDspsRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteDspsRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호삭제 ");        
        
        try {
        		
        	cmmnService.insert("chem.deleteFdgDspsfm", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteDspsRecord : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deletePackRecord
     * @Description : 인증번호신청삭제저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deletePackRecord.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deletePackRecord(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("인증번호삭제 ");        
        
        try {
        		
        	cmmnService.insert("chem.deleteFdgPackfm", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deletePackRecord : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    
    
}

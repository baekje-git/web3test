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
import kada.k99.service.FinishService;


@Controller
@RequestMapping("/k99/finish/")
public class FinishController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private FinishService FinishService;
	        
	/**
     * <pre>
     * @MethodName : saveFnReq
     * @Description : 완제품 시험의뢰서 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveFnReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveFnReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험의뢰서저장 ");        
        
        try {
        	FinishService.saveFnReq(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveFnReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveFnIns
     * @Description : 완제품 시험지시서 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveFnIns.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveFnIns(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험지시서 저장 ");        
        
        try {
        	FinishService.saveFnIns(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveFnIns : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveFnRep
     * @Description : 완제품 시험성적서 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveFnRep.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveFnRep(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험성적서 저장 ");        
        
        try {
        	FinishService.saveFnRep(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveFnRep : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveFnRec
     * @Description : 완제품 시험성적서 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveFnRec.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveFnRec(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험성적서 저장 ");        
        
        try {
        	FinishService.saveFnRec(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveFnRec : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveFnRec
     * @Description : 완제품 시험성적서 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveFnSt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveFnSt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("무균 시험성적서 ");        
        
        try {
        	FinishService.saveFnSt(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveFnSt : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveFnCs
     * @Description : 완제품 조건부출하승인서
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveFnCs.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveFnCs(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("무균 시험성적서 ");        
        
        try {
        	FinishService.saveFnCs(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveFnCs : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : saveFnFc
     * @Description : 완제품 조건부출하승인서
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveFnFc.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveFnFc(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("무균 시험성적서 ");        
        
        try {
        	FinishService.saveFnFc(hMap);
        	//cmmnService.insert("k99.certi.insertAUTH_STAGE", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveFnFc : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateFnReq
     * @Description : 완제품 시험의뢰서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateFnReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFnReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험의뢰서수정 ");        
        
        try {
        	
        	cmmnService.insert("finish.updateFnReq", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateFnReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    

    
    /**
     * <pre>
     * @MethodName : updateFnIns
     * @Description : 완제품 시험지시서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateFnIns.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFnIns(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험지시서수정 ");        
        
        try {
        	
        	cmmnService.insert("finish.updateFnIns", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateFnIns : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateFnRep
     * @Description : 완제품 시험성적서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateFnRep.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFnRep(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험성적서수정 ");        
        
        try {
        	
        	cmmnService.insert("finish.updateFnRep", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateFnRep : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateFnRec
     * @Description : 완제품 시험의뢰서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateFnRec.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFnRec(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험의뢰서수정 ");        
        
        try {
        	
        	cmmnService.insert("finish.updateFnRec", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateFnRec : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateFnSt
     * @Description : 무균 시험성적서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateFnSt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFnSt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험의뢰서수정 ");        
        
        try {
        	
        	cmmnService.insert("finish.updateFnSt", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateFnSt : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateFnCs
     * @Description : 완제품 조건부출하승인서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * 
     * </pre>
     */                     
    @RequestMapping(value="/updateFnCs.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFnCs(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 조건부출하승인서 ");        
        
        try {
        	
        	cmmnService.insert("finish.updateFnCs", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateFnCs : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : updateFnFc
     * @Description : 완제품 조건부출하승인서 수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateFnFc.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateFnFc(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 최종출하승인서 ");        
        
        try {
        	
        	cmmnService.insert("finish.updateFnFc", hMap);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateFnFc : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    /**
     * <pre>
     * @MethodName : deleteFnReq
     * @Description : 완제품 시험의뢰서 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteFnReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteFnReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험의뢰서 삭제 ");        
        
        try {
        		
        	cmmnService.insert("finish.deleteFnReq", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteFnReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteFnIns
     * @Description : 완제품 시험지시서 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteFnIns.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteFnIns(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험지시서 삭제 ");        
        
        try {
        		
        	cmmnService.insert("finish.deleteFnIns", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteFnIns : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteFnRep
     * @Description : 완제품 시험성적서 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteFnRep.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteFnRep(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험성적서 삭제 ");        
        
        try {
        		
        	cmmnService.insert("finish.deleteFnRep", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteFnRep : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteFnRec
     * @Description : 완제품 시험성적서 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteFnRec.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteFnRec(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 시험성적서 삭제 ");        
        
        try {
        		
        	cmmnService.insert("finish.deleteFnRec", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteFnRec : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteFnRec
     * @Description : 무균 시험성적서 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteFnSt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteFnSt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("무균 시험성적서 삭제 ");        
        
        try {
        		
        	cmmnService.insert("finish.deleteFnSt", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteFnSt : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteFnCs
     * @Description : 완제품 조건부출하승인서 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteFnCs.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteFnCs(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 조건부출하승인서 삭제 ");        
        
        try {
        		
        	cmmnService.insert("finish.deleteFnCs", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteFnCs : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteFnFc
     * @Description : 완제품 조건부출하승인서 삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteFnFc.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteFnFc(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("완제품 최종출하승인서 삭제 ");        
        
        try {
        		
        	cmmnService.insert("finish.deleteFnFc", hMap);
        	

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]deleteFnFc : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
}

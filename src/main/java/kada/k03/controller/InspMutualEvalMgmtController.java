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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import kada.k03.service.InspMutualEvalMgmtService;

@Controller
@RequestMapping("/k03/inspmutualmgmt/")
public class InspMutualEvalMgmtController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private InspMutualEvalMgmtService inspmutualevalmgmtService;
	
	/**
     * <pre>
     * @MethodName : saveCodeMst
     * @Description : 대분류코드저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveEvalutionMngMst.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveEvalutionMngMst(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("상호평가질의항목마스터저장 ");        
        
        try {
        	String mode = String.valueOf(hMap.get("MODE"));
        	
        	if("ADD".equals(mode)) {
        		cmmnService.insert("k03.InspMutual.insertEvalutionMngMst", hMap);
        	}else if("UPDATE".equals(mode)) {
        		cmmnService.update("k03.InspMutual.updateEvalutionMngMst", hMap);
        	}

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveEvalutionMngMst : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
	/**
     * <pre>
     * @MethodName : savesaveEvalutionMngDetail
     * @Description : 상호평가질의항목마스터상세저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/savesaveEvalutionMngDetail.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> savesaveEvalutionMngDetail(@RequestBody List<HashMap<String, Object>> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("상호평가질의항목마스터상세저장 ");        
        
        try {
        	inspmutualevalmgmtService.savesaveEvalutionMngDetail(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
	 * 여러건의 코드 데이터를 리턴하는 경우
	 * parameter를 json형태로 받기위해서 client쪽에서 post로 호출해야 정상동작
	 * 
	 */
	@RequestMapping(value="/getCodeList", method = RequestMethod.POST )
	@ResponseBody
	public ResponseEntity<Object> bindList(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) {
		CmmnUtil.methodLog("getCodeList : " + hMap);
		
		try {
			List<Object> dataList =  cmmnService.selectList("k99.code.getCodeList", hMap);
			return new ResponseEntity<>(dataList, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]getCodeList : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
	}
}

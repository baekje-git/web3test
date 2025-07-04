package kada.k05.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.excel.ExcelReadBox;
import ever.support.excel.ExcelUtil;
import kada.k05.service.LocationScheduleMngService;
import kada.util.Util;
import ever.support.cmmn.GSSMap;


@Controller
@RequestMapping("/k05/locationschedulemng/")
public class LocationScheduleMngController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private LocationScheduleMngService locationschedulemngService;
	        
	/**
     * <pre>
     * @MethodName : LocationScheduleMngexcelUpload
     * @Description : 소재지정보스케줄관리엑셀업로드
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping("/LocationScheduleMngexcelUpload.do")
	@ResponseBody
	public ResponseEntity<Object> LocationScheduleMngexcelUpload(HttpServletRequest request) {
		CmmnUtil.methodLog("소재지정보스케줄관리엑셀업로드");
				
		try {
			// ------------------------------------------------------------------------------------------
	        // 파일정보 읽어오기
	        // ------------------------------------------------------------------------------------------
	        MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
	        MultipartFile excelFile = mptRequest.getFile("UPLOAD_FILE"); // 한개파일
	        
	        String fileName  = excelFile.getOriginalFilename();
	        String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	        
	        logger.info("fileName   : {}, extension  : {}", fileName,  extension);
	        
	        if(!extension.equals("xls")&&!extension.equals("xlsx")) {	            
	            return new ResponseEntity<>(ExcelReadBox.RESULT_MESSAGE_NOT_EXCEL, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        // ------------------------------------------------------------------------------------------
	        // 엑셀내용 읽어오기
	        // ------------------------------------------------------------------------------------------
	        String[] columsList = {"PLAYER_ID","START_DT","START_DT2","END_DT","END_DT2","LOCATION_CD","LOCATION_NM","LOCATION_ADDRESS","SCHEDULE_MEMO"};
	        ExcelReadBox box    = ExcelUtil.readToList(excelFile, columsList , 2, 0);
	        logger.info("box  : " + box );
	        List<Map<String, Object>> excelList  = box.getList();
	        
	        logger.info("excelList  : " + excelList );
	        logger.info("excelList.size()  : " + excelList.size());
	        int errCnt = locationschedulemngService.saveLocationScheduleMngList(excelList);
	        
	        // ------------------------------------------------------------------------
	        // 업로드한 파일자료 성공여부 체크 100
	        // ------------------------------------------------------------------------
	        if(!ExcelReadBox.RESULT_CODE_SUCCESS.equals(box.getResultCode())){	            
	            return new ResponseEntity<>(box.getResultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        if(excelList.size() > 1000) {
	            return new ResponseEntity<>("허용된 사이즈가 초과했습니다.(1000건미만)", HttpStatus.OK);
	        }
	        
	        if(errCnt > 0) {
	        	return new ResponseEntity<>(errCnt+"건의 데이터가 입력중 에러가 발생했습니다.", HttpStatus.OK);
	        }
	        
			return new ResponseEntity<>(box.getResultMessage(), HttpStatus.OK);
		} catch (Exception e) {			
			logger.error("엑셀업로드 오류 : ", e);
			return new ResponseEntity<>(ExcelReadBox.RESULT_MESSAGE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    /**
     * <pre>
     * @MethodName : saveLocationScheduleMng
     * @Description : 구단일정저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveLocationScheduleMng.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveLocationScheduleMng(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("구단일정저장 ");        
        
        try {
        	/*
        	if(!"DELETE".equals(Util.getValue("MODE", hMap))) {
        		List<Object> checkTerms = cmmnService.selectList("k05.LocationSchedule.selectTermCheckByClub", hMap);
            	if(checkTerms.size() > 0) {
            		return new ResponseEntity<>("중복된 일정이 존재합니다.", HttpStatus.OK);
            	}	
        	}*/
        	
        	locationschedulemngService.saveLocationScheduleMng(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("구단일정 저장 오류 : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveRepeatLocationScheduleMng
     * @Description : 구단반복일정저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/saveRepeatLocationScheduleMng.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveRepeatLocationScheduleMng(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("구단반복일정저장 ");        
        
        try {
        	int errCnt = locationschedulemngService.saveRepeatLocationScheduleMng(hMap);
        	
        	if(errCnt > 0) {
        		HashMap<String,Object> resMap = new HashMap<>();
        		resMap.put("errCnt", errCnt);
        		resMap.put("errMsg", errCnt+"건의 데이터가 중복일정으로 입력에서 제외되었습니다.");
        		resMap.put("error", hMap.get("ERROR"));
	        	return new ResponseEntity<>(resMap, HttpStatus.OK);
	        }
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("구단일정 저장 오류 : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteLocationScheduleMng
     * @Description : 구단일정삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/deleteLocationScheduleMng.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteLocationScheduleMng(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("구단일정삭제 ");        
        
        try {
        	locationschedulemngService.deleteLocationScheduleMng(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("구단일정 저장 오류 : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    
}

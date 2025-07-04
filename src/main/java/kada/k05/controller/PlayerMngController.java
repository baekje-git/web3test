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

import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.excel.ExcelReadBox;
import ever.support.excel.ExcelUtil;
import kada.k05.service.PlayerMngService;

@Controller
@RequestMapping("/k05/playerMng/")
public class PlayerMngController {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PlayerMngService playerMngService;
	
	/**
     * <pre>
     * @MethodName : savePlayerRegist
     * @Description : 선수정보저장 - 단건 입력/수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/savePlayerRegist.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> savePlayerRegist(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("선수정보저장 ");        
        
        try {
        	playerMngService.saveUserRegist(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : savePlayerRegist
     * @Description : 프로단체선수정보저장 - 단건 입력/수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="/proorgsavePlayerRegist.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> proorgsavePlayerRegist(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("프로단체선수정보저장 ");        
        
        try {
        	playerMngService.proorgsaveUserRegist(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : PlayerExcelUpload
     * @Description : 구단소속선수엑셀업로드
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping("/PlayerExcelUpload.do")
	@ResponseBody
	public ResponseEntity<Object> PlayerExcelUpload(HttpServletRequest request) {
		CmmnUtil.methodLog("구단소속선수엑셀업로드");
				
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
	        String[] columsList = {"NO",
	        		               "USER_ID",
	        		               "USER_FAMILYNAME",
	        		               "USER_NAME",
	        		               "USER_EN_FAMILYNAME",
	        		               "USER_EN_NAME",
	        		               "USER_SEX",
	        		               "USER_DATEOFBIRTH",
	        		               "USER_PHONE",
	        		               "USER_EMAIL",
	        		               "USER_ADDRESS",
	        		               "USER_COUNTRY",
	        		               "USER_GROUP"};
	        ExcelReadBox box    = ExcelUtil.readToList(excelFile, columsList , 2, 0);
	        logger.info("box  : " + box );
	        List<Map<String, Object>> excelList  = box.getList();
	        
	        logger.info("excelList  : " + excelList );
	        logger.info("excelList.size()  : " + excelList.size());
	        int errCnt = playerMngService.saveUserList(excelList);
	        
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
    
}

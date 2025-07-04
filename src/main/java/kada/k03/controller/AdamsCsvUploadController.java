package kada.k03.controller;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
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
import ever.support.session.SessionUtil;
import kada.k03.service.AdamsCsvUploadService;

@Controller
@RequestMapping("/k03/adams/")
public class AdamsCsvUploadController {
	final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CmmnService cmmnService;
	@Autowired
	private AdamsCsvUploadService adamsService;
	
	    
    /**
     * <pre>
     * @MethodName : csvUpload
     * @Description : CSV업로드
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping("/csvUpload.do")
	@ResponseBody
	public ResponseEntity<Object> PlayerExcelUpload(HttpServletRequest request) {
		CmmnUtil.methodLog("adams CSV업로드");
				
		try {
			// ------------------------------------------------------------------------------------------
	        // 파일정보 읽어오기
	        // ------------------------------------------------------------------------------------------
	        MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
	        MultipartFile csvlFile = mptRequest.getFile("UPLOAD_FILE"); // 한개파일
	        String gubun = mptRequest.getParameter("GUBUN");
	        
	        String fileName  = csvlFile.getOriginalFilename();
	        String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	        
	        logger.info("fileName   : {}, extension  : {}", fileName,  extension);
	        
	        if(!extension.equals("csv")) {	            
	            return new ResponseEntity<>("csv파일이 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        // ------------------------------------------------------------------------------------------
	        // CSV내용 읽어오기
	        // ------------------------------------------------------------------------------------------
	       	        
	        List<String> HEADERS = getHeaders(gubun);
	        Reader in =  new InputStreamReader(csvlFile.getInputStream());
	        CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(in);
	        logger.info("업로드 CSV의 HeaderNames===> ", csvParser.getHeaderNames());
	        
	        if (!csvParser.getHeaderNames().equals(HEADERS)) {	            
	            return new ResponseEntity<>(Gv.CSV_HEADER_FAIL_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
	        }	        	        
	        
	        List<CSVRecord> addData = new ArrayList<CSVRecord>();
	        for (CSVRecord csvRecord : csvParser) {
	        	for (String value : HEADERS) {
	                if(!StringUtils.isEmpty(csvRecord.get(value))) {                	
	                	addData.add(csvRecord);
	                	break;                	
	                }
	            }
	        }
	        
	        List<HashMap> errors = adamsService.saveCsv(gubun, addData);
	        logger.info("CSV size  : " + addData.size());
	        
	        // ------------------------------------------------------------------------
	        // 업로드한 파일자료 성공여부 체크 100
	        // ------------------------------------------------------------------------
	       
	        if(errors.size() > 0) {
	        	HashMap<String, Object> errMap = new HashMap<>(); 
	        	errMap.put("errMsg", errors.size()+"건의 데이터가 입력중 에러가 발생했습니다.");
	        	errMap.put("errors", errors);
	        	return new ResponseEntity<>(errMap, HttpStatus.OK);
	        }
	        
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {			
			logger.error("CSV파일 업로드 오류 : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    private List<String> getHeaders(String gubun) {
    	List<String> list = new ArrayList<>();
    	if("NEG".equals(gubun)) {
    		list.add("lin");
    		list.add("sample_code");
    		list.add("analysis_attribute");
    		list.add("test_result");
    		list.add("analysis_report_date");
    		list.add("mo_number");
    		list.add("sample_type");
    		list.add("sampleAB");
    		list.add("sample_collection_date");
    		list.add("date_received");
    		list.add("ta");
    		list.add("sca");
    		list.add("rma");
    		list.add("test_type");
    		list.add("sport_code");
    		list.add("discipline_code");
    		list.add("gender");
    		list.add("steroid_profile_variable_code1");
    		list.add("steroid_profile_variable_code2");
    		list.add("steroid_profile_variable_code3");
    		list.add("steroid_profile_variable_code4");
    		list.add("steroid_profile_variable_code5");
    		list.add("steroid_profile_variable_code6");
    		list.add("steroid_profile_variable_code7");
    		list.add("CF_code1");
    		list.add("CF_code2");
    		list.add("CF_code3");
    		list.add("CF_code4");
    		list.add("CF_code5");
    		list.add("CF_code6");
    		list.add("CF_code7");
    		list.add("CF_code8");
    		list.add("CF_code9");
    		list.add("CF_code10");
    		list.add("CF_code11");
    		list.add("CF_code12");
    		list.add("CF_code13");
    		list.add("specific_gravity");
    		list.add("sample_specific_gravity_CP");
    		list.add("ratio_5aand_a");
    		list.add("ratio_5band_etio");
    		list.add("ratio_freet_totalt");
    		list.add("steroid_profile_variable_value1");
    		list.add("steroid_profile_variable_value2");
    		list.add("steroid_profile_variable_value3");
    		list.add("steroid_profile_variable_value4");
    		list.add("steroid_profile_variable_value5");
    		list.add("steroid_profile_variable_value6");
    		list.add("steroid_profile_variable_value7");
    		list.add("CF_presence1");
    		list.add("CF_presence2");
    		list.add("CF_presence3");
    		list.add("CF_presence4");
    		list.add("CF_presence5");
    		list.add("CF_conc5");
    		list.add("CF_presence6");
    		list.add("CF_presence7");
    		list.add("CF_conc7");
    		list.add("CF_presence8");
    		list.add("CF_conc8");
    		list.add("CF_presence9");
    		list.add("CF_conc9");
    		list.add("CF_presence10");
    		list.add("CF_conc10");
    		list.add("CF_presence11");
    		list.add("CF_conc11");
    		list.add("CF_presence12");
    		list.add("CF_conc12");
    		list.add("CF_presence13");
    		list.add("analysis_details");
    		list.add("monitoring");
    	} else if("BLOOD".equals(gubun)) {
    		list.add("lin");	
    		list.add("sample_code");
    		list.add("mo_number");	
    		list.add("sample_type");	
    		list.add("sample_collection_date");	
    		list.add("date_received");	
    		list.add("analysis_report_date");	
    		list.add("ta");	
    		list.add("sca");	
    		list.add("rma");	
    		list.add("test_type");	
    		list.add("sport_code");	
    		list.add("discipline_code");	
    		list.add("gender");	
    		list.add("sampleAB");	
    		list.add("test_result");	
    		list.add("analysis_attribute");
    	} else if("ABP".equals(gubun)) {
    		list.add("lin");
    		list.add("sample_code");
    		list.add("mo_number");
    		list.add("sample_type");
    		list.add("date_results_reported");
    		list.add("ta");
    		list.add("sca");
    		list.add("rma");
    		list.add("test_type");
    		list.add("sport_code");
    		list.add("discipline_code");
    		list.add("gender");
    		list.add("lab");
    		list.add("date_collection");
    		list.add("date_received");
    		list.add("analysis_date");
    		list.add("HGB");
    		list.add("HCT");
    		list.add("IRF");
    		list.add("MCH");
    		list.add("MCHC");
    		list.add("MCV");
    		list.add("OFF-Score");
    		list.add("PLT");
    		list.add("RDW-SD");
    		list.add("RBC");
    		list.add("RET#");
    		list.add("RET%");
    		list.add("WBC");
    		list.add("analyser");
    	}
    	
    	return list;
    }
    
    /**
     * <pre>
     * @MethodName : updateCsvResultMgmt
     * @Description : CSVResult수정
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/updateCsvResultMgmt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateCsvResultMgmt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("CSVResult수정 ");        
        
        try {
        	adamsService.updateCsvResultMgmt(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]updateResultMgmt : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : deleteCsvResultMgmt
     * @Description : CSVResult삭제
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/deleteCsvResultMgmt.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteCsvResultMgmt(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("CSVResult삭제 ");        
        
        try {
        	adamsService.deleteCsvResultMgmt(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
		} catch (Exception e) {
			logger.error("[오류]deleteCsvResultMgmt : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
}

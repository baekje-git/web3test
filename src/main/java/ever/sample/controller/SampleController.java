package ever.sample.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import ever.sample.service.SampleService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.cmmn.GSSMap;
import ever.support.excel.ExcelReadBox;
import ever.support.excel.ExcelUtil;
import ever.support.exception.AjaxMsgCustomException;
import ever.support.file.FileUtil;
import ever.support.jwt.JwtUtil;

/**
 * 
 * <pre>
 * -------------------------------------------------------------
 * &#64;프로젝트 : ever
 * &#64;파일경로 : ever.login.controller
 * &#64;파일이름 : LoginController.java 
 * &#64;작성일자 : 2019. 12. 5. 
 * &#64;작성자명 : KMSDEV 
 * &#64;상세설명 : 로그인 컨트롤러
 * -------------------------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping("/sample/")
public class SampleController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CmmnService cmmnService;

	@Autowired
	private SampleService sampleService;
	
	@Autowired
	private JwtUtil jwt;

	/**
	 * 
	 * <pre>
	 * &#64;MethodName : 
	 * &#64;Date       : 2019. 12. 5. 오후 5:44:45
	 * &#64;Writer     : KMSDEV
	 * &#64;Description :
	 * &#64;param request
	 * &#64;return
	 * </pre>
	 */
	@RequestMapping(value = "sample.do", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request) {
		CmmnUtil.methodLog("샘플 페이지");
		return "sample/sample";
	}

	@RequestMapping("/hello")
	@ResponseBody
	public Object hello() {
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		mapList.put("Message", "성공");
		return mapList;		
	}

	/**
	 * 
	 * <pre>
	 * &#64;MethodName : selectSampleList
	 * &#64;Date       : 2020. 1. 21. 오후 5:55:51
	 * &#64;Writer     : MSKANG
	 * &#64;Description : 샘플조회
	 * &#64;param request
	 * &#64;return
	 * &#64;throws Exception
	 * </pre>
	 */
	@RequestMapping("/selectSampleList.ajax")
	@ResponseBody
	public Object selectCmmnCodeList(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("샘플리스트 조회");
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectSampleList", CmmnUtil.getParameters(request));
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");
		return mapList;
	}

	/**
	 * 
	 * <pre>
	 * &#64;MethodName : selectDataTypeList
	 * &#64;Date       : 2020. 1. 21. 오후 5:55:51
	 * &#64;Writer     : MSKANG
	 * &#64;Description : 샘플조회
	 * &#64;param request
	 * &#64;return
	 * &#64;throws Exception
	 * </pre>
	 */
	@RequestMapping("/selectDataTypeList.ajax")
	@ResponseBody
	public Object selectDataTypeList(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("데이터타입 조회");
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectDataTypeList", CmmnUtil.getParameters(request));
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");
		return mapList;
	}

	/**
	 * 
	 * <pre>
	 * &#64;MethodName : selectSumTypeList
	 * &#64;Date       : 2020. 1. 21. 오후 5:55:51
	 * &#64;Writer     : MSKANG
	 * &#64;Description : 샘플조회
	 * &#64;param request
	 * &#64;return
	 * &#64;throws Exception
	 * </pre>
	 */
	@RequestMapping("/selectSumTypeList.ajax")
	@ResponseBody
	public Object selectSumTypeList(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("데이터타입 조회");
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectSumTypeList", CmmnUtil.getParameters(request));
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");
		return mapList;
	}

	/**
	 * 
	 * <pre>
	 * &#64;MethodName : selectSampleList
	 * &#64;Date       : 2020. 1. 21. 오후 5:55:51
	 * &#64;Writer     : MSKANG
	 * &#64;Description : 샘플조회
	 * &#64;param request
	 * &#64;return
	 * &#64;throws Exception
	 * </pre>
	 */
	@RequestMapping(value = "/saveSample.ajax")
	@ResponseBody
	public Object saveSample(HttpServletRequest request) throws Exception {
		HashMap<String, Object> hMap = CmmnUtil.parsingToMap(request.getParameter("paramData"));

		List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) hMap.get("gridData");

		// Transaction은 service레벨부터 설정되어있음
		if (dataList.size() > 0) {
			for (HashMap<String, Object> dataMap : dataList) {
				System.out.println(dataMap);
				if (!"Deleted".equals(dataMap.get("STATUS").toString())) {
					cmmnService.insert("sample.saveSample", dataMap);
				} else {
					cmmnService.delete("sample.deleteSample", dataMap);
				}
			}
		}

		return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG);
	}

	/**
	 * 
	 * <pre>
	 * &#64;MethodName : selectSumTypeList
	 * &#64;Date       : 2020. 1. 21. 오후 5:55:51
	 * &#64;Writer     : MSKANG
	 * &#64;Description : 샘플조회
	 * &#64;param request
	 * &#64;return
	 * &#64;throws Exception
	 * </pre>
	 */
	@RequestMapping("/selectSolidList.ajax")
	@ResponseBody
	public Object selectSolidList(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("데이터타입 조회");
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectSolidList", CmmnUtil.getParameters(request));
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");
		return mapList;
	}

	/**
	 * 
	 * <pre>
	 * &#64;MethodName : selectTreeList
	 * &#64;Date       : 2020. 1. 21. 오후 5:55:51
	 * &#64;Writer     : MSKANG
	 * &#64;Description : 샘플조회
	 * &#64;param request
	 * &#64;return
	 * &#64;throws Exception
	 * </pre>
	 */
	@RequestMapping("/selectTreeList.ajax")
	@ResponseBody
	public Object selectTreeList(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("트리 데이터 조회");
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectTreeList", CmmnUtil.getParameters(request));
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");
		return mapList;
	}

	/**
	 * 
	 * <pre>
	 * &#64;MethodName : selectTreeList
	 * &#64;Date       : 2020. 1. 21. 오후 5:55:51
	 * &#64;Writer     : MSKANG
	 * &#64;Description : 샘플조회
	 * &#64;param request
	 * &#64;return
	 * &#64;throws Exception
	 * </pre>
	 */
	@RequestMapping("/selectPagingList.ajax")
	@ResponseBody
	public Object selectPagingList(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("서버 페이징 데이터 조회");
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		HashMap<String, Object> pMap = new GSSMap<String, Object>();

		pMap = CmmnUtil.getParameters(request);

//        System.out.println("pMap:"+pMap.toString());

		// 페이징 조회시 전체 건수를 알아야 함
		int totalCnt = (int) cmmnService.select("sample.selectTotalCount", pMap);
//        System.out.println("pageCount:"+request.getParameter("recordCountPerPage"));
//        System.out.println("ibpage:"+pMap.get("ibpage"));
		int recordCountPerPage = Integer.parseInt(pMap.get("recordCountPerPage") + "");
//        int recordCountPerPage = 20;
//
		int startIdx, endIdx;
		int ibpage = Integer.parseInt(pMap.get("ibpage") + "");
//        int ibpage = 2;

		startIdx = ibpage == 1 ? 1 : ((ibpage - 1) * recordCountPerPage) + 1;
		;
		endIdx = startIdx + (recordCountPerPage - 1);

		pMap.put("startIdx", startIdx);
		pMap.put("endIdx", endIdx);

		List<Object> list = cmmnService.selectList("sample.selectPagingList", pMap);

		mapList.put("Data", list);
		mapList.put("Total", totalCnt);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");

		return mapList;
	}

	@RequestMapping(value = "/save1.ajax")
	@ResponseBody
	public Object save1(HttpServletRequest request) throws Exception {
		sampleService.save1();
		return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG);
	}

	@RequestMapping(value = "/save2.ajax")
	@ResponseBody
	public Object save2(HttpServletRequest request) throws Exception {
		sampleService.save2();
		return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG);
	}

	@RequestMapping(value = "/save3.ajax")
	@ResponseBody
	public Object save3(HttpServletRequest request) throws Exception {
		HashMap<String, Object> hMap = new HashMap();
		hMap.put("GROUP_TXT", "TEST");

		sampleService.saveDynamic(hMap);
		return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG);
	}

	@RequestMapping("/ajaxException.ajax")
	@ResponseBody
	public Object ajaxException(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("데이터타입 조회");
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectSumTypeList", CmmnUtil.getParameters(request));
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");

		if (true) {
			throw new AjaxMsgCustomException("강제 익셉션 발생.");
		}
		return mapList;
	}

	@RequestMapping("/batchTest.ajax")
	public void batchTest(HttpServletRequest request) throws Exception {

		long startTime = System.currentTimeMillis();

		List<HashMap> listMap = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			HashMap hMap = new HashMap();
			hMap.put("GROUP_TXT", RandomStringUtils.randomAlphanumeric(20));
			listMap.add(hMap);
		}

		sampleService.saveBatchDynamic(listMap);

		long endTime = System.currentTimeMillis();
		long resutTime = endTime - startTime;
		System.out.println(" 소요시간  : " + resutTime / 1000 + "(ms)");
	}

	@RequestMapping("/mygration.ajax")
	public void mygration(HttpServletRequest request) throws Exception {
		myg1();
	}

	private void myg1() throws Exception {
		System.out.println("매출Detail 시작");
		long startTime = System.currentTimeMillis();
		HashMap hMap = new HashMap();
		List<HashMap> listMap = (List<HashMap>) cmmnService.selectListDynamic(Gv.DYNAMIC_DB_CODEGROUP_KEY + "ON_MS",
				"ms.매출Detail", hMap);
		System.out.println(listMap.size());
		sampleService.saveBatchDynamic(listMap);

		long endTime = System.currentTimeMillis();
		long resutTime = endTime - startTime;
		System.out.println("매출Detail 소요시간  : " + resutTime / 1000 + "(ms)");
	}
	
	@RequestMapping("/selectCODE00TB.do")
	@ResponseBody
	public Object selectCODE00TB(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("코드마스터 조회");
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectCODE00TB", CmmnUtil.getParameters(request));
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");
		
		return mapList;
	}
	
	@RequestMapping(value = "/saveCODE00TB.do")
	@ResponseBody
	public Object saveCODE00TB(@RequestBody HashMap<String, Object> hMap,HttpServletRequest request) throws Exception {
		logger.info("saveCODE00TB.do ==>{}", hMap);
		sampleService.saveCODE00TB(hMap);
		return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG);
	}
	
	@RequestMapping("/selectCODE01TB.do")
	@ResponseBody
	public Object selectCODE01TB(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("코드마스터 조회");
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectCODE01TB", CmmnUtil.getParameters(request));
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");
		
		return mapList;
	}
	
	@RequestMapping(value = "/saveCODE01TB.do")
	@ResponseBody
	public Object saveCODE01TB(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) {
		logger.info("saveCODE01TB.do ==>{}", hMap);
		
		try {
			sampleService.saveCODE01TB(hMap);
			return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG);
		} catch (Exception e) {
			e.printStackTrace();
			return CmmnUtil.resultMsg(Gv.FAIL, Gv.FAILMSG);
		}
		
	}
	
	@RequestMapping("/selectUserInfo.do")
	@ResponseBody
	public Object selectUserInfo(HttpServletRequest request) {
		CmmnUtil.methodLog("유저정보 조회");		
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		String userId = jwt.getUserId(jwtToken);
		HashMap<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("USER_ID", userId);
				
		try {
			HashMap<String, Object> userInfo = (HashMap<String, Object>) cmmnService.select("sample.selectUserInfo", pMap);
			return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG, userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return CmmnUtil.resultMsg(Gv.FAIL, Gv.FAILMSG);
		}
	}
	
	@RequestMapping("/fileUpload.do")
	@ResponseBody
	public Object fileUpload(HttpServletRequest request) {
		CmmnUtil.methodLog("파일업로드==>"+CmmnUtil.getParameters(request));
				
		try {
			List<HashMap<String, Object>> fileList = FileUtil.cmmnFileUpLoadAttach(request);
			for (HashMap<String, Object> fileInfo : fileList) {			
				logger.info("@ fileInfo  : " + fileInfo);			
			}
			
			return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG);
		} catch (Exception e) {
			e.printStackTrace();
			return CmmnUtil.resultMsg(Gv.FAIL, Gv.FAILMSG);
		}
	}
	
	@RequestMapping("/excelUpload.do")
	@ResponseBody
	public ResponseEntity<Object> excelUpload(HttpServletRequest request) {
		CmmnUtil.methodLog("엑셀업로드");
				
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
	        String[] columsList = {"GOODS_CD","REQ_QTY","SALE_DANGA"};
	        ExcelReadBox box    = ExcelUtil.readToList(excelFile, columsList , 4, 0);
	        logger.info("box  : " + box );
	        List<Map<String, Object>> excelList  = box.getList();
	        
	        logger.info("excelList  : " + excelList );
	        logger.info("excelList.size()  : " + excelList.size());
	        
	        // ------------------------------------------------------------------------
	        // 업로드한 파일자료 성공여부 체크 100
	        // ------------------------------------------------------------------------
	        if(!ExcelReadBox.RESULT_CODE_SUCCESS.equals(box.getResultCode())){	            
	            return new ResponseEntity<>(box.getResultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        if(excelList.size() > 1000) {
	            return new ResponseEntity<>("허용된 사이즈가 초과했습니다.(1000건미만)", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
			return new ResponseEntity<>(box.getResultMessage(), HttpStatus.OK);
		} catch (Exception e) {			
			logger.error("엑셀업로드 오류 : ", e);
			return new ResponseEntity<>(ExcelReadBox.RESULT_MESSAGE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/saveCODE01TB_2.do")
	@ResponseBody
	public ResponseEntity<Object> saveCODE01TB_2(@RequestBody List<HashMap<String, Object>> hMap, HttpServletRequest request) {
		logger.info("saveCODE01TB_2.do ==>{}", hMap);
		
		try {
			sampleService.saveCODE01TB_2(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}

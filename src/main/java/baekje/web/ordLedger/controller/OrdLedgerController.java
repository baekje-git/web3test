package baekje.web.ordLedger.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import baekje.web.ordLedger.service.OrdLedgerService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.ConstantProp;
@RequestMapping("/ordLedger")
@RestController
public class OrdLedgerController {
	private Logger log = LogManager.getLogger(this.getClass());
	@Autowired
	CmmnService cmmnService;
	
	@Autowired
	OrdLedgerService ordLedgerService;
	
	@Autowired
	CmmnService cmmnSvc;
	/**
	 * 매출원장 목록조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/listSearch")
	@ResponseBody
	public ResponseEntity<Object> selectOrdLedgerSearchList(@RequestParam HashMap<String, Object> hMap) throws Exception {

		HashMap<String, Object> rtnMap = new HashMap<>();

		// 마스터 판매금액 & 입금액 조회
		List<Object> masterData = cmmnService.selectList("ordLedger.selectOrdLedgerSearchMaster", hMap);

		// 마스터 현금잔고 & 보유어음 조회
		List<Object> masterCustData = cmmnService.selectList("ordLedger.selectOrdLedgerSearchMastercust", hMap);

		// 매출원장 목록 조회
		List<Object> list = cmmnService.selectList("ordLedger.selectOrdLedgerSearchList", hMap);

		rtnMap.put("masterData", masterData.get(0));
		rtnMap.put("masterCustData", masterCustData.get(0));
		rtnMap.put("list", list);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}

    /**
     * 
     * <strong>작성자 : </strong>KangMS<br>
     * <strong>작성일 : </strong>2017. 9. 15.오후 2:14:27<br>
     * 설명 : 첨부파일 다운로드 <br>
     *
     * @version 1.0
     * @author  KangMS
     * @since   2017. 9. 15.
     * @param    
     * @return  void 
     */
	@GetMapping("/acceptanceFile")
	@ResponseBody
    public String acceptFileDownLoadView(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
        HashMap<String, Object> paramMap = CmmnUtil.getParameters(req);
        String INVOICE_NO="";
        
        String DLV_BRCH_CD = String.valueOf(paramMap.get("DLV_BRCH_CD"));
        String APRV_DT = String.valueOf(paramMap.get("APRV_DT"));
        String APRV_NO = String.valueOf(paramMap.get("APRV_NO"));
        
        if(DLV_BRCH_CD.length()==1) {
        	DLV_BRCH_CD = "0"+DLV_BRCH_CD;
        }
        
        if(APRV_NO.length()==4) {
        	APRV_NO = "0"+APRV_NO;
        }
        
        INVOICE_NO = DLV_BRCH_CD+APRV_DT+APRV_NO;
        
        /* ---------- Parameters ---------- */ 
    	paramMap.put(Gv.DATASOURCE, Gv.SAL1);
    	paramMap.put(Gv.NAMESPACE , "ordLedger");
    	paramMap.put(Gv.QUERY_ID  , "selectAcceptanceFileList"); 
    	paramMap.put("INVOICE_NO" , INVOICE_NO);
    	
    	/* ---------- SelectList ---------- */ 
    	List<Object> dataList = cmmnSvc.selectList(paramMap);
    	String filePathNm="";
    	for(int i=0; i<dataList.size(); i++) {
    		HashMap<String, Object> rsltMap = (HashMap<String, Object>)dataList.get(i);
    		filePathNm = String.valueOf(rsltMap.get("FILE_DIRECTORY"));
    		System.out.println("------------acceptFileDownLoadView DIR:"+String.valueOf(dataList.get(i)));
    	}
        
        String filePath=ConstantProp.GV_scanImagePath;
        
        String fileName = ""; 
        String nameArry[] = filePathNm.split("\\.");
        
        if(nameArry.length>1) {
        
	        String pathNName = nameArry[0];
	        String[] pathArry = pathNName.split("\\/");
	        
	        for(int i=0; i<pathArry.length; i++) {
	        	
	        	if(i==pathArry.length-1) {
	        		fileName=pathArry[i];
	        	}else {
	        		filePath = filePath+pathArry[i]+"/";
	        	}
	        }
	        
	        long time = System.currentTimeMillis(); 
	        HashMap<String, Object> fileInfo = new HashMap<String, Object>();
	        
	        if(nameArry.length>1) {
	        	fileInfo.put("FILE_PATH", filePath); // 실제파일명
	        	fileInfo.put("FILE_NM", fileName+"."+nameArry[1]); // 실제파일명
	        	fileInfo.put("ORG_FILE_NM", fileName+"."+nameArry[1]); // 가공파일명
	        }else {
	        	fileInfo.put("FILE_PATH", filePath); // 실제파일명
	        	fileInfo.put("FILE_NM", fileName); // 실제파일명
	        	fileInfo.put("ORG_FILE_NM", fileName); // 가공파일명
	        }
	        System.out.println("------------fileInfo:"+fileInfo);
	        
	        model.addAttribute("downloadFile", fileInfo);
        }
    	
        return "fileDownloadView";
    }
	/**
	 * 매출원장 상세조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detailSearch")
	@ResponseBody
	public ResponseEntity<Object> selectOrdLedgerDetail(@RequestParam HashMap<String, Object> hMap) throws Exception {

		HashMap<String, Object> rtnMap = new HashMap<>();

		List<Object> data = cmmnService.selectList("ordLedger.itemSearchListDetail", hMap);
		rtnMap.put("custList", data);

		List<Object> dataMaster = cmmnService.selectList("ordLedger.itemSearchListDetailMaster", hMap);

		if (dataMaster.size() > 0) {
			rtnMap.put("detailMaster", dataMaster.get(0));
		}

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	/**
	 * 사인등록
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveSign")
	@ResponseBody
	public ResponseEntity<Object> insertOrdLedgerSignFile(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			ordLedgerService.insertOrdLedgerSignFile(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 * 사인 보기
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getSign")
	@ResponseBody
	public ResponseEntity<Object> selectOrdLedgerSignFileName(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		HashMap<String, Object> fileInfo = (HashMap<String, Object>) cmmnService.select("ordLedger.selectOrdLedgerSignFileName", hMap);

		fileInfo.put("FILE_EXT2", "png");
        fileInfo.put("ORG_FILE_NM", fileInfo.get("ORG_FILE_NM") + ".png");
        fileInfo.put("FILE_NM", fileInfo.get("FILE_NM") + ".png");
		
		return new ResponseEntity<>(fileInfo, HttpStatus.OK);

	}
	/**
	 * 사인 보기 (2024-04-30)
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
    @GetMapping("/showSign")
    public ResponseEntity<String> showImage(@RequestParam HashMap<String, Object> hMap) throws Exception{
    
    	HashMap<String, Object> fileInfo = (HashMap<String, Object>) cmmnService.select("ordLedger.selectOrdLedgerSignFileName", hMap);
		
        String fileOrgNM = fileInfo.get("ORG_FILE_NM") + ".png";
        String filePathNM = (String) fileInfo.get("FILE_PATH");
    	
        // 사진이 저장된 폴더 경로 변수 선언
        String imageRoot = filePathNM;
 
        // 서버 로컬 경로 + 파일 명 저장 실시
        imageRoot = imageRoot + fileOrgNM;
     
        File file = new File(imageRoot);

		ResponseEntity<String> result = null;

		try {
			byte[] fileContent = FileCopyUtils.copyToByteArray(file);
			String encodedString = Base64.getEncoder().encodeToString(fileContent);
			result = new ResponseEntity<>(encodedString, HttpStatus.OK);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
    }
	
    /**
	 * 더보기 담당자별 영업현황 일별 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/rptnSalesDeptSalesSchDayList")
	@ResponseBody
	public ResponseEntity<Object> rptnSalesDeptSalesSchDayList(@RequestParam HashMap<String, Object> hMap) throws Exception {

		// 더보기 담당자별 영업현황 일별 목록 조회
		List<Object> list = cmmnService.selectList("ordLedger.rptnSalesDeptSalesSchDayList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 더보기 담당자별 영업현황 월별 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/rptnSalesDeptSalesSchMonthList")
	@ResponseBody
	public ResponseEntity<Object> rptnSalesDeptSalesSchMonthList(@RequestParam HashMap<String, Object> hMap) throws Exception {

		// 더보기 담당자별 영업현황 월별 목록 조회
		List<Object> list = cmmnService.selectList("ordLedger.rptnSalesDeptSalesSchMonthList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 더보기 초당약품 매출현황 일반현황 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/selectChoSalesStateNorList")
	@ResponseBody
	public ResponseEntity<Object> selectChoSalesStateNorList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		// 더보기 초당약품 매출현황 일반현황 목록 조회
//		List<Object> list = cmmnService.selectList("ordLedger.selectChoSalesStateNorList", hMap);
		
		List<Object> list = new ArrayList<>();
		
		// TOTAL COUNT
		List<Object> dataCount =  cmmnService.selectList("ordLedger.selectChoSalesStateListCount", hMap);
		int totalCnt = 0;
		   
		if(dataCount!=null && dataCount.size()>0) {
			 HashMap hm = (HashMap)dataCount.get(0);
			 
			 totalCnt = Integer.parseInt(String.valueOf(hm.get("CNT")));
			 
			 if(totalCnt > 0) {
				// 더보기 초당약품 매출현황 목록 조회
				list = cmmnService.selectList("ordLedger.selectChoSalesStateNorList", hMap);
			 }
			 
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 더보기 초당약품 매출현황 거래처별 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/selectChoSalesStateCustList")
	@ResponseBody
	public ResponseEntity<Object> selectChoSalesStateCustList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		// 더보기 초당약품 매출현황 거래처별 목록 조회
//		List<Object> list = cmmnService.selectList("ordLedger.selectChoSalesStateCustList", hMap);
		
		List<Object> list = new ArrayList<>();
		
		// TOTAL COUNT
		List<Object> dataCount =  cmmnService.selectList("ordLedger.selectChoSalesStateListCount", hMap);
		int totalCnt = 0;
		   
		if(dataCount!=null && dataCount.size()>0) {
			 HashMap hm = (HashMap)dataCount.get(0);
			 
			 totalCnt = Integer.parseInt(String.valueOf(hm.get("CNT")));
			 
			 if(totalCnt > 0) {
				// 더보기 초당약품 매출현황 목록 조회
				list = cmmnService.selectList("ordLedger.selectChoSalesStateCustList", hMap);
			 }
			 
		}
		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 더보기 거래처별 영업현황  목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/rptnSalesCustomSalesNorList")
	@ResponseBody
	public ResponseEntity<Object> rptnSalesCustomSalesNorList(@RequestParam HashMap<String, Object> hMap) throws Exception {

		// 더보기 거래처별 영업현황  목록 조회
		List<Object> list = cmmnService.selectList("ordLedger.rptnSalesCustomSalesNorList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 더보기 일일거래내역서  목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/selectRptnSalesDailyCustomSales")
	@ResponseBody
	public ResponseEntity<Object> selectRptnSalesDailyCustomSales(@RequestParam HashMap<String, Object> hMap) throws Exception {

		// 더보기 일일거래내역서  목록 조회
		List<Object> list = cmmnService.selectList("ordLedger.selectRptnSalesDailyCustomSales", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 더보기 수금계획  목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchCollectMoneyList")
	@ResponseBody
	public ResponseEntity<Object> searchCollectMoneyList(@RequestParam HashMap<String, Object> hMap) throws Exception {

		// 더보기 일일거래내역서  목록 조회
		List<Object> list = cmmnService.selectList("ordLedger.searchCollectMoneyList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * MIS > 임원용 > 매출현황  목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/selectSalesBranchMobile")
	@ResponseBody
	public ResponseEntity<Object> selectSalesBranchMobile(@RequestParam HashMap<String, Object> hMap) throws Exception {
	    try {
	        List<Object> selectList = ordLedgerService.selectSalesBranchMobile(hMap); 
	        return new ResponseEntity<>(selectList, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	/**
	 * 더보기 제품수불내역 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/selectItemReceivePayList")
	@ResponseBody
	public ResponseEntity<Object> selectItemReceivePayList(@RequestParam HashMap<String, Object> hMap) throws Exception {

		// 더보기 제품수불내역 목록 조회
		List<Object> list = cmmnService.selectList("ordLedger.selectItemReceivePayList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 더보기 제품수불내역 상세 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/selectItemReceivePayDetailList")
	@ResponseBody
	public ResponseEntity<Object> selectItemReceivePayDetailList(@RequestParam HashMap<String, Object> hMap) throws Exception {

//		String itemCd = String.valueOf(hMap.get("itemCd"));
//		itemCd = itemCd.substring(1, itemCd.length());
//		hMap.put("itemCd", itemCd);
		
		// 더보기 제품수불내역 상세 목록 조회
		List<Object> list = cmmnService.selectList("ordLedger.selectItemReceivePayDetailList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	

	/**
	 * 장바구니 수량,단가 수정 (2024-05-31)
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/upatePrintOrder")
	@ResponseBody
	public ResponseEntity<Object> upatePrintOrder(@RequestBody HashMap<String, Object> hMap) throws Exception {
		cmmnService.update("ordLedger.upatePrintOrder", hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}
}

package baekje.web.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import baekje.web.main.service.MainService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.security.module.sha.SHACipher;

@CrossOrigin(origins = "http://localhost", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/main")
@RestController
public class MainController {
	@Autowired
	private CmmnService cmmnService;

	@Autowired
	private MainService mainService;

	/**
	 * 아이디 찾기
	 * @param hMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/findLoginId")
	@ResponseBody
	public ResponseEntity<Object> selectLoginIdFind(@RequestParam HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<Object> loginIdInfos = cmmnService.selectList("main.selectLoginIdFind", hMap);

			return new ResponseEntity<>(loginIdInfos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 비밀번호 찾기
	 * @param hMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/findLoginPw")
	@ResponseBody
	public ResponseEntity<Object> selectLoginPwFind(@RequestParam HashMap<String, Object> hMap) throws Exception {
		try {
			HashMap<String, Object> loginPwInfo = (HashMap<String, Object>) cmmnService.select("main.selectLoginPwFind", hMap);

			return new ResponseEntity<>(loginPwInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	/**
	 * 임시비밀번호 SMS전송
	 * @param hMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sendTempPw")
	@ResponseBody
	public ResponseEntity<Object> saveLoginPwFind(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			mainService.saveLoginPwFind(hMap);
			
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 신규상담신청
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/newQa")
	@ResponseBody
	public ResponseEntity<Object> saveNewQa(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
								    		 		
		cmmnService.insert("main.saveNewQa", hMap);
		
		Integer seqNo = (Integer) hMap.get("seqNo");
	    Map<String,Object> result = new HashMap<>();
	    result.put("seqNo", seqNo);
	    return new ResponseEntity<>(result, HttpStatus.OK);
		//return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);				
	}
	
	 /**
     * 신규상담신청 첨부파일 업로드
     * @param seqNo  /main/newQa 호출 후 리턴된 SEQ_NO
     * @param files  업로드된 이미지 파일 리스트
     */
    @PostMapping(value = "/newQaFiles",  consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<Object> uploadNewQaFiles(
            @RequestParam("seqNo") int seqNo,
            @RequestParam("files") List<MultipartFile> files
    ) throws Exception {
        for (MultipartFile f : files) {
            if (f.isEmpty()) {
                continue;
            }
            // 원본 파일명·확장자
            String orgName = f.getOriginalFilename();
            String ext     = FilenameUtils.getExtension(orgName);
            // 저장할 파일명 & 경로
            String saveName = UUID.randomUUID().toString() + "." + ext;
            String savePath = "/upload/board/" + saveName;
            // 실제 저장
            f.transferTo(new File(savePath));
            // DB에 메타데이터 저장
            HashMap<String, Object> fm = new HashMap<>();
            fm.put("seqNo",     seqNo);
            fm.put("fileNm",    saveName);
            fm.put("orgFileNm", orgName);
            fm.put("filePath",  savePath);
            fm.put("fileSize",  f.getSize());
            fm.put("fileExt",   ext);
            cmmnService.insert("main.insertBoardFile", fm);
        }
        return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
    }

	/**
	 * 품목명 목록 조회
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getItemNms")
	@ResponseBody
	public ResponseEntity<Object> selectItemNms() throws Exception {
		List<Object> list = cmmnService.selectList("main.selectItemNms", null);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 메인화면 로고이미지 validation체크
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/selectLoginUrl")
	@ResponseBody
	public ResponseEntity<Object> selectLoginUrl(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("login.selectLoginUrl", hMap);
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 메인화면 로고이미지 validation체크
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/selectLoginMainVar")
	@ResponseBody
	public ResponseEntity<Object> selectLoginMainVar(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("main.selectLoginMainVar", hMap);
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 메인화면 로고이미지 validation체크
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/selectLoginNoPwd")
	@ResponseBody
	public ResponseEntity<Object> selectLoginNoPwd(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("login.selectLoginNoPwd", hMap);
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	/**
	 * 거래처 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getCustList")
	@ResponseBody
	public ResponseEntity<Object> selectUserGb1020(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("main.selectUserGb1020", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 거래처 변경
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/editCustInfo")
	@ResponseBody
	public ResponseEntity<Object> selectChangeCust(@RequestParam HashMap<String, Object> hMap) throws Exception {
		// 선택한 거래처 정보 조회
		List<Object> list = cmmnService.selectList("main.selectChangeCust", hMap);
		HashMap<String,String> resMap = (HashMap<String,String>) list.get(0);
		
		String custCd = resMap.get("CUST_CD");
        String dlvBrchCd = resMap.get("DLV_BRCH_CD");
        String brchCd = resMap.get("BRCH_CD");
        String custNm = resMap.get("CUST_NM");
        String hpNo = resMap.get("HP_NO");
        String telNo = resMap.get("TEL_NO");
        String email = resMap.get("EMAIL");
        String ceoNm = resMap.get("CEO_NM");
        String empId = resMap.get("EMP_ID");
        String empNm = resMap.get("EMP_NM");
        String empHpNo = resMap.get("EMP_HP_NO");
        String custGbCd = resMap.get("CUST_GB_CD");
        String deptCd = resMap.get("DEPT_CD");
        String subdivBackYn = resMap.get("SUBDIV_BACK_YN");
        String accptYn = resMap.get("ACCPT_YN");
        String drugCd = resMap.get("DRUG_CD");
        String closeReadyYn = resMap.get("CLOSE_READY_YN");
        String ordMakerCd = resMap.get("ORD_MAKER_CD");
        String cashLimitBackYn = resMap.get("CASH_LIMIT_BACK_YN");
        String aniTreatYn = resMap.get("ANI_TREAT_YN");
        
          
        hMap.put("CUST_CD", custCd);
        hMap.put("DLV_BRCH_CD", dlvBrchCd);
        hMap.put("BRCH_CD", brchCd);
        hMap.put("CUST_NM", custNm);
        hMap.put("HP_NO", hpNo);
        hMap.put("TEL_NO", telNo);
        hMap.put("EMAIL", email);
        hMap.put("CEO_NM", ceoNm);
        hMap.put("EMP_ID", empId);
        hMap.put("EMP_NM", empNm);
        hMap.put("EMP_HP_NO", empHpNo);
        hMap.put("CUST_GB_CD", custGbCd);
        hMap.put("DEPT_CD", deptCd);
        hMap.put("SUBDIV_BACK_YN", subdivBackYn);
        hMap.put("ACCPT_YN", accptYn);
        hMap.put("DRUG_CD", drugCd);
        hMap.put("CLOSE_READY_YN", closeReadyYn);
        hMap.put("ORD_MAKER_CD", ordMakerCd);
        hMap.put("CASH_LIMIT_BACK_YN", cashLimitBackYn);
        hMap.put("ANI_TREAT_YN", aniTreatYn);
        
        // 선택한 거래처로 생성할 정보 등 조회
        List<Object> dataList1 = cmmnService.selectList("main.selectChangeCustInfo", hMap);
        
        HashMap<String, String> rtnMap = (HashMap<String, String>) dataList1.get(0);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 주문, 반품 장바구니 갯수 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketCount")
	@ResponseBody
	public ResponseEntity<Object> selectBasketCount(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("main.selectBasketCount", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 우측 광고배너 제품정보 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/bannerItemInfo")
	@ResponseBody
	public ResponseEntity<Object> selectBannerItemInfo(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("main.selectBannerItemInfo", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 우측 광고배너 제품 장바구니 담기
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addBasket")
	@ResponseBody
	public ResponseEntity<Object> insertBasket(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			cmmnService.update("comOrd.saveBasketItem", hMap);

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getMakerBrchList")
	@ResponseBody
	public ResponseEntity<Object> selectMakerBrchList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("main.selectMakerBrchList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 배송마감시간 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getDlvTime")
	@ResponseBody
	public ResponseEntity<Object> selectDlvTime(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("main.selectDlvTime", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 입고알림 조회(헤더 도트)
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getSoldOutYn")
	@ResponseBody
	public ResponseEntity<Object> selectSoldoutInYn(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("main.selectSoldoutInYn", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 알람여부 일괄 해제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/saveAllAlarmYn")
	@ResponseBody
	public ResponseEntity<Object> updateAllAlarmYn(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			cmmnService.select("main.updateAllAlarmYn", hMap);

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 비밀번호 수정
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/editPassword")
	@ResponseBody
	public ResponseEntity<Object> updatePassword(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			HashMap<String, Object> dataMap = new HashMap<>();
			
			dataMap.put("initLoginId", hMap.get("loginId").toString());
			dataMap.put("encNewPassword" , (new SHACipher()).encryptSHA256(hMap.get("pwd").toString()));
			
			cmmnService.update("main.updatePassword", dataMap);

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

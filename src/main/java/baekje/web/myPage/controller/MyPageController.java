package baekje.web.myPage.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import ever.support.security.module.sha.SHACipher;

import baekje.web.myPage.service.MyPageService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

@RequestMapping("/myPage")
@RestController
public class MyPageController {

	@Autowired
	CmmnService cmmnService;
	
	@Autowired
	MyPageService myPageService;
	
	/**
	 * 기본정보 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/custInfo")
	@ResponseBody
	public ResponseEntity<Object> selectCustInfoList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("myPage.selectCustInfoList", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}

	/**
	 * 최근주문내역 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/rcntlyOrd")
	@ResponseBody
	public ResponseEntity<Object> selectMypageOrdList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		List<Object> list = cmmnService.selectList("myPage.selectMypageOrdList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 마이페이지 공지사항 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/notice")
	@ResponseBody
	public ResponseEntity<Object> selectMypageNoticeList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		List<Object> rtnList = new ArrayList<>();
		
		List<Object> list = cmmnService.selectList("myPage.selectMypageNoticeList", hMap);
		
//		if (list.size() > 0) {
//			list.stream().flatMap(mapper)
//		}

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 마이페이지 팝업 공지사항 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/noticePopup")
	@ResponseBody
	public ResponseEntity<Object> selectMypagePopupNoticeList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		List<Object> list = cmmnService.selectList("myPage.selectMypagePopupNoticeList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 공지사항 상세 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/noticeDtlPopup")
	@ResponseBody
	public ResponseEntity<Object> selectNoticeDetail(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		HashMap<String, Object> dtl = (HashMap<String, Object>) cmmnService.select("myPage.selectNoticeDetail", hMap);
		if(dtl != null) {
			hMap.put("SEQ_NO", dtl.get("SEQ_NO"));
			List<Object> list = (List<Object>) cmmnService.selectList("cmmnFile.selectBoardFileList", hMap);
			rtnMap.put("list", list);
		}

		rtnMap.put("dtl", dtl);
		
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	@GetMapping("/noticeDtlPopup2")
	@ResponseBody
	public ResponseEntity<Object> selectNoticeDetail2(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		HashMap<String, Object> dtl = (HashMap<String, Object>) cmmnService.select("myPage.selectNoticeDetail2", hMap);
		if(dtl != null) {
			hMap.put("SEQ_NO", dtl.get("SEQ_NO"));
			List<Object> list = (List<Object>) cmmnService.selectList("cmmnFile.selectBoardFileList", hMap);
			rtnMap.put("list", list);
		}
		
		rtnMap.put("dtl", dtl);
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}

	/**
	 * 마이페이지 지점 공지사항 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/branchNotice")
	@ResponseBody
	public ResponseEntity<Object> selectMypageBrchNoticeList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		List<Object> list = cmmnService.selectList("myPage.selectMypageBrchNoticeList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	/**
	 * 마이페이지 팝업 지점 공지사항 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/branchNoticePopup")
	@ResponseBody
	public ResponseEntity<Object> selectMypagePopupBrchNoticeList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		List<Object> list = cmmnService.selectList("myPage.selectMypagePopupBrchNoticeList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 지점 공지사항 상세 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/branchNoticeDtlPopup")
	@ResponseBody
	public ResponseEntity<Object> selectBrNoticeDetail(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		HashMap<String, Object> dtl = (HashMap<String, Object>) cmmnService.select("myPage.selectBrNoticeDetail", hMap);
		if(dtl != null) {
			hMap.put("SEQ_NO", dtl.get("SEQ_NO"));
			List<Object> list = (List<Object>) cmmnService.selectList("cmmnFile.selectBoardFileList", hMap);
			rtnMap.put("list", list);
		}
		
		rtnMap.put("dtl", dtl);
		
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	@GetMapping("/branchNoticeDtlPopup2")
	@ResponseBody
	public ResponseEntity<Object> selectBrNoticeDetail2(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		HashMap<String, Object> dtl = (HashMap<String, Object>) cmmnService.select("myPage.selectBrNoticeDetail2", hMap);
		if(dtl != null) {
			hMap.put("SEQ_NO", dtl.get("SEQ_NO"));
			List<Object> list = (List<Object>) cmmnService.selectList("cmmnFile.selectBoardFileList", hMap);
			rtnMap.put("list", list);
		}
		
		rtnMap.put("dtl", dtl);
		
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	/**
	 * 마이페이지 My활동 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/myActivity")
	@ResponseBody
	public ResponseEntity<Object> selectMypageActivity(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("myPage.selectMypageActivity", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	/**
	 * 마이페이지 자주 묻는 질문 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/faq")
	@ResponseBody
	public ResponseEntity<Object> selectMypageFaq(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		List<Object> list = cmmnService.selectList("myPage.selectMypageFaq", hMap);
				

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 자주 묻는 질문 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/faqList")
	@ResponseBody
	public ResponseEntity<Object> selectFaqList(@RequestParam HashMap<String, Object> hMap) throws Exception {

		List<Object> list = cmmnService.selectList("myPage.selectFaqList", hMap);				

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 마이페이지 문의/답변 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/qna")
	@ResponseBody
	public ResponseEntity<Object> selectMypageQna(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		List<Object> list = cmmnService.selectList("myPage.selectMypageQna", hMap);
				

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 마이페이지 sms전송 전 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/popupSms")
	@ResponseBody
	public ResponseEntity<Object> selectMypageSmsBefore(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("myPage.selectMypageSmsBefore", hMap);
				

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	/**
	 * 마이페이지 sms전송
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sendSms")
	@ResponseBody
	public ResponseEntity<Object> insertSms(@RequestBody HashMap<String, Object> hMap) throws Exception {
		
		String userGbCd = hMap.get("userGbCd").toString();
        String custNm   = "[" +  hMap.get("custNm").toString() + "]";
        String contents = hMap.get("content").toString();

		/**
		1. USER_GB_CD 구분에 따른 분기.
		2. 해당 문구를 포함하여 80 byte 가 넘는지 여부 확인.
		3. 넘는다면 해당 문구를 포함하여 최대 2번까지 insert 문 실행.
		
		최대 입력 byte 는 160 byte .
		"[광고]무료수신거부 080-863-5559" 는 38 byte.
		 */

        byte [] strByte = contents.getBytes();

		int cntTotal         = 0;         // 처음 문자 총 byte 수
		int userGbIndex   = 0;                // user_gb_cd 에 따른 글자수
		int notHangulCnt = 0;           // 글자길이 중간 자르는 시점
		int hangulCnt      = 0;           // 한글 3byte 계산에 필요한 변수(ex: -59 , -41 , -67 )
		boolean splitYn   = false;
		
		if(userGbCd != null && (userGbCd.equals("10") || userGbCd.equals("20") )) {
			userGbIndex    = 38;      // 80 BYTE - 기본포함문자(38BYTE) = 40 .    40 / 3 = 13 총 13개의 한글 영문으로 계산시 최대 26.
		}else if(userGbCd != null && userGbCd.equals("30") ) {
			userGbIndex    = 80 - custNm.getBytes().length;       // 거래처명 byte 계산.
		}

		for( int i = 0; i < strByte.length; i++ ){
			if(!splitYn  && strByte[i] < 0 ) {
				++hangulCnt;                                          // 음수인 경우 ==  한글인 경우
				if(hangulCnt == 3) {                              // 3byte 체크.
					notHangulCnt = cntTotal ;
					hangulCnt      = 0;
				}
			}

			cntTotal++;

			if(cntTotal >= userGbIndex && splitYn == false) {
				splitYn   = true;
			}
		}
	
		String r_str = new String (strByte , 0,    notHangulCnt+1) ;       // 첫번째 문자 내용.
	
		if(cntTotal > userGbIndex) { // 2번에 나눠서 보내야 하는 경우
			String content1 = r_str;
			String content2 = new String(strByte, notHangulCnt+1 , strByte.length -(notHangulCnt+1));

			int cntTotal2         = 0;
			int notHangulCnt2 = 0;
			int hangulCnt2      = 0;
			boolean splitYn2   = false;
			byte [] strByte2     = content2.getBytes();

			for(int k = 0 ; k<strByte2.length ; k ++) {
				if(!splitYn2  && strByte2[k] < 0 ) {
					++hangulCnt2;
					if(hangulCnt2 == 3) {
						notHangulCnt2 = cntTotal2 ;
						hangulCnt2 = 0;
					}
				}
	
				cntTotal2++;
	
				if(cntTotal2 > userGbIndex && splitYn2 == false) {
					splitYn2 = true;
				}
			}
	
			if(cntTotal2 > userGbIndex) {
				content2 = new String (strByte2 , 0,    notHangulCnt2+1 ) ;
			}
	
			for(int j = 0 ; j < 2; j ++) {
				if(j == 0 ) {
					contents = content1;
				} else {
					contents = content2;
				}
	
				if(userGbCd != null && (userGbCd.equals("10") || userGbCd.equals("20") )) {
					contents = "[광고]"+contents+"무료수신거부 080-863-5559";
				}else if(userGbCd != null && userGbCd.equals("30") ) {
					contents = custNm +contents;
				}
	
				hMap.put("content", contents);
				myPageService.insertSms(hMap);
			}
		} else {
			if(userGbCd != null && (userGbCd.equals("10") || userGbCd.equals("20") )) {
				contents = "[광고]"+contents+"무료수신거부 080-863-5559";
			} else if (userGbCd != null && userGbCd.equals("30")) {
				contents = custNm +contents;
			}

			hMap.put("content", contents);
			myPageService.insertSms(hMap);
		}

		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);

	}
	
	/**
	 * 문의/답변 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/qnaList")
	@ResponseBody
	public ResponseEntity<Object> selectQnaList(@RequestBody HashMap<String, Object> hMap) throws Exception {

		List<Object> list = cmmnService.selectList("myPage.selectQnaList", hMap);				

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	/**
	 * 문의 등록
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/regQuestion")
	@ResponseBody
	public ResponseEntity<Object> insertQna(@RequestBody HashMap<String, Object> hMap) throws Exception {
		
		myPageService.insertQna(hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		
	}
	
	/**
	 * 답변 등록
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/regAnswer")
	@ResponseBody
	public ResponseEntity<Object> updateQna(@RequestBody HashMap<String, Object> hMap) throws Exception {
		
		myPageService.updateQna(hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		
	}
	
	/**
	 * 마이페이지 회원정보수정 정보 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/memberInfo")
	@ResponseBody
	public ResponseEntity<Object> selectMemberInfo(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		HashMap<String, Object> rtnMap = new HashMap<>();

		HashMap<String, Object> custData = (HashMap<String, Object>) cmmnService.select("myPage.selectMypageCustList", hMap);
		List<Object> list = cmmnService.selectList("myPage.selectMypageMemberList", hMap);
		
		rtnMap.put("custData", custData);
		rtnMap.put("list", list);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	/**
	 * 마이페이지 회원 사용여부 변경
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/editUseYn")
	@ResponseBody
	public ResponseEntity<Object> updateUserUseYn(@RequestBody HashMap<String, Object> hMap) throws Exception {
		
		myPageService.updateUserUseYn(hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		
	}
	
	/**
	 * 마이페이지 회원 사용여부 변경
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/editMemberInfo")
	@ResponseBody
	public ResponseEntity<Object> updateUserInfo(@RequestBody HashMap<String, Object> hMap) throws Exception {
		
		myPageService.updateUserInfo(hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		
	}
	
	/**
	 * 비밀번호 변경 전 체크
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/pwdChk")
	@ResponseBody
	public ResponseEntity<Object> selectPasswordCheck(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		SHACipher shaCipher = new SHACipher();
		
		String encPwd = shaCipher.encryptSHA256(hMap.get("oldPwd").toString());
		String encPwd1 = shaCipher.encryptSHA256(hMap.get("newPwd").toString());

		hMap.put("encPwd", encPwd);
		hMap.put("encPwd1", encPwd1);

		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("myPage.selectPasswordCheck", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	/**
	 * 비밀번호 변경
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/editPwd")
	@ResponseBody
	public ResponseEntity<Object> updateUserPwd(@RequestBody HashMap<String, Object> hMap) throws Exception {
		
		hMap.put("encNewPwd", (new SHACipher()).encryptSHA256(hMap.get("newPwd").toString()));
		
		myPageService.updateUserPw(hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		
	}
	
	/**
	 * 사용자 정보 추가
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addMemberInfo")
	@ResponseBody
	public ResponseEntity<Object> insertNewUserInfo(@RequestBody HashMap<String, Object> hMap) throws Exception {
		
		hMap.put("encNewPwd", (new SHACipher()).encryptSHA256(hMap.get("newPwd").toString()));
		
		myPageService.insertNewUserInfo(hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		
	}
	
	/**
	 * 로그인 ID 중복체크
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/chkIdDup")
	@ResponseBody
	public ResponseEntity<Object> selectIdDupCount(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		int dupCnt = (int) cmmnService.select("myPage.selectIdDupCount", hMap);
		
		HashMap<String, Object> rtnMap = new HashMap<>();
		
		rtnMap.put("dupCnt", dupCnt);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	/**
	 * 거래처 정보 수정
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/editCustInfo")
	@ResponseBody
	public ResponseEntity<Object> updateMypageCustInfo(@RequestBody HashMap<String, Object> hMap) throws Exception {
		
		myPageService.updateMypageCustInfo(hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		
	}
	
	/**
	 * 마이페이지 세금계산서 도트표시용 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/taxBillCount")
	@ResponseBody
	public ResponseEntity<Object> selectTaxBillCount(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("myPage.selectTaxBillCount", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}
	
	/**
	 * 마이페이지 세금계산서 도트표시용 조회 업데이트
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/taxBillCountUp")
	@ResponseBody
	public ResponseEntity<Object> updatetaxBillCountUp(@RequestBody HashMap<String, Object> hMap) throws Exception {
		
		cmmnService.update("myPage.selectTaxBillCountUp", hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		
	
		
	}
	
	/**
	 * 세금계산서 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/taxBill")
	@ResponseBody
	public ResponseEntity<Object> selectTaxBillList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		List<Object> rtnMap = cmmnService.selectList("myPage.selectMypageTaxbill", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	@PostMapping("/getPopupNotice")
	@ResponseBody
	public ResponseEntity<Object> selectPopupNoticeCnt(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		List<Object> list = cmmnService.selectList("myPage.selectNoticeDetail2", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	
	/**
	 * 첨부파일이미지 (2024-06-13)
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
    @GetMapping("/getImg")
    public ResponseEntity<String> getImg(@RequestParam HashMap<String, Object> hMap) throws Exception{
    
    	HashMap<String, Object> fileInfo = (HashMap<String, Object>) cmmnService.select("cmmnFile.selectBoardFileInfo", hMap);
		
        String fileNM = (String) fileInfo.get("FILE_NM");
        String filePathNM = (String) fileInfo.get("FILE_PATH");
     // 사진이 저장된 폴더 경로 변수 선언
        String imageRoot = filePathNM;
     // 서버 로컬 경로 + 파일 명 저장 실시
        imageRoot = imageRoot + fileNM;
      
        ResponseEntity<String> result = null;
        
        File file = new File(imageRoot);

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
	
    
    

}

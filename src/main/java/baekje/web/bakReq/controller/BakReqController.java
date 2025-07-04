package baekje.web.bakReq.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import baekje.web.bakReq.service.BakReqService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

@RequestMapping("/bakReq")
@RestController
public class BakReqController {

	@Autowired
	CmmnService cmmnService;

	@Autowired
	BakReqService bakReqService;

	/**
	 * 반품현황 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/listSearch")
	@ResponseBody
	public ResponseEntity<Object> selectBakReqList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		
		boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");

		if (isNumeric) {
			hMap.put("bohumCd", hMap.get("keyword"));
		} else {
			hMap.put("itemNm", "%" + hMap.get("keyword") + "%");
		}
		
		List<Object> list = this.cmmnService.selectList("bakReq.selectBakReqList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 반품메모 수정
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveBakMemo")
	@ResponseBody
	public ResponseEntity<Object> updateBakMemo(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			bakReqService.updateBakMemo(hMap);

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 반품현황 상세조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detailSearch")
	@ResponseBody
	public ResponseEntity<Object> selectBakReqDtlList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = this.cmmnService.selectList("bakReq.selectBakReqDtlList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 반품취소
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/cancelBakReq")
	@ResponseBody
	public ResponseEntity<Object> deleteBakReq(@RequestParam HashMap<String, Object> hMap) throws Exception {
		hMap.put("TYPE", "T_SALEBACKRCPT");
		
		cmmnService.callProcedure("bakReq.procBackReqDelete", hMap);
		
		String oVal = String.valueOf(hMap.get("O_VAL"));
		String oMsg = String.valueOf(hMap.get("O_MSG"));

     	if (oVal.equals("ERROR")) {
//     		throw new Exception(String.valueOf(oMsg) + " => O_VAL : [" + oVal + "]");
     		return new ResponseEntity<>(oMsg, HttpStatus.INTERNAL_SERVER_ERROR);
     	}
     	
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}

	/**
	 * 반품현황 상세 삭제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/deleteBakReqDtl")
	@ResponseBody
	public ResponseEntity<Object> deleteBakReqDtl(@RequestBody List<HashMap<String, Object>> hMaps) throws Exception {
		String oVal = "";
		String oMsg = "";
				
		try {
			for (HashMap<String, Object> hMap : hMaps) {
				hMap.put("TYPE", "T_SALEBACKRCPT_DTL");
				
				cmmnService.callProcedure("bakReq.procBackReqDelete", hMap);
				
				oVal = String.valueOf(hMap.get("O_VAL"));
				oMsg = String.valueOf(hMap.get("O_MSG"));
			}
			
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(oMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 레포트 호출용 파라미터 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getDlvInfo")
	@ResponseBody
	public ResponseEntity<Object> selectDlvInfo(@RequestBody HashMap<String, Object> hMaps) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("bakReq.selectDlvInfo", hMaps);
				
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 반품현황 체크
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/chk")
	@ResponseBody
	public ResponseEntity<Object> chk(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = this.cmmnService.selectList("bakReq.selectBakReqListChk", hMap);
		
		return new ResponseEntity<>(list.get(0), HttpStatus.OK);
	}

}

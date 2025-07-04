package baekje.web.bakRcpt.controller;

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

import baekje.web.bakRcpt.service.BakRcptService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

@RequestMapping("/bakRcpt")
@RestController
public class BakRcptController {

	@Autowired
	CmmnService cmmnService;

	@Autowired
	BakRcptService bakRcptService;

	/**
	 * 반품접수 품목조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/itemSearch")
	@ResponseBody
	public ResponseEntity<Object> getItems(@RequestParam HashMap<String, Object> hMap) throws Exception {
		boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");

		if (isNumeric) {
			hMap.put("bohumCd", hMap.get("keyword"));
		} else {
			hMap.put("itemNm", "%" + hMap.get("keyword") + "%");
		}

		List<Object> list = this.cmmnService.selectList("bakRcpt.selectItemSearchList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 제품 출고이력 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/history")
	@ResponseBody
	public ResponseEntity<Object> selectHistoryList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		hMap.put("expDt", hMap.get("expDt").toString().replaceAll("[^0-9]", ""));

		List<Object> list = cmmnService.selectList("bakRcpt.selectHistoryList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 반품 장바구니 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketList")
	@ResponseBody
	public ResponseEntity<Object> selectBakRcptBasketList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("bakRcpt.selectBakRcptBasketList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 반품 장바구니에 담기
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addBasket")
	@ResponseBody
	public ResponseEntity<Object> saveBakRcptBasketInput(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			cmmnService.update("bakRcpt.saveBakRcptBasketInput", hMap);

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	/**
	 * 장바구니 단건 삭제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/deleteBakRcptBasket")
	@ResponseBody
	public ResponseEntity<Object> deleteBakRcptBasket(@RequestParam HashMap<String, Object> hMap) throws Exception {
		try {
			bakRcptService.deleteBakRcptBasket(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 반품등록
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveBakRcptImsi")
	@ResponseBody
	public ResponseEntity<Object> saveBakRcptImsi(@RequestBody List<HashMap<String, Object>> hMap) throws Exception {
		try {
			bakRcptService.saveBakRcptImsi(hMap);

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	

	/**
	 * 반품등록
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveBakRcpt")
	@ResponseBody
	public ResponseEntity<Object> saveBakRcpt(@RequestBody List<HashMap<String, Object>> hMap) throws Exception {
		try {
			bakRcptService.saveBakRcpt(hMap);

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	
	/**
	 * 반품장바구니 유효기간,제조번호 (2024-06-21)
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/updateItemQty")
	@ResponseBody
	public ResponseEntity<Object> updateBasketItemQty(@RequestBody HashMap<String, Object> hMap) throws Exception {
		cmmnService.update("bakRcpt.updateBakRcptBasketItemQty", hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}
	
}

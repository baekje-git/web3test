package baekje.web.clctItem.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ever.cmmn.service.CmmnService;

@RequestMapping("/clctItm")
@RestController
public class ClctItmController {
	@Autowired
	CmmnService cmmnService;

	/**
	 * 회수품목 고시정보 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/ntfctInfo")
	@ResponseBody
	public ResponseEntity<Object> selectNtfctInfoList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = this.cmmnService.selectList("clctItm.selectNtfctInfoList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 회수품목 출하내역 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/shpmntItem")
	@ResponseBody
	public ResponseEntity<Object> selectShpmntItemList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = this.cmmnService.selectList("clctItm.selectShpmntItemList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 반품 장바구니 count 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/bakRcptBasketCnt")
	@ResponseBody
	public ResponseEntity<Object> selectBakRcptBasketCount(@RequestParam HashMap<String, Object> hMap) throws Exception {
		return new ResponseEntity<>(this.cmmnService.select("clctItm.selectBakRcptBasketCount", hMap), HttpStatus.OK);
	}
}

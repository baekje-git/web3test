package baekje.web.freOrd.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import baekje.web.freOrd.service.FreOrdService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;

@RequestMapping("/freOrd")
@RestController
public class FreOrdController {
	@Autowired
	CmmnService cmmnService;

	@Autowired
	FreOrdService freOrdService;

	/**
	 * 다빈도 품목조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/itemSearch")
	@ResponseBody
	public ResponseEntity<Object> selectFreOrdList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");

		if (isNumeric) {
			hMap.put("bohumCd", hMap.get("keyword"));
		} else {
			hMap.put("itemNm", "%" + hMap.get("keyword") + "%");
		}

		List<Object> list = this.cmmnService.selectList("freOrd.selectFreOrdList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 장바구니에 담기
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addBasket")
	@ResponseBody
	public ResponseEntity<Object> saveBasketItem(@RequestBody HashMap<String, Object> hMap) throws Exception {
		cmmnService.update("freOrd.saveBasketItem", hMap);

		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}

	/**
	 * 장바구니 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketList")
	@ResponseBody
	public ResponseEntity<Object> getBasketList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("freOrd.searchFreOrdBasketList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 장바구니 수량 수정
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/updateItemQty")
	@ResponseBody
	public ResponseEntity<Object> updateBasketItemQty(@RequestBody HashMap<String, Object> hMap) throws Exception {
		cmmnService.update("freOrd.updateBasketItemQty", hMap);

		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}

	/**
	 * 장바구니 단건 삭제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/deleteFreOrdBasket")
	@ResponseBody
	public ResponseEntity<Object> deleteFreOrdBasket(@RequestParam HashMap<String, Object> hMap) throws Exception {
		return freOrdService.deleteFreOrdBasket(hMap);
	}

	/**
	 * 주문등록
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/orderReg")
	@ResponseBody
	public ResponseEntity<Object> saveFreOrdWebgateList(@RequestBody List<HashMap<String, Object>> hMap) throws Exception {
		freOrdService.saveFreOrdWebgateList(hMap);

		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
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
		List<Object> list = cmmnService.selectList("freOrd.selectHistoryList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 누적 출하 수량 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/accShpmntQty")
	@ResponseBody
	public ResponseEntity<Object> selectAccShpmntQty(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("comOrd.selectAccShpmntQty", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}

	/**
	 * 제품정보 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/itemDetail")
	@ResponseBody
	public ResponseEntity<Object> selectItemDetailInfo(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("comOrd.selectItemDetailInfo", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
}

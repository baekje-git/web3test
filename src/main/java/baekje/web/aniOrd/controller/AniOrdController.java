package baekje.web.aniOrd.controller;

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

import baekje.web.aniOrd.service.AniOrdService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

@RequestMapping("/ani")
@RestController
public class AniOrdController {
	@Autowired
	CmmnService cmmnService;

	@Autowired
	AniOrdService aniOrdService;

	/**
	 * 일반주문 품목조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/itemSearch_ani")
	@ResponseBody
	public ResponseEntity<Object> getItems_ani(@RequestParam HashMap<String, Object> hMap) throws Exception {
		boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");

		if (isNumeric) {
			hMap.put("bohumCd", hMap.get("keyword"));
		} else {
			hMap.put("itemNm", "%" + hMap.get("keyword") + "%");
		}

		List<Object> list = this.cmmnService.selectList("ani.selectItemSearchList_ani", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 장바구니에 담기
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addBasket_ani")
	@ResponseBody
	public ResponseEntity<Object> saveBasketItem_ani(@RequestBody HashMap<String, Object> hMap) throws Exception {
		cmmnService.update("ani.saveBasketItem_ani", hMap);

		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}

	/**
	 * 장바구니 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketList_ani")
	@ResponseBody
	public ResponseEntity<Object> getBasketList_ani(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("ani.searchComOrdBasketList_ani", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketList_ani2")
	@ResponseBody
	public ResponseEntity<Object> basketList_ani2(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("ani.searchComOrdBasketList_ani2", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 장바구니 수량,단가 수정 (2024-05-31)
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/updateItemQty_ani")
	@ResponseBody
	public ResponseEntity<Object> updateBasketItemQty_ani(@RequestBody HashMap<String, Object> hMap) throws Exception {
		cmmnService.update("ani.updateBasketItemQty_ani", hMap);
		cmmnService.update("ani.updateBasketItemWp2Amt_ani", hMap);
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}

	/**
	 * 장바구니 단건 삭제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/deleteComOrdBasket_ani")
	@ResponseBody
	public ResponseEntity<Object> deleteComOrdBasket_ani(@RequestParam HashMap<String, Object> hMap) throws Exception {
		try {
			aniOrdService.deleteComOrdBasket_ani(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 주문등록
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/orderReg_ani")
	@ResponseBody
	public ResponseEntity<Object> saveComOrdWebgateList_ani(@RequestBody List<HashMap<String, Object>> hMap) throws Exception {
		try {
			aniOrdService.saveComOrdWebgateList_ani(hMap);

			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	/**
	 * 제품 출고이력 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/history_ani")
	@ResponseBody
	public ResponseEntity<Object> selectHistoryList_ani(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("ani.selectHistoryList_ani", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 누적 출하 수량 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/accShpmntQty_ani")
	@ResponseBody
	public ResponseEntity<Object> selectAccShpmntQty_ani(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("ani.selectAccShpmntQty_ani", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}

	/**
	 * 제품정보 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/itemDetail_ani")
	@ResponseBody
	public ResponseEntity<Object> selectItemDetailInfo_ani(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("ani.selectItemDetailInfo_ani", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 제품정보 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getAvailStock_ani")
	@ResponseBody
	public ResponseEntity<Object> selectAvailStock_ani(@RequestBody HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("ani.selectAvailStock_ani", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 중복 체크(광역)
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getBasketDupOrd_ani")
	@ResponseBody
	public ResponseEntity<Object> selectBasketDupOrd_ani(@RequestBody HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("ani.selectBasketDupOrd_ani", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 중복 체크
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getBasketDupOrd2_ani")
	@ResponseBody
	public ResponseEntity<Object> selectBasketDupOrd2_ani(@RequestBody HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("ani.selectBasketDupOrd2_ani", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
}

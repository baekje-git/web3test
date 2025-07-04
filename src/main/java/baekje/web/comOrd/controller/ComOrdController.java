package baekje.web.comOrd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import baekje.web.comOrd.service.ComOrdService;
import baekje.web.exception.controller.OrderException;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.exception.AjaxMsgCustomException;

@RequestMapping("/ord")
@RestController
public class ComOrdController {
	@Autowired
	CmmnService cmmnService;

	@Autowired
	ComOrdService comOrdService;

	/**
	 * 일반주문 품목조회
	 * @param request
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

		List<Object> list = this.cmmnService.selectList("comOrd.selectItemSearchList", hMap);

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
	    // Service로 위임
		comOrdService.saveBasketItem(hMap);
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
		List<Object> list = cmmnService.selectList("comOrd.searchComOrdBasketList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketList2")
	@ResponseBody
	public ResponseEntity<Object> basketList2(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("comOrd.searchComOrdBasketList2", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketList3")
	@ResponseBody
	public ResponseEntity<Object> basketList3(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("comOrd.searchComOrdBasketList3", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketList4")
	@ResponseBody
	public ResponseEntity<Object> basketList4(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("comOrd.searchComOrdBasketList4", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketList5")
	@ResponseBody
	public ResponseEntity<Object> basketList5(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("comOrd.searchComOrdBasketList5", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/basketList6")
	@ResponseBody
	public ResponseEntity<Object> basketList6(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("comOrd.searchComOrdBasketList6", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 장바구니 수량,단가 수정 (2024-05-31)
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/updateItemQty")
	@ResponseBody
    public ResponseEntity<Object> updateBasketItemQty(@RequestBody HashMap<String, Object> hMap) throws Exception {
        HashMap<String, Object> result = (HashMap<String, Object>) cmmnService.select("comOrd.selectBasketValChk", hMap);
        
        // result에서 CHK_MSG 값 가져오기
        String checkMessage = (String) result.get("CHK_MSG");  // 여기서 'CHK_MSG' 부분이 실제 키 이름인지 확인 필요!
        
        if (checkMessage == null || "".equals(checkMessage)) {
            cmmnService.update("comOrd.updateBasketItemQty", hMap);
            return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
        } else {
            // 실패하는 경우, CHK_MSG를 그대로 프론트엔드로 전달
            return new ResponseEntity<>(checkMessage, HttpStatus.BAD_REQUEST);
        }
    }

	/**
	 * 장바구니 단건 삭제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/deleteComOrdBasket")
	@ResponseBody
	public ResponseEntity<Object> deleteComOrdBasket(@RequestParam HashMap<String, Object> hMap) throws Exception {
		try {
			comOrdService.deleteComOrdBasket(hMap);
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
	@PostMapping("/orderReg")
	@ResponseBody
	public ResponseEntity<Object> saveComOrdWebgateList(@RequestBody List<HashMap<String, Object>> hMap) {
	    try {
	        comOrdService.saveComOrdWebgateList(hMap);
	        return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	    } catch (OrderException e) {
	    	System.out.println("🔥🔥🔥🔥🔥 OrderException caught: " + e.getMessage());
	        Map<String, Object> error = new HashMap<>();
	        error.put("result", "ERROR");
	        error.put("message", e.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        Map<String, Object> error = new HashMap<>();
	        error.put("result", "ERROR");
	        error.put("message", e.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
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
		List<Object> list = cmmnService.selectList("comOrd.selectHistoryList", hMap);

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
	
	/**
	 * 제품정보 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getAvailStock")
	@ResponseBody
	public ResponseEntity<Object> selectAvailStock(@RequestBody HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("comOrd.selectAvailStock", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 중복 체크(광역)
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getBasketDupOrd")
	@ResponseBody
	public ResponseEntity<Object> selectBasketDupOrd(@RequestBody HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("comOrd.selectBasketDupOrd", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 장바구니 중복 체크
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getBasketDupOrd2")
	@ResponseBody
	public ResponseEntity<Object> selectBasketDupOrd2(@RequestBody HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("comOrd.selectBasketDupOrd2", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
}

package baekje.web.recently.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;

@RequestMapping("/recently")
@RestController
public class RecentlyController {
	@Autowired
	CmmnService cmmnService;

	/**
	 * 최근주문내역 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchItem")
	@ResponseBody
	public ResponseEntity<Object> selectRecentlyList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = this.cmmnService.selectList("recently.selectRecentlyList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 최근주문내역 상세 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchDtls")
	@ResponseBody
	public ResponseEntity<Object> selectRecentlyDtlList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = this.cmmnService.selectList("recently.selectRecentlyDtlList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 재주문 장바구니에 담기
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addBasket")
	@ResponseBody
	public ResponseEntity<Object> saveBasketItem(@RequestBody List<HashMap<String, Object>> hMaps) throws Exception {
		for (HashMap<String, Object> hMap : hMaps) {
			cmmnService.update("comOrd.saveBasketItem", hMap);
		}
		
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}
	
	/**
	 * 재주문 장바구니에 담기 2024-06-07
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addBasket2")
	@ResponseBody
	public ResponseEntity<Object> saveBasketItem2(@RequestBody HashMap<String, Object> hMap) throws Exception {
		cmmnService.update("comOrd.saveBasketItem", hMap);
		
		return new ResponseEntity<>(1, HttpStatus.OK);
	}
	
	/**
	 * 제품정보 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/itemDtl")
	@ResponseBody
	public ResponseEntity<Object> selectItemDtlInfo(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("recently.selectItemDtlInfo", hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 주문취소
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/cancelRcently")
	@ResponseBody
	public ResponseEntity<Object> deleteRcently(@RequestParam HashMap<String, Object> hMap) throws Exception {
		hMap.put("TYPE", "WEB_D");
		
		cmmnService.callProcedure("recently.procSaleDelete", hMap);
		
		String oVal = String.valueOf(hMap.get("O_VAL"));
		String oMsg = String.valueOf(hMap.get("O_MSG"));

     	if (oVal.equals("ERROR")) {
//     		throw new Exception(String.valueOf(oMsg) + " => O_VAL : [" + oVal + "]");
     		return new ResponseEntity<>(oMsg, HttpStatus.INTERNAL_SERVER_ERROR);
     	}
     	
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}
	
	/**
	 * 개별주문취소
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/cancelSelRcently")
	@ResponseBody
	public ResponseEntity<Object> deleteSelRcently(@RequestParam HashMap<String, Object> hMap) throws Exception {
		hMap.put("I_TYPE", "WEB_D_SNO");
		
		cmmnService.callProcedure("recently.procSaleDeleteRe", hMap);
		String oVal = String.valueOf(hMap.get("O_VAL"));
		String oMsg = String.valueOf(hMap.get("O_MSG"));

     	if (oVal.equals("ERROR")) {
//     		throw new Exception(String.valueOf(oMsg) + " => O_VAL : [" + oVal + "]");
     		return new ResponseEntity<>(oMsg, HttpStatus.INTERNAL_SERVER_ERROR);
     	}
     	
		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}
}

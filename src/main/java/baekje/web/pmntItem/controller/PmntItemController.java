package baekje.web.pmntItem.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import baekje.web.bakReq.service.BakReqService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

@RequestMapping("/pmntItem")
@RestController
public class PmntItemController {

	@Autowired
	CmmnService cmmnService;

	/**
	 * 품목수불 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/listSearch")
	@ResponseBody
	public ResponseEntity<Object> selectBakReqList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = new HashMap<>();
		
		boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");

		if (isNumeric) {
			hMap.put("bohumCd", hMap.get("keyword"));
		} else {
			hMap.put("itemNm", hMap.get("keyword"));
		}

		rtnMap.put("count", cmmnService.select("pmntItem.selectPmntItemCount", hMap));
		rtnMap.put("list", cmmnService.selectList("pmntItem.selectPmntItemList", hMap));

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}

	/**
	 * 품목수불 상세목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/pmntItemDtl")
	@ResponseBody
	public ResponseEntity<Object> selectPmntItemDtlListPop(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = cmmnService.selectList("pmntItem.selectPmntItemDtlListPop", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}

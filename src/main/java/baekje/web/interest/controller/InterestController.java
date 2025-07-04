package baekje.web.interest.controller;

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

import baekje.web.interest.service.InterestService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

@RequestMapping("/interest")
@RestController
public class InterestController {
	@Autowired
	CmmnService cmmnService;
	
	@Autowired
	InterestService interestService;

	/**
	 * 관심상품 그룹 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchItem")
	@ResponseBody
	public ResponseEntity<Object> selectRecentlyList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = this.cmmnService.selectList("interest.selectRecentlyList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 관심상품 추가
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addInterest")
	@ResponseBody
	public ResponseEntity<Object> insertInterestItem(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			interestService.insertInterestItem(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 관심상품 삭제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/removeInterest")
	@ResponseBody
	public ResponseEntity<Object> deleteInterestItem(@RequestParam HashMap<String, Object> hMap) throws Exception {
		try {
			interestService.deleteInterestItem(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 관심상품 그룹 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/interestGroupList")
	@ResponseBody
	public ResponseEntity<Object> selectInterestGroupList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = new HashMap<>();
		
		List<Object> fixedList = cmmnService.selectList("interest.selectFixedInterestGroupList", hMap);
		List<Object> list = cmmnService.selectList("interest.selectInterestGroupList", hMap);
		
		rtnMap.put("fixedList", fixedList);
		rtnMap.put("list", list);
		
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}
	
	/**
	 * 관심상품 그룹별 상품 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/groupItemList")
	@ResponseBody
	public ResponseEntity<Object> selectGroupItemList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		if (!"".equals(hMap.get("keyword").toString())) {
			boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");

			if (isNumeric) {
				hMap.put("bohumCd", hMap.get("keyword"));
			} else {
				hMap.put("itemNm", "%" + hMap.get("keyword") + "%");
			}
		}
		
		List<Object> list = cmmnService.selectList("interest.selectGroupItemList", hMap);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * 관심상품 알림여부 수정
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/editAlarmYn")
	@ResponseBody
	public ResponseEntity<Object> updateAlarmYn(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			interestService.updateAlarmYn(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 관심상품 다건 삭제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/removeMyItems")
	@ResponseBody
	public ResponseEntity<Object> deleteMyItems(@RequestParam HashMap<String, Object> hMap) throws Exception {
		try {
			interestService.deleteMyItems(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 그룹 추가
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addGroup")
	@ResponseBody
	public ResponseEntity<Object> insertGroup(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			interestService.insertGroup(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 그룹 삭제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/removeGroups")
	@ResponseBody
	public ResponseEntity<Object> deleteGroups(@RequestBody List<HashMap<String, Object>> hMap) throws Exception {
		try {
			interestService.deleteGroups(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 관심상품 정렬순서 수정
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/saveSortNo")
	@ResponseBody
	public ResponseEntity<Object> updateSortNo(@RequestBody List<HashMap<String, Object>> hMap) throws Exception {
		try {
			interestService.updateSortNo(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 관심상품 정렬순서 수정
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/moveGroup")
	@ResponseBody
	public ResponseEntity<Object> updateGroupNo(@RequestBody List<HashMap<String, Object>> hMap) throws Exception {
		try {
			interestService.updateGroupNo(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 관심상품 월평균사용량 수정
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/saveMonthQty")
	@ResponseBody
	public ResponseEntity<Object> updateMonthQty(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			interestService.updateMonthQty(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 관심상품 입고알림 일괄 해제
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/saveAllAlarmYn")
	@ResponseBody
	public ResponseEntity<Object> updateAllAlarmYn(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			interestService.updateAllAlarmYn(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 관심상품 그룹명 수정
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/editGroupNm")
	@ResponseBody
	public ResponseEntity<Object> updateGroupNm(@RequestBody HashMap<String, Object> hMap) throws Exception {
		try {
			interestService.updateGroupNm(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
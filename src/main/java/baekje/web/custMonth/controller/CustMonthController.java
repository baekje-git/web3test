package baekje.web.custMonth.controller;

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

@RequestMapping("/custMonth")
@RestController
public class CustMonthController {

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
		List<Object> list = cmmnService.selectList("custMonth.selectCustCreditMonth", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}

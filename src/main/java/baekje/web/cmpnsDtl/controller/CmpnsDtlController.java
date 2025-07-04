package baekje.web.cmpnsDtl.controller;

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

@RequestMapping("/cmpnsDtl")
@RestController
public class CmpnsDtlController {
	@Autowired
	CmmnService cmmnService;

	/**
	 * 약가인하정보 > 대상품목 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchItem")
	@ResponseBody
	public ResponseEntity<Object> selectCmpnsDtlList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");

		if (isNumeric) {
			hMap.put("bohumCd", hMap.get("keyword"));
		} else {
			hMap.put("itemNm", "%" + hMap.get("keyword") + "%");
		}

		List<Object> list = this.cmmnService.selectList("cmpnsDtl.selectCmpnsDtlList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}

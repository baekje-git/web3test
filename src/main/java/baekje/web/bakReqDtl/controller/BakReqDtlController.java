package baekje.web.bakReqDtl.controller;

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

@RequestMapping("/bakReqDtl")
@RestController
public class BakReqDtlController {

	@Autowired
	CmmnService cmmnService;

	@Autowired
	BakReqService bakReqService;

	/**
	 * 반품상세 목록 조회
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/listSearch")
	@ResponseBody
	public ResponseEntity<Object> selectBakReqList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = this.cmmnService.selectList("bakReqDtl.selectBakReqDtlList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}

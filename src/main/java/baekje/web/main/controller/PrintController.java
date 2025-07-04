/**
 * Entity.java V1.0 2017. 06. 08.
 *
 * Copyright (c) 2016 UBIStorm Co. All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of UBIStorm Co.  You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement
 * you entered into with UBIStorm Co.
 **/

package baekje.web.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ever.cmmn.service.CmmnService;
import ever.support.excel.JxlsUtil;

/**
 * SAMPLE
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>khlee<br>
 * <strong>작성일 : </strong>2017. 12. 09. <br>
 * @author       khlee
 * @package      baekje.main.cmd
 * @file         SampleCmd.java
 * @version      1.0
 * @since        2017. 12. 09.
 */
@Controller
@RequestMapping("/print")
public class PrintController {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private CmmnService cmmnService;


	/**
	 * 매출원장 출력
	 * @param hMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ordLedger")
    public ModelAndView ordLedger(
    		@RequestParam HashMap<String, Object> hMap , HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("/print/popupPrintOrdLedger");

    	// 마스터 판매금액 & 입금액 조회
		List<Object> masterData = cmmnService.selectList("ordLedger.selectOrdLedgerSearchMaster", hMap);

		// 마스터 현금잔고 & 보유어음 조회
		List<Object> masterCustData = cmmnService.selectList("ordLedger.selectOrdLedgerSearchMastercust", hMap);

		// 매출원장 목록 조회
		List<Object> list = cmmnService.selectList("ordLedger.selectOrdLedgerSearchList", hMap);
		
		mv.addObject("masterData", masterData.get(0));
		mv.addObject("masterCustData", masterCustData.get(0));
		mv.addObject("list", list);
		mv.addObject("hMap", hMap);

		return mv;
	}
	
	/**
	 * 품목수불 출력
	 * @param hMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pmntItem")
    public ModelAndView pmntItem(
    		@RequestParam HashMap<String, Object> hMap , HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("/print/popupPrintPmntItem");

		boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");

		if (isNumeric) {
			hMap.put("bohumCd", hMap.get("keyword"));
		} else {
			hMap.put("itemNm", hMap.get("keyword"));
		}
		
		mv.addObject("count", cmmnService.select("pmntItem.selectPmntItemCount", hMap));
		mv.addObject("list", cmmnService.selectList("pmntItem.selectPmntItemList", hMap));
		mv.addObject("hMap", hMap);

		return mv;
	}
	
	/**
	 * 반품현황 출력
	 * @param hMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bakReq")
    public ModelAndView bakReq(
    		@RequestParam HashMap<String, Object> hMap , HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mv = new ModelAndView("/print/popupPrintBakReq");

    	mv.addObject("list", cmmnService.selectList("bakRcpt.selectPrintBakRcptList", hMap));
    	mv.addObject("hMap", hMap);

    	return mv;
    }
	
	/**
	 * 바코드 출력
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/barcode")
    public ModelAndView BarcodeView(
    		@RequestParam HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("barcodeView");
		mv.addObject("data", hMap);
        
        return mv;
    }
}

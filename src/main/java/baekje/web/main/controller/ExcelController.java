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
@RequestMapping("/excel")
public class ExcelController {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private CmmnService cmmnService;


	@RequestMapping(value = "/{template}")
    public void CommonAct(@PathVariable String template,
    		@RequestParam HashMap<String, Object> hMap , HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	try {
    		//매출원장
    		if(template.equals("ordLedger")) {
                List<Object> dataSheet = cmmnService.selectList("ordLedger.selectOrdLedgerSearchList", hMap);
                rtnMap.put("dataList", dataSheet);
            //반품현황, 반품상세
            }else if(template.equals("bakReq") || template.equals("bakReqDetail") ) {
               List<Object> dataSheet = cmmnService.selectList("bakReq.selectBakReqExcelList", hMap);
               rtnMap.put("dataList", dataSheet);
            //품목수불
            }else if(template.equals("pmntItem")) {
            	
            	boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");
            	
        		if (isNumeric) {
        			hMap.put("bohumCd", hMap.get("keyword"));
        		} else {
        			hMap.put("itemNm", hMap.get("keyword"));
        		}
            	
               List<Object> dataSheet = cmmnService.selectList("pmntItem.selectPmntItemList", hMap);
               rtnMap.put("dataList", dataSheet);
            //회수품목 출하내역
            }else if(template.equals("clctItm")) {
            	
//            	boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");
//                
//        		if (isNumeric) {
//        			hMap.put("bohumCd", hMap.get("keyword"));
//        		} else {
//        			hMap.put("itemNm", hMap.get("keyword"));
//        		}
            	
        		
        		List<Object> dataSheet = cmmnService.selectList("clctItm.selectShpmntItemList", hMap);        		
        		rtnMap.put("dataList", dataSheet);
            //고시정보
            }else if(template.equals("ntfctnInf")) {
            	boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");
                
        		if (isNumeric) {
        			hMap.put("bohumCd", hMap.get("keyword"));
        		} else {
        			hMap.put("itemNm", hMap.get("keyword"));
        		}
                List<Object> dataSheet = cmmnService.selectList("ntfctInfo.selectNtfctInfoList", hMap);
                rtnMap.put("dataList", dataSheet);
            //대상품목
            }else if(template.equals("trgtItm")) {
            	
            	boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");
                
        		if (isNumeric) {
        			hMap.put("bohumCd", hMap.get("keyword"));
        		} else {
        			hMap.put("itemNm", hMap.get("keyword"));
        		}
            	
                List<Object> dataSheet = cmmnService.selectList("trgtItem.selectTrgtItemList", hMap);
                rtnMap.put("dataList", dataSheet);
    		//보상내역
	        }else if(template.equals("cmpnsDtl")) {
	            List<Object> dataSheet = cmmnService.selectList("cmpnsDtl.selectCmpnsDtlList", hMap);
	            rtnMap.put("dataList", dataSheet);
	        // 다빈도
	        } else if (template.equals("freOrd")) {
	        	List<Object> dataSheet = cmmnService.selectList("freOrd.selectFreOrdList", hMap);
	            rtnMap.put("dataList", dataSheet);
	         // 제약회사 매출자료
	        }else if(template.equals("makerSale")) {	
	        	List<Object> dataSheet = cmmnService.selectList("main.selectMakerSaleList", hMap);
	            rtnMap.put("dataList", dataSheet);
	         // 제약회사 재고자료    	
	    	}else if(template.equals("makerStock")) {	
	    		List<Object> dataSheet = cmmnService.selectList("main.selectMakerStockList", hMap);
	            rtnMap.put("dataList", dataSheet);
	    	}else if(template.equals("taxBill")) {	
	    		List<Object> dataSheet = cmmnService.selectList("myPage.selectMypageTaxbill", hMap);
	            rtnMap.put("dataList", dataSheet);
	    	}else if(template.equals("interest")) {
	    		
	    		boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");
                
        		if (isNumeric) {
        			hMap.put("bohumCd", hMap.get("keyword"));
        		} else {
        			hMap.put("itemNm", hMap.get("keyword"));
        		}
	    		
//	    		if (!"".equals(hMap.get("keyword").toString())) {
//	    			boolean isNumeric = hMap.get("keyword").toString().matches("[+-]?\\d*(\\.\\d+)?");
//
//	    			if (isNumeric) {
//	    				hMap.put("bohumCd", hMap.get("keyword"));
//	    			} else {
//	    				hMap.put("itemNm", "%" + hMap.get("keyword") + "%");
//	    			}
//	    		}
	    		
	    		List<Object> dataSheet = cmmnService.selectList("interest.selectGroupItemList", hMap);
	    		System.out.println("dataSheet: "+dataSheet.toString());
	            rtnMap.put("dataList", dataSheet);
	    	}


	        JxlsUtil jx = new JxlsUtil();
	        jx.download(request, response, rtnMap, template +".xls");

    	}catch(Exception e) {
    		log.debug("엑셀다운로드 오류 : "+e.toString());
    	}
    }
}

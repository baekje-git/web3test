/**
 * MainSvcImpl.java V1.0 2017. 6. 12.
 *
 * Copyright (c) 2017 UBIStorm Co. All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of UBIStorm Co.  You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement
 * you entered into with UBIStorm Co.
 **/

package baekje.web.api.svr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import ever.cmmn.dao.CmmnDao;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

import baekje.web.api.dao.ApiMediPlanDao;

/**
 *
 * Copyright Copyright (c) 2017 Company UBIStorm Co. <br>
 * <br>
 * <strong>작성자 : </strong>abj1<br>
 * <strong>작성일 : </strong>2017. 9. 28.오후 7:06:19<br>
 *
 * @author abj1
 * @package baekje.web.comOrd.svr
 * @file ComOrdSvcImpl.java
 * @version 1.0
 * @since 2017. 9. 28.
 */
@Service
public class ApiMediPlanSvcImpl implements ApiMediPlanSvc {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	ApiMediPlanDao apiMediPlanDao;

	@Autowired
	CmmnDao cmmnDao;

	@SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
    public int insertApiHistory(HashMap<String, Object> paramMap) throws Exception {
		paramMap.put(Gv.QUERY_ID  , "apiMediPlan.insertApiHistory");
        return cmmnDao.insert(paramMap);
    }
	
    @Override
    public String checkAuth(HashMap<String, Object> paramMap) throws Exception {
		paramMap.put(Gv.QUERY_ID  , "apiMediPlan.checkAuth");
		return (String)cmmnDao.select(paramMap);
    }

    @Override
    public String checkCust(HashMap<String, Object> paramMap) throws Exception {
		paramMap.put(Gv.QUERY_ID  , "apiMediPlan.checkCust");
		return (String)cmmnDao.select(paramMap);
    }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW , isolation=Isolation.READ_COMMITTED)
    public HashMap<String, Object> putCust(HashMap<String, Object> paramMap) throws Exception {
		
		int putCount = 0;
		String putGubun;
		HashMap<String, Object> hMapResult = new HashMap<>();

		//신규거래처
		if (paramMap.get("CUST_CODE") == "") {
			putGubun = "I";
			//신규거래처코드
			paramMap.put(Gv.QUERY_ID  , "apiMediPlan.selectNewCustCd");
			paramMap.put("CUST_CODE",(String)cmmnDao.select(paramMap));
			log.debug("apiMediPlan newCustCode:"+paramMap.get("CUST_CODE"));

			//거래처 등록
			paramMap.put(Gv.QUERY_ID  , "apiMediPlan.insertCust");
			putCount = cmmnDao.insert(paramMap);

			//거래처 저장 후처리 프로시저 호출			
			paramMap.put("I_TYPE"    , "I");
			cmmnDao.callProcedure("apiMediPlan.callPCustAft",paramMap);

			if(!paramMap.get("O_VAL").equals("SUCCESS")) {
	    		int len = paramMap.get("O_VAL").toString().length() > 200 ? 200 : paramMap.get("O_VAL").toString().length();
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", paramMap.get("O_MSG").toString().substring(0,len));
				log.error("callProc callPSaleMallOrd ERROR:"+paramMap.get("O_MSG").toString().substring(0,len));
	        }

		}
		//기존거래처
		else {
			putGubun = "U";
			//거래처 등록
			paramMap.put(Gv.QUERY_ID  , "apiMediPlan.updateCust");
			putCount = cmmnDao.update(paramMap);
			
			if (putCount == 0) {
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", "존재하지 않는 거래처코드입니다");
				return hMapResult;
			}

			//거래처 저장 후처리 프로시저 호출			
			paramMap.put("I_TYPE"    , "U");
			cmmnDao.callProcedure("apiMediPlan.callPCustAft", paramMap); 

			if(!paramMap.get("O_VAL").equals("SUCCESS")) {
	 			hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", paramMap.get("O_MSG").toString().substring(0,200));
				return hMapResult;
	        }
		}		

		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", putGubun);
		hMapResult.put("CUST_CODE", paramMap.get("CUST_CODE"));

        return hMapResult;
    }
    
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW , isolation=Isolation.READ_COMMITTED)
    public HashMap<String, Object> putOrder(HashMap<String, Object> paramMap) throws Exception {
		
		int putCount = 0;
		String putGubun;
		String checkMessage;
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");
		
		//주문번호중복체크
		paramMap.put(Gv.QUERY_ID  , "apiMediPlan.checkOrderNo");
		checkMessage = (String)cmmnDao.select(paramMap);
		if (!"".equals(checkMessage)) {
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", paramMap.get("ORDER_NO") + ":" + checkMessage);
			log.error("checkOrderNo ERROR:"+paramMap.get("ORDER_NO") + ":" + checkMessage);
			return hMapResult;
		}
		
		//주문등록
		List<HashMap<String, Object>> listOrder = new ArrayList<>();
		listOrder =  (List<HashMap<String, Object>>) paramMap.get("DATA");
		if (listOrder != null && listOrder.size() > 0) {

			//품목별 가용재고 체크
			for (HashMap<String, Object> orderData : listOrder) {
				orderData.put("API_BRCH_CD", paramMap.get("API_BRCH_CD"));
				orderData.put("API_INS", paramMap.get("API_INS"));
				orderData.put("ORDER_NO", paramMap.get("ORDER_NO"));
				orderData.put("CUST_CODE", paramMap.get("CUST_CODE"));
				log.debug("apiMediPlan orderData:"+orderData);

				orderData.put(Gv.QUERY_ID  , "apiMediPlan.checkAvailStock");
				checkMessage = (String)cmmnDao.select(orderData);
				if (!"".equals(checkMessage)) {
					hMapResult.put("RESULT", "ERROR");
					hMapResult.put("MESSAGE", orderData.get("ITEM_CODE") + ":" + checkMessage);
					log.error("checkAvailStock ERROR:"+orderData.get("ITEM_CODE") + ":" + checkMessage);
					return hMapResult;
				}
			}

			//품목별 주문등록
			for (HashMap<String, Object> orderData : listOrder) {
				orderData.put("API_BRCH_CD", paramMap.get("API_BRCH_CD"));
				orderData.put("API_INS", paramMap.get("API_INS"));
				orderData.put("ORDER_NO", paramMap.get("ORDER_NO"));
				orderData.put("CUST_CODE", paramMap.get("CUST_CODE"));

				orderData.put(Gv.QUERY_ID  , "apiMediPlan.insertOrder");
				cmmnDao.insert(orderData);
			}

			//P_ORD_WEB_APRV
			HashMap<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("I_MALL_CD"    , paramMap.get("API_BRCH_CD"));
			pMap.put("I_ORD_NO"		, paramMap.get("ORDER_NO"));
			pMap.put("I_CUST_CD"    , paramMap.get("CUST_CODE"));
			pMap.put("I_USER_ID"    , paramMap.get("API_INS"));
			cmmnDao.callProcedure("apiMediPlan.callPSaleMallOrd", pMap);

			if(!pMap.get("O_VAL").equals("SUCCESS")) {
	    		int len = pMap.get("O_VAL").toString().length() > 200 ? 200 : pMap.get("O_VAL").toString().length();
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("MESSAGE", pMap.get("O_MSG").toString().substring(0,len));
				log.error("callProc callPSaleMallOrd ERROR:"+pMap.get("O_MSG").toString().substring(0,len));
	        }			
			hMapResult.put("DATACOUNT", listOrder.size());
		}
		else {
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", "NO DATA");
		}
		
		return hMapResult;
    }


}

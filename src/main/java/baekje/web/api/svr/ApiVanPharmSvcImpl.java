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
public class ApiVanPharmSvcImpl implements ApiVanPharmSvc {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	CmmnDao cmmnDao;

	@Autowired
	CmmnService cmmnSvc;
	
	@SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
    public int insertApiHistory(HashMap<String, Object> paramMap) throws Exception {
		paramMap.put(Gv.QUERY_ID  , "apiVanPharm.insertApiHistory");
        return cmmnDao.insert(paramMap);
    }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW , isolation=Isolation.READ_COMMITTED)
    public HashMap<String, Object> putBack(HashMap<String, Object> paramMap) throws Exception {
		
		int putCount = 0;
		String ordTypeCd = paramMap.get("ORD_TYPE_CD").toString();
		String checkMessage;
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("ERRORCODE", "");
		hMapResult.put("ERRORMESSAGE", "");
		
		//반품등록
		List<HashMap<String, Object>> listBack = new ArrayList<>();
		listBack =  (List<HashMap<String, Object>>) paramMap.get("DATA");
		if (listBack != null && listBack.size() > 0) {

	        HashMap<String, Object> snMap = new HashMap<String, Object>();
	        paramMap.put("RCPT_SN", cmmnSvc.getNextSn("SALEBACK", paramMap.get("CUST_CD").toString() , "", ""));

			//품목별 반품임시등록
	        
			for (HashMap<String, Object> backData : listBack) {
				backData.put("ORD_TYPE_CD", paramMap.get("ORD_TYPE_CD"));
				backData.put("RCPT_SN", paramMap.get("RCPT_SN"));
				backData.put("RCPT_SNO", ++putCount);
				backData.put("CUST_CD", paramMap.get("CUST_CD"));
				backData.put("USER_ID", paramMap.get("USER_ID"));
				
				backData.put(Gv.QUERY_ID  , "apiVanPharm.insertOrder");
				cmmnDao.insert(backData);
			}

			
			//반품접수등록
			//P_BACK_I (BARCODE_BACK)
			HashMap<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("RCPT_SN"      , paramMap.get("RCPT_SN"));
			pMap.put("USER_ID"      , paramMap.get("USER_ID"));
			cmmnDao.callProcedure("apiVanPharm.callPBackI", pMap);

			if(!pMap.get("O_VAL").equals("SUCCESS")) {
	    		int len = pMap.get("O_VAL").toString().length() > 200 ? 200 : pMap.get("O_VAL").toString().length();
				hMapResult.put("RESULT", "ERROR");
				hMapResult.put("ERRORCODE", "NOTINSERT");
				hMapResult.put("ERRORMESSAGE", pMap.get("O_MSG").toString().substring(0,len));
				log.error("callProc P_BACK_I(BARCODE_BACK) ERROR:"+pMap.get("O_MSG").toString().substring(0,len));
	        }
	        
	        
			hMapResult.put("DATACOUNT", listBack.size());
		}
		else {
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("ERRORCODE", "NODATA");
			hMapResult.put("ERRORMESSAGE", "NO DATA");
		}
		
		return hMapResult;
    }


}

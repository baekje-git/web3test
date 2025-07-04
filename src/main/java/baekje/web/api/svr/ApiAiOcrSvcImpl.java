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
public class ApiAiOcrSvcImpl implements ApiAiOcrSvc {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	CmmnDao cmmnDao;

	@SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
    public int insertApiHistory(HashMap<String, Object> paramMap) throws Exception {
		paramMap.put(Gv.QUERY_ID  , "apiAiOcr.insertApiHistory");
        return cmmnDao.insert(paramMap);
    }
	
    @Override
    public String checkAuth(HashMap<String, Object> paramMap) throws Exception {
		paramMap.put(Gv.QUERY_ID  , "apiAiOcr.checkAuth");
		return (String)cmmnDao.select(paramMap);
    }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW , isolation=Isolation.READ_COMMITTED)
    public HashMap<String, Object> putInvoice(HashMap<String, Object> paramMap) throws Exception {
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		//매입명세서 T_BUY_OCR 등록
		paramMap.put(Gv.QUERY_ID  , "apiAiOcr.insertInvoice");
		cmmnDao.insert(paramMap);

		//T_BUY_OCR_ITEM
		List<HashMap<String, Object>> listItem = new ArrayList<>();
		listItem =  (List<HashMap<String, Object>>) paramMap.get("ITEMS");
		if (listItem != null && listItem.size() > 0) {

			for (HashMap<String, Object> itemData : listItem) {
				itemData.put("INVOICE_SEQ", paramMap.get("INVOICE_SEQ"));
				itemData.put("API_INS", paramMap.get("API_INS"));

				itemData.put(Gv.QUERY_ID  , ".apiAiOcrinsertInvoiceItem");
				cmmnDao.insert(itemData);
			}
		}
		else {
			hMapResult.put("RESULT", "ERROR");
			hMapResult.put("MESSAGE", "NO ITEMS DATA");
		}

		//T_BUY_OCR_ITEM_CANDIDATE
		List<HashMap<String, Object>> listItemCandidate = new ArrayList<>();
		listItemCandidate =  (List<HashMap<String, Object>>) paramMap.get("CANDIDATE_ITEMS");
		if (listItemCandidate != null && listItemCandidate.size() > 0) {

			for (HashMap<String, Object> itemData : listItemCandidate) {
				itemData.put("INVOICE_SEQ", paramMap.get("INVOICE_SEQ"));
				itemData.put("API_INS", paramMap.get("API_INS"));

				itemData.put(Gv.QUERY_ID  , "apiAiOcr.insertInvoiceItemCandidate");
				cmmnDao.insert(itemData);
			}
		}

		return hMapResult;
    }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW , isolation=Isolation.READ_COMMITTED)
    public HashMap<String, Object> saveFileUploadResult(HashMap<String, Object> paramMap) throws Exception {
		
		HashMap<String, Object> hMapResult = new HashMap<>();
		hMapResult.put("RESULT", "SUCCESS");
		hMapResult.put("MESSAGE", "");

		//업로드이미지 처리결과 UPDATE
		paramMap.put(Gv.DATASOURCE, Gv.SAL1);
		paramMap.put(Gv.NAMESPACE , "apiAiOcr");
		paramMap.put(Gv.QUERY_ID  , "saveFileUploadResult");
		cmmnDao.update(paramMap);

		return hMapResult;
    }
	

}

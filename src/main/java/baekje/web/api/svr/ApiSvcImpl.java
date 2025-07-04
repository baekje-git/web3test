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

import ever.cmmn.dao.CmmnDao;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

import baekje.web.api.dao.ApiDao;

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
@Qualifier("comOrdSvc")
public class ApiSvcImpl implements ApiSvc {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	ApiDao apiDao;

	@Autowired
	CmmnDao cmmnDao;

	@Autowired
	CmmnService cmmnSvc;

	@SuppressWarnings("unchecked")
	@Override
	public  Map<String,Object> selectInicisLogin(HashMap<String, Object> paramMap) throws Exception{

		//Map<String,Object> retMap= new Map<String,Object>();
		HashMap<String, Object> retMap = new HashMap<String, Object>();
	    
		boolean successYn = false;
        List<Object> listUser=null;
        List<Object> listCompany=null;
        List<Object> listStoreItems=null;
        
        try {

            /* 사용자정보 */
        	listUser = apiDao.selectInicisUser(paramMap);
            /* 회사정보 */
    		listCompany = apiDao.selectInicisCompany(paramMap);
        	if (listUser.size() == 0 || listCompany.size() == 0) {
        		/* 로그인실패시 */
        		retMap.put("Result", "R401");
        		retMap.put("Msg", "로그인 실패");
        	}
        	else {
                /* 거래처목록 */
        		listStoreItems = apiDao.selectInicisStoreItems(paramMap);
        		
        		if (listStoreItems.size() == 0) {
            		/* 거래처조회건수 없음 */
            		retMap.put("Result", "R001");
            		retMap.put("Msg", "조회결과 없음");
        		}
        		else {
        			successYn = true;
        		}
        		
        	}
        	
        	if (successYn) {
        		retMap.put("User", listUser.get(0));
        		retMap.put("Company", listCompany.get(0));
        		retMap.put("StoreItems", listStoreItems);
        		retMap.put("Result", "R000");
        		retMap.put("Msg", "성공");
        	}

            return retMap;
            
        }catch(Exception e) {
        	retMap.put("Result", "R999");
        	retMap.put("Msg", "실패");
            return retMap;
        }
        
	}
	
}

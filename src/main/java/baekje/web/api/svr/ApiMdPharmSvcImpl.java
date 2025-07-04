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

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class ApiMdPharmSvcImpl implements ApiMdPharmSvc {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	CmmnDao cmmnDao;

	@SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
    public int insertApiHistory(HashMap<String, Object> paramMap) throws Exception {
		paramMap.put(Gv.QUERY_ID  , "apiMdPharm.insertApiHistory");
        return cmmnDao.insert(paramMap);
    }

    @Override
    public String checkAuth(HashMap<String, Object> paramMap) throws Exception {
		paramMap.put(Gv.QUERY_ID  , "apiMdPharm.checkAuth");
		return (String)cmmnDao.select(paramMap);
    }
}

/**
 * MainDaoImpl.java V1.0 2017. 6. 12.
 *
 * Copyright (c) 2017 UBIStorm Co. All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of UBIStorm Co.  You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement
 * you entered into with UBIStorm Co.
 **/

package baekje.web.api.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ever.support.mybatis.SqlSessionExt;

/**
 * API 호출
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>abj1<br>
 * <strong>작성일 : </strong>2017. 10. 31.오후 6:51:30<br>
 * @author       abj1
 * @package      baekje.web.bakRcpt.dao
 * @file         BakRcptDaoImpl.java
 * @version      1.0
 * @since        2017. 10. 31.
 */
@Repository("apiDao")
public class ApiDaoImpl extends SqlSessionExt implements ApiDao {
private Logger log = LogManager.getLogger(this.getClass());

	@Override
	public List<Object> selectInicisUser(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("api.selectInicisUser", paramMap);
	}

	@Override
	public List<Object> selectInicisCompany(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("api.selectInicisCompany", paramMap);
	}

	@Override
	public List<Object> selectInicisStoreItems(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("api.selectInicisStoreItems", paramMap);
	}

}

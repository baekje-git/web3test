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
 * 메디플랜 API
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong><br>
 * <strong>작성일 : </strong><br>
 * @author       
 * @package      
 * @file         
 * @version      1.0
 * @since        2017. 10. 31.
 */
@Repository("apiMediPlanDao")
public class ApiMediPlanDaoImpl extends SqlSessionExt implements ApiMediPlanDao {
private Logger log = LogManager.getLogger(this.getClass());

    //@Override
    //public int insertCust(HashMap<String, Object> paramMap) throws Exception {
    //    return insertDataSource("apiMediPlan.insertCust", paramMap);
    //}	

}

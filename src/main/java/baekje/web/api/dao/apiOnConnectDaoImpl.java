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
 * 온커넥트 매출조회 API
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
@Repository("apiOnConnectDao")
public class apiOnConnectDaoImpl extends SqlSessionExt implements apiOnConnectDao {
private Logger log = LogManager.getLogger(this.getClass());

    /*인증키체크*/
    @Override
    public List<Object> selectKeyCheck(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("apiOnConnect.selectKeyCheck", paramMap);
    }
    
    /*Mster전체합정보*/
    @Override
    public List<Object> selectSaleMaster(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("apiOnConnect.selectSaleMaster", paramMap);
    }
    
    /*Mster상세정보*/
    @Override
    public List<Object> selectSaleMasterList(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("apiOnConnect.selectSaleMasterList", paramMap);
    }
    
    /*dtl정보*/
    @Override
    public List<Object> selectSaleDetail(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("apiOnConnect.selectSaleDetail", paramMap);
    }
    
    /*dtlList정보*/
    @Override
    public List<Object> selectSaleDetailList(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("apiOnConnect.selectSaleDetailList", paramMap);
    }
    
    /*serial정보*/
    @Override
    public List<Object> selectSaleSerial(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("apiOnConnect.selectSaleSerial", paramMap);
    }
    
    /*serialList정보*/
    @Override
    public List<Object> selectSaleSerialList(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("apiOnConnect.selectSaleSerialList", paramMap);
    }

    /*code정보*/
    @Override
    public List<Object> selectCode(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("apiOnConnect.selectCode", paramMap);
    }
    
    /*serialList정보*/
    @Override
    public List<Object> selectCodeList(HashMap<String, Object> paramMap) throws Exception {
        return selectListDataSource("apiOnConnect.selectCodeList", paramMap);
    }

    @Override
    public int insertApiHistory(HashMap<String, Object> paramMap) throws Exception {
        return insertDataSource("apiOnConnect.insertApiHistory", paramMap);
    }	

}

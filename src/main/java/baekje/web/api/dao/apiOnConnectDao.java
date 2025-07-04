/**
 * MainDao.java V1.0 2017. 6. 12.
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
/**
 * 온커넥트 매출조회 API
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>abj1<br>
 * <strong>작성일 : </strong>2017. 10. 31.오후 6:45:49<br>
 * @author       abj1
 * @package      baekje.web.bakRcpt.dao
 * @file         BakRcptDao.java
 * @version      1.0
 * @since        2017. 10. 31.
 */
public interface apiOnConnectDao {

    /*인증키 확인*/
    public List<Object> selectKeyCheck(HashMap<String, Object> paramMap) throws Exception;

    /*마스터*/
    public List<Object> selectSaleMaster(HashMap<String, Object> paramMap) throws Exception;
    /*마스터리스트*/
    public List<Object> selectSaleMasterList(HashMap<String, Object> paramMap) throws Exception;
    
    /*명세서 상세*/
    public List<Object> selectSaleDetail(HashMap<String, Object> paramMap) throws Exception;
    /*명세서 상세리스트*/
    public List<Object> selectSaleDetailList(HashMap<String, Object> paramMap) throws Exception;
    
    /*시리얼 상세*/
    public List<Object> selectSaleSerial(HashMap<String, Object> paramMap) throws Exception;
    /*시리얼 상세리스트*/
    public List<Object> selectSaleSerialList(HashMap<String, Object> paramMap) throws Exception;
    
    /*code 정보*/
    public List<Object> selectCode(HashMap<String, Object> paramMap) throws Exception;
    /*code 정보리스트*/
    public List<Object> selectCodeList(HashMap<String, Object> paramMap) throws Exception;

    public int insertApiHistory(HashMap<String, Object> paramMap) throws Exception;

}

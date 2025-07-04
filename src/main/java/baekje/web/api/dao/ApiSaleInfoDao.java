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
 * 반품접수 DAO
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
public interface ApiSaleInfoDao {

    /*인증키 확인*/
    public List<Object> selectKeyCheck(HashMap<String, Object> paramMap) throws Exception;
    /*마스터전체*/
    public List<Object> selectSlaeMstInfo(HashMap<String, Object> paramMap) throws Exception;
    /*마스터상세*/
    public List<Object> selectSlaeMstDtlInfo(HashMap<String, Object> paramMap) throws Exception;
    
    /*명세서 상세리스트*/
    public List<Object> selectSlaeDtlInfo(HashMap<String, Object> paramMap) throws Exception;
    /*명세서 시리얼상세리스트*/
    public List<Object> selectSlaeDtlSerialInfo(HashMap<String, Object> paramMap) throws Exception;
    
    
    
}

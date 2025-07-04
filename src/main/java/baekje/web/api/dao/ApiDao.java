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
public interface ApiDao {

	public List<Object> selectInicisUser(HashMap<String, Object> paramMap) throws Exception;
	public List<Object> selectInicisCompany(HashMap<String, Object> paramMap) throws Exception;
	public List<Object> selectInicisStoreItems(HashMap<String, Object> paramMap) throws Exception;
	
}

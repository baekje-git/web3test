/**
 * MainSvc.java V1.0 2017. 6. 12.
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

/**
 * Copyright Copyright (c) 2017 Company UBIStorm Co. <br>
 * <br>
 * <strong>작성자 : </strong><br>
 * <strong>작성일 : </strong><br>
 * 설 명 : 내용<br>
 *
 * @author 
 * @package 
 * @file 
 * @version 1.0
 * @since 2017. 6. 12.
 */

public interface ApiMediPlanSvc {

    public int insertApiHistory(HashMap<String, Object> paramMap) throws Exception;
	public String checkAuth(HashMap<String, Object> paramMap) throws Exception;
	public String checkCust(HashMap<String, Object> paramMap) throws Exception;
	
	public HashMap<String, Object> putCust(HashMap<String, Object> paramMap) throws Exception;
	public HashMap<String, Object> putOrder(HashMap<String, Object> paramMap) throws Exception;

}

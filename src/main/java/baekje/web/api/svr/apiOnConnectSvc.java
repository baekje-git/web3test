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
import java.util.Map;

import org.springframework.util.MultiValueMap;
/**
 * Copyright Copyright (c) 2017 Company UBIStorm Co. <br>
 * <br>
 * <strong>작성자 : </strong>khlee<br>
 * <strong>작성일 : </strong>2017. 6. 12.오전 10:26:58<br>
 * 설 명 : 내용<br>
 *
 * @author khlee
 * @package baekje.main.svr
 * @file MainSvc.java
 * @version 1.0
 * @since 2017. 6. 12.
 */

public interface apiOnConnectSvc {

    public Map<Object, Object> selectSaleMaster(HashMap<String, Object> paramMap) throws Exception;
    public Map<Object, Object> selectSaleDetail(HashMap<String, Object> paramMap) throws Exception;
    public Map<Object, Object> selectSaleSerial(HashMap<String, Object> paramMap) throws Exception;
    public Map<Object, Object> selectCode(HashMap<String, Object> paramMap) throws Exception;
    public int insertApiHistory(HashMap<String, Object> paramMap) throws Exception;

}

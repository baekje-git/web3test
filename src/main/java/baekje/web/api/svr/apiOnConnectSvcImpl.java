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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import ever.cmmn.dao.CmmnDao;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

import baekje.web.api.dao.apiOnConnectDao;

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
@Qualifier("comOrdSvc2")
public class apiOnConnectSvcImpl implements apiOnConnectSvc {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	apiOnConnectDao apiOnConnectDao;

	@Autowired
	CmmnDao cmmnDao;


	@SuppressWarnings("unchecked")
	@Override
	
	public Map<Object, Object> selectSaleMaster(HashMap<String, Object> paramMap) throws Exception{
	    
		Map<Object,Object> retMap= new HashMap<Object,Object>();
	    
        List<Object> listKey  = null; 
        List<Object> listData = null; 
        List<Object> listLIST = null; 
        
        try {
    	    /*log.info("paramMap===========>"+paramMap);*/

            /* 인증키 확인 */
        	listKey = apiOnConnectDao.selectKeyCheck(paramMap);
        	
        	if (listKey.size() == 0 ) {
        		/* 로그인실패시 */
        	    retMap.put("RESULT", "01");
        	    retMap.put("MSG", "KEY값 오류로 접속 실패");
        	}
        	else {
                /* 마스터 */
        	    listData = apiOnConnectDao.selectSaleMaster(paramMap);

        	    if (listData.size() == 0) {
            		/* 조회건수 없음 */
        	        retMap.put("RESULT", "02");
        	        retMap.put("MSG", "조회결과 없음");
        		}
        		else {
            	    retMap.put("DATA", listData);

        			listLIST = apiOnConnectDao.selectSaleMasterList(paramMap);
                    
        		    if (listLIST.size() == 0) {
        		    	retMap.put("RESULT", "03");
        		    	retMap.put("MSG", "리스트조회결과 없음");
        		    }
        		    else {
            		    for(Object row : listLIST) {
            	            HashMap<String, Object> item = (HashMap<String, Object>)row;
            	            //log.info("item===========>"+item);
            	            //list.add(item);
            	            //retMap.put("LIST", item);
            	        }
        	            retMap.put("LIST", listLIST);
                	    retMap.put("RESULT", "00");
                	    retMap.put("MSG", "정상");
        		    }
        			
        		}
        		
        	}

            return retMap;
            
        }catch(Exception e) {
            retMap.put("RESULT", "99");
            retMap.put("MSG", "실패");
            return retMap;
        }
        
	}

	@Override
	public Map<Object, Object> selectSaleDetail(HashMap<String, Object> paramMap) throws Exception {
		Map<Object,Object> retMap= new HashMap<Object,Object>();
	    
        List<Object> listKey  = null; 
        List<Object> listDATA = null; 
        List<Object> listLIST = null; 
        
        try {
    	    /*log.info("paramMap===========>"+paramMap);*/

            /* 인증키 확인 */
        	listKey = apiOnConnectDao.selectKeyCheck(paramMap);
        	
        	if (listKey.size() == 0 ) {
        		/* 로그인실패시 */
        	    retMap.put("RESULT", "01");
        	    retMap.put("MSG", "KEY값 오류로 접속 실패");
        	}
        	else {
                /* 디테일 */
        	    listDATA = apiOnConnectDao.selectSaleDetail(paramMap);

        	    if (listDATA.size() == 0) {
            		/* 조회건수 없음 */
        	        retMap.put("RESULT", "02");
        	        retMap.put("MSG", "조회결과 없음");
        		}
        		else {
            	    retMap.put("DATA", listDATA);

        			listLIST = apiOnConnectDao.selectSaleDetailList(paramMap);
                    
        		    if (listLIST.size() == 0) {
        		    	retMap.put("RESULT", "03");
        		    	retMap.put("MSG", "리스트조회결과 없음");
        		    }
        		    else {
            		    for(Object row : listLIST) {
            	            HashMap<String, Object> item = (HashMap<String, Object>)row;
            	            //log.info("item===========>"+item);
            	            //list.add(item);
            	            //retMap.put("LIST", item);
            	        }
        	            retMap.put("LIST", listLIST);
                	    retMap.put("RESULT", "00");
                	    retMap.put("MSG", "정상");
        		    }
        			
        		}
        		
        	}

            return retMap;
            
        }catch(Exception e) {
            retMap.put("RESULT", "99");
            retMap.put("MSG", "실패");
            return retMap;
        }
	}
    
	@Override
	public Map<Object, Object> selectSaleSerial(HashMap<String, Object> paramMap) throws Exception {
		Map<Object,Object> retMap= new HashMap<Object,Object>();
	    
        List<Object> listKey  = null; 
        List<Object> listDATA = null; 
        List<Object> listLIST = null; 
        
        try {
    	    /*log.info("paramMap===========>"+paramMap);*/

            /* 인증키 확인 */
        	listKey = apiOnConnectDao.selectKeyCheck(paramMap);
        	
        	if (listKey.size() == 0 ) {
        		/* 로그인실패시 */
        	    retMap.put("RESULT", "01");
        	    retMap.put("MSG", "KEY값 오류로 접속 실패");
        	}
        	else {
                /* 디테일 */
        	    listDATA = apiOnConnectDao.selectSaleSerial(paramMap);

        	    if (listDATA.size() == 0) {
            		/* 조회건수 없음 */
        	        retMap.put("RESULT", "02");
        	        retMap.put("MSG", "조회결과 없음");
        		}
        		else {
            	    retMap.put("DATA", listDATA);

        			listLIST = apiOnConnectDao.selectSaleSerialList(paramMap);
                    
        		    if (listLIST.size() == 0) {
        		    	retMap.put("RESULT", "03");
        		    	retMap.put("MSG", "리스트조회결과 없음");
        		    }
        		    else {
            		    for(Object row : listLIST) {
            	            HashMap<String, Object> item = (HashMap<String, Object>)row;
            	            //log.info("item===========>"+item);
            	            //list.add(item);
            	            //retMap.put("LIST", item);
            	        }
        	            retMap.put("LIST", listLIST);
                	    retMap.put("RESULT", "00");
                	    retMap.put("MSG", "정상");
        		    }
        			
        		}
        		
        	}

            return retMap;
            
        }catch(Exception e) {
            retMap.put("RESULT", "99");
            retMap.put("MSG", "실패");
            return retMap;
        }
	}	

	@Override
	public Map<Object, Object> selectCode(HashMap<String, Object> paramMap) throws Exception {
		Map<Object,Object> retMap= new HashMap<Object,Object>();
	    
        List<Object> listKey  = null; 
        List<Object> listDATA = null; 
        List<Object> listLIST = null; 
        
        try {
    	    /*log.info("paramMap===========>"+paramMap);*/

            /* 인증키 확인 */
        	listKey = apiOnConnectDao.selectKeyCheck(paramMap);
        	
        	if (listKey.size() == 0 ) {
        		/* 로그인실패시 */
        	    retMap.put("RESULT", "01");
        	    retMap.put("MSG", "KEY값 오류로 접속 실패");
        	}
        	else {
                /* 디테일 */
        	    listDATA = apiOnConnectDao.selectCode(paramMap);

        	    if (listDATA.size() == 0) {
            		/* 조회건수 없음 */
        	        retMap.put("RESULT", "02");
        	        retMap.put("MSG", "조회결과 없음");
        		}
        		else {
            	    retMap.put("DATA", listDATA);

        			listLIST = apiOnConnectDao.selectCodeList(paramMap);
                    
        		    if (listLIST.size() == 0) {
        		    	retMap.put("RESULT", "03");
        		    	retMap.put("MSG", "리스트조회결과 없음");
        		    }
        		    else {
            		    for(Object row : listLIST) {
            	            HashMap<String, Object> item = (HashMap<String, Object>)row;
            	            //log.info("item===========>"+item);
            	            //list.add(item);
            	            //retMap.put("LIST", item);
            	        }
        	            retMap.put("LIST", listLIST);
                	    retMap.put("RESULT", "00");
                	    retMap.put("MSG", "정상");
        		    }
        			
        		}
        		
        	}

            return retMap;
            
        }catch(Exception e) {
            retMap.put("RESULT", "99");
            retMap.put("MSG", "실패");
            return retMap;
        }
	}	

    /**
     * 검색자IP정보이력저장
     * <strong>작성자 : </strong>abj1<br>
     * <strong>작성일 : </strong>2017. 10. 20.오전 11:19:30<br>
     * 설명 : 직접입력 <br>
     *
     * @version 1.0
     * @author  abj1
     * @since   2017. 10. 20.
     * @param   파라미터타입_직접입력  파라미터설명_직접입력
     * @return  int 리턴에대한설명_직접입력
     */
    @Override
    @Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
    public int insertApiHistory(HashMap<String, Object> paramMap) throws Exception {
        int retVal = 0;
        retVal = apiOnConnectDao.insertApiHistory(paramMap);
        return retVal;
    }


}

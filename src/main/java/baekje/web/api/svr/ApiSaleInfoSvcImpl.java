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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ever.cmmn.dao.CmmnDao;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

import baekje.web.api.dao.ApiSaleInfoDao;

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
public class ApiSaleInfoSvcImpl implements ApiSaleInfoSvc {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	ApiSaleInfoDao ApiSaleInfoDao;

	@Autowired
	CmmnDao cmmnDao;

	@Autowired
	CmmnService cmmnSvc;

	@SuppressWarnings("unchecked")
	@Override
	
	//public  Map<String,Object> selectInicisLogin(HashMap<String, Object> paramMap) throws Exception{
	public  Map<Object, Object> selectSaleMstInfo(HashMap<String, Object> paramMap) throws Exception{
	    
	    Map<Object, Object> retList = new HashMap<Object, Object>();
	    
		//Map<String,Object> retMap= new Map<String,Object>();
		HashMap<String, Object> retMap = new HashMap<String, Object>();
	    
		boolean successYn = false;
        List<Object> listKey =null; //listUser
        //List<Object> listMstInfo=null; //listCompany
        List<Object> listMstInfo =null; //listStoreItems
        List<Object> listMstDtlInfo =null; //
        
        try {

            /* 인증키 확인 */
        	listKey = ApiSaleInfoDao.selectKeyCheck(paramMap);
            /* 회사정보 */
    		/* listCompany = ApiSaleInfoDao.selectInicisCompany(paramMap); */
        	
        	if (listKey.size() == 0 ) {
        		/* 로그인실패시 */
        	    retList.put("RESULT", "01");
        	    retList.put("MSG", "KEY값 오류로 접속 실패");
        	}
        	else {
                /* 마스터 */
        	    /*log.info("paramMap===========>"+paramMap);*/
        	    listMstInfo = ApiSaleInfoDao.selectSlaeMstInfo(paramMap);
        	    if (listMstInfo.size() == 0) {
            		/* 거래처조회건수 없음 */
        	        retList.put("RESULT", "02");
        	        retList.put("MSG", "조회결과 없음");
        		}
        		else {
        		    listMstDtlInfo = ApiSaleInfoDao.selectSlaeMstDtlInfo(paramMap);
                    
        		    for(Object row : listMstInfo) {
        	            HashMap<String, Object> item = (HashMap<String, Object>)row;
        	            item.put("MST_LIST", listMstDtlInfo);
        	        }
        		    
        		    
        		    if (listMstDtlInfo.size() == 0) {
        		        retMap.put("RESULT", "03");
                        retMap.put("MSG", "리스트조회결과 없음");
        		    }
        		    else {
        		        successYn = true;
        		        
        		    }
        			
        		}
        		
        	}

        	if (successYn) {
        	    retList.put("DATA", listMstInfo);
        	    retList.put("RESULT", "00");
        	    retList.put("MSG", "정상");
        	}

            return retList;
            
        }catch(Exception e) {
            //log.error("에러", e.getCause());
            retList.put("RESULT", "99");
            retList.put("MSG", "실패");
            return retList;
        }
        
	}
	
public  Map<Object, Object> selectSaleDtlInfo(HashMap<String, Object> paramMap) throws Exception{
        
        Map<Object, Object> retList = new HashMap<Object, Object>();
        
        //Map<String,Object> retMap= new Map<String,Object>();
        //HashMap<String, Object> retMap = new HashMap<String, Object>();
        
        boolean successYn = false;
        List<Object> listKey =null; //listUser
        //List<Object> listMstInfo=null; //listCompany
        List<Object> listDtlInfo =null; //listStoreItems
        List<Object> listDtlSerialInfo =null; //
        
        try {

            /* 인증키 확인 */
            listKey = ApiSaleInfoDao.selectKeyCheck(paramMap);
            /* 회사정보 */
            /* listCompany = ApiSaleInfoDao.selectInicisCompany(paramMap); */
            
            if (listKey.size() == 0 ) {
                /* 로그인실패시 */
                retList.put("RESULT", "01");
                retList.put("MSG", "KEY값 오류로 접속 실패");
                successYn = false;
            }
            else {
                /* 마스터 */
                listDtlInfo = ApiSaleInfoDao.selectSlaeDtlInfo(paramMap);
              //paramMap.put("Sno",item.get("SNO"));
                listDtlSerialInfo = ApiSaleInfoDao.selectSlaeDtlSerialInfo(paramMap);                        
                
                if (listDtlInfo.size() == 0) {
                    /* 거래처조회건수 없음 */
                    retList.put("RESULT", "02");
                    retList.put("MSG", "조회결과 없음");
                    successYn = false;
                }
                else {                    
                    for(Object row : listDtlInfo) {
                        HashMap<String, Object> item = (HashMap<String, Object>)row;
                        //log.info("item===========>"+item);
                        
                        List<Object> detailList2 = listDtlSerialInfo.stream().filter(t -> {
                            HashMap<String,Object> r = (HashMap<String,Object>)t;
                            //log.info("item.get(\"SNO\")===========>"+item.get("SNO"));
                            //log.info("r.get(\"SNO\")===========>"+r.get("SNO"));
                            // log.info("String.valueOf===========>"+String.valueOf(item.get("SNO")).equals(String.valueOf(r.get("SNO"))));
                            
                            return item.get("SNO").equals(r.get("SNO")) ? true : false;                
                        }).collect(Collectors.toList());
                        
                        //List<Object> detailList2=null;
                        //log.info("detailList2===========>"+detailList2);
                        detailList2=detailList2.stream().filter(t -> {
                            HashMap<String,Object> r = (HashMap<String,Object>)t;
                            //log.info("item.get(\"SNO\")===========>"+item.get("SNO"));
                            //log.info("r.get(\"SNO\")===========>"+r.get("SNO"));
                            // log.info("String.valueOf===========>"+String.valueOf(item.get("SNO")).equals(String.valueOf(r.get("SNO"))));
                            r.remove("SNO");
                            r.remove("SN");
                            return true;                
                        }).collect(Collectors.toList());
                        
                        //log.info("detailList2===========>"+detailList2);
                        item.put("SERIAL_LIST", detailList2);
                        //log.info("item===========>"+item);
                        //log.info("listDtlSerialInfo===========>"+listDtlSerialInfo);
                        
                    }
                    
                	successYn = true;
                	/*
                    if (listDtlSerialInfo.size() == 0) {
                    	retList.put("RESULT", "03");
                    	retList.put("MSG", "시리얼 상세 조회결과 없음");
                    	successYn = false;
                    }
                    else {
                        successYn = true;
                        
                    }
                    */
                    
                }
                
            }

            if (successYn) {
                //log.info("listDtlInfo===========>"+listDtlInfo);
                retList.put("DATA", listDtlInfo);
                retList.put("RESULT", "00");
                retList.put("MSG", "정상");
            }
            
            /*log.info("retList===========>"+retList);*/

            return retList;
            
        }catch(Exception e) {
            retList.put("RESULT", "99");
            retList.put("MSG", "실패");
            return retList;
        }
        
    }
    
	

}

package kada.k01.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import kada.k01.service.DopingSubscriptionService;
import kada.util.Util;
import ever.support.session.SessionUtil;


@Controller
@RequestMapping("/k01/dopingsubscription/")
public class DopingSubscriptionController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private DopingSubscriptionService dopingsubscriptionService;
	        
	/**
     * <pre>
     * @MethodName : selectDopingSubscriptionDetail
     * @Description : 검사공지신청 상세조회
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/selectDopingSubscriptionDetail.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> selectDopingSubscriptionDetail(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사공지신청 상세 조회 ");        
        
        try {
        	HashMap<String, Object> data= new HashMap<>();
        	HashMap<String, Object> userInfoMAP= (HashMap<String, Object>)hMap.get("USERINFO");
    		
        	    data=(HashMap<String, Object>)cmmnService.select("k01.dopingsubscription.selectDopingSubscriptionDetail", hMap);
        		
        	    hMap.put("USER_AGENT",request.getHeader("user-agent"));
        		hMap.put("IP_ADDR", request.getRemoteAddr());
        		
        		String stripntplaceLongitude=(String)data.get("IPNT_PLACE_LONGITUDE");
        		String stripntplaceLatitude=(String)data.get("IPNT_PLACE_LATITUDE");
        		
        		//주소정보설정작업필요
        		String strUserAddress1=(String)userInfoMAP.get("USER_ADDRESS1");
        		String strUserDetailAddress1=(String)userInfoMAP.get("USER_DETAIL_ADDRESS1");
        		String strUserAddressLatitude1=(String)userInfoMAP.get("USER_ADDRESS_LATITUDE1");
        		String strUserAddressLongitude1=(String)userInfoMAP.get("USER_ADDRESS_LONGITUDE1");
        		
        		String strUserAddress2=(String)userInfoMAP.get("USER_ADDRESS2");;
        		String strUserDetailAddress2=(String)userInfoMAP.get("USER_DETAIL_ADDRESS2");
        		String strUserAddressLatitude2=(String)userInfoMAP.get("USER_ADDRESS_LATITUDE2");;
        		String strUserAddressLongitude2=(String)userInfoMAP.get("USER_ADDRESS_LONGITUDE2");
        		
        		data.put("USER_ID", userInfoMAP.get("USER_ID"));
        		data.put("SUB_GUBUN",hMap.get("SUB_GUBUN")); 
        		data.put("USER_ADDRESS1",strUserAddress1); 
        		data.put("USER_DETAIL_ADDRESS1",strUserDetailAddress1); 
        		data.put("USER_ADDRESS_LATITUDE1",strUserAddressLatitude1);
        		data.put("USER_ADDRESS_LONGITUDE1",strUserAddressLongitude1); 

                double distance = 0d;
                try {
                    distance = Util.gfn_getDistance(strUserAddressLongitude1, strUserAddressLatitude1, stripntplaceLongitude, stripntplaceLatitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                data.put("IPNT_PLACE_DISTANCE1",String.format("%.2f", distance));

                data.put("USER_ADDRESS1",strUserAddress2);
                data.put("USER_DETAIL_ADDRESS1",strUserDetailAddress2);
                data.put("USER_ADDRESS_LATITUDE1",strUserAddressLatitude2);
                data.put("USER_ADDRESS_LONGITUDE1",strUserAddressLongitude2);

                distance = 0d;
                try {
                    distance = Util.gfn_getDistance(strUserAddressLongitude2, strUserAddressLatitude2, stripntplaceLongitude, stripntplaceLatitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                                
                data.put("IPNT_PLACE_DISTANCE2", String.format("%.2f", distance));

                data.put("USER_ADDRESS2",strUserAddress2);
                data.put("USER_DETAIL_ADDRESS2",strUserDetailAddress2);
                data.put("USER_ADDRESS_LATITUDE2",strUserAddressLatitude2);
                data.put("USER_ADDRESS_LONGITUDE2",strUserAddressLongitude2);

                
			return new ResponseEntity<>(data, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]selectDopingSubscriptionDetail : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : selectDopingSubscriptionDetail
     * @Description : 검사공지신청 상세조회
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/selectDopingSubscriptionDetailAdtis.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> selectDopingSubscriptionDetailAdtis(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사공지신청 상세 조회 ");        
        
        try {
        	HashMap<String, Object> data= new HashMap<>();
        	HashMap<String, Object> userInfoMAP= (HashMap<String, Object>)hMap.get("USERINFO");
    		
        	    data=(HashMap<String, Object>)cmmnService.select("k01.dopingsubscriptionadtis.selectIPNT01TBByPK", hMap);
        		
        	    hMap.put("USER_AGENT",request.getHeader("user-agent"));
        		hMap.put("IP_ADDR", request.getRemoteAddr());
        		
        		String stripntplaceLongitude=(String)data.get("IPNT_PLACE_LONGITUDE");
        		String stripntplaceLatitude=(String)data.get("IPNT_PLACE_LATITUDE");
        		
        		//주소정보설정작업필요
        		String strUserAddress1=(String)userInfoMAP.get("USER_ADDRESS1");
        		String strUserDetailAddress1=(String)userInfoMAP.get("USER_DETAIL_ADDRESS1");
        		String strUserAddressLatitude1=(String)userInfoMAP.get("USER_ADDRESS_LATITUDE1");
        		String strUserAddressLongitude1=(String)userInfoMAP.get("USER_ADDRESS_LONGITUDE1");
        		
        		String strUserAddress2=(String)userInfoMAP.get("USER_ADDRESS2");;
        		String strUserDetailAddress2=(String)userInfoMAP.get("USER_DETAIL_ADDRESS2");
        		String strUserAddressLatitude2=(String)userInfoMAP.get("USER_ADDRESS_LATITUDE2");;
        		String strUserAddressLongitude2=(String)userInfoMAP.get("USER_ADDRESS_LONGITUDE2");
        		data.put("USER_ID", userInfoMAP.get("USER_ID"));
        		data.put("SUB_GUBUN", hMap.get("SUB_GUBUN") ); 
                		
        		data.put("USER_ADDRESS1",strUserAddress1); 
        		data.put("USER_DETAIL_ADDRESS1",strUserDetailAddress1); 
        		data.put("USER_ADDRESS_LATITUDE1",strUserAddressLatitude1);
        		data.put("USER_ADDRESS_LONGITUDE1",strUserAddressLongitude1); 

                double distance = 0d;
                try {
                    distance = Util.gfn_getDistance(strUserAddressLongitude1, strUserAddressLatitude1, stripntplaceLongitude, stripntplaceLatitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                data.put("IPNT_PLACE_DISTANCE1",String.format("%.2f", distance));

                data.put("USER_ADDRESS1",strUserAddress2);
                data.put("USER_DETAIL_ADDRESS1",strUserDetailAddress2);
                data.put("USER_ADDRESS_LATITUDE1",strUserAddressLatitude2);
                data.put("USER_ADDRESS_LONGITUDE1",strUserAddressLongitude2);

                distance = 0d;
                try {
                    distance = Util.gfn_getDistance(strUserAddressLongitude2, strUserAddressLatitude2, stripntplaceLongitude, stripntplaceLatitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                                
                data.put("IPNT_PLACE_DISTANCE2", String.format("%.2f", distance));

                data.put("USER_ADDRESS2",strUserAddress2);
                data.put("USER_DETAIL_ADDRESS2",strUserDetailAddress2);
                data.put("USER_ADDRESS_LATITUDE2",strUserAddressLatitude2);
                data.put("USER_ADDRESS_LONGITUDE2",strUserAddressLongitude2);

                
			return new ResponseEntity<>(data, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]selectDopingSubscriptionDetail : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    
    /**
     * <pre>
     * @MethodName : checkSaveValidation
     * @Description : 검사공지신청 저장전체크조회
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/checkSaveValidation.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> checkSaveValidation(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사공지신청 상세 조회 ");        
        
        try {
        	HashMap<String, Object> data= new HashMap();
        	HashMap<String, Object> userInfoMAP= SessionUtil.getUserInfo(); //(HashMap<String, Object>)hMap.get("USERINFO");
        	hMap.put("USER_ID",userInfoMAP.get("USER_ID"));
        	
        	  data=dopingsubscriptionService.checkSaveValidation(hMap);
        	          	  
			return new ResponseEntity<>(data, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]checkSaveValidation : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : checkCancleValidation
     * @Description : 검사공지신청 취소전체크조회
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/checkCancleValidation.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> checkCancleValidation(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사공지신청 취소전체크조회");        
        
        try {
        	HashMap<String, Object> data= new HashMap();
        	HashMap<String, Object> userInfoMAP= SessionUtil.getUserInfo(); //(HashMap<String, Object>)hMap.get("USERINFO");
        	hMap.put("USER_ID",userInfoMAP.get("USER_ID"));
        	
        	  data=dopingsubscriptionService.checkCancleValidation(hMap);
        	          	  
			return new ResponseEntity<>(data, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]checkCancleValidation : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    
    
    
    
    /**
     * <pre>
     * @MethodName : saveDopingSubscriptionRegReq
     * @Description : 검사공지신청등록요청
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */ 
    @RequestMapping(value="/saveDopingSubscriptionRegReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveDopingSubscriptionRegReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사공지신청등록요청 ");        
        
        try {
        	String ipAddr = request.getRemoteAddr();
            String userAgent = request.getHeader("user-agent");
            
            //서비스에 등록전 체크로직 반영 해야함
            
            dopingsubscriptionService.saveDopingSubscriptionRegReq(hMap,ipAddr,userAgent);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveDopingSubscriptionRegReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveDopingSubscriptionRegReq
     * @Description : 검사공지신청등록요청
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */ 
    @RequestMapping(value="/saveDopingSubscriptionChaperonRegReq.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveDopingSubscriptionChaperonRegReq(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사공지신청샤프롱등록요청 ");        
        
        try {
        	String ipAddr = request.getRemoteAddr();
            String userAgent = request.getHeader("user-agent");
            
            //서비스에 등록전 체크로직 반영 해야함
            
            dopingsubscriptionService.saveDopingSubscriptionChaperonRegReq(hMap,ipAddr,userAgent);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveDopingSubscriptionRegReq : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    /**
     * <pre>
     * @MethodName : saveDopingSubscriptionRegCancle
     * @Description : 검사공지신청등록취소요청
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */ 
    @RequestMapping(value="/saveDopingSubscriptionRegCancle.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveDopingSubscriptionRegCancle(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("검사공지신청등록취소요청 ");        
        
        try {
        	String ipAddr = request.getRemoteAddr();
            String userAgent = request.getHeader("user-agent");
            
           //서비스에 신청등록취소전 체크로직 반영 해야함
            
            dopingsubscriptionService.saveDopingSubscriptionRegCancle(hMap,ipAddr,userAgent);
        	
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveDopingSubscriptionRegCancle : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        
    }
    
    
}

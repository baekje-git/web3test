package kada.k01.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;
import ever.support.file.FileUtil;
import ever.support.session.SessionUtil;


@Service 
public class DopingSubscriptionServiceImpl implements DopingSubscriptionService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	
	@Override
	public HashMap<String, Object> checkSaveValidation(HashMap<String, Object> hmap) throws Exception{
		
			//checkMenuAuth();

		HashMap<String, Object> data= new HashMap<>();
		HashMap<String, Object> result= new HashMap<>();
		
		String subGubun=hmap.get("SUB_GUBUN").toString();
			
    	result.put("CODE","00");
    	result.put("MSG","정상처리되었습니다.");

		
			String USER_ADDRESS_LATITUDE1 =(String)hmap.get("USER_ADDRESS_LATITUDE1");

	        if (USER_ADDRESS_LATITUDE1 == null || "".equals(USER_ADDRESS_LATITUDE1)) {
	        	result.put("CODE","01");
	        	result.put("MSG","회원정보 변경에서 주소를 정상적으로 변경해주셔야 신청이 가능 합니다. 거리오류시엔 선정이 안될수도 있습니다.");
	        	return result;
	        }
	        
	        if("KADAMS".equals(subGubun)) {
		        data=(HashMap<String, Object>)cmmnDao.select("k01.dopingsubscription.selectK03_DOPING_INSPECT_DCOByPK",hmap);
	        }else {
		        data=(HashMap<String, Object>)cmmnDao.select("k01.dopingsubscriptionadtis.selectIPNT07TBByPK",hmap);
	        }

	        if (data != null && data.size() > 0) {
	        	result.put("CODE","01");
	        	result.put("MSG","신청자님은 [" + data.get("REQUEST_DATE") +  "] 에 이미 신청하셨습니다.");
	        	return result;
	        }

	        if("KADAMS".equals(subGubun)) {
	        	data = (HashMap<String, Object>)cmmnDao.select("k01.dopingsubscription.selectMILIGEByPK",hmap); 
	        }else {
	        	data = (HashMap<String, Object>)cmmnDao.select("k01.dopingsubscriptionadtis.selectMLGE01TBByPK",hmap); 
	        }
	        
	       //String aa=  data.get("USED_POINT").toString();
	        
	        if (data != null && Integer.parseInt(data.get("USED_POINT").toString()) > 0 && Integer.parseInt(data.get("USED_POINT").toString()) > 0) {
	        	result.put("CODE","01");
	        	result.put("MSG","신청자님은 포인트를 사용하실 수 없습니다.");
	        	return result;
	        }
	        	 
	        data=null;
	        if("KADAMS".equals(subGubun)) {
	        	data = (HashMap<String, Object>)cmmnDao.select("k01.dopingsubscription.select0101K03_ERROR_REPORT",hmap); 
	        }else {
	        	data = (HashMap<String, Object>)cmmnDao.select("k01.dopingsubscriptionadtis.select0101IPER01TB",hmap); 
	        }
	        
	        if (data != null && data.size() > 0) {
	        	result.put("CODE","98");
	        	result.put("MSG","신청자님은 " + data.get("IPNT_START_DATE") + "~" + data.get("IPNT_END_DATE")  + "까지 " + data.get("IPNT_NAME") + " 의" + data.get("MISTAKE_UPPER_NAME") + " " + data.get("MISTAKE_NAME") + " 중과실로 인해 신청할 수 없습니다. \n문의사항은 한국도핑방지원회 도핑검사부로 해주시기 바랍니다.(☎ 02-2045-9800)");
	        	return result;
	        }

	        
	        List<Object> tt = null;
	        
	        if("KADAMS".equals(subGubun)) {
	        	tt= cmmnDao.selectList("k01.dopingsubscription.selectK99_FOREIGN_EVENT",hmap); 
	        }else {
	        	 tt= cmmnDao.selectList("k01.dopingsubscriptionadtis.selectUSER13TB",hmap); 
	        }
	        
	        
	        if (tt != null && tt.size() > 0) {
					for(Object aa : tt) {
						HashMap<String,Object> row = (HashMap<String, Object>) aa;
					
					if( hmap.get("IPNT_EVENT_CODE").equals( row.get("IPNT_EVENT_CODE") )){
	        			result.put("CODE", "95");
	        			result.put("MSG","유관종목 검사이므로 신청이 불가합니다.");
	        		}
				}
	        }
	        
			
	        
	        
	
	     return result;
		
	}
	
	
	@Override
	public HashMap<String, Object> checkCancleValidation(HashMap<String, Object> hmap) throws Exception{
		
			//checkMenuAuth();

		HashMap<String, Object> data= new HashMap<>();
		HashMap<String, Object> result= new HashMap<>();
		
		String subGubun=hmap.get("SUB_GUBUN").toString();
			
    	result.put("CODE","00");
    	result.put("MSG","정상처리되었습니다.");
	    //신청내역존재여부체크    
	        if("KADAMS".equals(subGubun)) {
		        data=(HashMap<String, Object>)cmmnDao.select("k01.dopingsubscription.selectK03_DOPING_INSPECT_DCOByPK",hmap);
	        }else {
		        data=(HashMap<String, Object>)cmmnDao.select("k01.dopingsubscriptionadtis.selectIPNT07TBByPK",hmap);
	        }

	        if (data == null ) {
	        	result.put("CODE","01");
	        	result.put("MSG","신청자님은 신청한 내역이 없습니다.");
	        	return result;
	        }else {
	        	if( "Y".equals(data.get("CHOOSE_BCO_YN")) || "Y".equals(data.get("CHOOSE_DCO_YN")) ||
	        			"Y".equals(data.get("CHOOSE_DCOBCO_YN")) || "Y".equals(data.get("PREDECESSOR_YN"))  )
	        	{
	        		result.put("CODE","97");
		        	result.put("MSG","신청자님은 이미 선정되셨습니다. 신청을 취소하실 수 없습니다. 관리자에게 문의하십시요.");
		        	return result;
	        	}else if("Y".equals(data.get("CHOOSE_CHAPERON_YN"))) {
	        		result.put("CODE","97");
		        	result.put("MSG","신청자님은 이미 샤프롱으로 선정되셨습니다. 신청을 취소하실 수 없습니다. 관리자에게 문의하십시요.");
		        	return result;
	        	}
	        }
	
	     return result;
		
	}
	@Override
	public void saveDopingSubscriptionRegReq( HashMap<String, Object> hmap,String ipaddr,String userAgent) throws Exception {
		
		//HashMap<String, Object> DopingSubscriptionReqMap= (HashMap<String, Object>)hmap.get("noticeDetail");
		HashMap<String, Object> saveMap =new HashMap<>();
		HashMap<String, Object> userMap =new HashMap<>();
		hmap.put("USER_AGENT",userAgent);
		hmap.put("IP_ADDR", ipaddr);
		hmap.put("GSS", SessionUtil.getUserInfo());
		userMap=(HashMap<String, Object>)hmap.get("GSS");
		
		String subGubun=hmap.get("SUB_GUBUN").toString();
		
		String hope_use=hmap.get("HUSE_YN").toString();
		//검사등록 벨리데이션체크
		//validateRequest(hmap);
		
		saveMap.put("IPNT_NO", hmap.get("IPNT_NO"));
		saveMap.put("IPNT_UPPER_SEQ", hmap.get("IPNT_UPPER_SEQ"));
		saveMap.put("USER_ID", hmap.get("USER_ID"));
		saveMap.put("USE_POINT",hmap.get("USE_POINT"));
		saveMap.put("DISTANCE",hmap.get("DISTANCE"));
		saveMap.put("USER_ADDRESS",hmap.get("USER_ADDRESS"));
		saveMap.put("USER_DETAIL_ADDRESS",hmap.get("USER_DETAIL_ADDRESS"));
		saveMap.put("USER_ADDRESS_LATITUDE",hmap.get("USER_ADDRESS_LATITUDE"));
		saveMap.put("USER_ADDRESS_LONGITUDE",hmap.get("USER_ADDRESS_LONGITUDE"));
		saveMap.put("USE_YN","Y");
		saveMap.put("IP_ADDR",ipaddr);
		saveMap.put("USER_AGENT",userAgent);
		saveMap.put("CHAPERON_YN","N");
        
		int cntPerformance =0;
		if("KADAMS".equals(subGubun)) {
			cntPerformance =  (Integer) cmmnDao.select("k01.dopingsubscription.countK03_DOPING_INSPECT_DCOForPerformance", hmap);
		}else {
			cntPerformance = (Integer) cmmnDao.select("k01.dopingsubscriptionadtis.countIPNT07TBForPerformance", hmap);
		}
		
		saveMap.put("PERFORMANCE_COUNT",String.valueOf(cntPerformance));
		saveMap.put("PERFORMANCE_POINT",String.valueOf((50 - (cntPerformance * 2)) <= 0 ? 0 : 50 - (cntPerformance * 2)));
		saveMap.put("CHAPERON_COUNT","0");
		
        /*if("Y".equals(ipnt07TB.getCHAPERON_YN())){
        	int cntChaperon = (Integer) sqlSession.selectOne("KADA.countIPNT07TBForChaperon", ipnt07TB);
            ipnt07TB.setCHAPERON_COUNT(String.valueOf(cntChaperon));
        }*/
        
		//검사공지신청 등록
		if("KADAMS".equals(subGubun)) {
			cmmnDao.insert("k01.dopingsubscription.insertK03_DOPING_INSPECT_DCO", saveMap);
		}else {
			cmmnDao.insert("k01.dopingsubscriptionadtis.insertIPNT07TB", saveMap);
				
		}
		//공지히망권사용
		if("N".equals(hope_use)) {
			saveMap.put("USE_YN","N");
		}else {
			saveMap.put("USE_YN","Y");
		}
		//검사공지희맘권 사용 등록
		if("KADAMS".equals(subGubun)) {
			cmmnDao.update("k01.dopingsubscription.updateK03_DOPING_INSPECT_HOPEUSE", saveMap);
		}else {
			cmmnDao.update("k01.dopingsubscriptionadtis.updateIPNT15TBByUSE", saveMap);
			
		}
		
	} 
	
	@Override
	public void saveDopingSubscriptionChaperonRegReq( HashMap<String, Object> hmap,String ipaddr,String userAgent) throws Exception {
		
		//HashMap<String, Object> DopingSubscriptionReqMap= (HashMap<String, Object>)hmap.get("noticeDetail");
		HashMap<String, Object> saveMap =new HashMap<>();
		HashMap<String, Object> userMap =new HashMap<>();
		hmap.put("USER_AGENT",userAgent);
		hmap.put("IP_ADDR", ipaddr);
		hmap.put("GSS", SessionUtil.getUserInfo());
		userMap=(HashMap<String, Object>)hmap.get("GSS");
		
		String subGubun=hmap.get("SUB_GUBUN").toString();
		
		
		//검사등록 벨리데이션체크
		//validateRequest(hmap);
		
		saveMap.put("IPNT_NO", hmap.get("IPNT_NO"));
		saveMap.put("IPNT_UPPER_SEQ", hmap.get("IPNT_UPPER_SEQ"));
		saveMap.put("USER_ID", hmap.get("USER_ID"));
		saveMap.put("USE_POINT",hmap.get("USE_POINT"));
		saveMap.put("DISTANCE",hmap.get("DISTANCE"));
		saveMap.put("USER_ADDRESS",hmap.get("USER_ADDRESS"));
		saveMap.put("USER_DETAIL_ADDRESS",hmap.get("USER_DETAIL_ADDRESS"));
		saveMap.put("USER_ADDRESS_LATITUDE",hmap.get("USER_ADDRESS_LATITUDE"));
		saveMap.put("USER_ADDRESS_LONGITUDE",hmap.get("USER_ADDRESS_LONGITUDE"));
		saveMap.put("USE_YN","Y");
		saveMap.put("IP_ADDR",ipaddr);
		saveMap.put("USER_AGENT",userAgent);
		saveMap.put("CHAPERON_YN","Y");
        
		int cntPerformance =0;
		if("KADAMS".equals(subGubun)) {
			cntPerformance =  (Integer) cmmnDao.select("k01.dopingsubscription.countK03_DOPING_INSPECT_DCOForPerformance", hmap);
		}else {
			cntPerformance = (Integer) cmmnDao.select("k01.dopingsubscriptionadtis.countIPNT07TBForPerformance", hmap);
		}
		
		saveMap.put("PERFORMANCE_COUNT",String.valueOf(cntPerformance));
		saveMap.put("PERFORMANCE_POINT",String.valueOf((50 - (cntPerformance * 2)) <= 0 ? 0 : 50 - (cntPerformance * 2)));
		
		int cntChaperon =0;
		if("KADAMS".equals(subGubun)) {
			cntChaperon =  (Integer) cmmnDao.select("k01.dopingsubscription.countK03_DOPING_INSPECT_DCOForChaperon", hmap);
		}else {
			cntChaperon = (Integer) cmmnDao.select("k01.dopingsubscriptionadtis.countIPNT07TBForChaperon", hmap);
		}
		
		saveMap.put("CHAPERON_COUNT",cntChaperon);
		
        
        
		//검사공지신청 등록
		if("KADAMS".equals(subGubun)) {
			cmmnDao.insert("k01.dopingsubscription.insertK03_DOPING_INSPECT_DCO", saveMap);
		}else {
			cmmnDao.insert("k01.dopingsubscriptionadtis.insertIPNT07TB", saveMap);
				
		}
		
	} 
	
	
	@Override
	public void validateRequest(HashMap<String, Object> hmap) throws Exception {
		
		//checkMenuAuth();

		HashMap<String, Object> data= new HashMap<>();
		
		String USER_ADDRESS_LATITUDE1 =(String)hmap.get("USER_ADDRESS_LATITUDE1");

        if (USER_ADDRESS_LATITUDE1 == null || "".equals(USER_ADDRESS_LATITUDE1)) {
        	throw new RuntimeException("회원정보 변경에서 주소를 정상적으로 변경해주셔야 신청이 가능 합니다. 거리오류시엔 선정이 안될수도 있습니다.");
        }

        
        
        data=(HashMap<String, Object>)cmmnDao.select("k01.dopingsubscription.selectK03_DOPING_INSPECT_DCOByPK",hmap);

        if (data != null && data.size() > 0) {
        	throw new RuntimeException("신청자님은 [" + data.get("REQUEST_DATE") +  "] 에 이미 신청하셨습니다.");
        }

        data = (HashMap<String, Object>)cmmnDao.select("k01.dopingsubscription.selectMILIGEByPK",hmap); 
        if (data != null && Integer.parseInt((String)data.get("USED_POINT")) > 0 && Integer.parseInt((String)data.get("USED_POINT")) > 0) {
        	throw new RuntimeException("신청자님은 포인트를 사용하실 수 없습니다.");
        }

/*
        IPER01TB iper01TB = new IPER01TB();
        iper01TB.setUSER_ID(user01TB.getUSER_ID());
        iper01TB.setIPNT_UPPER_SEQ(box.get("IPNT_UPPER_SEQ"));
        iper01TB.setIPNT_NO(box.get("IPNT_NO"));
        List<IPER01TB> lstIPER01TB = kadaInspectService.select0101IPER01TB(iper01TB);
        if (lstIPER01TB != null && lstIPER01TB.size() > 0) {
            validateObject.put("CODE", "98");
            validateObject.put("MESSAGE", "신청자님은 " + Util.date2split2(lstIPER01TB.get(0).getIPNT_START_DATE()) + "~" + Util.date2split2(lstIPER01TB.get(0).getIPNT_END_DATE()) + "까지 " + lstIPER01TB.get(0).getIPNT_NAME() + " 의" + lstIPER01TB.get(0).getMISTAKE_UPPER_NAME() + " " + lstIPER01TB.get(0).getMISTAKE_NAME() + " 중과실로 인해 신청할 수 없습니다. \n문의사항은 한국도핑방지원회 도핑검사부로 해주시기 바랍니다.(☎ 02-2045-9800)");
        }

        USER13TB user13TB = new USER13TB();
        user13TB.setUSER_ID(user01TB.getUSER_ID());
        List<USER13TB> selectedUSER13TB = getKadaUserService().selectUSER13TB(user13TB);
        if (selectedUSER13TB != null && selectedUSER13TB.size() > 0) {
        	for(int i = 0; selectedUSER13TB != null && i < selectedUSER13TB.size(); i++){
        		if(box.get("IPNT_EVENT_CODE").equals(selectedUSER13TB.get(i).getEVENT_CODE())){
        			validateObject.put("CODE", "95");
                	validateObject.put("MESSAGE", "유관종목 검사이므로 신청이 불가합니다.");
        		}
        	}
        }
        
		if(hMap.get("DETAILS") != null) {
			
		}else {
			throw new RuntimeException("1.활동신청공지2 데이터가 입력되지 않았습니다.");
		}*/
	}
	
	@Override
	public void saveDopingSubscriptionRegCancle( HashMap<String, Object> hmap,String ipaddr,String userAgent) throws Exception {
		
		
		hmap.put("USER_AGENT",userAgent);
		hmap.put("IP_ADDR", ipaddr);
		hmap.put("GSS", SessionUtil.getUserInfo());
		//취소시벨리데이션추거ㅏ
		String subGubun=hmap.get("SUB_GUBUN").toString();
		
		//검사공지신청취소 
		if("KADAMS".equals(subGubun)) {
			cmmnDao.delete("k01.dopingsubscription.deleteRequestK03_DOPING_INSPECT_DCO", hmap);
		}else {
			cmmnDao.delete("k01.dopingsubscriptionadtis.deleteRequestIPNT07TB", hmap);
				
		}
		
		//검사공지희망권사용취소 
		if("KADAMS".equals(subGubun)) {
			cmmnDao.delete("k01.dopingsubscription.deleteRequestK03_DOPING_INSPECT_HOPEUSE", hmap);
		}else {
			cmmnDao.delete("k01.dopingsubscriptionadtis.deleteRequestIPNT15TB", hmap);
				
		}
		
			
		
	} 
	
}

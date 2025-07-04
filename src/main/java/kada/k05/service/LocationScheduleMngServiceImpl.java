package kada.k05.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;
import ever.support.session.SessionUtil;
import kada.util.Util;

@Service
public class LocationScheduleMngServiceImpl implements LocationScheduleMngService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public int saveLocationScheduleMngList(List<Map<String, Object>> hList) throws Exception {
		
		int errCnt=0;
		int seq=0;
		
		if(hList.size() > 0 ) {
            List<Map<String, Object>> saveList = hList;
            
            HashMap<String, Object> delMap = new HashMap<>();
            delMap.put("GSS", SessionUtil.getUserInfo());
            cmmnDao.delete("k05.LocationSchedule.deletePLAYER_LOC_SCHE_ERR", delMap); // 이전 에러내역삭제
            
            HashMap<String,Object> user = new HashMap<>();
    		user.put("USER_ID", Util.getUserId());
    		HashMap<String, Object> clubCode = (HashMap<String, Object>) cmmnDao.select("k05.LocationSchedule.selectClubCode", user);
            
            for(Map<String,Object>saveData : saveList) {
                HashMap<String, Object> sData = (HashMap<String, Object>) saveData;
                sData.put("GSS", SessionUtil.getUserInfo());
                sData.put("PRO_ORGZ", clubCode.get("PRO_ORGZ"));
            	sData.put("CLUB_CODE", clubCode.get("CLUB_CODE"));
                                
        		List<Object> checkList = cmmnDao.selectList("k05.LocationSchedule.selectTermCheckByClub", sData);
        		if(checkList != null && checkList.size() > 0) {
        			sData.put("NO", ++seq);
        			sData.put("GUBUN", "1");
        			sData.put("ERR_REASON", "겹치는 일정 존재");
        			cmmnDao.insert("k05.LocationSchedule.insertCLUB_LOC_SCHE_ERR", sData);
        			for(Object row : checkList) {
        				HashMap<String,Object> checkRow = (HashMap<String, Object>) row;
        				checkRow.put("GSS", SessionUtil.getUserInfo());
        				checkRow.put("NO", ++seq);
        				checkRow.put("GUBUN", "2");
        				checkRow.put("PRO_ORGZ", clubCode.get("PRO_ORGZ"));
        				checkRow.put("CLUB_CODE", clubCode.get("CLUB_CODE"));        				 
        				cmmnDao.insert("k05.LocationSchedule.insertCLUB_LOC_SCHE_ERR", checkRow);
        			}        			
        			errCnt++;        			
        		}else {
        			cmmnDao.insert("k05.LocationSchedule.insertK03_CLUB_LOC_SCHEDULE", sData);
        		}
        		
		    }
		}
		
		return errCnt;
	}

	@Override
	public void saveLocationScheduleMng(HashMap<String, Object> hMap) throws Exception {
		hMap.put("GSS", SessionUtil.getUserInfo());
		
		String mode = Util.getValue("MODE", hMap);		
		HashMap<String,Object> user = new HashMap<>();
		user.put("USER_ID", Util.getUserId());
		HashMap<String, Object> clubCode = (HashMap<String, Object>) cmmnDao.select("k05.LocationSchedule.selectClubCode", user);
		hMap.put("PRO_ORGZ", clubCode.get("PRO_ORGZ"));
		hMap.put("CLUB_CODE", clubCode.get("CLUB_CODE"));
				
		if("ADD".equals(mode)) {
			List<HashMap<String,Object>> clubGroups = (List<HashMap<String, Object>>) hMap.get("CLUB_GROUP");
			for(HashMap<String,Object> group : clubGroups) {
				hMap.put("CLUB_GROUP", group.get("code"));				
				cmmnDao.update("k05.LocationSchedule.insertK03_CLUB_LOC_SCHEDULE", hMap);
			}	
			
		}else if("UPDATE".equals(mode)) {
			cmmnDao.update("k05.LocationSchedule.updateK03_CLUB_LOC_SCHEDULE", hMap);	
		}else if("DELETE".equals(mode)) {
			cmmnDao.update("k05.LocationSchedule.deleteK03_CLUB_LOC_SCHEDULE", hMap);	
		}			
	}

	@Override
	public int saveRepeatLocationScheduleMng(HashMap<String, Object> hMap) throws Exception {
		hMap.put("GSS", SessionUtil.getUserInfo());
		
		List<HashMap<String,Object>> clubGroups = (List<HashMap<String, Object>>) hMap.get("CLUB_GROUP");
		for(HashMap<String,Object> group : clubGroups) {
			hMap.put("CLUB_GROUP", group.get("code"));				
			cmmnDao.update("k05.LocationSchedule.insertRepeatLocationSchedule", hMap);		
		}
		return 0;
	}

	@Override
	public void deleteLocationScheduleMng(HashMap<String, Object> hMap) throws Exception {
		List<HashMap<String,Object>> chkSeqs = (List<HashMap<String, Object>>) hMap.get("CHK_SEQ");
		for(HashMap<String,Object> row : chkSeqs) {			
			cmmnDao.update("k05.LocationSchedule.deleteK03_CLUB_LOC_SCHEDULE", row);
		}
	}
	
	
}

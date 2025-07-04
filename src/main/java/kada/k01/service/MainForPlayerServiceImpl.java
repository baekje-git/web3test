package kada.k01.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.session.SessionUtil;
import kada.util.Util;

@Service
public class MainForPlayerServiceImpl implements MainForPlayerService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public int insertRepeatSchedule(HashMap<String, Object> hMap) throws Exception {
		hMap.put("GSS", SessionUtil.getUserInfo());
				 
		HashMap<String,Object> user = new HashMap<>();
		user.put("USER_ID", Util.getUserId());
		HashMap<String, Object> clubCode = (HashMap<String, Object>) cmmnDao.select("k05.LocationSchedule.selectClubCode", user);
		hMap.put("PRO_ORGZ", clubCode.get("PRO_ORGZ"));
		hMap.put("CLUB_CODE", clubCode.get("CLUB_CODE"));
		
		List<HashMap<String,Object>> clubGroups = (List<HashMap<String, Object>>) hMap.get("CLUB_GROUP");
		for(HashMap<String,Object> group : clubGroups) {
			hMap.put("CLUB_GROUP", group.get("code"));
			cmmnDao.update("k01.main.player.insertRepeatSchedule", hMap);
		}
				
		return 0;
	}

	@Override
	public int insertSchedule(HashMap<String, Object> hMap) throws Exception {
		hMap.put("GSS", SessionUtil.getUserInfo());
		
		HashMap<String,Object> user = new HashMap<>();
		user.put("USER_ID", Util.getUserId());
		HashMap<String, Object> clubCode = (HashMap<String, Object>) cmmnDao.select("k05.LocationSchedule.selectClubCode", user);
		hMap.put("PRO_ORGZ", clubCode.get("PRO_ORGZ"));
		hMap.put("CLUB_CODE", clubCode.get("CLUB_CODE"));
		
		List<HashMap<String,Object>> clubGroups = (List<HashMap<String, Object>>) hMap.get("CLUB_GROUP");
		for(HashMap<String,Object> group : clubGroups) {
			hMap.put("CLUB_GROUP", group.get("code"));
			cmmnDao.insert("k01.main.player.insertSchedule", hMap);
		}		
		
		return 0;
	}

}

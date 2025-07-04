package kada.k03.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;
import ever.support.session.SessionUtil;
import kada.util.Util;

@Service
public class ProClubMngServiceImpl implements ProClubMngService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public void saveClubGroup(HashMap<String, Object> hMap) throws Exception {
		List<HashMap<String, Object>> hList = (List<HashMap<String, Object>>) hMap.get("CLUB_GROUPS");
		String clubCd = Util.getValue("CLUB_CD", hMap);
		
		for(HashMap<String, Object> row : hList) {
			String mode = Util.getValue("ROW_STATUS", row);
			row.put("GSS", SessionUtil.getUserInfo());
			row.put("CLUB_CD", clubCd);
			
			if("ADD".equals(mode)) {
				cmmnDao.insert("k03.proclubmng.insertClubGroup", row);			
			}else if("DELETE".equals(mode)) {
				cmmnDao.update("k03.proclubmng.deleteClubGroup", row);
			}else {
				cmmnDao.update("k03.proclubmng.updateClubGroup", row);
			}	
		}
	}

	
}

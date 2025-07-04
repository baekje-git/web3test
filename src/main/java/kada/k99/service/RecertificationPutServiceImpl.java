package kada.k99.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;
import ever.support.session.SessionUtil;

@Service
public class RecertificationPutServiceImpl implements RecertificationPutService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public void updateSelectedReauthConfirm(HashMap<String, Object> hMap) throws Exception {
		List<String> reauthIds = (List<String>) hMap.get("REAUTH_IDS");
		
		for(String id : reauthIds) {
			HashMap<String,Object> row = new HashMap<>();
			row.put("GSS", SessionUtil.getUserInfo());
			row.put("REAUTH_ID", id);
			row.put("REAUTH_SEQ", hMap.get("REAUTH_SEQ"));
			row.put("CONFIRM_STATUS", hMap.get("CONFIRM_STATUS"));
			cmmnDao.update("k99.recertification.changeConfirmREAUTH02TB", row);			
		}
	}

}

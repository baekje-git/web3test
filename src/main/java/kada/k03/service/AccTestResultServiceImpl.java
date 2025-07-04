package kada.k03.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import kada.util.Util;

@Service
public class AccTestResultServiceImpl implements AccTestResultService {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Override
	public void saveUnitAmout(List<HashMap<String, Object>> hList) throws Exception {
		for(HashMap<String, Object> row : hList) {
			cmmnDao.update("k03.acctestresult.update0515IPDN04TB", row);
		}
	}

	@Override
	public void saveLabBalance(HashMap<String, Object> hMap) throws Exception {
		String labBalance = Util.getValue("LAB_BALANCE", hMap);
		List<HashMap<String,Object>> hList = (List<HashMap<String, Object>>) hMap.get("ITEMS");
		
		for(HashMap<String, Object> row : hList) {
			row.put("LAB_BALANCE", labBalance);
			cmmnDao.update("k03.acctestresult.update0315IPNT01TB", row);
		}
	}

}

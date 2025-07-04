package kada.k99.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;

@Service
public class CertiNumberServiceImpl implements CertiNumberService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public void saveCertiNumberReq( HashMap<String, Object> hList) throws Exception {
		
		List<HashMap<String, Object>> certinumberlist= (List<HashMap<String, Object>>)hList.get("certinumberlist");
		for(HashMap<String, Object> row : certinumberlist) {
			cmmnDao.insert("k99.certi.insertAUTH_STAGE", row);
        }
		
	}
}
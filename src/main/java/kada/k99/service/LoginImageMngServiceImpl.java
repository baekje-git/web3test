package kada.k99.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;

@Service
public class LoginImageMngServiceImpl implements LoginImageMngService {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public void selectedLoginBgImage(HashMap<String, Object> hMap) throws Exception {
		cmmnDao.update("k99.loginImageMng.updateResetAll", hMap);
		cmmnDao.update("k99.loginImageMng.updateBgImage", hMap);
	}

}

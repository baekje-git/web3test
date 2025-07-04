package kada.k03.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import ever.cmmn.dao.CmmnDao;
import ever.support.exception.CustomException;
import ever.support.file.FileUtil;
import ever.support.session.SessionUtil;
import kada.util.Util;

@Service
public class AllowPayMgmtServiceImpl implements AllowPayMgmtService {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Override
	public void saveAllowPayMgmt(HashMap<String, Object> hMap) throws Exception {
		
		if(hMap.get("PAYLIST") != null) {
			List<HashMap<String,Object>> payDetails = (List<HashMap<String, Object>>) hMap.get("PAYLIST");
			
			
			for(HashMap<String,Object> row : payDetails) {
				row.put("GSS", SessionUtil.getUserInfo());
				cmmnDao.update("k03.allowpay.updateAllowPayMgmt", row);
			}
			
			
		}else {
			throw new RuntimeException("수당지급 변경 데이터가 입력되지 않았습니다.");
		}
		
		
	}

	
	
}

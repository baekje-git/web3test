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
public class CodeServiceImpl implements CodeService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public void saveCodeDetail(List<HashMap<String, Object>> hList) throws Exception {
		for(HashMap<String, Object> row : hList) {
			String status = String.valueOf(row.get(ConstantProp.GV_ROW_STATUS_COLUMN));
			
			logger.info("row==> {}",row);
			if(ConstantProp.GV_ROW_STATUS_ADD.equals(status)) {
				cmmnDao.insert("k99.code.insertCodeDetail", row); 
			}else if(ConstantProp.GV_ROW_STATUS_UPDATE.equals(status)) {
				cmmnDao.update("k99.code.updateCodeDetail", row);
			}else if(ConstantProp.GV_ROW_STATUS_DELETE.equals(status)) {
				cmmnDao.delete("k99.code.deleteCodeDetail", row);
			}
		}
	}

}

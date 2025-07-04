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
import ever.support.ConstantProp;
import ever.support.exception.CustomException;
import ever.support.file.FileUtil;
import ever.support.session.SessionUtil;
import kada.util.Util;

@Service
public class InspMutualEvalMgmtServiceImpl implements InspMutualEvalMgmtService {
final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	
	
	@Override
	public void savesaveEvalutionMngDetail(List<HashMap<String, Object>> hList) throws Exception {
		for(HashMap<String, Object> row : hList) {
			String status = String.valueOf(row.get(ConstantProp.GV_ROW_STATUS_COLUMN));
			row.put("GSS", SessionUtil.getUserInfo());
			logger.info("row==> {}",row);
			if(ConstantProp.GV_ROW_STATUS_ADD.equals(status)) {
				cmmnDao.insert("k03.InspMutual.insertInspMutualEvalDetail", row); 
			}else if(ConstantProp.GV_ROW_STATUS_UPDATE.equals(status)) {
				cmmnDao.update("k03.InspMutual.updateInspMutualEvalDetail", row);
			}else if(ConstantProp.GV_ROW_STATUS_DELETE.equals(status)) {
				cmmnDao.delete("k03.InspMutual.deleteInspMutualEvalDetail", row);
			}
		}
	}
	
	
}

package ever.sample.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.cmmn.dao.CmmnDao;
import ever.cmmn.dao.CmmnDynamicDao;
import ever.cmmn.service.CmmnService;
import ever.support.ConstantProp;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;


@Service
public class SampleServiceImpl implements SampleService{
    
    private static final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);
    
    @Resource(name="cmmnDao")
    private CmmnDao cmmnDao;
    
    @Autowired
    private CmmnService cmmnService;

    @Resource(name="cmmnDynamicDao")
    private CmmnDynamicDao cmmnDynamicDao;
    
    @Override
    public void save1() throws Exception {
        cmmnDao.insert("sample.insertError1", null); //테이블1
        
        int error = 0 / 0; // 강제 오류발생
        
        cmmnDao.insert("sample.insertError2", null); //테이블2
    }
    
    @Override
    public void save2() throws Exception {
        cmmnDao.insert("sample.insertError1", null); //테이블1
        
        int error = 0 / 0; // 강제오류발생
        
        cmmnService.insert("sample.insertError2", null); //테이블2
    }

    public void saveDynamic(HashMap hMap) throws Exception {
        
        cmmnService.insertDynamic(Gv.DYNAMIC_DB_CODEGROUP_KEY + "BASIC" , "cmmnCode.insertCodeGroup" , hMap);

        int error = 0 / 0; // 강제 오류발생
        
        cmmnService.insertDynamic(Gv.DYNAMIC_DB_CODEGROUP_KEY + "BASIC" , "cmmnCode.insertCodeGroup" , hMap);
    }

    public void saveBatchDynamic(List<HashMap> listMap) throws Exception{
        int i = 0;
        try {
            for(HashMap hMap : listMap) {
                if(i == 0) {
                    cmmnDynamicDao.insert(Gv.DYNAMIC_DB_CODEGROUP_KEY + "BASIC" , "cmmnCode.insertCodeGroup" , hMap);
                }
                cmmnDynamicDao.insert(null , "cmmnCode.insertCodeGroup" , hMap);
                i++;
            }
        }
        catch(Exception e) {
            cmmnDynamicDao.rollback();
        }
        finally {
            cmmnDynamicDao.endBatch();
            cmmnDynamicDao.endTransaction();
        }
    }

    @Override
    public void saveEmp(HashMap<String, Object> hMap) throws Exception {
        List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) hMap.get("gridData");
        
        if(dataList.size() > 0) {
            for (HashMap<String, Object> dataMap : dataList) {
                //dataMap.put("GSS",hMap.get("GSS")); //로그인정보                     
                if(ConstantProp.GV_ROW_STATUS_ADD.equals(dataMap.get(ConstantProp.GV_ROW_STATUS_COLUMN))) {
                    cmmnDao.insert("sample2.insertEmp", dataMap);    
                }else if(ConstantProp.GV_ROW_STATUS_UPDATE.equals(dataMap.get(ConstantProp.GV_ROW_STATUS_COLUMN))) {
                    cmmnDao.update("sample2.updateEmp", dataMap);
                }else if(ConstantProp.GV_ROW_STATUS_DELETE.equals(dataMap.get(ConstantProp.GV_ROW_STATUS_COLUMN))) {
                    cmmnDao.delete("sample2.deleteEmp", dataMap);
                }
            }
        }     
    }

	@Override
	public void saveCODE00TB(HashMap<String, Object> hMap) throws Exception {
		cmmnDao.update("sample.saveCODE00TB", hMap);		
	}

	@Override
	public void saveCODE01TB(HashMap<String, Object> hMap) throws Exception {
		cmmnDao.update("sample.saveCODE01TB", hMap);		
	}

	@Override
	public void saveCODE01TB_2(List<HashMap<String, Object>> hMap) throws Exception {
		for(HashMap<String, Object> row : hMap) {
			String status = String.valueOf(row.get("_STATUS"));
			logger.info("row==> {}",row);
			if("I".equals(status)) {
				cmmnDao.insert("sample.insertCODE01TB", row); 
			}else if("U".equals(status)) {
				cmmnDao.update("sample.updateCODE01TB", row);
			}else if("D".equals(status)) {
				cmmnDao.delete("sample.deleteCODE01TB", row);
			}
		}
	}

    
}

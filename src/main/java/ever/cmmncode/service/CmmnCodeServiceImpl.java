package ever.cmmncode.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.Gv;


@Service
public class CmmnCodeServiceImpl implements CmmnCodeService{
    
    private static final Logger logger = LoggerFactory.getLogger(CmmnCodeServiceImpl.class);
    
    @Resource(name="cmmnDao")
    private CmmnDao cmmnDao;


    
    @Override
    public Map getCodeGroupList(HashMap<String, Object> parameter) throws Exception {
        HashMap resultMap = new HashMap();
        
        List codeGroupList = cmmnDao.selectList("cmmnCode.getCodeGroupList" , parameter);
        int groupListCount = 0;
        
        if ( codeGroupList.size() > 0 ) {
            groupListCount = codeGroupList.size();
        }
        
        resultMap.put("codeGroupList", codeGroupList);        
        resultMap.put("groupListCount", groupListCount);
        
        
        return resultMap;
    }    
    
    
    
   
    @Override
    public Map getCodeList(HashMap<String, Object> parameter) throws Exception {
        HashMap resultMap = new HashMap();        
        
        List codeList = cmmnDao.selectList("cmmnCode.getCodeList" , parameter) ;        
        int codeListCount = 0;
        
        if ( codeList.size() > 0 ) {
            codeListCount = codeList.size();
        }
        resultMap.put("codeList", codeList);
        resultMap.put("codeListCount", codeListCount);
        
        return resultMap;      
    }

    
    @Override
    public void insertCodeGroup(HashMap<String, Object> parameter) throws Exception {
        cmmnDao.insert("cmmnCode.insertCodeGroup", parameter);
    }




    @Override
    public void updateCodeGroup(HashMap<String, Object> parameter) throws Exception {
        cmmnDao.update("cmmnCode.updateCodeGroup", parameter);
    }




    @Override
    public void deleteCodeGroup(HashMap<String, Object> parameter) throws Exception {
        
        int cnt = (int) cmmnDao.select("cmmnCode.getCodeCountForDelete", parameter);
        
        if ( cnt > 0 ) {
            cmmnDao.update("cmmnCode.deleteCodeGroupAndCode", parameter);
        }
        
        cmmnDao.update("cmmnCode.deleteCodeGroup", parameter);
    }




    @Override
    public void insertCode(HashMap<String, Object> parameter) throws Exception {
        cmmnDao.insert("cmmnCode.insertCode", parameter);
    }




    @Override
    public void updateCode(HashMap<String, Object> parameter) throws Exception {
        cmmnDao.update("cmmnCode.updateCode", parameter);
    }




    @Override
    public void deleteCode(HashMap<String, Object> parameter) throws Exception {
        cmmnDao.update("cmmnCode.deleteCode", parameter);
    }

        
    

    /*
    @Override
    public int insert(String queryId, HashMap<String, Object> hMap) throws Exception {
        return cmmnDao.insert(queryId, hMap);
    }*/
     
}

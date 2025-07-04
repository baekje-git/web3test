package ever.dbsession.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.cmmncode.service.CmmnCodeServiceImpl;

@Service
public class DbSessionServiceImpl implements DbSessionService{
    
    private static final Logger logger = LoggerFactory.getLogger(CmmnCodeServiceImpl.class);
    
    @Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

    @Override
    public Map getConnectionSessionList(HashMap<String, Object> parameter) throws Exception {
        HashMap resultMap = new HashMap();  
        List<Object> connectionInfoList = (List<Object>)cmmnDao.selectList("dbSession.getConnectionInfoList" , parameter);
        
        for(Object rowMap : connectionInfoList) {
            Map row = (Map)rowMap;
            
            String loginId = row.get("login_id").toString();
            
            HashMap<String, Object> prm = new HashMap();
            prm.put("LOGIN_ID" , loginId);
            
            List<Object> connectionDetailInfoList = (List<Object>)cmmnDao.selectList("dbSession.getConnectionDetailInfoList" , prm);
            
            row.put("connectionDetailInfoList" , connectionDetailInfoList);        
        }
        
        resultMap.put("connectionInfoList" , connectionInfoList);
        return resultMap;
    }

    @Override
    public Map connectionSessionRegist(HashMap<String, Object> parameter) throws Exception {
        HashMap resultMap = new HashMap();        

                
        //1. 기존 레코드 삭제 
        int resultCnt = cmmnDao.delete("dbSession.deleteSessionMaster", parameter);
        int resultCnt2 = cmmnDao.delete("dbSession.deleteSessionDetail", parameter);
        
        //2. 신규 등록 
        int resultCnt3 = cmmnDao.insert("dbSession.insertSessionMaster", parameter);
        
        for (Map.Entry<String, Object> entry: parameter.entrySet()) {
            String sessionKey = entry.getKey() ;
            String sessionValue = String.valueOf(entry.getValue());
            if(sessionValue ==null) sessionValue = "";
            
            if( !sessionKey.contains("USER_PC_IP") && !sessionKey.contains("ADD_SESSION_ENDTIME")) {
                HashMap<String, Object> param = new HashMap();
                
                param.put("LOGIN_ID", parameter.get("LOGIN_ID").toString());
                param.put("SESSION_KEY", sessionKey);
                param.put("SESSION_VALUE", sessionValue);
                int resultCnt4 = cmmnDao.insert("dbSession.insertSessionDetail", param);
            }
        }

        resultMap.put("result", "OK");        
        
        return resultMap;      
    }

    @Override
    public Map getSessionMasterBySessionId(HashMap<String, Object> parameter) throws Exception {
        HashMap resultMap = new HashMap();  
        List<Object> sessionMasterList = (List<Object>)cmmnDao.selectList("dbSession.getSessionMasterBySessionId" , parameter);
         
        resultMap.put("sessionMasterList" , sessionMasterList);
        return resultMap;
    }

}

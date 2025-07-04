package ever.dbsession.service;

import java.util.HashMap;
import java.util.Map;

public interface DbSessionService {
     
    
    
    
    /**
     * 
     * <pre>
     * @FileName : getConnectionSessionList
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : 세션 연결정보 조회 
     * @param queryId
     * @param hMap
     * @return 
     * @throws Exception
     * </pre>
     */
    public Map getConnectionSessionList(HashMap<String, Object> parameter) throws Exception;    
     
 
    
    /**
     * 
     * <pre>
     * @FileName : connectionSessionRegist
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : 로그인 접속 정보 DB 저장 처리  
     * @param queryId
     * @param hMap
     * @return 
     * @throws Exception
     * </pre>
     */
    public Map connectionSessionRegist(HashMap<String, Object> parameter) throws Exception;        
     
    
    /**
     * 
     * <pre>
     * @FileName : getSessionMasterBySessionId
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : sessionId를 통해 세션 마스터 정보 조회 
     * @param queryId
     * @param hMap
     * @return 
     * @throws Exception
     * </pre>
     */
    public Map getSessionMasterBySessionId(HashMap<String, Object> parameter) throws Exception;     
}

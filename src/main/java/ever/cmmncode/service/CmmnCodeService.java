package ever.cmmncode.service;

import java.util.HashMap;
import java.util.Map;

public interface CmmnCodeService {
     
    
    
    
    /**
     * 
     * <pre>
     * @FileName : getCodeGroupList
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : 공통코드 조회 
     * @param queryId
     * @param hMap
     * @return 
     * @throws Exception
     * </pre>
     */
    public Map getCodeGroupList(HashMap<String, Object> parameter) throws Exception;    
    
    
    /**
     * 
     * <pre>
     * @FileName : getCodeList
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : 공통코드 조회 
     * @param queryId
     * @param hMap
     * @return
     * @throws Exception
     * </pre>
     */
    public Map getCodeList(HashMap<String, Object> parameter) throws Exception;

    /**
     * 
     * <pre>
     * @FileName : insertCodeGroup
     * @Date     : 2020. 05. 27. 오후 7:14:02
     * @Writer   : NEU-T
     * @Description : 공통코드 등록
     * @param queryId
     * @param hMap
     * @return
     * @throws Exception
     * </pre>
     */
    public void insertCodeGroup(HashMap<String, Object> parameter) throws Exception;    
    
    
    /**
     * 
     * <pre>
     * @FileName : updateCodeGroup
     * @Date     : 2020. 05. 27. 오후 7:14:02
     * @Writer   : NEU-T
     * @Description : 공통코드 수정
     * @param queryId
     * @param hMap
     * @return
     * @throws Exception deleteCodeGroup
     * </pre>
     */
    public void updateCodeGroup(HashMap<String, Object> parameter) throws Exception;    
    
    
    /**
     * 
     * <pre>
     * @FileName : deleteCodeGroup
     * @Date     : 2020. 05. 27. 오후 7:14:02
     * @Writer   : NEU-T
     * @Description : 공통코드 삭제
     * @param queryId
     * @param hMap
     * @return
     * @throws Exception deleteCodeGroup
     * </pre>
     */
    public void deleteCodeGroup(HashMap<String, Object> parameter) throws Exception;  
    
    
    /**
     * 
     * <pre>
     * @FileName : insertCode
     * @Date     : 2020. 05. 27. 오후 7:14:02
     * @Writer   : NEU-T
     * @Description : 코드 등록
     * @param queryId
     * @param hMap
     * @return
     * @throws Exception
     * </pre>
     */
    public void insertCode(HashMap<String, Object> parameter) throws Exception;    
    
    
    /**
     * 
     * <pre>
     * @FileName : updateCode
     * @Date     : 2020. 05. 27. 오후 7:14:02
     * @Writer   : NEU-T
     * @Description : 코드 수정
     * @param queryId
     * @param hMap
     * @return
     * @throws Exception deleteCodeGroup
     * </pre>
     */
    public void updateCode(HashMap<String, Object> parameter) throws Exception;    
    
    
    /**
     * 
     * <pre>
     * @FileName : insertCode
     * @Date     : 2020. 05. 27. 오후 7:14:02
     * @Writer   : NEU-T
     * @Description : 코드 삭제
     * @param queryId
     * @param hMap
     * @return
     * @throws Exception deleteCodeGroup
     * </pre>
     */
    public void deleteCode(HashMap<String, Object> parameter) throws Exception;  
    
    
     
}

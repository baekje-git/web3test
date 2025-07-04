package ever.cmmncode.service;

import java.util.List;

import ever.cmmncode.domain.CacheCode;
import ever.cmmncode.domain.CacheCodeGroup;
 
public interface CodeCacheService {
     
    
    
    
    /**
     * 
     * <pre>
     * @FileName : getCodeGroupList
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : 캐시 코드그룹 조회
     * @param queryId
     * @param hMap
     * @return 
     * @throws Exception
     * </pre>
     */
    public List<CacheCodeGroup> getCacheCodeGroupList() throws Exception;    
    
    
    
    /**
     * 
     * <pre>
     * @FileName : getCodeGroupsByLikeGroupText
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : 캐시 코드그룹 조회 (코드그룹 LIKE검색)
     * @param queryId
     * @param hMap
     * @return 
     * @throws Exception
     * </pre>
     */    
    public List<CacheCodeGroup> getCodeGroupsByLikeGroupText(String groupText) throws Exception;
    
    /**
     * 
     * <pre>
     * @FileName : getCacheCodeList
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : 캐시코드 목록 조회 
     * @param queryId
     * @param hMap
     * @return
     * @throws Exception
     * </pre>
     */
    public List<CacheCode> getCacheCodeListByGroupText(String groupText) throws Exception;    
    
    
    
    /**
     * 
     * <pre>
     * @FileName : getCacheCodeListByGroupText
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : 코드 목록중 키검색으로  값 추출
     * @param queryId
     * @param hMap
     * @return
     * @throws Exception
     * </pre>
     */
    public String getValueByCodeKey(List<CacheCode> cacheCodeList , String codeKey ) throws Exception;
    
    
    /**
     * 
     * <pre>
     * @FileName : refreshAllFromRepository
     * @Date     : 2020. 05. 12. 오후 4:50:02
     * @Writer   : NEU-T
     * @Description : 모든 캐시 정보 갱신
     * @throws Exception
     * </pre>
     */
    public void refreshAllFromRepository() throws Exception;    
}

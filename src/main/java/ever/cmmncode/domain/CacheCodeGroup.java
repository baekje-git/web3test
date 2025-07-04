package ever.cmmncode.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre>
 * ------------------------------------------------------------- 
 * @파일경로 : ever.cmmncode.domain
 * @파일이름 : CodeGroup.java 
 * @작성일자 : 2021. 3. 4. 
 * @작성자명 : Lee seungchan 
 * @상세설명 : 캐시코드그룹 도메인
 * -------------------------------------------------------------
 * </pre>
 */
public class CacheCodeGroup {
    private String groupIdx;
    private String groupTxt;    
    private List<CacheCode> childCacheCodeList = new ArrayList();
    
    public String getGroupIdx() {
        return groupIdx;
    }
    public void setGroupIdx(String groupIdx) {
        this.groupIdx = groupIdx;
    }
    public String getGroupTxt() {
        return groupTxt;
    }
    public void setGroupTxt(String groupTxt) {
        this.groupTxt = groupTxt;
    }
    public List<CacheCode> getChildCacheCodeList() {
        return childCacheCodeList;
    }
    public void setChildCacheCodeList(List<CacheCode> childCacheCodeList) {
        this.childCacheCodeList = childCacheCodeList;
    }
     
    
     
}

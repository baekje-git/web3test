package ever.cmmncode.domain;

/**
 * 
 * <pre>
 * -------------------------------------------------------------
 * @파일경로 : ever.cmmncode.domain
 * @파일이름 : Code.java 
 * @작성일자 : 2020. 5. 17. 
 * @작성자명 : NEU-T 
 * @상세설명 : 캐시코드 도메인
 * -------------------------------------------------------------
 * </pre>
 */
public class CacheCode {
    private CacheCodeGroup parentCacheCodeGroup;
    private String codeValue;
    private String codeText;

    public CacheCodeGroup getParentCacheCodeGroup() {
        return parentCacheCodeGroup;
    }
    public void setPrarentCacheCodeGroup(CacheCodeGroup parentCacheCodeGroup) {
        this.parentCacheCodeGroup = parentCacheCodeGroup;
    }
    public String getCodeValue() {
        return codeValue;
    }
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
    public String getCodeText() {
        return codeText;
    }
    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }
    
   
}

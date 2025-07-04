package ever.cmmncode.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ever.cmmn.dao.CmmnDao;
import ever.cmmncode.domain.CacheCode;
import ever.cmmncode.domain.CacheCodeGroup;
import ever.support.Gv;
import ever.support.file.FileUtil;


@Service
public class CodeCacheServiceImpl implements CodeCacheService{

    private static final Logger logger = LoggerFactory.getLogger(CodeCacheServiceImpl.class);

    @Autowired
    private CmmnCodeService cmmnCodeService;

    private List<CacheCodeGroup> cacheCodeGroupList = new ArrayList();

    @Override
    public List<CacheCodeGroup> getCacheCodeGroupList() throws Exception {
        return this.cacheCodeGroupList;
    }

    @Override
    public List<CacheCodeGroup> getCodeGroupsByLikeGroupText(String groupText) throws Exception {
        List<CacheCodeGroup> list = new ArrayList();
        for(CacheCodeGroup  cacheCodeGroup : this.cacheCodeGroupList) {
            if(cacheCodeGroup.getGroupTxt().contains(groupText))
                list.add(cacheCodeGroup);
        }
        return list;
    }

    @Override
    public List<CacheCode> getCacheCodeListByGroupText(String groupText) throws Exception {
        List<CacheCode> list = new ArrayList();
        for(CacheCodeGroup  cacheCodeGroup : this.cacheCodeGroupList) {
            if(cacheCodeGroup.getGroupTxt().contentEquals(groupText)) {
                return cacheCodeGroup.getChildCacheCodeList();
            }       
        }
        return list;
    }

    @Override
    public String getValueByCodeKey(List<CacheCode> cacheCodeList, String codeKey) throws Exception {
        String value = "";
        for(CacheCode cacheCode : cacheCodeList) {
            if(cacheCode.getCodeText().contentEquals(codeKey)) {
                value = cacheCode.getCodeValue();
                break;
            }
        }
        return value;
    }

    @Override
    public void refreshAllFromRepository() throws Exception {
        HashMap parameter = new HashMap();
        parameter.put("firstNum", "0");
        parameter.put("once_display_count", "99999999999");
        parameter.put("search_use_yn", "Y");

        Map resultMap = cmmnCodeService.getCodeGroupList(parameter);
        List<Map> codeGroupList = (List<Map>)resultMap.get("codeGroupList");

        this.cacheCodeGroupList.clear();         
        
        for(Map row : codeGroupList) {
            
            String groupIdx     = row.get("CD_IDX").toString();
            String groupText    = row.get("CD_IDX_NM").toString();
            
            CacheCodeGroup cacheCodeGroup = new CacheCodeGroup();
            cacheCodeGroup.setGroupIdx(groupIdx);
            cacheCodeGroup.setGroupTxt(groupText);
            
            
            parameter.put("GROUP_IDX", groupIdx);           
            Map resultMap2 = cmmnCodeService.getCodeList(parameter);
            List<Map> codeList = (List<Map>)resultMap2.get("codeList");
            
            for(Map row2 : codeList) {                
                String codeValue    = row2.get("CD_CODE").toString();
                String codeText     = row2.get("CD_NAME").toString();

                CacheCode cacheCode = new CacheCode();                
                cacheCode.setCodeValue(codeValue);
                cacheCode.setCodeText(codeText);
                cacheCode.setPrarentCacheCodeGroup(cacheCodeGroup);
                
                cacheCodeGroup.getChildCacheCodeList().add(cacheCode);
            }
            this.cacheCodeGroupList.add(cacheCodeGroup);
        }
    }

}

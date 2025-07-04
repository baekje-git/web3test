package ever.cmmncode.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ever.cmmn.controller.BaseController;
import ever.cmmncode.domain.CacheCodeGroup;
import ever.cmmncode.service.CmmnCodeService;
import ever.cmmncode.service.CodeCacheService;
import ever.support.ConstantProp;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;

@Controller
@RequestMapping("/cmmnCode/")
public class CmmnCodeController extends BaseController {
	
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CmmnCodeService cmmnCodeService;
    @Autowired
    private CodeCacheService codeCacheService;   
    
    /**
     * 
     * <pre>
     * @MethodName : codeManagement
     * @Date       : 2020. 05. 21. 오후 5:44:45
     * @Writer     : NEU-T
     * @Description :
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="codeManagement.do",  method = RequestMethod.GET)
    public String codeManagement(HttpServletRequest request , Model model) throws Exception{
        CmmnUtil.methodLog("코드 리스트 페이지 ");        
        //HashMap parameter = (HashMap) model.asMap().get("webParameterMap");
        HashMap parameter = getParameterMap();
       
        if(!parameter.containsKey("current_page2") || parameter.get("current_page2") == null){
            parameter.put("current_page2", "1");
        }   
        
        
        model.addAttribute("codeGroupList", cmmnCodeService.getCodeGroupList(parameter));       
                
        //코드리스트에서 코드그룹의 전체목록을 표기하기 위함
        HashMap<String, Object> parameter2 = new HashMap<String, Object>();
        parameter2.put("current_page", parameter.get("current_page"));
        parameter2.put("once_display_count", parameter.get("once_display_count"));
        parameter2.put("firstnum", parameter.get("firstnum"));
        model.addAttribute("codeGroupListAll", cmmnCodeService.getCodeGroupList(parameter2)); //검색 안하는 효과
        
        
        if(parameter.get("GROUP_IDX") == null){
            parameter.put("GROUP_IDX", 0);
        }
        
        Map resultMap3 = cmmnCodeService.getCodeList(parameter);
        model.addAttribute("codeList", resultMap3);
        
        
        return "cmmnCode/codeManagement";
        
    }    
    
    /**
     * 
     * <pre>
     * @MethodName : cacheList
     * @Date       : 2020. 05. 21. 오후 5:44:45
     * @Writer     : NEU-T
     * @Description :
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="cacheList.do",  method = RequestMethod.GET)
    public String cacheList(HttpServletRequest request , Model model) throws Exception{
        CmmnUtil.methodLog("캐시코드 리스트 페이지 ");        
         

        List<CacheCodeGroup> cacheCodeGroupList = codeCacheService.getCacheCodeGroupList();

        
        model.addAttribute("cacheCodeGroupList", cacheCodeGroupList);
        return "cmmnCode/cacheList";
        
    }      
    
    /**
     * 
     * <pre>
     * @MethodName : registCodeGroupProcess
     * @Date       : 2020. 05. 27. 오후 5:44:45
     * @Writer     : NEU-T
     * @Description : registCodeGroupProcess
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="registCodeGroup.do",  method = RequestMethod.POST)
    public String registCodeGroupProcess(HttpServletRequest request , Model model) throws Exception{
        CmmnUtil.methodLog("코드그룹 등록(수정) 처리 ");        
        HashMap parameter = getParameterMap(); //웹파라미터
        
        parameter.put("GROUP_TXT", parameter.get("GROUP_TXT").toString().trim());
                
        if ( parameter.get("GROUP_IDX") == null || parameter.get("GROUP_IDX").toString().equals("") ){
            cmmnCodeService.insertCodeGroup(parameter);
        } else {
            cmmnCodeService.updateCodeGroup(parameter);
        }
        
        
        //캐쉬 리프레쉬
        codeCacheService.refreshAllFromRepository();        
        
        return "redirect:/cmmnCode/codeManagement.do";
    }  
    
    
    /**
     * 
     * <pre>
     * @MethodName : deleteCodeGroupProcess
     * @Date       : 2020. 05. 27. 오후 5:44:45
     * @Writer     : NEU-T
     * @Description : deleteCodeGroup
     * @param request  
     * @return
     * </pre>
     */
    @RequestMapping(value="deleteCodeGroup.do",  method = RequestMethod.GET)
    public String deleteCodeGroupProcess(HttpServletRequest request , Model model) throws Exception{
        CmmnUtil.methodLog("코드그룹 삭제 처리 ");        
        HashMap parameter = getParameterMap(); //웹파라미터
            
        cmmnCodeService.deleteCodeGroup(parameter);
        
        //캐쉬 리프레쉬
        codeCacheService.refreshAllFromRepository();             
        
        return "redirect:/cmmnCode/codeManagement.do";
    }  
    
    
    
    
    /**
     * 
     * <pre>
     * @MethodName : registCodeProcess
     * @Date       : 2020. 05. 27. 오후 5:44:45
     * @Writer     : NEU-T
     * @Description : registCodeProcess
     * @param request 
     * @return
     * </pre>
     */
    @RequestMapping(value="registCode.do",  method = RequestMethod.POST)
    public String registCodeProcess(HttpServletRequest request , Model model) throws Exception{
        CmmnUtil.methodLog("코드그룹 등록(수정) 처리 ");        
        HashMap parameter = getParameterMap(); //웹파라미터
        
        String url = "";
        
        if ( parameter.get("CODE_IDX") == null || parameter.get("CODE_IDX").toString().equals("") ){
            
            url = parameter.get("GROUP_IDX").toString();
            
            cmmnCodeService.insertCode(parameter);
            
        } else {
            
            url = parameter.get("modify_group_idx").toString();
            
            cmmnCodeService.updateCode(parameter);
            
        }
        
        //캐쉬 리프레쉬
        codeCacheService.refreshAllFromRepository();             
        
        return "redirect:/cmmnCode/codeManagement.do?GROUP_IDX=" + url;
    }  
    
    
    /**
     * 
     * <pre>
     * @MethodName : deleteCodeProcess
     * @Date       : 2020. 05. 27. 오후 5:44:45
     * @Writer     : NEU-T
     * @Description : deleteCode
     * @param request  
     * @return
     * </pre>
     */
    @RequestMapping(value="deleteCode.do",  method = RequestMethod.GET)
    public String deleteCodeProcess(HttpServletRequest request , Model model) throws Exception{
        CmmnUtil.methodLog("코드그룹 삭제 처리 ");        
        HashMap parameter = getParameterMap(); //웹파라미터
            
        cmmnCodeService.deleteCode(parameter);
            
        //캐쉬 리프레쉬
        codeCacheService.refreshAllFromRepository();             
        
        return "redirect:/cmmnCode/codeManagement.do";
    } 
    
    
    /**
     * 
     * <pre>
     * @MethodName : saveCode
     * @Date       : 
     * @Writer     : 
     * @Description : 코드저장
     * @param request
     * @return
     * @throws Exception
     * </pre>
     */
    @RequestMapping(value="saveCode.ajax")
    @ResponseBody
    public Object saveCode(HttpServletRequest request) throws Exception {
        HashMap<String, Object> hMap = CmmnUtil.parsingToMap(request.getParameter("paramData"));
        List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) hMap.get("gridData");
        List<HashMap<String, Object>> detailDataList = (List<HashMap<String, Object>>) hMap.get("gridDetail");
        
        //Transaction은 service레벨부터 설정되어있음
        if(dataList.size() > 0) {
            for (HashMap<String, Object> dataMap : dataList) {                
                if(!ConstantProp.GV_ROW_STATUS_DELETE.equals(dataMap.get(ConstantProp.GV_ROW_STATUS_COLUMN).toString())) {
                    dataMap.put("GSS",hMap.get("GSS"));
                    //cmmnService.insert("rss.admin.code.saveCode", dataMap);    
                }
            }
        }
        
        if(detailDataList.size() > 0) {
            for (HashMap<String, Object> dataMap : detailDataList) {                
                if(!ConstantProp.GV_ROW_STATUS_DELETE.equals(dataMap.get(ConstantProp.GV_ROW_STATUS_COLUMN).toString())) {
                    //cmmnService.insert("rss.admin.code.saveCode", dataMap);    
                }
            }
        }

        return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG);
    }    
    
	
}

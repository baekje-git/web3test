package ever.dbsession.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ever.cmmn.controller.BaseController;
import ever.dbsession.service.DbSessionService;
import ever.support.cmmn.CmmnUtil;
import ever.support.session.SessionUtil;

@Controller
@RequestMapping("/dbsession/")
public class DbSessionController extends BaseController {
	
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private DbSessionService dbSessionService;

    /**
     * 
     * <pre>
     * @MethodName : connectionSessionList
     * @Date       : 2020. 05. 21. 오후 5:44:45
     * @Writer     : NEU-T
     * @Description :
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value="connectionSessionList.do",  method = RequestMethod.GET)
    public String connectionSessionList(HttpServletRequest request , Model model) throws Exception{
        CmmnUtil.methodLog("connectionSessionList");       
        HashMap parameter = getParameterMap();
        
        Map resultMap = dbSessionService.getConnectionSessionList(parameter);
        
        // 세션정보가져오기
        HashMap<String, Object> sessionInfo = SessionUtil.getSessionLoginInfo(request.getSession());        
        
        model.addAttribute("connectionInfoList" ,resultMap.get("connectionInfoList"));
        model.addAttribute("sessionInfo" ,sessionInfo);
        
        logger.debug("connectionInfoList=> {}",resultMap.get("connectionInfoList"));
        logger.debug("sessionInfo=> {}",sessionInfo);
        return "dbsession/connectionSessionList";        
    }    
    
     
	
}

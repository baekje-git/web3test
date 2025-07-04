package ever;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ever.support.cmmn.CmmnUtil;

/**
 * 
 * <pre>
 * -------------------------------------------------------------
 * @프로젝트 : ever
 * @파일경로 : ever
 * @파일이름 : IndexController.java 
 * @작성일자 : 2021. 8. 31. 
 * @작성자명 : MSKANG 
 * @상세설명 : 냉무
 * -------------------------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping("/")
public class IndexController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
    @RequestMapping(value = "index.do")
    public String index(Locale locale, Model model) {
        CmmnUtil.methodLog("최초 화면");
        return "index";
    }
    
    @RequestMapping(value = "")
    public String index2(Locale locale, Model model) {
        CmmnUtil.methodLog("최초 화면2");
        return "index";
    }
}

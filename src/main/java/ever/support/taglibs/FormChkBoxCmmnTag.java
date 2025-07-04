 package ever.support.taglibs;
 
 import ever.cmmn.service.CmmnService;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.HashMap;
 import java.util.List;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.tagext.TagSupport;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.lang3.exception.ExceptionUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.web.context.WebApplicationContext;
 import org.springframework.web.context.support.WebApplicationContextUtils;
 
 public class FormChkBoxCmmnTag extends TagSupport {
   private static final long serialVersionUID = -2795813889165550220L;
   
   final Logger logger = LoggerFactory.getLogger(getClass());
   
   String pId = "";
   
   String pNm = "";
   
   String pCodeId = "";
   
   String pClass = "";
   
   String pValue = "";
   
   String pExceptCd = "";
   
   Boolean pAllYn = Boolean.valueOf(true);
   
   Boolean pDisabled = Boolean.valueOf(false);
   
   String pString1 = "";
   
   String pString2 = "";
   
   String pString3 = "";
   
   String pString4 = "";
   
   String pString5 = "";
   
   String pString6 = "";
   
   String pString7 = "";
   
   public String getpId() {
     return this.pId;
   }
   
   public void setpId(String pId) {
     this.pId = pId;
   }
   
   public String getpNm() {
     return this.pNm;
   }
   
   public void setpNm(String pNm) {
     this.pNm = pNm;
   }
   
   public String getpCodeId() {
     return this.pCodeId;
   }
   
   public void setpCodeId(String pCodeId) {
     this.pCodeId = pCodeId;
   }
   
   public String getpClass() {
     return this.pClass;
   }
   
   public void setpClass(String pClass) {
     this.pClass = pClass;
   }
   
   public String getpValue() {
     return this.pValue;
   }
   
   public void setpValue(String pValue) {
     this.pValue = pValue;
   }
   
   public String getpExceptCd() {
     return this.pExceptCd;
   }
   
   public void setpExceptCd(String pExceptCd) {
     this.pExceptCd = pExceptCd;
   }
   
   public Boolean getpAllYn() {
     return this.pAllYn;
   }
   
   public void setpAllYn(Boolean pAllYn) {
     this.pAllYn = pAllYn;
   }
   
   public Boolean getpDisabled() {
     return this.pDisabled;
   }
   
   public void setpDisabled(Boolean pDisabled) {
     this.pDisabled = pDisabled;
   }
   
   public String getpString1() {
     return this.pString1;
   }
   
   public void setpString1(String pString1) {
     this.pString1 = pString1;
   }
   
   public String getpString2() {
     return this.pString2;
   }
   
   public void setpString2(String pString2) {
     this.pString2 = pString2;
   }
   
   public String getpString3() {
     return this.pString3;
   }
   
   public void setpString3(String pString3) {
     this.pString3 = pString3;
   }
   
   public String getpString4() {
     return this.pString4;
   }
   
   public void setpString4(String pString4) {
     this.pString4 = pString4;
   }
   
   public String getpString5() {
     return this.pString5;
   }
   
   public void setpString5(String pString5) {
     this.pString5 = pString5;
   }
   
   public String getpString6() {
     return this.pString6;
   }
   
   public void setpString6(String pString6) {
     this.pString6 = pString6;
   }
   
   public String getpString7() {
     return this.pString7;
   }
   
   public void setpString7(String pString7) {
     this.pString7 = pString7;
   }
   
   public int doStartTag() throws JspException {
     WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
     CmmnService cmmnService = (CmmnService)webApplicationContext.getBean(CmmnService.class);
     List<Object> codeList = new ArrayList();
     HashMap<String, Object> pMap = new HashMap<>();
     StringBuffer check = new StringBuffer();
     try {
       pMap.put("P_CODE", this.pCodeId);
       codeList = cmmnService.selectList("cmmn.selectCmmnCodeList", pMap);
       int idx = 0;
       for (Object codeMap : codeList) {
         idx++;
         String pId = String.valueOf(this.pId) + "_" + idx;
         HashMap<String, Object> data = (HashMap<String, Object>)codeMap;
         if (!StringUtils.isEmpty(this.pExceptCd) && this.pExceptCd.equals(data.get("CODE_ID")))
           continue; 
         if (this.pAllYn.booleanValue()) {
           check.append("<span class=\"board_data_wrap \">");
           check.append("<input type=\"checkbox\" ");
           check.append("id    =\"").append(pId).append("\" ");
           check.append("name  =\"").append(this.pNm).append("\" ");
           check.append("value =\"").append(data.get("CODE_ID")).append("\" ");
           check.append("checked=\"checked\" ");
           if (this.pDisabled.booleanValue())
             check.append("disabled=\"disabled\" "); 
           check.append("/>");
           check.append("<label for=\"").append(pId).append("\"><span></span>").append(data.get("CODE_NM")).append("</label>").append("\r\n");
           check.append("</span>");
           continue;
         } 
         if (StringUtils.isBlank(getpValue())) {
           check.append("<span class=\"board_data_wrap \">");
           check.append("<input type=\"checkbox\" ");
           check.append("id    =\"").append(pId).append("\" ");
           check.append("name  =\"").append(this.pNm).append("\" ");
           check.append("value =\"").append(data.get("CODE_ID")).append("\" ");
           if (idx == 1)
             check.append("checked=\"checked\" "); 
           if (this.pDisabled.booleanValue())
             check.append("disabled=\"disabled\" "); 
           check.append("/>");
           check.append("<label for=\"").append(pId).append("\"><span></span>").append(data.get("CODE_NM")).append("</label>").append("\r\n");
           check.append("</span>");
           continue;
         } 
         if (this.pValue.contains(",")) {
           String[] pValueArray = this.pValue.split(",");
           List<String> checkedList = Arrays.asList(pValueArray);
           boolean checkedFlag = false;
           for (String checkedValue : checkedList) {
             if (checkedValue.equals(data.get("CODE_ID")))
               checkedFlag = true; 
           } 
           check.append("<span class=\"board_data_wrap \">");
           check.append("<input type=\"checkbox\" ");
           check.append("id    =\"").append(pId).append("\" ");
           check.append("name  =\"").append(this.pNm).append("\" ");
           check.append("value =\"").append(data.get("CODE_ID")).append("\" ");
           if (checkedFlag)
             check.append("checked=\"checked\" "); 
           if (this.pDisabled.booleanValue())
             check.append("disabled=\"disabled\" "); 
           check.append("/>");
           check.append("<label for=\"").append(pId).append("\"><span></span>").append(data.get("CODE_NM")).append("</label>").append("\r\n");
           check.append("</span>");
           continue;
         } 
         if (getpValue().equals(data.get("CODE_ID"))) {
           check.append("<span class=\"board_data_wrap \">");
           check.append("<input type=\"checkbox\" ");
           check.append("id    =\"").append(pId).append("\" ");
           check.append("name  =\"").append(this.pNm).append("\" ");
           check.append("value =\"").append(data.get("CODE_ID")).append("\" ");
           check.append("checked=\"checked\" ");
           if (this.pDisabled.booleanValue())
             check.append("disabled=\"disabled\" "); 
           check.append("/>");
           check.append("<label for=\"").append(pId).append("\"><span></span>").append(data.get("CODE_NM")).append("</label>").append("\r\n");
           check.append("</span>");
           continue;
         } 
         check.append("<span class=\"board_data_wrap \">");
         check.append("<input type=\"checkbox\" ");
         check.append("id    =\"").append(pId).append("\" ");
         check.append("name  =\"").append(this.pNm).append("\" ");
         check.append("value =\"").append(data.get("CODE_ID")).append("\" ");
         if (this.pDisabled.booleanValue())
           check.append("disabled=\"disabled\" "); 
         check.append("/>");
         check.append("<label for=\"").append(pId).append("\"><span></span>").append(data.get("CODE_NM")).append("</label>").append("\r\n");
         check.append("</span>");
       } 
       this.pageContext.getOut().print(check);
     } catch (Exception e) {
       this.logger.error(ExceptionUtils.getStackTrace(e));
       e.printStackTrace();
     } 
     return 0;
   }
 }


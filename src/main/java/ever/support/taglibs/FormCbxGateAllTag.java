package ever.support.taglibs;

import ever.cmmn.service.CmmnService;
import java.util.ArrayList;
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

public class FormCbxGateAllTag extends TagSupport {
	private static final long serialVersionUID = -8307011305157005064L;

	final Logger logger = LoggerFactory.getLogger(getClass());

	String pGateCls1 = "";

	String pGateCls2 = "";

	String pGateCls3 = "";

	String pValue = "";

	String pClass = "";

	String pClass1 = "";

	String pClass2 = "";

	String pClass3 = "";

	String pTitle = "";

	Boolean pAllYn = Boolean.valueOf(true);

	Boolean pDisabled = Boolean.valueOf(false);

	public String getpGateCls1() {
		return this.pGateCls1;
	}

	public void setpGateCls1(String pGateCls1) {
		this.pGateCls1 = pGateCls1;
	}

	public String getpGateCls2() {
		return this.pGateCls2;
	}

	public void setpGateCls2(String pGateCls2) {
		this.pGateCls2 = pGateCls2;
	}

	public String getpGateCls3() {
		return this.pGateCls3;
	}

	public void setpGateCls3(String pGateCls3) {
		this.pGateCls3 = pGateCls3;
	}

	public String getpValue() {
		return this.pValue;
	}

	public void setpValue(String pValue) {
		this.pValue = pValue;
	}

	public String getpClass() {
		return this.pClass;
	}

	public void setpClass(String pClass) {
		this.pClass = pClass;
	}

	public String getpClass1() {
		return this.pClass1;
	}

	public void setpClass1(String pClass1) {
		this.pClass1 = pClass1;
	}

	public String getpClass2() {
		return this.pClass2;
	}

	public void setpClass2(String pClass2) {
		this.pClass2 = pClass2;
	}

	public String getpClass3() {
		return this.pClass3;
	}

	public void setpClass3(String pClass3) {
		this.pClass3 = pClass3;
	}

	public String getpTitle() {
		return this.pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
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

	public int doStartTag() throws JspException {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(this.pageContext.getServletContext());
		CmmnService cmmnService = (CmmnService) webApplicationContext.getBean(CmmnService.class);
		List<Object> gateList1 = new ArrayList();
		HashMap<String, Object> pMap = new HashMap<>();
		StringBuffer select = new StringBuffer();
		try {
			pMap.put("UPPERKIND", "0");
			gateList1 = cmmnService.selectList("cmmn.selectCmmnGateList", pMap);
			String pClass1 = StringUtils.isBlank(this.pClass1) ? this.pClass : this.pClass1;
			select.append(
					"<select class=\"board_sch_select " + (String) StringUtils.defaultIfEmpty(pClass1, "w120") + "\" ");
			select.append("id=\"").append(this.pGateCls1).append("\" ");
			select.append("name=\"").append(this.pGateCls1).append("\" ");
			select.append("onchange=\"CmmnUtil.getCmmnCateTagLib('/bindSELECT/cmmn/selectCmmnGateList.ajax','#"
					+ this.pGateCls2 + "',{'UPPERKIND':event.target.value});\" ");
			if (this.pDisabled.booleanValue())
				select.append("disabled=\"disabled\" ");
			select.append(">");
			if (this.pAllYn.booleanValue())
				select.append("<option value=\"\"> ");
			if (this.pAllYn.booleanValue())
				select.append((String) StringUtils.defaultIfEmpty(this.pTitle, "선택"));
			if (this.pAllYn.booleanValue())
				select.append("</option>");
			for (Object gateMap1 : gateList1) {
				HashMap<String, Object> data = (HashMap<String, Object>) gateMap1;
				Object optValue = data.get("CODE_ID");
				Object optName = "(" + data.get("CODE_ID") + ") " + data.get("CODE_NM");
				select.append("<option value=\"" + optValue + "\"                      >" + optName + "</option>");
			}
			select.append("</select>").append("\r\n");
			String pClass2 = StringUtils.isBlank(this.pClass2) ? this.pClass : this.pClass2;
			select.append(
					"<select class=\"board_sch_select " + (String) StringUtils.defaultIfEmpty(pClass2, "w120") + "\" ");
			select.append("id    =\"").append(this.pGateCls2).append("\" ");
			select.append("name  =\"").append(this.pGateCls2).append("\" ");
			select.append("onchange=\"CmmnUtil.getCmmnCateTagLib('/bindSELECT/cmmn/selectCmmnGateList.ajax','#"
					+ this.pGateCls3 + "',{'UPPERKIND':event.target.value});\" ");
			if (this.pDisabled.booleanValue())
				select.append("disabled=\"disabled\" ");
			select.append(">");
			if (this.pAllYn.booleanValue())
				select.append("<option value=\"\"> ");
			if (this.pAllYn.booleanValue())
				select.append((String) StringUtils.defaultIfEmpty(this.pTitle, "선택"));
			if (this.pAllYn.booleanValue())
				select.append("</option>");
			select.append("</select>").append("\r\n");
			String pClass3 = StringUtils.isBlank(this.pClass3) ? this.pClass : this.pClass3;
			select.append(
					"<select class=\"board_sch_select " + (String) StringUtils.defaultIfEmpty(pClass3, "w120") + "\" ");
			select.append("id    =\"").append(this.pGateCls3).append("\" ");
			select.append("name  =\"").append(this.pGateCls3).append("\" ");
			if (this.pDisabled.booleanValue())
				select.append("disabled=\"disabled\" ");
			select.append(">");
			if (this.pAllYn.booleanValue())
				select.append("<option value=\"\"> ");
			if (this.pAllYn.booleanValue())
				select.append((String) StringUtils.defaultIfEmpty(this.pTitle, "선택"));
			if (this.pAllYn.booleanValue())
				select.append("</option>");
			select.append("</select>").append("\r\n");
			this.pageContext.getOut().print(select);
		} catch (Exception e) {
			this.logger.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return 0;
	}
}

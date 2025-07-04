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

public class FormCbxGateClsTag extends TagSupport {
	private static final long serialVersionUID = -3341889788326919584L;

	final Logger logger = LoggerFactory.getLogger(getClass());

	String pGateCls = "";

	String pValue = "";

	String pClass = "";

	String pTitle = "";

	Boolean pAllYn = Boolean.valueOf(true);

	Boolean pDisabled = Boolean.valueOf(false);

	Boolean pTotOptNo = Boolean.valueOf(false);

	Boolean pTotOptNm = Boolean.valueOf(false);

	String pStep = "";

	public String getpGateCls() {
		return this.pGateCls;
	}

	public void setpGateCls(String pGateCls) {
		this.pGateCls = pGateCls;
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

	public Boolean getpTotOptNo() {
		return this.pTotOptNo;
	}

	public void setpTotOptNo(Boolean pTotOptNo) {
		this.pTotOptNo = pTotOptNo;
	}

	public Boolean getpTotOptNm() {
		return this.pTotOptNm;
	}

	public void setpTotOptNm(Boolean pTotOptNm) {
		this.pTotOptNm = pTotOptNm;
	}

	public String getpStep() {
		return this.pStep;
	}

	public void setpStep(String pStep) {
		this.pStep = pStep;
	}

	public int doStartTag() throws JspException {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(this.pageContext.getServletContext());
		CmmnService cmmnService = (CmmnService) webApplicationContext.getBean(CmmnService.class);
		List<Object> gateList = new ArrayList();
		HashMap<String, Object> pMap = new HashMap<>();
		StringBuffer select = new StringBuffer();
		try {
			String pStep = StringUtils.isBlank(getpStep()) ? "0" : getpStep();
			pMap.put("STEP", pStep);
			gateList = cmmnService.selectList("cmmn.selectCmmnGateList", pMap);
			select.append("<select class=\"board_sch_select "
					+ (String) StringUtils.defaultIfEmpty(this.pClass, this.pTotOptNm.booleanValue() ? "w100p" : "w120")
					+ "\" ");
			select.append("id=\"").append(this.pGateCls).append("\" ");
			select.append("name=\"").append(this.pGateCls).append("\" ");
			if (this.pDisabled.booleanValue())
				select.append("disabled=\"disabled\" ");
			select.append(">");
			if (this.pAllYn.booleanValue())
				select.append("<option value=\"\"> ");
			if (this.pAllYn.booleanValue())
				select.append((String) StringUtils.defaultIfEmpty(this.pTitle, "선택"));
			if (this.pAllYn.booleanValue())
				select.append("</option>");
			for (Object gateMap : gateList) {
				HashMap<String, Object> data = (HashMap<String, Object>) gateMap;
				Object optValue = getpTotOptNo().booleanValue() ? data.get("TOT_CODE_NO") : data.get("CODE_ID");
				Object optName = getpTotOptNm().booleanValue()
						? ("(" + data.get("CODE_ID") + ") " + data.get("TOT_CODE_NM"))
						: ("(" + data.get("CODE_ID") + ") " + data.get("CODE_NM"));
				if (getpValue().equals(optValue.toString())) {
					select.append("<option value=\"" + optValue + "\" selected=\"selected\">" + optName + "</option>");
					continue;
				}
				select.append("<option value=\"" + optValue + "\"                      >" + optName + "</option>");
			}
			select.append("</select>").append("\r\n");
			this.pageContext.getOut().print(select);
		} catch (Exception e) {
			this.logger.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return 0;
	}
}

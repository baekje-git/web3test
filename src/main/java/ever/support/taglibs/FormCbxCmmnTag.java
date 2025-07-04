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

public class FormCbxCmmnTag extends TagSupport {
	private static final long serialVersionUID = 7303001223655387936L;

	final Logger logger = LoggerFactory.getLogger(getClass());

	String pId = "";

	String pNm = "";

	String pCodeId = "";

	String pClass = "";

	String pValue = "";

	String pTitle = "";

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

	public String getpTitle() {
		return this.pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
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

	public int doStartTag() throws JspException {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(this.pageContext.getServletContext());
		CmmnService cmmnService = (CmmnService) webApplicationContext.getBean(CmmnService.class);
		List<Object> codeList = new ArrayList();
		HashMap<String, Object> pMap = new HashMap<>();
		StringBuffer select = new StringBuffer();
		try {
			pMap.put("P_CODE", this.pCodeId);
			codeList = cmmnService.selectList("cmmn.selectCmmnCodeList", pMap);
			select.append("<select class=\"board_sch_select "
					+ (String) StringUtils.defaultIfEmpty(this.pClass, "w100p") + "\" ");
			select.append("id    =\"").append(this.pId).append("\" ");
			select.append("name  =\"").append(this.pNm).append("\" ");
			if (this.pDisabled.booleanValue())
				select.append("disabled=\"disabled\" ");
			select.append(">");
			if (this.pAllYn.booleanValue())
				select.append("<option value=\"\"> ");
			if (this.pAllYn.booleanValue())
				select.append((String) StringUtils.defaultIfEmpty(this.pTitle, "전체"));
			if (this.pAllYn.booleanValue())
				select.append("</option>");
			for (Object codeMap : codeList) {
				HashMap<String, Object> data = (HashMap<String, Object>) codeMap;
				if (!StringUtils.isEmpty(this.pExceptCd) && this.pExceptCd.equals(data.get("CODE_ID")))
					continue;
				if (getpValue().equals(data.get("CODE_ID"))) {
					select.append("<option value=\"" + data.get("CODE_ID") + "\" selected=\"selected\">"
							+ data.get("CODE_NM") + "</option>");
					continue;
				}
				select.append("<option value=\"" + data.get("CODE_ID") + "\"                      >"
						+ data.get("CODE_NM") + "</option>");
			}
			select.append("</select>");
			this.pageContext.getOut().print(select);
		} catch (Exception e) {
			this.logger.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return 0;
	}
}

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

public class FormRadioCmmnTag extends TagSupport {
	private static final long serialVersionUID = -8276813635621033633L;

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
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(this.pageContext.getServletContext());
		CmmnService cmmnService = (CmmnService) webApplicationContext.getBean(CmmnService.class);
		List<Object> codeList = new ArrayList();
		HashMap<String, Object> pMap = new HashMap<>();
		StringBuffer radio = new StringBuffer();
		try {
			pMap.put("P_CODE", this.pCodeId);
			codeList = cmmnService.selectList("cmmn.selectCmmnCodeList", pMap);
			int idx = 0;
			radio.append("<span class=\"board_data_wrap \">");
			if (this.pAllYn.booleanValue()) {
				radio.append("<input type=\"radio\" ");
				radio.append("id    =\"").append(String.valueOf(this.pId) + "_0").append("\" ");
				radio.append("name  =\"").append(this.pNm).append("\" ");
				radio.append("value =\"\" ");
				radio.append("checked=\"checked\" ");
				if (this.pDisabled.booleanValue())
					radio.append("disabled=\"disabled\" ");
				radio.append("/>");
				radio.append("<label for=\"").append(String.valueOf(this.pId) + "_0").append("\"><span></span>")
						.append("전체").append("</label>").append("\r\n");
			}
			for (Object codeMap : codeList) {
				idx++;
				String pId = String.valueOf(this.pId) + "_" + idx;
				HashMap<String, Object> data = (HashMap<String, Object>) codeMap;
				if (!StringUtils.isEmpty(this.pExceptCd) && this.pExceptCd.equals(data.get("CODE_ID")))
					continue;
				if (StringUtils.isBlank(getpValue()) && !this.pAllYn.booleanValue() && idx == 1) {
					radio.append("<input type=\"radio\" ");
					radio.append("id    =\"").append(pId).append("\" ");
					radio.append("name  =\"").append(this.pNm).append("\" ");
					radio.append("value =\"").append(data.get("CODE_ID")).append("\" ");
					radio.append("checked=\"checked\" ");
					if (this.pDisabled.booleanValue())
						radio.append("disabled=\"disabled\" ");
					radio.append("/>");
					radio.append("<label for=\"").append(pId).append("\"><span></span>").append(data.get("CODE_NM"))
							.append("</label>").append("\r\n");
					continue;
				}
				if (getpValue().equals(data.get("CODE_ID"))) {
					radio.append("<input type=\"radio\" ");
					radio.append("id    =\"").append(pId).append("\" ");
					radio.append("name  =\"").append(this.pNm).append("\" ");
					radio.append("value =\"").append(data.get("CODE_ID")).append("\" ");
					radio.append("checked=\"checked\" ");
					if (this.pDisabled.booleanValue())
						radio.append("disabled=\"disabled\" ");
					radio.append("/>");
					radio.append("<label for=\"").append(pId).append("\"><span></span>").append(data.get("CODE_NM"))
							.append("</label>").append("\r\n");
					continue;
				}
				radio.append("<input type=\"radio\" ");
				radio.append("id    =\"").append(pId).append("\" ");
				radio.append("name  =\"").append(this.pNm).append("\" ");
				radio.append("value =\"").append(data.get("CODE_ID")).append("\" ");
				if (this.pDisabled.booleanValue())
					radio.append("disabled=\"disabled\" ");
				radio.append("/>");
				radio.append("<label for=\"").append(pId).append("\"><span></span>").append(data.get("CODE_NM"))
						.append("</label>").append("\r\n");
			}
			radio.append("</span>");
			this.pageContext.getOut().print(radio);
		} catch (Exception e) {
			this.logger.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return 0;
	}
}

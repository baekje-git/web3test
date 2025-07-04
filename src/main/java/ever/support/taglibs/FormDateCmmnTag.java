package ever.support.taglibs;

import ever.cmmn.service.CmmnService;
import ever.support.date.DateUtil;
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

public class FormDateCmmnTag extends TagSupport {
	private static final long serialVersionUID = 8712341918109124136L;

	final Logger logger = LoggerFactory.getLogger(getClass());

	String pId = "";

	String pNm = "";

	String pClass = "";

	String pStyle = "";

	String pValue = "";

	Boolean pDefaultValue = Boolean.valueOf(false);

	Boolean pBeforeYn = Boolean.valueOf(true);

	Boolean pReadonly = Boolean.valueOf(false);

	Boolean pDisabled = Boolean.valueOf(false);

	int pBtnCnt = 0;

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

	public String getpClass() {
		return this.pClass;
	}

	public void setpClass(String pClass) {
		this.pClass = pClass;
	}

	public String getpStyle() {
		return this.pStyle;
	}

	public void setpStyle(String pStyle) {
		this.pStyle = pStyle;
	}

	public String getpValue() {
		return this.pValue;
	}

	public void setpValue(String pValue) {
		this.pValue = pValue;
	}

	public Boolean getpDefaultValue() {
		return this.pDefaultValue;
	}

	public void setpDefaultValue(Boolean pDefaultValue) {
		this.pDefaultValue = pDefaultValue;
	}

	public Boolean getpBeforeYn() {
		return this.pBeforeYn;
	}

	public void setpBeforeYn(Boolean pBeforeYn) {
		this.pBeforeYn = pBeforeYn;
	}

	public Boolean getpReadonly() {
		return this.pReadonly;
	}

	public void setpReadonly(Boolean pReadonly) {
		this.pReadonly = pReadonly;
	}

	public Boolean getpDisabled() {
		return this.pDisabled;
	}

	public void setpDisabled(Boolean pDisabled) {
		this.pDisabled = pDisabled;
	}

	public int getpBtnCnt() {
		return this.pBtnCnt;
	}

	public void setpBtnCnt(int pBtnCnt) {
		this.pBtnCnt = pBtnCnt;
	}

	public int doStartTag() throws JspException {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(this.pageContext.getServletContext());
		CmmnService cmmnService = (CmmnService) webApplicationContext.getBean(CmmnService.class);
		HashMap<String, Object> svrDateMap = null;
		String serverDate = DateUtil.getDate();
		StringBuffer date = new StringBuffer();
		try {
			date.append("<span class=\"board_data_wrap date\">");
			date.append("<input type=\"text\" ");
			date.append("class =\"").append(this.pClass).append("\" ");
			date.append("id    =\"").append(this.pId).append("\" ");
			date.append("name  =\"").append(this.pNm).append("\" ");
			if (!StringUtils.isEmpty(getpStyle()))
				date.append("style =\"").append(this.pStyle).append("\" ");
			if (!StringUtils.isEmpty(getpValue()))
				date.append("value =\"").append(this.pValue).append("\" ");
			if (this.pDefaultValue.booleanValue())
				date.append("value =\"").append(serverDate).append("\" ");
			if (!this.pBeforeYn.booleanValue())
				date.append("data-min-data =\"0\"  ");
			if (this.pDisabled.booleanValue())
				date.append("disabled=\"disabled\" ");
			if (this.pReadonly.booleanValue())
				date.append("readonly=\"readonly\" ");
			date.append("maxlength =\"10\" />");
			date.append("<label for=\"" + this.pId + "\"></label>");
			date.append("</span>").append("\r\n");
			List<HashMap<String, String>> listBtn = new ArrayList<>();
			HashMap<String, String> mapAttr = new HashMap<>();
			if (this.pBtnCnt > 0) {
				if (this.pBtnCnt >= 1) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pNm) + "_BTCLR_N");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pNm + "','n','','','" + this.pNm + "_BTCLR_N');");
					mapAttr.put("text", "현재");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 2) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pNm) + "_BTCLR_D1");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pNm + "','d','1','','" + this.pNm + "_BTCLR_D1');");
					mapAttr.put("text", "1일전");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 3) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pNm) + "_BTCLR_W1");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pNm + "','w','1','','" + this.pNm + "_BTCLR_W1');");
					mapAttr.put("text", "1주일");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 4) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pNm) + "_BTCLR_M1");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pNm + "','m','1','','" + this.pNm + "_BTCLR_M1');");
					mapAttr.put("text", "1개월");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 5) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pNm) + "_BTCLR_M3");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pNm + "','m','3','','" + this.pNm + "_BTCLR_M3');");
					mapAttr.put("text", "3개월");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 6) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pNm) + "_BTCLR_M6");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pNm + "','m','6','', '" + this.pNm + "_BTCLR_M6');");
					mapAttr.put("text", "6개월");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 7) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pNm) + "_BTCLR_Y1");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pNm + "','m','12','','" + this.pNm + "_BTCLR_Y1');");
					mapAttr.put("text", "1년");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 8) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pNm) + "_BTCLR_Y2");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pNm + "','m','24','','" + this.pNm + "_BTCLR_Y2');");
					mapAttr.put("text", "2년");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 9) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pNm) + "_BTCLR_Y3");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pNm + "','m','36','','" + this.pNm + "_BTCLR_Y3');");
					mapAttr.put("text", "3년");
					listBtn.add(mapAttr);
				}
			}
			for (HashMap<String, String> mAttr : listBtn) {
				date.append("<a ");
				date.append("name=\"" + (String) mAttr.get("name") + "\" ");
				date.append("onclick=\"" + (String) mAttr.get("onclick") + "\" ");
				date.append("class=\"btn1\">");
				date.append(mAttr.get("text"));
				date.append("</a> ").append("\r\n");
			}
			this.pageContext.getOut().print(date);
		} catch (Exception e) {
			this.logger.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return 0;
	}
}

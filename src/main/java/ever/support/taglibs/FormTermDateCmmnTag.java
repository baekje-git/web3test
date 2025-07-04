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

public class FormTermDateCmmnTag extends TagSupport {
	private static final long serialVersionUID = -7089360438365491379L;

	final Logger logger = LoggerFactory.getLogger(getClass());

	String pBegin = "";

	String pEnd = "";

	String pClass = "";

	String pStyle = "";

	String pValue = "";

	String pBValue = "";

	String pEValue = "";

	boolean pDefaultValue = false;

	Boolean pDisabled = Boolean.valueOf(false);

	Boolean pReadonly = Boolean.valueOf(false);

	String pBClass = "";

	String pEClass = "";

	String pBStyle = "";

	String pEStyle = "";

	int pBtnCnt = 0;

	public String getpBegin() {
		return this.pBegin;
	}

	public String getpEnd() {
		return this.pEnd;
	}

	public void setpEnd(String pEnd) {
		this.pEnd = pEnd;
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

	public String getpBValue() {
		return this.pBValue;
	}

	public void setpBValue(String pBValue) {
		this.pBValue = pBValue;
	}

	public String getpEValue() {
		return this.pEValue;
	}

	public void setpEValue(String pEValue) {
		this.pEValue = pEValue;
	}

	public boolean ispDefaultValue() {
		return this.pDefaultValue;
	}

	public void setpDefaultValue(boolean pDefaultValue) {
		this.pDefaultValue = pDefaultValue;
	}

	public Boolean getpDisabled() {
		return this.pDisabled;
	}

	public void setpDisabled(Boolean pDisabled) {
		this.pDisabled = pDisabled;
	}

	public Boolean getpReadonly() {
		return this.pReadonly;
	}

	public void setpReadonly(Boolean pReadonly) {
		this.pReadonly = pReadonly;
	}

	public String getpBClass() {
		return this.pBClass;
	}

	public void setpBClass(String pBClass) {
		this.pBClass = pBClass;
	}

	public String getpEClass() {
		return this.pEClass;
	}

	public void setpEClass(String pEClass) {
		this.pEClass = pEClass;
	}

	public String getpBStyle() {
		return this.pBStyle;
	}

	public void setpBStyle(String pBStyle) {
		this.pBStyle = pBStyle;
	}

	public String getpEStyle() {
		return this.pEStyle;
	}

	public void setpEStyle(String pEStyle) {
		this.pEStyle = pEStyle;
	}

	public void setpBegin(String pBegin) {
		this.pBegin = pBegin;
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
		StringBuffer date = new StringBuffer();
		try {
			if (this.pBegin.equals(this.pEnd))
				return 0;
			String F_DATE = DateUtil.getFirstDay(DateUtil.getDate(), "yyyy-MM-dd", "yyyy-MM-dd");
			String L_DATE = DateUtil.getLastDay(DateUtil.getDate(), "yyyy-MM-dd", "yyyy-MM-dd");
			this.pClass = StringUtils.isBlank(getpClass()) ? "w100" : getpClass();
			setpClass(StringUtils.isBlank(getpBClass()) ? getpClass() : getpBClass());
			setpClass(StringUtils.isBlank(getpEClass()) ? getpClass() : getpEClass());
			setpStyle(StringUtils.isBlank(getpBStyle()) ? getpStyle() : getpBStyle());
			setpStyle(StringUtils.isBlank(getpEStyle()) ? getpStyle() : getpEStyle());
			date.append("<span class=\"board_data_wrap date\">");
			date.append("<input type=\"text\" ");
			date.append("class  =\"").append(this.pClass).append("\" ");
			date.append("id     =\"").append(this.pBegin).append("\" ");
			date.append("name   =\"").append(this.pBegin).append("\" ");
			if (!StringUtils.isEmpty(getpBValue()))
				date.append("value =\"").append(this.pBValue).append("\" ");
			if (!StringUtils.isEmpty(getpBStyle()))
				date.append("style =\"").append(this.pBStyle).append("\" ");
			if (this.pDefaultValue)
				date.append("value =\"").append(F_DATE).append("\" ");
			if (this.pDisabled.booleanValue())
				date.append("disabled=\"disabled\" ");
			if (this.pReadonly.booleanValue())
				date.append("readonly=\"readonly\" ");
			date.append("maxlength=\"10\" >");
			date.append("<label for=\"" + this.pBegin + "\">");
			date.append("</label>");
			date.append("</span>");
			date.append("<b> ~ </b>");
			date.append("<span class=\"board_data_wrap date\">");
			date.append("<input type=\"text\" ");
			date.append("class =\"").append(this.pClass).append("\" ");
			date.append("id    =\"").append(this.pEnd).append("\" ");
			date.append("name  =\"").append(this.pEnd).append("\" ");
			if (!StringUtils.isEmpty(getpEValue()))
				date.append("value =\"").append(this.pEValue).append("\" ");
			if (!StringUtils.isEmpty(getpEStyle()))
				date.append("style =\"").append(this.pEStyle).append("\" ");
			if (this.pDefaultValue)
				date.append("value =\"").append(L_DATE).append("\" ");
			if (this.pDisabled.booleanValue())
				date.append("disabled=\"disabled\" ");
			if (this.pReadonly.booleanValue())
				date.append("readonly=\"readonly\" ");
			date.append("maxlength=\"10\" >");
			date.append("<label for=\"" + this.pEnd + "\">");
			date.append("</label>");
			date.append("</span>").append("\r\n");
			List<HashMap<String, String>> listBtn = new ArrayList<>();
			HashMap<String, String> mapAttr = new HashMap<>();
			if (this.pBtnCnt > 0) {
				if (this.pBtnCnt >= 1) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pBegin) + "_BTCLR_N");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pBegin + "','n','','','" + this.pBegin + "_BTCLR_N');");
					mapAttr.put("text", "현재");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 2) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pBegin) + "_BTCLR_D1");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pBegin + "','d','1','','" + this.pBegin + "_BTCLR_D1');");
					mapAttr.put("text", "1일전");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 3) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pBegin) + "_BTCLR_W1");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pBegin + "','w','1','','" + this.pBegin + "_BTCLR_W1');");
					mapAttr.put("text", "1주일");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 4) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pBegin) + "_BTCLR_M1");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pBegin + "','m','1','','" + this.pBegin + "_BTCLR_M1');");
					mapAttr.put("text", "1개월");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 5) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pBegin) + "_BTCLR_M3");
					mapAttr.put("onclick",
							"DateUtil.getDateContrl('" + this.pBegin + "','m','3','','" + this.pBegin + "_BTCLR_M3');");
					mapAttr.put("text", "3개월");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 6) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pBegin) + "_BTCLR_M6");
					mapAttr.put("onclick", "DateUtil.getDateContrl('" + this.pBegin + "','m','6','', '" + this.pBegin
							+ "_BTCLR_M6');");
					mapAttr.put("text", "6개월");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 7) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pBegin) + "_BTCLR_Y1");
					mapAttr.put("onclick", "DateUtil.getDateContrl('" + this.pBegin + "','m','12','','" + this.pBegin
							+ "_BTCLR_Y1');");
					mapAttr.put("text", "1년");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 8) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pBegin) + "_BTCLR_Y2");
					mapAttr.put("onclick", "DateUtil.getDateContrl('" + this.pBegin + "','m','24','','" + this.pBegin
							+ "_BTCLR_Y2');");
					mapAttr.put("text", "2년");
					listBtn.add(mapAttr);
				}
				if (this.pBtnCnt >= 9) {
					mapAttr = new HashMap<>();
					mapAttr.put("name", String.valueOf(this.pBegin) + "_BTCLR_Y3");
					mapAttr.put("onclick", "DateUtil.getDateContrl('" + this.pBegin + "','m','36','','" + this.pBegin
							+ "_BTCLR_Y3');");
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

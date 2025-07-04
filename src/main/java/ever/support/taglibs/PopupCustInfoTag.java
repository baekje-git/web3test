package ever.support.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopupCustInfoTag extends TagSupport {
	private static final long serialVersionUID = 5273398409689921786L;

	final Logger logger = LoggerFactory.getLogger(getClass());

	String pType = "";

	String pCode = "";

	String pName = "";

	String pClass = "";

	String pCallFunc = "";

	String pCallParam = "";

	String pRetnFunc = "";

	String pClearId = "";

	Boolean pReadOnly = Boolean.valueOf(false);

	public String getpType() {
		return this.pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	public String getpCode() {
		return this.pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getpName() {
		return this.pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpClass() {
		return this.pClass;
	}

	public void setpClass(String pClass) {
		this.pClass = pClass;
	}

	public String getpCallFunc() {
		return this.pCallFunc;
	}

	public void setpCallFunc(String pCallFunc) {
		this.pCallFunc = pCallFunc;
	}

	public String getpCallParam() {
		return this.pCallParam;
	}

	public void setpCallParam(String pCallParam) {
		this.pCallParam = pCallParam;
	}

	public String getpRetnFunc() {
		return this.pRetnFunc;
	}

	public void setpRetnFunc(String pRetnFunc) {
		this.pRetnFunc = pRetnFunc;
	}

	public String getpClearId() {
		return this.pClearId;
	}

	public void setpClearId(String pClearId) {
		this.pClearId = pClearId;
	}

	public Boolean getpReadOnly() {
		return this.pReadOnly;
	}

	public void setpReadOnly(Boolean pReadOnly) {
		this.pReadOnly = pReadOnly;
	}

	public int doStartTag() throws JspException {
		StringBuffer popup = new StringBuffer();
		try {
			this.pType = StringUtils.isBlank(getpType()) ? "CUST" : getpType();
			this.pClass = StringUtils.isBlank(getpClass()) ? "w100" : getpClass();
			int width = Integer.parseInt(this.pClass.replaceAll("w", "").replaceAll("p", "")) + 4;
			String clearInput = this.pName.isEmpty() ? "" : this.pName;
			if (!this.pClearId.isEmpty())
				clearInput = String.valueOf(clearInput) + "@" + this.pClearId;
			StringBuffer popOptObj = new StringBuffer();
			popOptObj.append("{");
			popOptObj.append("etcInput   : '").append(clearInput).append("', ");
			popOptObj.append("pType      : '").append(this.pType).append("', ");
			popOptObj.append("pName      : '").append(this.pName).append("', ");
			popOptObj.append("callFunc   : '").append(this.pCallFunc).append("', ");
			popOptObj.append("retnFunc   : '").append(this.pRetnFunc).append("', ");
			popOptObj.append("params     : '").append(this.pCallParam).append("'  ");
			popOptObj.append("}");
			if (this.pReadOnly.booleanValue()) {
				popup.append("<span class=\"board_data_wrap popup\"> ");
				popup.append("<input type=\"text\" ");
				popup.append("id=\"").append(this.pCode).append("\" ");
				popup.append("name=\"").append(this.pCode).append("\" ");
				popup.append("value=\"\" ");
				popup.append("class=\"").append(this.pClass).append("\" ");
				popup.append("readonly=\"readonly\" ");
				popup.append("/>");
				popup.append("</span>");
			} else {
				popup.append("<span class=\"board_data_wrap popup\"> ");
				popup.append("<input type=\"text\" ");
				popup.append("id=\"").append(this.pCode).append("\" ");
				popup.append("name=\"").append(this.pCode).append("\" ");
				popup.append("value=\"\" ");
				popup.append("class=\"").append(this.pClass).append("\" ");
				popup.append("onkeydown=\"PopUtil.onTabEvent(this, event);\" ");
				popup.append("onkeyup=\"PopUtil.onSearchPopEvent(this, event, ").append(popOptObj.toString())
						.append(");\" ");
				popup.append("onchange=\"PopUtil.onChangeClearEvent(this, event, '").append(clearInput)
						.append("');\" ");
				popup.append("maxlength=\"6\" ");
				popup.append("/>");
				popup.append("<a href=\"javascript:void(0);\" ");
				popup.append(
						"onclick=\"PopUtil.onClickEvent(this,'" + this.pCallFunc + "','" + this.pCallParam + "');\" ");
				popup.append(">").append("</a>");
				popup.append("</span>");
			}
			popup.append("<span class=\"board_data_wrap\"> ");
			popup.append("<input type=\"text\" ");
			popup.append("id=\"").append(this.pName).append("\" ");
			popup.append("name=\"").append(this.pName).append("\" ");
			popup.append("value=\"\" ");
			popup.append("style=\"width:calc(100% - ").append(width).append("px);\" ");
			popup.append("readonly=\"readonly\" ");
			popup.append("/>").append("</span>");
			this.pageContext.getOut().print(popup);
		} catch (Exception e) {
			this.logger.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return 0;
	}
}

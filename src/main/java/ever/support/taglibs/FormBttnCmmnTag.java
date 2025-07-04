package ever.support.taglibs;

import ever.support.session.SessionUtil;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormBttnCmmnTag extends TagSupport {
	private static final long serialVersionUID = 4680623024569331205L;

	final Logger logger = LoggerFactory.getLogger(getClass());

	String pType = "";

	String pId = "";

	String pTitle = "";

	String pOnClick = "";

	String pRole = "";

	String pAdmin = "";

	public String getpType() {
		return this.pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	public String getpId() {
		return this.pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpTitle() {
		return this.pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}

	public String getpOnClick() {
		return this.pOnClick;
	}

	public void setpOnClick(String pOnClick) {
		this.pOnClick = pOnClick;
	}

	public String getpRole() {
		return this.pRole;
	}

	public void setpRole(String pRole) {
		this.pRole = pRole;
	}

	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		HashMap<String, Object> userInfo = SessionUtil.getSessionLoginInfo(request.getSession());
		String adminYn = (String) userInfo.get("ADMIN_YN");
		String roleCd = (String) userInfo.get("ROLE_CD");
		this.logger.debug("type : " + this.pType + " | adminYn : " + adminYn + " | roleCd : " + roleCd);
		StringBuffer button = new StringBuffer();
		try {
			if (this.pType.equals("APRVL") && !adminYn.equals("Y") && (!roleCd.equals("000") || !roleCd.equals("001")
					|| !roleCd.equals("002") || !roleCd.equals("003") || !roleCd.equals("004")))
				return 0;
			boolean btnChk = false;
			String btnImg = "";
			String str1;
			switch ((str1 = this.pType).hashCode()) {
			case -1898676947:
				if (!str1.equals("ORDER_CRM"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "발주확정" : this.pTitle);
				btnImg = "/common/images/ico_order.png";
				break;
			case -1853007448:
				if (!str1.equals("SEARCH"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "조회" : this.pTitle);
				btnImg = "/common/images/ico_search.png";
				break;
			case 82233:
				if (!str1.equals("SMS"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "SMS전송" : this.pTitle);
				btnImg = "/common/images/ico_smssend.png";
				break;
			case 2123274:
				if (!str1.equals("EDIT"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "수정" : this.pTitle);
				btnImg = "/common/images/ico_edit.png";
				break;
			case 2336926:
				if (!str1.equals("LIST"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "목록" : this.pTitle);
				btnImg = "/common/images/ico_smdown.png";
				break;
			case 2537853:
				if (!str1.equals("SAVE"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "저장" : this.pTitle);
				btnImg = "/common/images/ico_save.png";
				break;
			case 62493689:
				if (!str1.equals("APRVL"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "승인" : this.pTitle);
				btnImg = "/common/images/ico_appro.png";
				break;
			case 62970894:
				if (!str1.equals("BASIC"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "버튼명" : this.pTitle);
				break;
			case 64218584:
				if (!str1.equals("CLOSE"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "닫기" : this.pTitle);
				btnImg = "/common/images/ico_close.png";
				break;
			case 66081660:
				if (!str1.equals("EMAIL"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "이메일전송" : this.pTitle);
				btnImg = "/common/images/ico_emailsend.png";
				break;
			case 66411159:
				if (!str1.equals("EXCEL"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "엑셀" : this.pTitle);
				btnImg = "/common/images/ico_excel.png";
				break;
			case 69820330:
				if (!str1.equals("INPUT"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "입력" : this.pTitle);
				btnImg = "/common/images/ico_input.png";
				break;
			case 77866287:
				if (!str1.equals("RESET"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "초기화" : this.pTitle);
				btnImg = "/common/images/ico_reset.png";
				break;
			case 818758738:
				if (!str1.equals("EXCEL_SAMPLE"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "샘플양식파일" : this.pTitle);
				btnImg = "/common/images/ico_smdown.png";
				break;
			case 1242929327:
				if (!str1.equals("VIEW_MORE"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "더보기" : this.pTitle);
				btnImg = "/common/images/ico_add.png";
				break;
			case 1737765840:
				if (!str1.equals("ROW_DELETE"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "행삭제" : this.pTitle);
				btnImg = "/common/images/ico_row_minus.png";
				break;
			case 1980572282:
				if (!str1.equals("CANCEL"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "취소" : this.pTitle);
				btnImg = "/common/images/ico_cancel.png";
				break;
			case 2105801852:
				if (!str1.equals("ROW_ADD"))
					break;
				btnChk = true;
				setpTitle(StringUtils.isBlank(this.pTitle) ? "행추가" : this.pTitle);
				btnImg = "/common/images/ico_row_add.png";
				break;
			}
			if (StringUtils.isBlank(this.pType))
				this.pTitle = "버튼타입은 필수입니다";
			if (!btnChk)
				this.pTitle = String.valueOf(this.pTitle) + "(버튼타입등록필요)";
			button.append("<li><a href=\"javascript:void(0);\" id=\"" + this.pId + "\" onclick=\"" + this.pOnClick
					+ "\"><img src=\"" + btnImg + "\" onerror=\"this.style.display='none'\" ><span>" + this.pTitle
					+ "</span></a></li>");
			if (!StringUtils.isBlank(this.pTitle))
				setpTitle("");
			this.pageContext.getOut().print(button);
		} catch (Exception e) {
			this.logger.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return 0;
	}
}

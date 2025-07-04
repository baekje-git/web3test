package ever.support.taglibs;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormFileUpload2Tag extends TagSupport {
	private static final long serialVersionUID = -6916177973086405914L;

	final Logger logger = LoggerFactory.getLogger(getClass());

	int pFileNo = 0;

	String pFileId = "";

	String pFileNm = "";

	String pAccept = "";

	Boolean pMultiple = Boolean.valueOf(true);

	String pOnClick = "";

	String pCallbackFunction = "";

	public int getpFileNo() {
		return this.pFileNo;
	}

	public void setpFileNo(int pFileNo) {
		this.pFileNo = pFileNo;
	}

	public String getpFileId() {
		return this.pFileId;
	}

	public void setpFileId(String pFileId) {
		this.pFileId = pFileId;
	}

	public String getpFileNm() {
		return this.pFileNm;
	}

	public void setpFileNm(String pFileNm) {
		this.pFileNm = pFileNm;
	}

	public String getpAccept() {
		return this.pAccept;
	}

	public void setpAccept(String pAccept) {
		this.pAccept = pAccept;
	}

	public Boolean getpMultiple() {
		return this.pMultiple;
	}

	public void setpMultiple(Boolean pMultiple) {
		this.pMultiple = pMultiple;
	}

	public String getpOnClick() {
		return this.pOnClick;
	}

	public void setpOnClick(String pOnClick) {
		this.pOnClick = pOnClick;
	}

	public String getpCallbackFunction() {
		return this.pCallbackFunction;
	}

	public void setpCallbackFunction(String pCallbackFunction) {
		this.pCallbackFunction = pCallbackFunction;
	}

	public int doStartTag() throws JspException {
		if (StringUtils.isBlank(this.pCallbackFunction))
			return 0;
		StringBuffer fileBox = new StringBuffer();
		try {
			fileBox.append("<script type=\"text/javascript\">                           ").append("\r\n");
			fileBox.append("$(document).ready(function(){                               ").append("\r\n");
			fileBox.append("    $(document).on(\"change\" ,\"input#").append(this.pFileId)
					.append("[type='file']\", function(e) {").append("\r\n");
			fileBox.append("        var formData = new FormData();                                               ")
					.append("\r\n");
			fileBox.append("        var fileItems = e.originalEvent.target.files;                                ")
					.append("\r\n");
			fileBox.append("        if(fileItems.length == 0) return false;                                      ")
					.append("\r\n");
			fileBox.append("        if(fileItems.length == 1) {                                                  ")
					.append("\r\n");
			fileBox.append("            $(\"#INPUT_").append(this.pFileId).append("\").val(fileItems[0].name);   ")
					.append("\r\n");
			fileBox.append("        } else {                                                                     ")
					.append("\r\n");
			fileBox.append("            var fileName  = fileItems[0].name;                                       ")
					.append("\r\n");
			fileBox.append("            var etcCount  = fileItems.length-1;                                      ")
					.append("\r\n");
			fileBox.append("            var inputFile = fileName.concat(\" 외(\"+etcCount+\")개\");               ")
					.append("\r\n");
			fileBox.append("            $(\"#INPUT_").append(this.pFileId).append("\").val(inputFile);           ")
					.append("\r\n");
			fileBox.append("        }                                                                            ")
					.append("\r\n");
			fileBox.append("        var filesData = $(this);                                                     ")
					.append("\r\n");
			fileBox.append("        $.each(filesData,function(k,v){                                              ")
					.append("\r\n");
			fileBox.append("            var files = filesData[k].files;                                          ")
					.append("\r\n");
			fileBox.append("            for(var i=0;i<files.length;i++){                                         ")
					.append("\r\n");
			fileBox.append("                formData.append('UPLOAD_FILE',files[i]);                             ")
					.append("\r\n");
			fileBox.append("            }                                                                        ")
					.append("\r\n");
			fileBox.append("        });                                                                          ")
					.append("\r\n");
			fileBox.append("        var sendURL = \"/cmmnFile/fileUploadAttach.ajax\";                             ")
					.append("\r\n");
			fileBox.append("        CmmnUtil.ajax.form(sendURL, formData, function(data){                        ")
					.append("\r\n");
			if (StringUtils.isNotBlank(this.pCallbackFunction))
				fileBox.append(this.pCallbackFunction).append("(data);                                  ")
						.append("\r\n");
			fileBox.append("        });// END AJAX                                                               ")
					.append("\r\n");
			fileBox.append("    });// END CHANGE                                                                  ")
					.append("\r\n");
			fileBox.append("});// END READY                                                                      ")
					.append("\r\n");
			fileBox.append("</script>                                                                            ")
					.append("\r\n");
			fileBox.append("<ul class=\"btn_file_area\">");
			fileBox.append("<li>");
			fileBox.append("    <div class=\"filebox\">");
			fileBox.append("    <input type=\"text\" ");
			fileBox.append("             id=\"INPUT_").append(this.pFileId).append("\" ");
			fileBox.append("           name=\"INPUT_").append(this.pFileId).append("\" ");
			fileBox.append("          class=\"upload-name\" ");
			fileBox.append("          value=\"선택된 파일 없음\" ");
			fileBox.append("       readonly=\"readonly\" ").append("/>").append("\r\n");
			fileBox.append("    <label for=\"" + this.pFileId + "\" class=\"upload-serch\">찾기</label>");
			fileBox.append("    <input type=\"file\" ");
			fileBox.append("             id=\"").append(this.pFileId).append("\" ");
			fileBox.append("           name=\"").append("UPLOAD_FILE").append("\" ");
			fileBox.append("         accept=\"").append(this.pAccept).append("\" ");
			fileBox.append("          class=\"upload-hidden\" ");
			if (this.pMultiple.booleanValue()) {
				fileBox.append("       multiple=\"multiple\" ").append("/>").append("\r\n");
			} else {
				fileBox.append("       />").append("\r\n");
			}
			fileBox.append("</li>");
			fileBox.append("</ul>");
			this.pageContext.getOut().print(fileBox);
		} catch (IOException e) {
			this.logger.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		} catch (Exception e) {
			this.logger.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return 0;
	}
}

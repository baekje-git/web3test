package ever.support.file;

import ever.support.cmmn.CmmnUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownloadView extends AbstractView {
	private static final Logger logger = LoggerFactory.getLogger(FileDownloadView.class);

	public FileDownloadView() {
		setContentType("apllication/download; charset=utf-8");
	}

	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CmmnUtil.methodLog("FileDownloadView.renderMergedOutputModel");
		HashMap<String, Object> fileInfo = (HashMap<String, Object>) model.get("fileInfo");
		logger.debug(">>> fileInfo : " + fileInfo);
		String userAgent = request.getHeader("User-Agent");
		String filePhysicName = (String) fileInfo.get("FILE_NM");
		String fileLogicName = (String) fileInfo.get("ORG_FILE_NM");
		String fileExt = (String) fileInfo.get("FILE_EXT");
		String filePath = (String) fileInfo.get("FILE_PATH");
		String downloadPath = String.valueOf(filePath) + filePhysicName;
		logger.debug("downloadPath : " + downloadPath);
		String downloadfileName = null;
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			downloadfileName = URLEncoder.encode(fileLogicName, "UTF-8").replaceAll("\\+", "%20");
		} else {
			downloadfileName = new String(fileLogicName.getBytes("UTF-8"), "ISO-8859-1");
		}
		File file = new File(downloadPath);
		response.setContentType(getContentType());
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadfileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} catch (Exception e) {
			logger.error("Exception >>> -> [" + ExceptionUtils.getStackTrace(e) + "]");
			response.reset();
			response.setHeader("Content-Type", "text/html; charset=UTF-8");
			response.getWriter().println("<script language='javascript'>");
			response.getWriter().println("alert('파일이 존재하지 않습니다. 관리자에게 문의하여 주세요.');");
			response.getWriter().println("history.go(-1);");
			response.getWriter().println("</script>");
		} finally {
			FileUtil.closeQuietly(out);
			FileUtil.closeQuietly2(fis);
			out.flush();
		}
	}
}

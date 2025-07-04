package ever.support.excel;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import ever.support.cmmn.CmmnUtil;

public abstract class AbstractExcelView extends AbstractView {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractExcelView.class);
	private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	public AbstractExcelView(){}
	
	@Override
    protected boolean generatesDownloadContent() {
        return true;
    }

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CmmnUtil.methodLog("AbstractExcelView.renderMergedOutputModel");
		
		HashMap<String, Object> fileInfo = (HashMap<String, Object>) model.get("fileInfo");
		logger.debug(">>> fileInfo : " + fileInfo);
		String userAgent = request.getHeader("User-Agent");
		String fileName = (String) fileInfo.get("FILE_NM");		
		String downloadfileName = null;
		
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			downloadfileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else {
			downloadfileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		
		
		Workbook workbook = createWorkbook();

        setContentType(CONTENT_TYPE_XLSX);

        buildExcelDocument(model , workbook , request , response);

        response.setContentType(getContentType());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadfileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

        ServletOutputStream out = response.getOutputStream();
        out.flush();
        workbook.write(out);
        out.flush();
        
        if(workbook instanceof SXSSFWorkbook){
            ((SXSSFWorkbook) workbook).dispose();
        }
	}
	
	protected abstract Workbook createWorkbook();

    protected abstract void buildExcelDocument(Map<String,Object> model, Workbook workbook , HttpServletRequest request , HttpServletResponse response) throws Exception;
}

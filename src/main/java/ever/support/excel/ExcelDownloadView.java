package ever.support.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelDownloadView extends AbstractExcelView {

	private static final Logger logger = LoggerFactory.getLogger(ExcelDownloadView.class);
	
	@Override
	protected Workbook createWorkbook() {
		return new XSSFWorkbook();
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> fileInfo = (HashMap<String, Object>) model.get("fileInfo");
		HashMap<String, Object> data = (HashMap<String, Object>) fileInfo.get("DATA");
		logger.debug(">>> fileInfo : " + fileInfo);
		logger.debug("DATA==>" + data);
		
		List<String> sheetNames = (List<String>) data.get("sheet"); // sheet정보
		logger.debug("sheet info==>" + sheetNames);
		
		List<List<HashMap<String,Object>>> headerList = (List<List<HashMap<String,Object>>>) data.get("header"); // sheet별 컬럼정보
		logger.debug("header info==>" + headerList);
		
		List<List<HashMap<String,Object>>> dataList = (List<List<HashMap<String,Object>>>) data.get("data"); // sheet별 데이터정보
		
		int sheetCount = 0;
		for(String sheetName : sheetNames) {
			Sheet sheet = workbook.createSheet(sheetName);
	        Row row = null;
	        int rowCount = 0;
	        int cellCount = 0;
	        
	        row = sheet.createRow(rowCount++);
	        

	        for(HashMap<String,Object> header: headerList.get(sheetCount)) {
	        	row.createCell(cellCount++).setCellValue(String.valueOf(header.get("label")));
	        }

	        List<HashMap<String,Object>> sheetData = dataList.get(sheetCount);
	        for(HashMap<String,Object> rowData : sheetData) {
	        	row = sheet.createRow(rowCount++);
	            cellCount = 0;
	            
	            for(HashMap<String,Object> header: headerList.get(sheetCount)) {
	            	row.createCell(cellCount++).setCellValue(String.valueOf(rowData.get(String.valueOf(header.get("key")))));
		        }
	        }
	        sheetCount++;
		}
        
	}

}

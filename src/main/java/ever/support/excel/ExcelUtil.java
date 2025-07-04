package ever.support.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    
    public static ExcelReadBox readToList(MultipartFile multipartFile, String[] fieldNames){
        return readToList(multipartFile, fieldNames, 0, 0);
    }

    public static ExcelReadBox readToList(MultipartFile multipartFile, String[] fieldNames, int rowStartIndex, int colStartIndex){
        ExcelReadBox box     = new ExcelReadBox();
        String resultCode    = ExcelReadBox.RESULT_CODE_EMPTY;
        String resultMessage = ExcelReadBox.RESULT_MESSAGE_EMPTY;

        List<Map<String, Object>> list = null;
        Map<String, Object> map = null;

        if(multipartFile == null || multipartFile.getSize() <= 0){
            box.setResultCode(resultCode);
            box.setResultMessage(resultMessage);
            box.setList(list);
            return box;
        }

        try{
            String mimeType = multipartFile.getContentType();
            if("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(mimeType)
                || "application/octet-stream".equals(mimeType) || "application/haansoftxlsx".equals(mimeType))
            {
                list = new ArrayList<Map<String, Object>>();

                XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
                XSSFSheet sheet = workbook.getSheetAt(0);
                XSSFRow row     = null;
                XSSFCell cell   = null;
                String value    = null;

                for(int rowIndex = rowStartIndex; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++){
                    map = new HashMap<String, Object>();
                    row = sheet.getRow(rowIndex);

                    if(row != null){
                        for(int colIndex = colStartIndex; colIndex < fieldNames.length; colIndex++){
                            cell  = row.getCell(colIndex);
                            value = "";
                            if(cell != null){
                                switch(cell.getCellType()){
                                case FORMULA :
                                    value = cell.getCellFormula();
                                    break;
                                case NUMERIC:
                                    if(DateUtil.isCellDateFormatted(cell)) {
                                        value = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                                    }else{
                                        value = new DecimalFormat("#.##").format(cell.getNumericCellValue());
                                    }
                                    break;
                                case STRING:
                                    value = cell.getStringCellValue();
                                    break;
                                case BOOLEAN:
                                    value = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case BLANK:
                                    value = "";
                                    break;
                                case ERROR:
                                    value = String.valueOf(cell.getErrorCellValue());
                                    break;
                                default : value = ""; break;
                                }
                            }
                            map.put(fieldNames[colIndex], value);
                        }
                    }
                    list.add(map);
                }
                resultCode = ExcelReadBox.RESULT_CODE_SUCCESS;
                resultMessage = ExcelReadBox.RESULT_MESSAGE_SUCCESS;
            }
            else if("application/vnd.ms-excel".equals(mimeType)){
                resultCode = ExcelReadBox.RESULT_CODE_NOT_XLSX;
                resultMessage = ExcelReadBox.RESULT_MESSAGE_NOT_XLSX;
                logger.debug("not xlsx");
            }
            else{
                resultCode = ExcelReadBox.RESULT_CODE_NOT_EXCEL;
                resultMessage = ExcelReadBox.RESULT_MESSAGE_NOT_EXCEL;
            }
        }catch(RuntimeException e){
            resultCode = ExcelReadBox.RESULT_CODE_ERROR;
            resultMessage = ExcelReadBox.RESULT_MESSAGE_ERROR;
            box.setStackTraceMessage(ExceptionUtils.getMessage(e));

            logger.error(ExceptionUtils.getStackTrace(e));
        }catch(FileNotFoundException e){
            resultCode = ExcelReadBox.RESULT_CODE_ERROR;
            resultMessage = ExcelReadBox.RESULT_MESSAGE_ERROR;
            box.setStackTraceMessage(ExceptionUtils.getMessage(e));

            logger.error(ExceptionUtils.getStackTrace(e));
        }catch(IOException e){
            resultCode = ExcelReadBox.RESULT_CODE_ERROR;
            resultMessage = ExcelReadBox.RESULT_MESSAGE_ERROR;
            box.setStackTraceMessage(ExceptionUtils.getMessage(e));

            logger.error(ExceptionUtils.getStackTrace(e));
        }catch(Exception e){
            resultCode = ExcelReadBox.RESULT_CODE_ERROR;
            resultMessage = ExcelReadBox.RESULT_MESSAGE_ERROR;
            box.setStackTraceMessage(e.getMessage());

            logger.error(ExceptionUtils.getStackTrace(e));
        }
            
        box.setResultCode(resultCode);
        box.setResultMessage(resultMessage);
        box.setList(list);
        return box;
    }
    
}

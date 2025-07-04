package ever.support.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelReadBox {
    
    public final static String RESULT_CODE_SUCCESS     = "100";
    public final static String RESULT_CODE_EMPTY       = "200";
    public final static String RESULT_CODE_NOT_EXCEL   = "300";
    public final static String RESULT_CODE_NOT_XLSX    = "301";
    public final static String RESULT_CODE_UNVALID     = "800";
    public final static String RESULT_CODE_ERROR       = "900";
    public final static String RESULT_MESSAGE_SUCCESS  = "정상적으로 업로드 되었습니다.";
    public final static String RESULT_MESSAGE_EMPTY     = "선택된 파일이 없습니다.";
    public final static String RESULT_MESSAGE_NOT_EXCEL = "엑셀 파일이 아닙니다.";
    public final static String RESULT_MESSAGE_NOT_XLSX  = "xlsx확장자 파일만 업로드 가능합니다.";
    public final static String RESULT_MESSAGE_UNVALID   = "유효성 검증에 실패했습니다.";
    public final static String RESULT_MESSAGE_ERROR     = "처리중 오류가 발생했습니다.";
    
    private String resultCode;
    private String resultMessage;
    private String stackTraceMessage;
    
    private List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultMessage() {
        return resultMessage;
    }
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    public String getStackTraceMessage() {
        return stackTraceMessage;
    }
    public void setStackTraceMessage(String stackTraceMessage) {
        this.stackTraceMessage = stackTraceMessage;
    }
    public List<Map<String, Object>> getList() {
        List<Map<String, Object>> alistRet = new ArrayList<Map<String,Object>>();
        alistRet.addAll(list);
        return alistRet;
    }
    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }
}

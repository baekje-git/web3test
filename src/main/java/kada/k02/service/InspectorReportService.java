package kada.k02.service;

import java.util.HashMap;

public interface InspectorReportService {
	public void saveInspectorReportViewCnt(HashMap<String, Object> hMap) throws Exception;
	public void saveInspectorReport(HashMap<String, Object> hMap) throws Exception;
}

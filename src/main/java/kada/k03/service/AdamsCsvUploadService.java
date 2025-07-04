package kada.k03.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface AdamsCsvUploadService {
	public List<HashMap> saveCsv(String gubun, List<CSVRecord> records) throws Exception;
	
	public int updateCsvResultMgmt( HashMap<String, Object> hMap) throws Exception;
	public int deleteCsvResultMgmt( HashMap<String, Object> hMap) throws Exception;
}

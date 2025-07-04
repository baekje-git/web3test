package kada.k03.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface AccTestResultService {
	public void saveUnitAmout(List<HashMap<String, Object>> hList) throws Exception;
	public void saveLabBalance(HashMap<String, Object> hMap) throws Exception;
}

package kada.k99.service;

import java.util.HashMap;
import java.util.List;

public interface ChemService {
	public void saveChem(HashMap<String, Object> hList) throws Exception;
	public void saveInspRecord(HashMap<String, Object> hList) throws Exception;
	public void saveCmpsRecord(HashMap<String, Object> hList) throws Exception;
	public void saveDspsRecord(HashMap<String, Object> hList) throws Exception;
	public void savePackRecord(HashMap<String, Object> hList) throws Exception;
	public void saveProdNo(HashMap<String, Object> hList) throws Exception;
	
	
}

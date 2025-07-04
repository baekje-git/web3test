package kada.k99.service;

import java.util.HashMap;
import java.util.List;

public interface FinishService {
	public void saveFnReq(HashMap<String, Object> hList) throws Exception;
	public void saveFnIns(HashMap<String, Object> hList) throws Exception;
	public void saveFnRep(HashMap<String, Object> hList) throws Exception;
	public void saveFnRec(HashMap<String, Object> hList) throws Exception;
	public void saveFnSt(HashMap<String, Object> hList) throws Exception;
	public void saveFnCs(HashMap<String, Object> hList) throws Exception;
	public void saveFnFc(HashMap<String, Object> hList) throws Exception;
	
	
	
}

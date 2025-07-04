package kada.k03.service;

import java.util.HashMap;

public interface DopingInspectService {
	
	public void saveDopingInspect(HashMap<String, Object> hMap) throws Exception;
	public void deleteDopingInspect(HashMap<String, Object> hMap) throws Exception;
	public void addDopingInspectReadHist(HashMap<String, Object> hMap) throws Exception;
	public void sendSMS(HashMap<String, Object> hMap) throws Exception;
	public void deleteChoose(HashMap<String, Object> hMap) throws Exception;
	public void saveChoose(HashMap<String, Object> hMap) throws Exception;
	public void saveComment(HashMap<String, Object> hMap) throws Exception;
	public void deleteComment(HashMap<String, Object> hMap) throws Exception;
	public void chooseComplete(HashMap<String, Object> hMap) throws Exception;
}

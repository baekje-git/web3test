package kada.k99.service;

import java.util.HashMap;
import java.util.List;

public interface RoleService {
	public void saveRole(HashMap<String, Object> hMap) throws Exception;
	public void triggerSaveRole(HashMap<String, Object> hMap) throws Exception;
}

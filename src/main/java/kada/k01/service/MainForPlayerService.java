package kada.k01.service;

import java.util.HashMap;

public interface MainForPlayerService {
	public int insertSchedule(HashMap<String, Object> hMap) throws Exception;
	public int insertRepeatSchedule(HashMap<String, Object> hMap) throws Exception;
}

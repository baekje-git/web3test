package baekje.web.comOrd.dao;

import java.util.HashMap;

public interface ComOrdDao {
	int insertSearchIpHistory(HashMap<String, Object> paramMap) throws Exception;

	int saveComOrdWebgate(HashMap<String, Object> paramMap) throws Exception;
}

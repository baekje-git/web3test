package baekje.web.aniOrd.dao;

import java.util.HashMap;

public interface AniOrdDao {
	int insertSearchIpHistory_ani(HashMap<String, Object> paramMap) throws Exception;

	int saveComOrdWebgate_ani(HashMap<String, Object> paramMap) throws Exception;
}

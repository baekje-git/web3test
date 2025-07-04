package baekje.web.aniOrd.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface AniOrdService {
	int insertSearchIpHistory_ani(HashMap<String, Object> hMap) throws Exception;

	int deleteComOrdBasket_ani(HashMap<String, Object> paramMap) throws Exception;

	int saveComOrdWebgateList_ani(List<HashMap<String, Object>> hMap) throws Exception;
}

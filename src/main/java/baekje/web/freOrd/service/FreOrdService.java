package baekje.web.freOrd.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface FreOrdService {
	int insertSearchIpHistory(HashMap<String, Object> hMap) throws Exception;

	ResponseEntity<Object> deleteFreOrdBasket(HashMap<String, Object> paramMap) throws Exception;

	int saveFreOrdWebgateList(List<HashMap<String, Object>> hMap) throws Exception;
}

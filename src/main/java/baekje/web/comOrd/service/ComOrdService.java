package baekje.web.comOrd.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ComOrdService {
	int insertSearchIpHistory(HashMap<String, Object> hMap) throws Exception;

	int deleteComOrdBasket(HashMap<String, Object> paramMap) throws Exception;

	int saveComOrdWebgateList(List<HashMap<String, Object>> hMap) throws Exception;
	
	void saveBasketItem(HashMap<String, Object> hMap) throws Exception;
}

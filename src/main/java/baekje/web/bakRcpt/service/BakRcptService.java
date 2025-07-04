package baekje.web.bakRcpt.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface BakRcptService {
	ResponseEntity<Object> deleteBakRcptBasket(HashMap<String, Object> paramMap) throws Exception;

	int saveBakRcpt(List<HashMap<String, Object>> hMap) throws Exception;
	int saveBakRcptImsi(List<HashMap<String, Object>> hMap) throws Exception;
	
}

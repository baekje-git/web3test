package baekje.web.bakRcpt.dao;

import java.util.HashMap;

public interface BakRcptDao {

	int updateBakRcptBasketItemSave(HashMap<String, Object> paramMap) throws Exception;
	int updateBakRcptBasketItemImsiSave(HashMap<String, Object> paramMap) throws Exception;

	int updateBasketDelYn(HashMap<String, Object> paramMap) throws Exception;

}

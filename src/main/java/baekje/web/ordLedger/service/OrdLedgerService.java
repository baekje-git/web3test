package baekje.web.ordLedger.service;

import java.util.HashMap;
import java.util.List;

public interface OrdLedgerService {

	int insertOrdLedgerSignFile(HashMap<String, Object> hMap) throws Exception;
    public List< Object> selectSalesBranchMobile(HashMap<String, Object> paramMap)  throws Exception;
}

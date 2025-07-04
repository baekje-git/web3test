package baekje.web.bakReq.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ever.cmmn.service.CmmnService;

@Service
public class BakReqServiceImpl implements BakReqService {

	@Autowired
	CmmnService cmmnService;

	@Override
	@Transactional
	public int updateBakMemo(HashMap<String, Object> paramMap) throws Exception {

		int sal00010Cnt = (int) cmmnService.select("bakReq.selectCntSAL00010", paramMap);
		int sal00013Cnt = (int) cmmnService.select("bakReq.selectCntSAL00013", paramMap);

		if (sal00010Cnt > 0) cmmnService.update("bakReq.updateOrdMemoSAL00010", paramMap);
		if (sal00013Cnt > 0) cmmnService.update("bakReq.updateOrdMemoSAL00013", paramMap);

		return 1;

	}

}

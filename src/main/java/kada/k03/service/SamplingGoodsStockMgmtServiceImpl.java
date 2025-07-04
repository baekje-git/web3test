package kada.k03.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;
import ever.support.session.SessionUtil;
import kada.util.Util;

@Service
public class SamplingGoodsStockMgmtServiceImpl implements SamplingGoodsStockMgmtService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public void saveSampleGoodsMgntReg(HashMap<String, Object> hMap) throws Exception {
		List<HashMap<String, Object>> hList = (List<HashMap<String, Object>>) hMap.get("SAMPLE_GOODS");
		
		for(HashMap<String, Object> row : hList) {
			String mode = Util.getValue("_STATUS", row);
			row.put("GSS", SessionUtil.getUserInfo());
			
			if("I".equals(mode)) {
				cmmnDao.insert("k03.SampleGoods.insertSAMPLE_Goods_STOCK", row);			
			}	
		}
	}

	
}

package kada.k02.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.session.SessionUtil;
import kada.util.Util;

@Service
public class InspectorMutualEvalServiceImpl implements InspectorMutualEvalService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;

	@Override
	public void saveEvalResult(HashMap<String, Object> hMap) throws Exception {
		String ipntNo = Util.getValue("IPNT_NO", hMap);
		String ipntUpperSeq = Util.getValue("IPNT_UPPER_SEQ", hMap);
		String evaluatorId = Util.getValue("EVALUATOR_ID", hMap);
		List<HashMap<String,Object>> items = (List<HashMap<String, Object>>) hMap.get("INPUT");
		
		if(items != null) {
			for(HashMap<String,Object> row : items) {
				row.put("IPNT_NO", ipntNo);
				row.put("IPNT_UPPER_SEQ", ipntUpperSeq);
				row.put("EVALUATOR_ID", evaluatorId);
				row.put("GSS", SessionUtil.getUserInfo());
				HashMap<String,Object> evalResult = (HashMap<String, Object>) row.get("EVAL_RESULT");
				row.put("DTL_SEQ", evalResult.get("DTL_SEQ"));
				row.put("EVAL_VALUE", evalResult.get("EVAL_VALUE"));				
				cmmnDao.update("k02.inspectorMutualEval.saveEvalResult", row);
			}
		}
	}

}

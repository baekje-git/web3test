package kada.k02.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.session.SessionUtil;

@Service
public class InspectorReportServiceImpl implements InspectorReportService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Override
	public void saveInspectorReportViewCnt(HashMap<String, Object> hMap) throws Exception {
		hMap.put("GSS", SessionUtil.getUserInfo());
		cmmnDao.insert("k02.inspectorReport.insertIPNT04TB",hMap);		
	}

	@Override
	public void saveInspectorReport(HashMap<String, Object> hMap) throws Exception {
		HashMap<String,Object> inspectorReg = (HashMap<String, Object>) hMap.get("INSPECTOR_REG");
		HashMap<String,Object> inspectorOrder = (HashMap<String, Object>) hMap.get("INSPECTOR_ORDER");
		List<HashMap<String,Object>> samples = (List<HashMap<String, Object>>) hMap.get("INSPECTOR_SAMEPLES");
		List<HashMap<String,Object>> stock = (List<HashMap<String, Object>>) hMap.get("INSPECTOR_STOCK");
		
		inspectorOrder.put("IPNT_UPPER_SEQ", hMap.get("IPNT_UPPER_SEQ"));
		inspectorOrder.put("IPNT_NO", hMap.get("IPNT_NO"));
		inspectorOrder.put("REG_USER", hMap.get("USER_ID"));
		inspectorOrder.put("EDT_DATE", hMap.get("USER_ID"));
		inspectorOrder.put("EDT_DATE", hMap.get("USER_ID"));
		inspectorOrder.putAll((Map<? extends String, ? extends Object>) hMap.get("TABLE"));
		logger.info("inspectorOrder===>",inspectorOrder.toString());
		
		List<Object> lstIPDN01TB = cmmnDao.selectList("k02.inspectorReport.selectIPDN01TBByPk", inspectorOrder);
		
		if (lstIPDN01TB == null || lstIPDN01TB.size() <= 0) {
			cmmnDao.insert("k02.inspectorReport.insertIPDN01TB", inspectorOrder);
        } else {
        	cmmnDao.update("k02.inspectorReport.updateIPDN01TB", inspectorOrder);
        }
		
		// 시료
		cmmnDao.delete("k02.inspectorReport.deleteIPDN02TB", inspectorOrder);
		for (int j = 0 ; samples != null && j < samples.size() ; j++) {
			HashMap<String,Object> sample = samples.get(j);
			sample.putAll((Map<? extends String, ? extends Object>) hMap.get("TABLE"));
			cmmnDao.insert("k02.inspectorReport.insertIPDN02TB", sample);
        }
		
		cmmnDao.update("k02.inspectorReport.updateIPNTDN02TBByUNIT_AMOUNT", inspectorOrder);
		
		// 용품 사용/반납
		cmmnDao.delete("k03.SampleGoods.deleteSAMPLE_ITEM_STOCK", inspectorOrder);
        for (int j = 0 ; stock != null && j < stock.size() ; j++) {
        	HashMap<String,Object> stockHistory = stock.get(j);
        	stockHistory.putAll((Map<? extends String, ? extends Object>) hMap.get("TABLE"));
        	cmmnDao.insert("k03.SampleGoods.insertSAMPLE_ITEM_STOCK", stockHistory);
        }
		
		// 시료별 수당
        cmmnDao.delete("k02.inspectorReport.deleteIPDN04TB", inspectorOrder);
        cmmnDao.update("k02.inspectorReport.insertIPDN04TB", inspectorOrder);
	}

	

}

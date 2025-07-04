package kada.k99.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;

@Service
public class ChemServiceImpl implements ChemService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public void saveProdNo( HashMap<String, Object> hList) throws Exception {
		
		//List<HashMap<String, Object>> Chemlist= hList.get("Chemlist");
		HashMap<String, Object> ProdNoDetail= (HashMap<String, Object>)hList.get("ProdNoDetail");
		cmmnDao.insert("chem.PROD_NO_CHANGE", ProdNoDetail);
        
		//for(HashMap<String, Object> row : Chemlist) {
		//	cmmnDao.insert("k04.result.insertChem", row);
        //}
		
	}
	
	@Override
	public void saveChem( HashMap<String, Object> hList) throws Exception {
		
		//List<HashMap<String, Object>> Chemlist= hList.get("Chemlist");
		HashMap<String, Object> ChemDetail= (HashMap<String, Object>)hList.get("ChemDetail");
		ChemDetail.put("INS_NM", hList.get("INS_NM"));
		ChemDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("chem.changeFdgBpr", ChemDetail);
        
		//for(HashMap<String, Object> row : Chemlist) {
		//	cmmnDao.insert("k04.result.insertChem", row);
        //}
		
	}
	
	@Override
	public void saveInspRecord( HashMap<String, Object> hList) throws Exception {
		
		//List<HashMap<String, Object>> Chemlist= hList.get("Chemlist");
		HashMap<String, Object> InspRecordDetail= (HashMap<String, Object>)hList.get("InspRecordDetail");
		InspRecordDetail.put("INS_NM", hList.get("INS_NM"));
		InspRecordDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("chem.changeFdgCsfm", InspRecordDetail);
        
		//for(HashMap<String, Object> row : Chemlist) {
		//	cmmnDao.insert("k04.result.insertChem", row);
        //}
		
	}
	
	@Override
	public void saveCmpsRecord( HashMap<String, Object> hList) throws Exception {
		
		//List<HashMap<String, Object>> Chemlist= hList.get("Chemlist");
		HashMap<String, Object> CmpsRecordDetail= (HashMap<String, Object>)hList.get("CmpsRecordDetail");
		CmpsRecordDetail.put("INS_NM", hList.get("INS_NM"));
		CmpsRecordDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("chem.changeFdgCmps", CmpsRecordDetail);
        
		//for(HashMap<String, Object> row : Chemlist) {
		//	cmmnDao.insert("k04.result.insertChem", row);
        //}
		
	}
	
	@Override
	public void saveDspsRecord( HashMap<String, Object> hList) throws Exception {
		
		//List<HashMap<String, Object>> DspsRecordDetail= hList.get("DspsRecordDetail");
		HashMap<String, Object> DspsRecordDetail= (HashMap<String, Object>)hList.get("DspsRecordDetail");
		DspsRecordDetail.put("INS_NM", hList.get("INS_NM"));
		DspsRecordDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("chem.changeFdgDsps", DspsRecordDetail);

		//for(HashMap<String, Object> row : DspsRecordDetail) {
		//	cmmnDao.insert("k04.result.insertChem", row);
        //}
		
	}
	
	@Override
	public void savePackRecord( HashMap<String, Object> hList) throws Exception {
		
		//List<HashMap<String, Object>> DspsRecordDetail= hList.get("DspsRecordDetail");
		HashMap<String, Object> PackRecordDetail= (HashMap<String, Object>)hList.get("PackRecordDetail");
		PackRecordDetail.put("INS_NM", hList.get("INS_NM"));
		PackRecordDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("chem.changeFdgPack", PackRecordDetail);

		//for(HashMap<String, Object> row : PackRecordDetail) {
		//	cmmnDao.insert("k04.result.insertChem", row);
        //}
		
	}
}

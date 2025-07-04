package kada.k03.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.session.SessionUtil;

@Service
public class AdamsCsvUploadServiceImpl implements AdamsCsvUploadService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;

	@Override
	public List<HashMap> saveCsv(String gubun, List<CSVRecord> records) throws Exception {
		List<HashMap> errors = new ArrayList<HashMap>();
		for (CSVRecord record : records) {
			HashMap<String, Object> row = new HashMap<>(record.toMap());
			int cnt = (int) cmmnDao.select("k03.adams.selectCsvCheck"+gubun, row);
            if(cnt == 0) {
            	cmmnDao.insert("k03.adams.insertCsv"+gubun, row);
            }else {
            	errors.add(row);
            }
        }
		
		return errors;
	}
	
	@Override
	public int updateCsvResultMgmt( HashMap<String, Object> hMap) throws Exception {
		int errCnt= -1;
		hMap.put("GSS", SessionUtil.getUserInfo());
		String strGubun = hMap.get("GUBUN").toString();
		if("selectCsvNEG".equals(strGubun)) {
			errCnt=cmmnDao.update("k03.adams.updateCsvNEG", hMap);
		}else if("selectCsvBLOOD".equals(strGubun)) {
			errCnt=cmmnDao.update("k03.adams.updateCsvBLOOD", hMap);
		}else if("selectCsvABP".equals(strGubun)) {
			errCnt=cmmnDao.update("k03.adams.updateCsvABP", hMap);
		}else {
			errCnt = -1;
		}
		
		return errCnt;
	}
	
	@Override
	public int deleteCsvResultMgmt( HashMap<String, Object> hMap) throws Exception {
		int errCnt= -1;
		hMap.put("GSS", SessionUtil.getUserInfo());
		String strGubun = hMap.get("GUBUN").toString();
		if("selectCsvNEG".equals(strGubun)) {
			errCnt=cmmnDao.update("k03.adams.deleteCsvNEG", hMap);
		}else if("selectCsvBLOOD".equals(strGubun)) {
			errCnt=cmmnDao.update("k03.adams.deleteCsvBLOOD", hMap);
		}else if("selectCsvABP".equals(strGubun)) {
			errCnt=cmmnDao.update("k03.adams.deleteCsvABP", hMap);
		}else {
			errCnt = -1;
		}
		
		return errCnt;
	}
}

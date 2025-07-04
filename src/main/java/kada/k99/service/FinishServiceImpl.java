package kada.k99.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;

@Service
public class FinishServiceImpl implements FinishService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	/* 완제품 시험 의뢰서 */
	@Override
	public void saveFnReq( HashMap<String, Object> hList) throws Exception {
		//List<HashMap<String, Object>> Chemlist= hList.get("Chemlist");
		HashMap<String, Object> FnReqDetail= (HashMap<String, Object>)hList.get("FnReqDetail");
		FnReqDetail.put("INS_NM", hList.get("INS_NM"));
		FnReqDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("finish.insertFnReq", FnReqDetail);
		cmmnDao.insert("finish.insertFnIns", FnReqDetail);

		
		//for(HashMap<String, Object> row : Chemlist) {
		//	cmmnDao.insert("k04.result.insertChem", row);
        //}
	}
	/* 완제품 시험 지시서 */
	@Override
	public void saveFnIns( HashMap<String, Object> hList) throws Exception {
		
		HashMap<String, Object> FnInsDetail= (HashMap<String, Object>)hList.get("FnInsDetail");
		FnInsDetail.put("INS_NM", hList.get("INS_NM"));
		FnInsDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("finish.insertFnIns", FnInsDetail);

	}
	/* 완제품 시험 성적서 */
	@Override
	public void saveFnRep( HashMap<String, Object> hList) throws Exception {
		
		HashMap<String, Object> FnRepDetail= (HashMap<String, Object>)hList.get("FnRepDetail");
		FnRepDetail.put("INS_NM", hList.get("INS_NM"));
		FnRepDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("finish.insertFnRep", FnRepDetail);

	}
	
	/* 완제품 시험 기록서 */
	@Override
	public void saveFnRec( HashMap<String, Object> hList) throws Exception {
		
		HashMap<String, Object> FnRecDetail= (HashMap<String, Object>)hList.get("FnRecDetail");
		FnRecDetail.put("INS_NM", hList.get("INS_NM"));
		FnRecDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("finish.insertFnRec", FnRecDetail);

	}
	
	/* 무균 시험 성적서 */
	@Override
	public void saveFnSt( HashMap<String, Object> hList) throws Exception {
		
		HashMap<String, Object> FnStDetail= (HashMap<String, Object>)hList.get("FnStDetail");
		FnStDetail.put("INS_NM", hList.get("INS_NM"));
		FnStDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("finish.insertFnSt", FnStDetail);

	}
	
	/* 완제품 조건부출하승인서 */
	@Override
	public void saveFnCs( HashMap<String, Object> hList) throws Exception {
		
		HashMap<String, Object> FnCsDetail= (HashMap<String, Object>)hList.get("FnCsDetail");
		FnCsDetail.put("INS_NM", hList.get("INS_NM"));
		FnCsDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("finish.insertFnCs", FnCsDetail);

	}
	
	/* 완제품 최종출하승인서 */
	@Override
	public void saveFnFc( HashMap<String, Object> hList) throws Exception {
		
		HashMap<String, Object> FnFcDetail= (HashMap<String, Object>)hList.get("FnFcDetail");
		FnFcDetail.put("INS_NM", hList.get("INS_NM"));
		FnFcDetail.put("MODE_NM", hList.get("MODE_NM"));
		cmmnDao.insert("finish.insertFnFc", FnFcDetail);

	}
}

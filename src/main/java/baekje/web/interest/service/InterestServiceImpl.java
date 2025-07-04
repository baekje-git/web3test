package baekje.web.interest.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ever.cmmn.service.CmmnService;

@Service
public class InterestServiceImpl implements InterestService {

	@Autowired
	CmmnService cmmnService;

	@Override
	@Transactional
	public int insertInterestItem(HashMap<String, Object> paramMap) throws Exception {
		cmmnService.insert("interest.insertInterestItem", paramMap);

		return 1;
	}

	@Override
	@Transactional
	public int deleteInterestItem(HashMap<String, Object> paramMap) throws Exception {
		cmmnService.delete("interest.deleteInterestItem", paramMap);
		
		return 1;
	}

	@Override
	@Transactional
	public int updateAlarmYn(HashMap<String, Object> paramMap) throws Exception {
		cmmnService.update("interest.updateAlarmYn", paramMap);
		
		return 1;
	}
	
	@Override
	@Transactional
	public int deleteMyItems(HashMap<String, Object> paramMap) throws Exception {
		cmmnService.delete("interest.deleteMyItems", paramMap);
		
		return 1;
	}

	@Override
	@Transactional
	public int insertGroup(HashMap<String, Object> paramMap) throws Exception {
		cmmnService.insert("interest.insertGroup", paramMap);
		
		return 1;
	}

	@Override
	@Transactional
	public int deleteGroups(List<HashMap<String, Object>> paramMap) throws Exception {
		for (int i = 0; i < paramMap.size(); i++) {
			cmmnService.delete("interest.deleteAllMyItems", paramMap.get(i));
			cmmnService.delete("interest.deleteGroups", paramMap.get(i));
		}
		
		return 1;
	}

	@Override
	@Transactional
	public int updateSortNo(List<HashMap<String, Object>> paramMap) throws Exception {
		for (int i = 0; i < paramMap.size(); i++) {
			cmmnService.update("interest.updateSortNo", paramMap.get(i));
		}
		
		return 1;
	}
	
	@Override
	@Transactional
	public int updateGroupNo(List<HashMap<String, Object>> paramMap) throws Exception {
		for (int i = 0; i < paramMap.size(); i++) {
			cmmnService.update("interest.updateGroupNo", paramMap.get(i));
		}
		
		return 1;
	}

	@Override
	@Transactional
	public int updateMonthQty(HashMap<String, Object> paramMap) throws Exception {
		cmmnService.update("interest.updateMonthQty", paramMap);
		
		return 1;
	}

	@Override
	@Transactional
	public int updateAllAlarmYn(HashMap<String, Object> paramMap) throws Exception {
		cmmnService.update("interest.updateAllAlarmYn", paramMap);
		
		return 1;
	}

	@Override
	@Transactional
	public int updateGroupNm(HashMap<String, Object> paramMap) throws Exception {
		cmmnService.update("interest.updateGroupNm", paramMap);
		
		return 1;
	}

}

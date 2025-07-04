package kada.k03.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ever.cmmn.dao.CmmnDao;
import ever.support.session.SessionUtil;
import kada.modoc.ModocApi;
import kada.util.EmailService;
import kada.util.Util;

@Service
public class PlayerLocationServiceImpl implements PlayerLocationService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Override
	public void insertPlayerLocationDefaultReg(HashMap<String, Object> hList, String ipaddr, String userAgent) throws Exception {
		
		HashMap<String, Object> PlayerLocationMap= (HashMap<String, Object>)hList.get("playerLocationDefault");
		
		//공지사항채번 selectBDNT01TBSequence
		int maxSeq=0;
    	
		maxSeq=(int)cmmnDao.select("k03.playerlocationdefault.selectPlayerLocationDefaultSequence", PlayerLocationMap);
		PlayerLocationMap.put("SEQ", maxSeq);
		PlayerLocationMap.put("USER_AGENT",userAgent);
		PlayerLocationMap.put("IP_ADDR", ipaddr);
		//공지사항 입력 insertBDNT01TB
		cmmnDao.insert("k03.playerlocationdefault.insertPlayerWhereAbouts", PlayerLocationMap);
		
	}
	
	

}

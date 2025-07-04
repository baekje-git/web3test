package kada.k03.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;
import ever.support.session.SessionUtil;
import kada.util.EmailSender;
import kada.util.Util;

@Service
public class ResultWriteListServiceImpl implements ResultWriteListService {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Override
	public void saveResultWriteListRegist(HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> paperMst = (HashMap<String, Object>) hMap.get("PAPERMST"); // 기본정보,추가정보
		List<HashMap<String, Object>> paperDtl = (List<HashMap<String, Object>>) hMap.get("PAPERDTAIL"); // 경력사항
		
		// 기본정보,추가정보
		cmmnDao.insert("k03.ResultWrite.insertPaperTest", paperMst);

		// 경력사항
		int ipntSubSeq = 0;
		for (HashMap<String, Object> row : paperDtl) {
			//row.put("USER_ID", userId);
			
			row.put("IPNT_NO", paperMst.get("IPNT_NO").toString());
			row.put("IPNT_SUB_SEQ", ++ipntSubSeq);
			cmmnDao.insert("k03.ResultWrite.insertPaperTestDtl", row);
		}
	}


}

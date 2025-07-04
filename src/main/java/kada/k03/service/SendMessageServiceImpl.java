package kada.k03.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import kada.k03.service.SendMessageService;

@Service
public class SendMessageServiceImpl implements SendMessageService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public void saveSendMessageReq( HashMap<String, Object> hList) throws Exception {
		
		List<HashMap<String, Object>> sendMessagelist= (List<HashMap<String, Object>>)hList.get("sendMessagelist");
		for(HashMap<String, Object> row : sendMessagelist) {
			cmmnDao.insert("k03.SendMessage.insertPlayerRTP", row);
        }
		
	}
}

package kada.k06.service;

import java.util.HashMap;
import java.util.List;

public interface NoticeService {
	public void saveVoteResult(List<HashMap<String, Object>> hList) throws Exception;
	public void saveNoticeReg(HashMap<String, Object> hList, String ipaddr, String userAgent) throws Exception;
	public void saveNoticeEdit(HashMap<String, Object> hList, String ipaddr, String userAgent) throws Exception;
}

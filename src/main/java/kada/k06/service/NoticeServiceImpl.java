package kada.k06.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	@Override
	public void saveVoteResult(List<HashMap<String, Object>> hList) throws Exception {
		
		int check=0;
		
		for(HashMap<String, Object> row : hList) {
		        if(check==0) {
			    //이전투표결과삭제
			    cmmnDao.delete("k06.notice.deleteBDNT01TB_VOTE_RESULT", row);
		        }
		        if(!"".equals(row.get("REG_USER"))){
		        //새로운 투표결과 입력
				cmmnDao.insert("k06.notice.insertBDNT01TB_VOTE_RESULT", row);
		        }
		        check++;
	    }
	}
	
	@Override
	public void saveNoticeReg( HashMap<String, Object> hList,String ipaddr,String userAgent) throws Exception {
		
		HashMap<String, Object> noticeMap= (HashMap<String, Object>)hList.get("noticeDetail");
		
		//공지사항채번 selectBDNT01TBSequence
		int maxNoticeSeq=0;
    	
		maxNoticeSeq=(int)cmmnDao.select("k06.notice.selectBDNT01TBSequence", noticeMap);
		noticeMap.put("BOARD_SEQ", maxNoticeSeq);
		noticeMap.put("USER_AGENT",userAgent);
		noticeMap.put("IP_ADDR", ipaddr);
		//공지사항 입력 insertBDNT01TB
		cmmnDao.insert("k06.notice.insertBDNT01TB", noticeMap);
		
		
		//투표아이템 루프돌렴서 입력
		
		int check=0;
		List<HashMap<String, Object>> voteitemlist= (List<HashMap<String, Object>>)hList.get("voteitemlist");
		for(HashMap<String, Object> row : voteitemlist) {
			row.put("BOARD_SEQ", maxNoticeSeq);
        	row.put("ITEM_SEQ", check);
    		
			if(check==0) {
		    //이전투표item삭제	        	
		    cmmnDao.delete("k06.notice.deleteBDNT01TB_VOTE_ITEM", row);
	        }
	        
	        if(!"".equals(row.get("REG_USER"))){
	        //새로운 투표item 입력
			cmmnDao.insert("k06.notice.insertBDNT01TB_VOTE_ITEM", row);
	        }
	        check++;
    }
	}
    
	@Override
	public void saveNoticeEdit( HashMap<String, Object> hList,String ipaddr,String userAgent) throws Exception {
		
		List<HashMap<String, Object>> noticeList= (List<HashMap<String, Object>>)hList.get("noticeDetail");
		int maxNoticeSeq=0;

		for(HashMap<String, Object> row : noticeList) {
		
		//공지사항채번 selectBDNT01TBSequence
		
		maxNoticeSeq =(int)row.get("BOARD_SEQ");
		row.put("USER_AGENT",userAgent);
		row.put("IP_ADDR", ipaddr);
		//공지사항 입력 insertBDNT01TB
		cmmnDao.insert("k06.notice.updateBDNT01TB", row);
		
		}
		//투표아이템 루프돌렴서 입력
		
		int check=0;
		List<HashMap<String, Object>> voteitemlist= (List<HashMap<String, Object>>)hList.get("voteitemlist");
		for(HashMap<String, Object> row : voteitemlist) {
			if("".equals(row.get("BOARD_SEQ"))) {
				row.put("BOARD_SEQ", maxNoticeSeq);
			}
			
			
			row.put("ITEM_SEQ", check);
    		
			if(check==0) {
		    //이전투표item삭제	        	
		    cmmnDao.delete("k06.notice.deleteBDNT01TB_VOTE_ITEM", row);
	        }
	        
	        //if(!"".equals(row.get("REG_USER"))){
	        //새로운 투표item 입력
			cmmnDao.insert("k06.notice.insertBDNT01TB_VOTE_ITEM", row);
	        //}
	        check++;
    }
	}
}

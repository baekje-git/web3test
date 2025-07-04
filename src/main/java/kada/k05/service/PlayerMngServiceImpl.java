package kada.k05.service;

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
import kada.k99.service.RoleService;
import kada.modoc.ModocApi;
import kada.util.EmailService;
import kada.util.Util;

@Service
public class PlayerMngServiceImpl implements PlayerMngService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Autowired
	private ModocApi modoc;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	public void saveUserRegist(HashMap<String, Object> hMap) throws Exception {
		String mode = Util.getValue("MODE", hMap);
		HashMap<String, Object> basic = (HashMap<String, Object>) hMap.get("BASIC"); // 기본정보,추가정보
		basic.put("GSS", SessionUtil.getUserInfo());
		
		if("ADD".equals(mode)) {
			// 관계자 소속단체, 소속구단
			HashMap<String, Object> pMap = new HashMap<>();
			HashMap<String, Object> CreateuserMap = new HashMap<>();
			pMap.put("USER_ID", SessionUtil.getUserInfo().get("USER_ID"));
			HashMap<String, Object> managerInfo = (HashMap<String, Object>) cmmnDao.select("k99.user.selectUSER01TBByPK", pMap);
			basic.put("PRO_ORGZ", managerInfo.get("PRO_ORGZ"));
			basic.put("CLUB_CODE", managerInfo.get("CLUB_CODE"));
			
			CreateuserMap.put("PRO_ORGZ", managerInfo.get("PRO_ORGZ"));
			CreateuserMap.put("CLUB_CODE", managerInfo.get("CLUB_CODE"));
			
			String strUserId =(String)cmmnDao.select("k05.playerMng.selectPlayerCreateUser", CreateuserMap); 
			basic.put("USER_ID", strUserId);
			basic.put("LOGIN_ID", strUserId);
			hMap.put("USER_ID", strUserId);
			
			// 초기비밀번호생성 (등록에만 적용)
	        String initPwd = Util.newRandomPassword(10);
	        basic.put("USER_PWD_ORI", initPwd);
	        basic.put("USER_PWD", Util.SHA256(initPwd));	
		}
        
		// 기본정보,추가정보
		cmmnDao.update("k05.playerMng.updatePlayer", basic);
		
		// 권한생성
		if("ADD".equals(mode)) {
			basic.put("USER_FLAG", "P"); //선수
			roleService.triggerSaveRole(basic);		
		}
		
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
			@Override
			public void afterCommit() {
				try {
					
					if("ADD".equals(mode))emailService.playerRegSendMail(basic);
					
					HashMap<String, Object> playerInfo = (HashMap<String, Object>) cmmnDao.select("k99.user.selectUSER01TBByPK", hMap);
					String playerNo = Util.getValue("PLAYER_NO",playerInfo);
					
					if(StringUtils.isEmpty(playerNo)) { //post(신규)
						playerInfo.put("fn", "I");
					}else { //put(수정)
						playerInfo.put("fn", "U");
					}
					
					modoc.sendAthletesAsync(playerInfo);
				} catch (Exception e) {					
					logger.error("modoc interface error===>",e);
				}
			}
		});
	}

	@Override
	public void proorgsaveUserRegist(HashMap<String, Object> hMap) throws Exception {
		String mode = Util.getValue("MODE", hMap);
		HashMap<String, Object> basic = (HashMap<String, Object>) hMap.get("BASIC"); // 기본정보,추가정보
		basic.put("GSS", SessionUtil.getUserInfo());
		
		if("ADD".equals(mode)) {
			// 관계자 소속단체, 소속구단
			HashMap<String, Object> pMap = new HashMap<>();
			HashMap<String, Object> CreateuserMap = new HashMap<>();
			pMap.put("USER_ID", SessionUtil.getUserInfo().get("USER_ID"));
			//HashMap<String, Object> managerInfo = (HashMap<String, Object>) cmmnDao.select("k99.user.selectUSER01TBByPK", pMap);
			//basic.put("PRO_ORGZ", managerInfo.get("PRO_ORGZ"));
			//basic.put("CLUB_CODE", managerInfo.get("CLUB_CODE"));
			
			// 입력하는 단체의 구단정보로 조회 하여 유저생성
			CreateuserMap.put("PRO_ORGZ", basic.get("PRO_ORGZ"));
			CreateuserMap.put("CLUB_CODE", basic.get("CLUB_CODE"));
			
			String strUserId =(String)cmmnDao.select("k05.playerMng.selectPlayerCreateUser", CreateuserMap); 
			basic.put("USER_ID", strUserId);
			basic.put("LOGIN_ID", strUserId);
			hMap.put("USER_ID", strUserId);
			
			// 초기비밀번호생성 (등록에만 적용)
	        String initPwd = Util.newRandomPassword(10);
	        basic.put("USER_PWD_ORI", initPwd);
	        basic.put("USER_PWD", Util.SHA256(initPwd));	
		}
        
		// 기본정보,추가정보
		cmmnDao.update("k05.playerMng.updatePlayer", basic);
		
		// 권한생성
		if("ADD".equals(mode)) {
			basic.put("USER_FLAG", "P"); //선수
			roleService.triggerSaveRole(basic);		
		}
		
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
			@Override
			public void afterCommit() {
				try {
					
					if("ADD".equals(mode))emailService.playerRegSendMail(basic);
					
					HashMap<String, Object> playerInfo = (HashMap<String, Object>) cmmnDao.select("k99.user.selectUSER01TBByPK", hMap);
					String playerNo = Util.getValue("PLAYER_NO",playerInfo);
					
					if(StringUtils.isEmpty(playerNo)) { //post(신규)
						playerInfo.put("fn", "I");
					}else { //put(수정)
						playerInfo.put("fn", "U");
					}
					
					modoc.sendAthletesAsync(playerInfo);
				} catch (Exception e) {					
					logger.error("modoc interface error===>",e);
				}
			}
		});
	}

	@Override
	public int saveUserList(List<Map<String, Object>> hList) throws Exception {
		
		int errCnt=0;
		int seq=0;
		
		// 관계자 소속단체, 소속구단
		HashMap<String, Object> pMap = new HashMap<>();
		pMap.put("USER_ID", SessionUtil.getUserInfo().get("USER_ID"));
		HashMap<String, Object> managerInfo = (HashMap<String, Object>) cmmnDao.select("k99.user.selectUSER01TBByPK", pMap);		
		List<Object> clubGroups = cmmnDao.selectList("k05.playerMng.selectClubGroup", new HashMap<String,Object>() {{put("CLUB_CODE",managerInfo.get("CLUB_CODE"));}});
		
		if(hList.size() > 0 ) {
            List<Map<String, Object>> saveList = hList;
            
            // 이전 에러내역 삭제
            HashMap<String, Object> delMap = new HashMap<>();
            delMap.put("GSS", SessionUtil.getUserInfo());
            cmmnDao.delete("k05.playerMng.deletePLAYER_MNG_ERR", delMap);
            
            for(Map<String,Object>saveData : saveList) {
                HashMap<String, Object> sData = (HashMap<String, Object>) saveData;
                sData.put("GSS", SessionUtil.getUserInfo());
                HashMap<String, Object> newData = new HashMap<>();
                newData.putAll(sData);
                
                // user_id 가 null, empty check
                if(StringUtils.isBlank(String.valueOf(sData.get("USER_ID")))) {
                	continue;
                }
                
                int userCnt = (int) cmmnDao.select("k05.playerMng.selectPlayerCnt", sData);
                                
                if(userCnt > 0) {                	
                	sData.put("SEQ", ++seq);
                	sData.put("GUBUN", "1");
                	sData.put("ERR_REASON", "동일 선수ID가 존재 ");
                	cmmnDao.insert("k05.playerMng.insertPLAYER_MNG_ERR", sData);
                	errCnt++;
                	continue;
                }
                
                // 국문이름
                newData.put("USER_NAME", Util.getValue("USER_FAMILYNAME", sData)+Util.getValue("USER_NAME", sData));
                
                // 영문이름
                newData.put("USER_EN_NAME", Util.getValue("USER_EN_FAMILYNAME", sData)+" "+Util.getValue("USER_EN_NAME", sData));
                
                // 성별
                String[] male = {"남자","남"};
                String[] female = {"여자","여"};
                String userSex = Util.getValue("USER_SEX", sData);
                
                String userSex2 = Arrays.stream(male).anyMatch(userSex::equals)? "M" : Arrays.stream(female).anyMatch(userSex::equals) ? "F":"";
                newData.put("USER_SEX", userSex2);
                
                //전화번호
                String phone = Util.getValue("USER_PHONE", sData).replaceAll("-", "");
                newData.put("USER_PHONE", phone);
                
                // 이메일
                String[] email = String.valueOf(sData.get("USER_EMAIL")).split("@");
                if(email.length == 2) {
                	newData.put("USER_EMAIL_PREFIX", email[0]);
                	newData.put("USER_EMAIL_SUFFIX", email[1]);
                }
                
                // 생일
                String birthDt = Util.getValue("USER_DATEOFBIRTH", sData).replaceAll("-", "");
                if(!Util.isDateValid(birthDt, "yyyyMMdd")) {
                	sData.put("SEQ", ++seq);
                	sData.put("GUBUN", "1");
                	sData.put("ERR_REASON", "생일에 날짜형식이 틀립니다.");
                	cmmnDao.insert("k05.playerMng.insertPLAYER_MNG_ERR", sData);
                	errCnt++;
                	continue;
                }else {
                	newData.put("BIRTH_DT", birthDt);
                }
                
                // 그룹
                String clubGroup = Util.getValue("USER_GROUP", sData).trim();
                String clubGroupCode = "";
                
                List<Object> findGroupList = clubGroups.stream().filter(element -> {
                	HashMap<String, Object> row = (HashMap<String, Object>) element;
                	return clubGroup.equals(Util.getValue("CODE_NAME", row)); 
                }).collect(Collectors.toList());
                
                if(findGroupList != null && findGroupList.size() > 0) {
                	HashMap<String, Object> row = (HashMap<String, Object>) findGroupList.get(0);
                	clubGroupCode = Util.getValue("DTL_CODE", row);
                }
                
                newData.put("CLUB_GROUP", clubGroupCode);
                
                // 주소
                newData.put("USER_ADDRESS1", Util.getValue("USER_ADDRESS", sData).trim());
                
                // 초기비밀번호생성
                String initPwd = Util.newRandomPassword(10);
                newData.put("USER_PWD_ORI", initPwd);
                newData.put("USER_PWD", Util.SHA256(initPwd));
                
                // 소속단체, 소속구단
                newData.put("PRO_ORGZ", managerInfo.get("PRO_ORGZ"));
                newData.put("CLUB_CODE", managerInfo.get("CLUB_CODE"));        
                
                // 국적
                                
        		cmmnDao.insert("k05.playerMng.insertPlayer", newData);
        		
        		newData.put("fn", "I");
        		
        		// 권한생성
        		newData.put("USER_FLAG", "P"); //선수
        		roleService.triggerSaveRole(newData);        		
        		
        		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
        			@Override
        			public void afterCommit() {
        				try {
        					emailService.playerRegSendMail(newData);
        					modoc.sendAthletesAsync(newData);
        				} catch (Exception e) {					
        					logger.error("modoc interface error===>",e);
        				}
        			}
        		});
		    }
		}
		
		return errCnt;
	}

}

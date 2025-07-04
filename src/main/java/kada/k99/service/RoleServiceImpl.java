package kada.k99.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;
import kada.util.Util;

@Service
public class RoleServiceImpl implements RoleService {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;
	
	@Override
	public void saveRole(HashMap<String, Object> hMap) throws Exception {
		String mode = String.valueOf(hMap.get("mode"));
		HashMap<String, Object> role = (HashMap<String, Object>) hMap.get("role");
		String roleCode = String.valueOf(role.get("ROLE_CODE"));
    	List<HashMap<String, Object>> menuList = (List<HashMap<String, Object>>) hMap.get("menuList");
    	List<HashMap<String, Object>> userList = (List<HashMap<String, Object>>) hMap.get("userList");
    	
    	logger.info("mode===>{}",mode);
    	logger.info("role===>{}",role.toString());
    	logger.info("menuList===>{}",menuList.toString());
    	logger.info("userList===>{}",userList.toString());
    	
    	if("ADD".equals(mode)) {
    		cmmnDao.insert("k99.role.insertRole", role);
    	}else { // UPDATE
    		cmmnDao.update("k99.role.updateRole", role);
    	}
    	
    	cmmnDao.delete("k99.role.deleteRoleMenu", role);    		
		for(HashMap<String, Object> menu : menuList) {
			menu.put("ROLE_CODE", roleCode);
			cmmnDao.insert("k99.role.insertRoleMenu", menu);
		}
		
		cmmnDao.delete("k99.role.deleteRoleUser", role);    		
		for(HashMap<String, Object> user : userList) {
			user.put("ROLE_CODE", roleCode);
			//cmmnDao.insert("k99.role.insertRoleUser", user);
		}
	}

	/**
	 * 선수,관계자의 역할이 추가/수정시 권한을 자동으로 부여 
	 */
	@Override
	public void triggerSaveRole(HashMap<String, Object> hMap) throws Exception {
		String userFlag = Util.getValue("USER_FLAG", hMap);
		//String query = "k99.role.insertRoleUser";
		//String query2 = "k99.role.deleteRoleUserByUser";
		
		HashMap<String, Object> roleMap = new HashMap<>();
		roleMap.put("USER_ID", Util.getValue("USER_ID", hMap));
		roleMap.put("REG_USER", Util.getUserId());
		
		if("P".equals(userFlag)) { //선수
			roleMap.put("ROLE_CODE", ConstantProp.GV_PLAYER_ROLE_CODE);
			//cmmnDao.delete(query2, roleMap);
			//cmmnDao.insert(query, roleMap);
		}else if("R".equals(userFlag)) { //관계자
			roleMap.put("ROLE_CODE", ConstantProp.GV_MANAGER_ROLE_CODE);
			//cmmnDao.delete(query2, roleMap);
			//cmmnDao.insert(query, roleMap);
		}
	}

}

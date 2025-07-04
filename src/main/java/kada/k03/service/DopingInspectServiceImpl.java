package kada.k03.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import ever.cmmn.dao.CmmnDao;
import ever.support.exception.CustomException;
import ever.support.file.FileUtil;
import ever.support.session.SessionUtil;
import kada.modoc.ModocApi;
import kada.util.Util;

@Service
public class DopingInspectServiceImpl implements DopingInspectService {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Autowired
	private ModocApi modoc;

	@Override
	public void saveDopingInspect(HashMap<String, Object> hMap) throws Exception {
		
		if(hMap.get("DETAILS") != null) {
			List<HashMap<String,Object>> inspectDetails = (List<HashMap<String, Object>>) hMap.get("DETAILS");
			
			
			for(HashMap<String,Object> row : inspectDetails) {
				String mode = String.valueOf(row.get("mode"));
				String ipntDate = String.valueOf(row.get("ipntDate"));
				HashMap<String,Object> eventMap = (HashMap<String, Object>) row.get("ipntEventCode");
				String eventCode = String.valueOf(eventMap.get("code"));
				
				if("ADD".equals(mode)) {
					HashMap<String, Object> pMap = new HashMap<>();
					pMap.put("USER_ID", hMap.get("IPNT_CHARGER_ID"));
					HashMap<String, Object> user = (HashMap<String, Object>) cmmnDao.select("k99.user.selectUSER01TBByPK",pMap); 					
					String ipntUpperSeq = (String) cmmnDao.select("k03.dopingInspectReg.selectIPNT_UPPER_SEQ",null);
					user.put("IPNT_DATE", ipntDate);
					user.put("IPNT_TYPE", row.get("IPNT_TYPE"));
					user.put("IPNT_EVENT_CODE", row.get("IPNT_EVENT_CODE"));
					String ipntNo = (String) cmmnDao.select("k03.dopingInspectReg.selectMaxIPNT_NO", user);
					logger.info("ipnt_no,ipnt_upper_seq : {}, {}",ipntNo,ipntUpperSeq);
					
					HashMap<String, Object> insertMap = new HashMap<>(hMap);									
					setDopingInspectData(insertMap, ipntNo, ipntUpperSeq, eventCode, row);
					cmmnDao.insert("k03.dopingInspectReg.insertDopingInspect", insertMap);
					saveDopingInspectDetail(row, ipntNo, ipntUpperSeq, eventCode);
					cmmnDao.update("k03.dopingInspectReg.saveK00_ZMISSIONS", insertMap);
				}else if("UPDATE".equals(mode)) {
					
					String ipntNo = String.valueOf(row.get("ipntNo"));
					String ipntUpperSeq = String.valueOf(row.get("ipntUpperSeq"));
					
					HashMap<String, Object> updateMap = new HashMap<>(hMap);
					setDopingInspectData(updateMap, ipntNo, ipntUpperSeq, eventCode, row);				
					cmmnDao.update("k03.dopingInspectReg.updateDopingInspect", updateMap);
					saveDopingInspectDetail(row, ipntNo, ipntUpperSeq, eventCode);
					cmmnDao.update("k03.dopingInspectReg.saveK00_ZMISSIONS", updateMap);
				}
			}
			
			// 복사,추가 시 원본데이터의 첨부파일 복사
			String fileSeq = String.valueOf(hMap.get("FILE_SEQ"));
			if(inspectDetails.size() > 1 && StringUtils.hasLength(fileSeq)) {
				
				for(int i=1;i<inspectDetails.size();i++) {					
					int newFileSeq = (int)cmmnDao.select("cmmnFile.selectCmmnNewFileSeq",null);
					// file data copy
					HashMap<String, Object> cloneMap = new HashMap<>();
					cloneMap.put("FILE_SEQ", fileSeq);
					cloneMap.put("NEW_FILE_SEQ", newFileSeq);
					cloneMap.put("GSS", SessionUtil.getUserInfo());
					cmmnDao.insert("cmmnFile.copyCmmnFilInfo",cloneMap);
					// file copy
					HashMap<String, Object> fileMap = new HashMap<>();
					fileMap.put("FILE_SEQ", newFileSeq);
					List<Object> fileList = cmmnDao.selectList("cmmnFile.selectCmmnFileList", fileMap);
					for(Object file : fileList) {
						HashMap<String,Object> row = (HashMap<String, Object>) file;
						String filePath = String.valueOf(row.get("FILE_PATH"));
						String fileName = String.valueOf(row.get("FILE_NAME"));
						String newFileName = FileUtil.getUuid();
						String seqNo =  String.valueOf(row.get("SEQ_NO"));
						FileCopyUtils.copy(new FileInputStream(filePath+fileName), new FileOutputStream(filePath+newFileName));
						row.put("FILE_NAME", newFileName);
						cmmnDao.update("cmmnFile.updateFile",row);
					}
				}
			}
		}else {
			throw new RuntimeException("1.활동신청공지2 데이터가 입력되지 않았습니다.");
		}
		
		
	}

	private void setDopingInspectData(HashMap<String, Object> pMap, 
			                          String ipntNo, String ipntUpperSeq, String eventCode, HashMap<String, Object> row) throws Exception {
		pMap.put("IPNT_NO", ipntNo);
		pMap.put("IPNT_UPPER_SEQ", ipntUpperSeq);
		pMap.put("IPNT_NAME", row.get("ipntName"));
		pMap.put("IPNT_DATE", row.get("ipntDate"));
		pMap.put("IPNT_TIME", row.get("ipntTime"));
		pMap.put("IPNT_EVENT_CODE", eventCode);
		pMap.put("IPNT_CHOOSE_METHOD", row.get("ipntChooseMethod"));
		pMap.put("IPNT_PLACE_TYPE", row.get("ipntPlaceType"));
		pMap.put("IPNT_PLACE_DETAIL", row.get("ipntPlaceDetail"));
		pMap.put("IPNT_PLACE_ZIPCODE", row.get("ipntPlaceZipcode"));
		pMap.put("IPNT_PLACE_ADDRESS", row.get("ipntPlaceAddress"));
		pMap.put("IPNT_PLACE_NEWADDRESS", row.get("ipntPlaceNewaddress"));
		pMap.put("IPNT_PLACE_PHONE", row.get("ipntPlacePhone"));
		pMap.put("IPNT_PLACE_LATITUDE", row.get("ipntPlaceLatitude"));
		pMap.put("IPNT_PLACE_LONGITUDE", row.get("ipntPlaceLongitude"));
		pMap.put("IPNT_PLACE_STATE", row.get("ipntPlaceState"));
		pMap.put("IPNT_PLACE_CITY", row.get("ipntPlaceCity"));
		pMap.put("IPNT_PLACE_STREET", row.get("ipntPlaceStreet"));
		pMap.put("IPNT_PLACE_ENGADDRESS", row.get("ipntPlaceEngaddress"));
		pMap.put("IPNT_DCO", row.get("ipntDco"));
		pMap.put("IPNT_DCO_F", row.get("ipntDcoF"));
		pMap.put("IPNT_BCO", row.get("ipntBco"));
		pMap.put("LOCATION_CD", row.get("locationCd"));
		pMap.put("GSS", SessionUtil.getUserInfo());	
	}
	
	private void saveDopingInspectDetail(HashMap<String, Object> row, String ipntNo, String ipntUpperSeq, String eventCode) throws Exception {
		
		if(row.get("players") != null) {
			List<HashMap<String,Object>> players = (List<HashMap<String,Object>>) row.get("players");
			ArrayList<HashMap<String,Object>> inspectDtl = new ArrayList<HashMap<String,Object>>(); 
			int idx=1;
			for(HashMap<String,Object> player : players) {
				logger.info("검사대상선수===>{}",player);
				HashMap<String,Object> playerMap = new HashMap<>();
				playerMap.put("IPNT_NO", ipntNo);
				playerMap.put("IPNT_UPPER_SEQ", ipntUpperSeq);
				playerMap.put("NATIONALITY", "KOR");							
				playerMap.put("PLAYER_NAME", player.get("PLAYER_NAME"));							
				playerMap.put("SEX", player.get("SEX"));
				playerMap.put("MINOR_TYPE", player.get("MINOR_TYPE"));
				playerMap.put("EVENT_CODE", eventCode);
				playerMap.put("EVENT_DET_CODE", player.get("EVENT_DET_CODE"));
				playerMap.put("SORT_NO", player.get("SORT_NO"));
				playerMap.put("PLAYER_NO", player.get("PLAYER_NO"));
				playerMap.put("USER_ID", player.get("USER_ID"));
											
				List<HashMap<String,Object>> ipntSubFlagList = (List<HashMap<String, Object>>) player.get("IPNT_SUB_FLAG");							
				for(HashMap<String,Object> ipntSubFlag : ipntSubFlagList) {							
					HashMap<String,Object> addRow = new HashMap<>(playerMap);
					addRow.put("IPNT_SEQ", idx);
					addRow.put("IPNT_SUB_SEQ", idx);
					addRow.put("IPNT_FLAG", ipntSubFlag.get("ipntFlag"));
					addRow.put("IPNT_SUB_FLAG", ipntSubFlag.get("code"));		
					addRow.put("GSS", SessionUtil.getUserInfo());
					inspectDtl.add(addRow);
					idx++;
				}
			}
			
			// 검사대상선수(활동신청공지2-상세정보)
			HashMap<String,Object> delMap = new HashMap<>();
			delMap.put("IPNT_UPPER_SEQ", ipntUpperSeq);
			delMap.put("IPNT_NO", ipntNo);
			cmmnDao.delete("k03.dopingInspectReg.deleteK03_DOPING_INSPECT_DTL", delMap);
			cmmnDao.delete("k03.dopingInspectReg.deleteK00_ZTESTSByPK", delMap);
			for(HashMap<String,Object> dtl : inspectDtl) {
				cmmnDao.update("k03.dopingInspectReg.procK03_DOPING_INSPECT_DTL", dtl);
				cmmnDao.update("k03.dopingInspectReg.insertK00_ZTESTS", dtl);
			}			
			
			// 검사시료 테이블
			HashMap<String,Object> sampleMap = new HashMap<>();
			sampleMap.put("IPNT_UPPER_SEQ", ipntUpperSeq);
			sampleMap.put("IPNT_NO", ipntNo);
			cmmnDao.delete("k03.dopingInspectReg.deleteK03_DOPING_INSPECT_SAMPLE", sampleMap);
			cmmnDao.insert("k03.dopingInspectReg.insertK03_DOPING_INSPECT_SAMPLE", sampleMap);
			
			// 법례(활동신청공지2-검사구분)
			cmmnDao.delete("k03.dopingInspectReg.deleteK03_DOPING_INSPECT_LEGEND", sampleMap);
			if(row.get("ipntLegends") != null) {
				List<String> ipntLegends = (List<String>) row.get("ipntLegends");
				for(String legendCode : ipntLegends) {
					logger.info("검사구분===>{}",legendCode);
					HashMap<String,Object> legendMap = new HashMap<>();
					legendMap.put("IPNT_NO", ipntNo);
					legendMap.put("IPNT_UPPER_SEQ", ipntUpperSeq);
					legendMap.put("LEGEND_CODE", legendCode);
					cmmnDao.update("k03.dopingInspectReg.insertK03_DOPING_INSPECT_LEGEND",legendMap);
				}
			}
		}
	}

	@Override
	public void deleteDopingInspect(HashMap<String, Object> hMap) throws Exception {
		/*
		sqlSession.delete("KADA.deleteIPNT01TB", (IPNT01TB) map.get("IPNT01TB"));
        sqlSession.delete("KADA.deleteIPNT02TB", (IPNT02TB) map.get("IPNT02TB"));
        sqlSession.delete("KADA.deleteIPNT03TB", (IPNT03TB) map.get("IPNT03TB"));
        sqlSession.delete("KADA.deleteIPNT05TB", (IPNT05TB) map.get("IPNT05TB"));        
        sqlSession.delete("KADA.deleteIPNT04TB", (IPNT04TB) map.get("IPNT04TB"));
        sqlSession.delete("KADA.deleteIPNT06TB", (IPNT06TB) map.get("IPNT06TB"));
        sqlSession.delete("KADA.deleteIPNT07TB", (IPNT07TB) map.get("IPNT07TB"));
        sqlSession.delete("KADA.deleteIPNT08TBAll", (IPNT08TB) map.get("IPNT08TB"));
        sqlSession.delete("KADA.deleteIPNT11TB", (IPNT11TB) map.get("IPNT11TB"));
        sqlSession.delete("KADA.deleteIPNT13TBByPK", (IPNT13TB) map.get("IPNT13TB"));
        sqlSession.delete("KADA.deleteIPCF01TB", (IPCF01TB) map.get("IPCF01TB"));
        sqlSession.delete("KADA.deleteIPCF02TB", (IPCF02TB) map.get("IPCF02TB"));
        sqlSession.delete("KADA.deleteIPDN01TB", (IPDN01TB) map.get("IPDN01TB"));
        sqlSession.delete("KADA.deleteIPDN02TBAll", (IPDN02TB) map.get("IPDN02TB"));
        sqlSession.delete("KADA.deleteIPDN03TB", (IPDN03TB) map.get("IPDN03TB"));
        sqlSession.delete("KADA.deleteIPER01TBAll", (IPER01TB) map.get("IPER01TB"));
        
        to do:삭제 대상 추가
        */
		cmmnDao.delete("k03.dopingInspectReg.deleteK03_DOPING_INSPECT_REG",hMap);
		cmmnDao.delete("k03.dopingInspectReg.deleteK03_DOPING_INSPECT_DTL", hMap);
		cmmnDao.delete("k03.dopingInspectReg.deleteK03_DOPING_INSPECT_SAMPLE", hMap);
		cmmnDao.delete("k03.dopingInspectReg.deleteK03_DOPING_INSPECT_LEGEND", hMap);
		
	}

	@Override
	public void addDopingInspectReadHist(HashMap<String, Object> hMap) throws Exception {
		hMap.put("GSS", SessionUtil.getUserInfo());
		cmmnDao.insert("k03.dopingInspectReg.insertK03_DOPING_INSPECT_HIST", hMap);
	}

	@Override
	public void sendSMS(HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> inspect = (HashMap<String, Object>) cmmnDao.select("k03.dopingInspectReg.selectDopingInspectRegByPK", hMap);
		List<Object> dcoList = cmmnDao.selectList("k03.dopingInspectReg.selectDopingInspectDcoByPK", hMap); 
		StringBuffer sbSMS = new StringBuffer();
				
		for(Object row : dcoList) {
			HashMap<String, Object> dco = (HashMap<String, Object>) row;
			if("Y".equals(String.valueOf(dco.get("CHOOSE_DCO_YN"))) || "Y".equals(String.valueOf(dco.get("CHOOSE_BCO_YN"))) ||
			   "Y".equals(String.valueOf(dco.get("CHOOSE_DCOBCO_YN"))) || "Y".equals(String.valueOf(dco.get("CHOOSE_CHAPERON_YN")))	) {
				sbSMS.setLength(0);
				sbSMS.append("KADA 공지 ");
				
                if ("0".equals(String.valueOf(inspect.get("IPNT_PERIOD_CODE")))) { // 경기기간중
                    sbSMS.append("ICT");
                } else if ("1".equals(String.valueOf(inspect.get("IPNT_PERIOD_CODE")))) { // 경기기간외
                	sbSMS.append("OOCT");
                }
                
                sbSMS.append(" "+Util.date2split3(String.valueOf(inspect.get("IPNT_DATE"))) + " " + Util.time2split(String.valueOf(inspect.get("IPNT_TIME"))) + " ");
                sbSMS.append(String.valueOf(inspect.get("IPNT_PLACE_ADDRESS")).split(" ")[0] + " " + String.valueOf(inspect.get("IPNT_PLACE_ADDRESS")).split(" ")[1]  + " ");
                if ("Y".equals(String.valueOf(dco.get("PREDECESSOR_YN")))) {
                    sbSMS.append("선임");
                }
                if ("Y".equals(String.valueOf(dco.get("CHOOSE_DCO_YN")))) {
                    sbSMS.append("DCO");
                } else if ("Y".equals(String.valueOf(dco.get("CHOOSE_BCO_YN")))) {
                    sbSMS.append("BCO");
                } else if ("Y".equals(String.valueOf(dco.get("CHOOSE_DCOBCO_YN")))) {
                    sbSMS.append("DCO/BCO");
                } else if ("Y".equals(String.valueOf(dco.get("CHOOSE_CHAPERON_YN")))) {
                    sbSMS.append("샤프롱으");
                }
                sbSMS.append("로 선정되셨습니다.");
                
                HashMap<String,Object> map = new HashMap<>();
                map.put("msg", sbSMS.toString());
                map.put("smsType", "SMS");
                map.put("rphone", dco.get("USER_PHONE"));
                map.put("MENU_NM", "KADA0302 > sendSMS");
                map.put("LOGIN_USER_ID", SessionUtil.getUserInfo().get("USER_ID"));
			}
		}
	}

	@Override
	public void deleteChoose(HashMap<String, Object> hMap) throws Exception {
		hMap.put("GSS", SessionUtil.getUserInfo());
		cmmnDao.update("k03.dopingInspectReg.initChooseDco", hMap);
		cmmnDao.delete("k03.dopingInspectReg.deleteChooseDcoPay", hMap);
	}

	@Override
	public void saveChoose(HashMap<String, Object> hMap) throws Exception {
		hMap.put("GSS", SessionUtil.getUserInfo());
		HashMap<String,Object> inspect = (HashMap<String, Object>) cmmnDao.select("k03.dopingInspectReg.selectDopingInspectRegByPK", hMap);
		HashMap<String,Object> user = (HashMap<String, Object>) cmmnDao.select("k99.user.selectUSER01TBByPK",hMap);
		HashMap<String,Object> chooseCnt = (HashMap<String, Object>) cmmnDao.select("k03.dopingInspectReg.selectInspectChooseCount",hMap);
		
		if ("N".equals(user.get("BCO_YN")) && "N".equals(user.get("DCO_YN"))) {
            // validateObject.put("CODE", "98");   //둘다 아님.
			throw new CustomException("해당 신청자는 BCO 또는 DCO가 아닙니다.");
        } else if ("Y".equals(inspect.get("CHOOSE_BCO_YN")) || "Y".equals(inspect.get("CHOOSE_DCOBCO_YN"))) {
            if (Integer.parseInt(chooseCnt.get("BCO_COUNT").toString()) + Integer.parseInt(chooseCnt.get("DCOBCO_COUNT").toString()) + 1 > Integer.parseInt(Util.nullreplace(inspect.get("IPNT_BCO"), "0"))) {
                // validateObject.put("CODE", "97");   //BCO 꽉찼음.
            	throw new CustomException("BCO 는 선정이 완료되었습니다. 해당 신청자는 더이상 선정할 수 없습니다.");
            }
        } else if ("Y".equals(inspect.get("CHOOSE_DCO_YN")) || "Y".equals(inspect.get("CHOOSE_DCOBCO_YN"))) {
            if (Integer.parseInt(chooseCnt.get("DCO_COUNT").toString()) + Integer.parseInt(chooseCnt.get("DCOBCO_COUNT").toString()) + 1 > Integer.parseInt(Util.nullreplace(inspect.get("IPNT_DCO"), "0")) + Integer.parseInt(Util.nullreplace(inspect.get("IPNT_DCO_F"), "0"))) {
                // validateObject.put("CODE", "94");   //DCO 꽉찼음.
            	throw new CustomException("DCO 는 선정이 완료되었습니다. 해당 신청자는 더이상 선정할 수 없습니다.");
            }
        }

        cmmnDao.update("k03.dopingInspectReg.updateChooseDco", hMap);
	}

	@Override
	public void saveComment(HashMap<String, Object> hMap) throws Exception {
		hMap.put("GSS", SessionUtil.getUserInfo());
		hMap.put("USE_YN", "Y");
		cmmnDao.insert("k03.dopingInspectReg.insertDcoSelectEval", hMap);
	}

	@Override
	public void deleteComment(HashMap<String, Object> hMap) throws Exception {
		cmmnDao.delete("k03.dopingInspectReg.deleteDcoSelectEval", hMap);		
	}

	/**
	 * 선정완료 
	 */
	@Override
	public void chooseComplete(HashMap<String, Object> hMap) throws Exception {
		cmmnDao.update("k03.dopingInspectReg.updateChooseDoneYn", hMap);
		
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
			@Override
			public void afterCommit() {
				try {
					if("Y".equals(Util.getValue("CHOOSE_DONE_YN", hMap)))modoc.modocSendData(hMap);
				} catch (Exception e) {					
					logger.error("modoc interface error===>",e);
				}
			}
		});
	}
	
}

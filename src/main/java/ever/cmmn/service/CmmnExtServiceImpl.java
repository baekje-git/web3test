package ever.cmmn.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ever.cmmn.dao.CmmnDao;
import ever.support.file.FileUtil;

public class CmmnExtServiceImpl implements CmmnExtService{
    
    private static final Logger logger = LoggerFactory.getLogger(CmmnExtServiceImpl.class);
    private CmmnDao cmmnDao;
        
    public CmmnExtServiceImpl(CmmnDao cmmnDao) {
        super();
        this.cmmnDao = cmmnDao;
    }
    
    public Object select(HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.select(hMap);
	}

	public Object select(String queryId, HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.select(queryId, hMap);
	}

	public List<Object> selectList(HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.selectList(hMap);
	}

	public List<Object> selectList(String queryId, HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.selectList(queryId, hMap);
	}

	public int insert(HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.insert(hMap);
	}

	public int insert(String queryId, HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.insert(queryId, hMap);
	}

	public int update(HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.update(hMap);
	}

	public int update(String queryId, HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.update(queryId, hMap);
	}

	public int delete(HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.delete(hMap);
	}

	public int delete(String queryId, HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.delete(queryId, hMap);
	}

	public Object callProcedure(HashMap<String, Object> hMap) throws Exception {
		this.cmmnDao.callProcedure(hMap);
		String oVal = hMap.get("O_VAL").toString();
		String oMsg = hMap.get("O_MSG").toString();
		if (oVal.equals("ERROR"))
			throw new Exception(String.valueOf(oMsg) + " => O_VAL : [" + oVal + "]");
		return hMap;
	}

	public Object callProcedure(String queryId, HashMap<String, Object> hMap) throws Exception {
		this.cmmnDao.callProcedure(queryId, hMap);
		String oVal = String.valueOf(hMap.get("O_VAL"));
		String oMsg = String.valueOf(hMap.get("O_MSG"));
		logger.debug("[callProcedure service] >> oVal : " + oVal + " >> oMsg : " + oMsg);
		if (oVal.equals("ERROR"))
			throw new Exception(String.valueOf(oMsg) + " => O_VAL : [" + oVal + "]");
		return hMap;
	}

	public List<Object> selectAttachFileList(HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.selectList(hMap);
	}

	public List<Object> selectAttachFileList(String queryId, HashMap<String, Object> hMap) throws Exception {
		return this.cmmnDao.selectList(queryId, hMap);
	}

	public Object insertAttatchFileInfo(HttpServletRequest request) throws Exception {
		return Integer.valueOf(0);
	}

	public Object insertAttatchFileInfo(String queryId, HttpServletRequest request) throws Exception {
		List<HashMap<String, Object>> fileList = FileUtil.cmmnFileUpLoadAttach(request);
		try {
			for (HashMap<String, Object> fileInfo : fileList) {
				int newFileNo = ((Integer) this.cmmnDao.select("cmmnFile.selectAttachNewFileNo", null)).intValue();
				logger.debug("@ newFileNo : " + newFileNo);
				fileInfo.put("NEW_FILE_NO", Integer.valueOf(newFileNo));
				logger.debug("@ fileInfo  : " + fileInfo);
				this.cmmnDao.insert(queryId, fileInfo);
			}
			logger.debug("@ filelist  : " + fileList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("[>>> RuntimeException] : " + ExceptionUtils.getStackTrace(e));
			for (HashMap<String, Object> fileInfo : fileList)
				FileUtil.cmmnFileDelete(fileInfo);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return fileList;
	}

	public Object doSave(String queryId, String upQueryId, String delQueryId, HashMap<String, Object> hMap)
			throws Exception {
		int retVal = 0;
		List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) hMap.get("gridData");
		if (dataList.size() > 0)
			for (HashMap<String, Object> dataMap : dataList) {
				String status = (dataMap.get("STATUS") != null) ? dataMap.get("STATUS").toString() : "";
				if ("Added".equals(status)) {
					this.cmmnDao.insert(queryId, dataMap);
					continue;
				}
				if ("Changed".equals(status)) {
					this.cmmnDao.update(upQueryId, dataMap);
					continue;
				}
				if ("Deleted".equals(status))
					this.cmmnDao.delete(delQueryId, dataMap);
			}
		return Integer.valueOf(retVal);
	}

	public Object doSaveSuffix(String queryId, HashMap<String, Object> hMap) throws Exception {
		int retVal = 0;
		List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) hMap.get("gridData");
		if (dataList.size() > 0)
			for (HashMap<String, Object> dataMap : dataList) {
				String status = (dataMap.get("STATUS") != null) ? dataMap.get("STATUS").toString() : "";
				if ("Added".equals(status)) {
					this.cmmnDao.insert(String.valueOf(queryId) + "INSERT", dataMap);
					continue;
				}
				if ("Changed".equals(status)) {
					this.cmmnDao.update(String.valueOf(queryId) + "UPDATE", dataMap);
					continue;
				}
				if ("Deleted".equals(status))
					this.cmmnDao.delete(String.valueOf(queryId) + "DELETE", dataMap);
			}
		return Integer.valueOf(retVal);
	}

}

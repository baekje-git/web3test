package baekje.web.ordLedger.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;
import ever.support.Gv;
import ever.support.exception.AjaxMsgCustomException;

@Service
public class OrdLedgerServiceImpl implements OrdLedgerService {
	
	@Autowired
	CmmnDao cmmnDao;

	@Override
	@Transactional
	public int insertOrdLedgerSignFile(HashMap<String, Object> hMap) throws Exception {        
        Calendar cal = Calendar.getInstance();
        
        HashMap<String, Object> reqParamMap = new HashMap<>();
        
        String orgFilePath = ConstantProp.GV_mobileSignPath;
//        String orgFilePath = "C:/Project/2017-BAEKJE/UPLOAD/MOBILE_SIGN/";
//        String filePath=ConstantProp.GV_scanImagePath;
        
        //2024-04-29 업로드시 해당 폴더가 없을 경우 생성
        File Folder = new File(orgFilePath);
        //해당 디렉토리가 없을경우 디렉토리를 생성합니다.
    	if (!Folder.exists()) {
    		try{
    		    Folder.mkdir(); //폴더 생성합니다.
    	        } 
    	        catch(Exception e){
    		    e.getStackTrace();
    		}          
    	}
             
        String fileNm = "ordLedger_"+hMap.get("custCd")+"_"+cal.getTimeInMillis()+"_"+hMap.get("userId");
        String fileExt = "png";
        String imgData = (String) hMap.get("uploadFile");
        imgData = imgData.replaceAll("data:image/png;base64,", "");
        
        byte[] imgByte = Base64.decodeBase64(imgData.getBytes());
        
        FileOutputStream osf = new FileOutputStream(new File(orgFilePath + fileNm + "." + fileExt));
        
        osf.write(imgByte);
        osf.close();
        osf.flush();
        
        reqParamMap.put("SN", hMap.get("sn"));
        reqParamMap.put("FILE_NM", fileNm);
        reqParamMap.put("ORG_FILE_NM", fileNm);
        reqParamMap.put("FILE_PATH", orgFilePath);
        reqParamMap.put("FILE_SIZE", (new File(orgFilePath + fileNm + "." + fileExt)).length());
        reqParamMap.put("FILE_EXT", fileExt);
        reqParamMap.put("USER_ID", hMap.get("userId"));

        //첨부파일 저장 
        if(hMap.get("signYn") != null && "Y".equalsIgnoreCase(hMap.get("signYn").toString())) {
            //기존건 삭제 
            HashMap<String, Object> map = (HashMap<String, Object>) cmmnDao.select("ordLedger.selectOrdLedgerSignFileName", hMap);
            String filePath = map.get("FILE_PATH").toString();
            String oldFileNm =  map.get("FILE_NM").toString();
            
            File f = new File(filePath + oldFileNm + ".png" );
            f.delete();
            
            cmmnDao.update("ordLedger.updateOrdLedgerSignFile", reqParamMap);
        }else {
        	cmmnDao.insert("ordLedger.insertOrdLedgerSignFile", reqParamMap);
        }
        
		return 1;
	}
	
    @Override
    public List<Object> selectSalesBranchMobile(HashMap<String, Object> paramMap) throws Exception {
    	cmmnDao.callProcedure("ordLedger.callPMB0004", paramMap);
        if(!paramMap.get("O_VAL").equals("SUCCESS")) {
            throw new AjaxMsgCustomException(paramMap.get("O_MSG").toString());
        }
        
        paramMap.put(Gv.DATASOURCE , Gv.SAL1);
        paramMap.put(Gv.NAMESPACE  , "ordLedger");
        paramMap.put(Gv.QUERY_ID   , "selectSalesBranchMobile");
        return cmmnDao.selectList(paramMap);
    }
}

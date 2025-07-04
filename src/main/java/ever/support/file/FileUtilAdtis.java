package ever.support.file;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ever.support.ConstantProp;
import ever.support.cmmn.CmmnUtil;
import ever.support.cmmn.GSSMap;
import ever.support.date.DateUtil;

public class FileUtilAdtis {
	private static final Logger logger = LoggerFactory.getLogger(FileUtilAdtis.class);

	public static List<HashMap<String, Object>> cmmnFileUpLoadBoard(HttpServletRequest request, Object borad, Object boardSeq, Object seqNo)
			throws Exception {
		CmmnUtil.methodLog("게시판 파일업로드");
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		logger.info("mptRequest : " + mptRequest);
		List<HashMap<String, Object>> filelist = new ArrayList<>();
		HashMap<String, Object> fileMap = null;
		String filePath = ConstantProp.GV_TEMP_UPLOAD_PATH;
		File saveFolder = new File(filePath);
		if (!saveFolder.exists() || saveFolder.isFile())
			saveFolder.mkdirs();
		int idx = 0;
		List<MultipartFile> mFileList = mptRequest.getFiles("UPLOAD_FILE");
		for (MultipartFile mf : mFileList) {
			String fileLogicName = mf.getOriginalFilename();
			String filePhysicName = getUuid();
			String fileExt = fileLogicName.substring(fileLogicName.lastIndexOf(".") + 1);
			long fileSize = mf.getSize();
			File f = new File(String.valueOf(filePath) + filePhysicName);
			mf.transferTo(f);
			fileMap = new HashMap<>();
			fileMap.put("BOARD", borad);
			fileMap.put("BOARD_SEQ", boardSeq);
			fileMap.put("SEQ_NO", seqNo);
			fileMap.put("ORG_FILE_NM", fileLogicName);
			fileMap.put("FILE_EXT", fileExt);
			fileMap.put("FILE_SIZE", Long.valueOf(fileSize));
			fileMap.put("FILE_NM", filePhysicName);
			fileMap.put("FILE_PATH", filePath);
			fileMap.put("GSS", (new GSSMap()).get("GSS"));
			filelist.add(fileMap);
			logger.info("SEQ_NO  : " + seqNo);
			logger.info("논리파일명 : " + fileLogicName);
			logger.info("물리파일명 : " + filePhysicName);
			logger.info("파일확장자 : " + fileExt);
			logger.info("파일사이즈 : " + fileSize);
			logger.info("업로드경로 : " + filePath);
			logger.info("파일업로드 목록 - " + idx++ + " : " + filelist);
			logger.info("=================================================");
		}
		return filelist;
	}

	public static List<HashMap<String, Object>> cmmnFileUpLoadAttach(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("공통 파일업로드");
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		logger.info("mptRequest : " + mptRequest);
		List<HashMap<String, Object>> filelist = new ArrayList<>();
		HashMap<String, Object> fileMap = null;
		//String filePath = ConstantProp.GV_TEMP_UPLOAD_PATH;
		String filePath = getStorePath();
		File dir = new File(filePath);
		if (!dir.exists() || dir.isFile()) {
			dir.mkdirs();
			dir.setExecutable(true);
			dir.setReadable(true);
			dir.setWritable(true);
		}
		int idx = 0;
		List<MultipartFile> mFileList = mptRequest.getFiles("UPLOAD_FILE");
		for (MultipartFile mf : mFileList) {
			String fileLogicName = mf.getOriginalFilename();
			String filePhysicName = getUuid();
			String fileExt = fileLogicName.substring(fileLogicName.lastIndexOf(".") + 1);
			long fileSize = mf.getSize();
			File f = new File(String.valueOf(filePath) + filePhysicName);
			mf.transferTo(f);
			fileMap = new HashMap<>();
			fileMap.put("ORG_FILE_NM", fileLogicName);
			fileMap.put("FILE_EXT", fileExt);
			fileMap.put("FILE_SIZE", Long.valueOf(fileSize));
			fileMap.put("FILE_NM", filePhysicName);
			fileMap.put("FILE_PATH", filePath);
			fileMap.put("GSS", (new GSSMap()).get("GSS"));
			filelist.add(fileMap);
		}
		return filelist;
	}
	
	private static String getStorePath() {
			
		Date createDate = new Date(); 
		String year = (new SimpleDateFormat("yyyy").format(createDate)); //년도 
		String month = (new SimpleDateFormat("MM").format(createDate)); //월 
		String day = (new SimpleDateFormat("dd").format(createDate)); //일

		Path directory = Paths.get(ConstantProp.GV_TEMP_UPLOAD_PATH, year, month, day); //Path를 사용해 디렉토리 경로 지정
	
		return directory.toString()+System.getProperty("file.separator");
	}

	public static void cmmnFileDelete(HashMap<String, Object> fileInfo) {
		CmmnUtil.methodLog("공통 파일삭제");
		logger.info(">>> fileInfo : " + fileInfo);
		String filePhysicName = (String) fileInfo.get("FILE_NM");
		String filePath = (String) fileInfo.get("FILE_PATH");
		String targetFile = String.valueOf(filePath) + filePhysicName;
		logger.info("targetFile : " + targetFile);
		File file = new File(targetFile);
		if (file.exists()) {
			if (file.delete()) {
				logger.info(">> 파일삭제 성공 : " + targetFile);
			} else {
				logger.info(">> 파일삭제 실패 : " + targetFile);
			}
		} else {
			logger.info(">> 삭제 할 파일이 존재하지 않습니다.");
		}
	}

	public static void closeQuietly(OutputStream output) {
		closeQuietly2(output);
	}

	public static void closeQuietly2(Closeable closeable) {
		try {
			if (closeable != null)
				closeable.close();
		} catch (IOException iOException) {
			logger.error("IOException===>",iOException);
		} catch (StackOverflowError se ) {
			logger.error("StackOverflowError===>",se);
		}
	}

	public static String getUuid() {
		return String.valueOf(DateUtil.getCurrDate("yyyyMMddHHmmss")) + "_"
				+ UUID.randomUUID().toString().replaceAll("-", "");
	}
}

package ever.cmmn.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import ever.cmmn.service.CmmnAdtisService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.excel.ExcelDownloadView;
import ever.support.file.FileDownloadView;
import ever.support.file.FileUtil;

@Controller
@RequestMapping({ "/cmmnFileAdtis" })
public class CmmnFileAdtisController {
	private static final Logger logger = LoggerFactory.getLogger(CmmnFileAdtisController.class);

	@Autowired
	private CmmnAdtisService CmmnAdtisService;

	@RequestMapping({ "/attachFileDownload.do", "/attachFileDownload.ajax" })
	public View attachFileDownload(HttpServletRequest request, Model model) throws Exception {
		CmmnUtil.methodLog("공통 파일다운로드 뷰");
		HashMap<String, Object> pMap = CmmnUtil.getParameters(request);
		HashMap<String, Object> fileInfo = (HashMap<String, Object>) this.CmmnAdtisService.select("cmmnFileAdtis.selectAttachFileInfo", pMap);
		model.addAttribute("fileInfo", fileInfo);
		return (View) new FileDownloadView();
	}

	@RequestMapping({ "/boardFileDownload.do", "/boardFileDownload.ajax" })
	public View boardFileDownload(HttpServletRequest request, Model model) throws Exception {
		CmmnUtil.methodLog("게시판 파일다운로드 뷰");
		HashMap<String, Object> pMap = CmmnUtil.getParameters(request);
		HashMap<String, Object> fileInfo = (HashMap<String, Object>) this.CmmnAdtisService.select("cmmnFileAdtis.selectBoardFileInfo", pMap);
		model.addAttribute("fileInfo", fileInfo);
		return (View) new FileDownloadView();
	}
    //기존adtis의 게시판내역에서 받는경우만
	@RequestMapping({ "/fileUploadAttach.ajax" })
	@ResponseBody
	public Object fileUploadAttach(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("공통 파일업로드");
		HashMap<String, Object> resultMap = CmmnUtil.resultMsg("SUCCESS", "정상적으로 처리되었습니다.");
		resultMap.put("data", this.CmmnAdtisService.insertAttatchFileInfo("cmmnFileAdtis.insertAttachFileInfo", request));
		return resultMap;
	}

	@RequestMapping({ "/fileDeleteBoard.ajax" })
	@ResponseBody
	public Object fileDeleteBoard(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("게시판 파일삭제");
		HashMap<String, Object> fileInfo = (HashMap<String, Object>) this.CmmnAdtisService
				.select("cmmnFileAdtis.selectBoardFileInfo", CmmnUtil.getParameters(request));
		int delete = this.CmmnAdtisService.delete("cmmnFileAdtis.deleteBoardFilInfo", CmmnUtil.getParameters(request));
		if (delete > 0)
			FileUtil.cmmnFileDelete(fileInfo);
		HashMap<String, Object> codeMap = new HashMap<>();
		codeMap.put("CODE", "SUCCESS");
		codeMap.put("MESSAGE", "정상적으로 처리되었습니다.");
		HashMap<String, Object> dataMap = new HashMap<>();
		dataMap.put("RESULT", codeMap);
		dataMap.put("fileList",
				this.CmmnAdtisService.selectList("cmmnFileAdtis.selectBoardFileList", CmmnUtil.getParameters(request)));
		return dataMap;
	}
	
	/**
	 * 엑셀다운로드
	 */
	/*
	@RequestMapping(value="/excelDownload", method = RequestMethod.POST)
	public View excelDownload(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, Model model) {
		CmmnUtil.methodLog("excelDownload : " + hMap.toString());
		
		HashMap<String, Object> fileInfo = new HashMap<>();
		fileInfo.put("FILE_NM", "file.xlsx");
		fileInfo.put("DATA", hMap);
		model.addAttribute("fileInfo", fileInfo);				
		return (View) new ExcelDownloadView();
	}
	*/
	
	/**기존adtis의 게시판내역에서 받는경우만
	 * 파일 업로드
	 */
	@RequestMapping("/fileUpload")
	@ResponseBody
	public Object fileUpload(HttpServletRequest request) {
		CmmnUtil.methodLog("fileUpload");
				
		try {
			HashMap<String, Object> hMap = CmmnUtil.getParameters(request);
			List<HashMap<String, Object>> fileList = FileUtil.cmmnFileUpLoadAttach(request);			
			for (HashMap<String, Object> fileInfo : fileList) {			
				logger.info("@ fileInfo  : " + fileInfo);
				fileInfo.put("FILE_NAME", fileInfo.get("FILE_NM"));
				fileInfo.put("FILE_ORIGIN_NAME", fileInfo.get("ORG_FILE_NM"));
				fileInfo.put("USE_YN", "Y");				
			}
			
			int fileSeq = NumberUtils.toInt(String.valueOf(hMap.get("FILE_SEQ")));
			if(fileSeq==0) {
				fileSeq = (int) this.CmmnAdtisService.select("cmmnFileAdtis.selectCmmnNewFileSeq", hMap);
			}
			hMap.put("FILE_SEQ", fileSeq);
			int seqNo = (int) this.CmmnAdtisService.select("cmmnFileAdtis.selectCmmnMaxSeqNo", hMap);
			
			for (HashMap<String, Object> fileInfo : fileList) {				
				fileInfo.put("FILE_SEQ",  ++seqNo);
				//fileInfo.put("SEQ_NO", ++seqNo);
				fileInfo.put("BOARD",  hMap.get("BOARD"));
				fileInfo.put("BOARD_SEQ",  hMap.get("BOARD_SEQ"));
				fileSeq=seqNo;
			}
			
			this.CmmnAdtisService.insertList("cmmnFileAdtis.insertCmmnFileInfo", fileList);
			HashMap<String, Object> fileMap = new HashMap<>();
			fileMap.put("FILE_SEQ", fileSeq);
			
			return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG, fileMap);
		} catch (Exception e) {
			e.printStackTrace();
			return CmmnUtil.resultMsg(Gv.FAIL, Gv.FAILMSG);
		}
	}
	
	/**기존adtis의 게시판내역에서 받는경우만
	 * 업로드 파일 삭제
	 */
	@RequestMapping(value = "/fileDelete")
	@ResponseBody
	public ResponseEntity<Object> fileDelete(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) {
		CmmnUtil.methodLog("fileDelete : " + hMap.toString());
		
		try {
			// 파일삭제
			HashMap<String, Object> fileInfo = (HashMap<String, Object>) this.CmmnAdtisService.select("cmmnFileAdtis.selectCmmnFile", hMap);
			fileInfo.put("FILE_NM", fileInfo.get("FILE_NAME"));
			FileUtil.cmmnFileDelete(fileInfo);
			// db의 파일내용 삭제
			this.CmmnAdtisService.delete("cmmnFileAdtis.deleteCmmnFilInfo", hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**기존adtis의 게시판내역에서 받는경우만
	 * 개별 파일 다운로드
	 */
	@RequestMapping(value="/fileDownload", method = RequestMethod.POST )
	public View fileDownload(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, Model model) throws Exception {
		CmmnUtil.methodLog("fileDownload : " + hMap.toString());
		
		HashMap<String, Object> fileInfo = (HashMap<String, Object>) this.CmmnAdtisService.select("cmmnFileAdtis.selectCmmnFile", hMap);
		fileInfo.put("FILE_NM", fileInfo.get("FILE_NAME"));
		fileInfo.put("ORG_FILE_NM", fileInfo.get("FILE_ORIGIN_NAME"));
		model.addAttribute("fileInfo", fileInfo);
		return (View) new FileDownloadView();
	}
	
	/** 기존adtis의 게시판내역에서 받는경우만
	 * 다운로드 파일 리스트
	 */
	@RequestMapping(value="/fileDownloadList", method = RequestMethod.POST )
	@ResponseBody
	public ResponseEntity<Object> fileDownloadList(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, Model model) throws Exception {
		CmmnUtil.methodLog("fileDownloadList : " + hMap.toString());
				
		try {
			List<Object> fileList =  CmmnAdtisService.selectList("cmmnFileAdtis.selectCmmnFileList", hMap);
			return new ResponseEntity<>(fileList, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]fileDownloadList : " + hMap.toString() + " : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
	}
	
	/**
	 * 서버상의 이미지를 브라우저에 리턴
	 * ex) /getImage?FILE_SEQ=62&SEQ_NO=4
	 */
	/*
	 @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> fileInfo = (HashMap<String, Object>) this.CmmnAdtisService.select("cmmnFileAdtis.selectCmmnFile", CmmnUtil.getParameters(request));
		OutputStream out = response.getOutputStream();
        FileInputStream fis = null;
        //String file = "C:/temp/2021/11/17/20211117203204_2e115bd8f9454b239c5462bcfc222e9a";
        String file = String.valueOf(fileInfo.get("FILE_PATH"))+String.valueOf(fileInfo.get("FILE_NAME"));
 
        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            out.flush();
        }
	}
	*/
}


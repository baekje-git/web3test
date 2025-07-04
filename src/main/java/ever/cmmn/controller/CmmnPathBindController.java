package ever.cmmn.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.cmmn.service.CmmnExtService;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;

@Controller
public class CmmnPathBindController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@RequestMapping({ "/bindURL/{pDir1}/{pDir2}.do", "/bindURL/{pDir1}/{pDir2}/{pDir3}.do", "/bindURL/{pDir1}/{pDir2}/{pDir3}/{pDir4}.do" })
	public String viewBindPathUrl(@PathVariable String pDir1, @PathVariable String pDir2, 
			                      @PathVariable(required = false) String pDir3, @PathVariable(required = false) String pDir4, 
			                      HttpServletRequest request) throws Exception {
		String bindPath = "";
		if (StringUtils.isBlank(pDir3) && StringUtils.isBlank(pDir4)) {
			bindPath = String.valueOf(pDir1) + "/" + pDir2;
		} else if (!StringUtils.isBlank(pDir4) && !StringUtils.isBlank(pDir4)) {
			bindPath = String.valueOf(pDir1) + "/" + pDir2 + "/" + pDir3 + "/" + pDir4;
		} else if (StringUtils.isBlank(pDir4)) {
			bindPath = String.valueOf(pDir1) + "/" + pDir2 + "/" + pDir3;
		} else {
			bindPath = String.valueOf(pDir1) + "/" + pDir2 + "/" + pDir4;
		}
		CmmnUtil.methodLog("bindURL : " + bindPath);
		return bindPath;
	}

	/**
	 * @param pDir1 : mybatis의 namespace
	 * @param pDir2 : mybatis의 query id
	 *  
	 */
	@RequestMapping({ "/bindSELECT/{pDir1}/{pDir2}.ajax" })
	@ResponseBody
	public Object selectBindPathList(@PathVariable String pDir1, @PathVariable String pDir2, HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("bindSELECT : " + pDir1 + "." + pDir2);
		List<Object> dataList = this.cmmnService.selectList(String.valueOf(pDir1) + "." + pDir2, CmmnUtil.getParameters(request));
		return dataList;
	}
		

	@RequestMapping({ "/bindSAVE/{pDir1}/{pDir2}.ajax", "/bindSAVE/{pDir1}/{pDir2}/{pDir3}.ajax", "/bindSAVE/{pDir1}/{pDir2}/{pDir3}/{pDir4}.ajax" })
	@ResponseBody
	public Object CommonSaveAjx(@PathVariable String pDir1, @PathVariable String pDir2,
			@PathVariable(required = false) String pDir3, @PathVariable(required = false) String pDir4,
			HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("bindSAVE : " + pDir1 + "." + pDir2 + "." + StringUtils.isBlank(pDir3) + "." + StringUtils.isBlank(pDir4));
		this.cmmnService.doSave(String.valueOf(pDir1) + "." + pDir2,
				StringUtils.isBlank(pDir3) ? "" : (String.valueOf(pDir1) + "." + pDir3),
				StringUtils.isBlank(pDir4) ? "" : (String.valueOf(pDir1) + "." + pDir4),
				CmmnUtil.parsingToMap(request.getParameter("paramData")));
		return CmmnUtil.resultMsg("SUCCESS", Gv.SUCCMSG);
	}
	
	@RequestMapping({ "/bindSAVESuffix/{pDir1}/{pDir2}.ajax" })
	@ResponseBody
	public Object CommonSaveSuffixAjx(@PathVariable String pDir1, @PathVariable String pDir2,
			HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("bindSAVESuffix : " + pDir1 + "." + pDir2);
		this.cmmnService.doSaveSuffix(String.valueOf(pDir1) + "." + pDir2, CmmnUtil.parsingToMap(request.getParameter("paramData")));
		return CmmnUtil.resultMsg("SUCCESS", Gv.SUCCMSG);
	}
	
}

package ever.cmmn.controller;

import ever.cmmn.service.CmmnService;
import ever.support.cmmn.CmmnUtil;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/cmmn" })
public class CmmnController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;

	@RequestMapping({ "/selectCmmnCodeList.ajax" })
	@ResponseBody
	public Object selectCmmnCodeList(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("공통코드조회");
		List<Object> list = this.cmmnService.selectList("cmmn.selectCmmnCodeList", CmmnUtil.getParameters(request));
		return list;
	}

	@RequestMapping({ "/callProcedure.ajax" })
	@ResponseBody
	public Object callProcedure(HttpServletRequest request) throws Exception {
		CmmnUtil.methodLog("프로시저호출 테스트");
		HashMap<String, Object> hMap = CmmnUtil.parsingToMap(request.getParameter("paramData"));
		HashMap<String, Object> callParams = (HashMap<String, Object>) hMap.get("callParams");
		this.logger.debug(">>> callParams before : " + callParams);
		this.cmmnService.callProcedure((String) callParams.get("CALL_ID"), callParams);
		this.logger.debug(">>> callParams after  : " + callParams);
		return callParams;
	}
}

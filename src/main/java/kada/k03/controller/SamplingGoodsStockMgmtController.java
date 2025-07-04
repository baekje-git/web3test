package kada.k03.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.session.SessionUtil;
import kada.k03.service.SamplingGoodsStockMgmtService;


@Controller
@RequestMapping("/k03/samplinggoodsstock/")
public class SamplingGoodsStockMgmtController {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CmmnService cmmnService;
	
	@Autowired
	private SamplingGoodsStockMgmtService samplinggoodsstockmgmtService;
	        
	
    /**
     * <pre>
     * @MethodName : saveClubGroup
     * @Description : 구단별 그룹 저장
     * @param hMap - 입력데이터
     * @param request
     * @return
     * </pre>
     */                     
    @RequestMapping(value="/saveSampleGoodsMgntReg.do",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> saveSampleGoodsMgntReg(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request) throws Exception{
        CmmnUtil.methodLog("시료채취용품관리 저장");        
        
        try {
        	hMap.put("GSS", SessionUtil.getUserInfo());
        	samplinggoodsstockmgmtService.saveSampleGoodsMgntReg(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("[오류]saveSampleGoodsMgntReg : ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
    }
    
}

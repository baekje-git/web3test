package ever.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ever.cmmn.service.CmmnService;
import ever.rest.vo.CodeDtlVO;
import ever.rest.vo.CodeMstVO;
import ever.support.cmmn.CmmnUtil;
import ever.support.cmmn.XMLGenerator;

@Controller
@RequestMapping("/sample/rest")
public class RestApiController2 {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CmmnService cmmnService;

	 /**
     * SELECT LIST
     */
    @RequestMapping(value="code",  method = RequestMethod.GET)
    public ResponseEntity<Object> getCodeList() throws Exception {
        
        HashMap<String, Object> hMap = new HashMap<String, Object>();

        List<Object> list = cmmnService.selectList("sample.selectCODE00TB", hMap);
        
        for(Object row : list) {
        	HashMap<String, Object> item = (HashMap<String, Object>)row;
        	List<Object> detailList = cmmnService.selectList("sample.selectCODE01TB", item);
        	item.put("DTL_CODES", detailList);
        }

        XMLGenerator xmlGen = new XMLGenerator();
        String xmlStr = xmlGen.genListData("sampleList","sample",list);
        
        if(list.size() < 1){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @RequestMapping(value="code2",produces=MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public Object selectCODE00TB(HttpServletRequest request) throws Exception {
		
		Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectCODE00TB", CmmnUtil.getParameters(request));
		
		for(Object row : list) {
        	HashMap<String, Object> item = (HashMap<String, Object>)row;
        	List<Object> detailList = cmmnService.selectList("sample.selectCODE01TB", item);
        	item.put("DTL_CODE", detailList);
        }
		
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");
		
		return mapList;
	}
    
    @RequestMapping(value="code3",produces=MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public Object selectCODE3(HttpServletRequest request) throws Exception {
		
    	Map<Object, Object> mapList = new HashMap<Object, Object>();
		List<Object> list = cmmnService.selectList("sample.selectCODE00TB_2", CmmnUtil.getParameters(request));
		List<Object> detailList = cmmnService.selectList("sample.selectCODE01TB_2", CmmnUtil.getParameters(request));
		
		for(Object row : list) {
			HashMap<String,Object> item = (HashMap<String,Object>)row;
			List<Object> detailList2 = detailList.stream().filter(t -> {
				HashMap<String,Object> r = (HashMap<String,Object>)t;
				return String.valueOf(item.get("MST_CODE")).equals(r.get("MST_CODE"))? true : false;				
			}).collect(Collectors.toList());
			item.put("DTL_CODE", detailList2);
        }
		
		mapList.put("Data", list);
		mapList.put("Result", 0);
		mapList.put("Message", "성공");
		
		return mapList;
	}
	
}

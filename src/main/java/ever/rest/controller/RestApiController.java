package ever.rest.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ever.cmmn.service.CmmnService;
import ever.rest.vo.SampleListVO;
import ever.rest.vo.SampleVO;
import ever.support.Gv;
import ever.support.cmmn.XMLGenerator;

/**
 * 
 * <pre>
 * -------------------------------------------------------------
 * @프로젝트 : ever
 * @파일경로 : ever.rest.controller
 * @파일이름 : RestController.java 
 * @작성일자 : 2020. 06. 22. 
 * @작성자명 : KMSDEV 
 * @상세설명 : REST 컨트롤러
 * -------------------------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/api")
public class RestApiController {
    
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CmmnService cmmnService;
    
    /**
     * SELECT 
     */
    @RequestMapping(value="sample/{no}",  method = RequestMethod.GET)
    public ResponseEntity<Object> getSample(@PathVariable("no") String no) throws Exception {
        
        HashMap<String, Object> hMap = new HashMap<String, Object>();
        hMap.put("NO", no);
        
        HashMap<String, Object> rMap = (HashMap<String, Object>) cmmnService.select("sample.selectSampleList", hMap);
        
        String xmlStr;
        XMLGenerator xmlGen = new XMLGenerator();
        xmlStr = xmlGen.genXMLtoMapData("sample",rMap);
        System.out.println(xmlStr);
        if(rMap == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(rMap, HttpStatus.OK);
    }
    
    /**
     * SELECT LIST
     */
    @RequestMapping(value="sample",  method = RequestMethod.GET)
    public ResponseEntity<Object> getSampleList() throws Exception {
        
        HashMap<String, Object> hMap = new HashMap<String, Object>();

        List<Object> list = cmmnService.selectList("sample.selectSampleList", hMap);

        XMLGenerator xmlGen = new XMLGenerator();
        String xmlStr = xmlGen.genListData("sampleList","sample",list);
        
        if(list.size() < 1){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * INSERT
     */
    @RequestMapping(value="sample",  method = RequestMethod.POST)
    public ResponseEntity<Object> postSample(@RequestBody JSONObject jsonString, UriComponentsBuilder ucb) throws Exception {
        if (jsonString.size() < 1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //JSON > MAP
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hMap = mapper.readValue(jsonString.toString(), new TypeReference<HashMap<String, Object>>() {}); 

        int saved = cmmnService.insert("sample.insertSample", hMap);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/sample/")
            .path(String.valueOf(saved))
            .build()
            .toUri();
        headers.setLocation(locationUri);
        
        return new ResponseEntity<>(Gv.SUCC, headers, HttpStatus.CREATED);
    }

    /**
     * INSERT_UPDATE / 전체수정
     */
    @RequestMapping(value="sample/{no}",  method = RequestMethod.PUT)
    public ResponseEntity<Object> putSample(@PathVariable("no") String no, @RequestBody JSONObject jsonString, UriComponentsBuilder ucb) throws Exception {
        if (jsonString.size() < 1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //JSON > MAP
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hMap = mapper.readValue(jsonString.toString(), new TypeReference<HashMap<String, Object>>() {}); 
        hMap.put("NO", no);
        
        int saved = cmmnService.update("sample.saveSample", hMap);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/sample/")
            .path(String.valueOf(saved))
            .build()
            .toUri();
        headers.setLocation(locationUri);
        
        return new ResponseEntity<>(Gv.SUCC, headers, HttpStatus.OK);
    }

    /**
     * UPDATE /일부수정
     */
    @RequestMapping(value="sample/{no}",  method = RequestMethod.PATCH)
    public ResponseEntity<Object> patchSample(@PathVariable("no") String no, @RequestBody JSONObject jsonString) throws Exception {
        if (jsonString.size() < 1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //JSON > MAP
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> hMap = mapper.readValue(jsonString.toString(), new TypeReference<HashMap<String, Object>>() {}); 
        hMap.put("NO", no);
        
        int saved = cmmnService.update("sample.updateSample", hMap);
        
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
    
    /**
     * DELETE
     */
    @RequestMapping(value="sample/{no}",  method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSample(@PathVariable("no") String no) throws Exception {
        
        HashMap<String, Object> hMap = new HashMap<String, Object>();
        hMap.put("NO", no);
        int delCnt = 0;
        delCnt = cmmnService.delete("sample.deleteSample", hMap);

        if(delCnt == 0 ){
            return new ResponseEntity<>(Gv.FAIL, HttpStatus.NOT_MODIFIED);
        }
        
        return new ResponseEntity<>(Gv.SUCC, HttpStatus.OK);
    }
    
    

    /**
     * SELECT 
     */
    @RequestMapping(value="sample/xml/{no}",  method = RequestMethod.GET, produces=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> getSampleXml(@PathVariable("no") String no) throws Exception {
        
        HashMap<String, Object> hMap = new HashMap<String, Object>();
        hMap.put("NO", no);
        
        SampleVO vo = (SampleVO) cmmnService.select("sample.selectSampleVOList", hMap);

        if(vo == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }
    
    /**
     * SELECT LIST
     */
    @RequestMapping(value="sample/xml",  method = RequestMethod.GET, produces=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> getSampleListXml() throws Exception {
        
        HashMap<String, Object> hMap = new HashMap<String, Object>();

        List<Object> samples = (List<Object>) cmmnService.selectList("sample.selectSampleVOList", hMap);

        SampleListVO listVo = new SampleListVO();
        List<SampleVO> voList = new ArrayList<SampleVO>();
        
        if(samples.size() < 1){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            for(Object obj : samples) {
                SampleVO vo = (SampleVO) obj;
                voList.add(vo);
            }
        }
        listVo.setSamples(voList);
        return new ResponseEntity<>(listVo, HttpStatus.OK);
    }

    /**
     * INSERT
     */
    @RequestMapping(value="sample/xml",  method = RequestMethod.POST, produces=MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> postSampleXml(@RequestBody SampleVO vo, UriComponentsBuilder ucb) throws Exception {
        if (vo == null) {
            return new ResponseEntity<>(xmlresult(Gv.FAIL),HttpStatus.CONFLICT);
        }
        //VO > MAP
        HashMap<String, Object> hMap = new HashMap<>();
        hMap.put("NO",vo.getNo());
        hMap.put("TITLE",vo.getTitle());
        hMap.put("INDATE",vo.getIndate());

        int saved = cmmnService.insert("sample.insertSample", hMap);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/sample/")
            .path(String.valueOf(saved))
            .build()
            .toUri();
        headers.setLocation(locationUri);
        
        return new ResponseEntity<>(xmlresult(Gv.SUCC), headers, HttpStatus.CREATED);
    }

    /**
     * INSERT_UPDATE / 전체수정
     */
    @RequestMapping(value="sample/xml/{no}",  method = RequestMethod.PUT, produces=MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> putSampleXml(@PathVariable("no") String no, @RequestBody SampleVO vo, UriComponentsBuilder ucb) throws Exception {
        if (vo == null) {
            return new ResponseEntity<>(xmlresult(Gv.FAIL),HttpStatus.CONFLICT);
        }
        //VO > MAP
        HashMap<String, Object> hMap = new HashMap<>();
        hMap.put("NO",vo.getNo());
        hMap.put("TITLE",vo.getTitle());
        hMap.put("INDATE",vo.getIndate());
        
        int saved = cmmnService.update("sample.saveSample", hMap);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/sample/")
            .path(String.valueOf(saved))
            .build()
            .toUri();
        headers.setLocation(locationUri);
        
        return new ResponseEntity<>(xmlresult(Gv.SUCC), headers, HttpStatus.OK);
    }

    /**
     * UPDATE /일부수정
     */
    @RequestMapping(value="sample/xml/{no}",  method = RequestMethod.PATCH, produces=MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> patchSampleXml(@PathVariable("no") String no, @RequestBody SampleVO vo) throws Exception {
        if (vo == null) {
            return new ResponseEntity<>(xmlresult(Gv.FAIL),HttpStatus.CONFLICT);
        }
        //VO > MAP
        HashMap<String, Object> hMap = new HashMap<>();
        hMap.put("NO",vo.getNo());
        hMap.put("TITLE",vo.getTitle());
        hMap.put("INDATE",vo.getIndate());
        
        int saved = cmmnService.update("sample.updateSample", hMap);
        
        return new ResponseEntity<>(xmlresult(Gv.SUCC), HttpStatus.OK);
    }
    
    /**
     * DELETE
     */
    @RequestMapping(value="sample/xml/{no}",  method = RequestMethod.DELETE, produces=MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> deleteSampleXml(@PathVariable("no") String no) throws Exception {
        
        HashMap<String, Object> hMap = new HashMap<String, Object>();
        hMap.put("NO", no);
        int delCnt = 0;
        delCnt = cmmnService.delete("sample.deleteSample", hMap);

        if(delCnt == 0 ){
            return new ResponseEntity<>(xmlresult(Gv.FAIL), HttpStatus.NOT_MODIFIED);
        }
        
        return new ResponseEntity<>(xmlresult(Gv.SUCC), HttpStatus.OK);
    }
    
    private String xmlresult(String code) {
        String retStr;
        retStr = "<RESULT>";
        retStr+= "<CODE>";
        retStr+= code;
        retStr+= "</CODE>";
        retStr+= "</RESULT>";
        
        return retStr;
    }
    
    /**
     * SELECT LIST
     */
    @RequestMapping(value="sample/code",  method = RequestMethod.GET)
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
}

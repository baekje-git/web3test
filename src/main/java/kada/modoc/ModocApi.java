package kada.modoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ever.cmmn.dao.CmmnDao;
import ever.support.exception.CustomException;
import kada.util.Util;

@Service
public class ModocApi {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("#{info['modoc.apiUrl']}")
    private String apiUrl;
	
	@Value("#{info['modoc.apiId']}")
    private String apiId;
	
	@Value("#{info['modoc.apiPw']}")
    private String apiPw;
	
	private String apiGetUrl = "";
	private String apiGetUrl2 = "";
	
	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	
	public String login() {
		String token = "";
		
		try {
            String url = apiUrl+"users/authenticate?force";

            Base64 b = new Base64();
            token = b.encodeAsString(new String(apiId+":"+apiPw).getBytes());
            token = "Basic " + token;
            String jsonStr = postHttp(url, "data", token, "", new SaveErrorLog("로그인","apiId"));
	    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    	Map<String, Object> rtnMap = (Map<String, Object>) gson.fromJson(jsonStr, LinkedHashMap.class);
	    	token = rtnMap.get("token").toString();
        }
        catch(Exception e) {
        	logger.error("modocApi.login error", e);
        }
		
		return token;
	}
	
	public List<HashMap<String, Object>> getData(String url, String keyName, String token, SaveErrorLog log){
		List<HashMap<String, Object>> rtnListMap = null;
        try {
        	url = apiUrl+url;

        	token = "Bearer " + token;
            String jsonStr = getHttp(url, keyName, token, "", log);
            logger.debug(url+" => result : "+ jsonStr);

	    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    	rtnListMap = gson.fromJson(jsonStr.toString() , new TypeToken<ArrayList<LinkedHashMap>>(){}.getType() );
        }
        catch(Exception e) {
        	logger.error("modocApi.getData error", e);
        }
		return rtnListMap;
	}
	
	public String getDataForString(String url, String keyName, String token, SaveErrorLog log){
		String rtn = null;
        try {
        	url = apiUrl+url;

        	token = "Bearer " + token;
            String jsonStr = getHttp(url, keyName, token, "", log);
            logger.debug(url+" => result : "+ jsonStr);

	    	//Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    	//rtn = gson.fromJson(jsonStr.toString() , new TypeToken<ArrayList<LinkedHashMap>>(){}.getType() );
            rtn = jsonStr;
        }
        catch(Exception e) {
        	logger.error("modocApi.getDataForString error", e);
        }
		return rtn;
	}

	public String postData(String url, String keyName, String token, String jsonParam, SaveErrorLog log){
		String retStr = "";
        try {
        	url = apiUrl+url;

        	token = "Bearer " + token;
            retStr = postHttp(url, keyName, token, jsonParam, log);
            logger.debug(url+" => result : "+ retStr);
        }
        catch(Exception e) {
            logger.error("modocApi.postData error", e);
        }
		return retStr;
	}

	public String putData(String url, String keyName, String token, String jsonParam, String id, SaveErrorLog log){
		String retStr = "";
        try {
        	url = apiUrl+url+"/"+id;

        	token = "Bearer " + token;
        	retStr = putHttp(url, keyName, token, jsonParam, log);
            logger.debug(url+" => result : "+ retStr);
        }
        catch(Exception e) {
        	logger.error("modocApi.putData error", e);
        }
		return retStr;
	}

	private String getHttp(String httpUrl, String keyName, String token, String jsonParam, SaveErrorLog log){
    	String resStr = "";
		try{
			HttpClient client = HttpClientBuilder.create().build();
			
			HttpGet getRequest = new HttpGet(httpUrl);
			getRequest.addHeader("Authorization", token);
			logger.info("Executing request : {}",getRequest.getRequestLine());
			HttpResponse response = client.execute(getRequest);

			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				resStr = handler.handleResponse(response);
				JSONParser jsonParser = new JSONParser();
		    	HashMap<String, Object> resMap = (HashMap<String, Object>) jsonParser.parse(resStr);
		    	resStr = resMap.get(keyName).toString();
				logger.info("GET Result : \n"+Util.jsonFormatter(resStr));
			} else {
				resStr = EntityUtils.toString(response.getEntity());
				logger.error("GET Error Status Code : " + response.getStatusLine().getStatusCode());
				logger.error("GET Error : \n" + Util.jsonFormatter(resStr));				
				log.traceLog(getRequest.getRequestLine().toString(), resStr);
			}

		}catch(Exception e) {			
			logger.error("modocApi.getHttp error", e);
		}
		return resStr;
	}

	private String postHttp(String httpUrl, String keyName, String token, String jsonParam, SaveErrorLog log){
    	String resStr = "";
		try{
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(httpUrl);
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection", "keep-alive");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.addHeader("Authorization", token);
			postRequest.setEntity(new StringEntity(jsonParam, "utf-8"));
			String body = Util.jsonFormatter(IOUtils.toString(postRequest.getEntity().getContent(),"UTF-8"));
			logger.info("Executing request : {}",postRequest.getRequestLine());
			logger.info("Executing request body : \n{}",body);

			HttpResponse response = client.execute(postRequest);

			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				resStr = handler.handleResponse(response);
				JSONParser jsonParser = new JSONParser();
		    	HashMap<String, Object> resMap = (HashMap<String, Object>) jsonParser.parse(resStr);
		    	resStr = resMap.get(keyName).toString();
				logger.info("POST Result: \n" + Util.jsonFormatter(resStr));
			} else {
				resStr = EntityUtils.toString(response.getEntity());
				logger.error("POST Error Status Code : " + response.getStatusLine().getStatusCode());
				logger.error("POST Error : \n" + Util.jsonFormatter(resStr));				
				log.traceLog(body, resStr);
			}

		}catch(Exception e) {
			logger.error("modocApi.postHttp error", e);
		}
		return resStr;
	}


	private String putHttp(String httpUrl, String keyName, String token, String jsonParam, SaveErrorLog log){
    	String resStr = "";
		try{

			HttpClient client = HttpClientBuilder.create().build();
			HttpPut putRequest = new HttpPut(httpUrl);
			putRequest.setHeader("Accept", "application/json");
			putRequest.setHeader("Connection", "keep-alive");
			putRequest.setHeader("Content-Type", "application/json");
			putRequest.addHeader("Authorization", token);
			putRequest.setEntity(new StringEntity(jsonParam, "utf-8"));
			String body = Util.jsonFormatter(IOUtils.toString(putRequest.getEntity().getContent(),"UTF-8"));
			logger.info("Executing request : {}",putRequest.getRequestLine());
			logger.info("Executing request body : \n{}",body);
			
			HttpResponse response = client.execute(putRequest);

			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				resStr = handler.handleResponse(response);
				JSONParser jsonParser = new JSONParser();
		    	HashMap<String, Object> resMap = (HashMap<String, Object>) jsonParser.parse(resStr);
		    	resStr = resMap.get(keyName).toString();
				logger.info("PUT Result: \n" + Util.jsonFormatter(resStr));
			} else {
				resStr = EntityUtils.toString(response.getEntity());				
				logger.error("PUT Error Status Code : " + response.getStatusLine().getStatusCode());
				logger.error("PUT Error : \n" + Util.jsonFormatter(resStr));				
				log.traceLog(body, resStr);
			}

		}catch(Exception e) {
			logger.error("modocApi.putHttp error", e);
		}
		return resStr;
	}


	public void callHttpApi(){
		try{
			logger.debug("==========================================================");
			logger.debug("CALL Connection Start");
			logger.debug("CALL Url : " + apiGetUrl);

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(apiGetUrl);
			HttpResponse response = client.execute(getRequest);
			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				logger.debug("CALL Connection response : " + handler.handleResponse(response));
			} else {
				logger.debug("CALL Connection response is error : " + response.getStatusLine().getStatusCode());
				logger.debug("CALL Connection response is error message: " + EntityUtils.toString(response.getEntity()));
			}
			logger.debug("CALL Connection End");

		}catch(Exception e) {
			logger.error("modocApi.callHttpApi error", e);
		}
	}

	public void callHttpApi2(){
		try{
			logger.debug("==========================================================");
			logger.debug("CALL Connection Start");
			logger.debug("CALL Url : " + apiGetUrl2);

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(apiGetUrl);
			HttpResponse response = client.execute(getRequest);
			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				logger.debug("CALL Connection response : " + handler.handleResponse(response));
			} else {
				logger.debug("CALL Connection response is error : " + response.getStatusLine().getStatusCode());
				logger.debug("CALL Connection response is error message: " + EntityUtils.toString(response.getEntity()));
			}
			logger.debug("CALL Connection End");

		}catch(Exception e) {
			logger.error("modocApi.callHttpApi2 error", e);
		}
	}
	
	private void saveError(String menuName, String memuData, String sendData, String returnData) {
		HashMap<String, Object> pMap = new HashMap<>();
		pMap.put("MENU_NAME", menuName);
		pMap.put("MENU_DATA", memuData);
		pMap.put("SEND_DATA", sendData);
		pMap.put("RETURN_DATA", returnData);
		cmmnDao.insert("modoc.ModocApi.insertModocError", pMap);
	}
	
	private HashMap<String, Object> getJson2Map(String resStr) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		HashMap<String, Object> resMap = (HashMap<String, Object>) jsonParser.parse(resStr);
		return resMap;
	}
	
	private HashMap<String, Object> getJson2Map(String resStr, String key) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		HashMap<String, Object> resMap = (HashMap<String, Object>) jsonParser.parse(resStr);
		return (HashMap<String, Object>) resMap.get(key);
	}
	
	private <T> T getJson2Map(String resStr, String key, Class<T> type) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		return type.cast(jsonParser.parse(resStr));		
	}
	
	
	/**
	 * modoc 에서 리턴되는 값이 에러인지 판단하고 에러코드를 리턴
	 * 
	 */
	private String getErrorCode(String jsonStr) throws ParseException {
		if(StringUtils.isEmpty(jsonStr))return "";
		
		JSONParser jsonParserChk = new JSONParser();
		HashMap<String, Object> chkMap = (HashMap<String, Object>) jsonParserChk.parse(jsonStr);
		HashMap<String,Object> error = (HashMap<String, Object>) chkMap.get("error");
		return error == null ? "" : Util.getValue("code", error);
	}
	
	/**
	 * modoc 에서 리턴되는 값이 에러인지 판단하고 에러메세지를 리턴
	 * 
	 */
	private String getErrorMessage(String jsonStr) throws ParseException {
		if(StringUtils.isEmpty(jsonStr))return "";
		
		JSONParser jsonParserChk = new JSONParser();
		HashMap<String, Object> chkMap = (HashMap<String, Object>) jsonParserChk.parse(jsonStr);
		HashMap<String,Object> error = (HashMap<String, Object>) chkMap.get("error");
		return error == null ? "" : Util.getValue("message", error);
	}
	
	/**
	 * modoc api통신시 송/수신 에러 로깅용 
	 * 
	 */
	private class SaveErrorLog{
		private String menuName;
		private String memuData;
		
		/**
		 * 호출하는 api의 메뉴명, 식별자 데이터를 파라미터로 넘김
		 * 
		 * @param menuName
		 * @param memuData
		 */
		public SaveErrorLog(String menuName, String memuData) {
			this.menuName = menuName;
			this.memuData = memuData;
		}
				
		/**
		 * modoc api 통신에서 전송 메세지, 에러메세지를 저장
		 * 
		 * @param sendData
		 * @param returnData
		 */
		public void traceLog(String sendData, String returnData) {
			HashMap<String, Object> pMap = new HashMap<>();
			pMap.put("MENU_NAME", menuName);
			pMap.put("MENU_DATA", memuData);
			pMap.put("SEND_DATA", sendData);
			pMap.put("RETURN_DATA", returnData);
			cmmnDao.insert("modoc.ModocApi.insertModocError", pMap);
		};
	}
	
	
	/**
	 * modoc에 선수등록 
	 * 
	 */
	@Async
	public void sendAthletesAsync(HashMap<String, Object> pMap) throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String type = "athletes";
		String fn = Util.getValue("fn", pMap); // I,U
		String resStr = "";
		HashMap<String, Object> sqlMap = null;

		String token = login();
		sqlMap = (HashMap<String, Object>) cmmnDao.select("modoc.ModocApi.selectAthletes", pMap);
		String strJson = gson.toJson(sqlMap);
		logger.info("Send Json : \n"+strJson);
		
		if(fn.equals("I")){
			resStr = postData(type, type.substring(0, type.length()-1) , token, strJson, new SaveErrorLog("선수등록", Util.getValue("USER_ID", pMap)));
			HashMap<String, Object> resMap = getJson2Map(resStr);
	    	logger.info("return : " + resMap.toString());
	    		    	
	    	if(!resMap.containsKey("error")) {
	    		logger.info("return id : " + resMap.get("id"));	    		
		    	resMap.put("USER_ID", pMap.get("USER_ID"));
		    	cmmnDao.update("modoc.ModocApi.updatePlayerNo", resMap);
	    	}else {
	    		saveError("선수정보저장" , String.valueOf(pMap.get("USER_ID")) , strJson, resMap.toString());
	    	}
	    	
		}else if(fn.equals("U")){
			resStr = putData(type, "result", token, strJson, String.valueOf(sqlMap.get("id")), new SaveErrorLog("선수수정", Util.getValue("USER_ID", pMap)));
			logger.info("resStr : " + resStr);	    
						
			if(!resStr.equals("OK")) {
				saveError("선수정보저장" , String.valueOf(pMap.get("USER_ID")) , strJson, resStr);
			}
		}
	}

	/**
	 * modoc data 수신,저장
	 * 
	 **/
	public void getReciveData(HashMap<String, Object> hMap){
		String token = login();
		String url = Util.getValue("url", hMap);
		String keyName = Util.getValue("keyName", hMap);
		String save = Util.getValue("save", hMap);
		
		List<HashMap<String,Object>> list =  getData(url, keyName, token, new SaveErrorLog("Modoc데이터 수신",keyName));
		if(list != null) {
			for(HashMap<String,Object> row : list) {
				cmmnDao.insert("modoc.ModocReciveData."+save, row);
			}
		}
	}
	
	
	/**
	 * modoc data 송신(선정완료)
	 * 
	 **/
	@Async
	public void modocSendData(HashMap<String, Object> pMap) throws Exception  {
		String token = login();
		pMap.put("token", token);
		sendMissions(pMap);
		sendTests(pMap);
	}
	
	private void sendMissions(HashMap<String, Object> pMap) throws Exception {
		String type = "missions";			
		String fn = "I"; // I(생성), U(수정)
		String resStr = "";		
		HashMap<String, Object> sqlMap = null;
		String token = Util.getValue("token", pMap);
		String ipntNo = Util.getValue("IPNT_NO", pMap);
		int ipntUpperSeq = NumberUtils.toInt(Util.getValue("IPNT_UPPER_SEQ", pMap));
				
		logger.info("############################# sendMissions start  #######################################");
		logger.info("IPNT_NO : {}, IPNT_UPPER_SEQ : {}", ipntNo, ipntUpperSeq);		
		
		// 해당 mission이 modoc에 존재하는지 체크 (IPNT_UPPER_SEQ)
		String checkStr = getDataForString("missions/" + ipntUpperSeq, "mission", token, new SaveErrorLog("mission조회 ", ipntUpperSeq+""));

		String errorCode = getErrorCode(checkStr);
		if (StringUtils.isEmpty(errorCode)) { //미션존제 
			HashMap<String, String> mission = getJson2Map(checkStr, "mission", new HashMap<String, String>().getClass());
			
			/* 삭제안되고 , id=IPNT_UPPER_SEQ, code = IPNT_NO 가 성립, 수정대상 */
			String deleted = mission.get("deleted");
			String id = mission.get("id");
			String code = mission.get("code");
			if("0".equals(deleted) && ipntNo.equals(code) && String.valueOf(ipntUpperSeq).equals(code)) { 
				fn = "U";
			}			
		}
		
		if("I".equals(fn)) {
			deleteMissions(type, String.valueOf(ipntUpperSeq), token);
						
			int newIpntUpperSeq = doUpdateIpntUpperSeq(ipntNo, ipntUpperSeq);
			if(newIpntUpperSeq <= 0)throw new CustomException("신규 IPNT_UPPER_SEQ 생성오류");
			logger.info("IPNT_UPPER_SEQ 변경 : {} ===> {}", ipntUpperSeq, newIpntUpperSeq);
			ipntUpperSeq = newIpntUpperSeq;
			pMap.put("IPNT_UPPER_SEQ", ipntUpperSeq);
		}
				
		sqlMap = (HashMap<String, Object>) cmmnDao.select("modoc.ModocApi.selectMissions", pMap);
		if(sqlMap==null)throw new CustomException("미션데이터 생성오류");

		List<Object> list_dcos = null;
		List<Object> list_urineAnalyses = null;
		List<Object> list_bloodAnalyses = null;
		List<Object> list_missionStatus = null;

		String strTestSituationsId = Util.getValue("testSituationsId", sqlMap);
		pMap.put("testSituationsId",strTestSituationsId);
		
		list_dcos = cmmnDao.selectList("modoc.ModocApi.selectDCOs", pMap);
		list_urineAnalyses = cmmnDao.selectList("modoc.ModocApi.selectUrineAnalyses", pMap);
        //list_bloodAnalyses = cmmnDao.selectList("API.selectBloodAnalyses", pMap);
		list_missionStatus = cmmnDao.selectList("modoc.ModocApi.selectMissionStatus", pMap);

		JSONObject jsonObject = new JSONObject(sqlMap);
		
    	JSONArray jsonArray_dcos = new JSONArray();
    	JSONArray jsonArray_urine = new JSONArray();
    	//JSONArray jsonArray_blood = new JSONArray();

		if (list_dcos != null) {
			for (Object obj : list_dcos) {
				Map<String, Integer> map = (Map<String, Integer>) obj;
				JSONObject jsonObjectDcos = new JSONObject();
				jsonObjectDcos.put("dcosId", map.get("dcosId"));
				jsonArray_dcos.add(jsonObjectDcos);
			}
			jsonObject.put("dcos", jsonArray_dcos);
		}
    	

    	if(list_urineAnalyses != null){
    		for(Object obj : list_urineAnalyses){
    			Map<String, Integer> map = (Map<String, Integer>) obj;
    			if(map!=null) {
    				JSONObject jsonObjectUrine = new JSONObject();
        			jsonObjectUrine.put("analysesId", map.get("analysesId"));
        			jsonArray_urine.add(jsonObjectUrine);	
    			}    			
    		}
    		jsonObject.put("analyses", jsonArray_urine);
    	}

    	
    	//timeZonesId
    	jsonObject.put("timeZonesId", "307");

		// 쿼리결과 Json변환
		String strJson = jsonObject.toString();
		
		if("I".equals(fn)){
			logger.info("================= insert Missions");
			resStr = postData(type, type.substring(0, type.length()-1) , token, strJson, new SaveErrorLog("mission등록", String.valueOf(ipntUpperSeq)));
	    	String errorCodePost = getErrorCode(resStr);
            
            if(StringUtils.isEmpty(errorCodePost)){            	
            	HashMap<String, Object> resMap = (HashMap<String, Object>) getJson2Map(resStr);
            	resMap.put("IPNT_NO", pMap.get("IPNT_NO"));
            	logger.info("================= insert Missions Result(2)");
            	logger.info("modoc에 등록된 mission id : {}",resMap.get("id"));//WADA에 신규등록시 ID를 리턴하여 필요시 adtis 데이터 수정
    	    	cmmnDao.update("modoc.ModocApi.updateZMISSIONS_APIOK", resMap);	
            }
		}else if("U".equals(fn)) {
			logger.info("================= update Missions");
			resStr = putData(type, "result", token, strJson, String.valueOf(ipntUpperSeq), new SaveErrorLog("mission수정", String.valueOf(ipntUpperSeq)));
		}
		
		logger.info("############################# sendMissions end  #######################################");	
		
	}

	private void deleteMissions(String type, String id, String token) throws ParseException {
		String putJson = "{\"id\": \"" + id + "\",\"deleted\": 1}";
		String resStr = putData(type, "result", token, putJson, id, new SaveErrorLog("미션삭제", id));
		logger.info("mission 삭제 : \n{}", Util.jsonFormatter(resStr));				
	}
	
	private int doUpdateIpntUpperSeq(String ipntNo, int ipntUpperSeq) throws Exception {
		HashMap<String, Object> param = new HashMap<>();
		param.put("I_IPNT_UPPER_SEQ", ipntUpperSeq);
        param.put("I_IPNT_NO", ipntNo);
        cmmnDao.callProcedure("modoc.ModocApi.doUpdateIpntUpperSeq", param);
        return NumberUtils.toInt(Util.getValue("O_NEW_IPNT_UPPER_SEQ", param));
	}
	
	
	private void sendTests(HashMap<String, Object> pMap) throws Exception {
		
		logger.info("############################# sendTests start  #######################################");
		Gson gson = new Gson();
		String type = "tests";
		String fn = "I";
		String period = Util.getValue("period", pMap); // 0
		String resStr = "";
		Map<String, String> sqlMap = null;
		String kadamMissionId = "";
		String modocMissionId = "";
		String token = Util.getValue("token", pMap);
		String ipntNo = Util.getValue("IPNT_NO", pMap);		
		int ipntUpperSeq = NumberUtils.toInt(Util.getValue("IPNT_UPPER_SEQ", pMap));
		
		List<Object> list_urineAnalyses = null;
		List<Object> list_bloodAnalyses = null;
		List<Object> list_testStatus = null;
		List<Object> list_dcos = cmmnDao.selectList("modoc.ModocApi.selectDCOs", pMap);
		List<Object> list_unRegAthlete = cmmnDao.selectList("modoc.ModocApi.selectAthletesUnRegModoc", pMap); //modoc 미등록선수

    	JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		//선수등록후 검사내용에 선수,종목,세부종목 추가
		for(int i = 0 ; list_unRegAthlete != null && i < list_unRegAthlete.size() ; i++) {
			HashMap<String,Object> row = (HashMap<String, Object>) list_unRegAthlete.get(i);
			row.put("token", token);
			this.regAthletes(row);
		}
		
		List<Object> list = cmmnDao.selectList("modoc.ModocApi.selectTests", pMap);
		for (int i = 0 ; list != null && i < list.size() ; i++) {
			HashMap<String,Object> row = (HashMap<String, Object>) list.get(i);

			for( HashMap.Entry<String, Object> entry : row.entrySet() ) {
			    String key = entry.getKey();
			    Object value = entry.getValue();
		    	if("id".equals(key)){
		    		pMap.put("test_no", String.valueOf(value));
		    	}
		    	jsonObject.put(key, value);
			}
			
			//list_dcos
	    	jsonArray = new JSONArray();
	    	if(list_dcos != null) {
	    		for(Object dco : list_dcos){
	    			HashMap<String, Integer> map = (HashMap<String, Integer>) dco;
		    		JSONObject jsonObjectDcos = new JSONObject();
		    		jsonObjectDcos.put("dcosId", map.get("dcosId"));
		    		jsonArray.add(jsonObjectDcos);
		    	}	
	    	}	    	
	    	jsonObject.put("dcos", jsonArray);

			String strTestSituationsId = String.valueOf(row.get("testSituationsId"));
			pMap.put("athletesId",String.valueOf(row.get("athletesId")));
						
			list_urineAnalyses = cmmnDao.selectList("modoc.ModocApi.selectUrineAnalysesTests", pMap);
			list_testStatus = cmmnDao.selectList("modoc.ModocApi.selectTestStatus", pMap);

			//analyses (UrineAnalyses과 BloodAnalyses를 analyses로 처리
	    	jsonArray = new JSONArray();
	    	if(list_urineAnalyses != null){
	    		for(Object urine : list_urineAnalyses){
	    			HashMap<String, Integer> map = (HashMap<String, Integer>) urine;
	    			if(map!=null) {
	    				JSONObject jsonObjectUrine = new JSONObject();
		    			jsonObjectUrine.put("analysesId", map.get("analysesId"));
		    			jsonArray.add(jsonObjectUrine);	
	    			}	    			
	    		}
	    		jsonObject.put("analyses", jsonArray);
	    	}

	    	//timeZonesId
	    	jsonObject.put("timeZonesId", "307");
	    	
	    	// 퀄리결과 Json변환
			String strJson = jsonObject.toString();
			String testId = String.valueOf(jsonObject.get("id"));
			kadamMissionId = String.valueOf(jsonObject.get("missionsId"));
			boolean delete = false;
			
			// K00_ZTESTS 해당id의 상태가 Y 이면 update
			if(list_testStatus.size() < 1) {
				fn = "I";
			} else {
				fn = "U";
			}
			
			// 해당 test가  modoc에 존재하는지 체크
			String checkStr = getDataForString("tests/" + testId, "test", token, new SaveErrorLog("Tests조회", testId));			
	    	String errorCode = getErrorCode(checkStr);
	    	
	    	if(StringUtils.isEmpty(errorCode)){ 	    		
	    		HashMap<String, Object> testMap = getJson2Map(checkStr,"test");
	    		modocMissionId = Util.getValue("missionsId", testMap);
	    		String deleted = Util.getValue("deleted", testMap);
	    		
	    		if(!kadamMissionId.equals(modocMissionId) && "0".equals(deleted)) { //test의  mission id가 다른 경우    			
	    			logger.info("다른 mission의 소속 test : \n{}", Util.jsonFormatter(jsonObject.toString()));
	    			fn = "I";
	    			deleteTests(type, testId, token);
	    			delete = true;
	    		}else {
	    			if("0".equals(deleted)) {
	    				fn = "U";	
	    			}else {
	    				fn = "I";
	    			}	    				
	    		}
	    	}else{ // 404
	    		fn = "I";	    		
	    	}
	    		    	
			if(fn.equals("I")){
				logger.info("================> insert tests");
				jsonObject.remove("id");
    			strJson = jsonObject.toString();
				
				resStr = postData(type, type.substring(0, type.length()-1) , token, strJson, new SaveErrorLog("Tests 등록", ""));				
		    	HashMap<String, Object> resMap = (HashMap<String, Object>) getJson2Map(resStr);
		    	logger.info("sendTest result id(test_no) : "+resMap.get("id"));//WADA에 신규등록시 ID를 리턴하여 필요시 adtis 데이터 수정
		    			    	
		    	if(delete) { //삭제후 test id신규생성
		    		HashMap<String,Object> paramMap = new HashMap<>();
			    	paramMap.put("id", testId);
			    	paramMap.put("new_id", resMap.get("id"));
			    	cmmnDao.update("modoc.ModocApi.updateZTESTS_API", paramMap);	
		    	}		    	
		    	cmmnDao.update("modoc.ModocApi.updateZTESTS_APIOK", resMap);
			}else if(fn.equals("U")){				
				logger.info("================> update tests");
				resStr = putData(type, "result", token, strJson, testId, new SaveErrorLog("Tests 수정", testId));
			}
        }

		logger.info("############################# sendTests end  #######################################");
		
	}
	
	/**
	 * 무기명 선수 신규등록
	 * K03_DOPING_INSPECT_DTL에 PLAYER_NO가 없는 경우
	 * 
	 */
	private void regAthletes(HashMap<String, Object> pMap) throws Exception {
		Gson gson = new Gson();
		String type = "athletes";
		String resStr = "";
		String token = Util.getValue("token", pMap);
		String userId = Util.getValue("USER_ID", pMap);
		String ipntSeq = Util.getValue("IPNT_SEQ", pMap);
		String ipntNo = Util.getValue("IPNT_NO", pMap);
		String sortNo = Util.getValue("SORT_NO", pMap);;
		
		pMap.remove("token");
		pMap.remove("USER_ID");
		pMap.remove("IPNT_SEQ");
		pMap.remove("IPNT_NO");
		pMap.remove("SORT_NO");
		
		String strJson = gson.toJson(pMap);
		resStr = postData(type, type.substring(0, type.length() - 1), token, strJson, 
				          new SaveErrorLog("modoc 미등록 선수 등록", Util.getValue("IPNT_NO", pMap) + "," + Util.getValue("IPNT_SEQ", pMap)));		
		HashMap<String, Object> resMap = (HashMap<String, Object>) getJson2Map(resStr);
		logger.info("return new athletes id : " + resMap.get("id"));
			
				
		HashMap<String,Object> pMap2 = new HashMap<>();
		pMap2.put("USER_ID", userId);
		pMap2.put("PLAYER_NO", resMap.get("id"));
		pMap2.put("IPNT_NO", ipntNo);
		pMap2.put("IPNT_SEQ", ipntSeq);
		pMap2.put("SORT_NO", sortNo);
		
		if(!StringUtils.isEmpty(userId)) { /* user01tb에 존재 */
			cmmnDao.update("modoc.ModocApi.updateAthletesForUSER01TB", pMap2); // PLAYER_NO 저장
		}else { /* 신규선수 저장 */
			HashMap<String, Object> proOrgzMap  = new HashMap<String, Object>() {{
			    put("OPTION6", pMap.get("sportsId"));
			    put("OPTION7", pMap.get("sportDisciplinesId"));
			}};
			String proOrgz = (String) cmmnDao.select("modoc.ModocApi.selectProOrgzCode", proOrgzMap);
			
			String birth = Util.getValue("dateOfBirth", pMap);
			birth = birth.length() > 10 ? birth.substring(0, 10): "";
			birth = birth.replaceAll("-", "");			
			
			userId = StringUtils.isEmpty(userId)? "be_"+resMap.get("id"): userId; /* user01tb에 미존재 */
			HashMap<String,Object> userMap = new HashMap<>();
			userMap.put("USER_ID", userId);
			userMap.put("PLAYER_NO", resMap.get("id"));
			userMap.put("USER_NAME", pMap.get("middleName"));
			userMap.put("USER_SEX", pMap.get("gender"));
			userMap.put("BIRTH_DT", birth);
			userMap.put("PRO_ORGZ", proOrgz);
			// 초기비밀번호생성 (등록에만 적용)
	        String initPwd = Util.newRandomPassword(10);
	        userMap.put("USER_PWD_ORI", initPwd);
	        userMap.put("USER_PWD", Util.SHA256(initPwd));
	        cmmnDao.update("modoc.ModocApi.insertAthletesForUSER01TB", userMap);
	        pMap2.put("USER_ID", userId);
		}
		// athletesId 업데이트
		cmmnDao.update("modoc.ModocApi.updateAthletesForDopingInspectDtl", pMap2);
		cmmnDao.update("modoc.ModocApi.updateAthletesForK00_ZTESTS", pMap2);
	}
	
	private void deleteTests(String type, String id, String token) throws ParseException {
		String putJson = "{\"id\": \"" + id + "\" , \"archived\": 0 ,\"deleted\": 1}";
		String resStr = putData(type, "result", token, putJson, id, new SaveErrorLog("테스트삭제", id));
		logger.info("test 삭제 : \n{}", Util.jsonFormatter(resStr));
	}
	
}

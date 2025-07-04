package ever.support.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RestUtil {
	public static String reqGetObj(String uri) {
		RestTemplate restTemplate = new RestTemplate();
		return (String) restTemplate.getForObject(uri, String.class, new Object[0]);
	}

	public static HashMap<String, Object> reqPostStr(String uri, String str) {
		HashMap<String, Object> retMap = new HashMap<>();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "plain", Charset.forName("UTF-8")));
		String url = UriComponentsBuilder.fromHttpUrl(uri).build().toString();
		HttpEntity<String> encordStr = new HttpEntity(str, (MultiValueMap) headers);
		try {
			String retStr = (String) restTemplate.postForObject(url, encordStr, String.class, new Object[0]);
			HashMap<String, Object> hMap = jsonStrToMap(retStr);
			retMap = hMap;
			if (retMap.isEmpty())
				retMap.put("code", "999");
		} catch (Exception e) {
			retMap.put("code", "999");
			retMap.put("messege", e);
		}
		return retMap;
	}

	public static HashMap<String, Object> jsonStrToMap(String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> retMap = new HashMap<>();
		try {
			retMap = (HashMap<String, Object>) mapper.readValue(jsonStr, new TypeReference<HashMap<String, Object>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retMap;
	}

	public static String mapTojsonStr(HashMap<String, Object> paramMap) {
		String retStr = "";
		try {
			retStr = (new ObjectMapper()).writeValueAsString(paramMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retStr;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @brief HashMap > url Param String
	 */
	public static String mapToParamUrlEncode(HashMap<String,Object> map, String exceptStr) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		
		for (Map.Entry<?,?> entry : map.entrySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			if (exceptStr.indexOf(entry.getKey().toString()) > 0) {
				sb.append( String.format( "%s=%s" , entry.getKey().toString() , entry.getValue( ).toString( ) ) );
			} else {
				sb.append( String.format( "%s=%s" , entry.getKey().toString() , URLEncoder.encode(entry.getValue( ).toString( ), "UTF-8") ) );
			}
		}

		return sb.toString();
	}
}

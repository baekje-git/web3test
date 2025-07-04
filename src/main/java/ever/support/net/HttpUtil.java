package ever.support.net;

import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private static String getHttp(String httpUrl, String keyName, String token, String jsonParam) {
		String resStr = "";
		try {
			logger.debug("==========================================================");
			logger.debug("GET Connection Start");
			logger.debug("GET Url : " + httpUrl);
			CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(httpUrl);
			getRequest.addHeader("Authorization", token);
			HttpResponse response = closeableHttpClient.execute((HttpUriRequest) getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
				resStr = (String) basicResponseHandler.handleResponse(response);
				JSONParser jsonParser = new JSONParser();
				HashMap<String, Object> resMap = (HashMap<String, Object>) jsonParser.parse(resStr);
				resStr = resMap.get(keyName).toString();
				logger.debug(resStr);
			} else {
				logger.debug("GET Connection response is error : " + response.getStatusLine().getStatusCode());
			}
			logger.debug("GET Connection End");
		} catch (Exception e) {
			logger.debug(e.toString());
			e.printStackTrace();
		}
		return resStr;
	}

	private static String postHttp(String httpUrl, String keyName, String token, String jsonParam) {
		String resStr = "";
		try {
			logger.debug("==========================================================");
			logger.debug("POST Connection Start");
			logger.debug("POST Url : " + httpUrl);
			CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(httpUrl);
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection", "keep-alive");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.addHeader("Authorization", token);
			postRequest.setEntity((HttpEntity) new StringEntity(jsonParam));
			HttpResponse response = closeableHttpClient.execute((HttpUriRequest) postRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
				resStr = (String) basicResponseHandler.handleResponse(response);
				JSONParser jsonParser = new JSONParser();
				HashMap<String, Object> resMap = (HashMap<String, Object>) jsonParser.parse(resStr);
				resStr = resMap.get(keyName).toString();
				logger.debug(resStr);
			} else {
				logger.debug("POST Connection response is error : " + response.getStatusLine().getStatusCode());
			}
			logger.debug("POST Connection End");
		} catch (Exception e) {
			logger.debug(e.toString());
			e.printStackTrace();
		}
		return resStr;
	}

	private static String putHttp(String httpUrl, String keyName, String token, String jsonParam) {
		String resStr = "";
		try {
			logger.debug("==========================================================");
			logger.debug("PUT Connection Start");
			logger.debug("PUT Url : " + httpUrl);
			CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
			HttpPut putRequest = new HttpPut(httpUrl);
			putRequest.setHeader("Accept", "application/json");
			putRequest.setHeader("Connection", "keep-alive");
			putRequest.setHeader("Content-Type", "application/json");
			putRequest.addHeader("Authorization", token);
			putRequest.setEntity((HttpEntity) new StringEntity(jsonParam));
			HttpResponse response = closeableHttpClient.execute((HttpUriRequest) putRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
				resStr = (String) basicResponseHandler.handleResponse(response);
				JSONParser jsonParser = new JSONParser();
				HashMap<String, Object> resMap = (HashMap<String, Object>) jsonParser.parse(resStr);
				resStr = resMap.get(keyName).toString();
				logger.debug(resStr);
			} else {
				logger.debug("PUT Connection response is error : " + response.getStatusLine().getStatusCode());
			}
			logger.debug("PUT Connection End");
		} catch (Exception e) {
			logger.debug(e.toString());
			e.printStackTrace();
		}
		return resStr;
	}
}

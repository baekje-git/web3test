package ever.inicis.service;

import java.net.URLEncoder;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;


@Service
public class InicisServiceImpl implements InicisService {
    
    private static final Logger logger = LoggerFactory.getLogger(InicisServiceImpl.class);
    
    @Resource(name="cmmnDao")
    private CmmnDao cmmnDao;
    
//    private static final HttpClient httpClient = HttpClient.newBuilder()
//            .connectTimeout(Duration.ofSeconds(10))
//            .build();
    
//    public String checkPcReturn(HashMap<String, Object> hMap) throws Exception {
//    	logger.info(">>> checkPcReturn hMap : " + hMap);
//    	
//    	if(!"0000".equals(hMap.get("resultCode").toString())) throw new IllegalAccessException("결제 실패");
//
//        // form parameters
//        Map<Object, Object> data = new HashMap<>();
//        data.put("mid", mid);
//        data.put("authToken", authToken);
//        data.put("timestamp", orderData.getTimestamp());
//        data.put("signature", HashUtils.getAuthSignature(authToken, orderData.getTimestamp()));
//        data.put("charset", charset);
//        data.put("format", "JSON");
//        data.put("price", orderData.getPrice());
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .POST(ofFormData(hMap))
//                .uri(URI.create(hMap.get("authUrl").toString()))
//                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        
//        logger.info(">>> response : " + response);
//        
//        return response.body();
//    }

//    public static HttpRequest.BodyPublisher ofFormData(Map<String, Object> data) {
//    	StringBuilder builder = new StringBuilder();
//        for (Entry<String, Object> entry : data.entrySet()) {
//            if (builder.length() > 0) {
//                builder.append("&");
//            }
//            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
//            builder.append("=");
//            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
//        }
//        return HttpRequest.BodyPublishers.ofString(builder.toString());
//    }

	@Override
	public void savePayment(HashMap<String, Object> hMap) throws Exception {
		logger.info(">>>  InicisServiceImpl savePayment: " + hMap);
		//cmmnDao.insert("xxxx.xxx.savePayment", hMap);
	}
    
}

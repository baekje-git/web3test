package ever.cmmn.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SimpMessagingTemplate template;
	
	private final WebSocketConfig webSocketConfig;

    public SocketController(WebSocketConfig webSocketConfig, SimpMessagingTemplate template) {
        this.webSocketConfig = webSocketConfig;
        this.template = template;
    }
	
	@MessageMapping("/receive")
	@SendTo("/send")
	public HashMap<String, Object> SocketHandler(HashMap<String, Object> hMap) throws Exception {
		logger.debug("initStomp==>{}", hMap);
		return hMap;
	}

    @MessageMapping("/{userId}")
    public void message(@DestinationVariable("userId") String userId) {
    	template.convertAndSend("/send/" + userId, "alarm socket connection completed.");
    }
    
    
    @MessageMapping("/exclude")
    //@SendTo("/send/exclude")
    public void gameGrid(HashMap<String, Object> hMap, SimpMessageHeaderAccessor headers) {
    	logger.debug("headers==>{}", headers);
    	sendTo("/send/exclude", hMap, headers);
    }

    private void sendTo(String destination, Object payload, SimpMessageHeaderAccessor headers) {
    	logger.debug("sendTo====================>");
    	Map<String, String> userMap = webSocketConfig.getSessionkeys();
    	logger.debug("userMap==>{}", userMap);
    	logger.debug("sessionId==>{}", headers.getSessionId());
    	
    	Map<String, String> subscriberMap = userMap.entrySet().stream()
    			.filter(x -> !x.getValue().equals(headers.getSessionId()))
    			.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
    	
    	logger.debug("subscriberMap==>{}", subscriberMap);
    	//KEY:USER_ID VALUE:STOMP_SESSION_ID
    	subscriberMap.forEach((k,v) -> template.convertAndSendToUser(v, destination, payload, createHeaders(v)));
    }
    
    /* convertAndSendToUser는 @SendToUser와 다르게 헤더정보가 없으므로 직접 입력해야 함. */
    private MessageHeaders createHeaders(@Nullable String sessionId) {
    	logger.debug("sessionId==>{}", sessionId);
    	SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if (sessionId != null) headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
      }
    
}

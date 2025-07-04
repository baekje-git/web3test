package ever.cmmn.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	//socket 유저 목록
	private static final Map<String, String> sessionKeys = new HashMap<>();
	
	public Map<String, String> getSessionkeys() {
		return sessionKeys;
	}

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
    @Override
    // 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/send");
        
        //specific user endpoint prefix (default /user)
        config.setUserDestinationPrefix("/user");
    }

    @Override
    // connection을 맺을때 CORS 허용합니다.
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
    // 스프링 5.3, 스프링부트 2.4 버전 부터 allowCredentials이 true인 경우 setAllowedOrigins 메서드에서 
    // 와일드 카드 `'*'`을 사용하실 수 없습니다.
      	.setAllowedOrigins("*")
        .withSockJS();
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        //registration.interceptors(stompHandler); // 핸들러 등록
        
    	// 메시지채널에서 주고받는 메시지를 확인 및/또는 수정할 수 있는 인터셉터 추가
        registration.interceptors(new ChannelInterceptor() {
            // 메시지가 실제로 채널로 전송되기 전에 호출됨
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor headerAccessor =
                    MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                
                logger.debug("headerAccessor:::::::::::::::{}", headerAccessor);
                
                if(headerAccessor.getCommand().equals(StompCommand.CONNECT)) {
                	sessionKeys.put(headerAccessor.getFirstNativeHeader("USER_ID"), headerAccessor.getSessionId());
                }
 
                // 사용자 접속 해제시 사용자 큐를 삭제한다.
                if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
                    String sessionKey = sessionKeys.get(headerAccessor.getFirstNativeHeader("USER_ID"));
                    sessionKeys.remove(sessionKey);
                }
 
                return message;
            }
        });
    }
    
}    
package ever.login.service;

import java.util.HashMap;
  
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface LoginService {

	ResponseEntity<Object> login(HashMap<String, Object> hMap, HttpServletRequest request) throws Exception;
	ResponseEntity<Object> loginCust(HashMap<String, Object> hMap, HttpServletRequest request) throws Exception;

}

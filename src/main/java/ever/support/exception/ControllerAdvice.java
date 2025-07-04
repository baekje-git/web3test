package ever.support.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
	@ExceptionHandler({ CustomException.class })
	public String custom() {
		return "hello custom";
	}
}

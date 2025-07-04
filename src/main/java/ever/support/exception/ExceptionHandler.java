package ever.support.exception;

import ever.support.cmmn.CmmnUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Object handleException(Exception e, HttpServletResponse response) {
		e.printStackTrace();
		logger.error(e.getMessage(),e);
		return CmmnUtil.resultMsg("FAIL", "처리중 오류가 발생하셨습니다.");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({ RuntimeException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Object handleRuntimeException(Exception e, HttpServletResponse response) {
		e.printStackTrace();
		logger.error(e.getMessage(),e);
		return CmmnUtil.resultMsg("FAIL", "처리중 오류가 발생하셨습니다.");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({ AjaxMsgCustomException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Object ajaxCustomException(Exception e, HttpServletRequest request, Model model) {
		e.printStackTrace();
		return CmmnUtil.resultMsg(AjaxMsgCustomException.getMessageCode(), e.getMessage());
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler({ CustomException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Object handleCustomException(Exception e, HttpServletRequest request, Model model) {
		e.printStackTrace();
		return CmmnUtil.resultMsg("FAIL", e.getMessage());
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler({ JwtCustomException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public Object handleJwtCustomException(Exception e, HttpServletRequest request, Model model) {
		e.printStackTrace();
		return e.getMessage();
	}
}
